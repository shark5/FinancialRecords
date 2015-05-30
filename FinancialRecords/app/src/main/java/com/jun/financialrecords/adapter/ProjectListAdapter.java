package com.jun.financialrecords.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jun.financialrecords.R;
import com.jun.financialrecords.model.InvestProject;
import com.jun.financialrecords.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jun on 2015/5/26.
 */

public class ProjectListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<InvestProject> mListData = new ArrayList<InvestProject>();
    private Context mContext;

    public ProjectListAdapter(Context mContext, List<InvestProject> mListData) {
        this.mListData = mListData;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setListData(List<InvestProject> mListData) {
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
            convertView = mInflater.inflate(R.layout.project_list_item, null);
            holder = new ViewHolder();
            holder.itemNameText = (TextView) convertView
                    .findViewById(R.id.pl_item_name);
            holder.itemAmountText = (TextView) convertView
                    .findViewById(R.id.pl_item_amount);
            holder.itemTotalAmountText = (TextView) convertView
                    .findViewById(R.id.pl_item_total_aAmount);
            holder.itemProfitText = (TextView) convertView
                    .findViewById(R.id.pl_item_profit);
            holder.itemIncreaseText = (TextView) convertView
                    .findViewById(R.id.pl_item_increase);
            holder.itemDateTimeText = (TextView) convertView
                    .findViewById(R.id.pl_item_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemNameText.setText(mListData.get(position).getProjectName());
        holder.itemAmountText.setText(mListData.get(position).getAmount() + "");
        holder.itemTotalAmountText.setText(mListData.get(position).getTotalAmount() + "");
        holder.itemProfitText.setText(mListData.get(position).getProfit() + "");
        holder.itemIncreaseText.setText(mListData.get(position).getIncrease() + "%");
        holder.itemDateTimeText.setText(TimeUtils.getTimeByDate(mListData.get(position).getDateTime()));
        return convertView;
    }

    public class ViewHolder {
        public TextView itemNameText;
        public TextView itemAmountText;
        public TextView itemTotalAmountText;
        public TextView itemProfitText;
        public TextView itemIncreaseText;
        public TextView itemDateTimeText;
    }
}



