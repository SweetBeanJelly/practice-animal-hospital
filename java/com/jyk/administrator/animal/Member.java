package com.jyk.administrator.animal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Member extends Activity {

    Button btnS, btnD, btnC;

    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 나의정보화면
        setTitle("나의정보");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member);

        activity = Member.this;

        Intent put = getIntent();
        final String m_id = put.getStringExtra("ID");

        btnS = (Button)findViewById(R.id.btnS);

        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update = new Intent(getApplicationContext(), Member_update.class);
                update.putExtra("ID",m_id);
                startActivity(update);
            }
        });

        btnD = (Button)findViewById(R.id.btnD);

        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent delete = new Intent(getApplicationContext(), Member_delete.class);
                delete.putExtra("ID",m_id);
                startActivity(delete);
            }
        });

        btnC = (Button)findViewById(R.id.btnC);

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
