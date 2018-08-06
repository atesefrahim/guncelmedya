package com.bedates.guncelhaberler.rsskoseyazarlari;


import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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

public class rssreaderkoseyazarlarihurriyet extends ListActivity {
        private ArrayList<RSSItem> itemlist = null;
        private RSSListAdaptor rssadaptor = null;
        static String myDate; 
        static String myDate2; 
        static String myDate3; 
       
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
           new SimpleDateFormat("dd MMM yyyy");
           SimpleDateFormat formatter2= 
           new SimpleDateFormat("dd MMMM yyyy" , new Locale("tr"));
           myDate = formatter.format(currentDate.getTime());
           myDate3 = formatter2.format(currentDate.getTime());
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
                        retrieveRSSFeed("http://rss.hurriyet.com.tr/rss.aspx?sectionId=9",itemlist);
                       
                        rssadaptor = new RSSListAdaptor(rssreaderkoseyazarlarihurriyet.this, R.layout.rssitemviewkopya,itemlist);
                       
                        return null;
                }
       
                @Override
                protected void onCancelled() {
                        super.onCancelled();
                }
               
                @Override
                protected void onPreExecute() {
                        progress = ProgressDialog.show(
                                        rssreaderkoseyazarlarihurriyet.this, null, "Yükleniyor...");
                       
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
                                LayoutInflater vi = (LayoutInflater)rssreaderkoseyazarlarihurriyet.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                view = vi.inflate(R.layout.rssitemviewkopya, null);
                        }
                       
                        RSSItem data = objects.get(position);

                        Calendar today1 = Calendar.getInstance();  
                        
                        today1.add(Calendar.DATE, -1);
                    
                        java.sql.Date yesterday = new java.sql.Date(today1.getTimeInMillis());   

                        SimpleDateFormat formatter2= 
                             new SimpleDateFormat("dd MMM yyyy");

                             myDate2 = formatter2.format(yesterday);
                        
                        
                        
                        
                        if(null != data )
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
                                i = baslik.indexOf(search); 
                                int a = i;
                                if(i != -1) { 
                                	
                                result = baslik.substring(0, a);                                 
                                baslik = result; 
                                int y = isim.length();
                                resultisim = isim.substring(a+2 ,y);
                                isim = resultisim;
                                } 
                                } while(i != -1); 
                                
        
                              String s = isim;
//Hürriyet Yazarlar Baþlangýç
                                if (s.contains("Ahmet HAKAN"))
                                {
                                    imv.setImageResource(R.drawable.ahakan);
                                	isim = "Ahmet HAKAN";
                                }
                                else
                                    if (s.contains("Altan Tanrýkulu"))
                                    {
                                    	imv.setImageResource(R.drawable.atanrikulu);
                                        isim = "Altan Tanrýkulu";
                                    }  else
                                        if (s.contains("Banu TUNA"))
                                        {
                                        	imv.setImageResource(R.drawable.btuna);
                                            isim = "Banu TUNA";
                                        } else
                                            if (s.contains("Yorgo KIRBAKÝ"))
                                            {
                                            	imv.setImageResource(R.drawable.ykirbaki);
                                                isim = "Yorgo KIRBAKÝ";
                                            } else
                                        if (s.contains("Ayþe ARAL"))
                                        {
                                        	imv.setImageResource(R.drawable.aaral);
                                        	isim = "Ayþe ARAL";
                                        } else
                                            if (s.contains("Ayþe ARMAN"))
                                            {
                                            	imv.setImageResource(R.drawable.aarman);
                                            	isim = "Ayþe ARMAN";
                                            } else
                                                    if (s.contains("Cengiz SEMERCÝOÐLU"))
                                                    {
                                                    	imv.setImageResource(R.drawable.csemercioglu);
                                                    	isim = "Cengiz SEMERCÝOÐLU";
                                                    } else
                                                        if (s.contains("Doðan HIZLAN"))
                                                        {
                                                        	imv.setImageResource(R.drawable.dhizlan);
                                                        	isim = "Doðan HIZLAN";
                                                        } else
                                                            if (s.contains("Ege CANSEN"))
                                                            {
                                                            	imv.setImageResource(R.drawable.ecansen);
                                                            	isim = "Ege CANSEN";
                                                            } else
                                                                if (s.contains("Bilgin GÖKBERK"))
                                                                {
                                                                	imv.setImageResource(R.drawable.bgokberk);
                                                                	isim = "Bilgin GÖKBERK";
                                                                } else
                                                                    if (s.contains("Ercan SAATÇÝ"))
                                                                    {
                                                                    	imv.setImageResource(R.drawable.esaatci);
                                                                    	isim = "Ercan SAATÇÝ";
                                                                    }else
                                                                        if (s.contains("Erdal SAÐLAM"))
                                                                        {
                                                                        	imv.setImageResource(R.drawable.esaglam);
                                                                        	isim = "Erdal SAÐLAM";
                                                                        }else
                                                                            if (s.contains("Erkan ÇELEBÝ"))
                                                                            {
                                                                            	imv.setImageResource(R.drawable.ecelebi);
                                                                            	isim = "Erkan ÇELEBÝ";
                                                                            }else
                                                                                if (s.contains("Erman TOROÐLU"))
                                                                                {
                                                                                	imv.setImageResource(R.drawable.etoroglu);
                                                                                	isim = "Erman TOROÐLU";
                                                                                }else
                                                                                    if (s.contains("Ertuðrul ÖZKÖK"))
                                                                                    {
                                                                                    	imv.setImageResource(R.drawable.eozkok);
                                                                                    	isim = "Ertuðrul ÖZKÖK";
                                                                                    }else
                                                                                        if (s.contains("Fatih ÇEKÝRGE"))
                                                                                        {
                                                                                        	imv.setImageResource(R.drawable.fcekirge);
                                                                                        	isim = "Fatih ÇEKÝRGE";
                                                                                        }else
                                                                                            if (s.contains("Gila BENMAYOR"))
                                                                                            {
                                                                                            	imv.setImageResource(R.drawable.gbenmayor);
                                                                                            	isim = "Gila BENMAYOR";
                                                                                            }else
                                                                                                if (s.contains("Gökhan KÝMSESÝZCAN"))
                                                                                                {
                                                                                                	imv.setImageResource(R.drawable.gkimsesizcan);
                                                                                                	isim = "Gökhan KÝMSESÝZCAN";
                                                                                                }else
                                                                                                        if (s.contains("Ýsmet BERKAN"))
                                                                                                        {
                                                                                                        	imv.setImageResource(R.drawable.iberkan);
                                                                                                        	isim = "Ýsmet BERKAN";
                                                                                                        }else
                                                                                                            if (s.contains("Kanat ATKAYA"))
                                                                                                            {
                                                                                                            	imv.setImageResource(R.drawable.katkaya);
                                                                                                            	isim = "Kanat ATKAYA";
                                                                                                            }else
                                                                                                                if (s.contains("Latif DEMÝRCÝ"))
                                                                                                                {
                                                                                                                	imv.setImageResource(R.drawable.ldemirci);
                                                                                                                	isim = "Latif DEMÝRCÝ";
                                                                                                                }else
                                                                                                                    if (s.contains("Mehmet ARSLAN"))
                                                                                                                    {
                                                                                                                    	imv.setImageResource(R.drawable.marslan);
                                                                                                                    	isim = "Mehmet ARSLAN";
                                                                                                                    }else
                                                                                                                        if (s.contains("Mehmet Y. YILMAZ"))
                                                                                                                        {
                                                                                                                        	imv.setImageResource(R.drawable.myilmaz);
                                                                                                                        	isim = "Mehmet Y. YILMAZ";
                                                                                                                        }else
                                                                                                                            if (s.contains("Melis ALPHAN"))
                                                                                                                            {
                                                                                                                            	imv.setImageResource(R.drawable.malphan);
                                                                                                                            	isim = "Melis ALPHAN";
                                                                                                                            }else
                                                                                                                                if (s.contains("Metehan Demir"))
                                                                                                                                {
                                                                                                                                	imv.setImageResource(R.drawable.mdemir);
                                                                                                                                	isim = "Metehan Demir";
                                                                                                                                }else
                                                                                                                                    if (s.contains("Nil KARAÝBRAHÝMGÝL"))
                                                                                                                                    {
                                                                                                                                    	imv.setImageResource(R.drawable.nkaraibrahimgil);
                                                                                                                                    	isim = "Nil KARAÝBRAHÝMGÝL";
                                                                                                                                    }else
                                                                                                                                        if (s.contains("Niobe"))
                                                                                                                                        {
                                                                                                                                        	imv.setImageResource(R.drawable.niobe);
                                                                                                                                        	isim = "Niobe";
                                                                                                                                        }else
                                                                                                                                            if (s.contains("Noyan Doðan"))
                                                                                                                                            {
                                                                                                                                            	imv.setImageResource(R.drawable.ndogan);
                                                                                                                                            	isim = "Noyan Doðan";
                                                                                                                                            }else
                                                                                                                                                if (s.contains("Onur BAÞTÜRK"))
                                                                                                                                                {
                                                                                                                                                	imv.setImageResource(R.drawable.obasturk);
                                                                                                                                                	isim = "Onur BAÞTÜRK";
                                                                                                                                                }else
                                                                                                                                                        if (s.contains("Oya ARMUTÇU"))
                                                                                                                                                        {
                                                                                                                                                        	imv.setImageResource(R.drawable.hurriyet);
                                                                                                                                                        	isim = "Oya ARMUTÇU";
                                                                                                                                                        }else
                                                                                                                                                            if (s.contains("Ömür GEDÝK"))
                                                                                                                                                            {
                                                                                                                                                            	imv.setImageResource(R.drawable.ogedik);
                                                                                                                                                            	isim = "Ömür GEDÝK";
                                                                                                                                                            }else
                                                                                                                                                                if (s.contains("Özgür BOLAT"))
                                                                                                                                                                {
                                                                                                                                                                	imv.setImageResource(R.drawable.obolat);
                                                                                                                                                                	isim = "Özgür BOLAT";
                                                                                                                                                                }else
                                                                                                                                                                    if (s.contains("Sahrap SOYSAL"))
                                                                                                                                                                    {
                                                                                                                                                                    	imv.setImageResource(R.drawable.ssoysal);
                                                                                                                                                                    	isim = "Sahrap SOYSAL";
                                                                                                                                                                    }else
                                                                                                                                                                        if (s.contains("Sedat Ergin"))
                                                                                                                                                                        {
                                                                                                                                                                        	imv.setImageResource(R.drawable.sergin);
                                                                                                                                                                        	isim = "Sedat Ergin";
                                                                                                                                                                        }else
                                                                                                                                                                            if (s.contains("Þükrü KIZILOT"))
                                                                                                                                                                            {
                                                                                                                                                                            	imv.setImageResource(R.drawable.skizilot);
                                                                                                                                                                            	isim = "Þükrü KIZILOT";
                                                                                                                                                                            }else
                                                                                                                                                                                if (s.contains("Þükrü KÜÇÜKÞAHÝN"))
                                                                                                                                                                                {
                                                                                                                                                                                	imv.setImageResource(R.drawable.skucuksahin);
                                                                                                                                                                                	isim = "Þükrü KÜÇÜKÞAHÝN";
                                                                                                                                                                                }else
                                                                                                                                                                                    if (s.contains("Taha AKYOL"))
                                                                                                                                                                                    {
                                                                                                                                                                                    	imv.setImageResource(R.drawable.takyol);
                                                                                                                                                                                    	isim = "Taha AKYOL";
                                                                                                                                                                                    }else
                                                                                                                                                                                        if (s.contains("Tolga TANIÞ"))
                                                                                                                                                                                        {
                                                                                                                                                                                        	imv.setImageResource(R.drawable.ttanis);
                                                                                                                                                                                        	isim = "Tolga TANIÞ";
                                                                                                                                                                                        }else
                                                                                                                                                                                            if (s.contains("Uður CEBECÝ"))
                                                                                                                                                                                            {
                                                                                                                                                                                            	imv.setImageResource(R.drawable.ucebeci);
                                                                                                                                                                                            	isim = "Uður CEBECÝ";
                                                                                                                                                                                            }else
                                                                                                                                                                                                if (s.contains("Vahap MUNYAR"))
                                                                                                                                                                                                {
                                                                                                                                                                                                	imv.setImageResource(R.drawable.vmunyar);
                                                                                                                                                                                                	isim = "Vahap MUNYAR";
                                                                                                                                                                                                }else
                                                                                                                                                                                                    if (s.contains("Yalçýn BAYER"))
                                                                                                                                                                                                    {
                                                                                                                                                                                                    	imv.setImageResource(R.drawable.ybayer);
                                                                                                                                                                                                    	isim = "Yalçýn BAYER";
                                                                                                                                                                                                    }else
                                                                                                                                                                                                        if (s.contains("Yalçýn DOÐAN"))
                                                                                                                                                                                                        {
                                                                                                                                                                                                        	imv.setImageResource(R.drawable.ydogan);
                                                                                                                                                                                                        	isim = "Yalçýn DOÐAN";
                                                                                                                                                                                                        }else
                                                                                                                                                                                                            if (s.contains("Yýlmaz ÖZDÝL"))
                                                                                                                                                                                                            {
                                                                                                                                                                                                            	imv.setImageResource(R.drawable.yozdil);
                                                                                                                                                                                                            	isim = "Yýlmaz ÖZDÝL";
                                                                                                                                                                                                            }else
                                                                                                                                                                                                                if (s.contains("Yonca TOKBAÞ"))
                                                                                                                                                                                                                {
                                                                                                                                                                                                                	imv.setImageResource(R.drawable.ytokbas);
                                                                                                                                                                                                                	isim = "Yonca TOKBAÞ";
                                                                                                                                                                                                                }else
                                                                                                                                                                                                                    if (s.contains("Mesude ERÞAN"))
                                                                                                                                                                                                                    {
                                                                                                                                                                                                                    	imv.setImageResource(R.drawable.mersan);
                                                                                                                                                                                                                    	isim = "Mesude ERÞAN";
                                                                                                                                                                                                                    }else
                                                                                                                                                                                                                        if (s.contains("Nuran ÇAKMAKÇI"))
                                                                                                                                                                                                                        {
                                                                                                                                                                                                                        	imv.setImageResource(R.drawable.ncakmakci);
                                                                                                                                                                                                                        	isim = "Nuran ÇAKMAKÇI";
                                                                                                                                                                                                                        }else
                                                                                                                                                                                                                            if (s.contains("Hüseyin YAYMAN"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.hyayman);
                                                                                                                                                                                                                            	isim = "Hüseyin YAYMAN";
                                                                                                                                                                                                                            }else if (s.contains("Cengiz ÇANDAR"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.ccandar);
                                                                                                                                                                                                                            	isim = "Cengiz ÇANDAR";
                                                                                                                                                                                                                            }else if (s.contains("Civan ER"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.cer);
                                                                                                                                                                                                                            	isim = "Civan ER";
                                                                                                                                                                                                                            }else if (s.contains("Ýlhan SÖYLER"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.isoyler);
                                                                                                                                                                                                                            	isim = "Ýlhan SÖYLER";
                                                                                                                                                                                                                            }else if (s.contains("Sibel Arna"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.sarna);
                                                                                                                                                                                                                            	isim = "Sibel Arna";
                                                                                                                                                                                                                            }else if (s.contains("Banu TUNA"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.btuna);
                                                                                                                                                                                                                            	isim = "Banu TUNA";
                                                                                                                                                                                                                            }else if (s.contains("Melda Narmanlý ÇÝMEN"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.mcimen);
                                                                                                                                                                                                                            	isim = "Melda Narmanlý ÇÝMEN";
                                                                                                                                                                                                                            }else if (s.contains("Güzin  Abla"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.gabla);
                                                                                                                                                                                                                            	isim = "Güzin  Abla";
                                                                                                                                                                                                                            }else if (s.contains("Nilüfer PAZVANTOÐLU"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.npazvantoglu);
                                                                                                                                                                                                                            	isim = "Nilüfer PAZVANTOÐLU";
                                                                                                                                                                                                                            }else if (s.contains("Prof.Dr. Mikdat KADIOÐLU"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.mkadioglu);
                                                                                                                                                                                                                            	isim = "Prof.Dr. Mikdat KADIOÐLU";
                                                                                                                                                                                                                            }else if (s.contains("Refika Birgül"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.rbirgul);
                                                                                                                                                                                                                            	isim = "Refika Birgül";
                                                                                                                                                                                                                            }else if (s.contains("Melike Karakartal"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.mkarakartal);
                                                                                                                                                                                                                            	isim = "Melike Karakartal";
                                                                                                                                                                                                                            }else if (s.contains("Osman MÜFTÜOÐLU"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.omuftuoglu);
                                                                                                                                                                                                                            	isim = "Osman MÜFTÜOÐLU";
                                                                                                                                                                                                                            }
                                                                                                                                                                                                                            else if (s.contains("Mehmet Ali BÝRAND"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.mbirand);
                                                                                                                                                                                                                            	isim = "Mehmet Ali BÝRAND";
                                                                                                                                                                                                                            }else if (s.contains("Rauf TAMER"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.rtamer);
                                                                                                                                                                                                                            	isim = "Rauf TAMER";
                                                                                                                                                                                                                            }else if (s.contains("Ünal Özüak"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.uozuak);
                                                                                                                                                                                                                            	isim = "Ünal ÖZÜAK";
                                                                                                                                                                                                                            }else if (s.contains("Mehmet YAÞÝN"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.myasin);
                                                                                                                                                                                                                            	isim = "Mehmet YAÞÝN";
                                                                                                                                                                                                                            }else if (s.contains("Figen BATUR"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.fbatur);
                                                                                                                                                                                                                            	isim = "Figen BATUR";
                                                                                                                                                                                                                            }else{
                                                                                                                                                                                                                        	 
                                                                                                                                                                                                                             imv.setImageResource(R.drawable.hurriyet);
                                                                                                                                                                                                                        }
                                //Hürriyet Yazarlar Son       
                                title.setText(isim);
                                date.setText("");
                                description.setText(baslik);
                                
                        }

                       
                        return view;
                }
    }
}
