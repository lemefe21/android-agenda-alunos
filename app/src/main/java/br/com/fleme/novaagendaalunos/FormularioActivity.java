package br.com.fleme.novaagendaalunos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import br.com.fleme.novaagendaalunos.dao.AlunoDAO;
import br.com.fleme.novaagendaalunos.helper.FormularioHelper;
import br.com.fleme.novaagendaalunos.model.Aluno;

public class FormularioActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_IMAGE_CAPTURE = 567;
    private FormularioHelper helper;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        Log.i("LOG_AGENDA", "onCreate - FormularioActivity");

        helper = new FormularioHelper(this);

        final Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno_selecionado");
        if(aluno != null) {
            helper.preencheFormulario(aluno);
        }

        Button botaoFoto = findViewById(R.id.formulario_btn_foto);
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //devemos informar que queremos salvar a imagem dentro da pasta da nossa aplicação
                //null >> subpastas
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(caminhoFoto);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                //essa startActivity nos possibilita obter o resultado que a activity irá retornar
                startActivityForResult(intentCamera, REQUEST_CODE_IMAGE_CAPTURE);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_formulario_ok:

                Log.i("LOG_AGENDA", "Click - Menu menu_formulario_ok");

                //se o aluno veio pela Intent ele já possui um id
                // objeto é atualizado no preencheFormulario do helper
                Aluno aluno = helper.pegaAlunoDoFormulario();

                AlunoDAO dao = new AlunoDAO(this);
                if(aluno.getId() != null) {
                    dao.altera(aluno);
                    Toast.makeText(FormularioActivity.this, "Aluno " + aluno.getNome() + " atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    dao.insere(aluno);
                    Toast.makeText(FormularioActivity.this, "Aluno " + aluno.getNome() + " salvo com sucesso!", Toast.LENGTH_SHORT).show();
                }
                dao.close();

                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //verificamos se o retorno está ok (para evitar erro ao cancelar a captura)
        if(resultCode == Activity.RESULT_OK) {
            //quando a activity chamada no startActivityForResult terminar a ação devolvendo o resultado
            switch (requestCode) {
                case REQUEST_CODE_IMAGE_CAPTURE:

                    ImageView foto = findViewById(R.id.formulario_foto);
                    Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
                    //bitmap não suporta imagens com resolução muito alta
                    Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                    foto.setImageBitmap(bitmapReduzido);
                    //aumenta a imagem para ocupar o espaço total da sua view
                    foto.setScaleType(ImageView.ScaleType.FIT_XY);
                    break;

            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("LOG_AGENDA", "onStart - FormularioActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("LOG_AGENDA", "onResume - FormularioActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //activity parcialmente visivel, por exemplo ao abrir um pop-up
        Log.i("LOG_AGENDA", "onPause - FormularioActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("LOG_AGENDA", "onStop - FormularioActivity");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("LOG_AGENDA", "onRestart - FormularioActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("LOG_AGENDA", "onDestroy - FormularioActivity");
    }

}
