package com.jyk.administrator.animal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends Activity {

    Button btnY, btnC;
    EditText etid, etpwd;
    String resultData;

    Handler handler = new Handler();

    BackPressClose close = new BackPressClose(this);

    // intent id
    String m_id;

    String a_id = "root";
    String a_pwd = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 로그인화면
        setTitle("로그인");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btnY = (Button)findViewById(R.id.btnY);
        btnC = (Button)findViewById(R.id.btnC);

        etid = (EditText)findViewById(R.id.editId);
        etpwd = (EditText)findViewById(R.id.editPwd);

        btnY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbSelectMember();
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent main = new Intent(getApplicationContext(), Main.class);
                startActivity(main);
            }
        });

    }

    public void dbSelectMember() {
        new Thread() {
            @Override
        public void run() {
                try {
                    final String id = etid.getText().toString();
                    final String password = etpwd.getText().toString();

                    URL url = new URL("http://jcpdit.dothome.co.kr/dbconn/selectLogin.php/");
                    HttpURLConnection http;
                    http = (HttpURLConnection) url.openConnection();

                    http.setDefaultUseCaches(false);
                    http.setDoInput(true);
                    http.setRequestMethod("POST");
                    http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

                    StringBuffer buffer = new StringBuffer();
                    buffer.append("id").append("=").append(id);

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

                    resultData = builder.toString();
                    final String result[] = resultData.split("/");

                    m_id = result[0];

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (id.equals(a_id)) {
                                if (password.equals(a_pwd)) {
                                        finish();
                                        Intent admin = new Intent(getApplicationContext(),Admin.class);
                                        startActivity(admin);
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else if (result[0].equals(id)) {
                                if (result[1].equals(password)) {
                                    popUp();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show();
                               }
                            }
                            else {
                                popUpJoin();
                            }
                        }
                    });

                }
                catch (Exception e) {
                    Log.e("Error : ","",e);
                }
            }
        }.start();
    }

    public void popUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage("로그인 되었습니다.");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Intent main = new Intent(getApplicationContext(), Main_login.class);
                main.putExtra("ID",m_id);
                startActivity(main);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void popUpJoin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage("회원가입을 해주세요.");
        builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Intent join = new Intent(getApplicationContext(), Join.class);
                startActivity(join);
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

    @Override
    public void onBackPressed() {
        close.onBackPressed();
    }
}