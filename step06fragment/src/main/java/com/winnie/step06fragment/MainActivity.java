package com.winnie.step06fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
                        implements MyFragment.MyFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 만일 activity_main.xml에 정의되 fragment 객체의 참조값이 Activity에서 필요하다면?

        // 프래그먼트 매니저 객체의 참조값을 얻어와서
        FragmentManager fm=getSupportFragmentManager();
        MyFragment myFragment=(MyFragment)fm.findFragmentById(R.id.myFragment);
    }

    // 버튼을 눌렀을 때 호출되는 메소드 (액티비티 이동)
    public void move(View v){
        Intent i=new Intent(this, SubActivity.class);
        startActivity(i);
    }

    // MyFragment가 호출하는 메소드
    @Override
    public void showMessage(int count) {
        Toast.makeText(this, "Count : "+count, Toast.LENGTH_LONG).show();
    }

    // 프래그먼트가 호출할 메소드
//    public void showMessage(int count){
//        Toast.makeText(this, "Count : "+count, Toast.LENGTH_LONG).show();
//    }

}
