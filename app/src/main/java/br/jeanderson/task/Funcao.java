package br.jeanderson.task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by jeand on 15/04/2016.
 */
public class Funcao {
    private String ipServer;
    private Context context;
    private final int TOCAR_MUSICA = 1;
    private final int PARAR_MUSICA = 2;
    private final int AUMENTAR_VOLUME = 3;
    private final int DIMINUIR_VOLUME = 4;
    private final int BUFFER_LIMIT = 4096;

    public Funcao(String ipServer, Context context) {
        this.ipServer = ipServer;
        this.context = context;
    }

    public void enviarMusica(final String nomeDaMusica) {
        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    File musica = new File("/sdcard/Musicas/" + nomeDaMusica);
                    FileInputStream pegar = new FileInputStream(musica);
                    Socket cliente = new Socket(ipServer, 8485);
                    ObjectOutputStream enviar = new ObjectOutputStream(cliente.getOutputStream());
                    enviar.writeInt(TOCAR_MUSICA);
                    enviar.flush();
                    enviar.writeUTF(nomeDaMusica);
                    byte[] buf = new byte[BUFFER_LIMIT];
                    while (true) {
                        int len = pegar.read(buf);
                        if (len == -1) {
                            break;
                        }
                        enviar.write(buf, 0, len);
                    }
                    enviar.flush();
                    enviar.close();
                    pegar.close();
                    cliente.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (!aBoolean) {
                    Toast.makeText(context, "Não foi possivel se conectar com o servidor! verifique o IP", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    public void pararMusica() {
        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    Socket cliente = new Socket(ipServer, 8485);
                    ObjectOutputStream enviar = new ObjectOutputStream(cliente.getOutputStream());
                    enviar.writeInt(PARAR_MUSICA);
                    enviar.flush();
                    enviar.close();
                    cliente.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (!aBoolean) {
                    Toast.makeText(context, "Não foi possivel se conectar com o servidor! verifique o IP", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    public void aumentarVolume() {
        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    Socket cliente = new Socket(ipServer, 8485);
                    ObjectOutputStream enviar = new ObjectOutputStream(cliente.getOutputStream());
                    enviar.writeInt(AUMENTAR_VOLUME);
                    enviar.flush();
                    enviar.close();
                    cliente.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (!aBoolean) {
                    Toast.makeText(context, "Não foi possivel se conectar com o servidor! verifique o IP", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    public void diminuirVolume() {
        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    Socket cliente = new Socket(ipServer, 8485);
                    ObjectOutputStream enviar = new ObjectOutputStream(cliente.getOutputStream());
                    enviar.writeInt(DIMINUIR_VOLUME);
                    enviar.flush();
                    enviar.close();
                    cliente.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (!aBoolean) {
                    Toast.makeText(context, "Não foi possivel se conectar com o servidor! verifique o IP", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }
}
