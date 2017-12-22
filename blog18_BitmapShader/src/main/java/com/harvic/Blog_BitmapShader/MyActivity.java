package com.harvic.Blog_BitmapShader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MyActivity extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if (v.getId() == R.id.btn1){
            intent.setClass(MyActivity.this,SecondActivity.class);
        }else if (v.getId() == R.id.btn2){
            intent.setClass(MyActivity.this,ThirdActivity.class);
        }else if (v.getId() == R.id.btn3){
            intent.setClass(MyActivity.this,ForthActivity.class);
        }
        startActivity(intent);
    }
}
