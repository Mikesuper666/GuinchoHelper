package onuse.com.br.ghinchohelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import onuse.com.br.ghinchohelper.Helper.Base64Custom;
import onuse.com.br.ghinchohelper.Helper.Preferencias;
import onuse.com.br.ghinchohelper.Model.Usuario;

/**
 * Created by maico on 11/08/17.
 */

public class LoginActivity extends AppCompatActivity {
    private Usuario usuario;
    private FirebaseAuth autenticacao;
    private DatabaseReference databaseRef;


    private Button logar;
    private EditText email, senha;
    private String idUsuarioLogado;
    private FirebaseApp app;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Valida as permissoes na classe estatica

        logar = (Button)findViewById(R.id.btnLogar);
        email = (EditText)findViewById(R.id.edtEmail);
        senha = (EditText)findViewById(R.id.edtSenha);

        //Busca a autenticação e a instancia do firebase
        app = FirebaseApp.getInstance();
        database = FirebaseDatabase.getInstance(app);

        autenticacao = FirebaseAuth.getInstance();
        FirebaseUser user = autenticacao.getCurrentUser();
        if(user != null) {
            AbrirTela();
        }
        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = new Usuario();
                if(ValidarCampos()) {
                    usuario.setEmail(email.getText().toString());
                    usuario.setSenha(senha.getText().toString());
                    Autenticar();
                }
            }
        });
    }

    private void Autenticar(){
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(), //busca o email
                usuario.getSenha()) //busca a senha
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //recuperar os dados do usuario
                            idUsuarioLogado = Base64Custom.ConverterBase64(usuario.getEmail());

                            // Get a reference to our chat "room" in the database
                            databaseRef = database.getReference("usuarios")
                            .child(idUsuarioLogado);

                            databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Usuario usuario = dataSnapshot.getValue(Usuario.class);

                                    // Salvar o email nas preferências
                                    String indentificadorUsuarioLogado = Base64Custom.ConverterBase64(usuario.getEmail());
                                    Preferencias preferencias = new Preferencias(LoginActivity.this);
                                    //Salva nas preferencia os dados do usuario para manipular mais facimente depois
                                    preferencias.SalvarDados(indentificadorUsuarioLogado, usuario.getNome(),usuario.getEmail(), usuario.getTelefone());

                                    AbrirTela();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void AbrirTela()
    {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void Cadastrar(View view)
    {
        Intent intent = new Intent(LoginActivity.this, UsuarioActivityCadastro.class);
        startActivity(intent);
    }

    public boolean ValidarCampos(){
        boolean validador = true;
        if(senha.getText().length() < 6)
        {
            senha.setError("Campo vazio ou menor que 6 caracteres");
            validador = false;
        }else{
            senha.setError(null);
        }

        if(email.getText().toString().trim().length() == 0){
            email.setError("Por favor digite seu email!");
            validador = false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()){
            email.setError("Email no formato incorreto, por favor verifique");
        }else{
            email.setError(null);
        }
        return validador;
    }
}