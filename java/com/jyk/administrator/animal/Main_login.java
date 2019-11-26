package com.jyk.administrator.animal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main_login extends Activity {

    Button btnMember, btnM;
    Button btnR, btnS, btnV, btnA;

    BackPressClose close = new BackPressClose(this);

    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 로그인 후에 메인화면
        setTitle("동물병원");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login);

        activity = Main_login.this;

        Intent put = getIntent();
        final String m_id = put.getStringExtra("ID");

        btnMember = (Button)findViewById(R.id.btnMLogout);
            btnMember.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popUp(v);
                }
            });

        btnM = (Button)findViewById(R.id.btnMember);
            btnM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent member = new Intent(getApplicationContext(), Member.class);
                    member.putExtra("ID",m_id);
                    startActivity(member);
                }
            });

        btnR = (Button)findViewById(R.id.btnChoice);
            btnR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent choice = new Intent(getApplicationContext(), Reservation_choice.class);
                    choice.putExtra("ID",m_id);
                    startActivity(choice);
                }
            });

        btnS = (Button)findViewById(R.id.btnSubject);
            btnS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent r_subject = new Intent(getApplicationContext(), Reservation_subject.class);
                    startActivity(r_subject);
                }
            });

        btnV = (Button)findViewById(R.id.btnVideo);
            btnV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent r_video = new Intent(getApplicationContext(), Reservation_video.class);
                    startActivity(r_video);
                }
            });

        btnA = (Button)findViewById(R.id.btnAsk);
            btnA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent r_ask = new Intent(getApplicationContext(), Ask.class);
                    startActivity(r_ask);
                }
            });
    }

    public void popUp(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage("로그아웃 되었습니다.");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Intent logout = new Intent(getApplicationContext(), Main.class);
                startActivity(logout);
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
