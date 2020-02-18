package com.winnie.step03listview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity
                            implements AdapterView.onItemLongClickListener {
    // 필드
    List<String> names;
    // 선택한 셀 인덱스를 저장할 필드
    int selectedIndex;
    // ListView에 연결된 어댑터의 참조값을 저장할 필드
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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
        // 셀을 오랫동안 눌렀을 때 동작할 리스너 등록하기
        listView.setOnItemLongClickListener(this);
    }

    // AlertDialog 버튼 클릭 리스너 객체
    DialogInterface.OnClickListener listenter=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            switch (i){
                case DialogInterface.BUTTON_POSITIVE:           // yes 버튼을 눌렀을 때 (i값 : -1)
                    // 1. 선택한 아이템 인덱스를 모델에서 삭제한다.
                    names.remove(selectedIndex);
                    // 2. ListView를 업데이트 한다. (어댑터를 이용해서)
                    adapter.notifyDataSetChanged();             // 데이터가 바뀌었으니 바뀐 데이터를 출력하라는 의미
                    break;
                case DialogInterface.BUTTON_NEGATIVE:       // no 버튼을 눌렀을 때 (i값 : -2)

                        break;
            }
        }
    };

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l){
        // 선택한 인덱스를 필드에 저장한다. (직접 참조하지 않고, 필드에 저장해놓고 필드에 저장한 값을 참조)
        selectedIndex=i;

        new AlertDialog.Builder(this)
                .setTitle("Notice")
                .setMessage("Are you sure that remove this cell? (cell no."+names.get(i)+")")
                .setPositiveButton("Yes", listenter)
                .setNegativeButton("No", listenter)
                .create()
                .show();
        return false;
    }
}
