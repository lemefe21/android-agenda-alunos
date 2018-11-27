package br.com.fleme.novaagendaalunos.listener;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.fleme.novaagendaalunos.ListaAlunosActivity;
import br.com.fleme.novaagendaalunos.dao.AlunoDAO;
import br.com.fleme.novaagendaalunos.model.Aluno;

public class RemoveClickListener implements MenuItem.OnMenuItemClickListener {

    ListaAlunosActivity context;
    Aluno aluno;

    public RemoveClickListener(ListaAlunosActivity context, Aluno aluno) {
        this.context = context;
        this.aluno = aluno;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Log.i("LOG_AGENDA", "onMenuItemClick - ListaAlunosActivity");

        new AlertDialog.Builder(context)
                .setTitle("Deletando o Aluno")
                .setMessage("Tem certeza que deseja deletar esse aluno?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        AlunoDAO dao = new AlunoDAO(context);
                        dao.remover(aluno);
                        dao.close();

                        context.carregaLista();

                    }
                })
                .setNegativeButton("Não", null)
                .show();
        return false;
    }

}
