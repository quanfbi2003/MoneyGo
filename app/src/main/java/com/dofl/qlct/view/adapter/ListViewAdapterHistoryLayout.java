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
import com.dofl.qlct.presenter.utils.DataProcessing;

import java.util.List;

public class ListViewAdapterHistoryLayout extends BaseAdapter {
    private final Context context;
    private List<Record> listRecord;

    public ListViewAdapterHistoryLayout(Context context, List<Record> listRecord) {
        this.context = context;
        this.listRecord = listRecord;
    }

    public void update(List<Record> listRecord) {
        this.listRecord = listRecord;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listRecord.size();
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

        description.setText(listRecord.get(position).getDescription());
        date.setText(listRecord.get(position).getDateCreate());
        time.setText(listRecord.get(position).getTimeCreate());
        money.setText(DataProcessing.formatIntToString(listRecord.get(position).getTotal()) + " đ");
        icon.setImageResource(listRecord.get(position).getIcon());
        return convertView;
    }
}
