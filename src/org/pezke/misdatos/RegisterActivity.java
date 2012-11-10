package org.pezke.misdatos;

import org.pezke.misdatos.layout.ControlRegister;
import org.pezke.misdatos.listener.RegisterListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class RegisterActivity extends Activity {

	/**
	 * Control
	 */
	private ControlRegister controlRegister;

	
    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
		controlRegister = (ControlRegister) findViewById(R.id.CtlRegister);
		controlRegister.setRegisterListener(new RegisterListener() {
			
			/*
			 * (non-Javadoc)
			 * @see org.pezke.misdatos.listener.RegisterListener#onRegister(java.lang.String, java.lang.String, java.lang.String)
			 */
			public void onRegister(String user, String password1, String password2) {
				boolean check = controlRegister.checkUser(user);
				if(check){
					check = controlRegister.checkPasswords(password1, password2);
					if(check){
						
					}else{
						controlRegister.setMessage(R.string.error_register_different);
					}
				}else{
					controlRegister.setMessage(R.string.error_register_existent);
				}
			}
			
		});
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_register, menu);
        return true;
    }
}
