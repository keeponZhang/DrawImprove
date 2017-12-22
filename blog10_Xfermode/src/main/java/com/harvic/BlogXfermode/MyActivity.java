package com.harvic.BlogXfermode;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MyActivity extends Activity implements View.OnClickListener{
    /**
     * Called when the activity is first created.
     */
    private demo_3_1_view mView1;
    private demo_google_view mView2;
    private demo_fixed_views mView3;
    @Override
    public void onCreate(Bundle savedInstanceState) {;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);


        mView1 = (demo_3_1_view)findViewById(R.id.view_1);
        mView2 = (demo_google_view)findViewById(R.id.view_2);
        mView3 = (demo_fixed_views)findViewById(R.id.view_3);
    }

    @Override
    public void onClick(View v) {
        hideAllViews();

        if (v.getId() == R.id.btn_1){
            mView1.setVisibility(View.VISIBLE);
        }else if (v.getId() == R.id.btn_2){
            mView2.setVisibility(View.VISIBLE);
        }else if (v.getId() == R.id.btn_3){
            mView3.setVisibility(View.VISIBLE);
        }
    }
    private void hideAllViews(){
        mView1.setVisibility(View.GONE);
        mView2.setVisibility(View.GONE);
        mView3.setVisibility(View.GONE);
    }
}
