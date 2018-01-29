package com.example.android.myfirstandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * Created by Jennifer on 1/29/2018.
 */

public class WebViewActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);

        webView = (WebView) findViewById(R.id.news_webview);
        webView.getSettings().setJavaScriptEnabled(true);


        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {

            String textEntered = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
            webView.loadUrl(textEntered);

        }


    }
}
