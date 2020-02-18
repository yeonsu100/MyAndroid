package com.winnie.step04customlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;

public class DetailActivity extends AppCompatActivity
                            implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // DetailActivity가 활성화 되는데 사용되었던 Intent 객체의 참조값 얻어오기
        // Intent intent=getIntent();
        // "dto"라는 키값으로 담긴 Serializable type 객체의 참조값 얻어오기 (getSerializableExtra() 이용)
        // Serializable serial=intent.getSerializableExtra("dto");
        // 원래 type (CountryDto)으로 casting!
        // CountryDto dto=(CountryDto)serial;

        // 위의 3줄을 1줄의 코드로 바꾸면 다음과 같다
        CountryDto dto=(CountryDto)getIntent().getSerializableExtra("dto");

        // 전개된 레이아웃에서 UI 참조값 얻어오기
        ImageView imageView=findViewById(R.id.imageView);
        TextView textView= findViewById(R.id.textView);
        Button confirmBtn=findViewById(R.id.confirmBtn);

        // 이미지와 내용 출력하기
        imageView.setImageResource(dto.getResId());
        textView.setText(dto.getContent());

        // 버튼에 리스너 등록하기
        confirmBtn.setOnClickListener(this);
    }

    // 버튼을 눌렀을 때 실행순서는 아래 코드가 된다.
    @Override
    public void onClick(View view) {
        // 액티비티 종료시키기
        finish();
    }
}
