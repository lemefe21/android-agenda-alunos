package br.com.fleme.novaagendaalunos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.com.fleme.novaagendaalunos.dao.AlunoDAO;
import br.com.fleme.novaagendaalunos.helper.FormularioHelper;
import br.com.fleme.novaagendaalunos.model.Aluno;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);

        Log.i("LOG_AGENDA", "onCreate - FormularioActivity");
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

                Aluno aluno = helper.pegaAluno();
                Toast.makeText(FormularioActivity.this, "Aluno " + aluno.getNome() + " salvo com sucesso!", Toast.LENGTH_SHORT).show();

                AlunoDAO dao = new AlunoDAO(this);
                dao.insere(aluno);
                dao.close();

                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
