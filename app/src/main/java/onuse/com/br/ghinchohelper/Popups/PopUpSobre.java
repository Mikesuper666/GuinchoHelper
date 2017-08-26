package onuse.com.br.ghinchohelper.Popups;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import onuse.com.br.ghinchohelper.LoginActivity;
import onuse.com.br.ghinchohelper.R;

/**
 * Created by maico on 13/08/17.
 */

public class PopUpSobre extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.pop_fragment_sobre, container, false);

        ImageView imgVoltar = view.findViewById(R.id.imgVoltarSobre);
        imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        return view;
    }
    public void onBackPressed() {
        getActivity().onBackPressed();
    }
}
