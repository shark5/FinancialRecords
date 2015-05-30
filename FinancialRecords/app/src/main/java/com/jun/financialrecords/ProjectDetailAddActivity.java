package com.jun.financialrecords;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jun.financialrecords.model.InvestDetail;
import com.jun.financialrecords.model.InvestProject;
import com.jun.financialrecords.util.Logger;
import com.jun.financialrecords.util.NormalFunctionUtils;
import com.jun.financialrecords.util.TimeUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ProjectDetailAddActivity extends ActionBarActivity implements View.OnClickListener {
    private String TAG = "ProjectDetailAddActivity";
    private Context mContext;

    private LinearLayout mInvestDateLayout;
    private TextView mInvestDateTx;
    private Button mAddBtn;
    private EditText mAmountEd;

    private int mProjectId = 0;
    private InvestProject mInvestProject;

    InvestDetail mInvestDetail = new InvestDetail();
    private List<InvestDetail> mListData = new ArrayList<InvestDetail>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail_add);
        mProjectId = getIntent().getExtras().getInt("projectId");
        mContext = this;
        initView();
        initData();
    }

    private void initData() {
        mInvestDateTx.setText(TimeUtils.getNowTime());
        mInvestDetail.setDateTime(new Date());
        getDataFromDB();
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
            setTitle(mInvestProject.getProjectName());
        }
    };
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project_detail_add, menu);
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
    }*/

    private void initView() {

        mInvestDateLayout = (LinearLayout) findViewById(R.id.pda_invest_date_layout);
        mInvestDateLayout.setOnClickListener(this);
        mInvestDateTx = (TextView) findViewById(R.id.pda_invest_date_tx);

        mAddBtn = (Button) findViewById(R.id.pda_add_btn);
        mAddBtn.setOnClickListener(this);

        mAmountEd = (EditText) findViewById(R.id.pda_invest_amount_ed);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pda_invest_date_layout:
                showTimeSelectDialog();
                break;
            case R.id.pda_add_btn:
                addInvestProject();
                break;
            default:

                break;
        }
    }

    private void showTimeSelectDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(mContext, AlertDialog.THEME_HOLO_LIGHT, setDateCallBack, year, month, day);
        dialog.show();
    }

    private DatePickerDialog.OnDateSetListener setDateCallBack = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Date date = calendar.getTime();
            mInvestDetail.setDateTime(date);
            mInvestDateTx.setText(TimeUtils.getTimeByDate(date));
        }
    };

    private void addInvestProject() {
        String projectAmount = mAmountEd.getText().toString();

        if (projectAmount.equals("")) {
            Toast.makeText(mContext, getString(R.string.pls_input_all_info), Toast.LENGTH_SHORT).show();
            return;
        }

        mInvestProject.setTotalAmount(Float.parseFloat(projectAmount));
        mInvestProject.setProfit(Float.parseFloat(projectAmount) - mInvestProject.getAmount());
        float increase = mInvestProject.getProfit() / mInvestProject.getAmount() * 100;

        mInvestProject.setIncrease(NormalFunctionUtils.getFloatFormat(increase, 2));
        mInvestProject.save();

        mInvestDetail.setInvestProject(mInvestProject);
        mInvestDetail.setNowAmount(Float.parseFloat(projectAmount));
        increase = (mInvestDetail.getNowAmount() - mListData.get(mListData.size() - 1).getNowAmount()) / mListData.get(mListData.size() - 1).getNowAmount() * 100;
        mInvestDetail.setIncrease(NormalFunctionUtils.getFloatFormat(increase, 2));
        mInvestDetail.save();

        Toast.makeText(mContext, getString(R.string.add_success), Toast.LENGTH_SHORT).show();
        finish();
    }
}
