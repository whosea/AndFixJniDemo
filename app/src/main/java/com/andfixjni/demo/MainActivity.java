package com.andfixjni.demo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    TextView tvResult;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        tvResult = (TextView) findViewById(R.id.tvResult);
        findViewById(R.id.tvCalculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cat calculate = new Cat();
                tvResult.setText(calculate.call()+"");
            }
        });

        findViewById(R.id.tvReplace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fix();
                Toast.makeText(MainActivity.this,"已修复",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fix(){
        DxManager dxManager = new DxManager(this);
        dxManager.loadDex(new File(Environment.getExternalStorageDirectory(),"out.dex"));
    }

}
