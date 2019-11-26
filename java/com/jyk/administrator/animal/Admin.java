package com.jyk.administrator.animal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Admin extends Activity {

    Button btnD, btnP, btnR, btnM, btnC, btnEnd;

    BackPressClose close = new BackPressClose(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 관리자화면
        setTitle("관리자");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        btnD = (Button)findViewById(R.id.btnD);
            btnD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent delete = new Intent(getApplication(), Admin_reservation_delete.class);
                    startActivity(delete);
                }
            });

        btnP = (Button)findViewById(R.id.btnP);
            btnP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pre = new Intent(getApplication(),Admin_prescription.class);
                    startActivity(pre);
                }
            });

        btnR = (Button)findViewById(R.id.btnR);
            btnR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent re = new Intent(getApplication(),Admin_reservation_result.class);
                    startActivity(re);
                }
            });

        btnM = (Button)findViewById(R.id.btnM);
            btnM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mSelect = new Intent(getApplication(),Admin_member_select.class);
                    startActivity(mSelect);
                }
            });

        btnC = (Button)findViewById(R.id.btnC);
            btnC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mCer = new Intent(getApplication(),Admin_medical_certificate.class);
                    startActivity(mCer);
                }
            });

        btnEnd = (Button)findViewById(R.id.btnEnd);
            btnEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popUp(v);
                }
            });

    }

    @Override
    public void onBackPressed() {
        close.onBackPressed();
    }

    public void popUp(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage("로그아웃 되었습니다.");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Intent logout = new Intent(getApplication(),Main.class);
                startActivity(logout);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
