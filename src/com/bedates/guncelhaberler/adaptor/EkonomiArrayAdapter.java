package com.bedates.guncelhaberler.adaptor;

import com.bedates.guncelhaberler.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import android.widget.TextView;

public class EkonomiArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;

	public EkonomiArrayAdapter(Context ates, String[] values) {
		super(ates, R.layout.ekonomi, values);
		this.context = ates;
		this.values = values;
	
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.koseyazarlari, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		textView.setText(values[position]);

		// Change icon based on name
		String s = values[position];

		System.out.println(s);

		if (s.equals("Dünya")) {
			imageView.setImageResource(R.drawable.dunya);
		} else if (s.equals("Bloomberg HT")) {
			imageView.setImageResource(R.drawable.bloomberg);
		} else if (s.equals("Bigpara")) {
			imageView.setImageResource(R.drawable.bigpara);
		} else if (s.equals("CNN Türk Ekonomi")) {
			imageView.setImageResource(R.drawable.cnnturk);
		} else if (s.equals("CNBC-E Ekonomi")) {
			imageView.setImageResource(R.drawable.cnbce);
		} 

		return rowView;
	}
}
