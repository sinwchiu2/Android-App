package com.example.user.kraken.template;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.user.kraken.R;
import android.support.v7.widget.Toolbar;

public class Template_2_1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_99_1);
        initToolbar();
    }

    public void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("演講辭");
    }
}