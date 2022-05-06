package com.example.calculatorInJava;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.calculatorInJava.R;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button[] batons = new Button[18];
    boolean clear=false;
    TextView t,tf1;
    String [] ids = {"btn0","btn1","btn2","btn3","btn4","btn5","btn6","btn7","btn8","btn9","btnplus"
        ,"btnsub","btnmul","btndiv","btnclr","btndot","btneq","btndel"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t= findViewById(R.id.res);
        tf1 = findViewById(R.id.equ);
        for (int i = 0; i < batons.length; i++)
        {
            int resID = getResources().getIdentifier(ids[i], "id", getPackageName());
            batons[i] =  ((Button)findViewById(resID));
            batons[i].setTextColor(Color.WHITE);
            batons[i].setTextSize(25);
            batons[i].setOnClickListener(this);
            if(i<10)
                batons[i].setBackgroundColor(Color.BLACK);
            else if(i<16)
                batons[i].setBackgroundColor(Color.GRAY);
            else
                batons[i].setBackgroundColor(Color.RED);
        }
    }
    @Override
    public void onClick(View view) {
            Stack<Character> s;
            Button btn = (Button)view;
            String actionStr = btn.getText().toString();
            if(view.getId()  == R.id.btnclr)
            {
                t.setText("");
                tf1.setText("");
                return;
            }
            String len = tf1.getText() + actionStr;
            //mx length of exp should be 50
            if(len.length() > 50)
                return;
            String caseNum = actionStr;
            if(actionStr.length() == 1)
            {
                char c = actionStr.charAt(0);
                int length = ((String) t.getText()).length(), i = length;
                String str = (String) t.getText();
                //to avoid double decimal in single number
                while (i>0) {
                    if (str.charAt(i - 1) == '.' && c == '.')
                        return;
                    if (!Character.isDigit(str.charAt(i - 1)))
                        break;
                    i--;
                }
            if(Character.isDigit(c))
                caseNum = "1";
            else if(c == '.')
                caseNum = "2";
            else if(c == '=')
                caseNum = "3";
            else
                caseNum = "6";
            }
            switch(caseNum) {
                case "1": {
                    operand(actionStr);
                    break;
                }
                case "2": {
                    String str = (String) t.getText();
                    boolean b = false;
                    //if a operator exists at last and . is entered, append 0
                    //if 4+ exits . is pressed make it 4+0.
//                    String str1 = (String)tf1.getText();
//                    char text = str1.charAt(str1.length()-1);
//                    if (!Character.isDigit(text))
//                    {
//                        tf1.setText(tf1.getText()+"0");
//                        t.setText(t.getText()+"0");
//                    }
                    if (str.length() > 0)
                        b = Character.isDigit(str.charAt(str.length() - 1));
                    if (str == "" || !b) {
                        t.setText(((String) t.getText()).concat("0"));
                        tf1.setText(((String) tf1.getText()).concat("0"));
                    }
                    t.setText(((String) t.getText()).concat("."));
                    tf1.setText(((String) tf1.getText()).concat("."));
                    break;
                }
                case "3":   ///for =
                {
                    tf1.setText("");
                    String before = (String) t.getText();
                    String ret = solve(before);
                    t.setText(ret);
                    if (!ret.equals(before))
                        clear = true;
                    break;
                }
                case "6": //for + - * /
                {
                    operators(actionStr);
                    break;
                }
                case "DEL": {
                    String str = (String) t.getText();
                    int l = str.length();
                    if (l > 0) l--;
                    str = str.substring(0, l);
                    t.setText(str);
                    str = (String) tf1.getText();
                    l = str.length();
                    if (l > 0) l--;
                    str = str.substring(0, l);
                    tf1.setText(str);
                    break;
                }

            }
    }

    boolean clearIt(boolean clear)
    {
        if(clear)
            t.setText("");
        return false;
    }
    void operators(String s)
    {
        clear = false;
        String str1 = (String)tf1.getText();
        char text = str1.charAt(str1.length()-1);
       // if last char is decimal append zero to it before next operator
        if (text == '.')
        {
            tf1.setText(tf1.getText()+"0");
            t.setText(t.getText()+"0");
       }

        if("".equals(t.getText()))
        {
            t.setText("0");
        }
        if("".equals(str1))
        {
            if(t.getText() != "")
            {
                tf1.setText(t.getText());
            }
        }
        String str = (String)t.getText();
        boolean b =false;
        if(str.length() > 0 )
        {
            boolean negate = "-".equals(str.charAt(str.length()-1)) && "-".equals(str.charAt(str.length()-2));
            if(Character.isDigit(str.charAt(str.length()-1)) || str.charAt(str.length()-1) == ')')
                b=true;
        }
        if(!b && str.length() > 0 )
            t.setText(str.substring(0,str.length()-1));
        t.setText(solve((String)t.getText()));
        t.setText(((String) t.getText()).concat(s));
        //resolving doubling of operator in upper field
        String tf11 = (String)tf1.getText();
        if (!Character.isDigit(tf11.charAt(tf11.length()-1)))
        {
            tf1.setText(tf11.substring(0,tf11.length()-1));
        }
        tf1.setText(tf1.getText()+s);
    }
    void operand(String s)
    {
        clear = clearIt(clear);
        if(t.getText().toString().matches("0"))
            t.setText("");
        t.setText(((String) t.getText()).concat(s));
        tf1.setText(tf1.getText()+s);
    }
    public static boolean isNumeric(String str)
    {
        for(int i =0;i < str.length();i++)
        {
            char c = str.charAt(i);
            if(c!='.')
            {
                if(!Character.isDigit(c))
                    return false;
            }
        }
        return true;
    }
    String solve(String x)
    {
        Stack<Character> cs;
        cs = new Stack<Character>();
        Stack<Double> is;
        is = new Stack<Double>();
            for(int i=0;i<x.length();i++)
            {
                char c = x.charAt(i);
                String num = "";
                int index = i;
                while(Character.isDigit(x.charAt(index)) || x.charAt(index) == '.')
                {
                    index++;
                    if(index == x.length())
                        break;
                }
                if(i!=index)
                {
                    num = x.substring(i,index);
                    i = index-1;
                }
                else
                    num = x.substring(i);
                boolean b = isNumeric(num);
                if(b)
                {
                    double op;
                    op = Double.parseDouble(num);
                    is.push(op);
                }
                else if( num != ")" || num!= "(")
                    cs.push(c);

            }
            if(!cs.isEmpty() && is.size()>=2)
            {
                char Operator_ = cs.pop();
                double operand1 , operand2=0;
                operand2 = is.pop();
                operand1 = is.pop();
                double res=0;
                if (Operator_ == '+')
                    res = (operand1 + operand2);
                else if (Operator_ == '-')
                    res = (operand1 - operand2);
                else if (Operator_ == '*')
                    res = (operand1 * operand2);
                else if (Operator_ == '/')
                {
                    if(operand2 != 0)
                        res = (operand1 / operand2);
                }
                String r = Double.toString(res);
                return r;
            }
        return x;
    }

}


//FIXEX TO DO
//2-DOUBLING OF OPRTOR IN UPER FILED