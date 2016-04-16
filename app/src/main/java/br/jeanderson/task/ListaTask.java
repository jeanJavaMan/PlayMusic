package br.jeanderson.task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by jeand on 15/04/2016.
 */
public class ListaTask extends AsyncTask<Void, Void, Boolean> {
    private ListView lvMusica;
    private ArrayAdapter<String> adapter;
    private File arqMusicas;
    private Context context;

    public ListaTask(ListView lvMusica, Context context) {
        this.lvMusica = lvMusica;
        this.context = context;
        this.arqMusicas = new File("/sdcard/Musicas/");
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        if (!arqMusicas.exists()) {
            arqMusicas.mkdir();
        }
        String[] listaMusicas = arqMusicas.list();
        if(listaMusicas.length == 0){
            return false;
        }else{
            adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,listaMusicas);
            return true;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if(aBoolean){
            this.lvMusica.setAdapter(adapter);
        }else{
            Toast.makeText(context,"Nenhuma musica encontrada",Toast.LENGTH_SHORT).show();
        }
    }
}
