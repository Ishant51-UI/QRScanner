package com.example.qrbarcodescanner;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class CreateUrl extends AppCompatActivity {

    EditText editUrl;
    Button btnGenerate, btnCopy, btnShare, btnOpen;
    ImageView qrImage;

    String generatedUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_url);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> finish());

        editUrl = findViewById(R.id.editUrl);
        btnGenerate = findViewById(R.id.btnGenerate);
        btnCopy = findViewById(R.id.btnCopy);
        btnShare = findViewById(R.id.btnShare);
        btnOpen = findViewById(R.id.btnOpen);
        qrImage = findViewById(R.id.qrImage);

        btnCopy.setEnabled(false);
        btnShare.setEnabled(false);
        btnOpen.setEnabled(false);


        btnGenerate.setOnClickListener(v -> {

            String url = editUrl.getText().toString().trim();

            if (url.isEmpty()) {
                editUrl.setError("Enter a URL");
                return;
            }

            if (!url.startsWith("http://") &&
                    !url.startsWith("https://")) {

                url = "https://" + url;
            }

            generatedUrl = url;

            generateQR(url);

            btnCopy.setEnabled(true);
            btnShare.setEnabled(true);
            btnOpen.setEnabled(true);
        });


        // COPY
        btnCopy.setOnClickListener(v -> {

            ClipboardManager clipboard =
                    (ClipboardManager) getSystemService(
                            Context.CLIPBOARD_SERVICE);

            ClipData clip =
                    ClipData.newPlainText("URL", generatedUrl);

            clipboard.setPrimaryClip(clip);

            Toast.makeText(
                    this,
                    "URL copied",
                    Toast.LENGTH_SHORT
            ).show();
        });


        // OPEN
        btnOpen.setOnClickListener(v -> {

            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(generatedUrl)
            );

            startActivity(intent);
        });


        // SHARE
        btnShare.setOnClickListener(v -> {

            Intent shareIntent =
                    new Intent(Intent.ACTION_SEND);

            shareIntent.setType("text/plain");

            shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    generatedUrl
            );

            startActivity(
                    Intent.createChooser(
                            shareIntent,
                            "Share URL"
                    )
            );
        });
    }


    private void generateQR(String text) {

        try {

            BitMatrix bitMatrix =
                    new MultiFormatWriter().encode(
                            text,
                            BarcodeFormat.QR_CODE,
                            700,
                            700
                    );

            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();

            Bitmap bitmap = Bitmap.createBitmap(
                    width,
                    height,
                    Bitmap.Config.RGB_565
            );

            for (int x = 0; x < width; x++) {

                for (int y = 0; y < height; y++) {

                    bitmap.setPixel(
                            x,
                            y,
                            bitMatrix.get(x, y)
                                    ? android.graphics.Color.BLACK
                                    : android.graphics.Color.WHITE
                    );
                }
            }

            qrImage.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();

            Toast.makeText(
                    this,
                    "QR generation failed",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}