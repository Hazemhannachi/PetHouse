package com.example.pethouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://pethouse-67da0-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText nom = findViewById(R.id.nom);
        final EditText prenom = findViewById(R.id.prenom);
        final EditText login = findViewById(R.id.login);
        final EditText adresse = findViewById(R.id.adresse);
        final EditText mdp = findViewById(R.id.mdp);

        final Button btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nomtxt = nom.getText().toString();
                final String prenomtxt = prenom.getText().toString();
                final String logintxt = login.getText().toString();
                final String adressetxt = adresse.getText().toString();
                final String mdptxt = mdp.getText().toString();

                if (nomtxt.isEmpty() || prenomtxt.isEmpty() || logintxt.isEmpty()|| adressetxt.isEmpty() ||mdptxt.isEmpty() ){
                    Toast.makeText(Register.this,"Svp remplir tous les champs ",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.hasChild(logintxt)){
                                Toast.makeText(Register.this,"Login deja existant ",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                databaseReference.child("users").child(logintxt).child("nom").setValue(nomtxt);
                                databaseReference.child("users").child(logintxt).child("prenom").setValue(prenomtxt);
                                databaseReference.child("users").child(logintxt).child("adresse").setValue(adressetxt);
                                databaseReference.child("users").child(logintxt).child("mdp").setValue(mdptxt);

                                Toast.makeText(Register.this,"user ajouteé avec succeé",Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });

    }
}