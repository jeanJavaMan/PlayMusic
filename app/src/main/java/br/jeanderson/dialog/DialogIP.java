package br.jeanderson.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.jeanderson.banco.Configuracao;
import br.jeanderson.playmusic.R;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by jeand on 16/04/2016.
 */
public class DialogIP extends AlertDialog implements View.OnClickListener {
    private Button btnSalvar, btnCancelar;
    private EditText editIp;
    private View dialogIp;
    private SweetAlertDialog dialog;
    private Configuracao configuracao;
    private Context context;

    public DialogIP(Context context) {
        super(context);
        this.context = context;
        dialogIp = View.inflate(context, R.layout.dialog_ip,null);
        btnSalvar = (Button) dialogIp.findViewById(R.id.btnSalvar);
        btnCancelar = (Button) dialogIp.findViewById(R.id.btnCancelar);
        editIp = (EditText) dialogIp.findViewById(R.id.editIp);
        btnSalvar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
        setView(dialogIp);
        configuracao = new Configuracao(context);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnSalvar){
            if(configuracao.adicionarIP(editIp.getText().toString())){
                dismiss();
                dialog = new SweetAlertDialog(context,SweetAlertDialog.SUCCESS_TYPE);
                dialog.setTitleText("Sucesso!").setContentText("IP do servidor configurado com sucesso!").show();
            }else{
                dialog = new SweetAlertDialog(context,SweetAlertDialog.ERROR_TYPE);
                dialog.setTitleText("ERRO").setContentText("Houve um erro ao salvar os dados").show();
            }
        }else if(v.getId() == R.id.btnCancelar){
            dismiss();
        }
    }
}
