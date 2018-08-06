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


public class rssreaderkymilliyet extends ListActivity {
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
                        retrieveRSSFeed("http://www.milliyet.com.tr/D/rss/rss/RssY.xml",itemlist);
                       
                        rssadaptor = new RSSListAdaptor(rssreaderkymilliyet.this, R.layout.rssitemviewkopya,itemlist);
                       
                        return null;
                }
       
                @Override
                protected void onCancelled() {
                        super.onCancelled();
                }
               
                @Override
                protected void onPreExecute() {
                        progress = ProgressDialog.show(
                                        rssreaderkymilliyet.this, null, "Y�kleniyor...");
                       
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
                                LayoutInflater vi = (LayoutInflater)rssreaderkymilliyet.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                view = vi.inflate(R.layout.rssitemviewkopya, null);
                        }
                       
                        RSSItem data = objects.get(position);

                        if(null != data)
                        {
                                TextView title = (TextView)view.findViewById(R.id.txtTitle);
                                TextView date = (TextView)view.findViewById(R.id.txtDate);
                                TextView description = (TextView)view.findViewById(R.id.txtDescription);
                               
                               

                                ImageView imv = (ImageView)view.findViewById(R.id.yazarlogo);
                                
                                String isim = data.description;
                                String baslik = data.title;

                                
        /*                        int i; 
                                do { 
                                i = isim.indexOf(search); 
                                int a = i;
                                if(i != -1) { 
                                	
                                
                              
                                int y = isim.length();
                                resultisim = isim.substring(a+2 ,y);
                                isim = resultisim;
                                } 
                                } while(i != -1);*/ 
                                
        
                              String s = isim;
//H�rriyet Yazarlar Ba�lang��
                                if (s.contains("- Melih A��k"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf1992.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Melih A��k";
                                }
                                else if (s.contains("Hasan Pulur"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf1297.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Hasan Pulur";
                                }
                                else if (s.contains("G�ng�r Uras"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf1298.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "G�ng�r Uras";
                                }
                                else if (s.contains("�etin Altan"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf1296.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "�etin Altan";
                                }
                                else if (s.contains("Metin M�nir"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf2152.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Metin M�nir ";
                                }
                                else if (s.contains("Dilara Ko�ak"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/CaddeYazar/2009/11/14/fft84_mf49.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Dilara Ko�ak";
                                }
                                else if (s.contains("Ali Ey�bo�lu"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/CaddeYazar/2010/04/06/fft84_mf290.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Ali Ey�bo�lu";
                                }
                                else if (s.contains("Menderes �zel "))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/CaddeYazar/2010/11/22/fft84_mf2375.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Menderes �zel ";
                                }
                                else if (s.contains("Hasan Cemal"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/GazeteHaberIciResim/2010/06/27/fft16_mf714026.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Hasan Cemal";
                                }
                                else if (s.contains("G�neri C�vao�lu"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf1316.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "G�neri C�vao�lu";
                                }
                                else if (s.contains("Semih �diz"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf2153.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Semih �diz";
                                }
                                else if (s.contains("Fikret Bila"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf1302.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Fikret Bila";
                                }
                                else if (s.contains("Abbas G��l�"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf143861.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Abbas G��l�";
                                }
                                else if (s.contains("�a�da� Ertuna"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/CaddeYazar/2009/11/14/fft84_mf66.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "�a�da� Ertuna";
                                }
                                else if (s.contains("Sina Kolo�lu"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/CaddeYazar/2009/11/14/fft84_mf54.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Sina Kolo�lu";
                                }
                                else if (s.contains("Hakan K�rko�lu"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/CaddeYazar/2009/11/14/fft84_mf53.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Hakan K�rko�lu";
                                }
                                else if (s.contains("Cadde'deki Hayalet "))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/CaddeYazar/2010/10/24/fft84_mf2139.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Cadde'deki Hayalet ";
                                }
                                else if (s.contains("Harun Uysal"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf1013365.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Harun Uysal";
                                }
                                else if (s.contains("Muhittin Akbel"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf1336039.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Muhittin Akbel";
                                }
                                else if (s.contains("Hamdi T�rkmen"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf435410.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Hamdi T�rkmen";
                                }
                                else if (s.contains("Feyzi Hep�enkal"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf178792.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Feyzi Hep�enkal";
                                }
                                else if (s.contains("Erdal �zgi"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf1235276.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Erdal �zgi";
                                }
                                else if (s.contains("Erdin� Yumrukaya"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf2306453.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Erdin� Yumrukaya";
                                }
                                else if (s.contains("Nail G�reli"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf2171216.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Nail G�reli";
                                }
                                else if (s.contains("Mehve� Evin"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf1094256.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Mehve� Evin";
                                }
                                else if (s.contains("Mehmet Tezkan"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf414215.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Mehmet Tezkan";
                                }
                                else if (s.contains("Atilla G�k�e"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf428892.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Atilla G�k�e";
                                }
                                else if (s.contains("Sel�uk Dereli"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf2633194.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Sel�uk Dereli";
                                }
                                else if (s.contains("Af��n Yakubo�lu"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf2546843.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Af��n Yakubo�lu";
                                }
                                else if (s.contains("Bilal Me�e"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf2507011.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Bilal Me�e";
                                }
                                else if (s.contains("Uzay G�kerman"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft164_mf2001774.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Uzay G�kerman";
                                }
                                else if (s.contains("Sami Kohen"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf1301.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Sami Kohen";
                                }
                                else if (s.contains("Meral Tamer"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf1528872.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Meral Tamer";
                                }
                                else if (s.contains("Can D�ndar"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf759622.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Can D�ndar";
                                }
                                else if (s.contains("Yaman T�r�ner"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf2151.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Yaman T�r�ner";
                                }
                                else if (s.contains("Dr. Hasan �nsel"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/CaddeYazar/2009/11/14/fft84_mf56.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Dr. Hasan �nsel";
                                }
                                else if (s.contains("Asu Maro"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/CaddeYazar/2010/05/21/fft84_mf834.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Asu Maro";
                                }
                                else if (s.contains("Ali Tufan Ko�"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/CaddeYazar/2011/01/22/fft84_mf69034.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Ali Tufan Ko�";
                                }
                                else if (s.contains("Sina Kolo�lu"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/CaddeYazar/2009/11/14/fft84_mf54.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Sina Kolo�lu";
                                }
                                else if (s.contains("Hakan K�rko�lu"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/CaddeYazar/2009/11/14/fft84_mf53.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Hakan K�rko�lu";
                                }
                                else if (s.contains("Ferhan �stanbullu"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/CaddeYazar/2011/11/28/fft84_mf186689.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Ferhan �stanbullu";
                                }
                                else if (s.contains("Asl� Ayd�nta�ba�"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf414077.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Asl� Ayd�nta�ba�";
                                }
                                else if (s.contains("Kemal �ndero�lu"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf587383.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Kemal �ndero�lu";
                                }
                                else if (s.contains("Mustafa Y�lmaz --"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf1958267.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Mustafa Y�lmaz --";
                                }
                                else if (s.contains("Mustafa An�kl�"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf1727932.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Mustafa An�kl�";
                                }
                                else if (s.contains("Orhan Uluca"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf2555998.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Orhan Uluca";
                                }
                                else if (s.contains("Murat Fevzi Tan�rl�"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf2556021.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Murat Fevzi Tan�rl�";
                                }
                                else if (s.contains("Kadir Onur Din�er"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf2556015.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Kadir Onur Din�er";
                                }
                                else if (s.contains("Erdem Ko�"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf2556010.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Erdem Ko�";
                                }
                                else if (s.contains("Ula� G�r�at"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf2556012.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Ula� G�r�at";
                                }
                                else if (s.contains("Kozmetisyen Nesrin S�rer"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft164_mf2618710.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Kozmetisyen Nesrin S�rer";
                                }
                                else if (s.contains("G�lg�n Karao�lu"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf804719.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Olcay G�lg�n Karao�lu";
                                }
                                else if (s.contains("Serhat Ayan"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf1010489.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Serhat Ayan";
                                }
                                else if (s.contains("G��l� Berk"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft164_mf2580482.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "G��l� Berk";
                                }
                                else if (s.contains("Nefes ve Ya�am Ko�u Sibel Kavuno�lu"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft164_mf1774787.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Nefes ve Ya�am Ko�u Sibel Kavuno�lu";
                                }
                                else if (s.contains("R.Hakan K�rko�lu"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/CaddeYazar/2011/08/22/fft84_mf170478.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "R.Hakan K�rko�lu";
                                }
                                else if (s.contains("U�ur Meleke"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf2451245.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "U�ur Meleke";
                                }
                                else if (s.contains("Prof. Dr. E. Murat Tuzcu"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf455664.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Prof. Dr. E. Murat Tuzcu";
                                }
                                else if (s.contains("Pelin �ini"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/CaddeYazar/2011/08/01/fft84_mf170033.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Pelin �ini";
                                }
                                else if (s.contains("Cadde'nin Patisi"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/CaddeYazar/2011/07/03/fft84_mf74139.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Cadde'nin Patisi";
                                }
                                else if (s.contains("Derya Sazak"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf1320.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Derya Sazak";
                                }
                                else if (s.contains("Ercan G�ven"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf3603.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Ercan G�ven";
                                }
                                else if (s.contains("Turgut �elik"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft164_mf2335414.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Turgut �elik";
                                }
                                else if (s.contains("Anibal G�lero�lu"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft164_mf2003619.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Anibal G�lero�lu";
                                }
                                else if (s.contains("Serpil �evikcan"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf1709162.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Serpil �evikcan";
                                }
                                else if (s.contains("Mr. Son Dakika"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf2547830.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Mr. Son Dakika";
                                }
                                else if (s.contains("O�uz Akdeniz"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft164_mf2063125.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "O�uz Akdeniz";
                                }
                                else if (s.contains("Mehmet Ali Birand"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf2002.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Mehmet Ali Birand";
                                }
                                else if (s.contains("Kadri G�rsel"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf2386.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Kadri G�rsel";
                                }
                                else if (s.contains("Fato� Karahasan"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://i.milliyet.com.tr/YazarResimleri/fft6_mf456193.Jpeg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Fato� Karahasan";
                                }
                                else if (s.contains("Meltem Maz�c�"))
                                {
                                    
                                	isim = "Meltem Maz�c�";
                                }
                                else if (s.contains("Arif �ahin"))
                                {
                                    
                                	isim = "Arif �ahin";
                                }
                                else if (s.contains("Tolga Aky�ld�z"))
                                {
                                    
                                	isim = "Tolga Aky�ld�z";
                                }
                                else if (s.contains("Hilmi G�ltay"))
                                {
                                    
                                	isim = "Hilmi G�ltay";
                                }
                                else {
                                                                                                                                                                                                                        	 
                                      imv.setImageResource(R.drawable.milliyet);
                                      }
                                //H�rriyet Yazarlar Son       
                                title.setText(isim);
                                date.setText("on " + data.date);
                                description.setText(baslik);
                                
                        }

                       
                        return view;
                }
    }
}
