package br.jeanderson.task;

import android.os.AsyncTask;

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

    public Funcao(String ipServer) {
        this.ipServer = ipServer;
    }

    public void enviarMusica(final String nomeDaMusica) {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    File musica = new File("/sdcard/Musicas/" + nomeDaMusica);
                    FileInputStream pegar = new FileInputStream(musica);
                    Socket cliente = new Socket(ipServer, 8485);
                    ObjectOutputStream enviar = new ObjectOutputStream(cliente.getOutputStream());
                    enviar.writeInt(1);
                    enviar.flush();
                    byte[] buf = new byte[4096];
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
                }
                return null;
            }
        }.execute();
    }

    public void pararMusica() {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Socket cliente = new Socket(ipServer, 8485);
                    ObjectOutputStream enviar = new ObjectOutputStream(cliente.getOutputStream());
                    enviar.writeInt(2);
                    enviar.flush();
                    enviar.close();
                    cliente.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    public void aumentarVolume() {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Socket cliente = new Socket(ipServer, 8485);
                    ObjectOutputStream enviar = new ObjectOutputStream(cliente.getOutputStream());
                    enviar.writeInt(3);
                    enviar.flush();
                    enviar.close();
                    cliente.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    public void diminuirVolume() {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Socket cliente = new Socket(ipServer, 8485);
                    ObjectOutputStream enviar = new ObjectOutputStream(cliente.getOutputStream());
                    enviar.writeInt(4);
                    enviar.flush();
                    enviar.close();
                    cliente.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
}
