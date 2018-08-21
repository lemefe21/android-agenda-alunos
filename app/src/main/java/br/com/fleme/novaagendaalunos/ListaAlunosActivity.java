package br.com.fleme.novaagendaalunos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.logging.Logger;

public class ListaAlunosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        Log.i("LOG_AGENDA", "onCreate - ListaAlunosActivity");

        String[] alunos = {"Felipe", "Gustavo", "Jeferson", "Ronaldo","Felipe", "Gustavo", "Jeferson", "Ronaldo","Felipe", "Gustavo", "Jeferson", "Ronaldo","Felipe", "Gustavo", "Jeferson", "Ronaldo"};
        ListView listaAlunosView = findViewById(R.id.lista_alunos);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunos);
        listaAlunosView.setAdapter(adapter);

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
}
