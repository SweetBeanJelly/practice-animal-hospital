package com.jyk.administrator.animal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class Admin_reservation_result_post extends Activity {

    EditText etid, etyear, etcontent;
    Spinner etsub;
    Button btnI, btnE, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 진료결과입력
        setTitle("진료결과입력");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_reservation_result_post);

        etid = (EditText)findViewById(R.id.editId);
        etyear = (EditText)findViewById(R.id.editYear);
        etsub = (Spinner)findViewById(R.id.editSubject);
        etcontent = (EditText)findViewById(R.id.editC);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.re, android.R.layout.simple_spinner_dropdown_item);
        etsub.setAdapter(adapter);

        btnI = (Button)findViewById(R.id.btnY);
        btnI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbInsertResult();
                popUp(v);
            }
        });

        btnE = (Button)findViewById(R.id.btnC);
        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnClear = (Button)findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etid.setText("");
                etyear.setText("");
                etcontent.setText("");
            }
        });
    }

    public void dbInsertResult() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String id = etid.getText().toString();
                    String year = etyear.getText().toString();
                    String content = etcontent.getText().toString();
                    String subject = etsub.getSelectedItem().toString();

                    URL url = new URL("http://jcpdit.dothome.co.kr/dbconn/insertResult.php/");
                    HttpURLConnection http;
                    http = (HttpURLConnection) url.openConnection();
                    http.setDoOutput(true);

                    StringBuffer buffer = new StringBuffer();
                    buffer.append("id").append("=").append(id).append("&");
                    buffer.append("year").append("=").append(year).append("&");
                    buffer.append("content").append("=").append(content).append("&");
                    buffer.append("subject").append("=").append(subject);

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
        builder.setMessage("입력 완료 되었습니다.");
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
