package br.com.fleme.novaagendaalunos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.fleme.novaagendaalunos.dao.AlunoDAO;
import br.com.fleme.novaagendaalunos.model.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunosView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        Log.i("LOG_AGENDA", "onCreate - ListaAlunosActivity");

        listaAlunosView = findViewById(R.id.lista_alunos);
        listaAlunosView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View itemLista, int position, long id) {

                Aluno aluno = (Aluno) listaAlunosView.getItemAtPosition(position);
                Log.i("LOG_AGENDA", "OnItemClickListener - " + position + " - ListaAlunosActivity - " + aluno.getNome());

                Intent intentAlunoProFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                intentAlunoProFormulario.putExtra("aluno_selecionado", aluno);
                startActivity(intentAlunoProFormulario);

            }
        });
        listaAlunosView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("LOG_AGENDA", "OnItemLongClickListener - " + position + " - ListaAlunosActivity");
                //return true; >> não passa o evento para frente (ex: Menu de contexto ou clique simples em seguida)
                return false;
            }
        });

        Button novoAluno = findViewById(R.id.lista_btn_adicionar);
        novoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("LOG_AGENDA", "Click - Botão adicionar novo aluno");
                Intent intentIrProFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intentIrProFormulario);
            }
        });

        registerForContextMenu(listaAlunosView);

    }

    private void carregaLista() {
        Log.i("LOG_AGENDA", "carregaLista - ListaAlunosActivity");

        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();


        ArrayAdapter<Aluno> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunos);
        listaAlunosView.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i("LOG_AGENDA", "onMenuItemClick - ListaAlunosActivity");

                //a view usa o adapter para mostrar os itens
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Aluno aluno = (Aluno) listaAlunosView.getItemAtPosition(info.position);

                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.remover(aluno);
                dao.close();

                carregaLista();

                Toast.makeText(ListaAlunosActivity.this, "Aluno " + aluno.getNome() + " excluído!", Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("LOG_AGENDA", "onStart - ListaAlunosActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("LOG_AGENDA", "onResume - ListaAlunosActivity");

        carregaLista();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //activity parcialmente visivel, por exemplo ao abrir um pop-up
        Log.i("LOG_AGENDA", "onPause - ListaAlunosActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("LOG_AGENDA", "onStop - ListaAlunosActivity");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("LOG_AGENDA", "onRestart - ListaAlunosActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("LOG_AGENDA", "onDestroy - ListaAlunosActivity");
    }
}
