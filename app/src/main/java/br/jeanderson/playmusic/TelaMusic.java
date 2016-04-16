package br.jeanderson.playmusic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import br.jeanderson.task.Funcao;
import br.jeanderson.task.ListaTask;

public class TelaMusic extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private TextView txtTocando;
    private Button btnParar, btnAumentar, btnDiminuir;
    private ListView lvMusicas;
    private ListaTask task;
    private Funcao funcao;
    private int contador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_music);
        this.txtTocando = (TextView) findViewById(R.id.txtTocando);
        this.btnAumentar = (Button) findViewById(R.id.btnAumentar);
        this.btnDiminuir = (Button) findViewById(R.id.btnDiminuir);
        this.btnParar = (Button) findViewById(R.id.btnParar);
        this.lvMusicas = (ListView) findViewById(R.id.lvMusicas);
        this.lvMusicas.setOnItemClickListener(this);
        this.task = new ListaTask(lvMusicas, this);
        this.task.execute();
        btnParar.setEnabled(false);
        btnAumentar.setEnabled(false);
        btnDiminuir.setEnabled(false);
        btnParar.setOnClickListener(this);
        btnAumentar.setOnClickListener(this);
        btnDiminuir.setOnClickListener(this);
        this.funcao = new Funcao("192.168.1.3");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String nomeDamusica = ((ArrayAdapter<String>) parent.getAdapter()).getItem(position);
        this.funcao.enviarMusica(nomeDamusica);
        this.txtTocando.setText(nomeDamusica);
        if (!btnParar.isEnabled()) {
            btnParar.setEnabled(true);
            btnAumentar.setEnabled(true);
            btnDiminuir.setEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnParar){
            this.funcao.pararMusica();
            this.btnParar.setEnabled(false);
            btnAumentar.setEnabled(false);
            btnDiminuir.setEnabled(false);
            txtTocando.setText("Nada");
        }else if(v.getId() == R.id.btnAumentar){
            contador++;
            funcao.aumentarVolume();
            if(contador == 5){
                btnAumentar.setEnabled(false);
            }
            if(contador == 1){
                btnDiminuir.setEnabled(true);
            }

        }else if(v.getId() == R.id.btnDiminuir){
            contador--;
            funcao.diminuirVolume();
            if(contador == 0){
                btnDiminuir.setEnabled(false);
            }
            if(contador == 4){
                btnAumentar.setEnabled(true);
            }
        }
    }
}
