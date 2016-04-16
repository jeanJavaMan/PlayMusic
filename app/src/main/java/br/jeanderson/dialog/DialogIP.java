package br.jeanderson.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.jeanderson.playmusic.R;

/**
 * Created by jeand on 16/04/2016.
 */
public class DialogIP extends AlertDialog implements View.OnClickListener {
    private Button btnSalvar, btnCancelar;
    private EditText editIp;
    private View dialogIp;

    protected DialogIP(Context context) {
        super(context);
        dialogIp = View.inflate(context, R.layout.dialog_ip,null);
        btnSalvar = (Button) dialogIp.findViewById(R.id.btnSalvar);
        btnCancelar = (Button) dialogIp.findViewById(R.id.btnCancelar);
        editIp = (EditText) dialogIp.findViewById(R.id.editIp);
        btnSalvar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
        setView(dialogIp);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnSalvar){

        }else if(v.getId() == R.id.btnCancelar){

        }
    }
}
