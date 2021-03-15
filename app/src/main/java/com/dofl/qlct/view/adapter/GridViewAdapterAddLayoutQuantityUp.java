package com.dofl.qlct.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.dofl.qlct.R;

public class GridViewAdapterAddLayoutQuantityUp extends BaseAdapter {
    private final Context context;
    private final String[] functionName;

    public GridViewAdapterAddLayoutQuantityUp(Context context, String[] functionName) {
        this.context = context;
        this.functionName = functionName;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.gridview_custom_add_layout_quantity_up, null);
        return convertView;
    }
}
