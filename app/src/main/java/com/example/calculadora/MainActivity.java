package com.example.calculadora;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fathzer.soft.javaluator.DoubleEvaluator;



public class MainActivity extends AppCompatActivity {
    private TextView tvRes; // mostrar el resultat
    private StringBuilder expressio = new StringBuilder(); // ex: "33+5+15")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvRes = findViewById(R.id.tvRes);
        Button btn0 = findViewById(R.id.button0);
        Button btn1 = findViewById(R.id.button1);
        Button btn2 = findViewById(R.id.button2);
        Button btn3 = findViewById(R.id.button3);
        Button btn4 = findViewById(R.id.button4);
        Button btn5 = findViewById(R.id.button5);
        Button btn6 = findViewById(R.id.button6);
        Button btn7 = findViewById(R.id.button7);
        Button btn8 = findViewById(R.id.button8);
        Button btn9 = findViewById(R.id.button9);
        Button btnbr1 = findViewById(R.id.buttonbr1);
        Button btnbr2 = findViewById(R.id.buttonbr2);
        Button btnPlus = findViewById(R.id.buttonPlus);
        Button btnEquals = findViewById(R.id.buttonEqual);
        Button btnRest = findViewById(R.id.buttonRest);
        Button btnMult = findViewById(R.id.buttonMult);
        Button btnC = findViewById(R.id.buttonC);
        Button btnDiv = findViewById(R.id.buttonDiv);
        Button btnComa = findViewById(R.id.buttonComa);
        Button btnPc = findViewById(R.id.buttonPerc);

        // Listeners
        btn0.setOnClickListener(v -> afegirNum("0"));
        btn1.setOnClickListener(v -> afegirNum("1"));
        btn2.setOnClickListener(v -> afegirNum("2"));
        btn3.setOnClickListener(v -> afegirNum("3"));
        btn4.setOnClickListener(v -> afegirNum("4"));
        btn5.setOnClickListener(v -> afegirNum("5"));
        btn6.setOnClickListener(v -> afegirNum("6"));
        btn7.setOnClickListener(v -> afegirNum("7"));
        btn8.setOnClickListener(v -> afegirNum("8"));
        btn9.setOnClickListener(v -> afegirNum("9"));
        btnbr1.setOnClickListener(v -> operacio('('));
        btnbr2.setOnClickListener(v -> operacio(')'));
        btnPlus.setOnClickListener(v -> operacio('+'));
        btnRest.setOnClickListener(v -> operacio('-'));
        btnMult.setOnClickListener(v -> operacio('*'));
        btnC.setOnClickListener(v -> netejar());
        btnDiv.setOnClickListener(v -> operacio('/'));
        btnComa.setOnClickListener(v -> afegirNum("."));
        btnPc.setOnClickListener(v -> percentatge());
        btnEquals.setOnClickListener(v -> evaluar());

        actualitzar();
    }

    private void afegirNum(String c) {
        if(expressio.length() == 0){

        if(c.equals(".")){
            expressio.append("0.");
            }else {
                expressio.append(c);
            }
        }else{
            char Uchar = expressio.charAt(expressio.length() - 1);
            //evitem punts duplicats
            if(c.equals(".") && (Uchar == '.')){
                return;
            }
            expressio.append(c);
        }
        actualitzar();
    }

    private void operacio(char calcul) {
        // Permetre '+' només si encara no n'hi ha (evita "33++-5")
        // codi ...
        if(expressio.length() == 0){
            if(calcul == '(' ){
                expressio.append(calcul);
            }else if(calcul == ')'){
                expressio.append(calcul);
            }
        }else {
            char signe = expressio.charAt(expressio.length() - 1); //evitem signes d'operació repetits
            if(signe == '+'|| signe == '-' || signe == '*' || signe == '/' || signe == '.' || signe == '%'){
                expressio.setCharAt(expressio.length() - 1, calcul);
            } else{
                expressio.append(calcul);
            }
        }
        actualitzar();
    }

    private void evaluar() {
        // https://mvnrepository.com/artifact/com.fathzer/javaluator
        // https://github.com/fathzer/javaluator  3.0.6
        // "(2^3-1)*sin(pi/4)/ln(pi^2)" = 2.1619718020347976
        try{
            if(expressio.length() == 0){
                return;
            }

        // evaluem
        DoubleEvaluator evaluator = new DoubleEvaluator();
        Double result = evaluator.evaluate(expressio.toString());
        // mostrem resultat
        // try/catch
        tvRes.setText(result.toString());
        expressio.setLength(0);
        expressio.append(result);

        } catch (Exception e){
            tvRes.setText("Error");
            expressio.setLength(0);
        }
    }

    private void netejar(){
        expressio.setLength(0);
        tvRes.setText("");
    }

    private void percentatge(){
        if(expressio.length() > 0){
            char UltC = expressio.charAt(expressio.length() - 1);
            if(Character.isDigit(UltC) || UltC == ')'){
                expressio.append("/100");
                actualitzar();
            }
        }
    }

    private void actualitzar() {
        tvRes.setText(expressio.toString());
    }
}