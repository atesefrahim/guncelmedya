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

public class rssreaderkyzaman extends ListActivity {
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
           is.setEncoding("ISO-8859-1");
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
                        retrieveRSSFeed("http://www.zaman.com.tr/yazarlar.rss",itemlist);
                       
                        rssadaptor = new RSSListAdaptor(rssreaderkyzaman.this, R.layout.rssitemviewkopya,itemlist);
                       
                        return null;
                }
       
                @Override
                protected void onCancelled() {
                        super.onCancelled();
                }
               
                @Override
                protected void onPreExecute() {
                        progress = ProgressDialog.show(
                                        rssreaderkyzaman.this, null, "Yükleniyor...");
                       
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
                                LayoutInflater vi = (LayoutInflater)rssreaderkyzaman.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                                isim = isim.replaceAll("ý", "ı");
                                isim = isim.replaceAll("þ", "ş");
                                isim = isim.replaceAll("ð", "ğ");
                                isim = isim.replaceAll("Ý", "İ");
                                isim = isim.replaceAll("Þ", "Ş");
                                isim = isim.replaceAll("Ð", "Ğ");
        
                                baslik = baslik.replaceAll("ý", "ı");
                                baslik = baslik.replaceAll("þ", "ş");
                                baslik = baslik.replaceAll("ð", "ğ");
                                baslik = baslik.replaceAll("Ý", "İ");
                                baslik = baslik.replaceAll("Þ", "Ş");
                                baslik = baslik.replaceAll("Ð", "Ğ");
                                
                              String s = isim;
                                if (s.contains("Ekrem Dumanlı"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://medya.zaman.com.tr/zamantryeni/pics/yazarlar-detay-yeni/ekremdumanli.jpg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Ekrem Dumanlı";
                                }
                                else if (s.contains("Hilmi Yavuz"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://medya.zaman.com.tr/zamantryeni/pics/yazarlar-detay-yeni/hilmiyavuz.jpg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Hilmi Yavuz";
                                }
                                else if (s.contains("Mustafa Ünal"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://medya.zaman.com.tr/zamantryeni/pics/yazarlar-detay-yeni/mustafaunal.jpg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Mustafa Ünal";
                                }
                                else if (s.contains("A. Turan Alkan"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://medya.zaman.com.tr/zamantryeni/pics/yazarlar-detay-yeni/aturanalkan.jpg");
                                    imv.setImageBitmap(bimage);
                                	isim = "A. Turan Alkan";
                                }
                                else if (s.contains("M.Nedim Hazar"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://medya.zaman.com.tr/zamantryeni/pics/yazarlar-detay-yeni/m.nedimhazar.jpg");
                                    imv.setImageBitmap(bimage);
                                	isim = "M.Nedim Hazar";
                                }
                                else if (s.contains("Hüseyin Gülerce"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://medya.zaman.com.tr/zamantryeni/pics/yazarlar-detay-yeni/huseyingulerce.jpg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Hüseyin Gülerce";
                                }
                                else if (s.contains("Etyen Mahçupyan"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://medya.zaman.com.tr/zamantryeni/pics/yazarlar-detay-yeni/etyenmahcupyan.jpg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Etyen Mahçupyan";
                                }
                                else if (s.contains("Ahmed Şahin"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://medya.zaman.com.tr/zamantryeni/pics/yazarlar-detay-yeni/ahmedsahin.jpg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Ahmed Şahin";
                                }
                                else if (s.contains("Saruhan Özel"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://medya.zaman.com.tr/zamantryeni/pics/yazarlar-detay-yeni/saruhanozel.jpg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Saruhan Özel";
                                }
                                else if (s.contains("Joost Lagendijk"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://medya.zaman.com.tr/zamantryeni/pics/yazarlar-detay-yeni/joostlagendijk.jpg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Joost Lagendijk";
                                }
                                else if (s.contains("Okay Karacan"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://medya.zaman.com.tr/zamantryeni/pics/yazarlar-detay-yeni/okaykaracan.jpg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Okay Karacan";
                                }
                                else if (s.contains("Mehmet Kamış"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://medya.zaman.com.tr/zamantryeni/pics/yazarlar-detay-yeni/mehmetkamis.jpg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Mehmet Kamış";
                                }
                                else if (s.contains("Ali Aydın"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://medya.zaman.com.tr/zamantryeni/pics/yazarlar-detay-yeni/aliaydin.jpg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Ali Aydın";
                                }
                                else if (s.contains("Ahmet Çakır"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://medya.zaman.com.tr/zamantryeni/pics/yazarlar-detay-yeni/ahmetcakir.jpg");
                                    imv.setImageBitmap(bimage);
                                	isim = "Ahmet Çakır";
                                }
                                else if (s.contains("Abdülhamit Bilici"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://cdncms.zaman.com.tr/columnists/ipad_yazarlar/abdulhamitbilici.png");
                                    imv.setImageBitmap(bimage);
                                	isim = "Abdülhamit Bilici";
                                }
                                else if (s.contains("Leyla İpekçi"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://cdncms.zaman.com.tr/columnists/ipad_yazarlar/leylaipekci.png");
                                    imv.setImageBitmap(bimage);
                                	isim = "Leyla İpekçi";
                                }
                                else if (s.contains("Ahmet  Yavuz"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://cdncms.zaman.com.tr/columnists/ipad_yazarlar/ahmetyavuz.png");
                                    imv.setImageBitmap(bimage);
                                	isim = "Ahmet  Yavuz";
                                }
                                else if (s.contains("Mümtaz'er Türköne"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://cdncms.zaman.com.tr/columnists/ipad_yazarlar/mumtazerturkone.png");
                                    imv.setImageBitmap(bimage);
                                	isim = "Mümtaz'er Türköne";
                                }
                                else if (s.contains("İhsan Dağı"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://cdncms.zaman.com.tr/columnists/ipad_yazarlar/ihsandagi.png");
                                    imv.setImageBitmap(bimage);
                                	isim = "İhsan Dağı";
                                }
                                else if (s.contains("Bülent Korucu"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://cdncms.zaman.com.tr/columnists/ipad_yazarlar/bulentkorucu.png");
                                    imv.setImageBitmap(bimage);
                                	isim = "Bülent Korucu";
                                }
                                else if (s.contains("İskender Pala"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://cdncms.zaman.com.tr/columnists/ipad_yazarlar/iskenderpala.png");
                                    imv.setImageBitmap(bimage);
                                	isim = "İskender Pala";
                                }
                                else if (s.contains("Kadir Dikbaş"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://cdncms.zaman.com.tr/columnists/ipad_yazarlar/kadirdikbas.png");
                                    imv.setImageBitmap(bimage);
                                	isim = "Kadir Dikbaş";
                                }
                                else if (s.contains("Şahin Alpay"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://cdncms.zaman.com.tr/columnists/ipad_yazarlar/sahinalpay.png");
                                    imv.setImageBitmap(bimage);
                                	isim = "Şahin Alpay";
                                }
                                else if (s.contains("Fikret Ertan"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://cdncms.zaman.com.tr/columnists/ipad_yazarlar/fikretertan.png");
                                    imv.setImageBitmap(bimage);
                                	isim = "Fikret Ertan";
                                }
                                else if (s.contains("Ali H. Aslan"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://cdncms.zaman.com.tr/columnists/ipad_yazarlar/alih.aslan.png");
                                    imv.setImageBitmap(bimage);
                                	isim = "Ali H. Aslan";
                                }
                                else if (s.contains("İbrahim Öztürk"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://cdncms.zaman.com.tr/columnists/ipad_yazarlar/ibrahimozturk.png");
                                    imv.setImageBitmap(bimage);
                                	isim = "İbrahim Öztürk";
                                }
                               
                                else if (s.contains("Abdullah Aymaz"))
                                {
                                    Bitmap bimage=  getBitmapFromURL("http://cdncms.zaman.com.tr/columnists/ipad_yazarlar/abdullahaymaz.png");
                                    imv.setImageBitmap(bimage);
                                	isim = "Abdullah Aymaz";
                                }
                                else
                                {
                                	imv.setImageResource(R.drawable.zaman);
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
