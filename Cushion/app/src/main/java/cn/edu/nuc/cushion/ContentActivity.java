package cn.edu.nuc.cushion;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;
import cn.edu.nuc.cushion.bean.News;

/**
 * Created by Yangyulin on 2019/6/24.
 */
public class ContentActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        Intent intent = getIntent();
        News news = (News) intent.getSerializableExtra("news");

        HtmlTextView htmlTextView = findViewById(R.id.html_text);
        String body = news.getBody();
        htmlTextView.setHtml(body, new HtmlResImageGetter(htmlTextView));

    }
}
