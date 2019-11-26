package com.jyk.administrator.animal;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by 진연경 on 2016-06-15.
 * chutedeau@daum.net
 * 010-8812-7561
 */

public class BackPressClose {
    private long backKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    public BackPressClose(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish();
            toast.cancel();
        }
    }


    private void showGuide() {
        toast = Toast.makeText(activity, "뒤로 버튼을 한번 더 누르면 종료됩니다.",
                Toast.LENGTH_SHORT);
        toast.show();
    }

}