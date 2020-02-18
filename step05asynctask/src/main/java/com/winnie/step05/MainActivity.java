package com.winnie.step05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // 진행 시작, 진행 과정, 결과를 표시할 TextView
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TextView의 참조값을  필드에 저장하기
        textView=findViewById(R.id.textView);
    }

    // 작업하기 버튼 (Task)을 눌렀을 때 호출되는 메소드
    public void start(View v) {
        /*
            버튼을 누르면 여기에 실행순서가 들어온다.
            그 스레드는 UI 스레드 (main 스레드) 이다.
            UI 스레드에서 시간이 오래 걸리거나 언제 끝날지 모르는 불확실한 작업을 하면 안된다.
            UI의 업데이트는 UI 스레드에서만 가능하다.
         */
        // 비동기 작업의 시작은 객체를 생성하고
        CounterTask task=new CounterTask();
        // execute() 메소드를 호출하면 된다.
        task.execute("Winnie", "Mickey", "Minnie");
    }

    /*
        extends AsyncTask<전달받는 type, 진행중 반환하는 type, 결과 type>

        type이 필요 없으면 Void type을 사용하면 된다.

        extends AsyncTask<String, void, Void>       * Void 타입 (대문자로 시작)
     */
       public class CounterTask extends AsyncTask<String, Integer, String>{

        // publishProgress() 메소드를 호출하면 아래의 메소드가 호출된다. (...은 배열을 나타낸다)
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // 여기는 UI 스레드이기 때문에 UI 업데이트 가능
            // publishProgress() 메소드에 전달된 인자가 이 메소드의 인자로 전달된다.
            int count=values[0];        // Integer 배열의 0번방에 값이 들어있다.
            textView.setText(Integer.toString(count));
        }

        // doInBackground() 메소드가 리턴되면 아래의 메소드가 호출된다.
        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            // 여기는 UI 스레드이기 때문에 UI 업데이트 가능
            textView.setText(s);
        }

        // doInBackground() 메소드가 호출되기 직전에 호출된다.
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // 여기는 UI 스레드이기 때문에 UI 업데이트 가능
            textView.setText("Start on counting numbers!");
        }

        @Override
           protected String doInBackground(String... strings) {
                String name1=strings[0];        // Winnie
                String name2=strings[1];        // Mickey
                String name3=strings[2];        // Minnie

               int count=0;
               // 백그라운드 (새로운 스레드)에서 작업할 내용을 여기서 하면 된다.
               for(int i=0; i<20; i++){
                   try {
                       Thread.sleep(1000);
                   } catch (Exception e) {}
                   count++;
                   // count 값을 TextView에 직접 출력이 안된다 (+정수 바로 쓰면 안되고 반드시 문자열로 출력)
                   publishProgress(count);
               }
               String result="Successfully counted!";

               return result;
           }
       }
}
