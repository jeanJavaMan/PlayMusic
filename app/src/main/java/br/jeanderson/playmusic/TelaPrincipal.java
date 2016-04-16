package br.jeanderson.playmusic;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import br.jeanderson.dialog.DialogIP;

public class TelaPrincipal extends AppCompatActivity implements View.OnClickListener {
    private Button btnMusic, btnConfig;
    private DialogIP dialogIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        this.btnMusic = (Button) findViewById(R.id.btnMusic);
        this.btnConfig = (Button) findViewById(R.id.btnConfig);
        btnMusic.setOnClickListener(this);
        btnConfig.setOnClickListener(this);
        this.dialogIP = new DialogIP(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnMusic){
            Intent telaMusica = new Intent(this,TelaMusic.class);
            startActivity(telaMusica);
        }else if(v.getId() == R.id.btnConfig){
            dialogIP.show();
        }
    }
}
