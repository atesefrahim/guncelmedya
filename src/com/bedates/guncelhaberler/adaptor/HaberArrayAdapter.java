package com.bedates.guncelhaberler.adaptor;

import com.bedates.guncelhaberler.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HaberArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;

	public HaberArrayAdapter(Context ates, String[] values) {
		super(ates, R.layout.haber, values);
		this.context = ates;
		this.values = values;
	
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.haber, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		textView.setText(values[position]);

		// Change icon based on name
		String s = values[position];

		if (s.equals("En Son Haber")) {
			imageView.setImageResource(R.drawable.ensonhaber);
		} else if (s.equals("Ýnternet Haber")) {
			imageView.setImageResource(R.drawable.internethaber);
		} else if (s.equals("T24")) {
			imageView.setImageResource(R.drawable.t24);
		} else if (s.equals("Habertürk")) {
			imageView.setImageResource(R.drawable.haberturk);
		} else if (s.equals("Hürriyet")) {
			imageView.setImageResource(R.drawable.hurriyet);
		} else if (s.equals("Milliyet")) {
			imageView.setImageResource(R.drawable.milliyet);
		} else if (s.equals("Radikal")) {
			imageView.setImageResource(R.drawable.radikal);
		} else if (s.equals("Sabah")) {
			imageView.setImageResource(R.drawable.sabah);
		} else if (s.equals("Taraf")) {
			imageView.setImageResource(R.drawable.taraf);
		}else if (s.equals("Vatan")) {
			imageView.setImageResource(R.drawable.vatan);
		}else if (s.equals("Yeni Þafak")) {
			imageView.setImageResource(R.drawable.yenisafak);
		}else if (s.equals("Zaman")) {
			imageView.setImageResource(R.drawable.zaman);
		}else if (s.equals("Akþam")) {
			imageView.setImageResource(R.drawable.aksam);
		}else if (s.equals("Aktif Haber")) {
			imageView.setImageResource(R.drawable.aktifhaber);
		}else if (s.equals("Birgün")) {
			imageView.setImageResource(R.drawable.birgun);
		}else if (s.equals("Bugün")) {
			imageView.setImageResource(R.drawable.bugun);
		}else if (s.equals("Cumhuriyet")) {
			imageView.setImageResource(R.drawable.cumhuriyet);
		}else if (s.equals("Evrensel")) {
			imageView.setImageResource(R.drawable.evrensel);
		}else if (s.equals("Haber7")) {
			imageView.setImageResource(R.drawable.haber7);
		}else if (s.equals("Posta")) {
			imageView.setImageResource(R.drawable.posta);
		}else if (s.equals("Sözcü")) {
			imageView.setImageResource(R.drawable.sozcu);
		}else if (s.equals("Star")) {
			imageView.setImageResource(R.drawable.star);
		}else if (s.equals("Takvim")) {
			imageView.setImageResource(R.drawable.takvim);
		}

		return rowView;
	}
}
