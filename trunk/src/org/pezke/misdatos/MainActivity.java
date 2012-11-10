package org.pezke.misdatos;

import org.pezke.misdatos.layout.ControlLogin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;

public class MainActivity extends Activity {

	private ControlLogin ctlLogin;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ctlLogin = (ControlLogin) findViewById(R.id.CtlLogin);

		ctlLogin.setOnLoginListener(new OnLoginListener() {
			@Override
			public void onLogin(String usuario, String password) {
				// Validamos el usuario y la contrase�a
				if (usuario.equals("demo") && password.equals("demo"))
					ctlLogin.setMensaje("Login correcto!");
				else
					ctlLogin.setMensaje("Vuelva a intentarlo.");
			}
			
			@Override
			public void onNewAccount() {
				Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
				startActivity(intent);
	
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder builder = createAlertDialog();
			builder.show();
			// Si el listener devuelve true, el evento esta
			// procesado, y nadie debe hacer nada mas
			return true;
		}
		
		// para las demas cosas, se reenvia el evento al listener habitual
		return super.onKeyDown(keyCode, event);
	}
	
	
	/**
	 * Creado alert para la gestion del salir de la aplicación
	 */
	private AlertDialog.Builder createAlertDialog(){
		AlertDialog.Builder result = new AlertDialog.Builder(this)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setTitle(R.string.back_message)
		.setMessage(R.string.back_confirmation)
		
		//Boton negativo sin listener
		.setNegativeButton(android.R.string.cancel, null)
		
		//Boton positivo con listener
		.setPositiveButton(android.R.string.ok, createOnClickListener());
				
		return result;
	}
	
	/**
	 * Creado listener para el cuadro de dialogo para salir de la app
	 */
	private DialogInterface.OnClickListener createOnClickListener(){
		DialogInterface.OnClickListener result = 
			new DialogInterface.OnClickListener() {
			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// Salir
					MainActivity.this.finish();
				}
		};
		
		return result;
	}
	

}
