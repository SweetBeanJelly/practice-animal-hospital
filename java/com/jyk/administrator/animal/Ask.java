package com.jyk.administrator.animal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Ask extends Activity {

    EditText etAyear, etAC;
    Button btnY, btnC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 문의하기화면
        setTitle("문의하기");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ask);

        etAyear = (EditText)findViewById(R.id.editAskY);
        etAC = (EditText)findViewById(R.id.editAskC);

        btnY = (Button)findViewById(R.id.btnY);
            btnY.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbInsertAsk();
                    popUp();
                }
            });

        btnC = (Button)findViewById(R.id.btnC);
            btnC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
    }

    public void dbInsertAsk() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String year = etAyear.getText().toString();
                    String content = etAC.getText().toString();

                    URL url = new URL("http://jcpdit.dothome.co.kr/dbconn/insertAsk.php/");
                    HttpURLConnection http;
                    http = (HttpURLConnection) url.openConnection();
                    http.setDoOutput(true);

                    StringBuffer buffer = new StringBuffer();
                    buffer.append("year").append("=").append(year).append("&");
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

    public void popUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage("문의 완료 되었습니다.");
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
