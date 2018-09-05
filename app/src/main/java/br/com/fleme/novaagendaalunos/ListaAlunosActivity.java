package br.com.fleme.novaagendaalunos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.fleme.novaagendaalunos.adapter.AlunosAdapter;
import br.com.fleme.novaagendaalunos.converter.AlunoConverter;
import br.com.fleme.novaagendaalunos.dao.AlunoDAO;
import br.com.fleme.novaagendaalunos.model.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunosView;
    private final int MY_PERMISSION_CALL_PHONE = 123;
    private final int MY_RECEIVE_SMS = 456;
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        Log.i("LOG_AGENDA", "onCreate - ListaAlunosActivity");

        listaAlunosView = findViewById(R.id.lista_alunos);
        listaAlunosView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View itemLista, int position, long id) {
                //id é informado pelo adapter utilizado

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

        verificaPermissaoRecebimentoSMS();

        registerForContextMenu(listaAlunosView);

    }

    private void carregaLista() {
        Log.i("LOG_AGENDA", "carregaLista - ListaAlunosActivity");

        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        //adapter >> responsável por converter os objetos do Java em um view do Android
        //ArrayAdapter utiliza o toString do objeto Java para mostar a informação na View
        //ArrayAdapter<Aluno> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunos);

        AlunosAdapter adapter = new AlunosAdapter(alunos, this);
        listaAlunosView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.menu_enviar_notas:
                Toast.makeText(this, "Enviando notas...", Toast.LENGTH_SHORT).show();

                AlunoDAO dao = new AlunoDAO(this);
                List<Aluno> alunos = dao.buscaAlunos();
                dao.close();

                AlunoConverter conversor = new AlunoConverter();
                String json = conversor.converteParaJSON(alunos);

                Toast.makeText(this, json, Toast.LENGTH_SHORT).show();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        //a view usa o adapter para mostrar os itens
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        aluno = (Aluno) listaAlunosView.getItemAtPosition(info.position);

        MenuItem itemLigar = menu.add("Ligar");
        itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i("LOG_AGENDA", "onMenuItemClick- Ligar - ListaAlunosActivity");
                ligarPara();
                return false;
            }
        });

        MenuItem itemSMS = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + aluno.getTelefone()));
        itemSMS.setIntent(intentSMS);

        MenuItem itemMapa = menu.add("Visualizar no Mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
        itemMapa.setIntent(intentMapa);

        MenuItem menuSite = menu.add("Visitar site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        String site = aluno.getSite();
        if(!site.startsWith("http://")) {
            site = "http://" + site;
        }
        intentSite.setData(Uri.parse(site));
        menuSite.setIntent(intentSite);

        MenuItem itemDeletar = menu.add("Deletar");
        itemDeletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i("LOG_AGENDA", "onMenuItemClick - ListaAlunosActivity");

                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.remover(aluno);
                dao.close();

                carregaLista();

                Toast.makeText(ListaAlunosActivity.this, "Aluno " + aluno.getNome() + " removido!", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

    }

    private void ligarPara() {
        if(ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //nesse momento se a permissão ainda não foi dada pelo usuário, iremos solicitar
            Log.i("LOG_AGENDA", "permission.CALL_PHONE - Not PERMISSION_GRANTED");
            ActivityCompat.requestPermissions(ListaAlunosActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSION_CALL_PHONE);
        } else {
            Log.i("LOG_AGENDA", "permission.CALL_PHONE - PERMISSION_GRANTED");
            Intent intentLigar = new Intent(Intent.ACTION_CALL);
            intentLigar.setData(Uri.parse("tel:" + aluno.getTelefone()));
            startActivity(intentLigar);
        }
    }

    private void verificaPermissaoRecebimentoSMS() {
        if(ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            //nesse momento se a permissão ainda não foi dada pelo usuário, iremos solicitar
            Log.i("LOG_AGENDA", "permission.RECEIVE_SMS - Not PERMISSION_GRANTED");
            ActivityCompat.requestPermissions(ListaAlunosActivity.this, new String[]{Manifest.permission.RECEIVE_SMS}, MY_RECEIVE_SMS);
        } else {
            Log.i("LOG_AGENDA", "permission.RECEIVE_SMS - PERMISSION_GRANTED");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //método chamado após o usuário ter dado alguma permissão na activity atual
        //identificamos qual foi de acordo com o requestCode

        switch (requestCode) {
            case MY_PERMISSION_CALL_PHONE: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("LOG_AGENDA", "onRequestPermissionsResult - requestCode of CALL_PHONE - GRANTED");
                    ligarPara();
                } else {
                    Log.i("LOG_AGENDA", "onRequestPermissionsResult - requestCode of CALL_PHONE - Cancel");
                }
            }
        }

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
