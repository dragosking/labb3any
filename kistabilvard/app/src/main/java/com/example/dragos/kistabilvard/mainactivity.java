package com.example.dragos.kistabilvard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class mainactivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ConstraintLayout layout;
    private WebView view;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainactivity);

        String html="<iframe src=\"https://kistabilvardapp.youcanbook.me/?noframe=true&skipHeaderFooter=true\" id=\"ycbmiframekistabilvardapp\" style=\"width:100%;height:1000px;border:0px;background-color:transparent;\" frameborder=\"0\" allowtransparency=\"true\"></iframe><script>window.addEventListener && window.addEventListener(\"message\", function(event){if (event.origin === \"https://kistabilvardapp.youcanbook.me\"){document.getElementById(\"ycbmiframekistabilvardapp\").style.height = event.data + \"px\";}}, false);</script>";
        view=(WebView)findViewById(R.id.webview);
        view.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        view.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        view.loadData(html,"text/html",null);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
