package com.bedates.guncelhaberler;

import com.bedates.guncelhaberler.adaptor.HaberArrayAdapter;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

@SuppressLint({ "ParserError", "ParserError", "ParserError", "ParserError", "ParserError", "ParserError", "ParserError", "ParserError", "ParserError" })
public class haber extends ListActivity {
	static ImageView image;
	static String adres;
	static final String[] gazeteler = new String[] {"En Son Haber",
		"Ýnternet Haber", "T24", "Haber7", "Aktif Haber", "Habertürk", "Hürriyet", "Milliyet", "Radikal", "Sabah", "Taraf", "Vatan", "Yeni Þafak", "Zaman", 
		"Akþam", "Birgün", "Bugün", "Cumhuriyet", "Evrensel", "Posta", "Star", "Takvim"};
	
	


	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.haber);
        setListAdapter(new HaberArrayAdapter(this, gazeteler));
        
        image = (ImageView)findViewById(R.id.logo);
    }
    

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		//get selected items
/*		String selectedValue = (String) getListAdapter().getItem(position);
		Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();*/
		
		
		if (position == 0) {
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres = "http://m.ensonhaber.com/";

			startActivity(i);

		} else if (position == 1) {
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://m.internethaber.com/";
			startActivity(i);
	
		} else if (position == 2) {
			
			Intent i = new Intent(getApplicationContext(),rssreader.class);
			adres = "http://t24.com.tr/rss/haberler/gundem";
			startActivity(i);
		} else if (position == 5) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres = "http://m.haberturk.com/icerikler.aspx";
			startActivity(i);
		} else if (position == 6) {
			
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres = "http://cep.hurriyet.com.tr/Default.aspx";
			startActivity(i);
		} else if (position == 7) {
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres = "http://wap.milliyet.com.tr/HomePage.aspx";
			startActivity(i);
		} else if (position == 8) {
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres = "http://m.radikal.com.tr/Default.aspx";
			startActivity(i);
		} else if (position == 9) {
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres= "http://cep.sabah.com.tr/";

			startActivity(i);
		} else if (position == 10) {
			Intent i = new Intent(getApplicationContext(),rssreader.class);
			adres = "http://www.taraf.com.tr/rss/default.asp";
			startActivity(i);
		} else if (position == 11) {
			Intent i = new Intent(getApplicationContext(),rssreader.class);
			adres = "http://rss.gazetevatan.com/rss/gundem.xml";
			startActivity(i);
		} else if (position == 12) {
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres = "http://yenisafak.mobi/kategori.aspx?i=1";

			startActivity(i);
		} else if (position == 13) {
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres = "http://www.zaman.com.tr/wap.do?method=getSondakika&sdkType=all";

			startActivity(i);
		} else if (position == 14) {
			
			Intent i = new Intent(getApplicationContext(),rssreader.class);
			adres = "http://www.aksam.com.tr/cache/rss.xml";
			startActivity(i);
		} else if (position == 4) {
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres = "http://www.aktifhaber.com/service/mobi/index.php";

			startActivity(i);
		} else if (position == 15) {
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres = "http://www.birgun.net/mobile.php";

			startActivity(i);
		} else if (position == 16) {
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres = "http://mobil.bugun.com.tr/kat.aspx?cat=sonDak";

			startActivity(i);
		} else if (position == 17) {
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres = "http://m.cumhuriyet.com.tr/LastMinute.aspx";

			startActivity(i);
		} else if (position == 18) {
			
			Intent i = new Intent(getApplicationContext(),rssreader.class);
			adres = "http://evrensel.net/rss.php";
			startActivity(i);
		} else if (position == 3) {
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres = "http://m.haber7.com/";

			startActivity(i);
		} else if (position == 19) {
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres = "http://m.posta.com.tr";

			startActivity(i);
		} else if (position == 20) {
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres = "http://m.stargazete.com.tr/?";

			startActivity(i);
		} else if (position == 21) {
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres = "http://cep.takvim.com.tr/";

			startActivity(i);
		} 

		
	}
    public static String getAdres() {
		return adres;
	}


	public static void setAdres(String adres) {
		haber.adres = adres;
	}

}


