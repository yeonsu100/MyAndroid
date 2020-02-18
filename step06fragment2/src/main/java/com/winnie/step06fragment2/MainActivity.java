package com.winnie.step06fragment2;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // ViewPager에서 사용할 모델
    List<CountryDto> countries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 레이아웃 구성하기
        setContentView(R.layout.activity_main);
        countries=new ArrayList<>();
        countries.add(new CountryDto(R.drawable.usa,
                "U.S.A.", "The United States of America"));
        countries.add(new CountryDto(R.drawable.korea,
                "KOREA", "Blah bla..."));
        countries.add(new CountryDto(R.drawable.japan,
                "JAPAN", "Blah bla..."));
        countries.add(new CountryDto(R.drawable.austria,
                "AUSTRIA", "Blah bla..."));
        countries.add(new CountryDto(R.drawable.belgium,
                "BELGIUM", "Blah bla..."));
        countries.add(new CountryDto(R.drawable.brazil,
                "BRAZIL", "Blah bla..."));
        countries.add(new CountryDto(R.drawable.france,
                "FRANCE", "Blah bla..."));
        countries.add(new CountryDto(R.drawable.germany,
                "GERMANY", "Blah bla..."));
        countries.add(new CountryDto(R.drawable.greece,
                "GREECE", "Blah bla..."));
        countries.add(new CountryDto(R.drawable.israel,
                "ISRAEL", "Blah bla..."));
        countries.add(new CountryDto(R.drawable.italy,
                "ITALY", "Blah bla..."));
        countries.add(new CountryDto(R.drawable.poland,
                "POLAND", "Blah bla..."));
        countries.add(new CountryDto(R.drawable.spain,
                "SPAIN", "Blah bla..."));
        // Pager 어댑터 객체 생성하기
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(countries, getSupportFragmentManager());
        // ViewPager 객체의 참조값 얻어오기 (ViewPager는 프래그먼트를 하나하나씩 보여준다.)
        ViewPager viewPager = findViewById(R.id.view_pager);
        // ViewPager에 PagerAdapter 객체 연결하기 (PagerAdapter는 ViewPager에 프래그먼트를 공급해 주는 어댑터이다.)
        viewPager.setAdapter(sectionsPagerAdapter);
        // TabLayout 객체의 참조값 얻어오기
        TabLayout tabs = findViewById(R.id.tabs);
        // Tab과 viewPager를 함께 연계해서 쓰도록 설정
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}