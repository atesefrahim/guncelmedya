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


public class rssreaderkytaraf extends ListActivity {
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
        
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter= 
        new SimpleDateFormat("dd.MM.yyyy");
        myDate = formatter.format(currentDate.getTime());

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
                        retrieveRSSFeed("http://www.taraf.com.tr/rss/yazarlar/default.asp",itemlist);
                       
                        rssadaptor = new RSSListAdaptor(rssreaderkytaraf.this, R.layout.rssitemviewkopya,itemlist);
                       
                        return null;
                }
       
                @Override
                protected void onCancelled() {
                        super.onCancelled();
                }
               
                @Override
                protected void onPreExecute() {
                        progress = ProgressDialog.show(
                                        rssreaderkytaraf.this, null, "Yükleniyor...");
                       
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
                                LayoutInflater vi = (LayoutInflater)rssreaderkytaraf.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                view = vi.inflate(R.layout.rssitemviewkopya, null);
                        }
                       
                        RSSItem data = objects.get(position);
                        Calendar today1 = Calendar.getInstance();  
                        Calendar today2 = Calendar.getInstance();
                        
                     today1.add(Calendar.DATE, -2);  
                     today2.add(Calendar.DATE, -1);
                    

                     java.sql.Date yesterday2 = new java.sql.Date(today2.getTimeInMillis());  
                     SimpleDateFormat formatter2= 
                             new SimpleDateFormat("dd.MM.yyyy");

                            
                             myDatey = formatter2.format(yesterday2);

                        if(null != data)
                        {
                                TextView title = (TextView)view.findViewById(R.id.txtTitle);
                                TextView date = (TextView)view.findViewById(R.id.txtDate);
                                TextView description = (TextView)view.findViewById(R.id.txtDescription);
                               
                               

                                ImageView imv = (ImageView)view.findViewById(R.id.yazarlogo);
                                
                                String isim = data.title;
                                String baslik = data.description;
                                
                                String search = "-"; 
                                String result = ""; 

                                
                                int i; 
                                do { 
                                i = isim.indexOf(search); 
                                int a = i;
                                if(i != -1) { 
                                	
                                result = isim.substring(0, a);
                                 
                                isim = result; 

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
                             

                                if (s.contains("Sibel Oral"))
                                {
                                	
                                	
                                    imv.setImageResource(R.drawable.taraf);
                                	isim = "Sibel Oral";
                                	
                                }
                                else if (s.contains("Ayça Şen"))
                                {
                                	imv.setImageResource(R.drawable.asen);
                                	isim = "Ayça Şen";
                                }
                                else if (s.contains("Numan Türer"))
                                {
                                	imv.setImageResource(R.drawable.taraf);
                                	isim = "Numan Türer";
                                }
                                else if (s.contains("A. Esra Yalazan"))
                                {
                                	imv.setImageResource(R.drawable.ayalazan);
                                	isim = "A. Esra Yalazan";
                                }
                                else if (s.contains("Emre Uslu"))
                                {
                                	imv.setImageResource(R.drawable.euslu);
                                	isim = "Emre Uslu";
                                }
                                else if (s.contains("Çin Li"))
                                {
                                	imv.setImageResource(R.drawable.taraf);
                                	isim = "Çin Li";
                                }
                                else if (s.contains("Serdar Kaya"))
                                {
                                	imv.setImageResource(R.drawable.taraf);
                                	isim = "Serdar Kaya";
                                }
                                else if (s.contains("Ahmet Altan"))
                                {
                             
                                	imv.setImageResource(R.drawable.aaltan);
                                	isim = "Ahmet Altan";

                                	
    
                                }
                                else if (s.contains("Pelin Cengiz"))
                                {
                                	imv.setImageResource(R.drawable.pcengiz);
                                	isim = "Pelin Cengiz";
                                }
                                else if (s.contains("Barbaros Altuğ"))
                                {
                                	imv.setImageResource(R.drawable.taraf);
                                	isim = "Barbaros Altuğ";
                                }
                                else if (s.contains("Yıldıray Oğur"))
                                {
                                	imv.setImageResource(R.drawable.yogur);
                                	isim = "Yıldıray Oğur";
                                }
                                else if (s.contains("Murat Belge"))
                                {
                                	imv.setImageResource(R.drawable.mbelge);
                                	isim = "Murat Belge";
                                }
                                else if (s.contains("Andrew Finkel"))
                                {
                                	imv.setImageResource(R.drawable.afinkel);
                                	isim = "Andrew Finkel";
                                }
                                else if (s.contains("Telesiyej"))
                                {
                                	
                                	imv.setImageResource(R.drawable.tsiyej);
                                	isim = "Telesiyej";
                                	
                                }
                                else if (s.contains("Solmaz Kamuran"))
                                {
                                	imv.setImageResource(R.drawable.taraf);
                                	isim = "Solmaz Kamuran";
                                }
                                else if (s.contains("Roni Margulies"))
                                {
                                	imv.setImageResource(R.drawable.rmargulies);
                                	isim = "Roni Margulies";
                                }
                                else if (s.contains("Mithat Sancar"))
                                {
                                	imv.setImageResource(R.drawable.msancar);
                                	isim = "Mithat Sancar";
                                }
                                else if (s.contains("Levent Yılmaz"))
                                {
                                	imv.setImageResource(R.drawable.taraf);
                                	isim = "Levent Yılmaz";
                                }
                                else if (s.contains("Lale Kemal"))
                                {
                                	imv.setImageResource(R.drawable.lkemal);
                                	isim = "Lale Kemal";
                                }
                                else if (s.contains("İhsan Bilgin"))
                                {
                                	imv.setImageResource(R.drawable.taraf);
                                	isim = "İhsan Bilgin";
                                }
                                else if (s.contains("Halil Berktay"))
                                {
                                	imv.setImageResource(R.drawable.hberktay);
                                	isim = "Halil Berktay";
                                }
                                else if (s.contains("Hadi Uluengin"))
                                {
                                	imv.setImageResource(R.drawable.huluengin);
                                	isim = "Hadi Uluengin";
                                }
                                else if (s.contains("Gürbüz Özaltınlı"))
                                {
                                	imv.setImageResource(R.drawable.taraf);
                                	isim = "Gürbüz Özaltınlı";
                                }
                                else if (s.contains("Esmeray"))
                                {
                                	imv.setImageResource(R.drawable.taraf);
                                	isim = "Esmeray";
                                }
                                else if (s.contains("Can Belge"))
                                {
                                	imv.setImageResource(R.drawable.cbelge);
                                	isim = "Can Belge";
                                }
                                else if (s.contains("Bülent Şirin"))
                                {
                                	imv.setImageResource(R.drawable.taraf);
                                	isim = "Bülent Şirin";
                                }
                                else if (s.contains("Dr. Sivilay Genç"))
                                {
                                	imv.setImageResource(R.drawable.sgenc);
                                	isim = "Dr. Sivilay Genç";
                                }
                                else if (s.contains("Sezin Öney"))
                                {
                                	imv.setImageResource(R.drawable.soney);
                                	isim = "Sezin Öney";
                                }
                                else if (s.contains("Nurullah Öztürk"))
                                {
                                	imv.setImageResource(R.drawable.taraf);
                                	isim = "Nurullah Öztürk";
                                }
                                else if (s.contains("Mehmet Güreli"))
                                {
                                	imv.setImageResource(R.drawable.mgureli);
                                	isim = "Mehmet Güreli";
                                }
                                else if (s.contains("Kerem Altan"))
                                {
                                	imv.setImageResource(R.drawable.taraf);
                                	isim = "Kerem Altan";
                                }
                                else if (s.contains("Hidayet Şefkatli Tuksal"))
                                {
                                	imv.setImageResource(R.drawable.taraf);
                                	isim = "Hidayet Şefkatli Tuksal";
                                }
                                else if (s.contains("Markar Esayan"))
                                {
                                	imv.setImageResource(R.drawable.taraf);
                                	isim = "Markar Esayan";
                                }
                                else if (s.contains("Ferhat Uludere"))
                                {
                                	imv.setImageResource(R.drawable.fuludere);
                                	isim = "Ferhat Uludere";
                                }
                                else if (s.contains("Yasemin Çongar"))
                                {
                                	imv.setImageResource(R.drawable.ycongar);
                                	isim = "Yasemin Çongar";
                                }
                                else if (s.contains("Neşe Düzel"))
                                {
                                	imv.setImageResource(R.drawable.nduzel);
                                	isim = "Neşe Düzel";
                                }
                                else if (s.contains("Alper Görmüş"))
                                {
                                	imv.setImageResource(R.drawable.agormus);
                                	isim = "Alper Görmüş";
                                }
                                else if (s.contains("Cem Sey"))
                                {
                                	imv.setImageResource(R.drawable.csey);
                                	isim = "Cem Sey";
                                }
                                else if (s.contains("Erol Katırcıoğlu"))
                                {
                                	imv.setImageResource(R.drawable.taraf);
                                	isim = "Erol Katırcıoğlu";
                                }
                                else if (s.contains("Kurtuluş Tayiz"))
                                {
                                	imv.setImageResource(R.drawable.ktayiz);
                                	isim = "Kurtuluş Tayiz";
                                }
                                else if (s.contains("Özlem Ertan"))
                                {
                                	imv.setImageResource(R.drawable.oertan);
                                	isim = "Özlem Ertan";
                                }
                                else if (s.contains("Namık Çınar"))
                                {
                                	imv.setImageResource(R.drawable.taraf);
                                	isim = "Namık Çınar";
                                }
                                else if (s.contains("Murat Çetin"))
                                {
                                	imv.setImageResource(R.drawable.taraf);
                                	isim = "Murat Çetin";
                                }
                                else if (s.contains("Mehmet Baransu"))
                                {
                                	imv.setImageResource(R.drawable.mbaransu);
                                	isim = "Mehmet Baransu";
                                }
                                else if (s.contains("Gökhan Karabulut"))
                                {
                                	imv.setImageResource(R.drawable.taraf);
                                	isim = "Gökhan Karabulut";
                                }
                                else if (s.contains("Ertan Altan"))
                                {
                                	imv.setImageResource(R.drawable.taraf);
                                	isim = "Ertan Altan";
                                }
                                else if (s.contains("Fatih Uraz"))
                                {
                                	imv.setImageResource(R.drawable.furaz);
                                	isim = "Fatih Uraz";
                                }
                                else if (s.contains("Melih Altınok"))
                                {
                                	imv.setImageResource(R.drawable.maltinok);
                                	isim = "Melih Altınok";
                                }
                                else if (s.contains("Rengin Soysal"))
                                {
                                	imv.setImageResource(R.drawable.rsoysal);
                                	isim = "Rengin Soysal";
                                }
                                else if (s.contains("Semra Somersan"))
                                {
                                	imv.setImageResource(R.drawable.taraf);
                                	isim = "Semra Somersan";
                                }
                                else if (s.contains("Adnan Yıldız"))
                                {
                                	imv.setImageResource(R.drawable.taraf);
                                	isim = "Adnan Yıldız";
                                }
                                else if (s.contains("Akın Özçer"))
                                {
                                	imv.setImageResource(R.drawable.aozcer);
                                	isim = "Akın Özçer";
                                }
                                else if (s.contains("Cengiz Aktar"))
                                {
                                	imv.setImageResource(R.drawable.taraf);
                                	isim = "Cengiz Aktar";
                                }
                                else
                                {
                                	imv.setImageResource(R.drawable.taraf);
                                }
                
                                //Hürriyet Yazarlar Son       
                                title.setText(isim);
                                date.setText(data.date);
                                description.setText(baslik);
                                
                        }

                       
                        return view;
                }
    }
}
