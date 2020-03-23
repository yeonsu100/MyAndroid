package com.winnie.step17sqlitedb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // 필드
    private DBHelper helper;
    private EditText inputText;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    // ListView에 출력하기 위해 어댑터에 연결할 문자열 목록
    private List<String> stringList;
    // DB에 있는 실제 Data를 가지고있는 TodoDto 목록
    private List<TodoDto> todoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // version을 바꾸게 되면 DBHelper 객체의 onUpgrade() 메소드가 호출된다.
        helper=new DBHelper(this, "MyDB.sqlite", null, 1);

        // UI의 참조값 얻어오기
        inputText=findViewById(R.id.inputText);
        listView=findViewById(R.id.listView);
        Button saveBtn=findViewById(R.id.saveBtn);
        Button deleteBtn=findViewById(R.id.deleteBtn);
        Button detailBtn=findViewById(R.id.detailBtn);

        // 버튼 리스너 등록
        saveBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        detailBtn.setOnClickListener(this);

        // 모델 객체 생성
        todoList=new ArrayList<>();

        // 어댑터 객체 생성
        adapter=new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_single_choice, stringList);

        // ListView에 어댑터 연결하기
        listView.setAdapter(adapter);

        todoList=new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 목록 출력하기
        showList();
    }

    // SAVE 버튼을 눌렀을 때 호출되는 메소드
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.detailBtn:
                int index2=listView.getCheckedItemPosition();
                if(index2 == -1){      // 선택된 셀이 없을 경우 (대체로 -1을 반환한다)
                    Toast.makeText(this, "Select a cell for detail, please!", Toast.LENGTH_LONG).show();
                    return;         // 메소드 끝내기
                }
                Intent intent=new Intent(this, DetailActivity.class);
                // 클릭한 셀에 해당되는 dto 객체 얻어오기
                TodoDto dto=todoList.get(index2);
                // Intent 객체에 담고 (putExtra : dto라는 키값으로 시리얼라이저블 타입을 담은 것.
                // 읽을때는 getSerializableExtra("dto")로 읽을 수 있다)
                intent.putExtra("dto", dto);
                // 액티비티 이동
                startActivity(intent);
                break;

            case R.id.deleteBtn:    // 삭제 버튼을 눌렀을 때
                int index=listView.getCheckedItemPosition();
                if(index == -1){      // 선택된 셀이 없을 경우 (대체로 -1을 반환한다)
                    Toast.makeText(this, "Select a cell for delete, please!", Toast.LENGTH_LONG).show();
                    return;         // 메소드 끝내기
                }
                SQLiteDatabase db2=helper.getWritableDatabase();
                String sql2="DELETE FROM todo" +
                        " WHERE num=?";
                // 삭제할 셀의 Primary Key
                int num=todoList.get(index).getNum();
                Object[] args2={num};
                db2.execSQL(sql2, args2);
                db2.close();
                // 선택된 셀 취소하기
                listView.clearChoices();
                // 목록 다시 출력하기
                showList();
                break;

            case R.id.saveBtn:      // 저장 버튼을 눌렀을 때
                // 1. 입력한 문자열을 읽어와서
                String inputMsg=inputText.getText().toString();
                // 2. todo 테이블에 저장한다.
                SQLiteDatabase db=helper.getWritableDatabase();
                Object[] args={inputMsg};
                String sql="INSERT INTO todo (content, regdate)" +
                        " VALUES(?, datetime('now', 'localtime'))";
                db.execSQL(sql, args);
                db.close();

                showList();
                break;
        }
    }

    // ListView에 할 일 목록을 출력하는 메소드
    public void showList(){
        // 모델 초기화
        stringList.clear();
        todoList.clear();

        SQLiteDatabase db=helper.getReadableDatabase();
        // 실행할 SELECT문
        /*
            strftime ("날짜형식", 날짜, ('localtime'))
            연 : %Y
            월 : %m
            일 : %d
            시 : %H
            분 : %M
            초 : %S
         */
        String sql="SELECT num, content, strftime(\"%Y. %m. %d\", regdate)" +
                "FROM todo" +
                " ORDER BY num DESC";
        // SELECT문 수행하고 결과를 Cursor type으로 받아오기
        Cursor result=db.rawQuery(sql, null);
        // 반복문 돌면서 Cursor 객체에서 정보 읽어오기
        while(result.moveToNext()){
            // 0~2번째 컬럼의 문자열 읽어오기
            int num=result.getInt(0);
            String content=result.getString(1);
            String regdate=result.getString(2);

            // 읽어온 문자열을 모델에 추가하기
            stringList.add(content);
            // TodoDto 객체를 생성해서 번호, 내용, 등록날짜를 넣어주고
            TodoDto dto=new TodoDto(num, content, regdate);
            // todoList에 추가하기
            todoList.add(dto);
        }
        // 모델의 data가 바뀌었다고 어댑터에 알려서 ListView 업데이트하기
        adapter.notifyDataSetChanged();
    }
}
