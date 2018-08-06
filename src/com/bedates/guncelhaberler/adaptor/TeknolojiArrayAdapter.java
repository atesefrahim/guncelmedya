package com.bedates.guncelhaberler.adaptor;

import com.bedates.guncelhaberler.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import android.widget.TextView;

public class TeknolojiArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;

	public TeknolojiArrayAdapter(Context ates, String[] values) {
		super(ates, R.layout.teknoloji, values);
		this.context = ates;
		this.values = values;
	
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.teknoloji, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		textView.setText(values[position]);

		// Change icon based on name
		String s = values[position];

		if (s.equals("Chip")) {
			imageView.setImageResource(R.drawable.chip);
		}  else if (s.equals("Level")) {
			imageView.setImageResource(R.drawable.level);
		} else if (s.equals("PC World")) {
			imageView.setImageResource(R.drawable.pcworld);
		} else if (s.equals("Ntvmsnbc Teknoloji")) {
			imageView.setImageResource(R.drawable.ntv);
		}else if (s.equals("Hürriyet Teknoloji")) {
			imageView.setImageResource(R.drawable.hurriyet);
		} 

		return rowView;
	}
}
