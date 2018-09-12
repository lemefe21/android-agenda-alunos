package br.com.fleme.novaagendaalunos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetalhesProvaFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        //1. construimos a view que representa o fragment na tela
        //xml gerado >> layout.fragment_detalhes_prova
        //copiamos o layout já criado do activity_detalhes_prova

        View view = inflater.inflate(R.layout.fragment_detalhes_prova, container, false);



        return view;
    }

}
