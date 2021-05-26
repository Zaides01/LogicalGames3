package com.example.logicalgames;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Rules extends AppCompatActivity {
    TextView rules;
    Button backBut;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        rules = findViewById(R.id.rules);
        backBut = findViewById(R.id.backBut);
        intent = new Intent(this, MainMenu.class);

        backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        rules.setText("ПРАВИЛА ИГРЫ:\n" +
                "Перед тем как играть, вы загадываете количество цифр в будущем числе, которое генерирует компьютер. Ваша задача угадать это число с помощью подсказок от компьютера:\n" +
                "1.\tК - количество коров, то есть количество цифр из числа вашей попытки, которое совпадает с цифрами из задуманного числа.\n" +
                "2.\tБ - количество быков, то есть количество цифр из числа вашей попытки, которые совпадают еще и по месту расположения в задуманном числе (если есть быки, то они уже не считаются как коровы)\n" +
                "Чем меньше ходов и времени вы потратите – тем лучше ваш результат!\n" + "\n" +
                "О РАЗРАБОТЧИКАХ:\n" +
                "Мы – молодые программисты, создатели мобильных приложений. Хотим\n" +
                "развиваться в этой теме и радовать вас новыми крутыми и актуальными приложениями.\n" + "\n" +
                "Для связи с нами:\n" +
                "Зайдес Анна – zajdesnuska@gmail.com\n" +
                "Антепа Алексей – antepa.alex04@gmail.com \n");
    }
}