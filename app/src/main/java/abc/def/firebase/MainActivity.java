package abc.def.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    EditText txt1,txt2;
    TextView f2;
    Button btn1, btn2;
    FirebaseAuth Fauth;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.pass);
        f2 = findViewById(R.id.f1);

        btn1 = findViewById(R.id.button);
        btn2 = findViewById(R.id.btn3);
        Fauth = FirebaseAuth.getInstance();

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Register.class);
                startActivity(intent);
            }
        });

//        f2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,Forgotpassword.class);
//                startActivity(intent);
//            }
//        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lname = txt1.getText().toString().trim();
                String lpassword = txt2.getText().toString().trim();

                Fauth.signInWithEmailAndPassword(lname,lpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Homepage.class));
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Login Failure", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                Intent intent = new Intent(MainActivity.this,Register.class);
                startActivity(intent);

            }

        });
    }

    public void OnClick(View view) {
        startActivity(new Intent(getApplicationContext(),Forgotpassword.class));

    }

//    public void OnClick(View view) {
//    }
}