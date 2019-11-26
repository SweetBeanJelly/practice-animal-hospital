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

public class Admin_prescription extends Activity {

    EditText etId, etType, etMedic, etYear, etC;
    Spinner etGender,etDrug;
    Button btnY,btnC,btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 처방전작성화면
        setTitle("처방전작성");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_prescription);

        etId = (EditText)findViewById(R.id.editId);
        etType = (EditText)findViewById(R.id.editType);
        etGender = (Spinner)findViewById(R.id.editGender);
        etMedic = (EditText)findViewById(R.id.editMedic);
        etYear = (EditText)findViewById(R.id.editYear);
        etC = (EditText)findViewById(R.id.editC);
        etDrug = (Spinner)findViewById(R.id.editDrug);

        ArrayAdapter adaptermd = ArrayAdapter.createFromResource(this,R.array.medic, android.R.layout.simple_spinner_dropdown_item);
        etDrug.setAdapter(adaptermd);
        ArrayAdapter adaptergd = ArrayAdapter.createFromResource(this,R.array.gd, android.R.layout.simple_spinner_dropdown_item);
        etGender.setAdapter(adaptergd);

        btnY = (Button)findViewById(R.id.btnY);
        btnY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbInsertPrescription();
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
                etId.setText("");
                etType.setText("");
                etMedic.setText("");
                etYear.setText("");
                etC.setText("");
            }
        });
    }

    public void dbInsertPrescription() {
        new Thread() {
            @Override
            public void run() {
                try {

                    String id = etId.getText().toString();
                    String type = etType.getText().toString();
                    String gender = etGender.getSelectedItem().toString();
                    String doctor = etMedic.getText().toString();
                    String year = etYear.getText().toString();
                    String prescription_Content  = etC.getText().toString();
                    String medication  = etDrug.getSelectedItem().toString();

                    URL url = new URL("http://jcpdit.dothome.co.kr/dbconn/insertPrescription.php/");
                    HttpURLConnection http;
                    http = (HttpURLConnection) url.openConnection();
                    http.setDoOutput(true);

                    StringBuffer buffer = new StringBuffer();
                    buffer.append("id").append("=").append(id).append("&");
                    buffer.append("type").append("=").append(type).append("&");
                    buffer.append("gender").append("=").append(gender).append("&");
                    buffer.append("medic").append("=").append(doctor).append("&");
                    buffer.append("year").append("=").append(year).append("&");
                    buffer.append("content").append("=").append(prescription_Content).append("&");
                    buffer.append("drug").append("=").append(medication);

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
        builder.setMessage("처방전 작성 완료 되었습니다.");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Intent pre = new Intent(getApplicationContext(), Admin.class);
                startActivity(pre);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
