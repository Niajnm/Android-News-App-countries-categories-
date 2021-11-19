package com.example.myapicheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class webActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBar;
    String URl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        webView = findViewById(R.id.webView_id);
        progressBar = findViewById(R.id.webProgress_id);
        geturl();
    }

    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void geturl() {
        Intent intent = getIntent();
        URl = intent.getStringExtra("url");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(URl);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.webmenu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.WmenuShare_id) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/app");
            String subject = "Arg News:";
            String head = URl;
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, head);
            startActivity(Intent.createChooser(intent, "Share with"));
            Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.WmenuReport_id) {
            Toast.makeText(this, "Feedback", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}