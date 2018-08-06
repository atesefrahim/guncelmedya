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
                                        rssreaderkyaksam.this, null, "Y�kleniyor...");
                       
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
//H�rriyet Yazarlar Ba�lang��
                                if (s.contains("Ali Ece"))
                                {
                                    imv.setImageResource(R.drawable.aece);
                                	isim = "Ali Ece";
                                }
                                else if (s.contains("Avni Ba�o�lu"))
                                {
                                	imv.setImageResource(R.drawable.abasoglu);
                                	isim = "Avni Ba�o�lu";
                                }
                                else if (s.contains("Serkan Yetkin"))
                                {
                                	imv.setImageResource(R.drawable.syetkin);
                                	isim = "Serkan Yetkin";
                                }
                                else if (s.contains("�i�dem Toker"))
                                {
                                	imv.setImageResource(R.drawable.ctoker);
                                	isim = "�i�dem Toker";
                                }
                                else if (s.contains("Funda �zkan"))
                                {
                                	imv.setImageResource(R.drawable.fozkan);
                                	isim = "Funda �zkan";
                                }
                                else if (s.contains("�smail K���kkaya"))
                                {
                                	imv.setImageResource(R.drawable.ikucukkaya);
                                	isim = "�smail K���kkaya";
                                }
                                else if (s.contains("Deniz G�k�e"))
                                {
                                	imv.setImageResource(R.drawable.dgokce);
                                	isim = "Deniz G�k�e";
                                }
                                else if (s.contains("Alaattin Metin"))
                                {
                                	imv.setImageResource(R.drawable.ametin);
                                	isim = "Alaattin Metin";
                                }
                                else if (s.contains("Tu��e Tatari"))
                                {
                                	imv.setImageResource(R.drawable.ttatari);
                                	isim = "Tu��e Tatari";
                                }
                                else if (s.contains("Metin Ta�"))
                                {
                                	imv.setImageResource(R.drawable.mozcan);
                                	isim = "Metin Ta�-Sezgin �zcan";
                                }
                                else if (s.contains("Ahmet �nam"))
                                {
                                	imv.setImageResource(R.drawable.ainam);
                                	isim = "Ahmet �nam";
                                }
                                else if (s.contains("G�rkan Hac�r"))
                                {
                                	imv.setImageResource(R.drawable.ghacir);
                                	isim = "G�rkan Hac�r";
                                }
                                else if (s.contains("Esin Gedik"))
                                {
                                	imv.setImageResource(R.drawable.egedik);
                                	isim = "Esin Gedik";
                                }
                                else if (s.contains("Elif Aktu�"))
                                {
                                	imv.setImageResource(R.drawable.eaktug);
                                	isim = "Elif Aktu�";
                                }
                                else if (s.contains("Nilay �rnek"))
                                {
                                	imv.setImageResource(R.drawable.nornek);
                                	isim = "Nilay �rnek";
                                }
                                else if (s.contains("�ansal B�y�ka"))
                                {
                                	imv.setImageResource(R.drawable.aksam);
                                	isim = "�ansal B�y�ka";
                                }
                                else if (s.contains("Mustafa Sapmaz"))
                                {
                                	imv.setImageResource(R.drawable.msapmaz);
                                	isim = "Mustafa Sapmaz";
                                }
                                else if (s.contains("Nihal Kemalo�lu"))
                                {
                                	imv.setImageResource(R.drawable.nkemaloglu);
                                	isim = "Nihal Kemalo�lu";
                                }
                                else if (s.contains("Sevim G�zay"))
                                {
                                	imv.setImageResource(R.drawable.sgozay);
                                	isim = "Sevim G�zay";
                                }
                                else if (s.contains("H�sn� Mahalli"))
                                {
                                	imv.setImageResource(R.drawable.hmahalli);
                                	isim = "H�sn� Mahalli";
                                }
                                else if (s.contains("Simge ��tak"))
                                {
                                	imv.setImageResource(R.drawable.scitak);
                                	isim = "Simge ��tak";
                                }
                                else if (s.contains("Nedim Atilla"))
                                {
                                	imv.setImageResource(R.drawable.natilla);
                                	isim = "Nedim Atilla";
                                }
                                else if (s.contains("Deniz �lke Ar�bo�an"))
                                {
                                	imv.setImageResource(R.drawable.daribogan);
                                	isim = "Deniz �lke Ar�bo�an";
                                }
                                else if (s.contains("�zg�r Aras"))
                                {
                                	imv.setImageResource(R.drawable.oaras);
                                	isim = "�zg�r Aras";
                                }
                                else if (s.contains("�nder"))
                                {
                                	imv.setImageResource(R.drawable.aksam);
                                	isim = "�nder";
                                }
                                else if (s.contains("Pervin Din�er"))
                                {
                                	imv.setImageResource(R.drawable.pdincer);
                                	isim = "Pervin Din�er";
                                }
                                else if (s.contains("Orkun Bulut"))
                                {
                                	imv.setImageResource(R.drawable.obulut);
                                	isim = "Orkun Bulut";
                                }
                                else if (s.contains("Bar�� Kocao�lu"))
                                {
                                	imv.setImageResource(R.drawable.bkocaoglu);
                                	isim = "Bar�� Kocao�lu";
                                }
                                else
                                {
                                	imv.setImageResource(R.drawable.aksam);
                                }
                
                                //H�rriyet Yazarlar Son       
                                title.setText(isim);
                                date.setText(data.date);
                                description.setText(baslik);
                                
                        }

                       
                        return view;
                }
    }
}
