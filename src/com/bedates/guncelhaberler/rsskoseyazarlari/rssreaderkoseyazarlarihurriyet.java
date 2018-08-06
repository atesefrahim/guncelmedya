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
                                        rssreaderkoseyazarlarihurriyet.this, null, "Y�kleniyor...");
                       
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
//H�rriyet Yazarlar Ba�lang��
                                if (s.contains("Ahmet HAKAN"))
                                {
                                    imv.setImageResource(R.drawable.ahakan);
                                	isim = "Ahmet HAKAN";
                                }
                                else
                                    if (s.contains("Altan Tanr�kulu"))
                                    {
                                    	imv.setImageResource(R.drawable.atanrikulu);
                                        isim = "Altan Tanr�kulu";
                                    }  else
                                        if (s.contains("Banu TUNA"))
                                        {
                                        	imv.setImageResource(R.drawable.btuna);
                                            isim = "Banu TUNA";
                                        } else
                                            if (s.contains("Yorgo KIRBAK�"))
                                            {
                                            	imv.setImageResource(R.drawable.ykirbaki);
                                                isim = "Yorgo KIRBAK�";
                                            } else
                                        if (s.contains("Ay�e ARAL"))
                                        {
                                        	imv.setImageResource(R.drawable.aaral);
                                        	isim = "Ay�e ARAL";
                                        } else
                                            if (s.contains("Ay�e ARMAN"))
                                            {
                                            	imv.setImageResource(R.drawable.aarman);
                                            	isim = "Ay�e ARMAN";
                                            } else
                                                    if (s.contains("Cengiz SEMERC�O�LU"))
                                                    {
                                                    	imv.setImageResource(R.drawable.csemercioglu);
                                                    	isim = "Cengiz SEMERC�O�LU";
                                                    } else
                                                        if (s.contains("Do�an HIZLAN"))
                                                        {
                                                        	imv.setImageResource(R.drawable.dhizlan);
                                                        	isim = "Do�an HIZLAN";
                                                        } else
                                                            if (s.contains("Ege CANSEN"))
                                                            {
                                                            	imv.setImageResource(R.drawable.ecansen);
                                                            	isim = "Ege CANSEN";
                                                            } else
                                                                if (s.contains("Bilgin G�KBERK"))
                                                                {
                                                                	imv.setImageResource(R.drawable.bgokberk);
                                                                	isim = "Bilgin G�KBERK";
                                                                } else
                                                                    if (s.contains("Ercan SAAT��"))
                                                                    {
                                                                    	imv.setImageResource(R.drawable.esaatci);
                                                                    	isim = "Ercan SAAT��";
                                                                    }else
                                                                        if (s.contains("Erdal SA�LAM"))
                                                                        {
                                                                        	imv.setImageResource(R.drawable.esaglam);
                                                                        	isim = "Erdal SA�LAM";
                                                                        }else
                                                                            if (s.contains("Erkan �ELEB�"))
                                                                            {
                                                                            	imv.setImageResource(R.drawable.ecelebi);
                                                                            	isim = "Erkan �ELEB�";
                                                                            }else
                                                                                if (s.contains("Erman TORO�LU"))
                                                                                {
                                                                                	imv.setImageResource(R.drawable.etoroglu);
                                                                                	isim = "Erman TORO�LU";
                                                                                }else
                                                                                    if (s.contains("Ertu�rul �ZK�K"))
                                                                                    {
                                                                                    	imv.setImageResource(R.drawable.eozkok);
                                                                                    	isim = "Ertu�rul �ZK�K";
                                                                                    }else
                                                                                        if (s.contains("Fatih �EK�RGE"))
                                                                                        {
                                                                                        	imv.setImageResource(R.drawable.fcekirge);
                                                                                        	isim = "Fatih �EK�RGE";
                                                                                        }else
                                                                                            if (s.contains("Gila BENMAYOR"))
                                                                                            {
                                                                                            	imv.setImageResource(R.drawable.gbenmayor);
                                                                                            	isim = "Gila BENMAYOR";
                                                                                            }else
                                                                                                if (s.contains("G�khan K�MSES�ZCAN"))
                                                                                                {
                                                                                                	imv.setImageResource(R.drawable.gkimsesizcan);
                                                                                                	isim = "G�khan K�MSES�ZCAN";
                                                                                                }else
                                                                                                        if (s.contains("�smet BERKAN"))
                                                                                                        {
                                                                                                        	imv.setImageResource(R.drawable.iberkan);
                                                                                                        	isim = "�smet BERKAN";
                                                                                                        }else
                                                                                                            if (s.contains("Kanat ATKAYA"))
                                                                                                            {
                                                                                                            	imv.setImageResource(R.drawable.katkaya);
                                                                                                            	isim = "Kanat ATKAYA";
                                                                                                            }else
                                                                                                                if (s.contains("Latif DEM�RC�"))
                                                                                                                {
                                                                                                                	imv.setImageResource(R.drawable.ldemirci);
                                                                                                                	isim = "Latif DEM�RC�";
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
                                                                                                                                    if (s.contains("Nil KARA�BRAH�MG�L"))
                                                                                                                                    {
                                                                                                                                    	imv.setImageResource(R.drawable.nkaraibrahimgil);
                                                                                                                                    	isim = "Nil KARA�BRAH�MG�L";
                                                                                                                                    }else
                                                                                                                                        if (s.contains("Niobe"))
                                                                                                                                        {
                                                                                                                                        	imv.setImageResource(R.drawable.niobe);
                                                                                                                                        	isim = "Niobe";
                                                                                                                                        }else
                                                                                                                                            if (s.contains("Noyan Do�an"))
                                                                                                                                            {
                                                                                                                                            	imv.setImageResource(R.drawable.ndogan);
                                                                                                                                            	isim = "Noyan Do�an";
                                                                                                                                            }else
                                                                                                                                                if (s.contains("Onur BA�T�RK"))
                                                                                                                                                {
                                                                                                                                                	imv.setImageResource(R.drawable.obasturk);
                                                                                                                                                	isim = "Onur BA�T�RK";
                                                                                                                                                }else
                                                                                                                                                        if (s.contains("Oya ARMUT�U"))
                                                                                                                                                        {
                                                                                                                                                        	imv.setImageResource(R.drawable.hurriyet);
                                                                                                                                                        	isim = "Oya ARMUT�U";
                                                                                                                                                        }else
                                                                                                                                                            if (s.contains("�m�r GED�K"))
                                                                                                                                                            {
                                                                                                                                                            	imv.setImageResource(R.drawable.ogedik);
                                                                                                                                                            	isim = "�m�r GED�K";
                                                                                                                                                            }else
                                                                                                                                                                if (s.contains("�zg�r BOLAT"))
                                                                                                                                                                {
                                                                                                                                                                	imv.setImageResource(R.drawable.obolat);
                                                                                                                                                                	isim = "�zg�r BOLAT";
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
                                                                                                                                                                            if (s.contains("��kr� KIZILOT"))
                                                                                                                                                                            {
                                                                                                                                                                            	imv.setImageResource(R.drawable.skizilot);
                                                                                                                                                                            	isim = "��kr� KIZILOT";
                                                                                                                                                                            }else
                                                                                                                                                                                if (s.contains("��kr� K���K�AH�N"))
                                                                                                                                                                                {
                                                                                                                                                                                	imv.setImageResource(R.drawable.skucuksahin);
                                                                                                                                                                                	isim = "��kr� K���K�AH�N";
                                                                                                                                                                                }else
                                                                                                                                                                                    if (s.contains("Taha AKYOL"))
                                                                                                                                                                                    {
                                                                                                                                                                                    	imv.setImageResource(R.drawable.takyol);
                                                                                                                                                                                    	isim = "Taha AKYOL";
                                                                                                                                                                                    }else
                                                                                                                                                                                        if (s.contains("Tolga TANI�"))
                                                                                                                                                                                        {
                                                                                                                                                                                        	imv.setImageResource(R.drawable.ttanis);
                                                                                                                                                                                        	isim = "Tolga TANI�";
                                                                                                                                                                                        }else
                                                                                                                                                                                            if (s.contains("U�ur CEBEC�"))
                                                                                                                                                                                            {
                                                                                                                                                                                            	imv.setImageResource(R.drawable.ucebeci);
                                                                                                                                                                                            	isim = "U�ur CEBEC�";
                                                                                                                                                                                            }else
                                                                                                                                                                                                if (s.contains("Vahap MUNYAR"))
                                                                                                                                                                                                {
                                                                                                                                                                                                	imv.setImageResource(R.drawable.vmunyar);
                                                                                                                                                                                                	isim = "Vahap MUNYAR";
                                                                                                                                                                                                }else
                                                                                                                                                                                                    if (s.contains("Yal��n BAYER"))
                                                                                                                                                                                                    {
                                                                                                                                                                                                    	imv.setImageResource(R.drawable.ybayer);
                                                                                                                                                                                                    	isim = "Yal��n BAYER";
                                                                                                                                                                                                    }else
                                                                                                                                                                                                        if (s.contains("Yal��n DO�AN"))
                                                                                                                                                                                                        {
                                                                                                                                                                                                        	imv.setImageResource(R.drawable.ydogan);
                                                                                                                                                                                                        	isim = "Yal��n DO�AN";
                                                                                                                                                                                                        }else
                                                                                                                                                                                                            if (s.contains("Y�lmaz �ZD�L"))
                                                                                                                                                                                                            {
                                                                                                                                                                                                            	imv.setImageResource(R.drawable.yozdil);
                                                                                                                                                                                                            	isim = "Y�lmaz �ZD�L";
                                                                                                                                                                                                            }else
                                                                                                                                                                                                                if (s.contains("Yonca TOKBA�"))
                                                                                                                                                                                                                {
                                                                                                                                                                                                                	imv.setImageResource(R.drawable.ytokbas);
                                                                                                                                                                                                                	isim = "Yonca TOKBA�";
                                                                                                                                                                                                                }else
                                                                                                                                                                                                                    if (s.contains("Mesude ER�AN"))
                                                                                                                                                                                                                    {
                                                                                                                                                                                                                    	imv.setImageResource(R.drawable.mersan);
                                                                                                                                                                                                                    	isim = "Mesude ER�AN";
                                                                                                                                                                                                                    }else
                                                                                                                                                                                                                        if (s.contains("Nuran �AKMAK�I"))
                                                                                                                                                                                                                        {
                                                                                                                                                                                                                        	imv.setImageResource(R.drawable.ncakmakci);
                                                                                                                                                                                                                        	isim = "Nuran �AKMAK�I";
                                                                                                                                                                                                                        }else
                                                                                                                                                                                                                            if (s.contains("H�seyin YAYMAN"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.hyayman);
                                                                                                                                                                                                                            	isim = "H�seyin YAYMAN";
                                                                                                                                                                                                                            }else if (s.contains("Cengiz �ANDAR"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.ccandar);
                                                                                                                                                                                                                            	isim = "Cengiz �ANDAR";
                                                                                                                                                                                                                            }else if (s.contains("Civan ER"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.cer);
                                                                                                                                                                                                                            	isim = "Civan ER";
                                                                                                                                                                                                                            }else if (s.contains("�lhan S�YLER"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.isoyler);
                                                                                                                                                                                                                            	isim = "�lhan S�YLER";
                                                                                                                                                                                                                            }else if (s.contains("Sibel Arna"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.sarna);
                                                                                                                                                                                                                            	isim = "Sibel Arna";
                                                                                                                                                                                                                            }else if (s.contains("Banu TUNA"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.btuna);
                                                                                                                                                                                                                            	isim = "Banu TUNA";
                                                                                                                                                                                                                            }else if (s.contains("Melda Narmanl� ��MEN"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.mcimen);
                                                                                                                                                                                                                            	isim = "Melda Narmanl� ��MEN";
                                                                                                                                                                                                                            }else if (s.contains("G�zin  Abla"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.gabla);
                                                                                                                                                                                                                            	isim = "G�zin  Abla";
                                                                                                                                                                                                                            }else if (s.contains("Nil�fer PAZVANTO�LU"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.npazvantoglu);
                                                                                                                                                                                                                            	isim = "Nil�fer PAZVANTO�LU";
                                                                                                                                                                                                                            }else if (s.contains("Prof.Dr. Mikdat KADIO�LU"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.mkadioglu);
                                                                                                                                                                                                                            	isim = "Prof.Dr. Mikdat KADIO�LU";
                                                                                                                                                                                                                            }else if (s.contains("Refika Birg�l"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.rbirgul);
                                                                                                                                                                                                                            	isim = "Refika Birg�l";
                                                                                                                                                                                                                            }else if (s.contains("Melike Karakartal"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.mkarakartal);
                                                                                                                                                                                                                            	isim = "Melike Karakartal";
                                                                                                                                                                                                                            }else if (s.contains("Osman M�FT�O�LU"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.omuftuoglu);
                                                                                                                                                                                                                            	isim = "Osman M�FT�O�LU";
                                                                                                                                                                                                                            }
                                                                                                                                                                                                                            else if (s.contains("Mehmet Ali B�RAND"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.mbirand);
                                                                                                                                                                                                                            	isim = "Mehmet Ali B�RAND";
                                                                                                                                                                                                                            }else if (s.contains("Rauf TAMER"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.rtamer);
                                                                                                                                                                                                                            	isim = "Rauf TAMER";
                                                                                                                                                                                                                            }else if (s.contains("�nal �z�ak"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.uozuak);
                                                                                                                                                                                                                            	isim = "�nal �Z�AK";
                                                                                                                                                                                                                            }else if (s.contains("Mehmet YA��N"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.myasin);
                                                                                                                                                                                                                            	isim = "Mehmet YA��N";
                                                                                                                                                                                                                            }else if (s.contains("Figen BATUR"))
                                                                                                                                                                                                                            {
                                                                                                                                                                                                                            	imv.setImageResource(R.drawable.fbatur);
                                                                                                                                                                                                                            	isim = "Figen BATUR";
                                                                                                                                                                                                                            }else{
                                                                                                                                                                                                                        	 
                                                                                                                                                                                                                             imv.setImageResource(R.drawable.hurriyet);
                                                                                                                                                                                                                        }
                                //H�rriyet Yazarlar Son       
                                title.setText(isim);
                                date.setText("");
                                description.setText(baslik);
                                
                        }

                       
                        return view;
                }
    }
}
