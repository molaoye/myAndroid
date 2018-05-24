package com.android.web;

import com.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class WebViewTest1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.webviewtest1);
		
		final EditText editText1=(EditText) findViewById(R.id.editText1);
		
		final WebView webView1=(WebView) findViewById(R.id.webView1);
		
		Button button1=(Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				webView1.loadUrl(editText1.getText().toString());
				webView1.setWebViewClient(new WebViewClient(){
		            @Override
		            public boolean shouldOverrideUrlLoading(WebView view, String url) {
		                view.loadUrl(url);
		                return true;
		            }
		        });
//				webView1.setWebChromeClient(new WebChromeClient(){
//		            @Override
//		            public void onProgressChanged(WebView view, int newProgress) {
//		                //get the newProgress and refresh progress bar
//		            }
//		            @Override
//		            public void onReceivedTitle(WebView view, String title) {
////		                titleview.setText(title);//a textview
//		            }
//		        });
			}
		});
		
		
		
		
	}

	
	
}
