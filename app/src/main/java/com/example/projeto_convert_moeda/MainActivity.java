package com.example.projeto_convert_moeda;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.StrictMode;
import java.text.DecimalFormat;
import java.util.Map;

//Importação das bibliotecas para utilização do app.

public class MainActivity extends AppCompatActivity {
    Spinner moeda_entrada, moeda_saida;
    String[] tipos_moedas= new String [] {"Selecione a moeda","ARS","AUD","BRL","CAD","CHF","DKK","EUR","GBP","MXN","USD","UYU"};
    EditText valor_converter;
    Button botao_converter;
    Double valor;
    String entrada, saida, controle;
    TextView resultado,resultadodataHoraConversao,resultadovalorCotacao,resultadoinformacaoConversao,resultadoTipoConversao;
    ImageView buttonInicio, buttonInfo, buttonAjuda;

    //Definição dos tipos de variáveis.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //permite as Threads simultâneas no Android.

        comandos();
        preencher_moeda();

        // Inicializa os métodos comandos (busca os Ids nos arquivos xml, conectando o back e front do app) e preencher_moeda (spinner)

        botao_converter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    controle=valor_converter.getText().toString();
                    entrada= moeda_entrada.getSelectedItem().toString();
                    saida= moeda_saida.getSelectedItem().toString();


                    if (controle.isEmpty()){
                        //Se o usuário não digitar valor para conversão
                        resultadoTipoConversao.setText("Insira um valor válido");
                    }

                    else if (entrada.equals("Selecione a moeda") || saida.equals("Selecione a moeda") ) {
                        //Se o usuário não selecionar moeda de entrada ou saída
                        resultadoTipoConversao.setText("Selecione uma moeda");

                    }
                    else if(entrada.equals(saida)){
                        //Se o usuário selecionar moedas de entrada e saída iguais
                        resultadoTipoConversao.setText("Selecione moedas diferentes.");
                    }
                    else{
                        valor= Double.parseDouble(valor_converter.getText().toString());

                        if(valor<=0){
                            //Se o usuário digitar valor igual o menor que zero.
                            resultadoTipoConversao.setText("Insira um valor válido.");
                        }
                        else{
                            //Executado se todos os campos forem preenchidos corretamente
                            valor= Double.parseDouble(valor_converter.getText().toString());
                            ApiCotacao api= new ApiCotacao(); //Chama a class ApiCotação
                            Map retornoApi= api.buscaApi(entrada,saida); //chama a Classe buscaApi enviando as Strings parâmetros de entrada e saída
                            Map realdolar= (Map) retornoApi.get(entrada+saida); //recebe o MAP
                            String nomeConversao= (realdolar.get("name")).toString(); //busca no MAP o valor da chave "name"
                            Double valorMoedaCotacaoAtual= Double.parseDouble(realdolar.get("bid").toString()); // busca no MAP o valor da chave bid (valor da cotação para compra)
                            String dataHoraConversao= (realdolar.get("create_date")).toString(); // busca no MAP o valor da chave "create_date" (data da conversão)
                            String tipomoedaconvertida=(realdolar.get("codein").toString()); // busca no MAP o valor da chave "codein" (hora da conversão)

                            Double valorconvertido= valorMoedaCotacaoAtual*valor;
                            DecimalFormat df= new DecimalFormat("0.00");
                            //Utilizado para formatar o valor com apenas duas casas após o ponto.

                            resultadoTipoConversao.setText(nomeConversao.toString());
                            resultadovalorCotacao.setText("Valor da cotação: "+df.format(valorMoedaCotacaoAtual).toString());
                            resultado.setText((df.format(valorconvertido)).toString()+" "+tipomoedaconvertida.toString());
                            resultado.setText((df.format(valorconvertido)).toString().replace(".",",")+" "+tipomoedaconvertida.toString()); //replace: usado para substituir o . por , no valor exibido.
                            resultadodataHoraConversao.setText("Data/hora da conversão:");
                            resultadoinformacaoConversao.setText(dataHoraConversao.toString());

                        }

                    }

                } catch (Exception erro){
                    //Se retornar erro na API informa o erro ao usuário
                    resultadoTipoConversao.setText("Cotação não disponível");
                    resultadovalorCotacao.setText("");
                    resultado.setText("");
                    resultadodataHoraConversao.setText("");
                    resultadoinformacaoConversao.setText("");


                    //Verifica se há conexão com a internet e caso não tenha informa o erro ao usuário
                    ConnectivityManager cm= (ConnectivityManager)
                            getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetwork= cm.getActiveNetworkInfo();
                    boolean isConnected=activeNetwork !=null && activeNetwork.isConnectedOrConnecting();
                    if(!isConnected){
                        resultadoTipoConversao.setText("Erro na conexão.");
                        resultadovalorCotacao.setText("");
                        resultado.setText("");
                        resultadodataHoraConversao.setText("");
                        resultadoinformacaoConversao.setText("");

                    }


                }
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
    private void comandos(){
        //renomeia o Title
        this.setTitle("Converte Fácil");

        //busca os Ids nos arquivos xml, conectando o back e front do app
        moeda_entrada= findViewById(R.id.moeda_entrada);
        moeda_saida=findViewById(R.id.moeda_saida);
        valor_converter=findViewById(R.id.valor_a_converter);
        resultado = findViewById(R.id.resultado);
        botao_converter= findViewById(R.id.botao_converter);
        resultadoTipoConversao=findViewById(R.id.resultadoTipoConversao);
        resultadovalorCotacao=findViewById(R.id.valorCotacao);
        resultadodataHoraConversao=findViewById(R.id.dataHoraConversao);
        resultadoinformacaoConversao=findViewById(R.id.informacaoConversao);
        buttonInfo=findViewById(R.id.buttonInfo);
        buttonAjuda=findViewById(R.id.buttonAjuda);
        buttonInicio=findViewById(R.id.buttonInicio);
    }
    private void preencher_moeda(){

        //Insere o array no spinner de moeda de entrada e saída

        moeda_entrada.setAdapter(new ArrayAdapter<>(
                getApplicationContext(), //Activity
                R.layout.textview_spinner,
                tipos_moedas
        ));
        moeda_saida.setAdapter(new ArrayAdapter<>(
                getApplicationContext(),
                R.layout.textview_spinner,
                tipos_moedas
        ));
    }

}