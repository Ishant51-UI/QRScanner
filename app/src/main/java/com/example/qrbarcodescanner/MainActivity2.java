package com.example.qrbarcodescanner;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity2 extends AppCompatActivity {

    TextView txtScanned, txtType, txtDate, txtTime;

    Button btnCopy, btnOpen, btnShare;

    String scannedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> finish());

        txtScanned = findViewById(R.id.txtScanned);
        txtType = findViewById(R.id.txtType);
        txtDate = findViewById(R.id.txtDate);
        txtTime = findViewById(R.id.txtTime);

        btnCopy = findViewById(R.id.btnCopy);
        btnOpen = findViewById(R.id.btnOpen);
        btnShare = findViewById(R.id.btnShare);

        scannedText = getIntent().getStringExtra("scannedText");

        if (scannedText == null) {
            scannedText = "";
        }

        txtScanned.setText(scannedText);

        // Date and time
        String date = new java.text.SimpleDateFormat(
                "dd MMM yyyy",
                java.util.Locale.getDefault()
        ).format(new java.util.Date());

        String time = new java.text.SimpleDateFormat(
                "hh:mm a",
                java.util.Locale.getDefault()
        ).format(new java.util.Date());

        txtDate.setText("Date: " + date);
        txtTime.setText("Time: " + time);

        txtType.setText("Type: QR_CODE");


        // COPY
        btnCopy.setOnClickListener(v -> {

            ClipboardManager clipboard =
                    (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

            ClipData clip =
                    ClipData.newPlainText("Scanned QR", scannedText);

            clipboard.setPrimaryClip(clip);

            Toast.makeText(
                    this,
                    "Copied",
                    Toast.LENGTH_SHORT
            ).show();
        });


        // OPEN
        btnOpen.setOnClickListener(v -> {

            try {

                Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(scannedText)
                );

                startActivity(intent);

            } catch (Exception e) {

                Toast.makeText(
                        this,
                        "Invalid URL",
                        Toast.LENGTH_SHORT
                ).show();

            }

        });


        // SHARE
        btnShare.setOnClickListener(v -> {

            Intent shareIntent = new Intent(
                    Intent.ACTION_SEND
            );

            shareIntent.setType("text/plain");

            shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    scannedText
            );

            startActivity(
                    Intent.createChooser(
                            shareIntent,
                            "Share using"
                    )
            );
        });
    }
}