package com.winnie.step12sharedpref;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // res/layout/settings_activity.xml 문서를 전개해서 화면 구성하기
        setContentView(R.layout.settings_activity);
        /*
            id가 settings인 레이아웃 (FrameLayout)에
            SettingsFragment로 화면 구성하게 하기
         */
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        // 좌상단 up 버튼 동작 가능하도록 한다.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    // FrameLayout을 채울 Fragment
    public static class SettingsFragment extends PreferenceFragmentCompat {
        // Fragment가 처음 활성화 될 때 호출되는 메소드
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            // res/xml/root_preferences.xml 문서를 전개해서 Fragment UI 구성하기
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}