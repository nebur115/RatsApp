package app.stundenplan.ms.rats.ratsapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


public class fragment_website extends Fragment {

    public WebView mWebView;
    public ProgressBar bar;
    public ConstraintLayout blockview;


    public fragment_website() {
    }


    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){




        View v=inflater.inflate(R.layout.website_fragment, container, false);
        bar = v.findViewById(R.id.progressBar);
        blockview = v.findViewById(R.id.blockview);

        mWebView = v.findViewById(R.id.webView);
        mWebView.loadUrl("https://rats-ms.de");


        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new myWebclient());

        return v;
    }



    public class myWebclient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            bar.setVisibility(View.GONE);
            blockview.setVisibility(View.GONE);
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            super.onPageStarted(view, url, favicon);
        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    }



