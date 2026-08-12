package com.example.qrbarcodescanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    DecoratedBarcodeView barcodeView;
    ImageButton btnFlash ;
    ImageView image21;
    boolean flashOn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, 100);
        }
        barcodeView = findViewById(R.id.barcodeView);
        btnFlash = findViewById(R.id.btnFlash);
        image21=findViewById(R.id.image21);
barcodeView.getStatusView().setVisibility(View.GONE);
        barcodeView.decodeContinuous(result -> {
            barcodeView.pause();
            runOnUiThread(() -> {
                String text = result.getText();

                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("scannedText", text);
                startActivity(intent);
            });
            appdatabase db = databaseclient.getDatabase(this);

            String date = new SimpleDateFormat(
                    "dd MMM yyyy HH:mm",
                    Locale.getDefault()
            ).format(new Date());

            scanhistory history = new scanhistory(
                    result.getText(),
                    result.getBarcodeFormat().toString(),
                    date
            );

            Executors.newSingleThreadExecutor().execute(() -> {
                db.scanHistoryDao().insert(history);
            });
        });
        btnFlash.setOnClickListener(v -> {

            if(flashOn){
                barcodeView.setTorchOff();
                btnFlash.setImageResource(R.drawable.flash);
            }else{
                barcodeView.setTorchOn();
                btnFlash.setImageResource(R.drawable.flash4);
            }

            flashOn = !flashOn;
        });
        image21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        HIstoryActivity.class);

                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeView.pause();
    }
}