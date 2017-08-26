package onuse.com.br.ghinchohelper.Helper;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;

import onuse.com.br.ghinchohelper.R;

/**
 * Created by maico on 12/08/17.
 */

public class SplashScreenLogo extends AppCompatActivity {

    Thread spalshsThread;
    AnimationDrawable animaLogo;

    public void onAttachedToWindow(){
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_logo);
        StartAnimation();
    }

    private void StartAnimation(){
        //adicionar no manifest
        // android:largeHeap="true"
        ImageView imgIntro = (ImageView)findViewById(R.id.introLogo);
        imgIntro.setBackgroundResource(R.drawable.on_use_anima);

        animaLogo = (AnimationDrawable)imgIntro.getBackground();
        animaLogo.setOneShot(true);
        animaLogo.start();

        spalshsThread = new Thread(){
            @Override
            public void run(){
                try{
                    int espere = 0;//define o tempo para zero
                    while(espere < 5500){
                        sleep(100);
                        espere += 100;
                    }
                    Intent intent = new Intent(SplashScreenLogo.this, SpashScreen.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    SplashScreenLogo.this.finish();
                }catch (InterruptedException e){
                    //faz nada
                }finally {
                    SplashScreenLogo.this.finish();
                }
            }
        };
        spalshsThread.start();
    }
}