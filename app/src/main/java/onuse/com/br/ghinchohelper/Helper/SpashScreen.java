package onuse.com.br.ghinchohelper.Helper;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import onuse.com.br.ghinchohelper.LoginActivity;
import onuse.com.br.ghinchohelper.MainActivity;
import onuse.com.br.ghinchohelper.R;

/**
 * Created by maico on 11/08/17.
 */


public class SpashScreen  extends AppCompatActivity implements Runnable, ActivityCompat.OnRequestPermissionsResultCallback {
    private static final int PERMISSAO_GPS = 128;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slpashscreen);
        Handler handler = new Handler();
        handler.postDelayed(this, 1000);//Faz thread principal esperar por estes segundos aqui especificados
        AcessarPermissao();

        ImageView inicializador = (ImageView)findViewById(R.id.iniciador);
        inicializador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    menssagemPermissao("Para utilizar Guincho Helper é necessário seu sistema de GPS estar ativo com a sua permissão", new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
                } else {

                    startActivity(new Intent(getApplication(), MainActivity.class));
                    finish();
                }
            }
        });
    }

    private void AcessarPermissao(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            if(android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                //se permissao negada
                menssagemPermissao("Para utilizar Guincho Helper é necessário seu sistema de GPS estar ativo com a sua permissão", new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
            }else{
                //se permissao dada
                android.support.v4.app.ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSAO_GPS);
            }
        }
    }

    public void menssagemPermissao(String menssagem, final String[] permissao) {

        AlertDialog.Builder menssagemAlerta = new AlertDialog.Builder(this);
        menssagemAlerta.setTitle("Solicitando Permissão");
        menssagemAlerta.setMessage(menssagem);
        menssagemAlerta.setCancelable(false);
        menssagemAlerta.setPositiveButton("Concordo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                android.support.v4.app.ActivityCompat.requestPermissions(SpashScreen.this, permissao, PERMISSAO_GPS);
            }
        });
        menssagemAlerta.setNegativeButton("Sair do aplicativo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "É necessário aceitar a permissão para poder utilizar  o aplicativo", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        menssagemAlerta.show();
    }

    public void run() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {

                    } else {
                        startActivity(new Intent(getApplication(), LoginActivity.class));
                        finish();
                    }
                }//enquanto a permissao nao foi dada ele gira em looping
                startActivity(new Intent(getApplication(), LoginActivity.class));
                finish();
            }
        }).start();

    }
}
