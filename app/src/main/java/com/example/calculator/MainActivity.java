package com.example.calculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.operator.Operator;

public class MainActivity extends AppCompatActivity {

    private TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        show = findViewById(R.id.show);
        show.setText("0");

        setupClickListeners();
    }

    private void setupClickListeners() {
        setNumericButtonListener(R.id.b0, "0");
        setNumericButtonListener(R.id.b1, "1");
        setNumericButtonListener(R.id.b2, "2");
        setNumericButtonListener(R.id.b3, "3");
        setNumericButtonListener(R.id.b4, "4");
        setNumericButtonListener(R.id.b5, "5");
        setNumericButtonListener(R.id.b6, "6");
        setNumericButtonListener(R.id.b7, "7");
        setNumericButtonListener(R.id.b8, "8");
        setNumericButtonListener(R.id.b9, "9");

        setSymbolButtonListener(R.id.dot, ".");
        setSymbolButtonListener(R.id.add, "+");
        setSymbolButtonListener(R.id.subtract, "-");
        setSymbolButtonListener(R.id.multiply, "*");
        setSymbolButtonListener(R.id.divide, "/");
        setSymbolButtonListener(R.id.modulo, "%");

        Button equal = findViewById(R.id.equal);
        equal.setOnClickListener(v -> evaluateExpression());

        Button AC = findViewById(R.id.AC);
        AC.setOnClickListener(v -> show.setText("0"));

        Button bracesOpen = findViewById(R.id.bracesOpen);
        bracesOpen.setOnClickListener(v -> appendToExpression("("));

        Button bracesClose = findViewById(R.id.bracesClose);
        bracesClose.setOnClickListener(v -> appendToExpression(")"));
    }

    private void setNumericButtonListener(int buttonId, String value) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(v -> appendToExpression(value));
    }

    private void setSymbolButtonListener(int buttonId, String value) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(v -> appendToExpression(value));
    }

    private void appendToExpression(String value) {
        if (show.getText().toString().equals("0")) {
            show.setText(value);
        } else {
            show.append(value);
        }
    }

    private void evaluateExpression() {
        try {
            String expressionStr = show.getText().toString();

            // Custom operator for modulo (%)
            Operator modulo = new Operator("%", 2, true, Operator.PRECEDENCE_MODULO) {
                @Override
                public double apply(double... args) {
                    return args[0] % args[1];
                }
            };

            // Register the modulo operator
            ExpressionBuilder builder = new ExpressionBuilder(expressionStr)
                    .operator(modulo);

            // Build the expression
            Expression expression = builder.build();

            // Evaluate the expression
            double result = expression.evaluate();
            show.setText(formatResult(result)); // format the result if necessary
        } catch (Exception e) {
            show.setText("Error");
        }
    }

    // Optional: Method to format the result
    private String formatResult(double result) {
        // Example formatting
        return String.format("%.6f", result).replaceAll("\\.?0*$", "");
    }
}
