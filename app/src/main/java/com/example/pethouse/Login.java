package com.example.pethouse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://pethouse-67da0-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText login = findViewById(R.id.edt1);
        final EditText mdp = findViewById(R.id.edt2);
        final Button btnLogin = findViewById(R.id.btnLogin);
        final Button btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String logintxt = login.getText().toString();
                final String passwordtxt = mdp.getText().toString();

                if (logintxt.isEmpty() || passwordtxt.isEmpty()){
                    Toast.makeText(Login.this,"Svp ecrire votre login ou mdp",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(logintxt)) {
                                final String getMdp = snapshot.child(logintxt).child("mdp").getValue(String.class);

                                if (getMdp.equals(passwordtxt)){
                                    Toast.makeText(Login.this,"Login avec succeé",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this,MainActivity.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(Login.this,"impossible de connexion",Toast.LENGTH_SHORT).show();

                                }
                            }
                            else {
                                Toast.makeText(Login.this,"impossible de connexion",Toast.LENGTH_SHORT).show();

                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this , Register.class));

            }
        });

    }
}