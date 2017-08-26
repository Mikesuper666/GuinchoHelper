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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import onuse.com.br.ghinchohelper.Helper.Base64Custom;
import onuse.com.br.ghinchohelper.Helper.Preferencias;
import onuse.com.br.ghinchohelper.Model.Usuario;

/**
 * Created by maico on 11/08/17.
 */

public class UsuarioActivityCadastro extends AppCompatActivity {

    private EditText nome, email, senha, edtCadastroTelefone;
    private Button cadastrar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_cadastro);

        nome = (EditText)findViewById(R.id.edtCadastroNome);
        email = (EditText)findViewById(R.id.edtCadastroEmail);
        senha = (EditText)findViewById(R.id.edtCadastroSenha);
        edtCadastroTelefone = (EditText)findViewById(R.id.edtCadastroTelefone);
        cadastrar = (Button) findViewById(R.id.btnCadastrar);

        autenticacao = FirebaseAuth.getInstance();

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = new Usuario();
                usuario.setNome(nome.getText().toString());
                usuario.setEmail(email.getText().toString());
                usuario.setTelefone(edtCadastroTelefone.getText().toString());
                usuario.setSenha(senha.getText().toString());

                if(ValidarCampos()){
                    CadastrarUsuario();
                }//validar todos os campos antes de cadastrar
            }
        });
    }
    private void CadastrarUsuario()
    {
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(), //busca o email
                usuario.getSenha()) //busca a senha
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Chamado quando o registro completo com sucesso
                            Toast.makeText(UsuarioActivityCadastro.this, R.string.toast_usuario_cadastrado,Toast.LENGTH_SHORT).show();
                            String indentificador = Base64Custom.ConverterBase64(usuario.getEmail());
                            usuario.setId(indentificador);//pega a id do firebase
                            usuario.Salvar();//salva o id

                            String indentificadorUsuarioLogado = Base64Custom.ConverterBase64(usuario.getEmail());
                            Preferencias preferencias = new Preferencias(UsuarioActivityCadastro.this);
                            //Salva nas preferencia os dados do usuario para manipular mais facimente depois
                            preferencias.SalvarDados(indentificadorUsuarioLogado, usuario.getNome(), usuario.getEmail(), usuario.getTelefone());

                            finish();
                            ProsseguirLogin();
                        } else {
                            // chamado se o registro falir por algum motivo
                            Toast.makeText(UsuarioActivityCadastro.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public boolean ValidarCampos()
    {
        boolean validador = true;

        if(nome.getText().length() <= 3)
        {
            nome.setError("Por favor entre com seu nome");
            validador = false;
        }else
        {
            nome.setError(null);
        }

        if (email.getText().toString().trim().length() == 0)
        {
            email.setError("Por favor digite seu email");
            validador = false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches())
        {
            email.setError("Por favor entre com um email válido!");
            validador = false;
        }
        else
        {
            email.setError(null);
        }

        if(senha.getText().toString().trim().length() <= 5)
        {
            senha.setError("Por favor entra com a sua senha no minimo de 6 caracteres");
            validador = false;
        }
        else
        {
            senha.setError(null);
        }

        if(edtCadastroTelefone.getText().length() < 11)
        {
            edtCadastroTelefone.setError("Por favor entre com o DD+Número de telefone");
            validador = false;
        }else{
            edtCadastroTelefone.setError(null);
        }
        return validador;
    }

    private void ProsseguirLogin()
    {
        Intent intent = new Intent(UsuarioActivityCadastro.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
