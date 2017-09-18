package com.fenguo.library.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fenguo.library.R;
import com.fenguo.library.util.StringUtil;

/**
 * 编辑文本内容（右下角有剩余字数提示）
 *
 * @author Lee
 * @createDate 2015年4月7日
 */
public class EditContentActivity extends AppCompatActivity {

    private EditText mContentEt;
    private TextView mRemainder;

    private Toolbar mToolbar;

    /**
     * @Fields title : 标题
     */
    private String title = "";
    /**
     * @Fields maxLength : 汉字长度
     */
    private int maxLength = 8;
    /**
     * @Fields content : 内容
     */
    private String content = "";

    /**
     * @Fields hint : 输入框的的hint
     */
    private String hint = "请输入内容";
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_content);
        receiveDataFromPreActivity();
        initComponent();
        initListener();
    }

    protected void receiveDataFromPreActivity() {
        bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("title");
            maxLength = bundle.getInt("maxLength");
            content = bundle.getString("content");
            hint = bundle.getString("hint");
        }

    }

    protected void initComponent() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mContentEt = (EditText) findViewById(R.id.limit_content);
        mRemainder = (TextView) findViewById(R.id.remainder);
        int remainder = maxLength * 2 - StringUtil.getStringLength(content);
        remainder /= 2;
        mRemainder.setText(StringUtil.toString(remainder));
        mContentEt.setHint(hint);
        mContentEt.setText(content);
        mContentEt.setSelection(mContentEt.getEditableText().length());
    }

    protected void initListener() {
        mContentEt.addTextChangedListener(new TextWatcher() {
            int currentEnd = 0;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int len = StringUtil.getStringLength(s.toString());
                int remainder = (maxLength * 2 - len) / 2;
                mRemainder.setText(StringUtil.toString(remainder));
                currentEnd = start + count; // 取得變化的結束位置
                // LogUtil.i("msg", "start=" + start + ",before=" + before + ",count=" + count);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                while (StringUtil.getStringLength(s.toString()) > maxLength * 2) {
                    currentEnd--;
                    s.delete(currentEnd, currentEnd + 1);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
//           Activity EditContentActivity does not have a parent activity name specified. (Did you forget to add the android.support.PARENT_ACTIVITY <meta-data>  element in your manifest?)
//            NavUtils.navigateUpFromSameTask(this);
            onBackPressed();
            return true;

        } else if (id == R.id.action_save) {
            checkRequire();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    private void checkRequire() {
        String content = mContentEt.getEditableText().toString();
        if (StringUtil.isEmpty(content)) {
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtil.getStringLength(content) > maxLength * 2) {
            Toast.makeText(this, "最多" + maxLength + "个汉字，" + maxLength * 2 + "个字符",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("content", content);
        setResult(RESULT_OK, intent);
        finish();
    }

}
