package com.example.try_canvas_blog;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        FrameLayout root = (FrameLayout)findViewById(R.id.root);
        root.addView(new MyRegionView(MainActivity.this));
    }

}
