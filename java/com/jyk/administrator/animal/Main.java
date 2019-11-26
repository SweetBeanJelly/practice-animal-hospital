package com.jyk.administrator.animal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main extends Activity {

    Button btnMember;
    Button btnR, btnS, btnV, btnJ, btnA;

    BackPressClose close = new BackPressClose(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 메인화면
        setTitle("동물병원");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnMember = (Button)findViewById(R.id.btnMLogin);
            btnMember.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    Intent login = new Intent(getApplicationContext(), Login.class);
                    startActivity(login);
                }
            });

        btnR = (Button)findViewById(R.id.btnChoice);
            btnR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popUp(v);
                }
            });
        btnS = (Button)findViewById(R.id.btnSubject);
            btnS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent subject = new Intent(getApplicationContext(), Reservation_subject.class);
                    startActivity(subject);
                }
            });
        btnV = (Button)findViewById(R.id.btnVideo);
            btnV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent video = new Intent(getApplicationContext(), Reservation_video.class);
                    startActivity(video);
                }
            });
        btnJ = (Button)findViewById(R.id.btnJoin);
            btnJ.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent view = new Intent(getApplicationContext(), Join.class);
                    startActivity(view);
                }
            });
        btnA = (Button)findViewById(R.id.btnAsk);
            btnA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ask = new Intent(getApplicationContext(), Ask.class);
                    startActivity(ask);
                }
            });
    }

    public void popUp(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage("로그인 후에 볼 수 있습니다.");
        builder.setNegativeButton("로그인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Intent r_choice = new Intent(getApplicationContext(), Login.class);
                startActivity(r_choice);
            }
        });
        builder.setNeutralButton("회원가입", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent view = new Intent(getApplicationContext(), Join.class);
                startActivity(view);
            }
        });
        builder.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
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
