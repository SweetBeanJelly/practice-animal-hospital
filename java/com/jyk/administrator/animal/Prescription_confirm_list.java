package com.jyk.administrator.animal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Prescription_confirm_list extends Activity {

    Button btnE;
    ListView list;

    String m_type[] = new String[20];
    String m_gender[] = new String[20];
    String m_doctor[] = new String[20];
    String m_year[] = new String[20];
    String m_content[] = new String[20];
    String m_medic[] = new String[20];

    String resultData;

    String st_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 처방전확인화면
        setTitle("처방전확인");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescription_confirm_list);

        Intent put = getIntent();
        final String m_id = put.getStringExtra("ID");

        st_id = m_id;

        dbPreSelect();

        list = (ListView)findViewById(R.id.listView);
        final prescription_select adapter = new prescription_select(Prescription_confirm_list.this);
        list.setAdapter(adapter);
        // 리스트뷰 갱신
        adapter.notifyDataSetInvalidated();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),Prescription_confirm.class);
                intent.putExtra("Id",m_id);
                intent.putExtra("Type",m_type[position]);
                intent.putExtra("Gender",m_gender[position]);
                intent.putExtra("Doctor",m_doctor[position]);
                intent.putExtra("Year",m_year[position]);
                intent.putExtra("Content",m_content[position]);
                intent.putExtra("Medic",m_medic[position]);
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

    class prescription_select extends ArrayAdapter<String> {
        private final Activity activity;

        public prescription_select(Activity context) {
            super(context, R.layout.prescription_confirm_item,m_year);
            this.activity = context;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = activity.getLayoutInflater();

            View item = inflater.inflate(R.layout.prescription_confirm_item, null, true);
            TextView t_num = (TextView)item.findViewById(R.id.txtNum);
            TextView t_id = (TextView)item.findViewById(R.id.txtId);
            TextView t_year = (TextView)item.findViewById(R.id.txtYear);

            t_num.setText(Integer.toString(position+1));
            t_id.setText(st_id);
            t_year.setText(m_year[position]);

            TextView txt1 = (TextView)item.findViewById(R.id.txt1);
            TextView txt2 = (TextView)item.findViewById(R.id.txt2);

            if (t_year.length()==0){
                t_num.setText("");
                t_id.setText("");
                txt1.setText("");
                txt2.setText("");
            }
            else {
                txt1.setText("|");
                txt2.setText("|");
            }

            return item;
        }
    }

    public void dbPreSelect() {
        new Thread() {
            @Override
            public void run() {
                try{
                    String id = st_id;

                    URL url = new URL("http://jcpdit.dothome.co.kr/dbconn/prescriptionSelect.php/");
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
                        m_gender[i] = result[count++];
                        m_type[i] = result[count++];
                        m_doctor[i] = result[count++];
                        m_medic[i] = result[count++];
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
