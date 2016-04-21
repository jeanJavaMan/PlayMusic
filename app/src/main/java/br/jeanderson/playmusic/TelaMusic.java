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
import android.widget.SeekBar;
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
    private SeekBar sbProgresso;
    private ListaTask task;
    private Funcao funcao;
    private int volumeCount = 20;
    private RelativeLayout telaMusicas;
    private Configuracao config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_music);
        inicializarComponentes();

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
        sbProgresso.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(volumeCount > progress){
                    funcao.diminuirVolume();
                }else if(volumeCount < progress){
                    funcao.aumentarVolume();
                }
                volumeCount = progress;
                if (volumeCount == 20) {
                    btnAumentar.setEnabled(false);
                }
                if (volumeCount == 1) {
                    btnDiminuir.setEnabled(true);
                }
                if (volumeCount == 0) {
                    btnDiminuir.setEnabled(false);
                }
                if (volumeCount == 19) {
                    btnAumentar.setEnabled(true);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //sem necessidade
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //sem necessidade
            }
        });
    }

    private void inicializarComponentes() {
        this.txtTocando = (TextView) findViewById(R.id.txtTocando);
        this.btnAumentar = (Button) findViewById(R.id.btnAumentar);
        this.btnDiminuir = (Button) findViewById(R.id.btnDiminuir);
        this.btnParar = (Button) findViewById(R.id.btnParar);
        this.lvMusicas = (ListView) findViewById(R.id.lvMusicas);
        this.telaMusicas = (RelativeLayout) findViewById(R.id.TelaMusicas);
        this.sbProgresso = (SeekBar) findViewById(R.id.sbProgresso);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (funcao != null) {
            String nomeDamusica = ((ArrayAdapter<String>) parent.getAdapter()).getItem(position);
            this.funcao.enviarMusica(nomeDamusica);
            this.txtTocando.setText(nomeDamusica);
            sbProgresso.setProgress(20);
            volumeCount = 20;
            btnAumentar.setEnabled(false);
            if (!btnParar.isEnabled()) {
                btnParar.setEnabled(true);
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
            volumeCount++;
            sbProgresso.setProgress(volumeCount);
            funcao.aumentarVolume();
            if (volumeCount == 20) {
                btnAumentar.setEnabled(false);
            }
            if (volumeCount == 1) {
                btnDiminuir.setEnabled(true);
            }

        } else if (v.getId() == R.id.btnDiminuir) {
            volumeCount--;
            sbProgresso.setProgress(volumeCount);
            funcao.diminuirVolume();
            if (volumeCount == 0) {
                btnDiminuir.setEnabled(false);
            }
            if (volumeCount == 19) {
                btnAumentar.setEnabled(true);
            }
        }
    }


}
