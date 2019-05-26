package com.example.ta;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button submit_btn;
    EditText phone_number_edt;
    public static final String MyPREFERENCES = "MyPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffb2b2")));

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.red));
        }

        submit_btn = findViewById(R.id.phone_number_btn_main_activity);
        phone_number_edt = findViewById(R.id.phone_number_edt_main_activity);

        if (PreferenceData.getUserLoggedInStatus(getApplicationContext()) == true) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra("number", PreferenceData.getLoggedInPhoneNumberUser(this));
            startActivityForResult(intent,888);
        }

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber;
                phoneNumber = phone_number_edt.getText().toString();
                PreferenceData.setUserLoggedInStatus(getApplicationContext(), true);
                PreferenceData.setLoggedInUserPhoneNumber(getApplicationContext(), phoneNumber);
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("number", phoneNumber);
                startActivity(intent);
            }
        });
    }
}
