package com.jyk.administrator.animal;

import android.app.Activity;
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

public class Admin_member_select extends Activity {

    Button btnC;

    ListView list;
    String resultData;

    String m_name[] = new String[10];
    String m_id[] = new String[10];
    String m_year[] = new String[10];
    String m_tel[] = new String[10];
    String m_email[] = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 회원정보조회화면
        setTitle("회원정보조회");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_member_select);

        btnC = (Button)findViewById(R.id.btnC);
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dbMemberSelect();

        list = (ListView)findViewById(R.id.listView);
        final member_select adapter = new member_select(Admin_member_select.this);
        list.setAdapter(adapter);
        // 리스트뷰 갱신
        adapter.notifyDataSetInvalidated();

    }

    class member_select extends ArrayAdapter<String> {
        private final Activity activity;

        public member_select(Activity context) {
            super(context, R.layout.admin_member_select_item,m_id);
            this.activity = context;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = activity.getLayoutInflater();

            View item = inflater.inflate(R.layout.admin_member_select_item, null, true);
            TextView t_name = (TextView)item.findViewById(R.id.txtName);
            TextView t_id = (TextView)item.findViewById(R.id.txtId);
            TextView t_year = (TextView)item.findViewById(R.id.txtYear);
            TextView t_tel = (TextView)item.findViewById(R.id.txtPhone);
            TextView t_email = (TextView)item.findViewById(R.id.txtEmail);

            TextView txt1 = (TextView)item.findViewById(R.id.txt1);
            TextView txt2 = (TextView)item.findViewById(R.id.txt2);
            TextView txt3 = (TextView)item.findViewById(R.id.txt3);

            t_name.setText(m_name[position]);
            t_id.setText(m_id[position]);
            t_year.setText(m_year[position]);
            t_tel.setText(m_tel[position]);
            t_email.setText(m_email[position]);

            if (t_id.length()==0){
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

    public void dbMemberSelect() {
        new Thread() {
            @Override
            public void run() {
                try{
                    URL url = new URL("http://jcpdit.dothome.co.kr/dbconn/memberSelect.php/");
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
                        m_name[i] = result[count++];
                        m_id[i] = result[count++];
                        m_email[i] = result[count++];
                        m_tel[i] = result[count++];
                        m_year[i] = result[count++];
                    }

                }
                catch (Exception e) {
                    Log.e("Error", "", e);
                }
            }
        }.start();
    }
}
