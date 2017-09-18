package com.byth.lifesaver.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.byth.lifesaver.R;
import com.fenguo.library.widgets.dialog.BaseDialog;


/**
 * 创建对话框时，需要设置对话框的标题，内容，确定按钮的文字以及取消按钮的文字。
 * 例子1：
 * TipsDialog tipsDialog = new TipsDialog(DialogDemo.this,"Permissions",
 * "This app determines your phone`s location and shares is with Google in order to serve personalized alerts to you.This allows for a better overall app experience.",
 * "ACCEPT", "DECLINE");
 * 例子2：
 * TipsDialog.makeDialog(getActivity(), "Permissions", "This app determines your phone`s location and shares is with Google in order to serve personalized alerts to you.This allows for a better overall app experience.",
 * "ACCEPT", "DECLINE", new TipsDialog.onDialogListener() {
 *
 * @Override public void onOkClick() { 处理OK的事件 }
 * @Override public void onCancelClick() {处理Cancel的事件}}).show();
 * Created by ewhale on 2015/6/15.
 */
public class TipsDialog extends BaseDialog implements View.OnClickListener {
    private TextView mOkBtn;
    private TextView mCancelBtn;
    private TextView mTitle;
    private TextView mContent;

    private onDialogListener mListener;

    public TipsDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_self_defined);
        mTitle = (TextView) findViewById(R.id.title);
        mContent = (TextView) findViewById(R.id.message);
        mOkBtn = (TextView) findViewById(R.id.ok_btn);
        mCancelBtn = (TextView) findViewById(R.id.cancel_btn);
        mOkBtn.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
        setDialogWidth(0.7);
    }

    public TipsDialog(Context context, String title, String content, String ok, String cancel) {
        this(context);
        setTitle(title);
        setContent(content);
        setOkBtnText(ok);
        setCancelBtnText(cancel);
    }

    public TipsDialog(Context context, String title, String content, String ok) {
        this(context);
        setTitle(title);
        setContent(content);
        setOkBtnText(ok);
        mCancelBtn.setVisibility(View.GONE);
    }

    /**
     * 8012待完善
     *
     * @param builder
     */
    public TipsDialog(Builder builder) {
        super(builder.context);
        makeDialog(builder.context, builder.title, builder.content, builder.OKText, builder.cancelText, builder.listener);
    }

    public static TipsDialog makeDialog(Context context, String title, String content, String ok, String cancel, onDialogListener listener) {
        TipsDialog tipsDialog = new TipsDialog(context, title, content, ok, cancel);
        tipsDialog.setDialogListener(listener);
        return tipsDialog;
    }

    public static TipsDialog makeDialog(Context context, String title, String content, String ok, onDialogListener listener) {
        TipsDialog tipsDialog = new TipsDialog(context, title, content, ok);
        tipsDialog.setDialogListener(listener);
        return tipsDialog;
    }


    public void setDialogListener(onDialogListener mListener) {
        this.mListener = mListener;
    }

    /**
     * 设置对话框中的标题文字
     *
     * @param title 设置的标题文字
     */
    public void setTitle(String title) {
        mTitle.setText(title);
        mTitle.setVisibility(View.VISIBLE);
    }

    /**
     * 设置对话框中的详细内容
     *
     * @param content 内容文字
     */
    public void setContent(String content) {
        mContent.setText(content);
        mContent.setVisibility(View.VISIBLE);
    }

    /**
     * 设置对话框中确认按钮显示的文字
     *
     * @param okBtnText
     */
    public void setOkBtnText(String okBtnText) {
        mOkBtn.setText(okBtnText);
    }

    /**
     * 设置对话框中取消按钮显示的文字
     *
     * @param cancelBtnText
     */
    public void setCancelBtnText(String cancelBtnText) {
        mCancelBtn.setText(cancelBtnText);
    }

    @Override
    public void onClick(View v) {
        if (R.id.ok_btn == v.getId()) {
            mListener.onOkClick();
            dismiss();
        } else if (R.id.cancel_btn == v.getId()) {
            mListener.onCancelClick();
            dismiss();
        }

    }

    /**
     * 0812待完善
     */
    public static class Builder {

        public Context context;
        public String OKText;
        public String cancelText;
        public String title;
        public String content;
        public onDialogListener listener;

        private onDialogListener mListener;

        public Builder(Context context) {
            this.context = context;
        }


        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setOKText(String OKText) {
            this.OKText = OKText;
            return this;
        }

        public Builder setCancleText(String cancelText) {
            this.cancelText = cancelText;
            return this;
        }

        public Builder setListener(onDialogListener listener) {
            this.listener = listener;
            return this;
        }

        public TipsDialog build() {
            return new TipsDialog(this);
        }

    }


    /**
     * 对话框中的监听确定和取消的操作的接口
     */
    public interface onDialogListener {
        void onOkClick();

        void onCancelClick();
    }
}
