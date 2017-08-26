package onuse.com.br.ghinchohelper.Popups;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import onuse.com.br.ghinchohelper.R;

/**
 * Created by maico on 13/08/17.
 */

public class PopUpPromocao extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.pop_fragment_promocao, container, false);

        ImageView imgVoltar = view.findViewById(R.id.imgVoltarPromocao);
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
