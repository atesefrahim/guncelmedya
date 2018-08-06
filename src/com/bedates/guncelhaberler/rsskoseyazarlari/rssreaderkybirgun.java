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

public class rssreaderkybirgun extends ListActivity {
        private ArrayList<RSSItem> itemlist = null;
        private RSSListAdaptor rssadaptor = null;
        static String myDate; 
        static String myDatey; 
        static String myDatey2; 
        
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
           Calendar currentDate = Calendar.getInstance();
           SimpleDateFormat formatter= 
           new SimpleDateFormat("dd MMMM yyyy" , new Locale("tr"));
           myDate = formatter.format(currentDate.getTime());
           System.out.println("tarihhh === " + myDate);
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
                        retrieveRSSFeed("http://www.birgun.net/writer.xml",itemlist);
                       
                        rssadaptor = new RSSListAdaptor(rssreaderkybirgun.this, R.layout.rssitemviewkopya,itemlist);
                       
                        return null;
                }
       
                @Override
                protected void onCancelled() {
                        super.onCancelled();
                }
               
                @Override
                protected void onPreExecute() {
                        progress = ProgressDialog.show(
                        		rssreaderkybirgun.this, null, "Yükleniyor...");
                       
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
                                LayoutInflater vi = (LayoutInflater)rssreaderkybirgun.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                view = vi.inflate(R.layout.rssitemviewkopya, null);
                        }
                       
                        RSSItem data = objects.get(position);
                        
                        
                        
                        Calendar today1 = Calendar.getInstance();  
                        Calendar today2 = Calendar.getInstance();
                        
                     today1.add(Calendar.DATE, -2);  
                     today2.add(Calendar.DATE, -1);
                    
                     java.sql.Date yesterday = new java.sql.Date(today1.getTimeInMillis());   
                     java.sql.Date yesterday2 = new java.sql.Date(today2.getTimeInMillis());  
                     SimpleDateFormat formatter2= 
                             new SimpleDateFormat("dd MMMM yyyy", new Locale("tr"));

                             myDatey = formatter2.format(yesterday);
                             myDatey2 = formatter2.format(yesterday2);

                   

                        if(null != data )
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
                                
                                isim = isim.replaceAll("Ä±", "ı");
                                isim = isim.replaceAll("þ", "ş");
                                isim = isim.replaceAll("ð", "ğ");
                                isim = isim.replaceAll("Ý", "İ");
                                isim = isim.replaceAll("Þ", "Ş");
                                isim = isim.replaceAll("Ð", "Ğ");
                                isim = isim.replaceAll("Ã–", "Ö");
                                isim = isim.replaceAll("Ã¶", "ö");
                                isim = isim.replaceAll("Ãœ", "Ü");
                                isim = isim.replaceAll("Ã¼", "ü");
                                isim = isim.replaceAll("Ä°", "İ");
                                isim = isim.replaceAll("Ã‡", "Ç");
                                isim = isim.replaceAll("Ã§", "ç");
                                isim = isim.replaceAll("ÄŸ", "Ğ");
                                isim = isim.replaceAll("ÄŸ", "ğ");
                                isim = isim.replaceAll("Å", "Ş");
                                isim = isim.replaceAll("ÅŸ", "ş");
                                isim = isim.replaceAll("Ä°", "İ");
        
                                baslik = baslik.replaceAll("Ä±", "ı");
                                baslik = baslik.replaceAll("þ", "ş");
                                baslik = baslik.replaceAll("ð", "ğ");
                                baslik = baslik.replaceAll("Ý", "İ");
                                baslik = baslik.replaceAll("Þ", "Ş");
                                baslik = baslik.replaceAll("Ð", "Ğ");
                                baslik = baslik.replaceAll("Ã–", "Ö");
                                baslik = baslik.replaceAll("Ã¶", "ö");
                                baslik = baslik.replaceAll("Ãœ", "Ü");
                                baslik = baslik.replaceAll("Ã¼", "ü");
                                baslik = baslik.replaceAll("Ã‡", "Ç");
                                baslik = baslik.replaceAll("Ã§", "ç");
                                baslik = baslik.replaceAll("ÄŸ", "Ğ");
                                baslik = baslik.replaceAll("ÄŸ", "ğ");
                                baslik = baslik.replaceAll("Å", "Ş");
                                baslik = baslik.replaceAll("ÅŸ", "ş");
                                baslik = baslik.replaceAll("Ä°", "İ");
                                	
                           
                               
                              
  
                              String s = isim;
//Bugün Yazarlar Başlangıç
                                if (s.contains("L.Doğan Tılıç"))
                                {
                                    imv.setImageResource(R.drawable.ltilic);
                                	isim = "L.Doğan Tılıç";
                                }
                                else if (s.contains("Kemal Ulusaler"))
                                {
                                	imv.setImageResource(R.drawable.birgun);
                                	isim = "Kemal Ulusaler";
                                }
                                else if (s.contains("Nazım Alpman"))
                                {
                                	imv.setImageResource(R.drawable.nalpman);
                                	isim = "Nazım Alpman";
                                }
                                else if (s.contains("Fikri Sağlar"))
                                {
                                	imv.setImageResource(R.drawable.birgun);
                                	isim = "Fikri Sağlar";
                                }
                                else if (s.contains("İbrahim Varlı"))
                                {
                                	imv.setImageResource(R.drawable.birgun);
                                	isim = "İbrahim Varlı";
                                }
                                else if (s.contains("Bülend Karpat"))
                                {
                                	imv.setImageResource(R.drawable.birgun);
                                	isim = "Bülend Karpat";
                                }
                                else if (s.contains("Kadir Cangızbay"))
                                {
                                	imv.setImageResource(R.drawable.birgun);
                                	isim = "Kadir Cangızbay";
                                }                               
                                else if (s.contains("Cüneyt Cebenoyan"))
                                {
                                	imv.setImageResource(R.drawable.ccebenoyan);
                                	isim = "Cüneyt Cebenoyan";
                                }
                                else if (s.contains("Uğur Kutay"))
                                {
                                	imv.setImageResource(R.drawable.birgun);
                                	isim = "Uğur Kutay";
                                }
                                else if (s.contains("Refik Durbaş"))
                                {
                                	imv.setImageResource(R.drawable.rdurbas);
                                	isim = "Refik Durbaş";
                                }
                                else if (s.contains("Aziz Çelik"))
                                {
                                	imv.setImageResource(R.drawable.acelik);
                                	isim = "Aziz Çelik";
                                }
                                else if (s.contains("İbrahim Özden Kaboğlu"))
                                {
                                	imv.setImageResource(R.drawable.ikaboglu);
                                	isim = "İbrahim Özden Kaboğlu";
                                }
                                else if (s.contains("Haluk Geray"))
                                {
                                	imv.setImageResource(R.drawable.hgeray);
                                	isim = "Haluk Geray";
                                }
                                else if (s.contains("Rahmi Öğdül"))
                                {
                                	imv.setImageResource(R.drawable.rogdul);
                                	isim = "Rahmi Öğdül";
                                }
                                else if (s.contains("Fırat Topal"))
                                {
                                	imv.setImageResource(R.drawable.ftopal);
                                	isim = "Fırat Topal";
                                }
                                else
                                {
                                	imv.setImageResource(R.drawable.birgun);
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
