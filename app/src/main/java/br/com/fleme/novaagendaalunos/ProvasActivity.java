package br.com.fleme.novaagendaalunos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.fleme.novaagendaalunos.model.Prova;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        List<String> topicosJava = Arrays.asList("Orientação a Objetos", "Classes e Objetos", "Herança e Polimorfismo");
        Prova provaJava = new Prova("Java", "15/10/2018", topicosJava);

        List<String> topicosAndroid = Arrays.asList("Activitys", "Recursos do Dispositivo", "Layouts");
        Prova provaAndroid = new Prova("Android", "22/10/2018", topicosAndroid);

        List<String> topicosIonic = Arrays.asList("Instalação e Configuração", "Componentes e Plugins", "Material Design");
        Prova provaIonic = new Prova("Ionic", "24/10/2018", topicosIonic);

        List<Prova> provas = Arrays.asList(provaJava, provaAndroid, provaIonic);

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, provas);

        ListView listView = findViewById(R.id.provas_lista);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = (Prova) parent.getItemAtPosition(position);
                Toast.makeText(ProvasActivity.this, "Clicou na prova " + prova, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
