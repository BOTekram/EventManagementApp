package com.fit2081.assignment1;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EventGoogleResult extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_google_result);

        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());

        // Get the event name from the intent
        String eventName = getIntent().getStringExtra("eventName");
        if (eventName != null) {
            String url = "https://www.google.com/search?q=" + eventName;
            webView.loadUrl(url);
        }
    }
}
