package com.example.logicalgames;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {
    ImageButton home, maths, shop, words;
    RadioButton text4, text2, text3, text5, text6;
    TextView next;
    Dialog dialog;
    Intent intent;

    private static final String TIME = "time";
    private static final String STROKES = "strokes";
    long time;
    long strokes, rating;
    Button strokesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        dialog = new Dialog(MainMenu.this);

        dialog.setTitle("Выберите количество цифр в числе");
        dialog.setContentView(R.layout.number);

        maths = findViewById(R.id.math);
        home = findViewById(R.id.home);
        shop = findViewById(R.id.shop);
        words = findViewById(R.id.words);

        next = dialog.findViewById(R.id.next);
        text2 = dialog.findViewById(R.id.text2);
        text3 = dialog.findViewById(R.id.text3);
        text4 = dialog.findViewById(R.id.text4);
        text5 = dialog.findViewById(R.id.text5);
        text6 = dialog.findViewById(R.id.text6);

        strokesButton = findViewById(R.id.strokes);

        intent = new Intent(this, MainActivity.class);
        time = getIntent().getLongExtra(TIME, 0);
        strokes = getIntent().getIntExtra(STROKES, 1);
        rating = time * strokes;
        if (rating != 0)
            strokesButton.setText(String.valueOf(rating));


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
    }

}