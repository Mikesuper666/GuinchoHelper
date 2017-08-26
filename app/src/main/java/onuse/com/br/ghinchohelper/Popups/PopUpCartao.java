package onuse.com.br.ghinchohelper.Popups;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import onuse.com.br.ghinchohelper.Helper.Preferencias;
import onuse.com.br.ghinchohelper.R;

/**
 * Created by maico on 14/08/17.
 */

public class PopUpCartao extends BottomSheetDialogFragment {
    EditText numeroCartao, mesCartao, anoCartao, cvvCartao;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.pop_fragment_cartao, container, false);

        numeroCartao = view.findViewById(R.id.numeroCartao);
        mesCartao = view.findViewById(R.id.mesCartao);
        anoCartao = view.findViewById(R.id.anoCartao);
        cvvCartao = view.findViewById(R.id.cvvCartao);

        ImageView imgVoltar = view.findViewById(R.id.imgVoltarCartao);
        imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button addCartao = view.findViewById(R.id.addCartao);
        addCartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ValidarCamposCartao()){
                    //adicionar as preferencias!!!
                    Preferencias preferencias = new Preferencias(getActivity());
                    preferencias.SalvarCartao(numeroCartao.getText().toString(),
                            mesCartao.getText().toString(),
                            anoCartao.getText().toString(),
                            cvvCartao.getText().toString());
                    onBackPressed();
                }
            }
        });

        return view;
    }

    private boolean ValidarCamposCartao(){
        boolean validador = true;

        if(numeroCartao.getText().length() < 16)
        {
            numeroCartao.setError("Por favor entre com um cartão válido");
            validador = false;
        }else
        {
            numeroCartao.setError(null);
        }

        if(mesCartao.getText().length() < 2)
        {
            mesCartao.setError("Por favor entre com um mês válido");
            validador = false;
        }else
        {
            mesCartao.setError(null);
        }

        if(anoCartao.getText().length() < 2){
            anoCartao.setError("Entre com um ano válido");
            validador = false;
        }else{
            anoCartao.setError(null);
        }

        if(cvvCartao.getText().length() < 3){
            cvvCartao.setError("Entre com um código válido");
            validador = false;
        }else{
            cvvCartao.setError(null);
        }
        return validador;
    }

    public void onBackPressed() {
        getActivity().onBackPressed();
    }
}