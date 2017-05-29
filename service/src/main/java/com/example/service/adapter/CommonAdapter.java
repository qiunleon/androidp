package com.example.service.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.service.R;

import java.util.ArrayList;

public class CommonAdapter<T> extends BaseAdapter {

    private ArrayList<T> mData;

    private Context mContext;

    public CommonAdapter(Context context, ArrayList<T> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        if (mData == null) {
            return null;
        }
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {

        Hold hold;
        if (contentView == null) {
            contentView = LayoutInflater.from(mContext).inflate(R.layout.listview_item_main, null);
            hold = new Hold();
            hold.mTextView = (TextView) contentView.findViewById(R.id.listview_item_text);
            contentView.setTag(hold);
        } else {
            hold = (Hold) contentView.getTag();
        }
        hold.mTextView.setText("ListView Item");
        return contentView;
    }

    private static class Hold {
        TextView mTextView;
    }
}
