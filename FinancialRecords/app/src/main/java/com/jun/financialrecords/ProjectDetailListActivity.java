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

import com.jun.financialrecords.adapter.ProjectDetailListAdapter;
import com.jun.financialrecords.model.InvestDetail;
import com.jun.financialrecords.model.InvestProject;
import com.jun.financialrecords.util.CommonDialogUtils;
import com.jun.financialrecords.util.Logger;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目明细
 */
public class ProjectDetailListActivity extends ActionBarActivity implements AbsListView.OnItemClickListener,
        AbsListView.OnItemLongClickListener {
    private String TAG = "ProjectDetailListActivity";
    private ListView mContentListView;
    private static final int ITEM1 = Menu.FIRST;
    private Context mContext;
    private List<InvestDetail> mListData = new ArrayList<InvestDetail>();

    private ProjectDetailListAdapter mListAdapter = null;
    private int mProjectId = 0;
    private InvestProject mInvestProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail_list);
        Logger.w(TAG, "onCreate");
        mContext = this;
        mProjectId = getIntent().getExtras().getInt("projectId");
        initView();
//        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.w(TAG, "onResume");
        initData();
    }

    private void initView() {

        ((TextView) findViewById(R.id.pd_item_total_aAmount)).setText(getString(R.string.total_cash));
        ((TextView) findViewById(R.id.pd_item_increase)).setText(getString(R.string.increase));
        ((TextView) findViewById(R.id.pd_item_time)).setText(getString(R.string.date_time));

        mContentListView = (ListView) findViewById(R.id.pd_list);
        mContentListView.setOnItemClickListener(this);
        mContentListView.setOnItemLongClickListener(this);
        mListAdapter = new ProjectDetailListAdapter(mContext, mListData);
        mContentListView.setAdapter(mListAdapter);
    }

    private void initData() {
        getDataFromDB();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void getDataFromDB() {
        Logger.w(TAG, "getDataFromDB");

        new Thread(new Runnable() {
            @Override
            public void run() {
                mInvestProject = DataSupport.find(InvestProject.class, mProjectId, true);
                mListData = mInvestProject.getInvestDetailList();
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
            setTitle(mInvestProject.getProjectName());
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project_detail_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_data) {
            Intent intent = new Intent(this, ProjectDetailAddActivity.class);
            intent.putExtra("projectId", mProjectId);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        Logger.w(TAG, "position:" + position);

        CommonDialogUtils.showDeleteDialog(mContext, new CommonDialogUtils.CommonDeleteDialogListener() {
            @Override
            public void delete() {
                DataSupport.delete(InvestDetail.class, mListData.get(position).getId());
                mListData.remove(position);
                mListAdapter.setListData(mListData);
                mListAdapter.notifyDataSetChanged();
            }
        });
        return false;
    }

}
