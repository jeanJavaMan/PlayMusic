package br.jeanderson.playmusic;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import br.jeanderson.banco.Configuracao;
import br.jeanderson.task.Funcao;
import br.jeanderson.task.ListaTask;
import br.jeanderson.util.GlideTarget;

public class TelaMusic extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private TextView txtTocando;
    private Button btnParar, btnAumentar, btnDiminuir;
    private ListView lvMusicas;
    private ListaTask task;
    private Funcao funcao;
    private int contador;
    private RelativeLayout telaMusicas;
    private Configuracao config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_music);
        this.txtTocando = (TextView) findViewById(R.id.txtTocando);
        this.btnAumentar = (Button) findViewById(R.id.btnAumentar);
        this.btnDiminuir = (Button) findViewById(R.id.btnDiminuir);
        this.btnParar = (Button) findViewById(R.id.btnParar);
        this.lvMusicas = (ListView) findViewById(R.id.lvMusicas);
        this.telaMusicas = (RelativeLayout) findViewById(R.id.TelaMusicas);
        this.lvMusicas.setOnItemClickListener(this);
        this.task = new ListaTask(lvMusicas, this);
        this.task.execute();
        btnParar.setEnabled(false);
        btnAumentar.setEnabled(false);
        btnDiminuir.setEnabled(false);
        btnParar.setOnClickListener(this);
        btnAumentar.setOnClickListener(this);
        btnDiminuir.setOnClickListener(this);
        config = new Configuracao(this);
        try {
            String ipServer = config.getIpServer();
            this.funcao = new Funcao(ipServer,this);
        } catch (NullPointerException ex) {
            Snackbar.make(lvMusicas, "Nenhum IP configurado, por favor configure um IP antes!", Snackbar.LENGTH_LONG).show();
        }
        Glide.with(this).load(R.drawable.iurd_fundo).asBitmap().into(new GlideTarget(this,telaMusicas));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (funcao != null) {
            String nomeDamusica = ((ArrayAdapter<String>) parent.getAdapter()).getItem(position);
            this.funcao.enviarMusica(nomeDamusica);
            this.txtTocando.setText(nomeDamusica);
            if (!btnParar.isEnabled()) {
                btnParar.setEnabled(true);
                btnAumentar.setEnabled(true);
                btnDiminuir.setEnabled(true);
            }
        }else{
            Snackbar.make(view,"Por favor configure um IP antes!",Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnParar) {
            this.funcao.pararMusica();
            this.btnParar.setEnabled(false);
            btnAumentar.setEnabled(false);
            btnDiminuir.setEnabled(false);
            txtTocando.setText("Nada");
        } else if (v.getId() == R.id.btnAumentar) {
            contador++;
            funcao.aumentarVolume();
            if (contador == 5) {
                btnAumentar.setEnabled(false);
            }
            if (contador == 1) {
                btnDiminuir.setEnabled(true);
            }

        } else if (v.getId() == R.id.btnDiminuir) {
            contador--;
            funcao.diminuirVolume();
            if (contador == 0) {
                btnDiminuir.setEnabled(false);
            }
            if (contador == 4) {
                btnAumentar.setEnabled(true);
            }
        }
    }
}
