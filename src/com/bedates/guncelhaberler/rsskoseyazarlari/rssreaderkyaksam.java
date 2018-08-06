package com.bedates.guncelhaberler.rsskoseyazarlari;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

public class rssreaderkyaksam extends ListActivity {
        private ArrayList<RSSItem> itemlist = null;
        private RSSListAdaptor rssadaptor = null;
        static String myDate; 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.koseyazarlarilistesikopya);
       
        itemlist = new ArrayList<RSSItem>();
       
        new RetrieveRSSFeeds().execute();
        
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter= 
        new SimpleDateFormat(" dd MMM yy ");

        myDate = formatter.format(currentDate.getTime());
        
        
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
                        retrieveRSSFeed("http://www.aksam.com.tr/rss_yazarlar.xml",itemlist);
                       
                        rssadaptor = new RSSListAdaptor(rssreaderkyaksam.this, R.layout.rssitemviewkopya,itemlist);
                       
                        return null;
                }
       
                @Override
                protected void onCancelled() {
                        super.onCancelled();
                }
               
                @Override
                protected void onPreExecute() {
                        progress = ProgressDialog.show(
                                        rssreaderkyaksam.this, null, "Yükleniyor...");
                       
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
                                LayoutInflater vi = (LayoutInflater)rssreaderkyaksam.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                view = vi.inflate(R.layout.rssitemviewkopya, null);
                        }
                       
                        RSSItem data = objects.get(position);

                        if(null != data && data.date.contains(myDate))
                        {
                                TextView title = (TextView)view.findViewById(R.id.txtTitle);
                                TextView date = (TextView)view.findViewById(R.id.txtDate);
                                TextView description = (TextView)view.findViewById(R.id.txtDescription);
                               
                               

                                ImageView imv = (ImageView)view.findViewById(R.id.yazarlogo);
                                
                                String isim = data.title;
                                String baslik = data.title;
                                
                                String search = "-"; 
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
                                if (s.contains("Ali Ece"))
                                {
                                    imv.setImageResource(R.drawable.aece);
                                	isim = "Ali Ece";
                                }
                                else if (s.contains("Avni Baþoðlu"))
                                {
                                	imv.setImageResource(R.drawable.abasoglu);
                                	isim = "Avni Baþoðlu";
                                }
                                else if (s.contains("Serkan Yetkin"))
                                {
                                	imv.setImageResource(R.drawable.syetkin);
                                	isim = "Serkan Yetkin";
                                }
                                else if (s.contains("Çiðdem Toker"))
                                {
                                	imv.setImageResource(R.drawable.ctoker);
                                	isim = "Çiðdem Toker";
                                }
                                else if (s.contains("Funda Özkan"))
                                {
                                	imv.setImageResource(R.drawable.fozkan);
                                	isim = "Funda Özkan";
                                }
                                else if (s.contains("Ýsmail Küçükkaya"))
                                {
                                	imv.setImageResource(R.drawable.ikucukkaya);
                                	isim = "Ýsmail Küçükkaya";
                                }
                                else if (s.contains("Deniz Gökçe"))
                                {
                                	imv.setImageResource(R.drawable.dgokce);
                                	isim = "Deniz Gökçe";
                                }
                                else if (s.contains("Alaattin Metin"))
                                {
                                	imv.setImageResource(R.drawable.ametin);
                                	isim = "Alaattin Metin";
                                }
                                else if (s.contains("Tuðçe Tatari"))
                                {
                                	imv.setImageResource(R.drawable.ttatari);
                                	isim = "Tuðçe Tatari";
                                }
                                else if (s.contains("Metin Taþ"))
                                {
                                	imv.setImageResource(R.drawable.mozcan);
                                	isim = "Metin Taþ-Sezgin Özcan";
                                }
                                else if (s.contains("Ahmet Ýnam"))
                                {
                                	imv.setImageResource(R.drawable.ainam);
                                	isim = "Ahmet Ýnam";
                                }
                                else if (s.contains("Gürkan Hacýr"))
                                {
                                	imv.setImageResource(R.drawable.ghacir);
                                	isim = "Gürkan Hacýr";
                                }
                                else if (s.contains("Esin Gedik"))
                                {
                                	imv.setImageResource(R.drawable.egedik);
                                	isim = "Esin Gedik";
                                }
                                else if (s.contains("Elif Aktuð"))
                                {
                                	imv.setImageResource(R.drawable.eaktug);
                                	isim = "Elif Aktuð";
                                }
                                else if (s.contains("Nilay Örnek"))
                                {
                                	imv.setImageResource(R.drawable.nornek);
                                	isim = "Nilay Örnek";
                                }
                                else if (s.contains("Þansal Büyüka"))
                                {
                                	imv.setImageResource(R.drawable.aksam);
                                	isim = "Þansal Büyüka";
                                }
                                else if (s.contains("Mustafa Sapmaz"))
                                {
                                	imv.setImageResource(R.drawable.msapmaz);
                                	isim = "Mustafa Sapmaz";
                                }
                                else if (s.contains("Nihal Kemaloðlu"))
                                {
                                	imv.setImageResource(R.drawable.nkemaloglu);
                                	isim = "Nihal Kemaloðlu";
                                }
                                else if (s.contains("Sevim Gözay"))
                                {
                                	imv.setImageResource(R.drawable.sgozay);
                                	isim = "Sevim Gözay";
                                }
                                else if (s.contains("Hüsnü Mahalli"))
                                {
                                	imv.setImageResource(R.drawable.hmahalli);
                                	isim = "Hüsnü Mahalli";
                                }
                                else if (s.contains("Simge Çýtak"))
                                {
                                	imv.setImageResource(R.drawable.scitak);
                                	isim = "Simge Çýtak";
                                }
                                else if (s.contains("Nedim Atilla"))
                                {
                                	imv.setImageResource(R.drawable.natilla);
                                	isim = "Nedim Atilla";
                                }
                                else if (s.contains("Deniz Ülke Arýboðan"))
                                {
                                	imv.setImageResource(R.drawable.daribogan);
                                	isim = "Deniz Ülke Arýboðan";
                                }
                                else if (s.contains("Özgür Aras"))
                                {
                                	imv.setImageResource(R.drawable.oaras);
                                	isim = "Özgür Aras";
                                }
                                else if (s.contains("Önder"))
                                {
                                	imv.setImageResource(R.drawable.aksam);
                                	isim = "Önder";
                                }
                                else if (s.contains("Pervin Dinçer"))
                                {
                                	imv.setImageResource(R.drawable.pdincer);
                                	isim = "Pervin Dinçer";
                                }
                                else if (s.contains("Orkun Bulut"))
                                {
                                	imv.setImageResource(R.drawable.obulut);
                                	isim = "Orkun Bulut";
                                }
                                else if (s.contains("Barýþ Kocaoðlu"))
                                {
                                	imv.setImageResource(R.drawable.bkocaoglu);
                                	isim = "Barýþ Kocaoðlu";
                                }
                                else
                                {
                                	imv.setImageResource(R.drawable.aksam);
                                }
                
                                //Hürriyet Yazarlar Son       
                                title.setText(isim);
                                date.setText(data.date);
                                description.setText(baslik);
                                
                        }

                       
                        return view;
                }
    }
}
