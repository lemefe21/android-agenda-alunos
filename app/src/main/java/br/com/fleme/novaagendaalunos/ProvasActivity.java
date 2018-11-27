package br.com.fleme.novaagendaalunos;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.fleme.novaagendaalunos.fragments.DetalhesProvaFragment;
import br.com.fleme.novaagendaalunos.fragments.ListaProvasFragment;
import br.com.fleme.novaagendaalunos.model.Prova;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        //coloca o fragment pronto dentro da tela que está utilizando o FrameLayout
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction tx = fragmentManager.beginTransaction();

        tx.replace(R.id.frame_principal, new ListaProvasFragment());

        if(estaNoModoPaisagem()) {
            tx.replace(R.id.frame_secundario, new DetalhesProvaFragment());
        }

        tx.commit();

    }

    private boolean estaNoModoPaisagem() {
        return getResources().getBoolean(R.bool.modoPaisagem);
    }

    public void seleciona(Prova prova) {

        //passamos a responsabilidade de manipular o fragmento para a activity que possui os frames
        //para o fragment ficar mais desacoplado da activity evitando que
        //ele tente dar o replace em um layout que não seja o correto da activity

        FragmentManager fragmentManager = getSupportFragmentManager();

        //fragment de detalhes não está visivel precisamos do "replace"
        if(!estaNoModoPaisagem()) {

            FragmentTransaction tx = fragmentManager.beginTransaction();

            DetalhesProvaFragment detalhesFragment = new DetalhesProvaFragment();
            Bundle parametros = new Bundle();
            parametros.putSerializable("prova", prova);
            detalhesFragment.setArguments(parametros);

            tx.replace(R.id.frame_principal, detalhesFragment);

            //add a tx na pilha (por padrão o android só empilha activitys)
            //ao clicar no "voltar" o android faz um rollback da tx voltando para a tx anterior e não para a activity de lista
            //null >> posição especifica da pilha
            //add somente no modo portrait pois em landscape o correto é voltar para a listagem
            tx.addToBackStack(null);

            tx.commit();

        } else { //fragment de detalhes já está visivel, só precisamos popular com os dados

            //para não substituirmos o fragment principal pelos de detalhes quando estamos em landscape
            DetalhesProvaFragment detalhesFragment = (DetalhesProvaFragment) fragmentManager.findFragmentById(R.id.frame_secundario);
            detalhesFragment.populaCamposCom(prova);

        }

    }
}
