package com.example.systemwatcher;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private NetworkStateReceiver networkReceiver;
    private boolean isReceiverActive = false;
    private Button btnToggleReceiver, btnSendCustom;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        networkReceiver = new NetworkStateReceiver();

        tvStatus        = findViewById(R.id.tvStatus);
        btnToggleReceiver = findViewById(R.id.btnToggleReceiver);
        btnSendCustom   = findViewById(R.id.btnSendCustom);

        btnToggleReceiver.setOnClickListener(v -> toggleNetworkReceiver());
        btnSendCustom.setOnClickListener(v -> sendCustomSignal());
    }

    private void toggleNetworkReceiver() {
        if (!isReceiverActive) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            registerReceiver(networkReceiver, filter);
            isReceiverActive = true;
            tvStatus.setText("📡 Surveillance réseau : ACTIVE");
            btnToggleReceiver.setText("Désactiver surveillance");
        } else {
            unregisterReceiver(networkReceiver);
            isReceiverActive = false;
            tvStatus.setText("🔴 Surveillance réseau : INACTIVE");
            btnToggleReceiver.setText("Activer surveillance");
        }
    }

    private void sendCustomSignal() {
        Intent intent = new Intent("com.example.systemwatcher.SYSTEM_ALERT");
        intent.putExtra("info", "Alerte système déclenchée manuellement !");
        sendBroadcast(intent);
        Toast.makeText(this, "📣 Signal custom envoyé !", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (isReceiverActive) {
            unregisterReceiver(networkReceiver);
        }
        super.onDestroy();
    }
}