package com.dofl.qlct.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dofl.qlct.R;

public class GridViewAdapterAddLayoutInfo extends BaseAdapter {
    private final Context context;
    private final String[] functionName;
    private final int[] functionImage;

    public GridViewAdapterAddLayoutInfo(Context context, String[] functionName, int[] functionImage) {
        this.context = context;
        this.functionName = functionName;
        this.functionImage = functionImage;
    }

    @Override
    public int getCount() {
        return functionName.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.gridview_custom_add_layout_info, null);
        TextView textView = (TextView) convertView.findViewById(R.id.textView);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        textView.setText(functionName[position]);
        imageView.setImageResource(functionImage[position]);
        return convertView;
    }
}