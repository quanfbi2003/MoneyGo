package com.dofl.moneygo.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dofl.moneygo.R;

public class GridViewMenuAdapter extends BaseAdapter {
    private final Context context;
    private final String[] functionName;
    private final int[] functionImage;

    public GridViewMenuAdapter(Context context) {
        this.context = context;
        this.functionName = new String[]{"Thêm mới", "Lịch sử ghi chép", "Báo cáo tổng quan",
                "Bảng giá dịch vụ", "Phân tích tài chính", "Quản trị hệ thống"};
        this.functionImage = new int[]{R.drawable.main_menu_add, R.drawable.main_menu_search_history,
                R.drawable.main_menu_report, R.drawable.main_menu_fee_detail,
                R.drawable.main_menu_analyze, R.drawable.main_menu_manage};
    }

    public GridViewMenuAdapter(Context context, String[] functionName, int[] functionImage) {
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
        convertView = layoutInflater.inflate(R.layout.grid_view_menu_layout, null);
        TextView textView = convertView.findViewById(R.id.textView);
        ImageView imageView = convertView.findViewById(R.id.imageView);

        textView.setText(functionName[position]);
        imageView.setImageResource(functionImage[position]);
        return convertView;
    }
}
