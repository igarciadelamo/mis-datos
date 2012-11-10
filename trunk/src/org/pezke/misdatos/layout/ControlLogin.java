package org.pezke.misdatos.layout;

import org.pezke.misdatos.OnLoginListener;
import org.pezke.misdatos.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ControlLogin extends LinearLayout {

	//////////////////////////////////
	// Controles
	//////////////////////////////////
	
	private EditText txtUser;
	private EditText txtPassword;
	private Button btnLogin;
	private Button btnCreateAccount;
	private TextView lblMensaje;

	
	//////////////////////////////////
	// Listener
	//////////////////////////////////

	private OnLoginListener listener;

	
	//////////////////////////////////
	// Constructores
	//////////////////////////////////


	/**
	 * Constructor
	 */
	public ControlLogin(Context context) {
		super(context);
		init();
	}
	
	/**
	 * Constructor con atributos
	 */
	public ControlLogin(Context context, AttributeSet attrs) {
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
		li.inflate(R.layout.control_login, this, true);

		// Obtenemoslas referencias a los distintos control
		txtUser = (EditText) findViewById(R.id.txtLoginUser);
		txtPassword = (EditText) findViewById(R.id.txtLoginPassword);
		btnLogin = (Button) findViewById(R.id.buttonLogin);
		btnCreateAccount = (Button) findViewById(R.id.buttonNewAccount);
		lblMensaje = (TextView) findViewById(R.id.labelLoginMessage);

		// Asociamos los eventos necesarios
		manageEvents();
	}

	/**
	 * Setter del listener
	 */
	public void setOnLoginListener(OnLoginListener l) {
		listener = l;
	}

	/**
	 * Gestion de eventos
	 */
	private void manageEvents() {
		btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onLogin(
						txtUser.getText().toString(), 
						txtPassword.getText().toString());
			}
		});
		
		btnCreateAccount.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onNewAccount();
			}
		});
	}

	/**
	 * Almacenar el mensaje de error
	 */
	public void setMensaje(String msg) {
		lblMensaje.setText(msg);
	}
}
