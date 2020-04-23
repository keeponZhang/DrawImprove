package com.harvic.BlogPaintBasic;

import android.app.Activity;
import android.os.Bundle;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // MyDashView dashView = (MyDashView) findViewById(R.id.my_dash_view);
        // dashView.startAnim();
    }
}
