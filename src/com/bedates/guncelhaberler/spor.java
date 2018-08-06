package com.bedates.guncelhaberler;


import com.bedates.guncelhaberler.adaptor.SporArrayAdapter;


import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;


@SuppressLint({ "ParserError", "ParserError", "ParserError", "ParserError" })
public class spor extends ListActivity {
	static final String[] gazeteler = new String[] {
		"NTV Spor","Sporx", "Mackolik.com", "Sahadan.com", "HT Spor","Ýnternet Spor", "Ajans Spor", "Fotomaç","Fotospor","Fanatik"};
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.haber);
        setListAdapter(new SporArrayAdapter(this, gazeteler));
        
        
    }
    

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		//get selected items
//		String selectedValue = (String) getListAdapter().getItem(position);
/*		Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();*/
		
		
		if (position == 6) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  ="http://m.ajansspor.com/";
			startActivity(i);

		} else if (position == 9) {
	
			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://m.fanatik.com.tr/";
			startActivity(i);
		} else if (position == 7) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://cep.fotomac.com.tr/";
			startActivity(i);
		} else if (position == 8) {			

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://wap.fotospor.com/";
			startActivity(i);
		} else if (position == 4) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  ="http://m.haberturk.com/icerikler.aspx?CID=170";
			startActivity(i);
			
		} else if (position == 5) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://spor.internethaber.com/";
			startActivity(i);
			
		}
		else if (position == 0) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://wap.ntvmsnbc.com/Spor/Haberler";
			startActivity(i);
			
		}
		else if (position == 1) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://m.sporx.com/";
			startActivity(i);
			
		}
		else if (position == 2) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://wap.mackolik.com/(S(0dxgoffxipijyqa2jgqxwt33))/Default.aspx?r=92839";
			startActivity(i);
			
		}
		else if (position == 3) {

			Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
			UsingMyWebview.adres  = "http://wap.sahadan.com/(S(1crtis4tqatc5w3joxmfv3ax))/Default.aspx?r=00669";
			startActivity(i);
			
		}

		
	}
    
}


