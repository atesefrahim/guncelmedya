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


public class rssreaderkybugun extends ListActivity {
        private ArrayList<RSSItem> itemlist = null;
        private RSSListAdaptor rssadaptor = null;
        static String myDate; 
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
           //is.setEncoding("UTF-8");
           xmlreader.parse(is);
           Calendar currentDate = Calendar.getInstance();
           SimpleDateFormat formatter= 
           new SimpleDateFormat("yyyy-MM-dd");
           myDate = formatter.format(currentDate.getTime());
           
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
                        retrieveRSSFeed("http://www.bugun.com.tr/rss/yazarlar.xml",itemlist);
                       
                        rssadaptor = new RSSListAdaptor(rssreaderkybugun.this, R.layout.rssitemviewkopya,itemlist);
                       
                        return null;
                }
       
                @Override
                protected void onCancelled() {
                        super.onCancelled();
                }
               
                @Override
                protected void onPreExecute() {
                        progress = ProgressDialog.show(
                        		rssreaderkybugun.this, null, "Yükleniyor...");
                       
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
                                LayoutInflater vi = (LayoutInflater)rssreaderkybugun.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                                
                                String search = "-"; 
                                String result = ""; 
                                String resultisim = ""; 
                                String resulttarih = ""; 
                                
                                int i; 
                                do { 
                                i = baslik.indexOf(search); 
                                int a = i;
                                if(i != -1) { 
                                	
                                result = baslik.substring(0, a);
                                resulttarih = data.date.substring(0, 10);
                                baslik = result; 
                                int y = isim.length();
                                resultisim = isim.substring(a+2 ,y);
                                isim = resultisim;
                                data.date = resulttarih;
                                } 
                                } while(i != -1); 
                              String s = isim;

                                if (s.contains("Erhan BAÞYURT"))
                                {
                                    imv.setImageResource(R.drawable.ebasyurt);
                                	isim = "Erhan BAÞYURT";
                                }
                                else if (s.contains("Gülay GÖKTÜRK"))
                                {
                                	imv.setImageResource(R.drawable.ggokturk);
                                	isim = "Gülay GÖKTÜRK";
                                }
                                else if (s.contains("Ahmet TAÞGETÝREN"))
                                {
                                	imv.setImageResource(R.drawable.atasgetiren);
                                	isim = "Ahmet TAÞGETÝREN";
                                }
                                else if (s.contains("Nuh GÖNÜLTAÞ"))
                                {
                                	imv.setImageResource(R.drawable.ngonultas);
                                	isim = "Nuh GÖNÜLTAÞ";
                                }
                                else if (s.contains("Gültekin AVCI"))
                                {
                                	imv.setImageResource(R.drawable.gavci);
                                	isim = "Gültekin AVCI";
                                }
                                else if (s.contains("Doðu ERGÝL"))
                                {
                                	imv.setImageResource(R.drawable.dergil);
                                	isim = "Doðu ERGÝL";
                                }
                                else if (s.contains("Ali Atýf BÝR"))
                                {
                                	imv.setImageResource(R.drawable.abir);
                                	isim = "Ali Atýf BÝR";
                                }
                                else if (s.contains("Seda ÞÝMÞEK"))
                                {
                                	imv.setImageResource(R.drawable.ssimsek);
                                	isim = "Seda ÞÝMÞEK";
                                }
                                else if (s.contains("Adem Yavuz ARSLAN"))
                                {
                                	imv.setImageResource(R.drawable.aarslan);
                                	isim = "Adem Yavuz ARSLAN";
                                }
                                else if (s.contains("Vedat BÝLGÝN"))
                                {
                                	imv.setImageResource(R.drawable.vbilgin);
                                	isim = "Vedat BÝLGÝN";
                                }
                                else if (s.contains("Toktamýþ ATEÞ"))
                                {
                                	imv.setImageResource(R.drawable.tates);
                                	isim = "Toktamýþ ATEÞ";
                                }
                                else if (s.contains("Osman ARIOÐLU"))
                                {
                                	imv.setImageResource(R.drawable.oarioglu);
                                	isim = "Osman ARIOÐLU";
                                }
                                else if (s.contains("Aykut IÞIKLAR"))
                                {
                                	imv.setImageResource(R.drawable.aisiklar);
                                	isim = "Aykut IÞIKLAR";
                                }
                                else if (s.contains("Perihan ÇAKIROÐLU"))
                                {
                                	imv.setImageResource(R.drawable.pcakiroglu);
                                	isim = "Perihan ÇAKIROÐLU";
                                }
                                else if (s.contains("Tarýk TOROS"))
                                {
                                	imv.setImageResource(R.drawable.ttoros);
                                	isim = "Tarýk TOROS";
                                }
                                else if (s.contains("Erhan AFYONCU"))
                                {
                                	imv.setImageResource(R.drawable.eafyoncu);
                                	isim = "Erhan AFYONCU";
                                }
                                else if (s.contains("Prof. Dr. Ünsal BAN"))
                                {
                                	imv.setImageResource(R.drawable.uban);
                                	isim = "Prof. Dr. Ünsal BAN";
                                }
                                else if (s.contains("Celal TOPRAK"))
                                {
                                	imv.setImageResource(R.drawable.ctoprak);
                                	isim = "Celal TOPRAK";
                                }
                                else if (s.contains("Bilal ÖZCAN"))
                                {
                                	imv.setImageResource(R.drawable.bozcan);
                                	isim = "Bilal ÖZCAN";
                                }
                                else if (s.contains("Sadettin ORHAN"))
                                {
                                	imv.setImageResource(R.drawable.sorhan);
                                	isim = "Sadettin ORHAN";
                                }
                                else if (s.contains("Ýsmail KÖKBULUT"))
                                {
                                	imv.setImageResource(R.drawable.ikokbulut);
                                	isim = "Ýsmail KÖKBULUT";
                                }
                                else if (s.contains("Elif KORKMAZEL"))
                                {
                                	imv.setImageResource(R.drawable.ekorkmazel);
                                	isim = "Elif KORKMAZEL";
                                }
                                else if (s.contains("Yaþar ERDÝNÇ"))
                                {
                                	imv.setImageResource(R.drawable.yerdinc);
                                	isim = "Yaþar ERDÝNÇ";
                                }
                                else if (s.contains("Esra UÇAR"))
                                {
                                	imv.setImageResource(R.drawable.eucar);
                                	isim = "Esra UÇAR";
                                }
                                else if (s.contains("Dr.Mehmet CAVLI"))
                                {
                                	imv.setImageResource(R.drawable.mcavli);
                                	isim = "Dr.Mehmet CAVLI";
                                }
                                else if (s.contains("Engin VEREL"))
                                {
                                	imv.setImageResource(R.drawable.everel);
                                	isim = "Engin VEREL";
                                }
                                else if (s.contains("Hüseyin YILMAZ"))
                                {
                                	imv.setImageResource(R.drawable.hyilmaz);
                                	isim = "Hüseyin YILMAZ";
                                }
                                else if (s.contains("Ali ÞEN"))
                                {
                                	imv.setImageResource(R.drawable.asen);
                                	isim = "Ali ÞEN";
                                }
                                else if (s.contains("Cüneyt TANMAN"))
                                {
                                	imv.setImageResource(R.drawable.ctanman);
                                	isim = "Cüneyt TANMAN";
                                }
                                else if (s.contains("Gökmen ÖZDENAK"))
                                {
                                	imv.setImageResource(R.drawable.gozdenak);
                                	isim = "Gökmen ÖZDENAK";
                                }
                                else if (s.contains("Alp YALMAN"))
                                {
                                	imv.setImageResource(R.drawable.ayalman);
                                	isim = "Alp YALMAN";
                                }
                                else if (s.contains("Lemi ÇELÝK"))
                                {
                                	imv.setImageResource(R.drawable.lcelik);
                                	isim = "Lemi ÇELÝK";
                                }
                                else if (s.contains("Erdoðan SÜZER"))
                                {
                                	imv.setImageResource(R.drawable.esuzer);
                                	isim = "Erdoðan SÜZER";
                                }
                                else if (s.contains("Ziya DOÐAN"))
                                {
                                	imv.setImageResource(R.drawable.zdogan);
                                	isim = "Ziya DOÐAN";
                                }
                                else if (s.contains("Aysun KABA"))
                                {
                                	imv.setImageResource(R.drawable.akaba);
                                	isim = "Aysun KABA";
                                }
                                else if (s.contains("Ýbrahim ÜZÜLMEZ"))
                                {
                                	imv.setImageResource(R.drawable.iuzulmez);
                                	isim = "Ýbrahim ÜZÜLMEZ";
                                }
                                else if (s.contains("Ahmet KONANÇ"))
                                {
                                	imv.setImageResource(R.drawable.akonanc);
                                	isim = "Ahmet KONANÇ";
                                }
                                else if (s.contains("Ertuðrul DÝLEK"))
                                {
                                	imv.setImageResource(R.drawable.edilek);
                                	isim = "Ertuðrul DÝLEK";
                                }
                                else if (s.contains("Adem YILMAZ"))
                                {
                                	imv.setImageResource(R.drawable.ayilmaz);
                                	isim = "Adem YILMAZ";
                                }
                                else
                                {
                                	imv.setImageResource(R.drawable.bugun);
                                }
                            
                                //Bugün Yazarlar Son       
                                title.setText(isim);
                                date.setText(data.date);
                                description.setText(baslik);
                               
                        }

                       
                        return view;
                }
    }
}
