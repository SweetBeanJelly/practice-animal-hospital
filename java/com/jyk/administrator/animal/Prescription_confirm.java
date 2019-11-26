package com.jyk.administrator.animal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Prescription_confirm extends Activity {

    TextView etId, etYear, etType, etC, etDoctor, etGender, etMedic;

    Button btnY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 처방전확인화면 (intent)
        setTitle("처방전확인");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescription_confirm);

        Intent put = getIntent();
        String m_id = put.getStringExtra("Id");
        String m_year = put.getStringExtra("Year");
        String m_type = put.getStringExtra("Type");
        String m_content = put.getStringExtra("Content");
        String m_doctor = put.getStringExtra("Doctor");
        String m_gender = put.getStringExtra("Gender");
        String m_medic = put.getStringExtra("Medic");

        etId = (TextView)findViewById(R.id.editId);
        etYear = (TextView)findViewById(R.id.editYear);
        etType = (TextView)findViewById(R.id.editType);
        etC = (TextView)findViewById(R.id.editC);
        etDoctor = (TextView)findViewById(R.id.editMedic);
        etGender = (TextView)findViewById(R.id.editGender);
        etMedic = (TextView)findViewById(R.id.editDrug);

        etId.setText(m_id);
        etType.setText(m_type);
        etYear.setText(m_year);
        etC.setText(m_content);
        etDoctor.setText(m_doctor);
        etGender.setText(m_gender);
        etMedic.setText(m_medic);

        btnY = (Button)findViewById(R.id.btnY);
        btnY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
