package com.example.logicalgames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

import static android.content.ContentValues.TAG;

public class MainMenu extends AppCompatActivity {
    ImageButton home, maths, shop, words;
    RadioButton text4, text2, text3, text5, text6;
    TextView next, no, yes;
    Dialog dialog, exit, registration;
    Intent intent, rules;
    EditText login, password;
    Button signIn, vhod;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    private String USER_KEY = "User";


    private DatabaseReference myRef;
    int rating;
    Button strokesButton;

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
        password = registration.findViewById(R.id.password);
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
        myRef = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getInstance().getCurrentUser();

        long rrt = getIntent().getLongExtra("rating", 0);
        rating = Integer.parseInt(String.valueOf(rrt));

        //TODO alex's firebase
        myRef = FirebaseDatabase.getInstance().getReference(USER_KEY);



        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                else Log.d(TAG, "onAuthStateChanged:signed_out");
            }
        };

        boolean f = sharedPreferences.getBoolean("flag", false);
        if (!f){
            registration.show();
            flag = true;
            editor.putBoolean("flag", true);
            editor.apply();
        }


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registration(login.getText().toString(), password.getText().toString());
            }
        });

        vhod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signing(login.getText().toString(), password.getText().toString());
            }
        });



        rules = new Intent(this, Rules.class);
        intent = new Intent(this, MainActivity.class);


        if (rating != 0){
            int r1;
            long r = Integer.parseInt(strokesButton.getText().toString());
            r1 = Integer.parseInt(String.valueOf(r)) + Integer.parseInt(String.valueOf(rating));
            strokesButton.setText(String.valueOf(r1));
            editor.putInt("rating", Integer.parseInt(strokesButton.getText().toString()));
            editor.apply();
        }else if (rating == 0){
            long l = sharedPreferences.getInt("rating", 0);
            l+=3;
            strokesButton.setText(String.valueOf(l));
        }

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
                registration.show();
            }
        });

        //User user1 = new User(user.getUid(), rating);
       // myRef.push().setValue(user1);


/*        if (user.getUid() != null) {
            myRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    GenericTypeIndicator<Integer> k = new GenericTypeIndicator<Integer>() {
                    };
                    rating = snapshot.child("rating").getValue(k);

                    updateUI();
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }*/


    }

    public void registration (String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainMenu.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                    registration.dismiss();
                }
                else Toast.makeText(MainMenu.this, "Регистрация провалена", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void signing (String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {Toast.makeText(MainMenu.this, "Вход выполнен", Toast.LENGTH_SHORT).show(); registration.dismiss();}
                else Toast.makeText(MainMenu.this, "Вход невыполнен", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateUI() {
        strokesButton.setText(getBaseContext().toString());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}