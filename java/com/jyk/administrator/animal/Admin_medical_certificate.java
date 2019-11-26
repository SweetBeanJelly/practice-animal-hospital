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

public class Admin_medical_certificate extends Activity {

    EditText eId, eDate, eSick, eType, eContent;
    Spinner eGender;
    Button btnY, btnE, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 진단서작성화면
        setTitle("진단서작성");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_medical_certificate);

        eId = (EditText)findViewById(R.id.editId);
        eDate = (EditText)findViewById(R.id.editYear);
        eSick = (EditText)findViewById(R.id.editDrug);
        eType = (EditText)findViewById(R.id.editType);
        eGender = (Spinner)findViewById(R.id.editGender);
        eContent = (EditText)findViewById(R.id.editC);

        ArrayAdapter adaptergd = ArrayAdapter.createFromResource(this,R.array.gd, android.R.layout.simple_spinner_dropdown_item);
        eGender.setAdapter(adaptergd);


        btnY = (Button)findViewById(R.id.btnY);
        btnY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbInsertMc();
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
                eId.setText("");
                eDate.setText("");
                eSick.setText("");
                eType.setText("");
                eContent.setText("");
            }
        });
    }

    public void dbInsertMc() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String id = eId.getText().toString();
                    String disease = eSick.getText().toString();
                    String gender = eGender.getSelectedItem().toString();
                    String type = eType.getText().toString();
                    String year = eDate.getText().toString();
                    String content = eContent.getText().toString();

                    URL url = new URL("http://jcpdit.dothome.co.kr/dbconn/insertMedicalCertificate.php/");
                    HttpURLConnection http;
                    http = (HttpURLConnection) url.openConnection();
                    http.setDoOutput(true);

                    StringBuffer buffer = new StringBuffer();
                    buffer.append("id").append("=").append(id).append("&");
                    buffer.append("year").append("=").append(year).append("&");
                    buffer.append("disease").append("=").append(disease).append("&");
                    buffer.append("type").append("=").append(type).append("&");
                    buffer.append("gender").append("=").append(gender).append("&");
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
        builder.setMessage("작성 완료 되었습니다.");
        builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
