package org.pezke.misdatos.activity;

import org.pezke.misdatos.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class AboutActivity extends Activity {

	/*
     * (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_data);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_data);
    }
    
}
