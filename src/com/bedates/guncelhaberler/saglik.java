package com.bedates.guncelhaberler;


import com.bedates.guncelhaberler.adaptor.SaglikArrayAdapter;


import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;


@SuppressLint({ "ParserError", "ParserError", "ParserError", "ParserError" })
public class saglik extends ListActivity {
	static final String[] gazeteler = new String[] {
		"CNN TÜRK Saðlýk", "Habertürk", "Milliyet", "NTV Saðlýk", "Radikal", "Yeni Þafak"};
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.haber);
        setListAdapter(new SaglikArrayAdapter(this, gazeteler));
        
        
    }
    

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		//get selected items
//		String selectedValue = (String) getListAdapter().getItem(position);
/*		Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();*/
		
		
		if (position == 0) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://m.cnnturk.com/6";
			startActivity(i);

		} else if (position == 1) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://m.haberturk.com/icerikler.aspx?CID=220";
			startActivity(i);
		} else if (position == 2) {			

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://m2.milliyet.com.tr/News/?CID=31";
			startActivity(i);
		} else if (position == 3) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://www.ntvmsnbc.com/id/24928009/";
			startActivity(i);
			
		} else if (position == 4) {
		

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://m.radikal.com.tr/ListNews.aspx?CategoryIDs=28";
			startActivity(i);
			
					
		}else if (position == 5) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  ="http://yenisafak.mobi/kategori.aspx?i=9";
			startActivity(i);
			
		}


		
	}
    
}


