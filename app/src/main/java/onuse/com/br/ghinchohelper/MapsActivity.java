package onuse.com.br.ghinchohelper;

import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class MapsActivity  extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap meuMapa;
    private Marker marker;
    private List<LatLng> latLngList;
    private Polyline polyline;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        meuMapa = googleMap;

        try{
            //Para utilizar o myLocation é necessario um try-catch
            meuMapa.setMyLocationEnabled(true);

            //a localização estará ativa apartir desse momento mas porém sera necessario a auto-localizar
            meuMapa.getUiSettings().setMyLocationButtonEnabled(true);
        }catch (SecurityException e){
            //apartir da API23 security exception tornou-se obrigatória
        }


    }

    public void GuinchosCadastradoTemporario(String thislatitude, String thislongitude){

        double latitude = Double.parseDouble(thislatitude);
        double longitude = Double.parseDouble(thislongitude);

        LatLng novoGuincho = new LatLng(latitude, longitude);//marcadore utilizam valores doubles
            meuMapa.addMarker(new MarkerOptions()
                    .position(novoGuincho)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_guincho))
                    .title("Guincho Disponível"));

    }//Este método é temporário

    public void PostosCadastrado(){
        LatLng[] marcadorPostos = new LatLng[40];//marcadore utilizam valores doubles
        for(int m = 0; m < marcadorPostos.length; m++){
            double latitude = aleatoriarLatitude();
            double longitude = aleatoriarLongitude();

            latitude *= -1;
            longitude *= -1;

            marcadorPostos[m] = new LatLng(latitude,longitude);

            meuMapa.addMarker(new MarkerOptions()
                    .position(marcadorPostos[m])
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_gasoline))
                    .title("Posto"));
        }
    }

    public void OficinasCadastrado(){
        LatLng[] marcadorPostos = new LatLng[40];//marcadore utilizam valores doubles
        for(int m = 0; m < marcadorPostos.length; m++){
            double latitude = aleatoriarLatitude();
            double longitude = aleatoriarLongitude();

            latitude *= -1;
            longitude *= -1;

            marcadorPostos[m] = new LatLng(latitude,longitude);

            meuMapa.addMarker(new MarkerOptions()
                    .position(marcadorPostos[m])
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_oficina))
                    .title("Oficina"));
        }
    }

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

    public void LimparMapa(){
        meuMapa.clear();
    }

    public void NovoMarcador(double latitude, double longitude, int m) {

        LatLng POSICAO;
        POSICAO = new LatLng(latitude, longitude);
            marker = meuMapa.addMarker(new MarkerOptions()
                    .position(POSICAO)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_guincho))
                    .title("Guincho numero " + m));
    }

    public void DrawRoute(){
        PolylineOptions po;

        if(polyline == null){
            po = new PolylineOptions();

            for(int i = 0, tam = latLngList.size(); i < tam; i++){
                po.color(Color.BLACK);
                polyline = meuMapa.addPolyline(po);
            }
        }else{
            polyline.setPoints(latLngList);
        }
    }

    public void getDistance(){
        double distance = 0;
        for(int i = 0, tam = latLngList.size(); i < tam; i++){
            if(i < tam - 1){
                distance += Distance(latLngList.get(i), latLngList.get(i+1));
            }
        }
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


    public void PegarEndereco(){
        Geocoder geocoder = new Geocoder(getActivity());

        List<Address>addressList;
        try{
            addressList = geocoder.getFromLocationName("Rua B, Alvorada, Rio Grande do Sul, Brasil", 1);

            String address = "Rua: " + addressList.get(0).getThoroughfare()+"\n";
            address = "Cidade: " + addressList.get(0).getSubAdminArea()+"\n";
            address = "Estado: " + addressList.get(0).getAdminArea()+"\n";
            address = "País: " + addressList.get(0).getCountryName()+"\n";

            LatLng ll = new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());

            Toast.makeText(getActivity(), "LatLng" + ll, Toast.LENGTH_LONG).show();

        }catch (IOException e){}
    }
}