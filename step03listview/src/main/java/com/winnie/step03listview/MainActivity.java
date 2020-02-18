package com.winnie.step03listview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
                                    implements AdapterView.OnItemClickListener {

    List<String> names;                 // 모델의 참조값을 저장할 필드
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ListView (목록 뷰 - 예 : 전화번호 주소록)의 참조값 얻어오기
        ListView listView=findViewById(R.id.listView);
        // 어댑터에 연결할 모델 (Data)
        names=new ArrayList<>();
        // 모델에 샘플 데이터 저장
        for(int i=0; i<100; i++){
            names.add("Winnie"+i);
        }
        // ListView에 연결할 어댑터 객체 생성하기
        // (생성자의 인자로 this 전달, 미리 만들어져있는 레이아웃 전달, 모델 전달)
        ArrayAdapter<String> adapter=
                new ArrayAdapter<String>(this,
                                        android.R.layout.simple_list_item_1,
                                        names);
        // ListView에 어댑터 연결하기
        listView.setAdapter(adapter);
        // ListView에 아이템 클릭 리스너 등록하기 (각각의 셀을 클릭했을 때 일어나는 이벤트)
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        // 인자로 전달되는 i에는 클릭한 셀의 인덱스 값이 들어있다.
        new AlertDialog.Builder(this)
                .setMessage(names.get(i))
                .setNeutralButton("confirm", null)
                .create()
                .show();

//        AlertDialog.Builder builder=new AlertDialog.Builder(this);
//        builder.setMessage(names.get(i));
//        builder.setNeutralButton("confirm", null);
//        AlertDialog dialog=builder.create();
//        dialog.show();
    }

    // Next Example 버튼을 눌렀을 때 호출되는 메소드
    public void moveNext(View v){
        // Main2Activity로 이동할 의도 객체 생성
        Intent i=new Intent(this, Main2Activity.class);
        // startActivity() 메소드 호출하면서 의도 객체를 전달하면 이동된다.
        startActivity(i);
    }

}




