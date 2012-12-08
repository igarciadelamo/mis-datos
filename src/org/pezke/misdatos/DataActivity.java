package org.pezke.misdatos;

import org.pezke.misdatos.model.ListElement;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DataActivity extends Activity {

	//DataList
	private ListElement[] datos = new ListElement[25];
	
    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        
        for (int i = 1; i <= 25; i++){
			datos[i - 1] = new ListElement("Título " + i, "Subtítulo largo " + i);
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
				holder.tvName = (TextView) item.findViewById(R.id.LblTitulo);
				holder.tvDescription = (TextView) item.findViewById(R.id.LblSubTitulo);

				item.setTag(holder);
				
			} else {
				holder = (ViewHolder) item.getTag();
			}

			holder.tvName.setText(datos[position].getName());
			holder.tvDescription.setText(datos[position].getDescription());

			return (item);
		}
	}

    /**
     * Inner class to model the view
     */
	private static class ViewHolder {
		protected TextView tvName;
		protected TextView tvDescription;
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
