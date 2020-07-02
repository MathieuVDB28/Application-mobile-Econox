package com.example.econox;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class DeposeActivity extends AppCompatActivity {

    TextView txt ;
    String URLServeur ="http://192.168.0.8/Econox/insertTel.php";

    ProgressDialog pDialog ;


    EditText ed_id ;
    Button btn_exec ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depose);

        txt =(TextView)findViewById(R.id.txt);
        ed_id=(EditText)findViewById(R.id.editText) ;
        btn_exec=(Button)findViewById(R.id.button);

        btn_exec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // tester si editext est vide
                if (ed_id.getText().toString().equals("")) {

                    Toast.makeText(DeposeActivity.this,"Entre un ID SVP ",Toast.LENGTH_SHORT).show();
                }
                else



                    new  Webservice().execute(URLServeur,ed_id.getText().toString());  // ajouter 2 params le contenu de EditText
            }
        });
    }

    class Webservice  extends AsyncTask< String , Void, String > {
        @Override
        protected void onPreExecute () {

            // affichage de boite de  dialogue
            pDialog =new ProgressDialog(DeposeActivity.this) ;
            pDialog.setMessage("Connexion au serveur .......... " );
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            StringBuilder resultat =new StringBuilder();


            try {

                HttpURLConnection conx ;

                URL url =new URL (strings[0]) ;
                String sID=strings[1] ;




                conx=(HttpURLConnection) url.openConnection();


                conx.setDoInput(true);
                conx.setRequestMethod("POST");
                conx.setRequestProperty("Accept-Charset","UTF-8");
                conx.setConnectTimeout(1000);

                Uri.Builder builder =new Uri.Builder().appendQueryParameter("tel",sID) ;

                String requete =builder.build().getEncodedQuery();

                OutputStream os = conx.getOutputStream() ;
                BufferedWriter writer =new BufferedWriter( new OutputStreamWriter(os,"UTF-8"));
                writer.write(requete);
                writer.flush();
                writer.close();
                os.close();


                conx.connect();
                // recuperation de donnees sous forme string

                InputStream in =new BufferedInputStream(conx.getInputStream());
                BufferedReader reader =new BufferedReader( (new InputStreamReader(in)));

                String ligne ;
                while ((ligne=reader.readLine())!=null ) {
                    resultat.append(ligne) ;
                }
                conx.disconnect();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            return resultat.toString() ;
        }

        @Override
        protected  void onPostExecute (String str) {

            pDialog.dismiss(); //fermeture de boite de dialogue

            txt.setText( str);

        }
    }
}
