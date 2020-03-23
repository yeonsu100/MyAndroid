package com.winnie.step17sqlitedb;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity
                            implements View.OnClickListener {
    private DBHelper helper;
    private EditText editContent;
    private TodoDto dto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Intent에 전달된 TodoDto 객체 얻어내기
        dto=(TodoDto)getIntent().getSerializableExtra("dto");
        helper=new DBHelper(this, "MyDB.sqlite", null, 1);

        // 필요한 UI의 참조값 얻어오기
        TextView textNum=findViewById(R.id.textNum);
        editContent=findViewById(R.id.editContent);
        TextView textRegdate=findViewById(R.id.textRegdate);
        Button updateBtn=findViewById(R.id.updateBtn);
        Button listBtn=findViewById(R.id.listBtn);

        // 할 일 정보 출력하기
        textNum.setText("No.: "+dto.getNum());
        editContent.setText(dto.getContent());
        textRegdate.setText("Date : "+dto.getRegdate());

        // 버튼에 리스너 등록하기
        updateBtn.setOnClickListener(this);
        listBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.updateBtn:
                // 할 일(디테일 정보)을 읽어와서
                String todo=editContent.getText().toString();
                // DB에 수정 반영한다.
                SQLiteDatabase db=helper.getWritableDatabase();
                String sql="UPDATE todo" +
                        " SET content=?" +
                        " WHERE num=?";
                Object[] args={todo, dto.getNum()};
                db.execSQL(sql, args);
                db.close();
                Toast.makeText(this, "Successfully Updated!", Toast.LENGTH_LONG).show();
                break;
            case R.id.listBtn:
                // 액티비티를 종료시켜서 목록보기 액티비티로 돌아간다.
                finish();
                break;
        }
    }
}
