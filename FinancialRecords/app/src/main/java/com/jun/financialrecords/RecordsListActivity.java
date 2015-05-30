package com.jun.financialrecords;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.jun.financialrecords.adapter.ProjectListAdapter;
import com.jun.financialrecords.model.InvestProject;
import com.jun.financialrecords.util.CommonDialogUtils;
import com.jun.financialrecords.util.Logger;
import com.jun.financialrecords.util.NormalFunctionUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class RecordsListActivity extends ActionBarActivity implements AbsListView.OnItemClickListener, AbsListView.OnItemLongClickListener {
    private String TAG = "RecordsListActivity";
    private ListView mContentListView;

    private Context mContext;
    private List<InvestProject> mListData = new ArrayList<InvestProject>();

    private ProjectListAdapter mListAdapter = null;

    private TextView mTotalAmount;
    private TextView mTotalCash;
    private TextView mTotalProfit;
    private TextView mTotalIncrease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records_list);
        Logger.w(TAG, "onCreate");
        mContext = this;

        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.w(TAG, "onResume");
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_records_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_add_records) {
            Intent intent = new Intent(this, AddRecordsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_update_data) {
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        mTotalAmount = (TextView) findViewById(R.id.rl_amount);
        mTotalCash = (TextView) findViewById(R.id.rl_total_aAmount);
        mTotalProfit = (TextView) findViewById(R.id.rl_profit);
        mTotalIncrease = (TextView) findViewById(R.id.rl_increase);

        ((TextView) findViewById(R.id.pl_item_name)).setText(getString(R.string.name));
        ((TextView) findViewById(R.id.pl_item_amount)).setText(getString(R.string.amount));
        ((TextView) findViewById(R.id.pl_item_total_aAmount)).setText(getString(R.string.total_cash));
        ((TextView) findViewById(R.id.pl_item_profit)).setText(getString(R.string.profit));
        ((TextView) findViewById(R.id.pl_item_increase)).setText(getString(R.string.increase));
        ((TextView) findViewById(R.id.pl_item_time)).setText(getString(R.string.date_time));

        mContentListView = (ListView) findViewById(R.id.rl_list);
        mContentListView.setOnItemClickListener(this);

        mContentListView.setOnItemLongClickListener(this);
        mListAdapter = new ProjectListAdapter(mContext, mListData);
        mContentListView.setAdapter(mListAdapter);
    }

    private void initData() {
        getDataFromDB();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mContext, ProjectDetailListActivity.class);
        intent.putExtra("projectId", mListData.get(position).getId());
        startActivity(intent);
    }

    private void getDataFromDB() {
        Logger.w(TAG, "getDataFromDB");

        new Thread(new Runnable() {
            @Override
            public void run() {
                mListData = DataSupport.findAll(InvestProject.class);
                adhandler.sendEmptyMessage(1);
            }
        }).start();
    }


    Handler adhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mListAdapter.setListData(mListData);
            mListAdapter.notifyDataSetChanged();

            setTotalData();
        }
    };

    private void setTotalData() {
        float totalAmount = 0;
        float totalCrash = 0;
        float totalProfit = 0;

        for (int i = 0; i < mListData.size(); i++) {
            totalAmount = totalAmount + mListData.get(i).getAmount();
            totalCrash = totalCrash + mListData.get(i).getTotalAmount();
            totalProfit = totalProfit + mListData.get(i).getProfit();
        }

        float increase = totalProfit / totalAmount * 100;
        mTotalAmount.setText(String.valueOf(totalAmount));
        mTotalCash.setText(String.valueOf(totalCrash));
        mTotalProfit.setText(String.valueOf(totalProfit));
        mTotalIncrease.setText(String.valueOf(NormalFunctionUtils.getFloatFormat(increase, 2)) + "%");
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        Logger.w(TAG, "position:" + position);

        CommonDialogUtils.showDeleteDialog(mContext, new CommonDialogUtils.CommonDeleteDialogListener() {
            @Override
            public void delete() {
                DataSupport.delete(InvestProject.class, mListData.get(position).getId());
                mListData.remove(position);
                mListAdapter.setListData(mListData);
                mListAdapter.notifyDataSetChanged();
                setTotalData();
            }
        });
        return false;
    }
}
