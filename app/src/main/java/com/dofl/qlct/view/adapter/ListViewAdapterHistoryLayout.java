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
import com.dofl.qlct.model.Record;

import java.util.ArrayList;

public class ListViewAdapterHistoryLayout extends BaseAdapter {
    private final Context context;
    private final ArrayList<Record> recordArrayList;

    public ListViewAdapterHistoryLayout(Context context, ArrayList<Record> recordArrayList) {
        this.context = context;
        this.recordArrayList = recordArrayList;
    }

    @Override
    public int getCount() {
        return recordArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint({"SetTextI18n", "ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.listview_custom_search_history_layout, null);
        TextView description = convertView.findViewById(R.id.description);
        TextView date = convertView.findViewById(R.id.date);
        TextView time = convertView.findViewById(R.id.time);
        TextView money = convertView.findViewById(R.id.money);
        ImageView icon = convertView.findViewById(R.id.icon);

        description.setText(recordArrayList.get(position).getDescription());
        date.setText(recordArrayList.get(position).getDate_create());
        time.setText(recordArrayList.get(position).getTime_create());
        money.setText(recordArrayList.get(position).getTotal() + " đ");
        icon.setImageResource(recordArrayList.get(position).getIcon());
        return convertView;
    }
}
