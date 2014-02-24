package org.pezke.misdatos.layout;

import org.pezke.misdatos.R;

import android.content.Context;
import android.util.AttributeSet;

public class DeleteAccountDialog extends BaseDialog {

	/**
	 * Constructor
	 */
	public DeleteAccountDialog(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void createView() {
	}

	@Override
	public int getLayout() {
		return R.layout.dialog_delete_account;
	}

	@Override
	public void onClickOnPositiveButton() {
	    getCallbackActivity().deleteAccount();
	}

	@Override
	public void onClickOnNegativeButton() {
	}

}
