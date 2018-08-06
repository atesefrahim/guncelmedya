package com.bedates.guncelhaberler;


import com.bedates.guncelhaberler.adaptor.KoseYazarlariArrayAdapter;
import com.bedates.guncelhaberler.rsskoseyazarlari.*;


import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;

import android.widget.ListView;


@SuppressLint({ "ParserError", "ParserError", "ParserError", "ParserError", "ParserError", "ParserError", "ParserError", "ParserError", "ParserError", "ParserError" })
public class koseyazarlari extends ListActivity {
	public static String adres;
	static final String[] gazeteler = new String[] {
		"Habertürk", "Hürriyet", "Milliyet", "Radikal", "Sabah", "Taraf", "Vatan", "Yeni Þafak", "Zaman", 
		"Akþam", "Birgün", "Bugün", "Cumhuriyet", "Evrensel", "Posta", "Sözcü", "Star", "Takvim"};
	
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.haber);
//        setListAdapter(new KoseYazarlariArrayAdapter(this, gazeteler));
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        setListAdapter(new KoseYazarlariArrayAdapter(this, gazeteler));

        registerForContextMenu(getListView());
    }
    

	@SuppressLint("ParserError")
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		//get selected items
//		String selectedValue = (String) getListAdapter().getItem(position);
/*		Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();*/
		
		
		if (position == 0) {
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://m.haberturk.com/yazarlar.aspx";
			startActivity(i);

		} else if (position == 1) {
			Intent i = new Intent(getApplicationContext(),rssreaderkoseyazarlarihurriyet.class);
			startActivity(i);
	
		} else if (position == 2) {
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://m2.milliyet.com.tr/Columnists/";
			startActivity(i);
			
		} else if (position == 3) {
			Intent i = new Intent(getApplicationContext(),rssreaderkyradikal.class);
			startActivity(i);
			
		} else if (position == 4) {
			
			Intent i = new Intent(getApplicationContext(),rssreaderkysabah.class);
			startActivity(i);
		} else if (position == 5) {
			Intent i = new Intent(getApplicationContext(),rssreaderkytaraf.class);
			startActivity(i);
		} else if (position == 6) {
			Intent i = new Intent(getApplicationContext(),rssreaderkyvatan.class);
			startActivity(i);
		} else if (position == 7) {
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://yenisafak.mobi/Yazarlar.aspx";
			startActivity(i);
		} else if (position == 8) {
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://www.zaman.com.tr/columnistMenuDetail.action?sectionId=191";
			startActivity(i);
		} else if (position == 9) {
			Intent i = new Intent(getApplicationContext(),rssreaderkyaksam.class);
			startActivity(i);
		} else if (position == 10) {
			
			Intent i = new Intent(getApplicationContext(),rssreaderkybirgun.class);
			startActivity(i);
		} else if (position == 11) {
			
			Intent i = new Intent(getApplicationContext(),rssreaderkybugun.class);
			startActivity(i);
		} else if (position == 12) {
			
			Intent i = new Intent(getApplicationContext(),rssreaderkycumhuriyet.class);
			startActivity(i);
		} else if (position == 13) {
			
			Intent i = new Intent(getApplicationContext(),rssreaderkyevrensel.class);
			startActivity(i);
		} /*else if (position == 14) {
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://m.haber7.com/kategoriDetay.php?id=256";
			startActivity(i);
		} */else if (position == 14) {
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://m.posta.com.tr/ListArticles.aspx";
			startActivity(i);
		} else if (position == 15) {
			
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://sozcu.com.tr/kategori/yazarlar#";
			startActivity(i);
			
		} else if (position == 16) {
			
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://m.stargazete.com.tr/yazarlar.asp";
			startActivity(i);
			
		} else if (position == 17) {
			Intent i = new Intent(getApplicationContext(),rssreaderkytakvim.class);
			startActivity(i);
		}

		
	}


	public static String getAdres() {
		return adres;
	}


	public static void setAdres(String adres) {
		koseyazarlari.adres = adres;
	}
    
}


