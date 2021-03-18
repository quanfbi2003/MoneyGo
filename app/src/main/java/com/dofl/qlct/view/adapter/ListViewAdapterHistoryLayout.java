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
import com.dofl.qlct.model.ListViewHistoryElement;

import java.util.ArrayList;

public class ListViewAdapterHistoryLayout extends BaseAdapter {
    private final Context context;
    private final ArrayList<ListViewHistoryElement> listViewHistoryElement;

    public ListViewAdapterHistoryLayout(Context context, ArrayList<ListViewHistoryElement> listViewHistoryElement) {
        this.context = context;
        this.listViewHistoryElement = listViewHistoryElement;
    }

    @Override
    public int getCount() {
        return listViewHistoryElement.size();
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
        TextView dateTime = convertView.findViewById(R.id.date_time);
        TextView money = convertView.findViewById(R.id.money);
        ImageView icon = convertView.findViewById(R.id.icon);

        description.setText(listViewHistoryElement.get(position).getDescription());
        dateTime.setText(listViewHistoryElement.get(position).getDateTime());
        money.setText(listViewHistoryElement.get(position).getMoney() + " đ");
        icon.setImageResource(listViewHistoryElement.get(position).getIcon());
        return convertView;
    }
}
