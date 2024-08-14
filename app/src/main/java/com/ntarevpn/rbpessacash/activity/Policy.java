package com.ntarevpn.rbpessacash.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import  com.ntarevpn.rbpessacash.R;
import com.ntarevpn.rbpessacash.util.AdsController;
import static com.ntarevpn.rbpessacash.ApiBaseUrl.ADMIN_URL;

public class Policy extends AppCompatActivity {

    private String url = ADMIN_URL + "/privacy_policy.php";
    private WebView webView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browser_layout_indratech);

        AdsController adsController = new AdsController(this);
        swipeRefreshLayout = findViewById(R.id.swipe);
        webView = findViewById(R.id.webVewID);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        url = adsController.getPrivacyPolicyUrl();
        LoadPage(url);
        onClick();

    }

    private void onClick() {
        swipeRefreshLayout.post(() -> {
            swipeRefreshLayout.setRefreshing(true);
            LoadPage(url);
        });

        swipeRefreshLayout.setOnRefreshListener(() -> LoadPage(url));
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void LoadPage(String Url) {
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(android.webkit.WebView view, int progress) {
                swipeRefreshLayout.setRefreshing(progress != 100);
            }
        });
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(Url);
    }

    private static class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String myUrl) {
            view.loadUrl(myUrl);
            return false;
        }
    }
}