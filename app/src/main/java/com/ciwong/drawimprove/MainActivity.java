package com.ciwong.drawimprove;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

	CircleProgressBar circleProgressBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	 circleProgressBar = findViewById(R.id.circle_bar);



	}

	private static final String TAG = "MainActivity";
	int i =1;
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			Log.e(TAG, "handleMessage: " );
			if(i<100){
				i++;
				circleProgressBar.updateDownloadProgress(i/100.f);
				mHandler.sendMessageDelayed(Message.obtain(), 200);
			}else if(i==100){

			}

		}
	};
	public void go(View view){
		mHandler.sendMessageDelayed(Message.obtain(), 200);
	}
}
