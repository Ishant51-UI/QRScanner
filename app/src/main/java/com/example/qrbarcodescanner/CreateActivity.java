package com.example.qrbarcodescanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateActivity extends AppCompatActivity {
    Button btnUrl, btnText, btnContact, btnWifi, btnEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create);









                Toolbar toolbar = findViewById(R.id.toolbar);

                setSupportActionBar(toolbar);

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }

                toolbar.setNavigationOnClickListener(v -> finish());


                btnUrl = findViewById(R.id.btnUrl);
                btnText = findViewById(R.id.btnText);
                btnContact = findViewById(R.id.btnContact);
                btnWifi = findViewById(R.id.btnWifi);
                btnEmail = findViewById(R.id.btnEmail);


                // URL
                btnUrl.setOnClickListener(v -> {

                    Intent intent =
                            new Intent(CreateActivity.this, CreateUrl.class);

                    startActivity(intent);

                });


                // TEXT
//                btnText.setOnClickListener(v -> {
//
//                    Intent intent =
//                            new Intent(CreateActivity.this, CreateTextActivity.class);
//
//                    startActivity(intent);
//
//                });
//
//
//                // CONTACT
//                btnContact.setOnClickListener(v -> {
//
//                    Intent intent =
//                            new Intent(CreateActivity.this, CreateContactActivity.class);
//
//                    startActivity(intent);
//
//                });
//
//
//                // WIFI
//                btnWifi.setOnClickListener(v -> {
//
//                    Intent intent =
//                            new Intent(CreateActivity.this, CreateWifiActivity.class);
//
//                    startActivity(intent);
//
//                });
//
//
//                // EMAIL
//                btnEmail.setOnClickListener(v -> {
//
//                    Intent intent =
//                            new Intent(CreateActivity.this, CreateEmailActivity.class);
//
//                    startActivity(intent);
//
//                });


    }
}
