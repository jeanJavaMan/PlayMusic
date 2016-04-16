package br.jeanderson.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by jeand on 16/04/2016.
 */
public class Configuracao {
    private ConfigBanco configBanco;
    private Context context;
    private SQLiteDatabase db;

    public Configuracao(Context context) {
        this.context = context;
        configBanco = new ConfigBanco(context);
    }

    /**
     * Pega o IP que está no banco de dados.
     *
     * @return - retorna o IP em formato String.
     * @throws - Caso não acha um IP no Banco retorna a exceção NullPointerException.
     */
    public String getIpServer() {
        db = configBanco.getReadableDatabase();
        String[] coluna = {ConfigBanco.IP};
        Cursor cursor = db.query(ConfigBanco.TABELA, coluna, null, null, null, null, null);
        db.close();
        if (cursor.getCount() <= 0) {
            db.close();
            cursor.close();
            throw new NullPointerException();
        } else {
            cursor.moveToFirst();
            cursor.moveToPosition(cursor.getColumnIndexOrThrow(ConfigBanco.IP));
            String ipServer = cursor.getString(cursor.getColumnIndexOrThrow(ConfigBanco.IP));
            cursor.close();
            return ipServer;
        }
    }

    /**
     *  Método grava no banco o IP do servidor, caso já tenha um IP no server, ele
     *  realizara um update.
     * @param ipServer
     * @return - Se o processo ocorreu com sucesso ou não.
     */
    public boolean adicionarIP(String ipServer) {
        boolean existe;
        long resultado = -1;
        try {
            String ip = getIpServer();
            existe = true;
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            existe = false;
        }
        ContentValues valores = new ContentValues();
        valores.put(ConfigBanco.IP, ipServer);
        db = configBanco.getWritableDatabase();
        if (existe) {
            resultado = db.update(ConfigBanco.TABELA, valores, null, null);
            db.close();
        } else {
            resultado = db.insertOrThrow(ConfigBanco.TABELA, ConfigBanco.IP, valores);
            db.close();
        }
        if (resultado == -1) {
            return false;
        } else {
            return true;
        }
    }
}
