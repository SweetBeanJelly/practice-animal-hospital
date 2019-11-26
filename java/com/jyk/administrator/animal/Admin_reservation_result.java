package com.jyk.administrator.animal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Admin_reservation_result extends Activity {

    Button btnI, btnE;
    ListView list;
    String resultData;

    String m_num[] = new String[10];
    String m_year[] = new String[10];
    String m_subject[] = new String[10];
    String m_content[] = new String[10];
    String m_id[] = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 진료결과조회화면 (관리자)
        setTitle("진료결과");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_reservation_result);

        dbResultSelect();

        list = (ListView)findViewById(R.id.listView);
        final member_select adapter = new member_select(Admin_reservation_result.this);
        list.setAdapter(adapter);

        btnI = (Button)findViewById(R.id.btnInsert);
        btnI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), Admin_reservation_result_post.class);
                startActivity(intent);
            }
        });

        btnE = (Button)findViewById(R.id.btnC);
        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    class member_select extends ArrayAdapter<String> {
        private final Activity activity;

        public member_select(Activity context) {
            super(context, R.layout.admin_reservation_result_item, m_year);
            this.activity = context;

        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = activity.getLayoutInflater();

            View item = inflater.inflate(R.layout.admin_reservation_result_item, null, true);
            TextView t_num = (TextView)item.findViewById(R.id.txtNum);
            TextView t_id = (TextView)item.findViewById(R.id.txtId);
            TextView t_year = (TextView)item.findViewById(R.id.txtYear);
            TextView t_sub = (TextView)item.findViewById(R.id.txtSubject);
            TextView t_con = (TextView)item.findViewById(R.id.txtC);

            TextView txt1 = (TextView)item.findViewById(R.id.txt1);
            TextView txt2 = (TextView)item.findViewById(R.id.txt2);
            TextView txt3 = (TextView)item.findViewById(R.id.txt3);

            t_num.setText(Integer.toString(position+1));
            t_id.setText(m_id[position]);
            t_year.setText(m_year[position]);
            t_sub.setText(m_subject[position]);
            t_con.setText(m_content[position]);

            if (t_id.length()==0){
                t_num.setText("");
                txt1.setText("");
                txt2.setText("");
                txt3.setText("");
            }
            else {
                txt1.setText("|");
                txt2.setText("|");
                txt3.setText("|");
            }

            return item;
        }
    }

    public void dbResultSelect() {
        new Thread() {
            @Override
            public void run() {
                try{
                    URL url = new URL("http://jcpdit.dothome.co.kr/dbconn/resultSelect.php/");
                    HttpURLConnection http;
                    http = (HttpURLConnection) url.openConnection();

                    http.setDefaultUseCaches(false);
                    // 서버에서 읽기 모드로 지정
                    http.setDoInput(true);
                    http.setRequestMethod("POST");
                    http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

                    // 서버에서 받기
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

                    int count = 0;

                    for (int i=0;i<=result.length;i++) {
                        m_num[i] = result[count++];
                        m_id[i] = result[count++];
                        m_year[i] = result[count++];
                        m_content[i] = result[count++];
                        m_subject[i] = result[count++];
                    }

                }
                catch (Exception e) {
                    Log.e("Error", "", e);
                }
            }
        }.start();
    }
}
