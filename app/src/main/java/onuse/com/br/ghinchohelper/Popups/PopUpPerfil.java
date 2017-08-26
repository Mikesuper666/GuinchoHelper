package onuse.com.br.ghinchohelper.Popups;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import onuse.com.br.ghinchohelper.Helper.Base64Custom;
import onuse.com.br.ghinchohelper.Helper.Preferencias;
import onuse.com.br.ghinchohelper.LoginActivity;
import onuse.com.br.ghinchohelper.R;

/**
 * Created by maico on 13/08/17.
 */

public class PopUpPerfil extends BottomSheetDialogFragment {

    TextView nome, email, telefone, placa;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.pop_fragment_perfil, container, false);

        nome = view.findViewById(R.id.popPerfilNome);
        email = view.findViewById(R.id.popPerfilEmail);
        telefone = view.findViewById(R.id.popPerfilTelefone);
        placa = view.findViewById(R.id.popPerfilPlaca);

        Preferencias preferencias = new Preferencias(getActivity());
        nome.setText(preferencias.getNome());
        email.setText(preferencias.getEmail());
        telefone.setText(preferencias.getTelefone());

        ImageView imgVoltar = view.findViewById(R.id.imgVoltar);
        imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView deslogar = view.findViewById(R.id.popPerfilDeslogar);
        deslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth user = FirebaseAuth.getInstance();
                user.signOut();
                Base64Custom.Aberto(0);
                Intent sair = new Intent(getActivity(), LoginActivity.class);
                startActivity(sair);
                getActivity().finish();
            }
        });//Esquema de deslogar


        return view;
    }
    public void onBackPressed() {
        getActivity().onBackPressed();
    }
}
