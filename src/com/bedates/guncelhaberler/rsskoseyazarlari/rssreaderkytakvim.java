package com.bedates.guncelhaberler.rsskoseyazarlari;


import java.net.URL;

import java.util.ArrayList;
import java.util.List;


import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bedates.guncelhaberler.R;
import com.bedates.guncelhaberler.UsingMyWebview;

import com.bedates.guncelhaberler.adaptor.RSSItem;
import com.bedates.guncelhaberler.adaptor.RSSParser;


public class rssreaderkytakvim extends ListActivity {
        private ArrayList<RSSItem> itemlist = null;
        private RSSListAdaptor rssadaptor = null;
       
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.koseyazarlarilistesikopya);
       
        itemlist = new ArrayList<RSSItem>();
       
        new RetrieveRSSFeeds().execute();
    }
   
    @Override
        protected void onListItemClick(ListView l, View v, int position, long id) {
                super.onListItemClick(l, v, position, id);
               
                RSSItem data = itemlist.get(position);
               
        		Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
    			UsingMyWebview.adres  = Uri.parse(data.link).toString();
    			startActivity(i);
        }

        private void retrieveRSSFeed(String urlToRssFeed,ArrayList<RSSItem> list)
    {
        try
        {
           URL url = new URL(urlToRssFeed);
           SAXParserFactory factory = SAXParserFactory.newInstance();
           SAXParser parser = factory.newSAXParser();
           XMLReader xmlreader = parser.getXMLReader();
           RSSParser theRssHandler = new RSSParser(list);

           xmlreader.setContentHandler(theRssHandler);

           InputSource is = new InputSource(url.openStream());

           xmlreader.parse(is);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
   
    private class RetrieveRSSFeeds extends AsyncTask<Void, Void, Void>
    {
        private ProgressDialog progress = null;
       
                @Override
                protected Void doInBackground(Void... params) {
                        retrieveRSSFeed("http://www.takvim.com.tr/rss/Yazarlar.xml",itemlist);
                       
                        rssadaptor = new RSSListAdaptor(rssreaderkytakvim.this, R.layout.rssitemviewkopya,itemlist);
                       
                        return null;
                }
       
                @Override
                protected void onCancelled() {
                        super.onCancelled();
                }
               
                @Override
                protected void onPreExecute() {
                        progress = ProgressDialog.show(
                        		rssreaderkytakvim.this, null, "Y�kleniyor...");
                       
                        super.onPreExecute();
                }
               
                @Override
                protected void onPostExecute(Void result) {
                        setListAdapter(rssadaptor);
                       
                        progress.dismiss();
                       
                        super.onPostExecute(result);
                }
               
                @Override
                protected void onProgressUpdate(Void... values) {
                        super.onProgressUpdate(values);
                }
    }
   
    private class RSSListAdaptor extends ArrayAdapter<RSSItem>{
        private List<RSSItem> objects = null;
       
                public RSSListAdaptor(Context context, int textviewid, List<RSSItem> objects) {
                        super(context, textviewid, objects);
                       
                        this.objects = objects;
                }
               
                @Override
                public int getCount() {
                        return ((null != objects) ? objects.size() : 0);
                }
               
                @Override
                public long getItemId(int position) {
                        return position;
                }
               
                @Override
                public RSSItem getItem(int position) {
                        return ((null != objects) ? objects.get(position) : null);
                }


                 public View getView(int position, View convertView, ViewGroup parent) {
                        View view = convertView;
                       
                        if(null == view)
                        {
                                LayoutInflater vi = (LayoutInflater)rssreaderkytakvim.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                view = vi.inflate(R.layout.rssitemviewkopya, null);
                        }
                       
                        RSSItem data = objects.get(position);

                        if(null != data)
                        {
                                TextView title = (TextView)view.findViewById(R.id.txtTitle);
                                TextView date = (TextView)view.findViewById(R.id.txtDate);
                                TextView description = (TextView)view.findViewById(R.id.txtDescription);
                               
                               

                                ImageView imv = (ImageView)view.findViewById(R.id.yazarlogo);
                                
                                String isim = data.title;
                                String baslik = data.title;
                                
                                String search = "/"; 
                                String result = ""; 
                                String resultisim = ""; 
                                
                                int i; 
                                do { 
                                i = isim.indexOf(search); 
                                int a = i;
                                if(i != -1) { 
                                	
                                result = isim.substring(0, a);
                                 
                                isim = result; 
                                int y = baslik.length();
                                resultisim = baslik.substring(a+2 ,y);
                                baslik = resultisim;
                                } 
                                } while(i != -1); 
                                
        
                              String s = isim;
//H�rriyet Yazarlar Ba�lang��
                                if (s.contains("Hami Mand�ral�"))
                                {
                                    imv.setImageResource(R.drawable.hmandirali);
                                	isim = "Hami Mand�ral�";
                                }
                                else if (s.contains("B�LENT ERANDA�"))
                                {
                                	imv.setImageResource(R.drawable.berandac);
                                	isim = "B�LENT ERANDA�";
                                }
                                else if (s.contains("EM�N PAZARCI"))
                                {
                                	imv.setImageResource(R.drawable.epazarci);
                                	isim = "EM�N PAZARCI";
                                }
                                else if (s.contains("SAVA� AY"))
                                {
                                	imv.setImageResource(R.drawable.say);
                                	isim = "SAVA� AY";
                                }
                                else if (s.contains("Erg�n Diler"))
                                {
                                	imv.setImageResource(R.drawable.ediler);
                                	isim = "ERG�N D�LER";
                                }
                                else if (s.contains("MEHMET �ET�NG�LE�"))
                                {
                                	imv.setImageResource(R.drawable.mcetingulec);
                                	isim = "MEHMET �ET�NG�LE�";
                                }
                                else if (s.contains("ARDA USKAN"))
                                {
                                	imv.setImageResource(R.drawable.auskan);
                                	isim = "ARDA USKAN";
                                }
                                else if (s.contains("AL� �ERBET��"))
                                {
                                	imv.setImageResource(R.drawable.aserbetci);
                                	isim = "AL� �ERBET��";
                                }
                                else if (s.contains("HAKKI YAL�IN"))
                                {
                                	imv.setImageResource(R.drawable.hyalcin);
                                	isim = "HAKKI YAL�IN";
                                }
                                else if (s.contains("SEL�UK YULA"))
                                {
                                	imv.setImageResource(R.drawable.syula);
                                	isim = "SEL�UK YULA";
                                }
                                else if (s.contains("BEK�R HAZAR"))
                                {
                                	imv.setImageResource(R.drawable.bhazar);
                                	isim = "BEK�R HAZAR";
                                }
                                else if (s.contains("�LKER YA�CIO�LU"))
                                {
                                	imv.setImageResource(R.drawable.iyagcioglu);
                                	isim = "�LKER YA�CIO�LU";
                                }
                                else if (s.contains("MEHMET AKARCA"))
                                {
                                	imv.setImageResource(R.drawable.makarca);
                                	isim = "MEHMET AKARCA";
                                }
                                else if (s.contains("KAD�R TUNA"))
                                {
                                	imv.setImageResource(R.drawable.ktuna);
                                	isim = "KAD�R TUNA";
                                }
                                else if (s.contains("LEVENT T�ZEMEN"))
                                {
                                	imv.setImageResource(R.drawable.ltuzemen);
                                	isim = "LEVENT T�ZEMEN";
                                }
                                else if (s.contains("Vedat �nceefe"))
                                {
                                	imv.setImageResource(R.drawable.vinceefe);
                                	isim = "VEDAT �NCEEFE";
                                }
                                else if (s.contains("ERK�N �AH�N�Z"))
                                {
                                	imv.setImageResource(R.drawable.esahinoz);
                                	isim = "ERK�N �AH�N�Z";
                                }
                                else if (s.contains("Rasim Ozan"))
                                {
                                	imv.setImageResource(R.drawable.rkutahyali);
                                	isim = "Rasim Ozan";
                                }
                                else if (s.contains("Hayri �lgen"))
                                {
                                	imv.setImageResource(R.drawable.hulgen);
                                	isim = "HAYR� �LGEN";
                                }
                                else if (s.contains("Oktay DEREL�O�LU"))
                                {
                                	imv.setImageResource(R.drawable.oderelioglu);
                                	isim = "OKTAY DEREL�O�LU";
                                }
                                else if (s.contains("Nihat Hatipo�lu"))
                                {
                                	imv.setImageResource(R.drawable.nhatipoglu);
                                	isim = "N�HAT HAT�PO�LU";
                                }

                                else
                                {
                                	imv.setImageResource(R.drawable.takvim);
                                }
                
                                //Takvim Yazarlar Son       
                                title.setText(isim);
                                date.setText("");
                                description.setText(baslik);
                                
                        }

                       
                        return view;
                }
    }
}
