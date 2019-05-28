package com.example.ta;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cloudmersive.client.FaceApi;
import com.cloudmersive.client.invoker.ApiClient;
import com.cloudmersive.client.invoker.ApiException;
import com.cloudmersive.client.invoker.Configuration;
import com.cloudmersive.client.invoker.auth.ApiKeyAuth;

import java.io.ByteArrayInputStream;
import java.io.File;

public class FaceRecognitionActivity extends AppCompatActivity {
    public static final String API_KEY = "42194df8-20d4-4b34-8e93-155efbec5423";

    ApiClient defaultClient = Configuration.getDefaultApiClient();
    ImageView imageView;
    Button btn;
    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_recognition);
        path = getIntent().getStringExtra("image");
        imageView = findViewById(R.id.test_img);
        btn = findViewById(R.id.process_btn);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffb2b2")));

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.red));
        }


        ApiKeyAuth Apikey = (ApiKeyAuth) defaultClient.getAuthentication("Apikey");
        Apikey.setApiKey(API_KEY);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faceCrop();
            }
        });
    }

    public void faceCrop(){
        new Thread(()->{
            FaceApi apiInstance = new FaceApi();
            File imageFile = new File(path);
            try {
                byte[] result = apiInstance.faceCropFirstRound(imageFile);
                Bitmap b = BitmapFactory.decodeStream(new ByteArrayInputStream(result));
                runOnUiThread(()->finishedCrop(b));
            } catch (Exception e) {
                System.err.println("Exception when calling FaceApi#faceCropFirstRound");
                e.printStackTrace();
            }
        }).start();

    }

    private void finishedCrop(Bitmap b) {
        imageView.setImageBitmap(b);
    }
}
