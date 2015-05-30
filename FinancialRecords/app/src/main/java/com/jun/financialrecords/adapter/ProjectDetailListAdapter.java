package com.jun.financialrecords.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jun.financialrecords.R;
import com.jun.financialrecords.model.InvestDetail;
import com.jun.financialrecords.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jun on 2015/5/26.
 */

public class ProjectDetailListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<InvestDetail> mListData = new ArrayList<InvestDetail>();
    private Context mContext;

    public ProjectDetailListAdapter(Context mContext, List<InvestDetail> mListData) {
        this.mListData = mListData;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setListData(List<InvestDetail> mListData) {
        this.mListData = mListData;
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.project_detail_list_item, null);
            holder = new ViewHolder();
            holder.itemTotalAmountText = (TextView) convertView
                    .findViewById(R.id.pd_item_total_aAmount);
            holder.itemIncreaseText = (TextView) convertView
                    .findViewById(R.id.pd_item_increase);
            holder.itemDateTimeText = (TextView) convertView
                    .findViewById(R.id.pd_item_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemTotalAmountText.setText(mListData.get(position).getNowAmount() + "");
        holder.itemIncreaseText.setText(mListData.get(position).getIncrease() + "%");
        holder.itemDateTimeText.setText(TimeUtils.getTimeByDate(mListData.get(position).getDateTime()));
        return convertView;
    }

    public class ViewHolder {
        public TextView itemTotalAmountText;
        public TextView itemIncreaseText;
        public TextView itemDateTimeText;
    }
}



