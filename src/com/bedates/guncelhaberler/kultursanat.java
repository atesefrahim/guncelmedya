package com.bedates.guncelhaberler;


import com.bedates.guncelhaberler.adaptor.KulturSanatArrayAdapter;


import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;


@SuppressLint({ "ParserError", "ParserError", "ParserError", "ParserError" })
public class kultursanat extends ListActivity {
	static final String[] gazeteler = new String[] {
		"CNN TÜRK Kültür-Sanat", "Habertürk", "Hürriyet", "Milliyet", "NTV Kültür-Sanat", "Radikal", "Sabah"};
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.haber);
        setListAdapter(new KulturSanatArrayAdapter(this, gazeteler));
        
        
    }
    

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		//get selected items
//		String selectedValue = (String) getListAdapter().getItem(position);
/*		Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();*/
		
		
		if (position == 0) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://m.cnnturk.com/550";
			startActivity(i);

		} else if (position == 1) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://m.haberturk.com/icerikler.aspx?CID=190";
			startActivity(i);
		} else if (position == 2) {
			

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://cep.hurriyet.com.tr/Cinema.aspx";
			startActivity(i);
		} else if (position == 3) {			

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://m2.milliyet.com.tr/News/?CID=30";
			startActivity(i);
		} else if (position == 4) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://wap.ntvmsnbc.com/Kategori/Goster/24927483";
			startActivity(i);
			
		} else if (position == 5) {
		

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://m.radikal.com.tr/ListNews.aspx?CategoryIDs=7";
			startActivity(i);
			
		}
		else if (position == 6) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://cep.sabah.com.tr/Movie/Sinema";
			startActivity(i);
			
		}

		
	}
    
}


