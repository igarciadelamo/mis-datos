package org.pezke.misdatos.layout;

import org.pezke.misdatos.OnRegisterListener;
import org.pezke.misdatos.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ControlRegister extends LinearLayout {

	//////////////////////////////////
	// Controles
	//////////////////////////////////
	
	private EditText txtUser;
	private EditText txtPassword1;
	private EditText txtPassword2;
	private Button btnAccept;
	private TextView message;

	
	//////////////////////////////////
	// Listener
	//////////////////////////////////

	private OnRegisterListener listener;

	
	//////////////////////////////////
	// Constructores
	//////////////////////////////////


	/**
	 * Constructor
	 */
	public ControlRegister(Context context) {
		super(context);
		init();
	}
	
	/**
	 * Constructor con atributos
	 */
	public ControlRegister(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	
	//////////////////////////////////
	// Metodos auxiliares
	//////////////////////////////////
	
	/**
	 * Inicializacion del control
	 */
	private void init() {
		
		// Utilizamos el layout 'control_login' como interfaz del control
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
		li.inflate(R.layout.control_register, this, true);

		// Obtenemoslas referencias a los distintos control
		txtUser = (EditText) findViewById(R.id.txtRegisterUser);
		txtPassword1 = (EditText) findViewById(R.id.txtRegisterPassword1);
		txtPassword2 = (EditText) findViewById(R.id.txtRegisterPassword2);
		btnAccept = (Button) findViewById(R.id.buttonRegister);
		this.message = (TextView) findViewById(R.id.labelRegisterMessage);

		// Asociamos los eventos necesarios
		manageEvents();
	}

	/**
	 * Setter del listener
	 */
	public void setOnRegisterListener(OnRegisterListener l) {
		listener = l;
	}

	/**
	 * Gestion de eventos
	 */
	private void manageEvents() {
		btnAccept.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onRegister(
						txtUser.getText().toString(), 
						txtPassword1.getText().toString(),
						txtPassword2.getText().toString());
			}
		});
	}

	/**
	 * Almacenar el mensaje de error
	 */
	public void setMensaje(String msg) {
		message.setText(msg);
	}
}
