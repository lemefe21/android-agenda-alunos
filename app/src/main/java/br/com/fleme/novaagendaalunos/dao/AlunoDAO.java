package br.com.fleme.novaagendaalunos.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AlunoDAO extends SQLiteOpenHelper {


    public AlunoDAO(Context context) {
        super(context, "AGENDA_DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("LOG_AGENDA", "onCreate - SQLiteDatabase");
        String sql = "CREATE TABLE Alunos (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT, nota REAL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("LOG_AGENDA", "onUpgrade - SQLiteDatabase");
        String sql = "DROP TABLE IF EXISTS Alunos";
        db.execSQL(sql);
        onCreate(db);
    }

}
