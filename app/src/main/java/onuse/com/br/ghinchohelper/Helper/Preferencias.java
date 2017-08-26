package onuse.com.br.ghinchohelper.Helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by maico on 11/08/17.
 */


public class Preferencias {
    private Context contexto;
    private SharedPreferences sharedPreferences;
    private String NOME_ARQUIVO = "preferencia";
    private int MODE = 0;
    private SharedPreferences.Editor editor;

    private String CHAVE_IDENTIFICADOR = "identificadorUsuario";
    private String CHAVE_NOME = "nome";
    private String CHAVE_EMAIL = "email";
    private String CHAVE_TELEFONE = "telefone";
    private String CHAVE_CARTAO_NUMERO = "cartaoNumero";
    private String CHAVE_CARTAO_MES = "cartaoMes";
    private String CHAVE_CARTAO_ANO = "cartaoAno";
    private String CHAVE_CARTAO_CVV = "cartaoCvv";
    public Preferencias(Context contextoParametro)
    {
        contexto = contextoParametro;
        sharedPreferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = sharedPreferences.edit();
    }

    public void SalvarDados(String indentificador, String nome, String email, String telefone)
    {
        editor.putString(CHAVE_IDENTIFICADOR, indentificador);//
        editor.putString(CHAVE_NOME, nome);
        editor.putString(CHAVE_EMAIL, email);
        editor.putString(CHAVE_TELEFONE, telefone);
        editor.commit();
    }

    public void SalvarNome(String nome)
    {
        editor.putString(CHAVE_NOME, nome);//
        editor.commit();
    }

    public void SalvarEmail(String email)
    {
        editor.putString(CHAVE_EMAIL, email);//
        editor.commit();
    }

    public void SalvarTelefone(String telefone)
    {
        editor.putString(CHAVE_TELEFONE, telefone);//
        editor.commit();
    }

    public String getIndentificador()
    {
        return sharedPreferences.getString(CHAVE_IDENTIFICADOR, null);
    }//retorna quando chamado a id guardada dentro do indentificador

    public String getNome()
    {
        return sharedPreferences.getString(CHAVE_NOME, null);
    }//retorna quando chamado o nome guardada dentro do indentificador

    public String getEmail()
    {
        return sharedPreferences.getString(CHAVE_EMAIL, null);
    }//retorna quando chamado o nome guardada dentro do indentificador

    public String getTelefone()
    {
        return sharedPreferences.getString(CHAVE_TELEFONE, null);
    }//retorna quando chamado o nome guardada dentro do indentificador

    public void SalvarCartao(String numeroCartao, String mesCartao, String anoCartao, String cvvCartao)
    {
        editor.putString(CHAVE_CARTAO_NUMERO, numeroCartao);//
        editor.putString(CHAVE_CARTAO_MES, mesCartao);
        editor.putString(CHAVE_CARTAO_ANO, anoCartao);
        editor.putString(CHAVE_CARTAO_CVV, cvvCartao);
        editor.commit();
    }

    public String getCartaoNumero()
    {
        return sharedPreferences.getString(CHAVE_CARTAO_NUMERO, null);
    }//retorna quando chamado o nome guardada dentro do indentificador

    public String getCartaoMes()
    {
        return sharedPreferences.getString(CHAVE_CARTAO_MES, null);
    }//retorna quando chamado o nome guardada dentro do indentificador

    public String getCartaoAno()
    {
        return sharedPreferences.getString(CHAVE_CARTAO_ANO, null);
    }//retorna quando chamado o nome guardada dentro do indentificador

    public String getCartaoCvv()
    {
        return sharedPreferences.getString(CHAVE_CARTAO_CVV, null);
    }//retorna quando chamado o nome guardada dentro do indentificador
}