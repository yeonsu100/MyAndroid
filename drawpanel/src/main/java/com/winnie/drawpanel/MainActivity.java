package com.winnie.drawpanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // 그림판의 참조값을 담을 필드
    DrawPanel panel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //DrawPanel panel=new DrawPanel(this);
        //setContentView(panel);              // DrawPanel로 전체를 꽉 채우겠다는 의미
        setContentView(R.layout.activity_main);
        // 그림판의 참조값
        panel=findViewById(R.id.drawPanel);
    }

    // 저장하기 버튼을 눌렀을 때 외부 저장장치에 저장하기 (핸드폰은 별도의 외장하드의 개념으로 sd카드를 사용한다.)
    public void save(View v){
        /*
            외부 저장장치를 사용할 권한을 체크해서 권한이 없으면 권한을 얻도록 유도하고
            권한이 이미 있으면 외부 저장 장치에 파일을 만들어서 저장한다.
         */
        int permissionCheck=
                ContextCompat.checkSelfPermission
                        (this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // 만일 권한이 없으면
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            // 권한을 얻어내도록 유도한다.
            String[] permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissions, 0);
                        // permissions : 요청 퍼미션의 목록, 0 : 요청의 식별값 (request code)
        }else {         // 권한이 없다면
            // 파일에 저장한다.
            saveToFile();
        }
    }

    // 퍼미션 다이얼로그를 띄운 다음 결과값을 받을 메소드를 오버라이드 한다.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 요청 코드에 따라 분기한다.
        switch (requestCode){
            case 0:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    // 파일에 저장한다.
                    saveToFile();
                }else{              // Allow하지 않은 경우
                    Toast.makeText(this, "You won't to save this file?", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    // 그림정보를 파일에 저장하는 메소드
    public void saveToFile(){
        // 파일에 저장할 객체의 참조값 얻어오기
        List<Point> pointList=panel.getPointList();

        // 외부 저장장치의 절대경로 (사용자의 기기가 모두 다르기 때문에 파일명도 신경써야 한다. 확장자는 중요하진 않다.)
        String path=getExternalFilesDir(null).getAbsolutePath();

        File file=new File(path+"/data.dat");

        FileOutputStream fos=null;
        ObjectOutputStream oos=null;
        try{
            if(! file.exists()){
                file.createNewFile();
            }
            // 파일에 출력할 객체
            fos=new FileOutputStream(file);
            // 객체를 파일에 출력할 수 있는 객체
            oos=new ObjectOutputStream(fos);
            // List<Point> 객체 출력하기
            oos.writeObject(pointList);
            // 토스트 메세지 띄우기
            Toast.makeText(this, "Successfully saved file!", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Log.e("MainActivity", e.getMessage());
        }finally {
            try{
                if(fos!=null)fos.close();
                if(oos!=null)oos.close();
            }catch (Exception e){}
        }
    }

    // 불러오기 버튼을 눌렀을 때 호출되는 메소드
    public void load(View v){
        // 외부 저장장치의 절대 경로
        String path=getExternalFilesDir(null).getAbsolutePath();
        File file=new File(path+"/data.dat");
        if(!file.exists()){         // 파일이 존재하지 않으면
            return;                 // 메소드를 끝낸다.
        }

        FileInputStream fis=null;
        ObjectInputStream ois=null;
        try{
            fis=new FileInputStream(file);
            ois=new ObjectInputStream(fis);
            // 읽어온 Object를 원래 type인 List로 casting하기
            List<Point> pointList=(List)ois.readObject();
            // DrawPanel View에 전달해서 화면이 다시 그려지도록 한다.
            panel.setPointList(pointList);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(ois!=null)ois.close();
                if(fis!=null)fis.close();
            }catch(Exception e){}
        }
    }

}
