package com.bedates.guncelhaberler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bedates.guncelhaberler.adaptor.RSSItem;
import com.bedates.guncelhaberler.adaptor.RSSParser;

public class rssreaderkoseyazarlari extends ListActivity {
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
                        retrieveRSSFeed("http://rss.hurriyet.com.tr/rss.aspx?sectionId=9",itemlist);
                       
                        rssadaptor = new RSSListAdaptor(rssreaderkoseyazarlari.this, R.layout.rssitemviewkopya,itemlist);
                       
                        return null;
                }
       
                @Override
                protected void onCancelled() {
                        super.onCancelled();
                }
               
                @Override
                protected void onPreExecute() {
                        progress = ProgressDialog.show(
                                        rssreaderkoseyazarlari.this, null, "Loading RSS Feeds...");
                       
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


                 public Bitmap getBitmapFromURL(String src) {
                        try {
                            Log.e("src",src);
                            URL url = new URL(src);
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setDoInput(true);
                            connection.connect();
                            InputStream input = connection.getInputStream();
                            Bitmap myBitmap = BitmapFactory.decodeStream(input);
                            Log.e("Bitmap","returned");
                            return myBitmap;
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e("Exception",e.getMessage());
                            return null;
                        }
                    }

                public View getView(int position, View convertView, ViewGroup parent) {
                        View view = convertView;
                       
                        if(null == view)
                        {
                                LayoutInflater vi = (LayoutInflater)rssreaderkoseyazarlari.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                                    Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/131b.jpg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Ahmet HAKAN";
                                }
                                else
                                    if (s.contains("Altan Tanrýkulu"))
                                    {
/*                                    	imv.setImageResource(R.drawable.altantanrikulu);*/
                                    
                                        Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/162b.jpg");
                                        imv.setImageBitmap(bimage);	
                                        isim = "Altan Tanrýkulu";
                                    } else
                                        if (s.contains("Ayþe ARAL"))
                                        {
                                            Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/344b.jpg");
                                            imv.setImageBitmap(bimage);
                                        	isim = "Ayþe ARAL";
                                        } else
                                            if (s.contains("Ayþe ARMAN"))
                                            {
                                                Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/12b.jpg");
                                                imv.setImageBitmap(bimage);
                                            	isim = "Ayþe ARMAN";
                                            } else
                                                    if (s.contains("Cengiz SEMERCÝOÐLU"))
                                                    {
                                                        Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/105b.jpg");
                                                        imv.setImageBitmap(bimage);
                                                    	isim = "Cengiz SEMERCÝOÐLU";
                                                    } else
                                                        if (s.contains("Doðan HIZLAN"))
                                                        {
                                                            Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/4b.jpg");
                                                            imv.setImageBitmap(bimage);
                                                        	isim = "Doðan HIZLAN";
                                                        } else
                                                            if (s.contains("Ege CANSEN"))
                                                            {
                                                                Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/260b.jpg");
                                                                imv.setImageBitmap(bimage);
                                                            	isim = "Ege CANSEN";
                                                            } else
                                                                if (s.contains("Bilgin GÖKBERK"))
                                                                {
                                                                    Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/420b.jpg");
                                                                    imv.setImageBitmap(bimage);
                                                                	isim = "Bilgin GÖKBERK";
                                                                } else
                                                                    if (s.contains("Ercan SAATÇÝ"))
                                                                    {
                                                                        Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/84b.jpg");
                                                                        imv.setImageBitmap(bimage);
                                                                    	isim = "Ercan SAATÇÝ";
                                                                    }else
                                                                        if (s.contains("Erdal SAÐLAM"))
                                                                        {
                                                                            Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/8b.jpg");
                                                                            imv.setImageBitmap(bimage);
                                                                        	isim = "Erdal SAÐLAM";
                                                                        }else
                                                                            if (s.contains("Erkan ÇELEBÝ"))
                                                                            {
                                                                                Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/18b.jpg");
                                                                                imv.setImageBitmap(bimage);
                                                                            	isim = "Erkan ÇELEBÝ";
                                                                            }else
                                                                                if (s.contains("Erman TOROÐLU"))
                                                                                {
                                                                                    Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/83b.jpg");
                                                                                    imv.setImageBitmap(bimage);
                                                                                	isim = "Erman TOROÐLU";
                                                                                }else
                                                                                    if (s.contains("Ertuðrul ÖZKÖK"))
                                                                                    {
                                                                                        Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/10b.jpg");
                                                                                        imv.setImageBitmap(bimage);
                                                                                    	isim = "Ertuðrul ÖZKÖK";
                                                                                    }else
                                                                                        if (s.contains("Fatih ÇEKÝRGE"))
                                                                                        {
                                                                                            Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/174b.jpg");
                                                                                            imv.setImageBitmap(bimage);
                                                                                        	isim = "Fatih ÇEKÝRGE";
                                                                                        }else
                                                                                            if (s.contains("Gila BENMAYOR"))
                                                                                            {
                                                                                                Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/20b.jpg");
                                                                                                imv.setImageBitmap(bimage);
                                                                                            	isim = "Gila BENMAYOR";
                                                                                            }else
                                                                                                if (s.contains("Gökhan KÝMSESÝZCAN"))
                                                                                                {
                                                                                                    Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/387b.jpg");
                                                                                                    imv.setImageBitmap(bimage);
                                                                                                	isim = "Gökhan KÝMSESÝZCAN";
                                                                                                }else
                                                                                                    if (s.contains("Güzin Abla"))
                                                                                                    {
                                                                                                        Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/50b.jpg");
                                                                                                        imv.setImageBitmap(bimage);
                                                                                                    	isim = "Güzin Abla";
                                                                                                    }else
                                                                                                        if (s.contains("Ýsmet BERKAN"))
                                                                                                        {
                                                                                                            Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/386b.jpg");
                                                                                                            imv.setImageBitmap(bimage);
                                                                                                        	isim = "Ýsmet BERKAN";
                                                                                                        }else
                                                                                                            if (s.contains("Kanat ATKAYA"))
                                                                                                            {
                                                                                                                Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/25b.jpg");
                                                                                                                imv.setImageBitmap(bimage);
                                                                                                            	isim = "Kanat ATKAYA";
                                                                                                            }else
                                                                                                                if (s.contains("Latif DEMÝRCÝ"))
                                                                                                                {
                                                                                                                    Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/48b.jpg");
                                                                                                                    imv.setImageBitmap(bimage);
                                                                                                                	isim = "Latif DEMÝRCÝ";
                                                                                                                }else
                                                                                                                    if (s.contains("Mehmet ARSLAN"))
                                                                                                                    {
                                                                                                                        Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/173b.jpg");
                                                                                                                        imv.setImageBitmap(bimage);
                                                                                                                    	isim = "Mehmet ARSLAN";
                                                                                                                    }else
                                                                                                                        if (s.contains("Mehmet Y. YILMAZ"))
                                                                                                                        {
                                                                                                                            Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/148b.jpg");
                                                                                                                            imv.setImageBitmap(bimage);
                                                                                                                        	isim = "Mehmet Y. YILMAZ";
                                                                                                                        }else
                                                                                                                            if (s.contains("Melis ALPHAN"))
                                                                                                                            {
                                                                                                                                Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/350b.jpg");
                                                                                                                                imv.setImageBitmap(bimage);
                                                                                                                            	isim = "Melis ALPHAN";
                                                                                                                            }else
                                                                                                                                if (s.contains("Metehan Demir"))
                                                                                                                                {
                                                                                                                                    Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/80b.jpg");
                                                                                                                                    imv.setImageBitmap(bimage);
                                                                                                                                	isim = "Metehan Demir";
                                                                                                                                }else
                                                                                                                                    if (s.contains("Nil KARAÝBRAHÝMGÝL"))
                                                                                                                                    {
                                                                                                                                        Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/113b.jpg");
                                                                                                                                        imv.setImageBitmap(bimage);
                                                                                                                                    	isim = "Nil KARAÝBRAHÝMGÝL";
                                                                                                                                    }else
                                                                                                                                        if (s.contains("Niobe"))
                                                                                                                                        {
                                                                                                                                            Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/141b.jpg");
                                                                                                                                            imv.setImageBitmap(bimage);
                                                                                                                                        	isim = "Niobe";
                                                                                                                                        }else
                                                                                                                                            if (s.contains("Noyan Doðan"))
                                                                                                                                            {
                                                                                                                                                Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/257b.jpg");
                                                                                                                                                imv.setImageBitmap(bimage);
                                                                                                                                            	isim = "Noyan Doðan";
                                                                                                                                            }else
                                                                                                                                                if (s.contains("Onur BAÞTÜRK"))
                                                                                                                                                {
                                                                                                                                                    Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/149b.jpg");
                                                                                                                                                    imv.setImageBitmap(bimage);
                                                                                                                                                	isim = "Onur BAÞTÜRK";
                                                                                                                                                }else
                                                                                                                                                    if (s.contains("Prof. Dr. Osman MÜFTÜOÐLU"))
                                                                                                                                                    {
                                                                                                                                                        Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/95b.jpg");
                                                                                                                                                        imv.setImageBitmap(bimage);
                                                                                                                                                    	isim = "Prof. Dr. Osman MÜFTÜOÐLU";
                                                                                                                                                    }else
                                                                                                                                                        if (s.contains("Oya ARMUTÇU"))
                                                                                                                                                        {
                                                                                                                                                            Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/210b.jpg");
                                                                                                                                                            imv.setImageBitmap(bimage);
                                                                                                                                                        	isim = "Oya ARMUTÇU";
                                                                                                                                                        }else
                                                                                                                                                            if (s.contains("Ömür GEDÝK"))
                                                                                                                                                            {
                                                                                                                                                                Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/119b.jpg");
                                                                                                                                                                imv.setImageBitmap(bimage);
                                                                                                                                                            	isim = "Ömür GEDÝK";
                                                                                                                                                            }else
                                                                                                                                                                if (s.contains("Özgür BOLAT"))
                                                                                                                                                                {
                                                                                                                                                                    Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/313b.jpg");
                                                                                                                                                                    imv.setImageBitmap(bimage);
                                                                                                                                                                	isim = "Özgür BOLAT";
                                                                                                                                                                }else
                                                                                                                                                                    if (s.contains("Sahrap SOYSAL"))
                                                                                                                                                                    {
                                                                                                                                                                        Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/106b.jpg");
                                                                                                                                                                        imv.setImageBitmap(bimage);
                                                                                                                                                                    	isim = "Sahrap SOYSAL";
                                                                                                                                                                    }else
                                                                                                                                                                        if (s.contains("Sedat Ergin"))
                                                                                                                                                                        {
                                                                                                                                                                            Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/308b.jpg");
                                                                                                                                                                            imv.setImageBitmap(bimage);
                                                                                                                                                                        	isim = "Sedat Ergin";
                                                                                                                                                                        }else
                                                                                                                                                                            if (s.contains("Þükrü KIZILOT"))
                                                                                                                                                                            {
                                                                                                                                                                                Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/82b.jpg");
                                                                                                                                                                                imv.setImageBitmap(bimage);
                                                                                                                                                                            	isim = "Þükrü KIZILOT";
                                                                                                                                                                            }else
                                                                                                                                                                                if (s.contains("Þükrü KÜÇÜKÞAHÝN"))
                                                                                                                                                                                {
                                                                                                                                                                                    Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/92b.jpg");
                                                                                                                                                                                    imv.setImageBitmap(bimage);
                                                                                                                                                                                	isim = "Þükrü KÜÇÜKÞAHÝN";
                                                                                                                                                                                }else
                                                                                                                                                                                    if (s.contains("Taha AKYOL"))
                                                                                                                                                                                    {
                                                                                                                                                                                        Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/329b.jpg");
                                                                                                                                                                                        imv.setImageBitmap(bimage);
                                                                                                                                                                                    	isim = "Taha AKYOL";
                                                                                                                                                                                    }else
                                                                                                                                                                                        if (s.contains("Tolga TANIÞ"))
                                                                                                                                                                                        {
                                                                                                                                                                                            Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/322b.jpg");
                                                                                                                                                                                            imv.setImageBitmap(bimage);
                                                                                                                                                                                        	isim = "Tolga TANIÞ";
                                                                                                                                                                                        }else
                                                                                                                                                                                            if (s.contains("Uður CEBECÝ"))
                                                                                                                                                                                            {
                                                                                                                                                                                                Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/41b.jpg");
                                                                                                                                                                                                imv.setImageBitmap(bimage);
                                                                                                                                                                                            	isim = "Uður CEBECÝ";
                                                                                                                                                                                            }else
                                                                                                                                                                                                if (s.contains("Vahap MUNYAR"))
                                                                                                                                                                                                {
                                                                                                                                                                                                    Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/44b.jpg");
                                                                                                                                                                                                    imv.setImageBitmap(bimage);
                                                                                                                                                                                                	isim = "Vahap MUNYAR";
                                                                                                                                                                                                }else
                                                                                                                                                                                                    if (s.contains("Yalçýn BAYER"))
                                                                                                                                                                                                    {
                                                                                                                                                                                                        Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/42b.jpg");
                                                                                                                                                                                                        imv.setImageBitmap(bimage);
                                                                                                                                                                                                    	isim = "Yalçýn BAYER";
                                                                                                                                                                                                    }else
                                                                                                                                                                                                        if (s.contains("Yalçýn DOÐAN"))
                                                                                                                                                                                                        {
                                                                                                                                                                                                            Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/91b.jpg");
                                                                                                                                                                                                            imv.setImageBitmap(bimage);
                                                                                                                                                                                                        	isim = "Yalçýn DOÐAN";
                                                                                                                                                                                                        }else
                                                                                                                                                                                                            if (s.contains("Yýlmaz ÖZDÝL"))
                                                                                                                                                                                                            {
                                                                                                                                                                                                                Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/249b.jpg");
                                                                                                                                                                                                                imv.setImageBitmap(bimage);
                                                                                                                                                                                                            	isim = "Yýlmaz ÖZDÝL";
                                                                                                                                                                                                            }else
                                                                                                                                                                                                                if (s.contains("Yonca TOKBAÞ"))
                                                                                                                                                                                                                {
                                                                                                                                                                                                                    Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/232b.jpg");
                                                                                                                                                                                                                    imv.setImageBitmap(bimage);
                                                                                                                                                                                                                	isim = "Yonca TOKBAÞ";
                                                                                                                                                                                                                }else
                                                                                                                                                                                                                    if (s.contains("Mesude ERÞAN"))
                                                                                                                                                                                                                    {
                                                                                                                                                                                                                        Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/110b.jpg");
                                                                                                                                                                                                                        imv.setImageBitmap(bimage);
                                                                                                                                                                                                                    	isim = "Mesude ERÞAN";
                                                                                                                                                                                                                    }else
                                                                                                                                                                                                                        if (s.contains("Nuran ÇAKMAKÇI"))
                                                                                                                                                                                                                        {
                                                                                                                                                                                                                            Bitmap bimage=  getBitmapFromURL("http://www.hurriyet.com.tr/_yazarlar/images/372b.jpg");
                                                                                                                                                                                                                            imv.setImageBitmap(bimage);
                                                                                                                                                                                                                        	isim = "Nuran ÇAKMAKÇI";
                                                                                                                                                                                                                        }else {
                                                                                                                                                                                                                        	 
                                                                                                                                                                                                                             imv.setImageResource(R.drawable.hurriyet);
                                                                                                                                                                                                                        }
                                //Hürriyet Yazarlar Son       
                                title.setText(isim);
                                date.setText("on " + data.date);
                                description.setText(baslik);
                                
/*                                String sentence= data.description;
                                	String delims="-";
                                	StringTokenizer tokenizer=new StringTokenizer(sentence,delims);
                                	if(tokenizer.hasMoreTokens()){
                                		String token=tokenizer.nextToken();
                                		data.description = token;
                                	}*/
/*String search = "-";
                                
                                int a = data.description.indexOf(search);
                               
                                String roar = data.description.substring(0, '-'); 
                                data.description = roar;*/
/*                                String search = "-"; 
                                String result = ""; 
                                String temp = "";
                                
                                int i; 
                                do { // replace all matching substrings 
                                	temp = data.description;
                                i = temp.indexOf(search); 
                                int a = i;
                                if(i != -1) { 
                                	
                                result = data.description.substring(0, a);
                                 
                                data.description = result; 
                                
                                } 
                                } while(i != -1); 
                                data.description = temp;*/
                               

                                
                        }

                       
                        return view;
                }
    }
}
