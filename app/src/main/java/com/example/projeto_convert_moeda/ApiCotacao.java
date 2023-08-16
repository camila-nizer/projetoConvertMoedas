package com.example.projeto_convert_moeda;

import android.util.Log;
import android.widget.TextView;

import com.example.projeto_convert_moeda.Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;


public class ApiCotacao {

    static String api_cotacao= "https://economia.awesomeapi.com.br/json/last/"; //link padrão para busca das cotações
    static int codigoSucesso=200;

    public Map buscaApi(String codigoMoedaEntrada,String codigoMoedaSaida){ //informa os parâmetros (String das moedas de entrada e saída)
        Map map= new LinkedHashMap(); //utiliza LinkedHashMap para manter a ordem

        try{
            String urlParaChamada= api_cotacao+codigoMoedaEntrada+"-"+codigoMoedaSaida; //insere as moedas selecionadas no fim do link padrão- concatenação

            //faz a chamada da API
            URL url= new URL(urlParaChamada); //envia o URL concatenado
            HttpURLConnection conexao= (HttpURLConnection) url.openConnection();

            if(conexao.getResponseCode()!= codigoSucesso){
                Log.d("com.example.projeto_convert_moeda","**********----sucesso conexão"); //conexão realizada com sucesso
            }else{

                Log.d("com.example.projeto_convert_moeda","**********---- erro conexão"); // erro na conexão
            }


            BufferedReader resposta= new BufferedReader(new InputStreamReader(conexao.getInputStream())); //Lê o texto e armazena para melhor leitura
            String jsonEmString= Util.converteJsonEmString(resposta);

            Gson gson= new Gson(); //cria um novo gson da classe gson (biblioteca do google para Json)

            map = gson.fromJson(jsonEmString, Map.class); //cria um novo map

        }catch (Exception erro){
            Log.d("com.example.projeto_convert_moeda","*******************************"+erro.toString()); //mostra no logcat o erro, para debug
        }
        return map; //retorna o map com os valores e chaves
    }


}
