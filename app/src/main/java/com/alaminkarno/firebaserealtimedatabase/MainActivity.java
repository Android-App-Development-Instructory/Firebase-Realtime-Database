package com.alaminkarno.firebaserealtimedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText userNameET,numberET,ageET;
    Button saveUserData;
    String name,number,age;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        saveUserData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = userNameET.getText().toString();
                number = numberET.getText().toString();
                age = ageET.getText().toString();

                if(name.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter your name first", Toast.LENGTH_SHORT).show();
                }
                else if(number.isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter your number", Toast.LENGTH_SHORT).show();
                }
                else if(age.isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter your age", Toast.LENGTH_SHORT).show();
                }
                else {
                    saveUserDateIntoDatabase();
                }

            }
        });
    }

    private void saveUserDateIntoDatabase() {

        DatabaseReference userDate = databaseReference.child("UserInfo");

        String uID = databaseReference.push().getKey();

        User user = new User(name,number,age);

        userDate.child(uID).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "User Data Added..", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        userNameET = findViewById(R.id.nameET);
        numberET = findViewById(R.id.phoneNumberET);
        ageET = findViewById(R.id.ageET);
        saveUserData = findViewById(R.id.storeUserData);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void viewUserData(View view) {
        startActivity(new Intent(MainActivity.this,RetriveDataActivity.class));
    }
}