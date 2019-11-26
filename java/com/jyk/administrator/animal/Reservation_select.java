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
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Reservation_select extends Activity {

    Button btnB;

    ListView list;
    String resultData;

    String st_id;

    String m_year[] = new String[10];
    String m_subject[] = new String[10];
    String m_content[] = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 진료예약내역조회화면
        setTitle("진료예약내역");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_select);

        Intent put = getIntent();
        final String m_id = put.getStringExtra("ID");

        st_id = m_id;

        dbReSelect();

        list = (ListView)findViewById(R.id.listView);
        final reservation_select adapter = new reservation_select(Reservation_select.this);
        list.setAdapter(adapter);

        btnB = (Button)findViewById(R.id.btnC);
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    class reservation_select extends ArrayAdapter<String> {
        private final Activity activity;

        public reservation_select(Activity context) {
            super(context, R.layout.reservation_select_item,m_subject);
            this.activity = context;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = activity.getLayoutInflater();

            View item = inflater.inflate(R.layout.reservation_select_item, null, true);
            TextView t_num = (TextView)item.findViewById(R.id.txtNum);
            TextView t_id = (TextView)item.findViewById(R.id.txtId);
            TextView t_sub = (TextView)item.findViewById(R.id.txtSubject);
            TextView t_con = (TextView)item.findViewById(R.id.txtC);
            TextView t_year = (TextView)item.findViewById(R.id.txtYear);

            TextView txt1 = (TextView)item.findViewById(R.id.txt1);
            TextView txt2 = (TextView)item.findViewById(R.id.txt2);
            TextView txt3 = (TextView)item.findViewById(R.id.txt3);

            t_num.setText(Integer.toString(position+1));
            t_id.setText(st_id);
            t_sub.setText(m_subject[position]);
            t_con.setText(m_content[position]);
            t_year.setText(m_year[position]);

            if (t_sub.length()==0){
                t_num.setText("");
                t_id.setText("");
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

    public void dbReSelect() {
        new Thread() {
            @Override
            public void run() {
                try{
                    String id = st_id;

                    URL url = new URL("http://jcpdit.dothome.co.kr/dbconn/reservationSelect.php/");
                    HttpURLConnection http;
                    http = (HttpURLConnection) url.openConnection();

                    http.setDefaultUseCaches(false);
                    http.setDoInput(true);
                    http.setRequestMethod("POST");
                    http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
                    http.setDoOutput(true);

                    StringBuffer buffer = new StringBuffer();
                    buffer.append("id").append("=").append(id);

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

                    resultData = builder.toString();
                    final String result[] = resultData.split("/");

                    int count = 0;

                    for (int i=0;i<=result.length;i++) {
                        m_year[i] = result[count++];
                        m_subject[i] = result[count++];
                        m_content[i] = result[count++];

                    }

                }
                catch (Exception e) {
                    Log.e("Error", "", e);
                }
            }
        }.start();
    }

}
