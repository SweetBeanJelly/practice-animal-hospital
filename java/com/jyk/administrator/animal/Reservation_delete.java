package com.jyk.administrator.animal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Reservation_delete extends Activity {

    EditText etId, etYear;
    Spinner etSubject;
    Button btnI, btnB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 진료예약취소화면
        setTitle("진료예약취소");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_delete);

        Intent put = getIntent();
        final String m_id = put.getStringExtra("ID");

        etId = (EditText)findViewById(R.id.editId);
        etYear = (EditText)findViewById(R.id.editYear);
        etSubject = (Spinner)findViewById(R.id.editSubject);

        etId.setText(m_id);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.re, android.R.layout.simple_spinner_dropdown_item);
        etSubject.setAdapter(adapter);

        btnI = (Button)findViewById(R.id.btnY);
        btnI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbDeleteRe();
                popUp(v);
            }
        });

        btnB = (Button)findViewById(R.id.btnC);
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void dbDeleteRe() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String id = etId.getText().toString();
                    String year = etYear.getText().toString();

                    URL url = new URL("http://jcpdit.dothome.co.kr/dbconn/deleteReservation.php/");
                    HttpURLConnection http;
                    http = (HttpURLConnection) url.openConnection();
                    http.setDoOutput(true);

                    StringBuffer buffer = new StringBuffer();
                    buffer.append("id").append("=").append(id).append("&");
                    buffer.append("year").append("=").append(year);

                    OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(),"UTF-8");
                    outStream.write(buffer.toString());
                    outStream.flush();

                    InputStreamReader tmp = new InputStreamReader(http.getInputStream(),"UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuilder builder = new StringBuilder();
                    String str;

                    while ((str=reader.readLine()) != null) {
                        builder.append(str);
                        break;
                    }

                }
                catch (Exception e){
                    Log.e("Error ", "", e);
                }
            }
        }.start();
    }

    public void popUp(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage("예약취소를 하시겠습니까?");
        builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbDeleteRe();
                finish();
            }
        });
        builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
