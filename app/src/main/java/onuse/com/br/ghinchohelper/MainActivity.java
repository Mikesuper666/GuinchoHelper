package onuse.com.br.ghinchohelper;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.text.Collator;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import onuse.com.br.ghinchohelper.Helper.Base64Custom;
import onuse.com.br.ghinchohelper.Model.Coordenadas;
import onuse.com.br.ghinchohelper.Model.GuinchosDisponiveis;
import onuse.com.br.ghinchohelper.Popups.PopUpCompartilhar;
import onuse.com.br.ghinchohelper.Popups.PopUpGuinchos;
import onuse.com.br.ghinchohelper.Popups.PopUpPagamento;
import onuse.com.br.ghinchohelper.Popups.PopUpPerfil;
import onuse.com.br.ghinchohelper.Popups.PopUpPromocao;
import onuse.com.br.ghinchohelper.Popups.PopUpSobre;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;

    private FirebaseApp app;
    private FirebaseDatabase database;
    private FirebaseStorage storage;

    private DatabaseReference databaseRef;
    private StorageReference storageRef;

    Coordenadas coordenada = new Coordenadas();

    GPSTracker gpsTracker = new GPSTracker(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Criamos o suporte de fragmento
        fragmentManager = getSupportFragmentManager();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            Toast.makeText(getBaseContext(), "Solicitando a lista de guincho disponiveis.", Toast.LENGTH_LONG).show();

                Location location = gpsTracker.getLocation();
            if(location != null)
            {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.conteudo_principal, new PopUpGuinchos(), "PopUpGuinchos");
                transaction.addToBackStack(null); //Linha super importante para  o retorno do fragment
                transaction.commit();
                /* coordenada.Coordenadas(location.getLatitude()+"", ""+location.getLongitude(), "Meu local", "Meu tempo", "MeuLocal", "valor");
                databaseRef.setValue(coordenada);

                NovosGuinchos();*/
                guichoMarcadorTemporario();
            }else{
                Toast.makeText(MainActivity.this, "Precisa ativar seu GPS para usar essa função", Toast.LENGTH_LONG).show();
            }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();*/

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Dizemos ao fragment que ele  é do tipo instanciavel e que pode ser movido
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //dizemos ao nosso fragmento aonde ele será alocado, e também qual fragmento será alocado, e damos um apelido de acesso para ele
        transaction.add(R.id.conteudo_principal, new MapsActivity(), "MapsActivity");

        //agora confirmamos a troca de tela
        transaction.commitAllowingStateLoss();//quando chamamos comit dizemos que é nesse momento que deve ocorrer a troca de telas obrigatoriamente elas ja deves ter sidos configuradas

        // Get the Firebase app and all primitives we'll use
        app = FirebaseApp.getInstance();
        database = FirebaseDatabase.getInstance(app);
        storage = FirebaseStorage.getInstance(app);


        // Referencia da sala que será gravado
        databaseRef = database.getReference("Socorro");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Base64Custom.Aberto(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nac_principal) {
            // Handle the camera action
            onBackPressed();
        } else if (id == R.id.nav_perfil) {
            //RESPONSAVEL POR TRAZER  O FRAGMENT DO MAPA AO CRIAR
            if(Base64Custom.confirmarFragment()) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.conteudo_principal, new PopUpPerfil(), "PopUpPerfil");
                transaction.addToBackStack(null); //Linha super importante para  o retorno do fragment
                transaction.commit();
                Base64Custom.Aberto(1);
            }
        } else if (id == R.id.nav_pagamento) {
            if (Base64Custom.confirmarFragment()) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.conteudo_principal, new PopUpPagamento(), "PopUpPagamento");
                transaction.addToBackStack(null); //Linha super importante para  o retorno do fragment
                transaction.commit();
                Base64Custom.Aberto(1);
            }
        } else if (id == R.id.nav_postos) {
                MapsActivity mapsActivity = (MapsActivity)fragmentManager.findFragmentByTag("MapsActivity");
                mapsActivity.PostosCadastrado();
        } else if (id == R.id.nav_oficina) {
            MapsActivity mapsActivity = (MapsActivity)fragmentManager.findFragmentByTag("MapsActivity");
            mapsActivity.OficinasCadastrado();
        } else if (id == R.id.nav_promocao) {
            if (Base64Custom.confirmarFragment()) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.conteudo_principal, new PopUpPromocao(), "PopUpPromocao");
                transaction.addToBackStack(null); //Linha super importante para  o retorno do fragment
                transaction.commit();
                Base64Custom.Aberto(1);
            }
        } else if (id == R.id.nav_compartilhar) {
            if (Base64Custom.confirmarFragment()) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.conteudo_principal, new PopUpCompartilhar(), "PopUpCompartilhar");
                transaction.addToBackStack(null); //Linha super importante para  o retorno do fragment
                transaction.commit();
                Base64Custom.Aberto(1);
            }
        } else if (id == R.id.nav_sobre) {
            if (Base64Custom.confirmarFragment()) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.conteudo_principal, new PopUpSobre(), "PopUpSobre");
                transaction.addToBackStack(null); //Linha super importante para  o retorno do fragment
                transaction.commit();
                Base64Custom.Aberto(1);
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void NovosGuinchos()
    {
        //localização do ponto
        Location location = gpsTracker.getLocation();
        MapsActivity mapsActivity = (MapsActivity)fragmentManager.findFragmentByTag("MapsActivity");
        mapsActivity.LimparMapa();
        int i = 60;
        for(int m = 0; m < i; m++){
            // Referencia da sala que será gravado

            databaseRef = database.getReference("PosicaoGuinchos").child(""+m);

            double latitude = aleatoriarLatitude();
            double longitude = aleatoriarLongitude();
            latitude *= -1;
            longitude *= -1;
            mapsActivity.NovoMarcador(latitude, longitude, m);

            //cria uma nova localização do ponto destino para comparar com o local atual
            Location pontoAlvo = new Location("NovoPonto");
            pontoAlvo.setLatitude(latitude);
            pontoAlvo.setLongitude(longitude);

            float distancia = location.distanceTo(pontoAlvo);

            //Transfomando mertro em kilometros para melhor visualização
            float km = (distancia / 1000);

            float valor = km * 3.8f;

            //tranformar kilemtros em hora e formatar o relogio
            float tempo = (float) (km / 3.6);

            //buscar o endereço da lat lon
            String endereco = RetornarEndereco(latitude, longitude);

            coordenada.Coordenadas(latitude+"", ""+longitude, km+"", tempo+"", endereco, valor+"");
            databaseRef.setValue(coordenada);
        }
    }

    public String RetornarEndereco(double latitude, double longitude){
        Geocoder geocoder;
        String Endereco = "";
        List<Address> yourAddresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            yourAddresses= geocoder.getFromLocation(latitude, longitude, 1);

            if (yourAddresses.size() > 0)
            {
                String yourAddress = yourAddresses.get(0).getAddressLine(0);
                String yourCity = yourAddresses.get(0).getAddressLine(1);
                Endereco = yourAddress +" "+ yourCity;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Endereco;
    }

    public static double Distance(LatLng StartP, LatLng EndP)
    {
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat1 - lat2);
        double dLon = Math.toRadians(lon1 - lon2);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.asin(Math.sqrt(a));

        return 6366000 * c;
    }//metodo do stack overflow para calcular a distancia entre 2 pontos

    public double aleatoriarLatitude() {
        double minimo = 29.95;
        double maximo = 30.23;

        Random random = new Random();
        double randomValue = minimo + (maximo - minimo) *  random.nextDouble();

        return randomValue;
    }

    public double aleatoriarLongitude() {
        double minimo = 50.7;
        double maximo = 51.23;

        Random random = new Random();
        double randomValue = minimo + (maximo - minimo) *  random.nextDouble();

        return randomValue;
    }

    public void guichoMarcadorTemporario()
    {
        // Referencia da sala que será gravado
        databaseRef = database.getReference("PosicaoGuinchos");
        final MapsActivity mapsActivity = (MapsActivity)fragmentManager.findFragmentByTag("MapsActivity");
        mapsActivity.LimparMapa();
        /***********************************************************
         //Criar o listener das menssagens modifica quando se recebe as menssagens
         *************************************************************/
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //recuperar as menssagens
                for(DataSnapshot dados : dataSnapshot.getChildren())
                {
                    //recupera a menssagen individual
                    GuinchosDisponiveis menssagemC = dados.getValue(GuinchosDisponiveis.class);
                     mapsActivity.GuinchosCadastradoTemporario(menssagemC.getLatitude(), menssagemC.getLongitude());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
