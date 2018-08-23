package br.com.fleme.novaagendaalunos.helper;

import android.app.Activity;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import br.com.fleme.novaagendaalunos.FormularioActivity;
import br.com.fleme.novaagendaalunos.R;
import br.com.fleme.novaagendaalunos.model.Aluno;

public class FormularioHelper {

    private Aluno aluno;
    private final TextView campoNome;
    private final TextView campoEndereco;
    private final TextView campoTelefone;
    private final TextView campoSite;
    private final RatingBar campoNota;

    public FormularioHelper(FormularioActivity activity) {
        campoNome = activity.findViewById(R.id.formulario_nome);
        campoEndereco = activity.findViewById(R.id.formulario_endereco);
        campoTelefone = activity.findViewById(R.id.formulario_telefone);
        campoSite = activity.findViewById(R.id.formulario_site);
        campoNota = activity.findViewById(R.id.formulario_nota);
        aluno = new Aluno();
    }

    public Aluno pegaAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));
        return aluno;
    }

    public void preencheFormulario(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoEndereco.setText(aluno.getEndereco());
        campoTelefone.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        campoNota.setProgress(aluno.getNota().intValue());
        this.aluno = aluno;
    }
}
