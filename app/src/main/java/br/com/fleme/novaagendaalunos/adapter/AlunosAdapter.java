package br.com.fleme.novaagendaalunos.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.fleme.novaagendaalunos.ListaAlunosActivity;
import br.com.fleme.novaagendaalunos.model.Aluno;

public class AlunosAdapter extends BaseAdapter {

    private final List<Aluno> alunos;
    private final Context context;

    public AlunosAdapter(List<Aluno> alunos, Context context) {
        this.alunos = alunos;
        this.context = context;
    }

    //métodos abaixo são dados que o adapter precisa saber para passar para a lista

    @Override
    public int getCount() {
        return this.alunos.size();
    }

    @Override
    public Object getItem(int position) {
        //o adapter tem a responsabilidade de responder qual item está sendo clicado na lista
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //retorna cada view (itens) para a lista

        Aluno aluno = alunos.get(position);

        TextView view = new TextView(context);
        view.setText(aluno.toString());

        return view;
    }

}
