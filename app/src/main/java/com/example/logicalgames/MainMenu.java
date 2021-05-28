package com.example.logicalgames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class MainMenu extends AppCompatActivity {
    ImageButton home, maths, shop, words;
    RadioButton text4, text2, text3, text5, text6;
    TextView next, no, yes;
    Dialog dialog, exit, registration;
    Intent intent, rules;
    EditText login;
    Button signIn, vhod;

    private static final String TIME = "time";
    private static final String STROKES = "strokes";
    long rating;
    Button strokesButton;

    private DatabaseReference myDataBase;
    private String USER_KEY = "User";
    long idInt;
    boolean flag = false;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sharedPreferences = getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();

        dialog = new Dialog(MainMenu.this);
        exit = new Dialog(MainMenu.this);
        registration = new Dialog(MainMenu.this);

        dialog.setTitle("Выберите количество цифр в числе");
        dialog.setContentView(R.layout.number);
        exit.setContentView(R.layout.exit);
        registration.setContentView(R.layout.dialogregistration);

        maths = findViewById(R.id.math);
        home = findViewById(R.id.home);
        shop = findViewById(R.id.shop);
        words = findViewById(R.id.words);

        login = registration.findViewById(R.id.login);
        vhod = registration.findViewById(R.id.vhod);
        signIn = registration.findViewById(R.id.signIn);
        no = exit.findViewById(R.id.no);
        yes = exit.findViewById(R.id.yes);
        next = dialog.findViewById(R.id.next);
        text2 = dialog.findViewById(R.id.text2);
        text3 = dialog.findViewById(R.id.text3);
        text4 = dialog.findViewById(R.id.text4);
        text5 = dialog.findViewById(R.id.text5);
        text6 = dialog.findViewById(R.id.text6);

        strokesButton = findViewById(R.id.strokes);

        myDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
        Random random = new Random();
        idInt = random.nextLong();
        //editor.putBoolean("flag", flag);
        //editor.apply();
        boolean f = sharedPreferences.getBoolean("flag", false);
        if (!f){
            registration.show();
            flag = true;
            editor.putBoolean("flag", true);
            editor.apply();
        }



        rules = new Intent(this, Rules.class);
        intent = new Intent(this, MainActivity.class);

        rating = getIntent().getLongExtra("rating", 0);
        if (rating != 0){
        long r = Integer.parseInt(strokesButton.getText().toString());
        int r1 = Integer.parseInt(String.valueOf(r)) + Integer.parseInt(String.valueOf(rating));
        strokesButton.setText(String.valueOf(r1));
        editor.putInt("rating", Integer.parseInt(strokesButton.getText().toString()));
        editor.apply();
        }else {
            long l = sharedPreferences.getInt("rating", 0);
            strokesButton.setText(String.valueOf(l));
        }

        //time = getIntent().getLongExtra(TIME, 0);
        //strokes = getIntent().getIntExtra(STROKES, 1);

        //if (rating != 0)
        //    strokesButton.setText(String.valueOf(rating));


        maths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text2.isChecked()) { intent.putExtra("level", "2"); startActivity(intent);}
                if (text3.isChecked()) {intent.putExtra("level", "3"); startActivity(intent);}
                if (text4.isChecked()) {intent.putExtra("level", "4"); startActivity(intent);}
                if (text5.isChecked()) {intent.putExtra("level", "5"); startActivity(intent);}
                if (text6.isChecked()) {intent.putExtra("level", "6"); startActivity(intent);}
                else Toast.makeText(getApplicationContext(), "Выберите цифру", Toast.LENGTH_SHORT).show();


            }
        });

        words.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(rules);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit.show();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

        //getFromFB();

    }
    public void onClickRegistration(View view){
        String id = String.valueOf(idInt);
        String name = login.getText().toString();
        User user = new User(id, name, rating);
        if (!name.equals("")){
            myDataBase.push().setValue(user);
            editor.putString("login", name);
            editor.putString("id", id);
            editor.putLong("rating", rating);
            editor.apply();

            //update
            registration.cancel();
        }
        else Toast.makeText(getApplicationContext(), "Введите имя", Toast.LENGTH_SHORT).show();
    }

    public void onClickVhod (View view){
        String name = sharedPreferences.getString("login", ""),
                id = sharedPreferences.getString("id", "");
        String log = login.getText().toString();
        if (log.equals(name)){
            registration.cancel();
            getFromFB();
        }else Toast.makeText(getApplicationContext(), "введите имя еще раз", Toast.LENGTH_SHORT).show();
    }

    private void getFromFB(){
        ValueEventListener veListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /*for (DataSnapshot ds : snapshot.getChildren()){
                    User user = ds.getValue(User.class);
                    assert user != null;
                    if (user.login.equals(sharedPreferences.getString("login", "")) &&
                        user.id.equals(sharedPreferences.getString("id", ""))){
                        long r = Integer.parseInt(user.rating);
                        r += Integer.parseInt(rating);
                        strokesButton.setText(String.valueOf(r));
                        user.rating = String.valueOf(r);
                        myDataBase.child(user.login).setValue(r);
                    }
                }*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        myDataBase.addValueEventListener(veListener);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}