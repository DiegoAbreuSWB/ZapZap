package com.zap.zapzap.zapzap.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.zap.zapzap.zapzap.Model.Usuario;
import com.zap.zapzap.zapzap.R;
import com.zap.zapzap.zapzap.helper.Config.ConfiguracaoFirebase;

public class CasdastroUsuarioAtv extends AppCompatActivity {
    private EditText nome;
    private EditText email;
    private EditText senha;
    private Button botaocadastrar;
    private Usuario usuario; //classe criada
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casdastro_usuario_atv);

        nome = (EditText) findViewById(R.id.cdt_nome_id);
        email = (EditText) findViewById(R.id.cdt_email_id);
        senha = (EditText) findViewById(R.id.cdt_senha_id);
        botaocadastrar=(Button) findViewById(R.id.btn_cadastrar);

        botaocadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                usuario = new Usuario();
                usuario.setNome(nome.getText().toString());
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());

                cadastrarUsuario();

            }
        });

    }
    private void cadastrarUsuario(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(CasdastroUsuarioAtv.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if( task.isSuccessful() ){
                    Toast.makeText(CasdastroUsuarioAtv.this, "Sucesso ao cadastrar usuário", Toast.LENGTH_LONG ).show();

                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    usuario.setId( usuarioFirebase.getUid() );
                    usuario.salvar();

                    autenticacao.signOut();
                    finish();

                }else {
                    String erroExcecao="";
                 try{
                    throw task.getException();
                 } catch (FirebaseAuthWeakPasswordException e) {
                    erroExcecao="Senha Fraca! Digite uma senha com mais caracteres e contendo letras e numeros";

                 } catch (FirebaseAuthInvalidCredentialsException e) {
                     erroExcecao="O e-mail digitado é inválido. Por favor, digite um e-mail valido!!!";
                 } catch (FirebaseAuthUserCollisionException e) {
                     erroExcecao="Esse e-mail já está cadastrado no app";
                 } catch (Exception e) {
                     erroExcecao="Erro ao efetuar ao cadastro";
                     e.printStackTrace();
                 }
                    Toast.makeText(CasdastroUsuarioAtv.this, "Erro! "+ erroExcecao, Toast.LENGTH_LONG ).show();
                }
            }
        });
    }

}






