package com.jyk.administrator.animal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Member_update extends Activity {

    EditText etId, etName, etPwd, etEmail, etPhone, etYear;
    CheckBox cboP;
    Button btnY, btnC, btnClear;

    Handler handler = new Handler();

    String resultData;
    String st_Id;
    String txtN,txtE,txtP,txtB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 회원정보수정화면
        setTitle("정보수정");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_update);

        Intent put = getIntent();
        final String m_id = put.getStringExtra("ID");
        st_Id = m_id;

        etName = (EditText)findViewById(R.id.editName);
        etId = (EditText)findViewById(R.id.editId);
        etPwd = (EditText)findViewById(R.id.editPwd);
        etEmail = (EditText)findViewById(R.id.editEmail);
        etPhone = (EditText)findViewById(R.id.editPhone);
        etYear = (EditText)findViewById(R.id.editYear);
        cboP = (CheckBox)findViewById(R.id.cboPass);

        etId.setText(m_id);

        dbSelectThisMember();

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

        btnClear = (Button)findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etName.setText("");
                etPwd.setText("");
                etEmail.setText("");
                etPhone.setText("");
                etYear.setText("");
            }
        });


    }

    public void dbUpdateMember() {
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

                    URL url = new URL("http://jcpdit.dothome.co.kr/dbconn/updateMember.php/");
                    HttpURLConnection http;
                    http = (HttpURLConnection) url.openConnection();
                    http.setDoOutput(true);

                    StringBuffer buffer = new StringBuffer();
                    buffer.append("name").append("=").append(name).append("&");
                    buffer.append("id").append("=").append(id).append("&");
                    buffer.append("password").append("=").append(password).append("&");
                    buffer.append("eMail").append("=").append(eMail).append("&");
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

    public void dbSelectThisMember() {
        new Thread() {
            @Override
            public void run() {
                try{
                    URL url = new URL("http://jcpdit.dothome.co.kr/dbconn/thisMemberSelect.php/");
                    HttpURLConnection http;
                    http = (HttpURLConnection) url.openConnection();

                    http.setDefaultUseCaches(false);
                    http.setDoInput(true);
                    http.setRequestMethod("POST");
                    http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
                    http.setDoOutput(true);

                    StringBuffer buffer = new StringBuffer();
                    buffer.append("id").append("=").append(st_Id);

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

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            txtN = result[0];
                            txtE = result[1];
                            txtP = result[2];
                            txtB = result[3];

                            etName.setText(txtN);
                            etEmail.setText(txtE);
                            etPhone.setText(txtP);
                            etYear.setText(txtB);

                            cboP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if (!isChecked) {
                                        etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                    } else {
                                        etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                    }
                                }
                            });
                        }
                    });

                }
                catch (Exception e) {
                    Log.e("Error", "", e);
                }
            }
        }.start();
    }

    public void onButtonClicked(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage("정보수정을 하시겠습니까?");
        builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbUpdateMember();
                Toast.makeText(getApplication(),"수정 완료",Toast.LENGTH_SHORT).show();
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
