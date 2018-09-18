package br.com.fleme.novaagendaalunos.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.com.fleme.novaagendaalunos.R;
import br.com.fleme.novaagendaalunos.model.Prova;

public class DetalhesProvaFragment extends Fragment {

    private TextView campoMateria;
    private TextView campoData;
    private ListView listaTopicos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        //1. construimos a view que representa o fragment na tela
        //xml gerado >> layout.fragment_detalhes_prova
        //copiamos o layout já criado do activity_detalhes_prova

        View view = inflater.inflate(R.layout.fragment_detalhes_prova, container, false);

        campoMateria = view.findViewById(R.id.detalhe_prova_materia);
        campoData = view.findViewById(R.id.detalhe_prova_data);
        listaTopicos = view.findViewById(R.id.detalhe_prova_topicos);

        Bundle parametros = getArguments();
        if(parametros != null) {
            Prova prova = (Prova) parametros.getSerializable("prova");
            populaCamposCom(prova);
        }

        return view;
    }

    public void populaCamposCom(Prova prova) {

        campoMateria.setText(prova.getMateria());
        campoData.setText(prova.getData());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, prova.getTopicos());
        listaTopicos.setAdapter(adapter);

    }

}
