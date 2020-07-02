package com.example.econox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class CollecteActivity extends AppCompatActivity {

        private SQLiteDataBaseHelper db;
        TextView idInput, volumeInput, telInput, batterieInput;
        Button ButtonAddData;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collecte);

        db = new SQLiteDataBaseHelper(this);

        final EditText trame = findViewById(R.id.Trame);
        Button button = findViewById(R.id.buttonTrame);
        idInput = findViewById(R.id.viewId);
        volumeInput = findViewById(R.id.viewVolume);
        telInput = findViewById(R.id.viewTel);
        batterieInput = findViewById(R.id.viewBat);
        ButtonAddData = (Button) findViewById(R.id.envoyer);

        AddData();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!trame.getText().toString().isEmpty()) {
                    trame.getText().toString().length();
                    String[] replace = trame.getText().toString().split(";");
                    idInput.setText(String.valueOf(replace[0]));
                    volumeInput.setText(String.valueOf(replace[1]));
                    telInput.setText(String.valueOf(replace[2]));
                    batterieInput.setText(String.valueOf(replace[3]));
                } else {
                    trame.setError("Entrer le texte");
                }
            }
        });
    }

    public void AddData() {
        ButtonAddData.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        boolean isInserted = db.insertData(volumeInput.getText().toString(), telInput.getText().toString(), batterieInput.getText().toString());
                        if(isInserted==true){
                            Toast.makeText(CollecteActivity.this,"Data Inserted Sucessfully",Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(CollecteActivity.this,"Data Not Inserted",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void finDeJournee(View View) {
        Intent intentc  = new Intent(this, FinDeJourneeActivity.class);
        startActivity(intentc);
    }
}