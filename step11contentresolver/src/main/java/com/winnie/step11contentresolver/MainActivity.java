package com.winnie.step11contentresolver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
                        implements View.OnClickListener,
                                    AdapterView.OnItemClickListener {
    // 필요한 필드 정의하기
    private EditText editText;
    private ListView listView;
    private ContactAdapter adapter;
    private List<ContactDto> list=new ArrayList<>();
    private int selectedIndex;          // 클릭한 셀의 인덱스 값을 저장할 필드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.editText);
        listView=findViewById(R.id.listView);
        adapter=new ContactAdapter(this, R.layout.listview_cell, list);
        listView.setAdapter(adapter);

        // 버튼의 참조값 얻어와서 리스너 등록하기
        Button findBtn=findViewById(R.id.findBtn);
        findBtn.setOnClickListener(this);

        // ListView에 아이템 클릭 리스너 등록하기
        listView.setOnItemClickListener(this);
    }

    // 연락처 정보를 가져오는 메소드
    public void getContacts(){
        // 출력한 데이터를 한번 clear하고 읽어온다. (여러번 눌러도 데이터가 누적되지 않도록)
        list.clear();

        // ContentResolver 객체의 참조값 얻어오기
        ContentResolver resolver=getContentResolver();
        // 연락처 정보의 Uri의 참조값 얻어오기 (상수 객체로 미리 정의되어 있음)
        Uri contactUri= ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        // 원하는 컬럼명 (아이디 (PK), 전화번호, 이름)
        String[] columns={
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        };

        // 키워드 읽어오기
        String keyword=editText.getText().toString();
        // where절에 들어갈 조건
        String where=ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" LIKE ? ";
        // ?에 바인딩할 인자
        String[] args={"%"+keyword+"%"};

        // 정렬 (이름에 대하여 오름차순)
        String order=ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC";

        // 원하는 정보를 SELECT하고 결과값을 Cursor type으로 받는다.
        // .query(uri, columns, where, args, order)
        Cursor cursor=resolver.query(contactUri, columns, where, args, order);

        // Cursor 객체에서 반복문 돌면서 데이터 추출하기
        while(cursor.moveToNext()){
            int id=(int)cursor.getLong(0);
            String phoneNumber=cursor.getString(1);
            String name=cursor.getString(2);
            // 연락처 정보를 ContactDto에 담기
            ContactDto dto=new ContactDto(id, phoneNumber, name);
            // 모델에 추가한다.
            list.add(dto);
        }
        // ListView가 갱신되도록 어댑터에 알린다.
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int permissionCheck=
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_CONTACTS);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){       // 권한이 없다면
            // 권한을 얻어야하는 퍼미션 목록
            String[] permissions={Manifest.permission.READ_CONTACTS};
            // 권한을 얻어내도록 유도한다.
            ActivityCompat.requestPermissions(this, permissions, Constants.REQUEST_CONTACTS);
        }else{                      // 권한이 있다면
            getContacts();
        }
    }

    // 퍼미션을 체크하거나 취소했을 때 호출되는 메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case Constants.REQUEST_CONTACTS:                 // 0 번 요청 코드인 경우
                // 퍼미션을 Allow 했을경우
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    getContacts();
                }else{              // Allow 하지 않았을 경우
                    Toast.makeText(this, "Please check the permission state!",
                            Toast.LENGTH_LONG).show();
                }
                break;
            case Constants.REQUEST_CALL:                 // 1 번 요청 코드인 경우
                // 퍼미션을 Allow 했을경우
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    call();
                }else{              // Allow 하지 않았을 경우
                    Toast.makeText(this, "Please check the permission state!",
                            Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    // ListView의 cell을 클릭하면 호출하는 메소드
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // 선택한 인덱스 값을 필드에 저장
        selectedIndex=i;

        int permissionCheck=
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CALL_PHONE);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){       // 권한이 없다면
            // 권한을 얻어야하는 퍼미션 목록
            String[] permissions={Manifest.permission.CALL_PHONE};
            // 권한을 얻어내도록 유도한다.
            ActivityCompat.requestPermissions(this, permissions, Constants.REQUEST_CALL);
        }else{                      // 권한이 있다면
            call();
        }
    }

    // 전화를 거는 메소드
    public void call(){
        // 어댑터로부터 i번째 아이템을 얻어온다.
        ContactDto dto=(ContactDto) adapter.getItem(selectedIndex);
        // 전화번호를 얻어낸다.
        String phone=dto.getPhone();
        // 전화를 걸겠다는 Intent 객체를 생성한다.
        Intent intent=new Intent();
        /*
            - Intent.ACTION_DIAL은 통화버튼을 눌러야 전화가 걸리므로 permission이 필요없다.
            - Intent.ACTION_CALL은 바로 전화가 걸리기 때문에 permission을 얻어야한다.
         */
        //intent.setAction(Intent.ACTION_DIAL);
        intent.setAction(Intent.ACTION_CALL);
        // 전화번호를 Uri로 전달한다.
        Uri uri=Uri.parse("Tel : "+phone);
        intent.setData(uri);
        // 해당 인텐트를 받아줄 수 있는 액티비티를 찾는다.
        startActivity(intent);
    }
}
