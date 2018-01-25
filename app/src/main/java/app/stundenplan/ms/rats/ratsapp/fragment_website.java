package app.stundenplan.ms.rats.ratsapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class fragment_website extends Fragment {

    public WebView mWebView;


    public fragment_website() {
    }


    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){



        View v=inflater.inflate(R.layout.website_fragment, container, false);
        mWebView = (WebView) v.findViewById(R.id.webView);
        mWebView.loadUrl("https://rats-ms.de");

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient());

        return v;
    }










    }



