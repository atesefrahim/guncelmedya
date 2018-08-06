package com.bedates.guncelhaberler;


import com.bedates.guncelhaberler.adaptor.MagazinArrayAdapter;


import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;


@SuppressLint({ "ParserError", "ParserError", "ParserError", "ParserError" })
public class magazin extends ListActivity {
	static final String[] gazeteler = new String[] {
		"Habertürk", "Hürriyet", "Milliyet", "Radikal", "Vatan", "Akþam","Posta", "Star", "Takvim"};
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.haber);
        setListAdapter(new MagazinArrayAdapter(this, gazeteler));
        
        
    }
    

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		//get selected items
//		String selectedValue = (String) getListAdapter().getItem(position);
/*		Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();*/
		
		
		if (position == 0) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://m.haberturk.com/icerikler.aspx?CID=150";
			startActivity(i);

		} else if (position == 1) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://cep.hurriyet.com.tr/ListNews.aspx?CategoryID=49";
			startActivity(i);
		
		}else if (position == 2) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://wap.milliyet.com.tr/News/?CID=17";
			startActivity(i);
		
		} else if (position == 3) {			

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://m.radikal.com.tr/ListNews.aspx?CategoryIDs=35";
			startActivity(i);
		} 
		else if (position == 4) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://m.gazetevatan.com/ListNews.aspx?CategoryIDs=8";
			startActivity(i);
			
		}	else if (position == 5) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://www.aksam.com.tr/magazin-6hk.html";
			startActivity(i);
			
		}else if (position == 6) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  ="http://m.posta.com.tr/ListNews.aspx?CategoryID=34";
			startActivity(i);
			
		}else if (position == 7) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://m.stargazete.com.tr/kategori.asp?cid=20";
			startActivity(i);
			
		}else if (position == 8) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://cep.takvim.com.tr/Gallery/Magazin/";
			startActivity(i);
			
		}
		
	}
    
}


