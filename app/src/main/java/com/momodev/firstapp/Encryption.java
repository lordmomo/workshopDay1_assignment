package com.momodev.firstapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.security.MessageDigest;
import java.util.Base64;

public class Encryption extends AppCompatActivity {

    Button encBtn;
    Button decBtn;
    Button displayQr;
    String outputString;
    String AES = "AES";

    TextView outputText;
    EditText inputText, inputPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Encryption");

        inputText = (EditText)findViewById(R.id.inputText);
        inputPassword = (EditText)findViewById(R.id.password);
        outputText= (TextView)findViewById(R.id.outputText);

        encBtn =(Button) findViewById(R.id.encode);
        decBtn =(Button) findViewById(R.id.decode);
        displayQr = (Button) findViewById(R.id.showQR);

        encBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              try {
                  outputString = encrypt(inputText.getText().toString(),inputPassword.getText().toString());
                  outputText.setText(outputString);
              }catch(Exception e){
                  e.printStackTrace();
              }
            }
        });

        decBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    outputString = decrypt(outputString,inputPassword.getText().toString());
                    outputText.setText(outputString);
                }catch(Exception e){
                    Toast.makeText(Encryption.this,"WrongPassword",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });



        displayQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // String password = inputPassword.getText().toString();
                String outputMessage = outputText.getText().toString();
                Intent intent = new Intent(Encryption.this, QrDisplayActivity.class);
               // intent.putExtra("inputPassword",password);
                intent.putExtra("outputText",outputMessage);
                startActivity(intent);
            }
        });

    }

    private String decrypt(String outputString, String password) throws Exception{
        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE,key);
        byte[] decodedValue = Base64.getDecoder().decode(outputString);
        byte[] decValue = c.doFinal(decodedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }


    private String encrypt(String Data, String password) throws Exception{
        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = Base64.getEncoder().encodeToString(encVal);
        return encryptedValue;
    }

    //data encodes in messageDigest

    private SecretKeySpec generateKey(String password) throws Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes,0,bytes.length);
        byte[] key= digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


 }