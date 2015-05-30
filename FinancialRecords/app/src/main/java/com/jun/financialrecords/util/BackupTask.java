package com.jun.financialrecords.util;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.jun.financialrecords.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by jun on 2015/5/30.
 */
public class BackupTask extends AsyncTask<String, Void, Integer> {
    private String TAG = "BackupTask";
    public static final String COMMAND_BACKUP = "backupDatabase";
    public static final String COMMAND_RESTORE = "restroeDatabase";
    private Context mContext;

    public BackupTask(Context context) {
        this.mContext = context;
    }

    @Override
    protected Integer doInBackground(String... params) {
        // TODO Auto-generated method stub

        // 获得正在使用的数据库路径，我的是 sdcard 目录下的 /dlion/db_dlion.db
        // 默认路径是 /data/data/(包名)/databases/*.db
        File dbFile = mContext.getDatabasePath("/data/data/com.jun.financialrecords/databases/FinancialRecords.db");
        File exportDir = new File(Environment.getExternalStorageDirectory(),
                "FinancialRecords");
        if (!exportDir.exists()) {
            Logger.w(TAG, "mkdirs");
            exportDir.mkdirs();
        } else {
            Logger.w(TAG, "exportDir.exists()");
        }
        if (!dbFile.exists()) {
            Logger.w(TAG, "!dbFile.exists()");
        } else {
            Logger.w(TAG, "dbFile.exists()");
        }
        Logger.w(TAG, "exportDir:" + exportDir.getAbsolutePath());
        Logger.w(TAG, "dbFile:" + dbFile.getAbsolutePath());

        File backup = new File(exportDir, dbFile.getName());
        String command = params[0];
        if (command.equals(COMMAND_BACKUP)) {
            try {
                backup.createNewFile();
                fileCopy(dbFile, backup);
                Logger.w(TAG, "backup ok");
                return 1;
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                Logger.w(TAG, "backup fail");
                return 0;
            }
        } else if (command.equals(COMMAND_RESTORE)) {
            try {
                fileCopy(backup, dbFile);
                Logger.w(TAG, "restore ok");
                return 1;
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                Logger.w(TAG, "restore fail");
                return 0;
            }
        } else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer == 1) {
            Toast.makeText(mContext, mContext.getString(R.string.success), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.fail), Toast.LENGTH_SHORT).show();
        }

    }

    private void fileCopy(File dbFile, File backup) throws IOException {
        // TODO Auto-generated method stub
        FileChannel inChannel = new FileInputStream(dbFile).getChannel();
        FileChannel outChannel = new FileOutputStream(backup).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }
}