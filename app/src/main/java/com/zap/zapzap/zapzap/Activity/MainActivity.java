package com.zap.zapzap.zapzap.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zap.zapzap.zapzap.R;
import com.zap.zapzap.zapzap.helper.Config.ConfiguracaoFirebase;

public class MainActivity extends AppCompatActivity {
//private DatabaseReference referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        private Button botaosair;
        private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // referenciaFirebase.child("pontos").setValue(100);

        botaosair= (Button) findViewById(R.id.bnt_sair);

        botaosair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                autenticacao.signOut();
                Intent intent = new Intent(MainActivity.this, Login_Acitivity.class);
                startActivity(intent);


            }
        });


    }
}
