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


public class rssreaderkyyenisafak extends ListActivity {
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
                        retrieveRSSFeed("http://beta.yenisafak.com.tr/rss/?xml=yazarlar",itemlist);
                       
                        rssadaptor = new RSSListAdaptor(rssreaderkyyenisafak.this, R.layout.rssitemviewkopya,itemlist);
                       
                        return null;
                }
       
                @Override
                protected void onCancelled() {
                        super.onCancelled();
                }
               
                @Override
                protected void onPreExecute() {
                        progress = ProgressDialog.show(
                                        rssreaderkyyenisafak.this, null, "Yükleniyor...");
                       
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
                                LayoutInflater vi = (LayoutInflater)rssreaderkyyenisafak.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                                
                                String search = ":"; 
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
                                if (s.contains("Ali Bayramoðlu"))
                                {
                                    imv.setImageResource(R.drawable.abayramoglu);
                                	isim = "Ali Bayramoðlu";
                                }
                                else if (s.contains("Salih Tuna"))
                                {
                                	imv.setImageResource(R.drawable.stuna);
                                	isim = "Salih Tuna";
                                }
                                else if (s.contains("Akif Emre"))
                                {
                                	imv.setImageResource(R.drawable.aemre);
                                	isim = "Akif Emre";
                                }
                                else if (s.contains("Müfit Yüksel"))
                                {
                                	imv.setImageResource(R.drawable.myuksel);
                                	isim = "Müfit Yüksel";
                                }
                                else if (s.contains("Mehmet Þeker"))
                                {
                                	imv.setImageResource(R.drawable.mseker);
                                	isim = "Mehmet Þeker";
                                }
                                else if (s.contains("Murat Aksoy"))
                                {
                                	imv.setImageResource(R.drawable.maksoy);
                                	isim = "Murat Aksoy";
                                }
                                else if (s.contains("Yasin Aktay"))
                                {
                                	imv.setImageResource(R.drawable.yaktay);
                                	isim = "Yasin Aktay";
                                }
                                else if (s.contains("Ali Saydam"))
                                {
                                	imv.setImageResource(R.drawable.asaydam);
                                	isim = "Ali Saydam";
                                }
                                else if (s.contains("Ýbrahim Tenekeci"))
                                {
                                	imv.setImageResource(R.drawable.itenekeci);
                                	isim = "Ýbrahim Tenekeci";
                                }
                                else if (s.contains("Ýbrahim Karagül"))
                                {
                                	imv.setImageResource(R.drawable.ikaragul);
                                	isim = "Ýbrahim Karagül";
                                }
                                else if (s.contains("Kürþat Bumin"))
                                {
                                	imv.setImageResource(R.drawable.kbumin);
                                	isim = "Kürþat Bumin";
                                }
                                else if (s.contains("Hilal Kaplan"))
                                {
                                	imv.setImageResource(R.drawable.hkaplan);
                                	isim = "Hilal Kaplan";
                                }
                                else if (s.contains("Fatma Barbarasoðlu"))
                                {
                                   
                                    imv.setImageResource(R.drawable.fbarbarosglu);
                                	isim = "Fatma Barbarasoðlu";
                                }
                                else if (s.contains("Abdulkadir Selvi"))
                                {
                                	imv.setImageResource(R.drawable.aselvi);
                                	isim = "Abdulkadir Selvi";
                                }
                                else if (s.contains("Mahmut Osmanoðlu"))
                                {
                                	imv.setImageResource(R.drawable.mosmanoglu);
                                	isim = "Mahmut Osmanoðlu";
                                }
                                else if (s.contains("Ayþe Böhürler"))
                                {
                                	imv.setImageResource(R.drawable.abohurler);
                                	isim = "Ayþe Böhürler";
                                }
                                else
                                {
                                	imv.setImageResource(R.drawable.yenisafak);
                                }
                
                                //Hürriyet Yazarlar Son       
                                title.setText(isim);
                                date.setText("on " + data.date);
                                description.setText(baslik);
                                
                        }

                       
                        return view;
                }
    }
}
