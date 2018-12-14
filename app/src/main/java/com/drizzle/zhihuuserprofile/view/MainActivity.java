package com.drizzle.zhihuuserprofile.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;

import com.drizzle.zhihuuserprofile.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        if (fm != null) {
            UserProfileFragment userProfileFragment = new UserProfileFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.main_container, userProfileFragment);
            ft.commitAllowingStateLoss();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int contentTop =getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        Log.i("lgq", "标题栏_measuredHeight"+contentTop);
    }

}
