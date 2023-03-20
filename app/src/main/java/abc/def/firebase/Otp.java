package abc.def.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Otp extends AppCompatActivity {

    EditText phno,pas;
    Button phno2,otp;

    FirebaseAuth fauth;
    private String verificationId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        phno = findViewById(R.id.phno);
        pas = findViewById(R.id.pas);
        phno2 = findViewById(R.id.phno2);
        otp = findViewById(R.id.otp);
        fauth = FirebaseAuth.getInstance();

        phno2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!phno.getText().toString().isEmpty() && phno.getText().toString().length()==10) {
                    String phoneNum = "+91" + phno.getText().toString();
                    requestOTP(phoneNum);
                } else {
                    phno.setError("Invalid PhoneNumber");
                }
            }
        });
        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifycode(pas.getText().toString());
            }
        });
    }
            private void requestOTP(String phoneNum){
                PhoneAuthOptions options = PhoneAuthOptions.newBuilder(fauth)
                        .setPhoneNumber(phoneNum)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(Otp.this)
                        .setCallbacks(mCallbacks)
                        .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
            }
            private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    verificationId = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    final String code = phoneAuthCredential.getSmsCode();
                    if(code!=null){
                        pas.setText(code);
                    }
                    verifycode(code);
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {

                }
            };
            private void verifycode(String code){
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
                signInWithCredential(credential);
            }
            private void signInWithCredential(PhoneAuthCredential credential){
                fauth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent i = new Intent(Otp.this,Homepage.class);
                            startActivity(i);
                            finish();
                        }
                        else {
                            Toast.makeText(Otp.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }


}