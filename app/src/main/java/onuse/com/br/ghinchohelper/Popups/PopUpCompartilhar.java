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
import android.widget.Toast;

import onuse.com.br.ghinchohelper.Adapter.ImageAdapter;
import onuse.com.br.ghinchohelper.R;

/**
 * Created by maico on 13/08/17.
 */

public class PopUpCompartilhar extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.pop_fragment_compartilhar, container, false);

        ImageView imgVoltar = view.findViewById(R.id.imgVoltarCompartilhar);
        imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        GridView gridview = (GridView) view.findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(getActivity()));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                String urlToShare = "Guincho.Helper.com";
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, urlToShare);
                        intent.setPackage("com.facebook.katana");

                        startActivity(intent);
                        break;

                    case 1:
                        intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, urlToShare);
                        intent.setPackage("com.google.android.apps.plus");

                        startActivity(intent);
                        break;

                    case 2:

                        intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, urlToShare);
                        intent.setPackage("com.whatsapp");

                        startActivity(intent);
                        break;

                    case 3:

                        intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, urlToShare);
                        intent.setPackage("com.twitter.android");

                        startActivity(intent);
                        break;
                }
            }
        });

        return view;
    }
    public void onBackPressed() {
        getActivity().onBackPressed();
    }
}