package com.example.ankpmessenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText userETlogin, passETlogin;
    Button loginBtn, registerBtn;

    //Firebase
    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //Verificarea existentei utilizatorilor: salvarea utilizatorului curent
        if(firebaseUser != null){
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userETlogin = findViewById(R.id.editTextEmail);
        passETlogin = findViewById(R.id.editTextPassword);
        loginBtn = findViewById(R.id.buttonLogin);
        registerBtn = findViewById(R.id.btn_2);

        //Firebase Auth:
        auth = FirebaseAuth.getInstance();

        //Buton inregistrare
       registerBtn.setOnClickListener(new View.OnClickListener() {
          @Override
           public void onClick(View v) {
              Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
              startActivity(i);
         }
      });


        //Buton login
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_text = userETlogin.getText().toString();
                String pass_text = passETlogin.getText().toString();


                //Verificare daca un camp este liber

                if(TextUtils.isEmpty(email_text) || TextUtils.isEmpty(pass_text)){
                    Toast.makeText(LoginActivity.this, "Completeaza campurile libere", Toast.LENGTH_SHORT).show();
                }
                else{
                    auth.signInWithEmailAndPassword(email_text,pass_text).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                                finish();
                            }
                            else{
                               Toast.makeText(LoginActivity.this, "Inregistrare nereusita!", Toast.LENGTH_SHORT);
                            }
                        }
                    });



                }
            }
        });
    }
}