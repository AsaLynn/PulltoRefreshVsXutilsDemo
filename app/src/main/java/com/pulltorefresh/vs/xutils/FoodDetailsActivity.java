package com.pulltorefresh.vs.xutils;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

public class FoodDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    protected SimpleDraweeView foodDetailsSdv;
    protected Toolbar toolbar;
    protected TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_food_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra(UC.TITLE));
        toolbar.setLogo(R.mipmap.ic_launcher_round);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitle(getIntent().getStringExtra(UC.ID));
        toolbar.setSubtitleTextColor(Color.RED);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher_round);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initView();
        foodDetailsSdv.setImageURI(Uri.parse(getIntent().getStringExtra(UC.URL_IMG)));
        tvContent.setText(getIntent().getStringExtra(UC.FOOD_STR));
    }

    private void initView() {
        foodDetailsSdv = (SimpleDraweeView) findViewById(R.id.food_details_sdv);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvContent = (TextView) findViewById(R.id.tv_content);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_content) {

        }
    }
}
