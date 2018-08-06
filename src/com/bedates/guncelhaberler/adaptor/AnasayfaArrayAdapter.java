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

@SuppressLint("ParserError")
public class AnasayfaArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;

	public AnasayfaArrayAdapter(Context ates, String[] values) {
		super(ates, R.layout.anasayfa, values);
		this.context = ates;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.anasayfa, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		textView.setText(values[position]);

		// Change icon based on name
		String s = values[position];

		System.out.println(s);

		if (s.equals("Politika")) {
			imageView.setImageResource(R.drawable.haber);
		//	rowView.setBackgroundResource(R.drawable.layoutrengi);
		} else if (s.equals("Köþe Yazarlarý")) {
			imageView.setImageResource(R.drawable.koseyazarlari);
		} else if (s.equals("Ekonomi")) {
			imageView.setImageResource(R.drawable.ekonomi);
		} else if (s.equals("Spor")) {
			imageView.setImageResource(R.drawable.spor);
		} else if (s.equals("Magazin")) {
			imageView.setImageResource(R.drawable.magazin);
		} else if (s.equals("Kültür-Sanat")) {
			imageView.setImageResource(R.drawable.kultur_sanat);
		} else if (s.equals("Saðlýk")) {
			imageView.setImageResource(R.drawable.saglik);
		} else if (s.equals("Teknoloji")) {
			imageView.setImageResource(R.drawable.teknoloji);
		}else if (s.equals("Yaþam")) {
			imageView.setImageResource(R.drawable.yasam);
		}else {
			imageView.setImageResource(R.drawable.logo);
		}

		return rowView;
	}
}
