package com.example.logicalgames;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class Registration extends AppCompatActivity {
    EditText login, password;
    Button signIn;

    private DatabaseReference myDataBase;
    private String USER_KEY = "User";
    long idInt;

    SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        login = findViewById(R.id.login);
        signIn = findViewById(R.id.signIn);

        myDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
        Random random = new Random();
        idInt = random.nextLong();
    }

    public void onClick(View view){
        String id = String.valueOf(idInt);
        String name = login.getText().toString();
        //User user = new User(id, name);
        if (!name.equals("")){
        //    myDataBase.push().setValue(user);
            editor.putString("login", name);
            editor.putString("id", id);
            editor.commit();

            //update
        }
        else Toast.makeText(getApplicationContext(), "Введите имя", Toast.LENGTH_SHORT).show();
    }
}