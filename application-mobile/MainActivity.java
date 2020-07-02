package com.example.econox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public static String apilink = "http://192.168.0.8/Econox/";
    public static EconoxUser econoxUser;
    private EditText nomet, motdepasseet;
    private UserLoginTask mAuthTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.nomet = findViewById(R.id.agent);
        this.motdepasseet = findViewById(R.id.motdepasse);
    }

    public void onclick(View v) {
        String nom = nomet.getText().toString();
        String motdepasse = motdepasseet.getText().toString();
        mAuthTask = new UserLoginTask(this, nom, motdepasse);
        mAuthTask.execute((Void) null);
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
        private final String mnom;
        private final String mmdp;
        private Context context;
        String result="";
        String error;
        BufferedReader bufferedReader;
        String data;
        JSONObject jsonObj;
        UserLoginTask(Context context,String nom, String mdp) {
            mnom = nom;
            mmdp = mdp;
            this.context = context;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                data = "?motdepasse=" +mmdp;
                data += "&nom=" + mnom;
                URL url = new URL(apilink +"login_pdo.php" + data);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                result = bufferedReader.readLine();
                jsonObj = new JSONObject(result.toString());
                try {
                    error = jsonObj.getString("Erreur");
                    if(error != null) return false;
                    int id = Integer.parseInt(jsonObj.getString("idTechnicien"));
                    String nom = jsonObj.getString("Nom");
                    String prenom = jsonObj.getString("Prenom");
                    MainActivity.econoxUser = new EconoxUser(id, nom, prenom);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if (success) {
                System.out.println("Ca marche !");
                Intent intent = new Intent(context, MenuActivity.class);
                startActivity(intent);
            } else {
                System.out.println("Ca marche pas !");
                Toast errorToast = Toast.makeText(context, "Une erreur est survenue", Toast.LENGTH_LONG);
                errorToast.show();
            }
        }
        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}
