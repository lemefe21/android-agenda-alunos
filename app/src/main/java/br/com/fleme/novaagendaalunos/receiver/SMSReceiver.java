package br.com.fleme.novaagendaalunos.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.fleme.novaagendaalunos.R;
import br.com.fleme.novaagendaalunos.dao.AlunoDAO;

public class SMSReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");

        //tratar os dois casos para que ela funcione corretamente de acordo com a versão da API
        //pegamos somente a primeira parte dos pdus porque precisamos do cabeçalho apenas
        SmsMessage sms = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String formato = intent.getStringExtra("format");
            sms = SmsMessage.createFromPdu((byte[]) pdus[0], formato);
        } else {
            sms = SmsMessage.createFromPdu((byte[]) pdus[0]);
        }

        String telefone = sms.getDisplayOriginatingAddress();
        AlunoDAO dao = new AlunoDAO(context);
        if(dao.ehAluno(telefone)) {
            Toast.makeText(context, "Chegou um SMS de um Aluno!", Toast.LENGTH_SHORT).show();
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();
        }

        Toast.makeText(context, "Chegou um SMS! sms: " + sms + ", telefone: " + telefone, Toast.LENGTH_SHORT).show();

    }

}
