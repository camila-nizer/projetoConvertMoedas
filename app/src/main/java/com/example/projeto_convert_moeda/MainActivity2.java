package com.example.projeto_convert_moeda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    TextView perguntaUm,perguntaDois,respostaUm,respostaDois;
    ImageView buttonInicio,buttonInfo,buttonAjuda;
    Button cotacoesDisponiveis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        comandosAjuda();

        perguntaUm.setText("O que significa 'Erro na conexão'?");
        respostaUm.setText("Este aplicativo funciona com conexão a internet para realizar as cotações. Verifique se você está conectado e tente novamente.");
        perguntaDois.setText("O que significa 'Cotação não disponível'?");
        respostaDois.setText("Algumas cotações não são suportadas pela API. Confira a lista de combinações a seguir.");

        cotacoesDisponiveis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Botão com link para as cotações disponíveis da API utilizada
                Intent cotacoesDisponiveis = new Intent(android.content.Intent.ACTION_VIEW);
                cotacoesDisponiveis.setData(Uri.parse("https://economia.awesomeapi.com.br/xml/available"));
                startActivity(cotacoesDisponiveis);
            }
        });

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




    //busca os Ids nos arquivos xml, conectando o back e front do app
    private void comandosAjuda(){

        this.setTitle("Converte Fácil");
        perguntaUm= findViewById(R.id.perguntaUm);
        perguntaDois=findViewById(R.id.perguntaDois);
        respostaUm=findViewById(R.id.respostaUm);
        respostaDois = findViewById(R.id.respostaDois);
        buttonInfo=findViewById(R.id.buttonInfo);
        buttonAjuda=findViewById(R.id.buttonAjuda);
        buttonInicio=findViewById(R.id.buttonInicio);
        cotacoesDisponiveis=findViewById(R.id.cotacoesDisponiveis);

    }
}