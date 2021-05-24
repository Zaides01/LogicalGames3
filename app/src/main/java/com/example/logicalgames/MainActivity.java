package com.example.logicalgames;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static com.example.logicalgames.R.layout.list_item;

public class MainActivity extends AppCompatActivity {
    Button deleteButton, okButton, nullButton, oneButton, twoButton, threeButton, fourButton, fiveButton, sixButton, sevenButton, eightButton, nineButton;
    TextView number;
    ListView list;
    int bulls, cows;
    int strokes = 0;

    SimpleAdapter simpleAdapter;
    LinkedList<HashMap<String, String>> mapNumber = new LinkedList<>();

    private static final String LEVEL = "level";
    String level;

    long startTime, endTime, time;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number = findViewById(R.id.number);
        deleteButton = findViewById(R.id.deleteButton);
        okButton = findViewById(R.id.okButton);
        nullButton = findViewById(R.id.nullButton);
        oneButton = findViewById(R.id.oneButton);
        twoButton = findViewById(R.id.twoButton);
        threeButton = findViewById(R.id.threeButton);
        fourButton = findViewById(R.id.fourButton);
        fiveButton = findViewById(R.id.fiveButton);
        sixButton = findViewById(R.id.sixButton);
        sevenButton = findViewById(R.id.sevenButton);
        eightButton = findViewById(R.id.eightButton);
        nineButton = findViewById(R.id.nineButton);
        list = findViewById(R.id.list);

        //TODO расчет времени игры
        startTime = System.currentTimeMillis();
        intent = new Intent(this, MainMenu.class);

        level = getIntent().getStringExtra(LEVEL);
        final RandomNumber randomNumber = new RandomNumber(Integer.parseInt(level));
        final int levelInt = Integer.parseInt(String.valueOf(randomNumber.generate()));

        String[] keyFrom = {"numberbc", "bulls", "cows"};
        int[] idTo = {R.id.numberbc, R.id.bulls, R.id.cows};
        simpleAdapter = new SimpleAdapter(this, mapNumber, list_item, keyFrom, idTo);
        list.setAdapter(simpleAdapter);
        //TODO работа с адаптером
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinkedList<String> numbers = new LinkedList();
//                if (number.getText().toString().length() == Integer.parseInt(level)){
                    if (!number.getText().toString().equals("") && number.getText().toString().length() == Integer.parseInt(level)) {
                        numbers.add(number.getText().toString());
                        bulls = numberOfBulls(levelInt, Integer.parseInt(number.getText().toString()));
                        cows = numberOfCaws(levelInt, Integer.parseInt(number.getText().toString()));
                        strokes++;
                        for (int i = 0; i < numbers.size(); i++) {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("numberbc", numbers.get(i));
                            map.put("bulls", String.valueOf(bulls));
                            map.put("cows", String.valueOf(cows));
                            mapNumber.add(map);
                        }
                        simpleAdapter.notifyDataSetChanged();
                        number.setText("");
                        nullButton.setEnabled(true);
                        oneButton.setEnabled(true);
                        twoButton.setEnabled(true);
                        threeButton.setEnabled(true);
                        fourButton.setEnabled(true);
                        fiveButton.setEnabled(true);
                        sixButton.setEnabled(true);
                        sevenButton.setEnabled(true);
                        eightButton.setEnabled(true);
                        nineButton.setEnabled(true);
                        if (String.valueOf(bulls).equals(level)){
                            onStop();
                        }
                    } else Toast.makeText(getApplicationContext(), "Число не подходит по количеству цифр", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void numbers(Button but){
        String num = but.getText().toString();
        if (!num.equals("")){
            switch (num){
                case ("0"):
                    number.append(nullButton.getText().toString());
                    nullButton.setEnabled(false);
                    break;
                case ("1"):
                    number.append(oneButton.getText().toString());
                    oneButton.setEnabled(false);break;
                case ("2"):
                    number.append(twoButton.getText().toString());
                    twoButton.setEnabled(false);break;
                case ("3"):
                    number.append(threeButton.getText().toString());
                    threeButton.setEnabled(false);break;
                case ("4"):
                    number.append(fourButton.getText().toString());
                    fourButton.setEnabled(false);break;
                case ("5"):
                    number.append(fiveButton.getText().toString());
                    fiveButton.setEnabled(false);break;
                case ("6"):
                    number.append(sixButton.getText().toString());
                    sixButton.setEnabled(false);break;
                case ("7"):
                    number.append(sevenButton.getText().toString());
                    sevenButton.setEnabled(false);break;
                case ("8"):
                    number.append(eightButton.getText().toString());
                    eightButton.setEnabled(false);break;
                case ("9"):
                    number.append(nineButton.getText().toString());
                    nineButton.setEnabled(false);break;
                case ("DEL"):
                    String str = number.getText().toString();
                    char s='a';
                    if (!str.isEmpty()){
                        s = str.charAt(str.length() - 1);
                        str = str.substring(0, str.length()-1);
                        number.setText(str);
                    }else if (str.equals(""))
                        Toast.makeText(getApplicationContext(), "Нет числа", Toast.LENGTH_SHORT).show();
                    if (s == '0')
                        nullButton.setEnabled(true);
                    if (s == '1')
                        oneButton.setEnabled(true);
                    if (s == '2')
                        twoButton.setEnabled(true);
                    if (s == '3')
                        threeButton.setEnabled(true);
                    if (s == '4')
                        fourButton.setEnabled(true);
                    if (s == '5')
                        fiveButton.setEnabled(true);
                    if (s == '6')
                        sixButton.setEnabled(true);
                    if (s == '7')
                        sevenButton.setEnabled(true);
                    if (s == '8')
                        eightButton.setEnabled(true);
                    if (s == '9')
                        nineButton.setEnabled(true);
                    break;
            }
        }
    }

    public void onClick (View v){
        numbers ((Button) v);
    }

    public int numberOfBulls(int originalNumber, int enteredNumber){
        int k = 0;
        while (originalNumber != 0){
            if (originalNumber % 10 == enteredNumber % 10)
                k++;
            originalNumber /= 10;
            enteredNumber /= 10;
        }
        return k;
    }

    public int numberOfCaws (int originalNumber, int enteredNumber){
        int k = 0;
        String s = String.valueOf(originalNumber);
        String ss = String.valueOf(enteredNumber);
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < ss.length(); j++) {
                if (i != j){
                    if (s.charAt(i) == ss.charAt(j))
                        k++;
                }
            }
        }
        return k;
    }

    @Override
    protected void onStop() {
        super.onStop();
        endTime = System.currentTimeMillis();
        time = endTime - startTime;
        time /= 6000;
        intent.putExtra("time", time);
        intent.putExtra("strokes", strokes);
        startActivity(intent);
    }
}