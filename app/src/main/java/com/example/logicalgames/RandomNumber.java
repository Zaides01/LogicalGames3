package com.example.logicalgames;

import java.time.OffsetDateTime;
import java.util.Random;

public class RandomNumber {
    int a;
    Random random = new Random();
    public RandomNumber(int a){
        this.a = a;
    }
    int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};


    public int generate(){

        int kolvo = random.nextInt(150);
        int k;
        int n1, n2;
        for (int i = 0; i < kolvo; i++) {
            n1 = random.nextInt(10);
            n2 = random.nextInt(10);
            k = numbers[n1];
            numbers[n1] = numbers[n2];
            numbers[n2] = k;
        }
        String s = "";
        for (int i = 0; i < a; i++) {
            s += String.valueOf(numbers[i]);
        }
        k = Integer.parseInt(s);
        return k;
    }
}
