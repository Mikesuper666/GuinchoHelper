package onuse.com.br.ghinchohelper.Popups;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import onuse.com.br.ghinchohelper.Helper.Preferencias;
import onuse.com.br.ghinchohelper.MainActivity;
import onuse.com.br.ghinchohelper.R;

/**
 * Created by maico on 13/08/17.
 */

public class PopUpPagamento extends BottomSheetDialogFragment {

    TextView numero, mes, ano, cvv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.pop_fragment_pagamento, container, false);

        numero = view.findViewById(R.id.pagamentoNumero);
        mes = view.findViewById(R.id.pagamentoMes);
        ano = view.findViewById(R.id.pagamentoAno);
        cvv = view.findViewById(R.id.pagamentoCvv);

        ImageView imgVoltar = view.findViewById(R.id.imgVoltarPagamento);
        imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Preferencias preferencias = new Preferencias(getActivity());
        numero.setText(preferencias.getCartaoNumero()+":");
        mes.setText(preferencias.getCartaoMes()+":");
        ano.setText(preferencias.getCartaoAno()+":");
        cvv.setText(preferencias.getCartaoCvv()+"");

        Button btnAddCartao = view.findViewById(R.id.btnAddCartao);
        btnAddCartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopUpCartao popcartao = new PopUpCartao();

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.add(R.id.conteudo_principal, popcartao, "popcartao");
                transaction.addToBackStack(null); //Linha super importante para  o retorno do fragment
                transaction.commit();
            }
        });

        return view;
    }
    public void onBackPressed() {
        getActivity().onBackPressed();
    }
}
