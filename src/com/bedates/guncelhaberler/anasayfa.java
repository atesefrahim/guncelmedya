package com.bedates.guncelhaberler;


import com.bedates.guncelhaberler.adaptor.AnasayfaArrayAdapter;

import android.os.Bundle;
import android.annotation.SuppressLint;

import android.app.ListActivity;
import android.content.Intent;

import android.view.View;

import android.widget.ListView;


@SuppressLint({ "ParserError", "ParserError", "ParserError", "ParserError" })
public class anasayfa extends ListActivity {

	static final String[] MOBILE_OS = new String[] {"Politika",
			"Köþe Yazarlarý", "Ekonomi", "Spor", "Teknoloji", "Kültür-Sanat", "Magazin", "Saðlýk", "Yaþam"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.anasayfa);
        setListAdapter(new AnasayfaArrayAdapter(this, MOBILE_OS));
        
/*        ListView listView = (ListView) findViewById(R.id.listview);
        String[] values = new String[] { "Son Dakika", "Haberler", "Köþe Yazarlarý", "Spor",
          "Ekonomi", "Magazin", "Kültür-Sanat", "Saðlýk", "Yaþam" };

        // First paramenter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
          android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter); */

    }
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

	
		if (position == 0) {
			Intent i = new Intent(getApplicationContext(), haber.class);
			startActivity(i);
		} else if (position == 1) {
			Intent i = new Intent(getApplicationContext(), koseyazarlari.class);
			startActivity(i);
		}  else if (position == 2) {
			Intent i = new Intent(getApplicationContext(), ekonomi.class);
			startActivity(i);
		} else if (position == 3) {
			Intent i = new Intent(getApplicationContext(), spor.class);
			startActivity(i);
		} else if (position == 4) {
			Intent i = new Intent(getApplicationContext(), teknoloji.class);
			startActivity(i);
		} else if (position == 5) {
			Intent i = new Intent(getApplicationContext(), kultursanat.class);
			startActivity(i);
		} else if (position == 6) {
			Intent i = new Intent(getApplicationContext(), magazin.class);
			startActivity(i);
		}else if (position == 7) {
			Intent i = new Intent(getApplicationContext(), saglik.class);
			startActivity(i);
		} else if (position == 8) {
			Intent i = new Intent(getApplicationContext(), yasam.class);
			startActivity(i);
		} 

		
	}/*
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }
	@Override
	 public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
	        case R.id.sil:
	        	startActivity(new Intent(this, haber.class));
	            return true;
	        case R.id.duzenle:
	        	startActivity(new Intent(this, saglik.class));
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}*/
	
	}
