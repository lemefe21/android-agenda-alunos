package br.com.fleme.novaagendaalunos.helper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
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
    private final ImageView campoFoto;

    public FormularioHelper(FormularioActivity activity) {
        campoNome = activity.findViewById(R.id.formulario_nome);
        campoEndereco = activity.findViewById(R.id.formulario_endereco);
        campoTelefone = activity.findViewById(R.id.formulario_telefone);
        campoSite = activity.findViewById(R.id.formulario_site);
        campoNota = activity.findViewById(R.id.formulario_nota);
        campoFoto = activity.findViewById(R.id.formulario_foto);
        aluno = new Aluno();
    }

    public Aluno pegaAlunoDoFormulario() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));
        aluno.setCaminhoFoto(campoFoto.getTag().toString());
        return aluno;
    }

    public void preencheFormulario(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoEndereco.setText(aluno.getEndereco());
        campoTelefone.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        campoNota.setProgress(aluno.getNota().intValue());
        carregaImagem(aluno.getCaminhoFoto());
        this.aluno = aluno;
    }

    public void carregaImagem(String caminhoFoto) {

        //para alunos sem fotos já cadastrados
        if(caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            //bitmap não suporta imagens com resolução muito alta
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            //aumenta a imagem para ocupar o espaço total da sua view
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            //método da View para associar informações
            campoFoto.setTag(caminhoFoto);
        }

    }
}
