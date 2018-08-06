package com.bedates.guncelhaberler;


import com.bedates.guncelhaberler.adaptor.YasamArrayAdapter;


import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;


@SuppressLint({ "ParserError", "ParserError", "ParserError", "ParserError" })
public class yasam extends ListActivity {
	static final String[] gazeteler = new String[] {
		"Habertürk", "Milliyet", "CNN Türk Yaþam", "NTV Yaþam", "Radikal", "Vatan", "Yeni Þafak"};
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.haber);
        setListAdapter(new YasamArrayAdapter(this, gazeteler));
        
        
    }
    

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		//get selected items
//		String selectedValue = (String) getListAdapter().getItem(position);
/*		Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();*/
		
		
		if (position == 0) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://m.haberturk.com/icerikler.aspx?CID=200";
			startActivity(i);

		} else if (position == 1) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  ="http://m2.milliyet.com.tr/News/?CID=226";
			startActivity(i);
		} else if (position == 2) {			

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://m.cnnturk.com/7";
			startActivity(i);
		} else if (position == 3) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://www.ntvmsnbc.com/id/24928017/";
			startActivity(i);
			
		} else if (position == 4) {
		

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://m.radikal.com.tr/ListNews.aspx?CategoryIDs=3";
			startActivity(i);
			
		}
		else if (position == 5) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://m.gazetevatan.com/ListNews.aspx?CategoryIDs=9";
			startActivity(i);
			
		}
		else if (position == 6) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://yenisafak.mobi/kategori.aspx?i=5";
			startActivity(i);
			
		}

		
	}
    
}


