package onuse.com.br.ghinchohelper.Popups;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import onuse.com.br.ghinchohelper.Adapter.GuinchoAdapter;
import onuse.com.br.ghinchohelper.MainActivity;
import onuse.com.br.ghinchohelper.Model.GuinchosDisponiveis;
import onuse.com.br.ghinchohelper.R;

/**
 * Created by maico on 15/08/17.
 */

public class PopUpGuinchos extends BottomSheetDialogFragment {
    private ArrayAdapter<GuinchosDisponiveis> arrayAdapter;
    private ArrayList<GuinchosDisponiveis> menssagem;
    ListView lista;

    private FirebaseApp app;
    private FirebaseDatabase database;
    private FirebaseStorage storage;

    private DatabaseReference databaseRef;
    private StorageReference storageRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.pop_fragment_lista_guincho, container, false);

        lista = view.findViewById(R.id.listaGuinchos);
        ImageView imgVoltarVoltarGuincho = view.findViewById(R.id.imgVoltarVoltarGuincho);
        imgVoltarVoltarGuincho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

/*******************************
 //motagem do arrayAdapter
 ******************************/
        menssagem = new ArrayList<>();
        arrayAdapter = new GuinchoAdapter(getActivity(), menssagem);

        lista.setAdapter(arrayAdapter);


        // Get the Firebase app and all primitives we'll use
        app = FirebaseApp.getInstance();
        database = FirebaseDatabase.getInstance(app);
        storage = FirebaseStorage.getInstance(app);


        // Referencia da sala que será gravado
        databaseRef = database.getReference("PosicaoGuinchos");

        /***********************************************************
         //Criar o listener das menssagens modifica quando se recebe as menssagens
         *************************************************************/
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //limpar as menssagens antes de inserir
                menssagem.clear();
                //recuperar as menssagens
                for(DataSnapshot dados : dataSnapshot.getChildren())
                {
                    if(dados != null) {
                        //recupera a menssagen individual
                        GuinchosDisponiveis menssagemC = dados.getValue(GuinchosDisponiveis.class);
                        menssagem.add(menssagemC);
                     }
                }
                //Ordena as mensagems finais pela distancia
                final Collator col = Collator.getInstance();
                arrayAdapter.sort(new Comparator<GuinchosDisponiveis>() {
                    @Override
                    public int compare(GuinchosDisponiveis cv1, GuinchosDisponiveis cv2) {
                        return col.compare(cv1.getDistancia(),cv2.getDistancia());
                    }
                });

                //notifica o array adapter das novas mudanças
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }
    public void onBackPressed() {
        getActivity().onBackPressed();
    }


    public void AtualizarLista(double latitude, double longitute){
        GuinchosDisponiveis guinchosDisponiveis = new GuinchosDisponiveis();
        guinchosDisponiveis.setId(1);
        guinchosDisponiveis.setLatitude(""+latitude);
        guinchosDisponiveis.setLongitude(""+longitute);

        menssagem.add(guinchosDisponiveis);
        arrayAdapter.notifyDataSetChanged();
    }

}