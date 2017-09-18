package com.fenguo.library.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.fenguo.library.R;
import com.fenguo.library.activity.base.BaseActivity;
import com.fenguo.library.util.StringUtil;

/**
 * 打开webview的activity
 *
 * @author Lee
 * @createDate 2015年5月26日
 */
public class WebViewActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnTouchListener {

    private WebView mWebview;
    private Toolbar mToolbar;
    private RadioGroup mFontContainerRb;
    private WebSettings mSettings;
    private ProgressBar mProgressBar;


    private String url, content, title, protocolUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        if (savedInstanceState == null) {
            String dataString = getIntent().getDataString();
            if (!StringUtil.isEmpty(dataString)) {
                protocolUrl = dataString.replace("myscheme://", "http://");
            }
        }
        init();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_webview, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (mSettings == null) {
            mSettings = mWebview.getSettings();
        }
        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.action_refresh) {
            initWebView();
            return true;
        }  else if (id == R.id.action_browser) {
            if (TextUtils.isEmpty(url)) {
                Toast.makeText(this, "该页面无法在浏览器打开", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    private void init() {
        mWebview = (WebView) findViewById(R.id.web_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mFontContainerRb = (RadioGroup) findViewById(R.id.font_container);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        setToolbar(mToolbar);
        Bundle bundle = getIntent().getExtras();
        url = bundle.getString("url");
        content = bundle.getString("content");
        title = bundle.getString("title");
        boolean ispass = false;
        if (!StringUtil.isEmpty(url) || !StringUtil.isEmpty(content)) {
            ispass = true;
        }
        if (!StringUtil.isEmpty(protocolUrl)) {
            title = "使用协议";
            url = protocolUrl;
            ispass = true;
        }
        if (ispass) {
            initWebView();
        } else {
            Toast.makeText(this, "数据有误", Toast.LENGTH_SHORT).show();
        }
        getSupportActionBar().setTitle(title);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mFontContainerRb.setOnCheckedChangeListener(this);
        mWebview.setOnTouchListener(this);

    }

    private void initWebView() {
        // 网页加载
        mSettings = mWebview.getSettings();
        mSettings.setJavaScriptEnabled(true);
        mSettings.setBlockNetworkImage(false);
        mSettings.setDefaultTextEncodingName("utf-8");
//        mSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        mWebview.setWebViewClient(client);
        // 弹出对话框
        mWebview.setWebChromeClient(chromeClient);
        if (!StringUtil.isEmpty(url)) {
            mWebview.loadUrl(url);
        } else if (!StringUtil.isEmpty(content)) {
            mWebview.loadData(content, "text/html", "utf-8");
        }
    }

    private WebChromeClient chromeClient = new WebChromeClient() {


        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress > 0 && !mProgressBar.isShown()) {
                mProgressBar.setVisibility(View.VISIBLE);
            }
            mProgressBar.setProgress(newProgress);
            if (newProgress == 100) {
                mProgressBar.setVisibility(View.GONE);
            }
        }


    };

    private WebViewClient client = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            // LogUtil.i("msg", "onPageFinished----" + url);
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // LogUtil.i("msg", "onPageStarted----" + url);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // LogUtil.i("msg", "shouldOverrideUrlLoading----" + url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description,
                                    String failingUrl) {
            // LogUtil.i("msg", "onReceivedError----failingUrl:" + failingUrl + "---errorCode:"
            // + errorCode + "---description:" + description);
            super.onReceivedError(view, errorCode, description, failingUrl);

        }
    };

    @Override
    public void onBackPressed() {
        if (mWebview.canGoBack()) {
            mWebview.goBack();
        } else {
            finish();
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.font1) {
            mSettings.setTextZoom(50);
        } else if (checkedId == R.id.font2) {
            mSettings.setTextZoom(100);
        } else if (checkedId == R.id.font3) {
            mSettings.setTextZoom(150);
        } else if (checkedId == R.id.font4) {
            mSettings.setTextZoom(200);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int id = v.getId();
        if (id == R.id.web_view && mFontContainerRb.isShown()) {
            mFontContainerRb.setVisibility(View.GONE);
            return true;
        }
        return false;
    }
}
