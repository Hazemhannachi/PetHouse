package com.example.pethouse;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddPet extends AppCompatActivity {

        EditText nomPet,age,sexe,image,categorie,tel;
        Button btnAdd,btnBack ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        nomPet =(EditText)findViewById(R.id.txtname);
        age =(EditText)findViewById(R.id.age);
        sexe =(EditText)findViewById(R.id.sexe);
        image =(EditText)findViewById(R.id.image);
        categorie =(EditText)findViewById(R.id.categorie);
        tel =(EditText)findViewById(R.id.tel);

        btnAdd =(Button)findViewById(R.id.btnAdd);
        btnBack=(Button)findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });

    }
    private void insertData(){
        Map<String,Object> map = new HashMap<>();
        map.put("nomPet",nomPet.getText().toString());
        map.put("age",age.getText().toString());
        map.put("sexe",sexe.getText().toString());
        map.put("categorie",categorie.getText().toString());
        map.put("telephone",tel.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Pets").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddPet.this," Pet add successfully",Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddPet.this," Erreur while Insertion",Toast.LENGTH_SHORT).show();

                    }
                });

    }
}