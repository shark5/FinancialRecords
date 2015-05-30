package com.jun.financialrecords;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jun.financialrecords.adapter.TypeListAdapter;
import com.jun.financialrecords.model.InvestDetail;
import com.jun.financialrecords.model.InvestProject;
import com.jun.financialrecords.model.InvestType;
import com.jun.financialrecords.util.CommonDialogUtils;
import com.jun.financialrecords.util.Logger;
import com.jun.financialrecords.util.TimeUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 添加项目
 */
public class AddRecordsActivity extends ActionBarActivity implements View.OnClickListener {
    private String TAG = "AddRecordsActivity";

    final static private int REQUEST_CODE = 101;
    private Context mContext;

    private List<InvestType> mListData = new ArrayList<InvestType>();
    private InvestProject mInvestProject = new InvestProject();

    private LinearLayout mInvestTypeLayout;
    private TextView mInvestTypeTx;
    private LinearLayout mInvestDateLayout;
    private TextView mInvestDateTx;
    private Button mAddBtn;
    private EditText mNameEd;
    private EditText mAmountEd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_records);
        Logger.w(TAG, "onCreate");
        mContext = this;
        initView();
        initData();
    }

    private void initView() {
        mInvestTypeLayout = (LinearLayout) findViewById(R.id.ar_invest_type_layout);
        mInvestTypeLayout.setOnClickListener(this);
        mInvestTypeTx = (TextView) findViewById(R.id.ar_invest_type_tx);

        mInvestDateLayout = (LinearLayout) findViewById(R.id.ar_invest_date_layout);
        mInvestDateLayout.setOnClickListener(this);
        mInvestDateTx = (TextView) findViewById(R.id.ar_invest_date_tx);

        mAddBtn = (Button) findViewById(R.id.ar_add_btn);
        mAddBtn.setOnClickListener(this);

        mNameEd = (EditText) findViewById(R.id.ar_invest_name_ed);
        mAmountEd = (EditText) findViewById(R.id.ar_invest_amount_ed);
    }

    private void initData() {
        Logger.w(TAG, "initData");
        mInvestDateTx.setText(TimeUtils.getNowTime());
        mInvestProject.setDateTime(new Date());
        getDataFromDB();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ar_invest_type_layout:
                if (mListData.size() > 0) {
                    selectInvestType();
                } else {
                    Toast.makeText(mContext, getString(R.string.pls_go_to_settings_to_add_type), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ar_invest_date_layout:
                showTimeSelectDialog();
                break;
            case R.id.ar_add_btn:
                addInvestProject();
                break;
            default:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {

        }
    }

    /**
     * 读取数据
     */
    private void getDataFromDB() {
        Logger.w(TAG, "getDataFromDB");
        new Thread(new Runnable() {
            @Override
            public void run() {
                mListData = DataSupport.findAll(InvestType.class);
                arhandler.sendEmptyMessage(1);
            }
        }).start();
    }

    Handler arhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mListData.size() > 0) {
                mInvestTypeTx.setText(mListData.get(0).getName());
                mInvestProject.setInvestType(mListData.get(0));
            } else {
            }
        }
    };

    private void selectInvestType() {
        final Dialog dialog = new Dialog(mContext, R.style.ListDialog);
        dialog.setContentView(R.layout.activity_invest_type_manager);
        ListView listView = (ListView) dialog
                .findViewById(R.id.it_list);
        listView.setAdapter(new TypeListAdapter(mContext, mListData));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Logger.d(TAG, "position = " + position);
                mInvestProject.setInvestType(mListData.get(position));
                mInvestTypeTx.setText(mListData.get(position).getName());
                dialog.dismiss();
            }
        });
        WindowManager.LayoutParams lay = dialog.getWindow().getAttributes();
        CommonDialogUtils.setParams_fullScreen((Activity) mContext, lay);
        dialog.show();
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
            mInvestProject.setDateTime(date);
            mInvestDateTx.setText(TimeUtils.getTimeByDate(date));
        }
    };

    private void addInvestProject() {
        String projectName = mNameEd.getText().toString();
        String projectAmount = mAmountEd.getText().toString();

        if (projectName.equals("") || projectAmount.equals("")) {
            Toast.makeText(mContext, getString(R.string.pls_input_all_info), Toast.LENGTH_SHORT).show();
            return;
        }

        mInvestProject.setProjectName(projectName);
        mInvestProject.setAmount(Float.parseFloat(projectAmount));
        mInvestProject.setTotalAmount(Float.parseFloat(projectAmount));
        mInvestProject.save();

        InvestDetail investDetail = new InvestDetail();
        investDetail.setInvestProject(mInvestProject);
        investDetail.setDateTime(mInvestProject.getDateTime());
        investDetail.setNowAmount(mInvestProject.getAmount());
        investDetail.setIncrease(0);
        investDetail.save();

        Toast.makeText(mContext, getString(R.string.add_success), Toast.LENGTH_SHORT).show();
        finish();
    }

}
