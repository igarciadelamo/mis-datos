package org.pezke.misdatos.activity;

import java.util.Date;

import org.pezke.misdatos.R;
import org.pezke.misdatos.model.ListElement;
import org.pezke.misdatos.util.DateUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DataActivity extends Activity {

	//DataList
	private ListElement[] datos = null;
	
    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_data);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_data);
        
        String keyLastAccess = getString(R.string.label_lastAccess);
    	String keyNumAccess  = getString(R.string.label_numAccess);
    	
    	datos = new ListElement[0];
    	datos = new ListElement[25];
        for (int i = 1; i <= 25; i++){
        	ListElement item = new ListElement("Título " + i);
        	item.setLastAccess(keyLastAccess + DateUtils.formatDateTime(new Date()));
        	item.setNumAccess(keyNumAccess + i);
			datos[i - 1] = item;
        }
                
        MyDataAdapter adaptador = new MyDataAdapter(this);
		ListView lstOpciones = (ListView) findViewById(R.id.CtlList);
		lstOpciones.setAdapter(adaptador);
    }
    
    /**
     * Adapter for the data list
     */
    private class MyDataAdapter extends ArrayAdapter<ListElement> {

		private Activity context;

		public MyDataAdapter(Activity context) {
			super(context, R.layout.control_list, datos);
			this.context = context;
		}

		/*
		 * (non-Javadoc)
		 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			View item = convertView;
			ViewHolder holder;

			if (item == null) {
				LayoutInflater inflater = context.getLayoutInflater();
				item = inflater.inflate(R.layout.control_list, null);

				holder = new ViewHolder();
				holder.tvName = (TextView) item.findViewById(R.id.textDataName);
				holder.tvNumAccess = (TextView) item.findViewById(R.id.textDataLabel1);
				holder.tvLastAccess = (TextView) item.findViewById(R.id.textDataLabel2);

				item.setTag(holder);
				
			} else {
				holder = (ViewHolder) item.getTag();
			}
			
			ListElement data = datos[position];
			holder.completeData(data);
			return (item);
		}
	}

    /**
     * Inner class to model the view
     */
	private static class ViewHolder {
		protected TextView tvName;
		protected TextView tvLastAccess;
		protected TextView tvNumAccess;
		
		/**
		 * Fill the text with the information in data
		 * @param data ListElement to complete the textviews
		 */
		protected void completeData(ListElement data){
			this.tvName.setText(data.getName());
			this.tvLastAccess.setText(data.getLastAccess());
			this.tvNumAccess.setText(data.getNumAccess());
		}
		
		
	}
    
    
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.menu_addElement:
			System.err.println("Opcion 1 pulsada!");
			//lblMensaje.setText("Opcion 1 pulsada!");
			return true;

		case R.id.menu_settings:
			Intent intent = new Intent(DataActivity.this, ConfigActivity.class);
			startActivity(intent);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}



    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_data, menu);
        return true;
    }
}