package com.jun.financialrecords.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jun.financialrecords.R;
import com.jun.financialrecords.model.InvestType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jun on 2015/5/26.
 */

public class TypeListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<InvestType> mListData = new ArrayList<InvestType>();
    private Context mContext;

    public TypeListAdapter(Context mContext, List<InvestType> mListData) {
        this.mListData = mListData;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setListData(List<InvestType> mListData) {
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
            convertView = mInflater.inflate(R.layout.invest_type_list_item, null);
            holder = new ViewHolder();
            holder.itemNameText = (TextView) convertView
                    .findViewById(R.id.it_item_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemNameText.setText(mListData.get(position).getName());
        return convertView;
    }

    public class ViewHolder {
        public TextView itemNameText;
    }
}



