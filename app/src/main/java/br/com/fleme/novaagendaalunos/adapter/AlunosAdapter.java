package br.com.fleme.novaagendaalunos.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.fleme.novaagendaalunos.R;
import br.com.fleme.novaagendaalunos.model.Aluno;

public class AlunosAdapter extends BaseAdapter {

    private final List<Aluno> alunos;
    private final Context context;

    public AlunosAdapter(List<Aluno> alunos, Context context) {
        this.alunos = alunos;
        this.context = context;
        Log.i("LOG_AGENDA", "constructor - AlunosAdapter");
    }

    //métodos abaixo retornam dados que o adapter precisa saber para passar para a lista

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

        //infla o xml transformando em uma view
        LayoutInflater inflater = LayoutInflater.from(context);

        //convertView >> utilizado para reaproveitar as views durante a rolagem de tela, ajudando na performace
        //só iremos fazer o inflate caso a convertView seja nula
        View view = convertView;
        if(convertView == null) {
            view = inflater.inflate(R.layout.list_item, parent, false);
        }

        //como estamos inflando um layout dentro de um ListView
        //o null faz com que a altura/largura que estamos usando como match_parent
        //seja trocada por wrap_content por não saber ao certo que é a tag pai para referência
        //se precisarmos que o inflate respeite os tamanhos setados no xml, precisamos informar o pai
        //no nosso caso a LIstView passa a referência dela mesma atravéz do "ViewGroup parent"

        //View view = inflater.inflate(R.layout.list_item, parent, false);

        //se não passarmos o false depois do parent, o inflate ao criar a view ele já coloca automaticamento os itens
        //dentro de uma lista igual ao parent, com isso a lista que chamou o inflate passa a receber
        //uma outra lista com os item, como não podemos colocar dois elementos iguais dentro de um componete do Android
        //tomamos uma exception
        //"usa o parent como referência mas não coloca ele dentro da lista ainda"


        TextView campoNome = view.findViewById(R.id.item_nome);
        campoNome.setText(aluno.getNome());

        TextView campoTelefone = view.findViewById(R.id.item_telefone);
        campoTelefone.setText(aluno.getTelefone());

        ImageView campoFoto = view.findViewById(R.id.item_foto);
        String caminhoFoto = aluno.getCaminhoFoto();
        if(caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        return view;
    }

}
