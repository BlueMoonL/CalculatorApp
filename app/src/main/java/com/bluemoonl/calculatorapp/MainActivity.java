package com.bluemoonl.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bluemoonl.calculatorapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    boolean isFirstInput = true;
    boolean isOperatorClick = false;
    double resultNumber = 0;
    double inputNumber = 0;
    String operator = "＝";
    String lastOperator = "＋";
    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
    }

    public void numButtonClick(View view) {

        String getButtonText = view.getTag().toString();

        if(isFirstInput) {
            activityMainBinding.resultTextView.setText(getButtonText);
            isFirstInput = false;
            if(operator.equals("＝")) {
                activityMainBinding.mathTextView.setText(null);
                isOperatorClick = false;
            }
        }
        else {
            if(activityMainBinding.resultTextView.getText().toString().equals("0")) {
                Toast.makeText(this, "'0'으로 시작되는 숫자는 없습니다.", Toast.LENGTH_SHORT).show();
                isFirstInput = true;
            }
            else {
                activityMainBinding.resultTextView.append(getButtonText);
            }
        }
    }

    public void allClearButtonClick (View view) {

        activityMainBinding.resultTextView.setText("0");
        activityMainBinding.mathTextView.setText("");
        resultNumber = 0;
        operator = "＝";
        isFirstInput = true;
        isOperatorClick = false;
    }

    public void pointButtonCLick(View view) {

        if(isFirstInput) {
            if(operator.equals("＝")) {
                operator = view.getTag().toString();
                resultNumber = Double.parseDouble(activityMainBinding.resultTextView.getText().toString());
                activityMainBinding.mathTextView.setText(resultNumber + " " + operator + " ");
            }
            activityMainBinding.resultTextView.setText("0" + view.getTag().toString());
            isFirstInput = false;

        }
        else {
            if(activityMainBinding.resultTextView.getText().toString().contains(".")) {
                Toast.makeText(this, "이미 소숫점이 존재합니다.", Toast.LENGTH_SHORT).show();
            }
            else {
                if(operator.equals("＝")) {
                    Toast.makeText(this, "연산자를 선택해 주세요.", Toast.LENGTH_SHORT).show();
                    activityMainBinding.mathTextView.setText(resultNumber + " ");
                }
                else {
                    activityMainBinding.resultTextView.append(view.getTag().toString());
                }
            }
        }
    }

    public void operaterClick(View view) {

        isOperatorClick = true;
        lastOperator = view.getTag().toString();

        if(isFirstInput) {
            if(operator.equals("＝")) {
                operator = view.getTag().toString();
                resultNumber = Double.parseDouble(activityMainBinding.resultTextView.getText().toString());
                activityMainBinding.mathTextView.setText(resultNumber + " " + operator + " ");
            }
            else {
                operator = view.getTag().toString();
                String getMathText = activityMainBinding.mathTextView.getText().toString();
                String subString = getMathText.substring(0, getMathText.length() - 2);
                activityMainBinding.mathTextView.setText(subString);
                activityMainBinding.mathTextView.append(operator + " ");

            }
        }
        else {
            inputNumber = Double.parseDouble(activityMainBinding.resultTextView.getText().toString());

            resultNumber = calculator(resultNumber, inputNumber, operator);

            activityMainBinding.resultTextView.setText(String.valueOf(resultNumber));
            isFirstInput = true;
            operator = view.getTag().toString();

            activityMainBinding.mathTextView.append(inputNumber + " " + operator + " ");
        }
    }

    public void equalsButtonClick(View view) {

        if(isFirstInput) {
            if(isOperatorClick && lastOperator != "＝") {
                activityMainBinding.mathTextView.setText(resultNumber + " " + lastOperator + " " + inputNumber + " ＝ ");
                resultNumber = calculator(resultNumber, inputNumber, lastOperator);
                activityMainBinding.resultTextView.setText(String.valueOf(resultNumber));
            }
            else if (lastOperator.equals("＝")) {
                activityMainBinding.mathTextView.setText(resultNumber + " " + lastOperator + " ");
                Toast.makeText(this, "사칙 연산이 아닙니다.", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            inputNumber = Double.parseDouble(activityMainBinding.resultTextView.getText().toString());

            resultNumber = calculator(resultNumber, inputNumber, operator);

            activityMainBinding.resultTextView.setText(String.valueOf(resultNumber));
            isFirstInput = true;
            operator = view.getTag().toString();

            activityMainBinding.mathTextView.append(inputNumber + " " + operator + " ");
        }
    }

    public void backspaceButtonClick (View view) {

        if(!isFirstInput) {
            String getResultText = activityMainBinding.resultTextView.getText().toString();
            if(getResultText.length() > 1) {
                String subString = getResultText.substring(0, getResultText.length() - 1);
                activityMainBinding.resultTextView.setText(subString);
            }
            else {
                activityMainBinding.resultTextView.setText("0");
                isFirstInput = true;
            }
        }
    }

    private double calculator(double resultNumber, double inputNumber, String operator) {

        switch (operator) {
            case "＝":
                resultNumber = inputNumber;
                break;
            case "＋":
                resultNumber = resultNumber + inputNumber;
                break;
            case "－":
                resultNumber = resultNumber - inputNumber;
                break;
            case "×":
                resultNumber = resultNumber * inputNumber;
                break;
            case "÷":
                resultNumber = resultNumber / inputNumber;
                break;
        }
        return resultNumber;
    }
}