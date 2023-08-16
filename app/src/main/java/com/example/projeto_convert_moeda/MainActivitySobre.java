package com.example.projeto_convert_moeda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivitySobre extends AppCompatActivity {


    ImageView buttonInicio,buttonInfo,buttonAjuda;
    TextView infoEstudante;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sobre);

        comandosAjuda();

        infoEstudante.setText("Projeto desenvolvido por Camila Nizer Porfirio para disciplina de Prática de programação para dispositivos móveis. Professor: Leandro Pickler. ADS 3ª fase, 2023/1.");

        //ImageView para alteração de páginas.
        buttonAjuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
            }
        });
        buttonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), MainActivitySobre.class);
                startActivity(intent);
            }
        });
        buttonInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void comandosAjuda(){

        this.setTitle("Converte Fácil");
        buttonInfo=findViewById(R.id.buttonInfo);
        buttonAjuda=findViewById(R.id.buttonAjuda);
        buttonInicio=findViewById(R.id.buttonInicio);
        infoEstudante=findViewById(R.id.infoEstudante);

    }
}