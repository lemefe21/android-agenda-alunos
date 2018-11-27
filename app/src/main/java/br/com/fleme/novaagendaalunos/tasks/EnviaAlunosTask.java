package br.com.fleme.novaagendaalunos.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import br.com.fleme.novaagendaalunos.converter.AlunoConverter;
import br.com.fleme.novaagendaalunos.dao.AlunoDAO;
import br.com.fleme.novaagendaalunos.model.Aluno;
import br.com.fleme.novaagendaalunos.services.WebClient;

public class EnviaAlunosTask extends AsyncTask<Void, Void, String> {

    //podemos mudar os valores utilizados pela AsyncTask<?, ?, ?>
    //ex: AsyncTask<Integer, Object, String>
    //1º parâmetro usado no doInBackground >> (Object[] objects) para podermos restringir algum tipo especifico
    //utilizando Void para indicar que não estamos utilizando
    //3º é o tipo do retorno que precisamos

    private Context context;
    private ProgressDialog dialog;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Aguarde","Enviando alunos...", true, true);
    }

    @Override
    protected String doInBackground(Void... objects) {

        Log.i("LOG_AGENDA", "EnviaAlunosTask - doInBackground");

        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converteParaJSON(alunos);

        Log.i("LOG_AGENDA", "json: " + json);

        WebClient client = new WebClient();
        String response = client.post(json);

        return response;

    }

    @Override
    protected void onPostExecute(String response) {

        dialog.dismiss();

        //onPostExecute é executado na thread principal com o resultado/retorno do doInBackground
        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();

    }
}
