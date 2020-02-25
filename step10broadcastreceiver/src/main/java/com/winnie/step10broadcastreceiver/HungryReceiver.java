package com.winnie.step10broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class HungryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MainActivity activity=(MainActivity)context;
        activity.updateUI("I'm so hungry...");
    }
}
