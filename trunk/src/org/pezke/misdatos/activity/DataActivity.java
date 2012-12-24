package org.pezke.misdatos.activity;

import java.util.ArrayList;
import java.util.List;

import org.pezke.misdatos.R;
import org.pezke.misdatos.dao.DataDao;
import org.pezke.misdatos.dao.DbManager;
import org.pezke.misdatos.dao.UserDao;
import org.pezke.misdatos.model.Data;
import org.pezke.misdatos.model.ListElement;
import org.pezke.misdatos.model.User;
import org.pezke.misdatos.util.CommonConstants;
import org.pezke.misdatos.util.DateUtils;
import org.pezke.misdatos.util.PasswordUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DataActivity extends Activity {

	//DataList
	private List<ListElement> datos = null;
	
	//Dialogs
	private Dialog addDataDialog = null;
	private Dialog viewDataDialog = null;
	private Dialog aboutDialog = null;
	
	//DAO
	private DbManager dbManager = null;
	
	//List adapter
	private MyDataAdapter adapter = null;
	
	
    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        
    	//Create the database
		this.dbManager = DbManager.getInstance(this);
		
		//Get the data and compose the list
		calculateList();
		 
        //Adapter
    	this.adapter = new MyDataAdapter(this);
    	ListView list = (ListView) findViewById(R.id.CtlList);
		list.setAdapter(adapter);
		registerForContextMenu(list);
		
		list.setClickable(true);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * android.widget.AdapterView.OnItemClickListener#onItemClick(android
			 * .widget.AdapterView, android.view.View, int, long)
			 */
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				viewListElement(position);
			}
		});
    }
  

    /*
	 * (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.menu_addElement:
			createAddDialog();
			return true;

		case R.id.menu_settings:
			Intent intent = new Intent(DataActivity.this, ConfigActivity.class);
			startActivity(intent);
			return true;
			
		case R.id.menu_about:
			createAboutDialog();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateContextMenu(android.view.ContextMenu,
	 * android.view.View, android.view.ContextMenu.ContextMenuInfo)
	 */
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle(R.string.menu_context);
		getMenuInflater().inflate(R.menu.list_data, menu);
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onContextItemSelected(android.view.MenuItem)
	 */
    @Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

		switch(item.getItemId()){
			case R.id.menu_context_view:
				viewListElement(info.position);
				return true;
		
			case R.id.menu_context_delete:
				deleteListElement(info.position);
				return true;
		
			default:
				return super.onContextItemSelected(item);
		}
	}
	
	
	 /**
     * Compose the list
     */
	private void calculateList() {
		
		//Dao
		DataDao dataDao = new DataDao(dbManager);
		
		//Labels
		String keyLastAccess = getString(R.string.label_lastAccess);
    	String keyNumAccess  = getString(R.string.label_numAccess);
    	
    	//Get the login
		String login = getLogin();
		List<Data> list = dataDao.getByLogin(login);
		if(list!=null){
			datos = new ArrayList<ListElement>();
			for (int i = 0; i < list.size(); i++){
				Data data = list.get(i);
				ListElement item = getListElement(keyLastAccess, keyNumAccess, data);
				datos.add(item);
	        }
		}else{
			datos = new ArrayList<ListElement>();
		}
		
		if(datos.size() != 0){
			deleteWarning();
		}
	}


	/**
	 * Delete the warning
	 */
	private void deleteWarning() {
		LinearLayout textNoData = (LinearLayout)findViewById(R.id.layout_nodata);
		if(textNoData!=null){
			textNoData.removeAllViews();
		}
	}
	
	
	/**
	 * Create a ListElement from a Data
	 */
	private ListElement getListElement(Data data) {
		String keyLastAccess = getString(R.string.label_lastAccess);
    	String keyNumAccess  = getString(R.string.label_numAccess);
    	return getListElement(keyLastAccess, keyNumAccess, data);
		
	}

	/**
	 * Create a ListElement from a Data
	 */
	private ListElement getListElement(String keyLastAccess, String keyNumAccess, Data data) {
	
		int count = data.getCount();
		String date = "---";
		if(data.getLastAccessDate()!=null && data.getLastAccessDate().getTime()!=0){
			date = DateUtils.formatDateTime(data.getLastAccessDate());
		}
		
		ListElement item = new ListElement(data.getKey());
		item.setLastAccess(keyLastAccess + " " + date);
		item.setNumAccess(keyNumAccess + " " + count);
		return item;
	}
	
	/**
	 * Update adapter
	 */
	private void updateAdapter(){
		adapter.notifyDataSetChanged();
		DataActivity.this.runOnUiThread(new Runnable() {
			public void run() {
		       	adapter.notifyDataSetChanged();
		        }
		});
	}
	

	/**
	 * Get the login
	 */
	private String getLogin() {
		SharedPreferences preferences = 
				getSharedPreferences(CommonConstants.DATA, Context.MODE_PRIVATE);
		String login = preferences.getString(CommonConstants.LOGIN, "");
		return login;
	}

	
    /**
     * Create the dialog to add a new element to the list
     */
    private void createAddDialog(){
    	
    	//Create the dialog if it does not exist
    	if(this.addDataDialog == null){
    		
    		addDataDialog = new Dialog(DataActivity.this);
    		addDataDialog.setContentView(R.layout.dialog_add);
    		addDataDialog.setTitle(R.string.label_addData);
    		addDataDialog.setCancelable(true);
    		addDataDialog.getWindow().getAttributes().width = LayoutParams.MATCH_PARENT;
    		
    		//set up buttons
	        Button cancel = (Button) addDataDialog.findViewById(R.id.buttonAddCancel);
	        cancel.setOnClickListener(new OnClickListener() {
	        	/*
	        	 * (non-Javadoc)
	        	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	        	 */
	            public void onClick(View v) {
	            	//Close the dialog
	                addDataDialog.cancel();
	            }
	        });
	        
	        Button accept = (Button) addDataDialog.findViewById(R.id.buttonAddOk);
	        accept.setOnClickListener(new OnClickListener() {
	        	/*
	        	 * (non-Javadoc)
	        	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	        	 */
	            public void onClick(View v) {
	            	
	            	//input text
	        		EditText txtName = (EditText) addDataDialog.findViewById(R.id.txtDialogAddName);
	            	EditText txtValue = (EditText) addDataDialog.findViewById(R.id.txtDialogAddValue);
	            	String key =  txtName.getText().toString();
	            	String value =  txtValue.getText().toString();
	        	
	            	//Save the data
	            	boolean success = addListElement(key, value);
	            	if(success){
                      	//Close the dialog
	            		addDataDialog.cancel();
	            	
	            	}else{
	            		txtName.requestFocus();
	            	}
	            }
	        });
    	
    	}else{
    		EditText txtName = (EditText) addDataDialog.findViewById(R.id.txtDialogAddName);
    		EditText txtValue = (EditText) addDataDialog.findViewById(R.id.txtDialogAddValue);
    		
    		txtName.setText("");
        	txtValue.setText("");
    	}
    	
        //now that the dialog is set up, it's time to show it    
        addDataDialog.show();
    }
    
    
    /**
     * Create the ABOUT dialog to show the app information
     */
    private void createAboutDialog(){
    	
    	//Create the dialog if it does not exist
    	if(this.aboutDialog == null){
	    	aboutDialog = new Dialog(DataActivity.this);
	    	aboutDialog.setContentView(R.layout.dialog_about);
	    	aboutDialog.setTitle(R.string.app_name);
	    	aboutDialog.setCancelable(true);
	    	aboutDialog.getWindow().getAttributes().width = LayoutParams.MATCH_PARENT;
	    		
	    	//set up buttons
		    Button cancel = (Button) aboutDialog.findViewById(R.id.buttonAboutCancel);
		    cancel.setOnClickListener(new OnClickListener() {
		        	/*
		        	 * (non-Javadoc)
		        	 * @see android.view.View.OnClickListener#onClick(android.view.View)
		        	 */
		            public void onClick(View v) {
		            	//Close the dialog
		            	aboutDialog.cancel();
		            }
		        });
      
    	} 
    	
        //now that the dialog is set up, it's time to show it    
	    aboutDialog.show();
    }
    
    /**
     * Create the dialog to view the private data
     */
    private void createViewDialog(String name, String text){
    	
    	//Create the dialog if it does not exist
    	if(this.viewDataDialog == null){
    		
    		viewDataDialog = new Dialog(DataActivity.this);
    		viewDataDialog.setContentView(R.layout.dialog_view);
    		viewDataDialog.setTitle(name);
    		viewDataDialog.setCancelable(true);
    		viewDataDialog.getWindow().getAttributes().width = LayoutParams.MATCH_PARENT;
    		
    		//set up buttons
	        Button cancel = (Button) viewDataDialog.findViewById(R.id.buttonViewCancel);
	        cancel.setOnClickListener(new OnClickListener() {
	        	/*
	        	 * (non-Javadoc)
	        	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	        	 */
	            public void onClick(View v) {
	            	//Close the dialog
	            	viewDataDialog.cancel();
	            }
	        });
	   	}
    	
    	viewDataDialog.setTitle(name);
    	TextView txtName = (TextView) viewDataDialog.findViewById(R.id.txtViewDataName);
		txtName.setText(text);
		
        //now that the dialog is set up, it's time to show it    
        viewDataDialog.show();
    }
    
  
    /**
	 * Show the message with Toast
	 */
	private void showMessage(int message){
		Toast.makeText(DataActivity.this, message, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * Manage the event of viewing the element
	 */
	private void viewListElement(int position){
		
		//Selected element 
		ListElement item = datos.get(position);
		
		//Get the user
    	String login = getLogin();
    	UserDao userDao = new UserDao(this.dbManager);
    	User user = userDao.getByLogin(login);
    	
		//Update the data
    	DataDao dataDao = new DataDao(this.dbManager);
    	Data data = dataDao.update(login, item.getName());
    	
    	//Show the data
    	String mydata = PasswordUtils.decrypt(user, data);
    	createViewDialog(item.getName(), mydata);
    	
    	//Update the list
    	datos.remove(position);
    	ListElement element = getListElement(data); 
    	datos.add(position, element);
    	updateAdapter();
    }
	
	
	/**
	 * Manage the event of adding the element
	 */
	private boolean addListElement(String key, String value){
		
		//Result
		boolean result = false;
		
		//No introduced data 
		if( key!=null && key.length()>0 && value!=null && value.length()>0){
			
	    	//Check previous data
			String login = getLogin();
	    	DataDao dataDao = new DataDao(this.dbManager);
	    	boolean exists = dataDao.checkByLoginAndKey(login, key);
	    	
	    	//If it exists, we show a error message
	    	if(exists){
	    		showMessage(R.string.error_data_existent);
			
	    	}else{
	    			
				//Get the user
		    	UserDao userDao = new UserDao(this.dbManager);
		    	User user = userDao.getByLogin(login);
		    			    	
		    	//Save the data
		    	Data data = dataDao.save(user, key, value);
		    	
		    	//Refresh the view
		     	ListElement element = getListElement(data); 
		    	datos.add(element);
		    	if(datos.size() == 1){
					deleteWarning();
				}
		    	
		    	updateAdapter();
		    	
		    	//Message
		    	showMessage(R.string.success_add_data);
		    	
		    	//Success
		    	result = true;
	    	}
		
		}else {
			//Error message
			showMessage(R.string.error_data_empty);
		}
		
		return result;
	}
	
	
	/**
	 * Manage the event of deleting the element
	 */
	private void deleteListElement(int position){
		
		//Selected element 
		ListElement item = datos.get(position);
		
		//Get the user
    	String login = getLogin();
    	
		//Delete the data
    	DataDao dataDao = new DataDao(this.dbManager);
    	dataDao.delete(login, item.getName());
    	
    	//Update the list
    	datos.remove(item);
		updateAdapter();   
		
		//Message
    	showMessage(R.string.success_delete_data);
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
			
			ListElement data = datos.get(position);
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
}
