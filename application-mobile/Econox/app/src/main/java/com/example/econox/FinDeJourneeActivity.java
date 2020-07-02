package com.example.econox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.database.Cursor;
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

public class FinDeJourneeActivity extends AppCompatActivity {

        SQLiteDataBaseHelper db;
        Button buttonViewData;

        TextView txt ;
        String URLServeur ="http://192.168.0.8/Econox/insertCollecte.php";

        ProgressDialog pDialog ;

        EditText ed_id ;
        EditText ed_volume ;
        EditText ed_tel ;
        EditText ed_batterie ;
        Button btn_exec ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_de_journee);

        db = new SQLiteDataBaseHelper(this);

        buttonViewData = findViewById(R.id.affiche_btn);
        ViewAll();

        txt =(TextView)findViewById(R.id.txtverif);
        ed_id=(EditText)findViewById(R.id.editid) ;
        ed_volume=(EditText)findViewById(R.id.editvolume) ;
        ed_tel=(EditText)findViewById(R.id.edittel) ;
        ed_batterie=(EditText)findViewById(R.id.editbat) ;
        btn_exec=(Button)findViewById(R.id.btn_envoyer);

        btn_exec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // tester si editext est vide
                if (ed_id.getText().toString().equals("")) {

                Toast.makeText(FinDeJourneeActivity.this,"Entrez une valeur SVP ",Toast.LENGTH_SHORT).show();
            }
                else



                        new Webservice().execute(URLServeur,ed_id.getText().toString());  // ajouter 2 params le contenu de EditText

                if (ed_volume.getText().toString().equals("")) {

                    Toast.makeText(FinDeJourneeActivity.this,"Entrez une valeur SVP ",Toast.LENGTH_SHORT).show();
                }
                else



                    new Webservice().execute(URLServeur,ed_volume.getText().toString());  // ajouter 2 params le contenu de EditText

                if (ed_tel.getText().toString().equals("")) {

                    Toast.makeText(FinDeJourneeActivity.this,"Entrez une valeur SVP ",Toast.LENGTH_SHORT).show();
                }
                else



                    new Webservice().execute(URLServeur,ed_tel.getText().toString());  // ajouter 2 params le contenu de EditText

                if (ed_batterie.getText().toString().equals("")) {

                    Toast.makeText(FinDeJourneeActivity.this,"Entrez une valeur SVP ",Toast.LENGTH_SHORT).show();
                }
                else



                    new Webservice().execute(URLServeur,ed_batterie.getText().toString());  // ajouter 2 params le contenu de EditText
            }
        });
    }

    class Webservice  extends AsyncTask< String , Void, String > {
        @Override
        protected void onPreExecute () {

            // affichage de boite de  dialogue
            pDialog =new ProgressDialog(FinDeJourneeActivity.this) ;
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
                String sID=strings[1];
                String sVol=strings[2];
                String sTel=strings[3];
                String sBat=strings[4];

                conx=(HttpURLConnection) url.openConnection();


                conx.setDoInput(true);
                conx.setRequestMethod("POST");
                conx.setRequestProperty("Accept-Charset","UTF-8");
                conx.setConnectTimeout(1000);

                //Uri.Builder builder =new Uri.Builder();

                //builder.appendQueryParameter("id",sID);
                //builder.appendQueryParameter("volume",sVol);
               // builder.appendQueryParameter("tel",sTel);
               // builder.appendQueryParameter("batterie",sBat);

               // String requete =builder.build().getEncodedQuery();

                String requete="id="+sID+"&volume="+sVol+"&tel="+sTel+"&batterie"+sBat ;

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

    private void ViewAll() {
        buttonViewData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor data = db.getAllData();
                        if(data.getCount()==0){
                            showMessage("Error", "No Data Found !");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while(data.moveToNext()){
                            buffer.append("Id :" +data.getString(0)+"\n ");
                            buffer.append("Volume :" +data.getString(1)+"\n ");
                            buffer.append("Téléphone :" +data.getString(2)+"\n ");
                            buffer.append("Batterie :" +data.getString(3)+"\n ");
                        }
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
