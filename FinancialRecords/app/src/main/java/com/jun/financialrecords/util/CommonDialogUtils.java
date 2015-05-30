package com.jun.financialrecords.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jun.financialrecords.R;

/**
 * 
 * @ClassName: CommonDialogUtils
 * @Description: 通用对话框
 * @author shark
 * @date 2015年2月2日 下午8:41:59
 * 
 */
public class CommonDialogUtils {

	/**
	 * 带内容与取消、确定按钮的对话框
	 * 
	 * @param context
	 * @param message
	 * @param okText
	 * @param cancelText
	 * @param listener
	 */
	public static Dialog showCommonDialog(Context context, String message,
			String okText, String cancelText,
			final CommonDialogButtonClickListener listener) {
		final Dialog dialog = new Dialog(context, R.style.TransparentDialog);
		dialog.setContentView(R.layout.common_dialog);
		TextView messageTx = (TextView) dialog
				.findViewById(R.id.common_dialog_message);
		Button okBtn = (Button) dialog.findViewById(R.id.common_dialog_ok);
		okBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				listener.okBtnClick();
			}
		});
		Button cancelBtn = (Button) dialog
				.findViewById(R.id.common_dialog_cancel);
		cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				listener.cancelBtnClick();
			}
		});
		if (message != null) {
			messageTx.setText(message);
		}
		if (okText != null) {
			okBtn.setText(okText);
		}
		if (cancelText != null) {
			cancelBtn.setText(cancelText);
		}
		dialog.show();
		return dialog;
	}

	/**
	 * 输入对话框
	 * 
	 * @param context
	 * @param title
	 * @param okText
	 * @param cancelText
	 * @param listener
	 */
	public static void showCommonInputDialog(Context context, String title,
			String okText, String cancelText,
			final CommonInputDialogListener listener, TextWatcher textWatcher) {
		final Dialog dialog = new Dialog(context, R.style.TransparentDialog);
		dialog.setContentView(R.layout.common_input_dialog);
		final EditText editText = (EditText) dialog
				.findViewById(R.id.common_dialog_edittext);
		if (textWatcher != null) {
			editText.addTextChangedListener(textWatcher);
		} else {
			editText.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
						int arg3) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					if (EmojiTextUtils.containsEmoji(s.toString())) {
						editText.setText(EmojiTextUtils.filterEmoji(s.toString()));
					}
				}
			});
		}

		TextView titleTx = (TextView) dialog
				.findViewById(R.id.common_dialog_title);
		Button okBtn = (Button) dialog.findViewById(R.id.common_dialog_ok);
		okBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				String inputText = editText.getText().toString();
				if (listener.inputCheck(inputText)) {
					dialog.dismiss();
					listener.okBtnClick(inputText);
				}
			}
		});
		Button cancelBtn = (Button) dialog
				.findViewById(R.id.common_dialog_cancel);
		cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				listener.cancelBtnClick();
			}
		});
		if (title != null) {
			titleTx.setText(title);
		}
		if (okText != null) {
			okBtn.setText(okText);
		}
		if (cancelText != null) {
			cancelBtn.setText(cancelText);
		}
		dialog.show();
	}

	public static void showDeleteDialog(Context context,
			final CommonDeleteDialogListener listener) {
		final Dialog dialog = new Dialog(context, R.style.ListDialog);
		dialog.setContentView(R.layout.common_delete_dialog);
		dialog.findViewById(R.id.common_delete_dialog_tx).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						listener.delete();
					}
				});

		dialog.show();
	}
	
	public static void setParams(Activity context, WindowManager.LayoutParams lay) {
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		Rect rect = new Rect();
		View view = context.getWindow().getDecorView();
		view.getWindowVisibleDisplayFrame(rect);
		// lay.height = dm.heightPixels - rect.top;
		lay.width = dm.widthPixels;
	}
	
	public static void setParams_fullScreen(Activity context, WindowManager.LayoutParams lay) {
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		Rect rect = new Rect();
		View view = context.getWindow().getDecorView();
		view.getWindowVisibleDisplayFrame(rect);
		lay.height = dm.heightPixels - rect.top;
		lay.width = dm.widthPixels;
	} 
	
	public interface CommonDialogButtonClickListener {

		public void okBtnClick();

		public void cancelBtnClick();

	}

	public interface CommonInputDialogListener {

		public void okBtnClick(String inputText);

		public void cancelBtnClick();

		public Boolean inputCheck(String inputText);// 条件检查
	}

	public interface CommonDeleteDialogListener {

		public void delete();

	}
}
