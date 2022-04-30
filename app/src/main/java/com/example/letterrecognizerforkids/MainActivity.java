package com.example.letterrecognizerforkids;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button[] btns = new Button[18];
    Button b;
    String [] ids = {"btn0","btn1","btn2","btn3","btn4","btn5","btn6","btn7","btn8","btn9","btnplus"
        ,"btnsub","btnmul","btndiv","btnclr","btndot","btneq","btndel"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0; i < btns.length; i++)
        {
            int resID = getResources().getIdentifier(ids[i], "id", getPackageName());
            btns[i] =  findViewById(resID);
            btns[i].setTextColor(Color.WHITE);
            btns[i].setTextSize(25);
            if(i<10)
                btns[i].setBackgroundColor(Color.BLACK);
            else if(i<16)
                btns[i].setBackgroundColor(Color.GRAY);
            else
                btns[i].setBackgroundColor(Color.RED);
        }
    }
}