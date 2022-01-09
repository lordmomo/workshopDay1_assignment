package com.momodev.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.Point;
//import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
//import android.widget.Toast;

import com.google.zxing.WriterException;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QrDisplayActivity extends AppCompatActivity {

    private ImageView qrCodeIV;
    private TextView encodedText;
    private Button generateQrBtn;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_display_acivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("QrGenerator");


        qrCodeIV = findViewById(R.id.idIVQrcode);
        encodedText = findViewById(R.id.idEncodedText);
        generateQrBtn = findViewById(R.id.idBtnGenerateQR);

        generateQrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (TextUtils.isEmpty(encodedText.getText().toString())) {

                    //if the edittext inputs are empty then execute
                    // this method showing a toast message.
                   Toast.makeText(QrDisplayActivity.this, "NO data provided to be encrypted.", Toast.LENGTH_SHORT).show();
                } else {
                 */
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int dimen = width < height ? width : height;
                    dimen = dimen * 3 / 4;

                Intent intent = getIntent();
                String outputMessage = intent.getStringExtra("outputText");

                encodedText.setText(outputMessage);
                    qrgEncoder = new QRGEncoder(encodedText.getText().toString(), null, QRGContents.Type.TEXT, dimen);
                    try {
                        bitmap = qrgEncoder.encodeAsBitmap();
                        qrCodeIV.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        Log.e("Tag", e.toString());
                    }
               // }

                }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}