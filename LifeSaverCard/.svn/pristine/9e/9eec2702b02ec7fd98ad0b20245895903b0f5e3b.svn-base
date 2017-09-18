package com.fenguo.library.widgets.dialog;


import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fenguo.library.R;

public class AlertDialog extends Dialog {

	private Button okBtn;
	private Button cancelBtn;
	private TextView contentView;
	private CallBack callBack;
	private String content;
	private String isForce;
	private TextView title;

	public AlertDialog(Context context,String content, String isForce) {
		super(context, R.style.Dialog);
		setContentView(R.layout.dialog_alert);
		this.setCanceledOnTouchOutside(false);
		this.isForce = isForce;
		this.content = content;
		initDialog();
	}

	private void initDialog() {
		okBtn = (Button) findViewById(R.id.ok_btn);
		okBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				callBack.callBack();
				dismiss();
			}
		});
		cancelBtn = (Button) findViewById(R.id.cancel_btn);
		cancelBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				callBack.cancel();
				dismiss();
			}
		});
		title = (TextView) findViewById(R.id.title);
		title.setText(isForce.equals("1") ? "更新提示" : "提示");
		cancelBtn.setVisibility(isForce.equals("1") ? View.GONE : View.VISIBLE);
		cancelBtn.setText(isForce.equals("1") ? "立即更新" : "取消");
		contentView = (TextView) findViewById(R.id.dialog_operate_content);
		contentView.setText(content);
	}

	public void setCallBack(CallBack callBack) {
		this.callBack = callBack;
	}
	
	public void setOkText(String text){
		okBtn.setText(text);
	}

	public interface CallBack {
		void callBack();
		void cancel();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK )
		{
			if(isForce.equals("1")){
				this.setCancelable( false);
			}
		}
		return super.onKeyDown(keyCode, event);

	}
}
