package com.zap.zapzap.zapzap.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.pm.ActivityInfoCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diegoabreu on 20/06/2017.
 */

public class Permissao {
public static boolean valida_permissoes (int request_code,Activity activity, String[] permissoes){
//verficar se a versao eh maio ou igual a versao 23
                            if(Build.VERSION.SDK_INT    >=23){
                                List<String> lista_permissoes = new ArrayList<String>();

                                    for(String permissao : permissoes) {
                                        Boolean valida_permissao =ContextCompat.checkSelfPermission(  activity, permissao) == PackageManager.PERMISSION_GRANTED;
                                                if(!valida_permissao) lista_permissoes.add(permissao);
                                    }

                        if (lista_permissoes.isEmpty()) return true;

                                String[] novas_permissoes   = new String[lista_permissoes.size()];
                                lista_permissoes.toArray(novas_permissoes);

                                ActivityCompat.requestPermissions(activity,novas_permissoes,request_code);

                            }

return true;

        }


}
