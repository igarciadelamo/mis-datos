package org.pezke.misdatos.activity;

import java.util.Date;
import java.util.List;

import org.pezke.misdatos.R;
import org.pezke.misdatos.dao.DataDao;
import org.pezke.misdatos.dao.DbManager;
import org.pezke.misdatos.model.Data;
import org.pezke.misdatos.model.ListElement;
import org.pezke.misdatos.util.CommonConstants;
import org.pezke.misdatos.util.DateUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class DataActivity extends Activity {

	//DataList
	private ListElement[] datos = null;
	
	//Dialog
	private Dialog dialog = null;
	
	//DAO
	DataDao dataDao = null;
	
	
    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_data);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_data);
    
    	//Create the database
		DbManager dbManager = DbManager.getInstance(this);
		this.dataDao = new DataDao(dbManager);
	
		//List
		calculateList();
		 
        //adapter
    	MyDataAdapter adaptador = new MyDataAdapter(this);
		ListView lstOpciones = (ListView) findViewById(R.id.CtlList);
		lstOpciones.setAdapter(adaptador);
    }

    /**
     * Compose the list
     */
	private void calculateList() {
		String keyLastAccess = getString(R.string.label_lastAccess);
    	String keyNumAccess  = getString(R.string.label_numAccess);
    	
    	//Get the login
		SharedPreferences preferences = 
				getSharedPreferences(CommonConstants.DATA, Context.MODE_PRIVATE);
		String login = preferences.getString(CommonConstants.LOGIN, "");
		List<Data> list = dataDao.getByLogin(login);
		if(list!=null){
			datos = new ListElement[list.size()];
			for (int i = 0; i < list.size(); i++){
				Data data = list.get(i);
	        	ListElement item = new ListElement(data.getKey());
	        	item.setLastAccess(keyLastAccess + DateUtils.formatDateTime(new Date()));
	        	item.setNumAccess(keyNumAccess + i);
				datos[i - 1] = item;
	        }
		}else{
			datos = new ListElement[0];
		}
		
		if(datos.length != 0){
			LinearLayout textNoData = (LinearLayout)findViewById(R.id.layout_nodata);
			textNoData.setVisibility(View.INVISIBLE);
		}
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
			createDialog();
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
    
    
    /**
     * Create the dialog to add a new element to the list
     */
    private void createDialog(){
    	
    	//Create the dialog if it does not exist
    	if(this.dialog == null){
    		
    		dialog = new Dialog(DataActivity.this);
    		dialog.setContentView(R.layout.dialog_add);
    		dialog.setTitle(R.string.label_addData);
    		dialog.setCancelable(true);
    		dialog.getWindow().getAttributes().width = LayoutParams.FILL_PARENT;
    		
    		//set up buttons
	        Button cancel = (Button) dialog.findViewById(R.id.buttonAddCancel);
	        cancel.setOnClickListener(new OnClickListener() {
	        	/*
	        	 * (non-Javadoc)
	        	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	        	 */
	            public void onClick(View v) {
	                dialog.cancel();
	            }
	        });
	        
	        Button accept = (Button) dialog.findViewById(R.id.buttonAddOk);
	        accept.setOnClickListener(new OnClickListener() {
	        	/*
	        	 * (non-Javadoc)
	        	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	        	 */
	            public void onClick(View v) {
	            	
	            	//input text
	        		EditText txtName = (EditText) dialog.findViewById(R.id.txtDialogAddName);
	            	EditText txtValue = (EditText) dialog.findViewById(R.id.txtDialogAddValue);
	            	String name =  txtName.getText().toString();
	            	String value =  txtValue.getText().toString();
	        	
	            	System.err.println(" name: " + name);
	            	System.err.println(" value: " + value);
	            }
	        });
    	}
    	
        //now that the dialog is set up, it's time to show it    
        dialog.show();
    }
}
