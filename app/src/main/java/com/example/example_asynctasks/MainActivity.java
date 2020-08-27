package com.example.example_asynctasks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText tempo;
    private TextView resultadoFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempo = (EditText) findViewById(R.id.in_tempo);
        button = (Button) findViewById(R.id.btn_run);
        resultadoFinal = (TextView) findViewById(R.id.tv_result);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskRunner runner = new AsyncTaskRunner();
                String tempoAguardo = tempo.getText().toString();
                runner.execute(tempoAguardo);
            }
        });
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
        private String resp;
        ProgressDialog progressDialog;
        @Override
        protected String doInBackground(String... params) {
            publishProgress("Aguardando...");
            try {
                int tempo = Integer.parseInt(params[0])*1000;

                Thread.sleep(tempo);
                resp = "Aguardado por " + params[0] + " segundos";
            } catch (InterruptedException e) {
                e.printStackTrace();
                resp = e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            resultadoFinal.setText(result);
        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this,
                    "ProgressDialog",
                    "Esperar por "+tempo.getText().toString()+ " segundos");
        }


        @Override
        protected void onProgressUpdate(String... text) {
            resultadoFinal.setText(text[0]);

        }
    }
}