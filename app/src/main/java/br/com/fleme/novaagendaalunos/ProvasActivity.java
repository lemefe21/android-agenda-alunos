package br.com.fleme.novaagendaalunos;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

}
