package com.bitm.firebaseauthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText phnumET;
    private Button nextBtn;
    private String phoneno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phoneno=phnumET.getText().toString().trim();
                Intent intent=new Intent(LoginActivity.this,VerifyActivity.class);
                intent.putExtra("phone",phoneno);
                startActivity(intent);

            }
        });
    }

    private void init() {

        phnumET=findViewById(R.id.phoneNumET);
        nextBtn=findViewById(R.id.nextBtn);
    }
}
