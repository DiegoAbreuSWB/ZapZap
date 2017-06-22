package com.zap.zapzap.zapzap.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.zap.zapzap.zapzap.Manifest;
import com.zap.zapzap.zapzap.Model.Usuario;
import com.zap.zapzap.zapzap.R;
import com.zap.zapzap.zapzap.helper.Config.ConfiguracaoFirebase;
import com.zap.zapzap.zapzap.helper.Permissao;
import com.zap.zapzap.zapzap.helper.Preferencias;

import java.util.HashMap;
import java.util.Random;

public class Login_Acitivity extends AppCompatActivity {
    private EditText email;
    private  EditText senha;
    private  Button btn_logar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

   // private DatabaseReference referenciaFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__acitivity);

     //   referenciaFirebase= ConfiguracaoFirebase.getFirebase();
       // referenciaFirebase.child("pontos").setValue("800");
        verificarUsuarioLogado();

        email = (EditText) findViewById(R.id.login_email_id);
        senha= (EditText) findViewById(R.id.login_senha_id);
        btn_logar= (Button) findViewById(R.id.btn_logar);
        btn_logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario= new Usuario();
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                validarLogin();
            }
        });



    }
    private void verificarUsuarioLogado(){
        autenticacao=ConfiguracaoFirebase.getFirebaseAutenticacao();
        if(autenticacao.getCurrentUser() !=null ){
            abrirTelaPrincipal();
        }

    }
    private void validarLogin(){
    autenticacao= ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    abrirTelaPrincipal();


                    Toast.makeText(Login_Acitivity.this,"Sucesso ao fazer Login",Toast.LENGTH_LONG).show();}

                else{                Toast.makeText(Login_Acitivity.this,"ERRO ! ao fazer Login",Toast.LENGTH_LONG).show();
                }

            }
        });
    }



public void abrir_cadastro_usuario(View view){
    Intent intent = new Intent(Login_Acitivity.this,CasdastroUsuarioAtv.class);
        startActivity(intent);
}


private void abrirTelaPrincipal(){
    Intent intent = new Intent(Login_Acitivity.this,MainActivity.class);
    startActivity(intent);
    finish();
}


}



















/*
public class Login_Acitivity extends AppCompatActivity {
    private EditText nome;
    private EditText telefone;
    private EditText  cod_pais;
    private EditText cod_area;
    private Button   cadastrar;
    private String[] permissoes_necessarias= new String[]{

            android.Manifest.permission.SEND_SMS,
            android.Manifest.permission.INTERNET
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__acitivity);

        Permissao.valida_permissoes(1,this,permissoes_necessarias);


        telefone = (EditText) findViewById(R.id.txt_telefone_id);
        nome = (EditText) findViewById(R.id.txt_nome_id);
        cod_pais = (EditText) findViewById(R.id.txt_cod_pais_id);
        cod_area = (EditText) findViewById(R.id.txt_cod_area_id);
        cadastrar = (Button) findViewById(R.id.btn_cadastrar);


        SimpleMaskFormatter simple_mask_telefone = new SimpleMaskFormatter("NNNNN-NNNN");
        MaskTextWatcher mask_telefone = new MaskTextWatcher(telefone, simple_mask_telefone);

        SimpleMaskFormatter simple_mask_cod_pais = new SimpleMaskFormatter("+NN");
        MaskTextWatcher mask_cod_pais = new MaskTextWatcher(cod_pais, simple_mask_cod_pais);

        SimpleMaskFormatter simple_mask_cod_area = new SimpleMaskFormatter("NN");
        MaskTextWatcher mask_cod_area = new MaskTextWatcher(cod_area, simple_mask_cod_area);

        telefone.addTextChangedListener(mask_telefone);
        cod_area.addTextChangedListener(mask_cod_area);
        cod_pais.addTextChangedListener(mask_cod_pais);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nome_usuario = nome.getText().toString();
                String telefone_completo =
                        cod_pais.getText().toString() +
                                cod_area.getText().toString() +
                                telefone.getText().toString();
                String telefone_sem_formatacao = telefone_completo.replace("+", "");
                telefone_sem_formatacao = telefone_sem_formatacao.replace("-", "");
                Log.i("TELEFONE", "i:" + telefone_sem_formatacao);
                //Gerar Token
                Random randomico = new Random();
                int numero_randomico = randomico.nextInt((9999 - 1000) + 1000); //entre 1.000 e 9.999
                String token = String.valueOf(numero_randomico);
                String mensagem_envio= "ZapZap Codigo de confirmacao : "+token;

//                Log.i("TOKEN","i:"+token);

                //Salvar os dados para validacao
                Preferencias preferencias = new Preferencias(Login_Acitivity.this);
                preferencias.salvar_usuario_preferencias(nome_usuario, telefone_sem_formatacao, token);
                /* Ver no log
                HashMap<String,String> usuario=preferencias.get_dados_usuario();
                Log.i("TOKEN",usuario.get("token"));
                Log.i("nome",usuario.get("nome"));
                Log.i("TELEFONE",usuario.get("telefone"));
                */
/*
                telefone_sem_formatacao ="5554";
                boolean enviado_SMS= enviarSMS("+"+telefone_sem_formatacao,mensagem_envio);

                if (enviado_SMS){
                    //Ai passsar pra proxima activity
                    Intent intent = new Intent(Login_Acitivity.this,Validador.class);
                    startActivity(intent);
                    finish();



                }else {
                    Toast.makeText(Login_Acitivity.this,"Problema ao enviar SMS, tente novamente !!!",Toast.LENGTH_LONG).show();
                }







            }
        });



        }

    //Envio de SMS
    private Boolean enviarSMS (String telefone, String mensagens){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone,null,mensagens,null,null);
            return true;

        }catch ( Exception e){
            e.printStackTrace();
            return false;
        }

                            }

     public  void onRequestPermissionsResult(int request_code, String[] permissions, int[] grant_results){

         super.onRequestPermissionsResult(request_code,permissions,grant_results);
         for(int resultado:grant_results){
             if(resultado== PackageManager.PERMISSION_DENIED){
                    alerta_validacao_permissao();
             }
         }
                      }

    private void alerta_validacao_permissao(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Permissoes Negadas");
        builder.setMessage("Para utilizar esse aplicativo, é necesario aceitar as permissoes");
        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
*/