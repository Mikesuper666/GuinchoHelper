package onuse.com.br.ghinchohelper.Model;

/**
 * Created by maico on 10/08/17.
 */

public class Coordenadas {
    public String latitude;
    public String longitude;
    public String distancia;
    public String tempo;
    public String endereco;
    public String valor;

    public Coordenadas(){}

    public void Coordenadas(String latitude, String longitude, String distancia, String tempo, String endereco, String valor) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.distancia = distancia;
        this.tempo = tempo;
        this.endereco = endereco;
        this.valor = valor;
    }
}
