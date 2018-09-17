package br.com.fleme.novaagendaalunos;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.SupportMapFragment;

import br.com.fleme.novaagendaalunos.fragments.MapaFragment;

public class MapasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapas);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tx = manager.beginTransaction();

        //fragment de mapa pronto >> new SupportMapFragment() (do Play Services)
        tx.replace(R.id.frame_mapa, new MapaFragment());
        tx.commit();
    }
}
