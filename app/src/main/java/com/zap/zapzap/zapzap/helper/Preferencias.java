package com.zap.zapzap.zapzap.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by diegoabreu on 20/06/2017.
 */

public class Preferencias {

    private Context contexto;
    private SharedPreferences preferences;
    private String NOME_ARQUIVO= "curso_android";
    private  int MODE=0;
    private  String CHAVE_NOME="nome";
    private  String CHAVE_TELEFONE="telefone";
    private  String CHAVE_TOKEN="token";


    private  SharedPreferences.Editor editor;

    public Preferencias(Context context_parametros){

        contexto= context_parametros;
        preferences= contexto.getSharedPreferences(NOME_ARQUIVO,MODE);

            editor= preferences.edit();

    }
    public void salvar_usuario_preferencias(String nome, String telefone, String token){

        editor.putString(CHAVE_NOME,nome);
        editor.putString(CHAVE_TELEFONE,telefone);
        editor.putString(CHAVE_TOKEN,token);
        editor.commit();




    }
public HashMap<String, String> get_dados_usuario (){
    HashMap<String, String> dados_usuario = new HashMap<>();
    dados_usuario.put(CHAVE_NOME,preferences.getString(CHAVE_NOME,null));
    dados_usuario.put(CHAVE_TELEFONE,preferences.getString(CHAVE_TELEFONE,null));
    dados_usuario.put(CHAVE_TOKEN,preferences.getString(CHAVE_TOKEN,null));
    return dados_usuario;


}



}
