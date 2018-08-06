package com.bedates.guncelhaberler;


import com.bedates.guncelhaberler.adaptor.EkonomiArrayAdapter;


import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;


@SuppressLint({ "ParserError", "ParserError", "ParserError", "ParserError" })
public class ekonomi extends ListActivity {
	static String adres;
	static final String[] gazeteler = new String[] {
		"Dünya","Bloomberg HT", "Bigpara", "CNBC-E Ekonomi", "CNN Türk Ekonomi"};
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.haber);
        setListAdapter(new EkonomiArrayAdapter(this, gazeteler));
        
        
    }
    

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		//get selected items
//		String selectedValue = (String) getListAdapter().getItem(position);
/*		Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();*/
		
		
		if (position == 0) {
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://www.dunya.com/mobi/news.php?cid=2";
			startActivity(i);

		} else if (position == 1) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://www.bloomberght.com/";
			startActivity(i);
		} else if (position == 2) {
			
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://m.bigpara.com/haber.asp";
			startActivity(i);
		} else if (position == 4) {			

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://mobile.cnnturk.com/4";
			startActivity(i);
		} else if (position == 3) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://wap.ntvmsnbc.com/Kategori/Goster/24927360";
			startActivity(i);
			
		} 
		
	}
    
}


