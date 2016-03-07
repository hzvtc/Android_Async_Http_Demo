package com.fjq.android_async_http_demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "MainActivity";
    private ImageView imgView;
    private WebView webView;
    //图片地址
    final String imgUrl = "http://img1.imgtn.bdimg.com/it/u=4208467321,1884355786&fm=21&gp=0.jpg";
    //网页地址
    final String webUrl = "http://blog.csdn.net/wangwei_cq/article/details/9453345";
    private static AsyncHttpClient client=new AsyncHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById();
        Init();
        setListener();
    }

    /*界面的初始化工作*/
    private void Init() {

    }

    /*为控件设置事件监听*/
    private void setListener() {
        findViewById(R.id.img_btn).setOnClickListener(this);
        findViewById(R.id.web_btn).setOnClickListener(this);
    }

    /*实例化布局文件的控件*/
    private void findViewById() {
        imgView = (ImageView) findViewById(R.id.img_view);
        webView = (WebView) findViewById(R.id.web_view);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn:
                //点击获取图片资源
                imgView.setVisibility(View.VISIBLE);
                webView.setVisibility(View.GONE);
                haveImg(imgUrl);
                break;
            case R.id.web_btn:
                //点击获取网页资源
                imgView.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                haveWeb(webUrl);
                break;
        }
    }

    private void haveWeb(final String webUrl){
        client.get(webUrl, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Log.d(TAG,throwable+"");
                Toast.makeText(MainActivity.this,"对不起 读取失败 状态码为"+i,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                 webView.getSettings().setJavaScriptEnabled(true);//是否动态加载js
                  webView.getSettings().setDefaultTextEncodingName("UTF-8");//设置编码
                  //使网页自适应
                  webView.getSettings().setUseWideViewPort(true);
                webView.getSettings().setLoadWithOverviewMode(true);
                webView.loadData(s,"text/html;charset=UTF-8",null);
            }
        });
    }
    private void haveImg(String imgUrl){
        client.get(imgUrl, new BinaryHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                   //解析图片
                Bitmap b = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imgView.setImageBitmap(b);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.d(TAG,throwable+"");
                Toast.makeText(MainActivity.this,"对不起 读取失败 状态码为"+i,Toast.LENGTH_LONG).show();
            }
        });
    }
}
