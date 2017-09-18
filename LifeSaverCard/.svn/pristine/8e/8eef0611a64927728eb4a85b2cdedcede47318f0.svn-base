package com.fenguo.library.crash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fenguo.library.R;
import com.fenguo.library.util.SdCardUtil;

import java.io.File;

/**
 * @Description 发送错误消息，暂时未用
 * @auth Lee
 * @date 2015/6/16 14:40
 */
public class SendErrorActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mOKBtn;
    private Button mCancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_error);
        mOKBtn = (Button) findViewById(R.id.ok);
        mCancelBtn = (Button) findViewById(R.id.cancel);

        mOKBtn.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ok) {
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
            if (SdCardUtil.checkSdState()) {
                String filename = SdCardUtil.getExternalFilesDir(this, "") + "crash/crash.log";
                File file = new File(filename);
                file.delete();
            }

        } else if (id == R.id.cancel) {
            Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
