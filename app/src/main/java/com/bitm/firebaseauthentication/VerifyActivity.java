package com.bitm.firebaseauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyActivity extends AppCompatActivity {

    private EditText verificationcodeET;
    private Button loginBtn;
    private String phoneno;
    private String verificationId;

    private FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);


        init();



                phoneno=getIntent().getStringExtra("phone");

        sendOTP();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code =verificationcodeET.getText().toString();
                if (code.length() == 6){

                    verify(code);
                }
            }
        });
    }

    private void sendOTP() {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
               "+88"+phoneno,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                callbacks);        // OnVerificationStateChangedCallbacks

    }
   PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks =
           new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
               @Override
               public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                   String code = phoneAuthCredential.getSmsCode();
                   if(code != null){
                       verificationcodeET.setText(code);
                       verify(code);
                   }
               }

               @Override
               public void onVerificationFailed(@NonNull FirebaseException e) {

                   Toast.makeText(VerifyActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

               }

               @Override
               public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                   super.onCodeSent(s, forceResendingToken);
                   verificationId = s;
               }
           };

    private void verify(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
signInWithCredintial(credential);


    }

    private void signInWithCredintial(PhoneAuthCredential credential) {

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    Intent intent=new Intent(VerifyActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

                else {

                    Toast.makeText(VerifyActivity.this, "Please Enter Verification Code", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }


    private void init() {

        verificationcodeET=findViewById(R.id.verificationcodeET);
        loginBtn=findViewById(R.id.loginBtn);
        firebaseAuth=FirebaseAuth.getInstance();
    }
}
