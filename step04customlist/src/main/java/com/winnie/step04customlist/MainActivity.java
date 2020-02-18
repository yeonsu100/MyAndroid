package com.winnie.step04customlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
                            implements AdapterView.OnItemClickListener {
    // 필요한 필드 정의하기
    ListView listView;
    List<CountryDto> countries;
    CountryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.listView);
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

        // 어댑터 객체 생성
        adapter=new CountryAdapter(this,
                        R.layout.listview_cell, countries);
        // 어댑터를 ListView에 연결하기
        listView.setAdapter(adapter);
        // ListView에 아이템 클릭 리스너 등록하기
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
        // Intent 객체 생성하기
        Intent intent=new Intent(this, DetailActivity.class);
        // 클릭한 아이템에 해당하는 국가 정보를 얻어온다.
        CountryDto dto=countries.get(i);
        // 국가 정보를 Intent 객체에 "dto"라는 키값으로 담고 싶으나...(담기지 않는다...)
        intent.putExtra("dto", dto);
        // 액티비티 이동
        startActivity(intent);
    }
}
