package br.com.fleme.novaagendaalunos.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.fleme.novaagendaalunos.ProvasActivity;
import br.com.fleme.novaagendaalunos.R;
import br.com.fleme.novaagendaalunos.model.Prova;

public class ListaProvasFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //monta o nosso fragment

        View view = inflater.inflate(R.layout.fragment_lista_provas, container, false);

        List<String> topicosJava = Arrays.asList("Orientação a Objetos", "Classes e Objetos", "Herança e Polimorfismo");
        Prova provaJava = new Prova("Java", "15/10/2018", topicosJava);

        List<String> topicosAndroid = Arrays.asList("Activitys", "Recursos do Dispositivo", "Layouts");
        Prova provaAndroid = new Prova("Android", "22/10/2018", topicosAndroid);

        List<String> topicosIonic = Arrays.asList("Instalação e Configuração", "Componentes e Plugins", "Material Design");
        Prova provaIonic = new Prova("Ionic", "24/10/2018", topicosIonic);

        List<Prova> provas = Arrays.asList(provaJava, provaAndroid, provaIonic);

        ArrayAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, provas);

        ListView listView = view.findViewById(R.id.provas_lista);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Prova prova = (Prova) parent.getItemAtPosition(position);
                Toast.makeText(getContext(), "Clicou no fragment de prova " + prova, Toast.LENGTH_SHORT).show();

                //Intent intentDetalhesProva = new Intent(getContext(), DetalhesProvaActivity.class);
                //intentDetalhesProva.putExtra("prova", prova);
                //startActivity(intentDetalhesProva);

                //pegamos uma referencia da activity que está utilizando o fragment
                ProvasActivity provasActivity = (ProvasActivity) getActivity();
                provasActivity.seleciona(prova);

            }
        });

        return view;
    }
}
