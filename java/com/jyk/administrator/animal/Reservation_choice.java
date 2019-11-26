package com.jyk.administrator.animal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Reservation_choice extends Activity {

    Button btnRe, btnD, btnS, btnR, btnP, btnE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 진료선택화면
        setTitle("진료선택");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_choice);

        Intent put = getIntent();
        final String m_id = put.getStringExtra("ID");

        btnRe = (Button)findViewById(R.id.btnReservation);
            btnRe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent res = new Intent(getApplicationContext(), Reservation.class);
                    res.putExtra("ID",m_id);
                    startActivity(res);
                }
            });

        btnD = (Button)findViewById(R.id.btnDelete);
            btnD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent delete = new Intent(getApplicationContext(), Reservation_delete.class);
                    delete.putExtra("ID",m_id);
                    startActivity(delete);
                }
            });

        btnS = (Button)findViewById(R.id.btnSelect);
            btnS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent select = new Intent(getApplicationContext(), Reservation_select.class);
                    select.putExtra("ID",m_id);
                    startActivity(select);
                }
            });

        btnR = (Button)findViewById(R.id.btnResult);
            btnR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent re = new Intent(getApplicationContext(), Reservation_result.class);
                    re.putExtra("ID",m_id);
                    startActivity(re);
                }
            });

        btnP = (Button)findViewById(R.id.btnPre);
            btnP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pre = new Intent(getApplicationContext(), Prescription_confirm_list.class);
                    pre.putExtra("ID",m_id);
                    startActivity(pre);
                }
            });

        btnE = (Button)findViewById(R.id.btnE);
            btnE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
    }
}
