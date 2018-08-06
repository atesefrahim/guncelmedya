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
                        		rssreaderkytakvim.this, null, "Yükleniyor...");
                       
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
//Hürriyet Yazarlar Baþlangýç
                                if (s.contains("Hami Mandýralý"))
                                {
                                    imv.setImageResource(R.drawable.hmandirali);
                                	isim = "Hami Mandýralý";
                                }
                                else if (s.contains("BÜLENT ERANDAÇ"))
                                {
                                	imv.setImageResource(R.drawable.berandac);
                                	isim = "BÜLENT ERANDAÇ";
                                }
                                else if (s.contains("EMÝN PAZARCI"))
                                {
                                	imv.setImageResource(R.drawable.epazarci);
                                	isim = "EMÝN PAZARCI";
                                }
                                else if (s.contains("SAVAÞ AY"))
                                {
                                	imv.setImageResource(R.drawable.say);
                                	isim = "SAVAÞ AY";
                                }
                                else if (s.contains("Ergün Diler"))
                                {
                                	imv.setImageResource(R.drawable.ediler);
                                	isim = "ERGÜN DÝLER";
                                }
                                else if (s.contains("MEHMET ÇETÝNGÜLEÇ"))
                                {
                                	imv.setImageResource(R.drawable.mcetingulec);
                                	isim = "MEHMET ÇETÝNGÜLEÇ";
                                }
                                else if (s.contains("ARDA USKAN"))
                                {
                                	imv.setImageResource(R.drawable.auskan);
                                	isim = "ARDA USKAN";
                                }
                                else if (s.contains("ALÝ ÞERBETÇÝ"))
                                {
                                	imv.setImageResource(R.drawable.aserbetci);
                                	isim = "ALÝ ÞERBETÇÝ";
                                }
                                else if (s.contains("HAKKI YALÇIN"))
                                {
                                	imv.setImageResource(R.drawable.hyalcin);
                                	isim = "HAKKI YALÇIN";
                                }
                                else if (s.contains("SELÇUK YULA"))
                                {
                                	imv.setImageResource(R.drawable.syula);
                                	isim = "SELÇUK YULA";
                                }
                                else if (s.contains("BEKÝR HAZAR"))
                                {
                                	imv.setImageResource(R.drawable.bhazar);
                                	isim = "BEKÝR HAZAR";
                                }
                                else if (s.contains("ÝLKER YAÐCIOÐLU"))
                                {
                                	imv.setImageResource(R.drawable.iyagcioglu);
                                	isim = "ÝLKER YAÐCIOÐLU";
                                }
                                else if (s.contains("MEHMET AKARCA"))
                                {
                                	imv.setImageResource(R.drawable.makarca);
                                	isim = "MEHMET AKARCA";
                                }
                                else if (s.contains("KADÝR TUNA"))
                                {
                                	imv.setImageResource(R.drawable.ktuna);
                                	isim = "KADÝR TUNA";
                                }
                                else if (s.contains("LEVENT TÜZEMEN"))
                                {
                                	imv.setImageResource(R.drawable.ltuzemen);
                                	isim = "LEVENT TÜZEMEN";
                                }
                                else if (s.contains("Vedat Ýnceefe"))
                                {
                                	imv.setImageResource(R.drawable.vinceefe);
                                	isim = "VEDAT ÝNCEEFE";
                                }
                                else if (s.contains("ERKÝN ÞAHÝNÖZ"))
                                {
                                	imv.setImageResource(R.drawable.esahinoz);
                                	isim = "ERKÝN ÞAHÝNÖZ";
                                }
                                else if (s.contains("Rasim Ozan"))
                                {
                                	imv.setImageResource(R.drawable.rkutahyali);
                                	isim = "Rasim Ozan";
                                }
                                else if (s.contains("Hayri Ülgen"))
                                {
                                	imv.setImageResource(R.drawable.hulgen);
                                	isim = "HAYRÝ ÜLGEN";
                                }
                                else if (s.contains("Oktay DERELÝOÐLU"))
                                {
                                	imv.setImageResource(R.drawable.oderelioglu);
                                	isim = "OKTAY DERELÝOÐLU";
                                }
                                else if (s.contains("Nihat Hatipoðlu"))
                                {
                                	imv.setImageResource(R.drawable.nhatipoglu);
                                	isim = "NÝHAT HATÝPOÐLU";
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
