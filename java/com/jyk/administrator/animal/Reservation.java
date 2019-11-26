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

public class Reservation extends Activity {

    EditText etId, etType, etYear, etC;

    Spinner etGender, etSubject;

    Button btnY, btnC, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 진료예약화면
        setTitle("진료예약");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation);

        Intent put = getIntent();
        final String m_id = put.getStringExtra("ID");

        etId = (EditText)findViewById(R.id.editId);
        etType = (EditText)findViewById(R.id.editType);
        etGender = (Spinner)findViewById(R.id.editGender);
        etYear = (EditText)findViewById(R.id.editYear);
        etC = (EditText)findViewById(R.id.editC);
        etSubject = (Spinner)findViewById(R.id.editSubject);

        etId.setText(m_id);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.re, android.R.layout.simple_spinner_dropdown_item);
        etSubject.setAdapter(adapter);
        ArrayAdapter adaptergd = ArrayAdapter.createFromResource(this,R.array.gd, android.R.layout.simple_spinner_dropdown_item);
        etGender.setAdapter(adaptergd);

        btnY = (Button)findViewById(R.id.btnY);
        btnY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbInsertRe();
                popUp(v);
            }
        });

        btnC = (Button)findViewById(R.id.btnC);
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnClear = (Button)findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etType.setText("");
                etYear.setText("");
                etC.setText("");
            }
        });
    }

    public void dbInsertRe() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String id = etId.getText().toString();
                    String type = etType.getText().toString();
                    String gender = etGender.getSelectedItem().toString();
                    String year = etYear.getText().toString();
                    String hope = etSubject.getSelectedItem().toString();
                    String content  = etC.getText().toString();

                    URL url = new URL("http://jcpdit.dothome.co.kr/dbconn/insertReservation.php/");
                    HttpURLConnection http;
                    http = (HttpURLConnection) url.openConnection();
                    http.setDoOutput(true);

                    StringBuffer buffer = new StringBuffer();
                    buffer.append("id").append("=").append(id).append("&");
                    buffer.append("type").append("=").append(type).append("&");
                    buffer.append("gender").append("=").append(gender).append("&");
                    buffer.append("year").append("=").append(year).append("&");
                    buffer.append("subject").append("=").append(hope).append("&");
                    buffer.append("content").append("=").append(content);

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
        builder.setMessage("예약 완료 되었습니다.");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
