package com.winnie.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // MainActivity가 활성화 될 때 최초 한번 호출되는 메소드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // res/layout/activity_main.xml 문서를 전개해서 화면 구성하기
        setContentView(R.layout.activity_main);
    }

    // 버튼을 눌렀을 때 메소드가 호출되게 하려면 인자로 View 객체를 받을 준비를 하면 된다.
    public void btnClicked(View v){
        // 로그 출력하기
        Log.d("one", "You did click this button!");
        // 토스트 띄우기 (this는 나(MainActivity)의 참조값이다)
        Toast.makeText(this, "You did click this button!", Toast.LENGTH_LONG).show();
        // 알림창 띄우기
        new AlertDialog.Builder(this)
                .setTitle("Message")
                .setMessage("You did click this button!")
                .setNeutralButton("confirm", null)
                .create()
                .show();
    }
}
