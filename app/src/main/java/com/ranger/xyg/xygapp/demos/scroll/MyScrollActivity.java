package com.ranger.xyg.xygapp.demos.scroll;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ranger.xyg.xygapp.R;

/**
 * Created by xyg on 2017/5/26.
 */

public class MyScrollActivity extends AppCompatActivity {
    private MyViewGroup myViewGroup;
    private Button mButton;
    private Button mBackBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller_demo);
        myViewGroup = (MyViewGroup) findViewById(R.id.my_view_group);
        mButton = (Button) findViewById(R.id.btn_out_click);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewGroup.startScroll(100);
            }
        });
        mBackBtn = (Button) findViewById(R.id.btn_in_click);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewGroup.startScroll(-100);
            }
        });
    }
}
