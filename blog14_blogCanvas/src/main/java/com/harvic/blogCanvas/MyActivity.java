package com.harvic.blogCanvas;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MyActivity extends Activity implements View.OnClickListener{
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.bitmapcanvasview_btn).setOnClickListener(this);
        findViewById(R.id.xfermodeview_btn).setOnClickListener(this);
        findViewById(R.id.matrix_save_flag_btn).setOnClickListener(this);
        findViewById(R.id.savelayeruseexample_3_1_btn).setOnClickListener(this);
        findViewById(R.id.clip_save_flag_view_btn).setOnClickListener(this);
        findViewById(R.id.alpha_color_falg_view_btn).setOnClickListener(this);
        findViewById(R.id.clip_to_layer_save_flag_view_btn).setOnClickListener(this);
        findViewById(R.id.savelayeralphaview_btn).setOnClickListener(this);
        findViewById(R.id.restoretocountview_btn).setOnClickListener(this);

    }

    private void hideAllViews(){
        findViewById(R.id.bitmapcanvasview).setVisibility(View.GONE);
        findViewById(R.id.xfermodeview).setVisibility(View.GONE);
        findViewById(R.id.matrix_save_flag_view).setVisibility(View.GONE);
        findViewById(R.id.savelayeruseexample_3_1).setVisibility(View.GONE);
        findViewById(R.id.clip_save_flag_view).setVisibility(View.GONE);
        findViewById(R.id.alpha_color_falg_view).setVisibility(View.GONE);
        findViewById(R.id.clip_to_layer_save_flag_view).setVisibility(View.GONE);
        findViewById(R.id.savelayeralphaview).setVisibility(View.GONE);
        findViewById(R.id.restoretocountview).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        hideAllViews();
        switch (v.getId()){
            case R.id.bitmapcanvasview_btn:
                findViewById(R.id.bitmapcanvasview).setVisibility(View.VISIBLE);
                break;
            case R.id.xfermodeview_btn:
                findViewById(R.id.xfermodeview).setVisibility(View.VISIBLE);
                break;
            case R.id.matrix_save_flag_btn:
                findViewById(R.id.matrix_save_flag_view).setVisibility(View.VISIBLE);
                break;
            case R.id.savelayeruseexample_3_1_btn:
                findViewById(R.id.savelayeruseexample_3_1).setVisibility(View.VISIBLE);
                break;
            case R.id.clip_save_flag_view_btn:
                findViewById(R.id.clip_save_flag_view).setVisibility(View.VISIBLE);
                break;
            case R.id.alpha_color_falg_view_btn:
                findViewById(R.id.alpha_color_falg_view).setVisibility(View.VISIBLE);
                break;
            case R.id.clip_to_layer_save_flag_view_btn:
                findViewById(R.id.clip_to_layer_save_flag_view).setVisibility(View.VISIBLE);
                break;
            case R.id.savelayeralphaview_btn:
                findViewById(R.id.savelayeralphaview).setVisibility(View.VISIBLE);
                break;
            case R.id.restoretocountview_btn:
                findViewById(R.id.restoretocountview).setVisibility(View.VISIBLE);
                break;
        }

    }
}
