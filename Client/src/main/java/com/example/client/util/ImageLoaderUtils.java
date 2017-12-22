package com.example.client.util;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.LruCache;

import com.example.client.application.ClientApp;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

/**
 * Created by alienware on 2017/6/12.
 */

public class ImageLoaderUtils {

    private static ImageLoader imageLoader = ImageLoader.getInstance();

    private static LruCache<String, Bitmap> mMemoryCache;

    private static Bitmap mBitmap;

    public static void init() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(ClientApp.getInstance().getApplicationContext())
//                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
//                .diskCacheExtraOptions(480, 800, null)
                .threadPoolSize(3) // default
                .threadPriority(Thread.NORM_PRIORITY - 2) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .diskCache(new UnlimitedDiskCache(Environment.getDownloadCacheDirectory())) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(ClientApp.getInstance().getApplicationContext())) // default
                .imageDecoder(new BaseImageDecoder(true)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs()
                .build();

        imageLoader.init(config);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = 1024 * 1024 * 10 / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in bytes rather than number of items.
                return bitmap.getByteCount();
            }
        };
    }

    public static Bitmap loadImage(final String imageUri) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                mBitmap = getBitmapFromMemCache(imageUri);
                if (mBitmap == null) {
                    mBitmap = imageLoader.loadImageSync(imageUri);
                    addBitmapToMemoryCache(imageUri, mBitmap);
                }
            }
        }).start();
        return mBitmap;
    }

    private static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    private static Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }
}
