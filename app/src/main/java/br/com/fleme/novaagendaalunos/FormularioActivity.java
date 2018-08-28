package br.com.fleme.novaagendaalunos;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import br.com.fleme.novaagendaalunos.dao.AlunoDAO;
import br.com.fleme.novaagendaalunos.helper.FormularioHelper;
import br.com.fleme.novaagendaalunos.model.Aluno;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        Log.i("LOG_AGENDA", "onCreate - FormularioActivity");

        helper = new FormularioHelper(this);

        final Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno_selecionado");
        if(aluno != null) {
            helper.preencheFormulario(aluno);
        }

        Button botaoFoto = findViewById(R.id.formulario_btn_foto);
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //devemos informar que queremos salvar a imagem dentro da pasta da nossa aplicação
                //null >> subpastas
                String caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(caminhoFoto);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivity(intentCamera);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_formulario_ok:

                Log.i("LOG_AGENDA", "Click - Menu menu_formulario_ok");

                //se o aluno veio pela Intent ele já possui um id
                // objeto é atualizado no preencheFormulario do helper
                Aluno aluno = helper.pegaAlunoDoFormulario();

                AlunoDAO dao = new AlunoDAO(this);
                if(aluno.getId() != null) {
                    dao.altera(aluno);
                    Toast.makeText(FormularioActivity.this, "Aluno " + aluno.getNome() + " atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    dao.insere(aluno);
                    Toast.makeText(FormularioActivity.this, "Aluno " + aluno.getNome() + " salvo com sucesso!", Toast.LENGTH_SHORT).show();
                }
                dao.close();

                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
