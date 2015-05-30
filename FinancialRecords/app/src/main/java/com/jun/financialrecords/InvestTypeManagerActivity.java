package com.jun.financialrecords;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.jun.financialrecords.adapter.TypeListAdapter;
import com.jun.financialrecords.model.InvestType;
import com.jun.financialrecords.util.CommonDialogUtils;
import com.jun.financialrecords.util.Logger;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 投资类型管理
 */
public class InvestTypeManagerActivity extends ActionBarActivity {
    private String TAG = "InvestTypeManagerActivity";
    private ListView mContentListView;

    private Context mContext;

    private List<InvestType> mListData = new ArrayList<InvestType>();
    private TypeListAdapter mListAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invest_type_manager);
        mContext = this;
        initView();
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_invest_type_manager, menu);
        return true;
    }

    private void initView() {
        mContentListView = (ListView) findViewById(R.id.it_list);
        mListAdapter = new TypeListAdapter(mContext, mListData);
        mContentListView.setAdapter(mListAdapter);
    }

    private void initData() {
        getDataFromDB();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.it_add_type) {
            CommonDialogUtils.showCommonInputDialog(mContext, getString(R.string.add_invest_type_manager), getString(R.string.add), getString(R.string.cancel), new CommonDialogUtils.CommonInputDialogListener() {
                @Override
                public void okBtnClick(String inputText) {

                    if (!inputText.equals("")) {
                        addType(inputText);
                    }

                }

                @Override
                public void cancelBtnClick() {

                }

                @Override
                public Boolean inputCheck(String inputText) {
                    return true;
                }
            }, null);
        }
        return super.onOptionsItemSelected(item);
    }

    private void addType(String name) {
        InvestType investType = new InvestType();
        investType.setName(name);
        investType.save();
        getDataFromDB();
    }

    /**
     * 从数据库读取数据
     */
    private void getDataFromDB() {
        Logger.w(TAG, "getDataFromDB");
        new Thread(new Runnable() {
            @Override
            public void run() {
                mListData = DataSupport.findAll(InvestType.class);
                ithandler.sendEmptyMessage(1);
            }
        }).start();
    }


    Handler ithandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mListAdapter.setListData(mListData);
            mListAdapter.notifyDataSetChanged();
        }
    };


}
