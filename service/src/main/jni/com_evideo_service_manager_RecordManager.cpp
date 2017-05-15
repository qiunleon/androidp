#include <jni.h>

extern "C"{
#include "libavutil/mathematics.h"
#include "libavutil/log.h"
#include "libavformat/avformat.h"
}

#include <android/log.h>
#define LOGI(fmt, args...) __android_log_print(ANDROID_LOG_INFO,  "(^_^)", fmt, ##args)
#define LOGE(fmt, args...) __android_log_print(ANDROID_LOG_ERROR, "(>_<)", fmt, ##args)

//'1': Use H.264 bit stream filter
#define USE_H264BSF 0

int stopFlag;
/*
 * Class:     com_evideo_service_manager_RecordManager
 * Method:    native_start
 * Signature: (Ljava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_evideo_service_manager_RecordManager_native_1start
  (JNIEnv* jenv, jobject jobj, jstring input, jstring output){
  	stopFlag = 0;

	jboolean iscopy;
	const char *input_path_utf = jenv->GetStringUTFChars(input, &iscopy);
	const char *output_path_utf = jenv->GetStringUTFChars(output, &iscopy);

  	AVOutputFormat *ofmt = NULL;
  	//Input AVFormatContext and Output AVFormatContext
  	AVFormatContext *ifmt_ctx = NULL, *ofmt_ctx = NULL;
  	AVPacket pkt;
  	int ret, i;
  	int videoindex=-1;
  	int frame_index=0;

  	av_register_all();
  	//Network
  	avformat_network_init();
  	//Input
  	if ((ret = avformat_open_input(&ifmt_ctx, input_path_utf, 0, 0)) < 0) {
  		LOGE("Couldn't open input stream.\n");
  		goto end;
  	}
  	if ((ret = avformat_find_stream_info(ifmt_ctx, 0)) < 0) {
  		LOGE("Couldn't find stream information.\n");
  		goto end;
  	}

  	for(i=0; i<ifmt_ctx->nb_streams; i++)
  		if(ifmt_ctx->streams[i]->codec->codec_type==AVMEDIA_TYPE_VIDEO){
  			videoindex=i;
  			break;
  		}

  	av_dump_format(ifmt_ctx, 0, input_path_utf, 0);

  	//Output
  	avformat_alloc_output_context2(&ofmt_ctx, NULL, NULL, output_path_utf); //RTMP

  	if (!ofmt_ctx) {
  		LOGE( "Could not create output context\n");
  		ret = AVERROR_UNKNOWN;
  		goto end;
  	}
  	ofmt = ofmt_ctx->oformat;
  	for (i = 0; i < ifmt_ctx->nb_streams; i++) {
  		//Create output AVStream according to input AVStream
  		AVStream *in_stream = ifmt_ctx->streams[i];
  		AVStream *out_stream = avformat_new_stream(ofmt_ctx, in_stream->codec->codec);
  		if (!out_stream) {
  			LOGE( "Failed allocating output stream\n");
  			ret = AVERROR_UNKNOWN;
  			goto end;
  		}
  		//Copy the settings of AVCodecContext
  		ret = avcodec_copy_context(out_stream->codec, in_stream->codec);
  		if (ret < 0) {
  			LOGE( "Failed to copy context from input to output stream codec context\n");
  			goto end;
  		}
  		out_stream->codec->codec_tag = 0;
  		if (ofmt_ctx->oformat->flags & AVFMT_GLOBALHEADER)
  			out_stream->codec->flags |= CODEC_FLAG_GLOBAL_HEADER;
  	}
  	//Dump Format------------------
  	av_dump_format(ofmt_ctx, 0, output_path_utf, 1);
  	//Open output URL
  	if (!(ofmt->flags & AVFMT_NOFILE)) {
  		ret = avio_open(&ofmt_ctx->pb, output_path_utf, AVIO_FLAG_WRITE);
  		if (ret < 0) {
  			LOGE( "Could not open output URL '%s'", output_path_utf);
  			goto end;
  		}
  	}
  	//Write file header
  	ret = avformat_write_header(ofmt_ctx, NULL);
  	if (ret < 0) {
  		LOGE( "Error occurred when opening output URL\n");
  		goto end;
  	}

  	#if USE_H264BSF
  		AVBitStreamFilterContext* h264bsfc =  av_bitstream_filter_init("h264_mp4toannexb");
  	#endif

  	while (1) {
  		AVStream *in_stream, *out_stream;
  		//Get an AVPacket
  		ret = av_read_frame(ifmt_ctx, &pkt);
  		if (ret < 0)
  			break;

  		in_stream  = ifmt_ctx->streams[pkt.stream_index];
  		out_stream = ofmt_ctx->streams[pkt.stream_index];
  		/* copy packet */
  		//Convert PTS/DTS
  		pkt.pts = av_rescale_q_rnd(pkt.pts, in_stream->time_base, out_stream->time_base, AV_ROUND_NEAR_INF);
  		pkt.dts = av_rescale_q_rnd(pkt.dts, in_stream->time_base, out_stream->time_base, AV_ROUND_NEAR_INF);
  		pkt.duration = av_rescale_q(pkt.duration, in_stream->time_base, out_stream->time_base);
  		pkt.pos = -1;
  		//Print to Screen
  		if(pkt.stream_index==videoindex){
  			LOGE("Receive %8d video frames from input URL\n",frame_index);
  			frame_index++;

  		#if USE_H264BSF
  					av_bitstream_filter_filter(h264bsfc, in_stream->codec, NULL, &pkt.data, &pkt.size, pkt.data, pkt.size, 0);
  		#endif
  		}
  		//ret = av_write_frame(ofmt_ctx, &pkt);
  		ret = av_interleaved_write_frame(ofmt_ctx, &pkt);

  		if (ret < 0) {
  			LOGE( "Error muxing packet\n");
  			break;
  		}

  		av_free_packet(&pkt);

  	}

  	#if USE_H264BSF
  		av_bitstream_filter_close(h264bsfc);
  	#endif

  	//Write file trailer
  	av_write_trailer(ofmt_ctx);
  end:
  	avformat_close_input(&ifmt_ctx);
  	/* close output */
  	if (ofmt_ctx && !(ofmt->flags & AVFMT_NOFILE))
  		avio_close(ofmt_ctx->pb);
  	avformat_free_context(ofmt_ctx);
  	if (ret < 0 && ret != AVERROR_EOF) {
  		LOGE( "Error occurred.\n");
  		return -1;
  	}
  	return 0;
  }

/*
 * Class:     com_evideo_service_manager_RecordManager
 * Method:    native_stop
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_evideo_service_manager_RecordManager_native_1stop
  (JNIEnv* jenv, jobject jobj){
  	  stopFlag = -1;
  	  return 0;
  }
