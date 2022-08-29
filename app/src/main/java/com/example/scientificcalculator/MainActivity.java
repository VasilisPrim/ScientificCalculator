package com.example.scientificcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button clear_btn,clear_all_btn,factorial_btn,mcd_btn,fibo_btn;
    TextView factorial,mcd,fibo;
    EditText parameters;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clear_btn  = findViewById(R.id.button4);
        clear_all_btn = findViewById(R.id.button5);
        factorial_btn = findViewById(R.id.button3);
        mcd_btn = findViewById(R.id.button);
        fibo_btn = findViewById(R.id.button2);
        factorial = findViewById(R.id.textView2);
        mcd = findViewById(R.id.textView3);
        fibo = findViewById(R.id.textView);
        parameters = findViewById(R.id.edittext1);

        //Οι listeners για τα αντίστοιχα κουμπιά.

        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearEntries();
            }
        });
        clear_all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAllEntries();
            }
        });

        factorial_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Factorial().execute(parameters.getText().toString().trim().split(","));
            }
        });

        mcd_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                new Mcd().execute(parameters.getText().toString().trim().split(","));
           }
        });

        fibo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {
                new Fibonacci().execute(parameters.getText().toString().trim().split(","));
            }
        });
    }
    //Η συνάρτηση που καθαρίζει όλα τα πεδία.
    private void clearEntries() {
        parameters.getText().clear();
    }
    private void clearAllEntries() {
        parameters.getText().clear();
        factorial.setText("");
        mcd.setText("");
        fibo.setText("");
    }
    //Η συνάρτηση που υπολογίζει το παραγοντικό.
    private int factorialCalc(int number){
        int count = 2;
        int n = 1;
        while (count<=number){
            n *= count;
            count +=1;
        }
        return n;
    }
    //Η συνάρτηση που υπολογίζει την ακολουθία fibonacci.
    private List<Integer> fibonacci(int number){
        int i=0;
        int j=1;
        List<Integer> myList = new ArrayList<>();
        myList.add(i);
        myList.add(j);
        while (myList.get(i) + myList.get(j) <=number){
        myList.add(myList.get(i) + myList.get(j));
        i+=1;
        j+=1;
        }
        return myList;
    }
    //Δύο μέθοδοι για τον υπολογισμό του μέγιστου κοινού διαιρέτη.
    private int mdcOfTwo(int a,int b){
        if (a == 0)
            return b;
        return mdcOfTwo(b % a, a);
    }
    private int mcdCalc(List<Integer> list){
        {
            int result = list.get(0);
            for (int element: list){
                result = mdcOfTwo(result, element);
                if(result == 1)
                {
                    return 1;
                }
            }
            return result;
        }
    }


    //Ακολουθούν οι τρεις κλάσεις για την πραγματοποίηση υπολογισμών στο παρασκήνιο.
    private class Factorial extends AsyncTask<String[], Void, Object> {
        private List<String> list = new ArrayList<>();
        @Override
        protected Object doInBackground(String[]... strings) {
            List<String> myList = new ArrayList<>();
            try{
            for(String elem : strings[0]){
                myList.add(String.valueOf(factorialCalc(Integer.parseInt(elem))));
            }} catch (Exception e) {
                return list.add(e.toString());
            }
            return myList;
        }
        protected  void onPostExecute(Object result){
            if (!list.isEmpty()) Toast.makeText(MainActivity.this, "Please give an integer!", Toast.LENGTH_SHORT).show();
            else factorial.setText("Factorial("+parameters.getText().toString()+")"+" = "+String.join(", ", (ArrayList)result));
        }


    }
    private class Mcd extends AsyncTask<String[], Exception, Object> {
        private List<String> list = new ArrayList<>();
        @Override
        protected Object doInBackground(String[]... strings) {
            List<Integer> myList = new ArrayList<>();
            try{
            for(String elem : strings[0]){
                myList.add(Integer.parseInt(elem));
            }} catch (Exception e) {
                return list.add(e.toString());
            }
            return myList;
        }
        protected  void onPostExecute(Object result){
            if (!list.isEmpty()) Toast.makeText(MainActivity.this, "Please give an integer!", Toast.LENGTH_SHORT).show();
            else mcd.setText("MCD("+parameters.getText().toString()+")"+" = "+mcdCalc((List<Integer>) result));
        }
    }
    private class Fibonacci extends AsyncTask<String[], Void, Object> {
        private List<String> list = new ArrayList<>();
        @Override
        protected Object doInBackground(String[]... strings) {
            List<List<Integer>> myList = new ArrayList<>();
            try{
            for(String elem : strings[0]){
                myList.add(fibonacci(Integer.parseInt(elem)));
            }} catch (Exception e) {
                return list.add(e.toString());
            }
            return myList;
        }
        protected  void onPostExecute(Object result){
            if (!list.isEmpty()) Toast.makeText(MainActivity.this, "Please give an integer!", Toast.LENGTH_SHORT).show();
            else fibo.setText("Fibonacci("+parameters.getText().toString()+")"+" = "+result.toString());
            }
        }
}
