package com.bedates.guncelhaberler.adaptor;

import com.bedates.guncelhaberler.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import android.widget.TextView;

public class KulturSanatArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;

	public KulturSanatArrayAdapter(Context ates, String[] values) {
		super(ates, R.layout.kultursanat, values);
		this.context = ates;
		this.values = values;
	
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.kultursanat, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		textView.setText(values[position]);

		// Change icon based on name
		String s = values[position];

		System.out.println(s);

		if (s.equals("CNN TÜRK Kültür-Sanat")) {
			imageView.setImageResource(R.drawable.cnnturk);
		} else if (s.equals("Habertürk")) {
			imageView.setImageResource(R.drawable.haberturk);
		} else if (s.equals("Hürriyet")) {
			imageView.setImageResource(R.drawable.hurriyet);
		} else if (s.equals("Milliyet")) {
			imageView.setImageResource(R.drawable.milliyet);
		} else if (s.equals("NTV Kültür-Sanat")) {
			imageView.setImageResource(R.drawable.ntv);
		}else if (s.equals("Radikal")) {
			imageView.setImageResource(R.drawable.radikal);
		} else if (s.equals("Sabah")) {
			imageView.setImageResource(R.drawable.sabah);
		} 
		return rowView;
	}
}
