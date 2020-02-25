package com.winnie.step10broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/*
    [ Broadcast Receiver를 만드는 방법 ]

    - Broadcast Receiver 추상 클래스를 상속받아서 만든다.
    - onReceive() 메소드를 오버라이딩해서 방송이 수신되었을 때 원하는 작업을 한다.
    - AndroidManifest.xml에 등록을 해야한다.
 */
public class MyReceiver extends BroadcastReceiver {

    // 특정 방송(비행기모드가 on 또는 off 되었을 때)이 수신되면 호출되는 메소드
    @Override
    public void onReceive(Context context, Intent intent) {

        // Context를 MainActivity type으로 casting
        MainActivity activity=(MainActivity)context;

        boolean isOn=intent.getBooleanExtra("state", false);
        if(isOn){
           Toast.makeText(context, "Airplane mode is turned ON", Toast.LENGTH_LONG).show();
           activity.updateUI("Airplane mode is turned ON");
        }else{
            Toast.makeText(context, "Airplane mode is turned OFF", Toast.LENGTH_LONG).show();
            activity.updateUI("Airplane mode is turned OFF");
        }
        // Toast.makeText(context, "You've got a broadcast?", Toast.LENGTH_LONG).show();
    }
}
