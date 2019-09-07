package cn.edu.nuc.cushion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class PushText extends AppCompatActivity {

    WebView TextWebShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_text);

        TextWebShow = findViewById(R.id.TextWebShow);
        TextWebShow.loadUrl("https://web.ruubypay.com/web/yitongxing/feed/article/?id=185958&cityCode=1401&from=singlemessage");
        //TextWebShow.loadUrl("file:///C:/Users/dell/Desktop/zigbee%E5%AE%9E%E9%AA%8C/%E5%AE%9E%E9%AA%8C%E6%8C%87%E5%AF%BC%E4%B9%A6/%E7%89%A9%E8%81%94%E7%BD%91%E5%A4%9A%E5%8A%9F%E8%83%BD%E4%B8%80%E4%BD%93%E5%8C%96%E6%95%99%E5%AD%A6%E7%A7%91%E7%A0%94%E5%B9%B3%E5%8F%B0%E5%AE%9E%E9%AA%8C%E6%8C%87%E5%AF%BC%E4%B9%A6.pdf");
        TextWebShow.setWebViewClient(new WebViewClient());
        WebSettings webSettings = TextWebShow.getSettings();
        //允许javascript
        webSettings.setJavaScriptEnabled(true);
        //TextWebShow.loadUrl("http://cn.bing.com");


    }
}
