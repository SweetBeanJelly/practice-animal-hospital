package com.jyk.administrator.animal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class Member_delete extends Activity {

    EditText etId, etPwd;
    Button btnY, btnC;

    Member memberActivity = (Member)Member.activity;
    Main_login main = (Main_login)Main_login.activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 회원탈퇴화면
        setTitle("회원탈퇴");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_delete);

        Intent put = getIntent();
        final String m_id = put.getStringExtra("ID");

        etId = (EditText)findViewById(R.id.editId);
        etPwd = (EditText)findViewById(R.id.editPwd);

        etId.setText(m_id);

        btnY = (Button)findViewById(R.id.btnY);
        btnY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClicked(v);
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

    public void dbDeleteMember() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String id = etId.getText().toString();
                    String password = etPwd.getText().toString();

                    URL url = new URL("http://jcpdit.dothome.co.kr/dbconn/deleteMember.php/");
                    HttpURLConnection http;
                    http = (HttpURLConnection) url.openConnection();
                    http.setDoOutput(true);

                    StringBuffer buffer = new StringBuffer();
                    buffer.append("id").append("=").append(id).append("&");
                    buffer.append("password").append("=").append(password);

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

    public void onButtonClicked(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage("회원탈퇴를 하시겠습니까?");
        builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbDeleteMember();
                finish();
                memberActivity.finish();
                main.finish();
                Intent intent = new Intent(getApplicationContext(), Main.class);
                startActivity(intent);
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
