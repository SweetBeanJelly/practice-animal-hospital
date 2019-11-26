package com.jyk.administrator.animal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Reservation_subject extends Activity {

    Button btnC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 진료과목조회화면
        setTitle("진료과목");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_subject);

        btnC = (Button)findViewById(R.id.btnE);
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
