package com.example.artem.currencyconverter;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView textView;
    Button button;
    EditText editText;
    Spinner spinner, spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                CurrencyParse currencyParse = new CurrencyParse();
                currencyParse.execute();
                break;
            default:
                break;
        }
    }



    public class CurrencyParse extends AsyncTask<Void, Void, String> {
        String currencyFrom = spinner.getSelectedItem().toString();
        String currencyTo = spinner2.getSelectedItem().toString();
        String number = editText.getText().toString();
        int money = Integer.parseInt(number);
        public double Calculations , currencyUAH_USD, currencyUAH_EUR, currencyUAH_UAH = 1, currencyUAH_RUB;

        @Override
        protected String doInBackground(Void... params) {
            Document doc = null;
            String currency;
            try {
                doc = Jsoup.connect("https://finance.ua/ru/currency").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements h1Elements = doc.getElementsByAttributeValue("class", "c2");
            org.jsoup.nodes.Element dElement = h1Elements.get(0);
            org.jsoup.nodes.Element eElement = h1Elements.get(1);
            org.jsoup.nodes.Element rElement = h1Elements.get(2);
            String USD = dElement.text();
            String EUR = eElement.text();
            String RUB = rElement.text();
            currency = USD + " " + EUR + " " + RUB;
            Log.d("Log", currency);

        currencyUAH_USD = Double.parseDouble(USD);
        currencyUAH_EUR = Double.parseDouble(EUR);
        currencyUAH_RUB = Double.parseDouble(RUB);
            switch (currencyFrom) {
                case "USD":
                    switch (currencyTo) {
                        case "USD":
                            Calculations = money;
                            break;
                        case "EUR":
                            Calculations = (money * currencyUAH_USD) / currencyUAH_EUR;
                            break;
                        case "UAH":
                            Calculations = money * currencyUAH_USD;
                            break;
                        case "RUB":
                            Calculations = (money * currencyUAH_USD) / currencyUAH_RUB;
                            break;
                        default:
                            break;
                    }
                    break;
                case "EUR":
                    switch (currencyTo) {
                        case "USD":
                            Calculations = (money * currencyUAH_EUR) / currencyUAH_USD;
                            break;
                        case "EUR":
                            Calculations = money;
                            break;
                        case "UAH":
                            Calculations = money * currencyUAH_EUR;
                            break;
                        case "RUB":
                            Calculations = (money * currencyUAH_EUR) / currencyUAH_RUB;
                            break;
                        default:
                            break;
                    }
                    break;
                case "UAH":
                    switch (currencyTo) {
                        case "USD":
                            Calculations = (money * currencyUAH_UAH) / currencyUAH_USD;
                            break;
                        case "EUR":
                            Calculations = (money * currencyUAH_UAH) / currencyUAH_EUR;
                            break;
                        case "UAH":
                            Calculations = money;
                            break;
                        case "RUB":
                            Calculations = (money * currencyUAH_UAH) / currencyUAH_RUB;
                            break;
                        default:
                            break;
                    }
                    break;
                case "RUB":
                    switch (currencyTo) {
                        case "USD":
                            Calculations = (money * currencyUAH_RUB) / currencyUAH_USD;
                            break;
                        case "EUR":
                            Calculations = (money * currencyUAH_RUB) / currencyUAH_EUR;
                            break;
                        case "UAH":
                            Calculations = (money * currencyUAH_RUB) / currencyUAH_UAH;
                            break;
                        case "RUB":
                            Calculations = money;
                            break;
                        default:
                            break;
                    }
            }
            Log.d("Log", String.valueOf(Calculations));
            return String.valueOf(Calculations);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            textView.setText(result);
        }

    }

}
