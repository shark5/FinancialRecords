package com.jun.financialrecords;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.jun.financialrecords.util.BackupTask;


public class SettingActivity extends ActionBarActivity implements View.OnClickListener {
    private Context mContext;
    private String TAG = "SettingActivity";
    private RelativeLayout mInvestType;
    private RelativeLayout mBackuplayout;
    private RelativeLayout mRecoverLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mContext = this;
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        mInvestType = (RelativeLayout) findViewById(R.id.s_invest_type_layout);
        mInvestType.setOnClickListener(this);

        mBackuplayout = (RelativeLayout) findViewById(R.id.s_backup_layout);
        mBackuplayout.setOnClickListener(this);
        mRecoverLayout = (RelativeLayout) findViewById(R.id.s_recover_layout);
        mRecoverLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.s_invest_type_layout:
                Intent intent = new Intent(this, InvestTypeManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.s_backup_layout:
                dataBackup();
                break;
            case R.id.s_recover_layout:
                dataRecover();
                break;
            default:
                break;
        }
    }

    // 数据恢复
    private void dataRecover() {
        // TODO Auto-generated method stub
        new BackupTask(this).execute(BackupTask.COMMAND_RESTORE);
    }

    // 数据备份
    private void dataBackup() {
        // TODO Auto-generated method stub
        new BackupTask(this).execute(BackupTask.COMMAND_BACKUP);
    }
}
