package com.example.electricitybills;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView TotalChg;
    Button Calc;
    EditText kWh;
    TextView Rebate;
    Button Clear;
    TextView Warning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);



        TotalChg = findViewById(R.id.TotalChg);
        Calc = findViewById(R.id.Calc);
        kWh = findViewById(R.id.kWh);
        Rebate = findViewById(R.id.Rebate);
        Clear = findViewById(R.id.Clear);
        Warning = findViewById(R.id.Warning);

        Calc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String kwh = kWh.getText().toString();
                String rebate = Rebate.getText().toString();
                try{
                    double Kwh = Double.parseDouble(kwh);
                    double Rebate = Double.parseDouble(rebate);

                    if(Rebate>5){
                        Warning.setText("Please insert rebate between 0-5");
                        return;
                    }

                    double rebate2 = Rebate/100;
                    double FinalTotal=0;

                    if(Kwh <= 200)
                        FinalTotal += Kwh*0.218;
                    else if(Kwh <= 300){
                        FinalTotal += 200*0.218;
                        FinalTotal += (Kwh-200)*0.334;
                    }else if(Kwh <= 600){
                        FinalTotal += 200*0.218;
                        FinalTotal += 100*0.334;
                        FinalTotal += (Kwh-300)*0.516;
                    }else{
                        FinalTotal += 200*0.218;
                        FinalTotal += 100*0.334;
                        FinalTotal += 300*0.516;
                        FinalTotal += (Kwh-600)*0.546;
                    }

                    FinalTotal -= (FinalTotal*rebate2);

                    TotalChg.setText("RM"+String.format("%.2f", FinalTotal));
                    Warning.setText("");
                }catch(NumberFormatException nfe){
                    Warning.setText("Please insert number");
                    Toast.makeText(getApplicationContext(), "Please insert number",Toast.LENGTH_SHORT);
                }
            }
        });

        Clear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                TotalChg.setText("Lets calculate your bills again");
            }
        });


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selected = item.getItemId();

        if(selected == R.id.menuAbout){
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
        }
        return super.onOptionsItemSelected(item);

    }
}