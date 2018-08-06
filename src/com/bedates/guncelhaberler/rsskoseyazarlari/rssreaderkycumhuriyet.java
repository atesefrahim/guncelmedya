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


public class rssreaderkycumhuriyet extends ListActivity {
        private ArrayList<RSSItem> itemlist = null;
        private RSSListAdaptor rssadaptor = null;
        static String myDate; 
        static String myDatey; 
        
       
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
           Calendar currentDate = Calendar.getInstance();
           SimpleDateFormat formatter= 
           new SimpleDateFormat("yyyy-MM-dd");
           myDate = formatter.format(currentDate.getTime());
           System.out.println(myDate);
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
                        retrieveRSSFeed("http://www.cumhuriyet.com.tr/?kn=5&tumu=E&xl=rss",itemlist);
                       
                        rssadaptor = new RSSListAdaptor(rssreaderkycumhuriyet.this, R.layout.rssitemviewkopya,itemlist);
                       
                        return null;
                }
       
                @Override
                protected void onCancelled() {
                        super.onCancelled();
                }
               
                @Override
                protected void onPreExecute() {
                        progress = ProgressDialog.show(
                        		rssreaderkycumhuriyet.this, null, "Yükleniyor...");
                       
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
                                LayoutInflater vi = (LayoutInflater)rssreaderkycumhuriyet.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                view = vi.inflate(R.layout.rssitemviewkopya, null);
                        }
                       
                        RSSItem data = objects.get(position);
	
                        Calendar today1 = Calendar.getInstance();  
                 
                     today1.add(Calendar.DATE, -1);  

                     java.sql.Date yesterday = new java.sql.Date(today1.getTimeInMillis());   
                     SimpleDateFormat formatter2= 
                    		 	new SimpleDateFormat("yyyy-MM-dd");
                             	myDatey = formatter2.format(yesterday);

                               
                        if(null != data)
                        {
                                TextView title = (TextView)view.findViewById(R.id.txtTitle);
                                TextView date = (TextView)view.findViewById(R.id.txtDate);
                                TextView description = (TextView)view.findViewById(R.id.txtDescription);
                               
                                ImageView imv = (ImageView)view.findViewById(R.id.yazarlogo);
                                
                                String isim = data.title;
                                String baslik = data.description;
                                
                                String search = "|"; 
                                String result = ""; 
                                String resulttarih = ""; 

                                int i; 
                                do { 
                                    baslik = baslik.replaceAll("&#8216;", "'");
                                    baslik = baslik.replaceAll("&#8217;", "'");
                                    baslik = baslik.replaceAll("&#8230;", "...'");
                                    baslik = baslik.replaceAll("\\s"," ");
                                i = isim.indexOf(search); 
                                resulttarih = data.date.substring(0, 10);
                                int a = i;
                                if(i != -1) { 
                                	
                                result = isim.substring(0, a);
                                 
                                isim = result; 
                                data.date = resulttarih;
                                } 
                                } while(i != -1); 
                                
        
                              String s = isim;
//Cumhuriyet Yazarlar Baþlangýç
                                if (s.contains("Cüneyt Arcayürek"))
                                {
                                    imv.setImageResource(R.drawable.carcayurek);
                                	isim = "Cüneyt Arcayürek";
                                }
                                else if (s.contains("Oktay Akbal"))
                                {
                                	imv.setImageResource(R.drawable.oakbal);
                                	isim = "Oktay Akbal";
                                }
                                else if (s.contains("Bekir Coþkun"))
                                {
                                	imv.setImageResource(R.drawable.bcoskun);
                                	isim = "Bekir Coþkun";
                                }
                                else if (s.contains("Emre Kongar"))
                                {
                                	imv.setImageResource(R.drawable.ekongar);
                                	isim = "Emre Kongar";
                                }
                                else if (s.contains("Ali Sirmen"))
                                {
                                	imv.setImageResource(R.drawable.asirmen);
                                	isim = "Ali Sirmen";
                                }
                                else if (s.contains("Hikmet Çetinkaya"))
                                {
                                	imv.setImageResource(R.drawable.hcetinkaya);
                                	isim = "Hikmet Çetinkaya";
                                }
                                else if (s.contains("Orhan Bursalý"))
                                {
                                	imv.setImageResource(R.drawable.obursali);
                                	isim = "Orhan Bursalý";
                                }
                                else if (s.contains("Orhan Birgit"))
                                {
                                	imv.setImageResource(R.drawable.obirgit);
                                	isim = "Orhan Birgit";
                                }
                                else if (s.contains("Özgen Acar"))
                                {
                                	imv.setImageResource(R.drawable.oacar);
                                	isim = "Özgen Acar";
                                }
                                else if (s.contains("Utku Çakýrözer"))
                                {
                                	imv.setImageResource(R.drawable.ucakirozer);
                                	isim = "Utku Çakýrözer";
                                }
                                else if (s.contains("Kürþat Baþar"))
                                {
                                	imv.setImageResource(R.drawable.kbasar);
                                	isim = "Kürþat Baþar";
                                }
                                else if (s.contains("Þükran Soner"))
                                {
                                	imv.setImageResource(R.drawable.ssoner);
                                	isim = "Þükran Soner";
                                }
                                else if (s.contains("Nilgün Cerrahoðlu"))
                                {
                                	imv.setImageResource(R.drawable.ncerrahoglu);
                                	isim = "Nilgün Cerrahoðlu";
                                }
                                else if (s.contains("Mustafa Pamukoðlu"))
                                {
                                	imv.setImageResource(R.drawable.mpamukoglu);
                                	isim = "Mustafa Pamukoðlu";
                                }
                                else if (s.contains("Serdar Kýzýk"))
                                {
                                	imv.setImageResource(R.drawable.skizik);
                                	isim = "Serdar Kýzýk";
                                }
                                else if (s.contains("Ahmet Tan"))
                                {
                                	imv.setImageResource(R.drawable.atan);
                                	isim = "Ahmet Tan";
                                }
                                else if (s.contains("Bedri Baykam"))
                                {
                                	imv.setImageResource(R.drawable.bbaykam);
                                	isim = "Bedri Baykam";
                                }
                                else if (s.contains("Ýnci Aral"))
                                {
                                	imv.setImageResource(R.drawable.iaral);
                                	isim = "Ýnci Aral";
                                }
                                else if (s.contains("Iþýl Özgentürk"))
                                {
                                	imv.setImageResource(R.drawable.iozgenturk);
                                	isim = "Iþýl Özgentürk";
                                }
                                else if (s.contains("Adnan Dinçer"))
                                {
                                	imv.setImageResource(R.drawable.cumhuriyet);
                                	isim = "Adnan Dinçer";
                                }
                                else if (s.contains("Haydar Ergülen"))
                                {
                                	imv.setImageResource(R.drawable.cumhuriyet);
                                	isim = "Haydar Ergülen";
                                }
                                else if (s.contains("Mustafa Balbay"))
                                {
                                	imv.setImageResource(R.drawable.mbalbay);
                                	isim = "Mustafa Balbay";
                                }
                                else if (s.contains("Orhan Erinç"))
                                {
                                	imv.setImageResource(R.drawable.oerinc);
                                	isim = "Orhan Erinç";
                                }
                                else if (s.contains("Erdal Atabek"))
                                {
                                	imv.setImageResource(R.drawable.eatabek);
                                	isim = "Erdal Atabek";
                                }
                                else if (s.contains("Mustafa Sönmez"))
                                {
                                	imv.setImageResource(R.drawable.msonmez);
                                	isim = "Mustafa Sönmez";
                                }
                                else if (s.contains("Cumhuriyet"))
                                {
                                	imv.setImageResource(R.drawable.cumhuriyet);
                                	isim = "Cumhuriyet";
                                }
                                else if (s.contains("Yakup Kepenek"))
                                {
                                	imv.setImageResource(R.drawable.ykepenek);
                                	isim = "Yakup Kepenek";
                                }
                                else if (s.contains("Öztin Akgüç"))
                                {
                                	imv.setImageResource(R.drawable.oakguc);
                                	isim = "Öztin Akgüç";
                                }else if (s.contains("Deniz Kavukçuoðlu"))
                                {
                                	imv.setImageResource(R.drawable.dkavukcuoglu);
                                	isim = "Deniz Kavukçuoðlu";
                                }else if (s.contains("Oktay Ekinci"))
                                {
                                	imv.setImageResource(R.drawable.oekinci);
                                	isim = "Oktay Ekinci";
                                }else if (s.contains("Mine G. Kýrýkkanat"))
                                {
                                	imv.setImageResource(R.drawable.cumhuriyet);
                                	isim = "Mine G. Kýrýkkanat";
                                }else if (s.contains("Zeynep Oral"))
                                {
                                	imv.setImageResource(R.drawable.zoral);
                                	isim = "Zeynep Oral";
                                }else if (s.contains("Oðuz Tongsir"))
                                {
                                	imv.setImageResource(R.drawable.cumhuriyet);
                                	isim = "Oðuz Tongsir";
                                }else if (s.contains("Iþýk Kansu"))
                                {
                                	imv.setImageResource(R.drawable.ikansu);
                                	isim = "Iþýk Kansu";
                                }else if (s.contains("Zeynep Göðüþ"))
                                {
                                	imv.setImageResource(R.drawable.zgogus);
                                	isim = "Zeynep Göðüþ";
                                }else if (s.contains("Barbaros Talý"))
                                {
                                	imv.setImageResource(R.drawable.btali);
                                	isim = "Barbaros Talý";
                                }else if (s.contains("Mümtaz Soysal"))
                                {
                                	imv.setImageResource(R.drawable.msoysal);
                                	isim = "Mümtaz Soysal";
                                }
                                else if (s.contains("Ataol Behramoðlu"))
                                {
                                	imv.setImageResource(R.drawable.abehramoglu);
                                	isim = "Ataol Behramoðlu";
                                }
                                else if (s.contains("Ülkü Tamer"))
                                {
                                	imv.setImageResource(R.drawable.cumhuriyet);
                                	isim = "Ülkü Tamer";
                                }
                                else
                                {
                                	imv.setImageResource(R.drawable.cumhuriyet);
                                }
                
                                //Cumhuriyet Yazarlar Son       
                                title.setText(isim);
                                date.setText(data.date);
                                description.setText(baslik);
                                
                        }

                       
                        return view;
                }
    }
}
