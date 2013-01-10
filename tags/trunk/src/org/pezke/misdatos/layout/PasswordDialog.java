package org.pezke.misdatos.layout;

import org.pezke.misdatos.R;
import org.pezke.misdatos.dao.DbManager;
import org.pezke.misdatos.dao.UserDao;

import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordDialog extends DialogPreference {

	/**
	 * Components
	 */
	private String login;
	private EditText password1Text;	
	private EditText password2Text;
	
	/**
	 * DAOs
	 */
	private UserDao userDao;
	
	/**
	 * Constructor
	 */
	public PasswordDialog(Context context, AttributeSet attrs) {
		super(context, attrs);
		setDialogLayoutResource(R.layout.dialog_password);
        setPositiveButtonText(R.string.accept);
        setNegativeButtonText(R.string.cancel);
        setDialogIcon(null);
        
        //Creating the dao
        DbManager dbManager = DbManager.getInstance(context);
        userDao = new UserDao(dbManager);
	}
	

	/*
	 * (non-Javadoc)
	 * @see android.preference.DialogPreference#onCreateDialogView()
	 */
	@Override
	protected View onCreateDialogView() {
	  View root = super.onCreateDialogView();
	  password1Text = (EditText) root.findViewById(R.id.txtDialogPassword1);
	  password2Text = (EditText) root.findViewById(R.id.txtDialogPassword2);
	  return root;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.preference.DialogPreference#onClick(android.content.DialogInterface
	 * , int)
	 */
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case DialogInterface.BUTTON_POSITIVE: // User clicked OK!
			String password1 = password1Text.getText().toString();
			String password2 = password2Text.getText().toString();
			controlChangePassword(login, password1, password2);
			break;

		case DialogInterface.BUTTON_NEGATIVE: // User clicked Cancel!
			break;
		}	
		
		super.onClick(dialog, which);
	}
	
	
	/**
	 * Show the message with Toast
	 */
	private void showMessage(int message){
		Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
	}
	
	
	/**
	 * Manage the change of the password
	 */
	private boolean controlChangePassword(String login, String password1, String password2) {
		boolean result = false;
		boolean check = userDao.checkByLogin(login);
		if(check){
			check = userDao.checkPasswords(password1, password2);
			if(check){
				userDao.update(login, password1);
				showMessage(R.string.success_config);
				result = true;
			}else{
				showMessage(R.string.error_password);
			}
		}else{
			showMessage(R.string.error_general);
		}
		return result;
	}


	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}


}
