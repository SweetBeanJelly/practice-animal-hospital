package com.jyk.administrator.animal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class Admin_reservation_delete extends Activity {

    Button btnE;

    ListView list;
    String resultData;

    String r_num[] = new String[20];
    String r_year[] = new String[20];
    String r_subject[] = new String[20];
    String r_id[] = new String[20];
    String r_content[] = new String[20];

    String txtId;
    String txtYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 진료예약삭제화면
        setTitle("진료예약삭제");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_reservation_delete);

        dbReSelect();

        list = (ListView)findViewById(R.id.listView);
        final a_reservation_select adapter = new a_reservation_select(Admin_reservation_delete.this);
        list.setAdapter(adapter);
        // 리스트뷰 갱신
        adapter.notifyDataSetInvalidated();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtId = r_id[position];
                txtYear = r_year[position];
                popUp(view);
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

    class a_reservation_select extends ArrayAdapter<String> {
        private final Activity activity;

        public a_reservation_select(Activity context) {
            super(context, R.layout.admin_reservation_delete_item,r_id);
            this.activity = context;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = activity.getLayoutInflater();

            View item = inflater.inflate(R.layout.admin_reservation_delete_item, null, true);
            TextView t_num = (TextView)item.findViewById(R.id.txtNum);
            TextView t_id = (TextView)item.findViewById(R.id.txtId);
            TextView t_year = (TextView)item.findViewById(R.id.txtYear);
            TextView t_subject = (TextView)item.findViewById(R.id.txtSubject);
            TextView t_content = (TextView)item.findViewById(R.id.txtC);

            TextView txt1 = (TextView)item.findViewById(R.id.txt1);
            TextView txt2 = (TextView)item.findViewById(R.id.txt2);
            TextView txt3 = (TextView)item.findViewById(R.id.txt3);

            t_num.setText(r_num[position]);
            t_id.setText(r_id[position]);
            t_year.setText(r_year[position]);
            t_subject.setText(r_subject[position]);
            t_content.setText(r_content[position]);

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

    public void dbReSelect() {
        new Thread() {
            @Override
            public void run() {
                try{
                    URL url = new URL("http://jcpdit.dothome.co.kr/dbconn/reservationSelectA.php/");
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
                        r_num[i] = result[count++];
                        r_id[i] = result[count++];
                        r_year[i] = result[count++];
                        r_subject[i] = result[count++];
                        r_content[i] = result[count++];
                    }

                }
                catch (Exception e) {
                    Log.e("Error", "", e);
                }
            }
        }.start();
    }

    public void dbDeleteRe() {
        new Thread() {
            @Override
            public void run() {
                try {
                    
                    URL url = new URL("http://jcpdit.dothome.co.kr/dbconn/deleteReservation.php/");
                    HttpURLConnection http;
                    http = (HttpURLConnection) url.openConnection();
                    http.setDoOutput(true);

                    StringBuffer buffer = new StringBuffer();
                    buffer.append("id").append("=").append(txtId).append("&");
                    buffer.append("year").append("=").append(txtYear);

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

                }
                catch (Exception e){
                    Log.e("Error ", "", e);
                }
            }
        }.start();
    }

    public void popUp(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage("예약삭제를 하시겠습니까?");
        builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbDeleteRe();
                finish();
            }
        });
        builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
