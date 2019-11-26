package com.jyk.administrator.animal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class Reservation_video extends Activity {

    WebView webView;
    Button btnL, btnE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 진료영상재생화면
        setTitle("진료영상");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_video);

        webView = (WebView)findViewById(R.id.webView);
        btnL = (Button)findViewById(R.id.btnL);
        btnE = (Button)findViewById(R.id.btnE);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebClient());

        btnL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClicked(v);
            }
        });

        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    class WebClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            webView.loadUrl(url);
            return  true;
        }
    }

    public void onButtonClicked(View v) {
        webView.loadUrl("https://www.google.co.kr/search?q=동물병원+진료+영상&biw=360&bih=559&prmd=ivn&source=lnms&tbm=vid&sa=X&ved=0ahUKEwjY0oqw_f7MAhWBN5QKHateCpYQ_AUIBygC");

    }
}
