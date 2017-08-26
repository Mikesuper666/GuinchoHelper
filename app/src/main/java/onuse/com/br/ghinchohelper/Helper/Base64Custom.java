package onuse.com.br.ghinchohelper.Helper;

import android.util.Base64;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by maico on 11/08/17.
 */


public class Base64Custom {

    private static int travador = 0;

    public static void Aberto(int trava){
        travador = trava;
    }

    public static boolean confirmarFragment(){
        if(travador == 0)
        {
            return true;
        }else{
            return false;
        }
    }//trava o fragmento para nao abrir um novo em cima

    public static String ConverterBase64(String texto){
        String textoConvertido = Base64.encodeToString(texto.getBytes(), Base64.DEFAULT);
        return textoConvertido.trim();
    }

    public static String DecodificarBase64(String textoCdificado){
        byte[] byteDecodificado = Base64.decode(textoCdificado, Base64.DEFAULT);
        return new String(byteDecodificado);
    }

    public static String Horario()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss");
        // OU
        //SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm:ss");

        Date data = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();

        String data_completa = dateFormat.format(data_atual);

        String hora_atual = dateFormat.format(data_atual);


        return  hora_atual;
    }
}
