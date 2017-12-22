package com.example.client.activity.own;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.client.R;
import com.example.client.data.Image;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by yunliangqiu on 2017/6/3.
 */
public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private Context mContext;
    private List<Image> mData;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    GridAdapter(Context context, List<Image> mData) {
        mContext = context;
        this.mData = mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.grid_image_item, parent, false);
            ImageHolder holder = new ImageHolder(view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            return holder;
        } else {
            return new TextHolder(LayoutInflater.from(mContext).inflate(R.layout.page_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ImageHolder) {
            Picasso.with(mContext).load(mData.get(position).getUrl()).into(((ImageHolder) holder).image);
        } else if (holder instanceof TextHolder) {
            ((TextHolder) holder).text.setText(String.valueOf(mData.get(position).getPage()));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (!TextUtils.isEmpty(mData.get(position).getUrl())) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemLongClick(v);
        }
        return false;
    }

    private class ImageHolder extends RecyclerView.ViewHolder {
        private ImageView image;

        private ImageHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
        }
    }

    private class TextHolder extends RecyclerView.ViewHolder {
        private TextView text;

        private TextHolder(View view) {
            super(view);
            text = (TextView) view.findViewById(R.id.text);
        }
    }

    interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);

        void onItemLongClick(View view);
    }
}
