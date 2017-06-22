package com.zap.zapzap.zapzap.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.zap.zapzap.zapzap.R;
import com.zap.zapzap.zapzap.helper.Preferencias;

import java.util.HashMap;

public class Validador extends AppCompatActivity {
        private EditText codigo_validacao;
        private Button validar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        codigo_validacao = (EditText) findViewById(R.id.txt_codigo_validar_id);
        validar = (Button) findViewById(R.id.btn_verificar_id);

        SimpleMaskFormatter simple_mask_cod_validacao = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher mask_cod_validacao = new MaskTextWatcher(codigo_validacao , simple_mask_cod_validacao);

        codigo_validacao.addTextChangedListener(mask_cod_validacao);

        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Recuperar dados das preferencias do Usuarios
                Preferencias preferencias = new Preferencias( Validador.this);
                HashMap<String,String> usuario = preferencias.get_dados_usuario();
                String token_gerado = usuario.get("token");
                String token_digitado =codigo_validacao.getText().toString();
                if(token_digitado.equals(token_gerado)){
                    Toast.makeText(Validador.this,"Token Validado!",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(Validador.this,"Token Incorreto!",Toast.LENGTH_LONG).show();
                }
            }
        });



    }
}
