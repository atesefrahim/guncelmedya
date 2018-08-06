package com.bedates.guncelhaberler;


import com.bedates.guncelhaberler.adaptor.TeknolojiArrayAdapter;


import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;


@SuppressLint({ "ParserError", "ParserError", "ParserError", "ParserError" })
public class teknoloji extends ListActivity {
	static final String[] gazeteler = new String[] {
		"Chip", "Level", "PC World", "Ntvmsnbc Teknoloji", "Hürriyet Teknoloji"};
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.haber);
        setListAdapter(new TeknolojiArrayAdapter(this, gazeteler));
        
        
    }
    

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		//get selected items
//		String selectedValue = (String) getListAdapter().getItem(position);
/*		Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();*/
		
		
		if (position == 0) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://www.chip.com.tr/";
			startActivity(i);

		} else if (position == 1) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  ="http://www.level.com.tr/";
			startActivity(i);
		} else if (position == 2) {			

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://www.pcworld.com.tr/pcworld.html";
			startActivity(i);
		} else if (position == 3) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://wap.ntvmsnbc.com/Kategori/Goster/24927533";
			startActivity(i);
			
		} else if (position == 4) {
		

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://cep.hurriyet.com.tr/ListNews.aspx?CategoryID=48";
			startActivity(i);
			
		}

		
	}
    
}


