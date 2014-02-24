package org.pezke.misdatos.layout;

import org.pezke.misdatos.R;
import org.pezke.misdatos.activity.ConfigActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

public abstract class BaseDialog extends DialogPreference {

	/**
	 * Components
	 */
	private ConfigActivity callbackActivity;

	/**
	 * Constructor
	 */
	public BaseDialog(Context context, AttributeSet attrs) {
		super(context, attrs);
		setDialogLayoutResource(getLayout());
		setPositiveButtonText(R.string.accept);
		setNegativeButtonText(R.string.cancel);
		setDialogIcon(null);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.preference.DialogPreference#onCreateDialogView()
	 */
	@Override
	protected View onCreateDialogView() {
		View root = super.onCreateDialogView();
		createView();
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
			onClickOnPositiveButton();
			break;

		case DialogInterface.BUTTON_NEGATIVE: // User clicked Cancel!
			onClickOnNegativeButton();
			break;
		}

		super.onClick(dialog, which);
	}

	public abstract void createView();

	public abstract int getLayout();

	public abstract void onClickOnPositiveButton();

	public abstract void onClickOnNegativeButton();

	/**
	 * Show the message with Toast
	 */
	protected void showMessage(int message) {
		Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
	}

	/**
	 * @return the callbackActivity
	 */
	public ConfigActivity getCallbackActivity() {
		return callbackActivity;
	}

	/**
	 * @param callbackActivity
	 *            the callbackActivity to set
	 */
	public void setCallbackActivity(ConfigActivity callbackActivity) {
		this.callbackActivity = callbackActivity;
	}

}
