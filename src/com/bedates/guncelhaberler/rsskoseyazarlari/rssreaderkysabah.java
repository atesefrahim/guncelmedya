package com.bedates.guncelhaberler.rsskoseyazarlari;

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

import com.bedates.guncelhaberler.R;
import com.bedates.guncelhaberler.UsingMyWebview;

import com.bedates.guncelhaberler.adaptor.RSSItem;
import com.bedates.guncelhaberler.adaptor.RSSParser;


public class rssreaderkysabah extends ListActivity {
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
                        retrieveRSSFeed("http://sabah.com.tr.feedsportal.com/c/33784/f/606067/index.rss",itemlist);
                       
                        rssadaptor = new RSSListAdaptor(rssreaderkysabah.this, R.layout.rssitemviewkopya,itemlist);
                       
                        return null;
                }
       
                @Override
                protected void onCancelled() {
                        super.onCancelled();
                }
               
                @Override
                protected void onPreExecute() {
                        progress = ProgressDialog.show(
                                        rssreaderkysabah.this, null, "Yükleniyor...");
                       
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
                                LayoutInflater vi = (LayoutInflater)rssreaderkysabah.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                                if (s.contains("YAVUZ DONAT"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/b28c1f2b-3bb8-4e03-9184-953a3d35a4e3.jpg?v=3625");
                                    imv.setImageBitmap(bimage);
                                	isim = "YAVUZ DONAT";
                                }
                                else if (s.contains("NAZLI ILICAK"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/8fd19cfe-ce78-4f75-94f8-7ac8933508ed.jpg?v=92319");
                                    imv.setImageBitmap(bimage);
                                	isim = "NAZLI ILICAK";
                                }
                                else if (s.contains("ENGÝN ARDIÇ"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/6feea466-1c40-4d8d-a4c2-83439c3780fe.jpg?v=7427");
                                    imv.setImageBitmap(bimage);
                                	isim = "ENGÝN ARDIÇ";
                                }
                                else if (s.contains("ERDAL ÞAFAK"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/cd3375ba-cd74-4efc-99b4-3cfa6f8b6c78.jpg?v=73581");
                                    imv.setImageBitmap(bimage);
                                	isim = "ERDAL ÞAFAK";
                                }
                                else if (s.contains("M. ÞÜKRÜ HANÝOÐLU"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/91b55806-6a94-4213-aa02-aa000665269f.jpg?v=75308");
                                    imv.setImageBitmap(bimage);
                                	isim = "M. ÞÜKRÜ HANÝOÐLU";
                                }
                                else if (s.contains("MEHMET BARLAS"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/42ea8db0-a6e0-4c20-8bb8-8b4bb17c6b52.jpg?v=45468");
                                    imv.setImageBitmap(bimage);
                                	isim = "MEHMET BARLAS";
                                }
                                else if (s.contains("HINCAL ULUÇ"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/185d1ef6-f981-41d5-b67a-5911cf716cfe.jpg?v=11677");
                                    imv.setImageBitmap(bimage);
                                	isim = "HINCAL ULUÇ";
                                }
                                else if (s.contains("SEVÝLAY YÜKSELÝR"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/6cd7e367-7c7b-42d7-9f5d-79a36faac819.jpg?v=70191");
                                    imv.setImageBitmap(bimage);
                                	isim = "SEVÝLAY YÜKSELÝR";
                                }
                                else if (s.contains("MAHMUT ÖVÜR"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/76c95920-6a67-47b6-802f-657c172612d3.jpg?v=69456");
                                    imv.setImageBitmap(bimage);
                                	isim = "MAHMUT ÖVÜR";
                                }
                                else if (s.contains("ÞELALE KADAK"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/62799708-5f0d-4313-83f4-f3de1b10aea5.jpg?v=59214");
                                    imv.setImageBitmap(bimage);
                                	isim = "ÞELALE KADAK";
                                }
                                else if (s.contains("HASAN CELAL GÜZEL"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/7e450c26-ee15-4620-9cdd-46a89a692b49.jpg?v=80757");
                                    imv.setImageBitmap(bimage);
                                	isim = "HASAN CELAL GÜZEL";
                                }
                                else if (s.contains("EMRE AKÖZ"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/ca7d4b48-dcc5-4a1e-8994-e7134a9682b1.jpg?v=81781");
                                    imv.setImageBitmap(bimage);
                                	isim = "EMRE AKÖZ";
                                }
                                else if (s.contains("HAÞMET BABAOÐLU"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/c4df96a6-54c5-4770-80d0-7e78a0c5cdee.jpg?v=36890");
                                    imv.setImageBitmap(bimage);
                                	isim = "HAÞMET BABAOÐLU";
                                }
                                else if (s.contains("AYÞE ÖZYILMAZEL"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/8496fcea-9339-4faa-8f60-c3671177b503.jpg?v=5777");
                                    imv.setImageBitmap(bimage);
                                	isim = "AYÞE ÖZYILMAZEL";
                                }
                                else if (s.contains("TÝMUR SIRT"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/ya/be6a4647-d31c-4f86-956e-f861da9555bb.jpg?v=82712");
                                    imv.setImageBitmap(bimage);
                                	isim = "TÝMUR SIRT";
                                }
                                else if (s.contains("SÝNAN ÖZEDÝNCÝK"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/ya/1e17aaea-d15e-478c-8a1b-f9c641e8e87e.jpg?v=60241");
                                    imv.setImageBitmap(bimage);
                                	isim = "SÝNAN ÖZEDÝNCÝK";
                                }
                                else if (s.contains("TARIK YILMAZ"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/ya/fb88cdb1-8729-412d-bc6e-f6328e6e8298.jpg?v=97909");
                                    imv.setImageBitmap(bimage);
                                	isim = "TARIK YILMAZ";
                                }
                                else if (s.contains("TULU GÜMÜÞTEKÝN"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/44ed0b5f-d229-4ba3-a2d1-b7ef966e2eed.jpg?v=99822");
                                    imv.setImageBitmap(bimage);
                                	isim = "TULU GÜMÜÞTEKÝN";
                                }
                                else if (s.contains("OKAN MÜDERRÝSOÐLU"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/79a39858-5150-43e2-98f5-b2e4a9306513.jpg?v=82365");
                                    imv.setImageBitmap(bimage);
                                	isim = "OKAN MÜDERRÝSOÐLU";
                                }
                                else if (s.contains("ÞEREF OÐUZ"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/a90931ef-7dff-4dc3-922c-5883a7f43903.jpg?v=13786");
                                    imv.setImageBitmap(bimage);
                                	isim = "ÞEREF OÐUZ";
                                }
                                else if (s.contains("SAVAÞ AY"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/ya/0e110319-9d8c-4204-bb8a-3fb9d160a46c.jpg?v=89560");
                                    imv.setImageBitmap(bimage);
                                	isim = "SAVAÞ AY";
                                }
                                else if (s.contains("STELYO BERBERAKÝS"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/ya/c099d5bd-304d-47d9-a632-d5dc9906317b.jpg?v=115916");
                                    imv.setImageBitmap(bimage);
                                	isim = "STELYO BERBERAKÝS";
                                }
                                else if (s.contains("MELÝHA OKUR"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/4a23f29d-9876-4b75-8964-a2d578574744.jpg?v=98172");
                                    imv.setImageBitmap(bimage);
                                	isim = "MELÝHA OKUR";
                                }
                                else if (s.contains("SÜLEYMAN YAÞAR"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/7695d0cb-a5f8-4738-9fa1-b9a8894e1e9e.jpg?v=51735");
                                    imv.setImageBitmap(bimage);
                                	isim = "SÜLEYMAN YAÞAR";
                                }
                                else if (s.contains("ÖMER TAÞPINAR"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/8b8d385c-2a09-497c-be41-a1fca5b1cc3b.jpg?v=98448");
                                    imv.setImageBitmap(bimage);
                                	isim = "ÖMER TAÞPINAR";
                                }
                                else if (s.contains("HASAN BÜLENT KAHRAMAN"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/6d49ae08-f011-46b7-b6b3-06d7b795c7ad.jpg?v=59613");
                                    imv.setImageBitmap(bimage);
                                	isim = "HASAN BÜLENT KAHRAMAN";
                                }
                                else if (s.contains("REFÝK ERDURAN"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.sabah.com.tr/sbh/y/f0cef03a-2f04-488b-b78a-05901cb0fe13.jpg");
                                    imv.setImageBitmap(bimage);
                                	isim = "REFÝK ERDURAN";
                                }
                                                                
                                else
                                {
                                	imv.setImageResource(R.drawable.sabah);
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
