package br.com.fleme.novaagendaalunos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;
import java.util.logging.Logger;

import br.com.fleme.novaagendaalunos.dao.AlunoDAO;
import br.com.fleme.novaagendaalunos.model.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        Log.i("LOG_AGENDA", "onCreate - ListaAlunosActivity");

        Button novoAluno = findViewById(R.id.lista_btn_adicionar);
        novoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("LOG_AGENDA", "Click - Botão adicionar novo aluno");
                Intent intentIrProFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intentIrProFormulario);
            }
        });

    }

    private void carregaLista() {
        Log.i("LOG_AGENDA", "carregaLista - ListaAlunosActivity");

        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        ListView listaAlunosView = findViewById(R.id.lista_alunos);
        ArrayAdapter<Aluno> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunos);
        listaAlunosView.setAdapter(adapter);
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
