package com.example.econox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void doCollecte(View View) {
        Intent intentc  = new Intent(this, CollecteActivity.class);
        startActivity(intentc);
    }

    public void doDepose(View View) {
        Intent intentd = new Intent(this, DeposeActivity.class);
        startActivity(intentd);
    }
}
