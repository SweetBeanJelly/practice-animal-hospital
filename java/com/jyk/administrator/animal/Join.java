package com.jyk.administrator.animal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Join extends Activity {

    EditText etName, etId, etPwd, etEmail, etPhone, etYear;
    Button btnY, btnC;
    CheckBox cboP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 회원가입화면
        setTitle("회원가입");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        etName = (EditText)findViewById(R.id.editName);
        etId = (EditText)findViewById(R.id.editId);
        etPwd = (EditText)findViewById(R.id.editPwd);
        etEmail = (EditText)findViewById(R.id.editEmail);
        etPhone = (EditText)findViewById(R.id.editPhone);
        etYear = (EditText)findViewById(R.id.editYear);
        cboP = (CheckBox)findViewById(R.id.cboPass);

        btnY = (Button)findViewById(R.id.btnY);
            btnY.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        dbInsertJoin();
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

        cboP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else {
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

    }

    public void dbInsertJoin() {
        new Thread() {
            @Override
        public void run() {
                try {
                    String name = etName.getText().toString();
                    String id = etId.getText().toString();
                    String eMail = etEmail.getText().toString();
                    String password = etPwd.getText().toString();
                    String phoneNum = etPhone.getText().toString();
                    String birthDate = etYear.getText().toString();

                    URL url = new URL("http://jcpdit.dothome.co.kr/dbconn/insertJoin.php/");
                    HttpURLConnection http;
                    http = (HttpURLConnection) url.openConnection();
                    http.setDoOutput(true);

                    StringBuffer buffer = new StringBuffer();
                    buffer.append("name").append("=").append(name).append("&");
                    buffer.append("id").append("=").append(id).append("&");
                    buffer.append("eMail").append("=").append(eMail).append("&");
                    buffer.append("password").append("=").append(password).append("&");
                    buffer.append("phoneNum").append("=").append(phoneNum).append("&");
                    buffer.append("birthDate").append("=").append(birthDate);

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
        builder.setMessage("회원가입 완료 되었습니다.");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
