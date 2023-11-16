package com.example.logbookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Calculator extends AppCompatActivity {

    private TextView display;
    private Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    private Button buttonAdd, buttonSub, buttonDiv, buttonMul, buttonClear, buttonEql;

    private String operation = "";
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        display = findViewById(R.id.textView5);
        button0 = findViewById(R.id.button23);
        buttonClear = findViewById(R.id.button21);
        buttonSub = findViewById(R.id.button22);
        buttonEql = findViewById(R.id.button20);
        button2 = findViewById(R.id.button17);
        button3 = findViewById(R.id.button18);
        button1 = findViewById(R.id.button16);
        buttonMul = findViewById(R.id.button14);
        button5 = findViewById(R.id.button12);
        button4 = findViewById(R.id.button13);
        button6 = findViewById(R.id.button15);
        buttonDiv = findViewById(R.id.button8);
        button9 = findViewById(R.id.button6);
        button8 = findViewById(R.id.button7);
        button7 = findViewById(R.id.button5);
        buttonAdd = findViewById(R.id.button19);
        buttonEql = findViewById(R.id.button20);

        // button listeners
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText() + "0");
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText() + "1");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText() + "2");
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText() + "3");
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText() + "4");
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText() + "5");
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText() + "6");
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText() + "7");
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText() + "8");
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText(display.getText() + "9");
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    display.setText(display.getText() + " + ");
                    operation = "ADD";
                    flag = false;
                } else {
                    showToast("Operation already selected.");
                }
            }
        });

        buttonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    display.setText(display.getText() + " - ");
                    operation = "SUB";
                    flag = false;
                } else {
                    showToast("Operation already selected.");
                }
            }
        });

        buttonDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    display.setText(display.getText() + " / ");
                    operation = "DIV";
                    flag = false;
                } else {
                    showToast("Operation already selected.");
                }
            }
        });

        buttonMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    display.setText(display.getText() + " * ");
                    operation = "MUL";
                    flag = false;
                } else {
                    showToast("Operation already selected.");
                }
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText("");
                operation = "";
                flag = true;
            }
        });

        buttonEql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (operation) {
                    case "ADD":
                        String input = display.getText().toString();
                        String [] numStrArr = input.split("\\s*\\+\\s*");
                        int firstNum = Integer.parseInt(numStrArr[0]);
                        int secondNum = Integer.parseInt(numStrArr[1]);
                        display.setText(String.valueOf(firstNum + secondNum));
                        break;
                    case "SUB":
                        String input2 = display.getText().toString();
                        String [] numStrArr2 = input2.split("\\s*\\-\\s*");
                        int firstNum2 = Integer.parseInt(numStrArr2[0]);
                        int secondNum2 = Integer.parseInt(numStrArr2
                                [1]);
                        display.setText(String.valueOf(firstNum2 - secondNum2));
                        break;
                    case "MUL":
                        String input3 = display.getText().toString();
                        String [] numStrArr3 = input3.split("\\s*\\*\\s*");
                        int firstNum3 = Integer.parseInt(numStrArr3[0]);
                        int secondNum3 = Integer.parseInt(numStrArr3[1]);
                        display.setText(String.valueOf(firstNum3 * secondNum3));
                        break;
                    case "DIV":
                        String input4 = display.getText().toString();
                        String [] numStrArr4 = input4.split("\\s*\\/\\s*");
                        double firstNum4 = Double.parseDouble(numStrArr4[0]);
                        double secondNum4 = Double.parseDouble(numStrArr4[1]);

                        if (secondNum4 == 0) {
                            showToast("Cannot divide by zero!");
                            display.setText("Cannot divide by zero!");
                            break;
                        }

                        Log.d("CALC", firstNum4 / secondNum4 + "");

                        display.setText((firstNum4 / secondNum4) + "");
                        break;
                }
            }
        });

    }

    protected void showToast(String msg) {
        // Create a toast message
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), msg, duration);

        // Show the toast
        toast.show();
    }


}