package com.bedates.guncelhaberler.adaptor;

import com.bedates.guncelhaberler.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import android.widget.TextView;

public class SporArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;

	public SporArrayAdapter(Context ates, String[] values) {
		super(ates, R.layout.spor, values);
		this.context = ates;
		this.values = values;
	
	}

	@SuppressLint("ParserError")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.spor, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		textView.setText(values[position]);

		// Change icon based on name
		String s = values[position];

		System.out.println(s);

		if (s.equals("Ajans Spor")) {
			imageView.setImageResource(R.drawable.ajansspor);
		} else if (s.equals("Fanatik")) {
			imageView.setImageResource(R.drawable.fanatik);
		} else if (s.equals("Fotomaç")) {
			imageView.setImageResource(R.drawable.fotomac);
		} else if (s.equals("Fotospor")) {
			imageView.setImageResource(R.drawable.fotospor);
		} else if (s.equals("HT Spor")) {
			imageView.setImageResource(R.drawable.htspor);
		}else if (s.equals("Ýnternet Spor")) {
			imageView.setImageResource(R.drawable.internetspor);
		}  
		else if (s.equals("NTV Spor")) {
			imageView.setImageResource(R.drawable.ntvspor);
		}  
		
		else if (s.equals("Mackolik.com")) {
			imageView.setImageResource(R.drawable.mackolik);
		}
		else if (s.equals("Sahadan.com")) {
			imageView.setImageResource(R.drawable.sahadan);
		}    else if (s.equals("Sporx")) {
			imageView.setImageResource(R.drawable.sporx);
		} 

		return rowView;
	}
}
