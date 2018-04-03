package com.harvic.BlogMovePath;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MyActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final MyView myView = (MyView)findViewById(R.id.myview);
        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myView.reset();
            }
        });
    }
}
