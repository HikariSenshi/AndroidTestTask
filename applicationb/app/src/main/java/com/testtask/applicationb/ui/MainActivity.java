package com.testtask.applicationb.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.testtask.applicationb.R;

import static com.testtask.applicationb.utils.Consts.IMAGE_STATUS;
import static com.testtask.applicationb.utils.Consts.IMAGE_STATUS_DEFAULT;
import static com.testtask.applicationb.utils.Consts.IMAGE_URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,
                    MainFragment.getInstance(getIntent().getStringExtra(IMAGE_URL),
                    getIntent().getIntExtra(IMAGE_STATUS, IMAGE_STATUS_DEFAULT))).commit();
        }

    }
}
