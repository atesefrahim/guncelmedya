package com.bedates.guncelhaberler.adaptor;

import com.bedates.guncelhaberler.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import android.widget.TextView;

public class KoseYazarlariArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;

	public KoseYazarlariArrayAdapter(Context ates, String[] values) {
		super(ates, R.layout.koseyazarlari, values);
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

		if (s.equals("Habertürk")) {
			imageView.setImageResource(R.drawable.haberturk);
			rowView.setBackgroundResource(R.drawable.layoutrengi);
		} else if (s.equals("Hürriyet")) {
			imageView.setImageResource(R.drawable.hurriyet);
			rowView.setBackgroundResource(R.drawable.layoutrengi);
		} else if (s.equals("Milliyet")) {
			imageView.setImageResource(R.drawable.milliyet);
			rowView.setBackgroundResource(R.drawable.layoutrengi);
		} else if (s.equals("Radikal")) {
			imageView.setImageResource(R.drawable.radikal);
			rowView.setBackgroundResource(R.drawable.layoutrengi);
		} else if (s.equals("Sabah")) {
			imageView.setImageResource(R.drawable.sabah);
			rowView.setBackgroundResource(R.drawable.layoutrengi);
		} else if (s.equals("Taraf")) {
			imageView.setImageResource(R.drawable.taraf);
			rowView.setBackgroundResource(R.drawable.layoutrengi);
		}else if (s.equals("Vatan")) {
			imageView.setImageResource(R.drawable.vatan);
			rowView.setBackgroundResource(R.drawable.layoutrengi);
		}else if (s.equals("Yeni Þafak")) {
			imageView.setImageResource(R.drawable.yenisafak);
			rowView.setBackgroundResource(R.drawable.layoutrengi);
		}else if (s.equals("Zaman")) {
			imageView.setImageResource(R.drawable.zaman);
			rowView.setBackgroundResource(R.drawable.layoutrengi);
		}else if (s.equals("Akþam")) {
			imageView.setImageResource(R.drawable.aksam);
			rowView.setBackgroundResource(R.drawable.layoutrengi);
		}else if (s.equals("Birgün")) {
			imageView.setImageResource(R.drawable.birgun);
			rowView.setBackgroundResource(R.drawable.layoutrengi);
		}else if (s.equals("Bugün")) {
			imageView.setImageResource(R.drawable.bugun);
			rowView.setBackgroundResource(R.drawable.layoutrengi);
		}else if (s.equals("Cumhuriyet")) {
			imageView.setImageResource(R.drawable.cumhuriyet);
			rowView.setBackgroundResource(R.drawable.layoutrengi);
		}else if (s.equals("Evrensel")) {
			imageView.setImageResource(R.drawable.evrensel);
			rowView.setBackgroundResource(R.drawable.layoutrengi);
		}else if (s.equals("Haber7")) {
			imageView.setImageResource(R.drawable.haber7);
			rowView.setBackgroundResource(R.drawable.layoutrengi);
		}else if (s.equals("Posta")) {
			imageView.setImageResource(R.drawable.posta);
			rowView.setBackgroundResource(R.drawable.layoutrengi);
		}else if (s.equals("Sözcü")) {
			imageView.setImageResource(R.drawable.sozcu);
			rowView.setBackgroundResource(R.drawable.layoutrengi);
		}else if (s.equals("Star")) {
			imageView.setImageResource(R.drawable.star);
			rowView.setBackgroundResource(R.drawable.layoutrengi);
		}else if (s.equals("Takvim")) {
			imageView.setImageResource(R.drawable.takvim);
			rowView.setBackgroundResource(R.drawable.layoutrengi);
		}else if (s.equals("T24")) {
			imageView.setImageResource(R.drawable.t24);
			rowView.setBackgroundResource(R.drawable.layoutrengi);
		}else if (s.equals("NTV Spor")) {
			imageView.setImageResource(R.drawable.ntvspor);
			rowView.setBackgroundResource(R.drawable.layoutrengi);
		}

		return rowView;
	}
}
