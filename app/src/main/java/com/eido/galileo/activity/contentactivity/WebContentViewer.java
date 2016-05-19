package com.eido.galileo.activity.contentactivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.eido.galileo.R;
import com.eido.galileo.utility.Utility;

import java.io.IOException;
import java.io.InputStream;

public class WebContentViewer extends ViewerActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_content_view);
        Bundle b = getIntent().getExtras();
        String htmlFilename = b.getString("resource");

        WebView htmlWebView = (WebView)findViewById(R.id.webView);
        htmlWebView.setWebChromeClient(new WebChromeClient());
        WebSettings webSetting = htmlWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(true);

        AssetManager mgr = getBaseContext().getAssets();

        try {
            InputStream in = mgr.open(htmlFilename, AssetManager.ACCESS_STREAMING);
            String htmlContentInStringFormat = Utility.StreamToString(in);
            in.close();
            htmlWebView.loadDataWithBaseURL(null, htmlContentInStringFormat, "text/html", "utf-8", null);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
