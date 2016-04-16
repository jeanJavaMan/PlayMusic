package br.jeanderson.banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jeand on 16/04/2016.
 */
public class ConfigBanco extends SQLiteOpenHelper {
    private Context context;
    private static int versao = 1;
    public static final String NOME_BANCO = "configuracao";
    public static final String TABELA = "configuracao";
    public static final String IP = "ipServer";

    public ConfigBanco(Context context) {
        super(context, NOME_BANCO, null, versao);
       this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS "+TABELA+" ("+IP+" STRING);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABELA;
        db.execSQL(sql);
        onCreate(db);
    }
}
