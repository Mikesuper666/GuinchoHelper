package onuse.com.br.ghinchohelper.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import onuse.com.br.ghinchohelper.Helper.Preferencias;
import onuse.com.br.ghinchohelper.Model.GuinchosDisponiveis;
import onuse.com.br.ghinchohelper.R;

import static onuse.com.br.ghinchohelper.R.id.textView;
import static onuse.com.br.ghinchohelper.R.id.txtGuinhoEndereco;

/**
 * Created by maico on 15/08/17.
 */

public class GuinchoAdapter extends ArrayAdapter<GuinchosDisponiveis> {
    private Context context;
    private ArrayList<GuinchosDisponiveis> disponivel;
    public GuinchoAdapter(Context c, ArrayList<GuinchosDisponiveis> objects) {
        super(c, 0, objects);
        this.context = c;
        this.disponivel = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        //Verifica se a lista está vazia
        if(disponivel != null)
        {
            //recuperar a menssagem
            GuinchosDisponiveis guinchosDisponiveis = disponivel.get(position);


            //inicia o objeto para montagem do layout
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.adapter_lista_guicho, parent, false);

            //monta apartir do xml
            TextView txtValorViagem = (TextView)view.findViewById(R.id.txtGuinhoPagamento);
            txtValorViagem.setText("R$"+guinchosDisponiveis.getValor());

            //seta na lista a distancia
            TextView ttxDistancia = (TextView)view.findViewById(R.id.txtGuinchoDistancia);
            ttxDistancia.setText(guinchosDisponiveis.getDistancia());

            //setar o tempo para chegar
            TextView txtTempo = (TextView)view.findViewById(R.id.txtGuinchoTempo);
            txtTempo.setText(guinchosDisponiveis.getTempo());

            TextView textEndereco = (TextView) view.findViewById(R.id.txtGuinhoEndereco);
            if(guinchosDisponiveis.getEndereco() != "" && guinchosDisponiveis.getEndereco() != null) {
                textEndereco.setText(guinchosDisponiveis.getEndereco());
            }else{
                textEndereco.setText("Em serviço: Ocupado");
                textEndereco.setTextColor(Color.RED);
            }
        }
        return view;
    }
}