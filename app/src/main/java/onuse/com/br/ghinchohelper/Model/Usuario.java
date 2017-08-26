package onuse.com.br.ghinchohelper.Model;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

/**
 * Created by maico on 11/08/17.
 */

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    public Usuario(){}

    public void Salvar(){
        // Get the Firebase app and all primitives we'll use
        FirebaseApp app = FirebaseApp.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance(app);

        // Get a reference to our chat "room" in the database
        DatabaseReference databaseRef = database.getReference("usuarios").child(getId());

        databaseRef.setValue(this);
    }//Salva esse usuario lá no banco de dados por aqui

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
