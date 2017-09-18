package com.byth.lifesaver.util;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.byth.lifesaver.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * author:Lee
 * 身份证键盘
 */
public class IdKeyboardUtil {
    private Context mContext;
    private Activity mActivity;
    private KeyboardView mKeyboardView;
    private EditText mEditText;
    private Keyboard idKeyboard;

    public IdKeyboardUtil(Activity activity, Context context, EditText editText) {
        this.mActivity = activity;
        this.mContext = context;
        this.mEditText = editText;
        idKeyboard = new Keyboard(context, R.xml.id_keyboard_view);
        mKeyboardView = (KeyboardView) activity.findViewById(R.id.keyboard_view);
        mKeyboardView.setKeyboard(idKeyboard);
        mKeyboardView.setEnabled(true);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setOnKeyboardActionListener(listener);//添加焦点事件
    }

    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int i) {

        }

        @Override
        public void onRelease(int i) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCode) {
            Editable editable = mEditText.getText();
            int start = mEditText.getSelectionStart();
            if (primaryCode == Keyboard.KEYCODE_DELETE) {
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            } else {
                editable.insert(start, Character.toString((char) primaryCode));
            }
        }

        @Override
        public void onText(CharSequence charSequence) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };

    public void showKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            mKeyboardView.setVisibility(View.VISIBLE);
        }
    }

    public void hideKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            mKeyboardView.setVisibility(View.GONE);
        }
    }

    /**
     * 禁掉系统软键盘
     */
    public void hideSoftInputMethod() {
        mActivity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        String methodName = null;
        if (currentVersion >= 16) {
            // 4.2
            methodName = "setShowSoftInputOnFocus";
        } else if (currentVersion >= 14) {
            // 4.0
            methodName = "setSoftInputShownOnFocus";
        }
        if (methodName == null) {
            mEditText.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName,
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(mEditText, false);
            } catch (NoSuchMethodException e) {
                mEditText.setInputType(InputType.TYPE_NULL);
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
