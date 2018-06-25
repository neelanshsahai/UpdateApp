package com.sahai.neelansh.updateapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.github.snowdream.android.app.UpdateFormat;
import com.github.snowdream.android.app.UpdateManager;
import com.github.snowdream.android.app.UpdateOptions;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ProgressDialog progress;
//    private final String apkUrl = "https://drive.google.com/uc?export=download&id=1vS_SeWOVac9X1K_24bDYcyXIX1OT0Zeg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InstallAPK installAPK = new InstallAPK();
        installAPK.setContext(MainActivity.this, progress);
        installAPK.execute();

/*
        button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
*/
    }
}
