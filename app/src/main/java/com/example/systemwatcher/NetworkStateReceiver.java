package com.example.systemwatcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NetworkStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())) {
            boolean isOn = intent.getBooleanExtra("state", false);
            String msg = isOn
                    ? "✈ Mode avion activé — réseau coupé"
                    : " Réseau rétabli — mode avion désactivé";
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }
}