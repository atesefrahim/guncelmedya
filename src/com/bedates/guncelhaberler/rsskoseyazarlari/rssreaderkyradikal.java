package com.bedates.guncelhaberler.rsskoseyazarlari;


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


public class rssreaderkyradikal extends ListActivity {
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
                        retrieveRSSFeed("http://www.radikal.com.tr/d/rss/RssYazarlar.xml",itemlist);
                       
                        rssadaptor = new RSSListAdaptor(rssreaderkyradikal.this, R.layout.rssitemviewkopya,itemlist);
                       
                        return null;
                }
       
                @Override
                protected void onCancelled() {
                        super.onCancelled();
                }
               
                @Override
                protected void onPreExecute() {
                        progress = ProgressDialog.show(
                                        rssreaderkyradikal.this, null, "Yükleniyor...");
                       
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
                                LayoutInflater vi = (LayoutInflater)rssreaderkyradikal.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                view = vi.inflate(R.layout.rssitemviewkopya, null);
                        }
                       
                        RSSItem data = objects.get(position);
 

                     
                     
  /*                   Calendar currentDate = Calendar.getInstance();
                     SimpleDateFormat formatter= new SimpleDateFormat("dd.MM.yyyy");
                     
                     myDate = formatter.format(currentDate.getTime());
                     
                     System.out.println("bugunun tarihi === " + myDate);
                     
                     String xmltarih = data.date;
                     Date convertedDate = new Date();
                     try {
                    	 
                         convertedDate = formatter.parse(xmltarih);
                     } catch (ParseException e) {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
                     }
                     System.out.println("xml tarih "+convertedDate);
*/
                     
                             
                        if(null != data) {
                                TextView title = (TextView)view.findViewById(R.id.txtTitle);
                                TextView date = (TextView)view.findViewById(R.id.txtDate);
                                TextView description = (TextView)view.findViewById(R.id.txtDescription);
                               
                               

                                ImageView imv = (ImageView)view.findViewById(R.id.yazarlogo);
                                
                                String isim = data.description;
                                String baslik = data.title;
                                
                                String search = "-"; 
                     
                                String resultisim = ""; 
                        
                                
                                int i; 
                                do { 
                                i = baslik.indexOf(search); 
                                int a = i;
                                if(i != -1) { 
                                	
                                int y = isim.length();
                                resultisim = isim.substring(a+2 ,y);
                                isim = resultisim;
          
                                } 
                                } while(i != -1); 
        
                              String s = isim;
//Hürriyet Yazarlar Baþlangýç
                                if (s.contains("AVNÝ ÖZGÜREL"))
                                {
                                	imv.setImageResource(R.drawable.aozgurel);
                                	isim = "AVNÝ ÖZGÜREL";
                                }
                                else if (s.contains("FATÝH ÖZATAY"))
                                {
                                	imv.setImageResource(R.drawable.fozatay);
                                	isim = "FATÝH ÖZATAY";
                                }
                                else if (s.contains("MURAT YETKÝN"))
                                {
                                	imv.setImageResource(R.drawable.myetkin);
                                	isim = "MURAT YETKÝN";
                                }
                                else if (s.contains("CENGÝZ ÇANDAR"))
                                {
                                	imv.setImageResource(R.drawable.ccandar);
                                	isim = "CENGÝZ ÇANDAR";
                                }
                                else if (s.contains("ORAL ÇALIÞLAR"))
                                {
                                	imv.setImageResource(R.drawable.ocalislar);
                                	isim = "ORAL ÇALIÞLAR";
                                }
                                else if (s.contains("AHMET ÝNSEL"))
                                {
                                	imv.setImageResource(R.drawable.ainsel);
                                	isim = "AHMET ÝNSEL";
                                }
                                else if (s.contains("KORAY ÇALIÞKAN"))
                                {
                                	imv.setImageResource(R.drawable.kcaliskan);
                                	isim = "KORAY ÇALIÞKAN";
                                }
                                else if (s.contains("EYÜP CAN"))
                                {
                                	imv.setImageResource(R.drawable.ecan);
                                	isim = "EYÜP CAN";
                                }
                                else if (s.contains("EZGÝ BAÞARAN"))
                                {
                                    imv.setImageResource(R.drawable.ebasaran);
                                	isim = "EZGÝ BAÞARAN";
                                }
                                else if (s.contains("GÜVEN SAK"))
                                {
                                	imv.setImageResource(R.drawable.gsak);
                                	isim = "GÜVEN SAK";
                                }
                                else if (s.contains("KENAN BAÞARAN"))
                                {
                                	imv.setImageResource(R.drawable.kbasaran);
                                	isim = "KENAN BAÞARAN";
                                }
                                else if (s.contains("TAYFUN ATAY"))
                                {
                                	imv.setImageResource(R.drawable.tatay);
                                	isim = "TAYFUN ATAY";
                                }
                                else if (s.contains("ÖZGÜR MUMCU"))
                                {
                                	imv.setImageResource(R.drawable.omumcu);
                                	isim = "ÖZGÜR MUMCU";
                                }
                                else if (s.contains("FEHÝM TAÞTEKÝN"))
                                {
                                	imv.setImageResource(R.drawable.ftastekin);
                                	isim = "FEHÝM TAÞTEKÝN";
                                }
                                else if (s.contains("TAN MORGÜL"))
                                {
                                	imv.setImageResource(R.drawable.tmorgul);
                                	isim = "TAN MORGÜL";
                                }
                                else if (s.contains("PINAR ÖÐÜNÇ"))
                                {
                                	imv.setImageResource(R.drawable.pogunc);
                                	isim = "PINAR ÖÐÜNÇ";
                                }
                                else if (s.contains("GÜNDÜZ VASSAF"))
                                {
                                	imv.setImageResource(R.drawable.gvassaf);
                                	isim = "GÜNDÜZ VASSAF";
                                }
                                else if (s.contains("CÜNEYT ÖZDEMÝR"))
                                {
                                	imv.setImageResource(R.drawable.cozdemir);
                                	isim = "CÜNEYT ÖZDEMÝR";
                                }
                                else if (s.contains("UÐUR GÜRSES"))
                                {
                                	imv.setImageResource(R.drawable.ugurses);
                                	isim = "UÐUR GÜRSES";
                                }
                                else if (s.contains("FATÝH ÖZGÜVEN"))
                                {
                                	imv.setImageResource(R.drawable.fozguven);
                                	isim = "FATÝH ÖZGÜVEN";
                                }
                                else if (s.contains("TARHAN ERDEM"))
                                {
                                	imv.setImageResource(R.drawable.terdem);
                                	isim = "TARHAN ERDEM";
                                }
                                else if (s.contains("BERRÝN KARAKAÞ"))
                                {
                                	imv.setImageResource(R.drawable.bkarakas);
                                	isim = "BERRÝN KARAKAÞ";
                                }
                                else if (s.contains("BANU K. YELKOVAN"))
                                {
                                	imv.setImageResource(R.drawable.byelkovan);
                                	isim = "BANU K. YELKOVAN";
                                }
                                else if (s.contains("TANIL BORA"))
                                {
                                	imv.setImageResource(R.drawable.tbora);
                                	isim = "TANIL BORA";
                                }
                                else if (s.contains("SEYFETTÝN GÜRSEL"))
                                {
                                	imv.setImageResource(R.drawable.sgursel);
                                	isim = "SEYFETTÝN GÜRSEL";
                                }
                                else if (s.contains("DR. ALPER HASANOÐLU"))
                                {
                                	imv.setImageResource(R.drawable.ahasanoglu);
                                	isim = "DR. ALPER HASANOÐLU";
                                }
                                else if (s.contains("TUGBA KIRAÇ"))
                                {
                                	imv.setImageResource(R.drawable.tkirac);
                                	isim = "TUÐBA KIRAÇ";
                                }
                                else if (s.contains("AYÞE HÜR"))
                                {
                                	imv.setImageResource(R.drawable.ahur);
                                	isim = "AYÞE HÜR";
                                }
                                else if (s.contains("UÐUR VARDAN"))
                                {
                                	imv.setImageResource(R.drawable.uvardan);
                                	isim = "UÐUR VARDAN";
                                }
                                else if (s.contains("JALE ÖZGENTÜRK"))
                                {
                                	imv.setImageResource(R.drawable.jozgenturk);
                                	isim = "JALE ÖZGENTÜRK";
                                }
                                else if (s.contains("DENÝZ ZEYREK"))
                                {
                                	imv.setImageResource(R.drawable.dzeyrek);
                                	isim = "DENÝZ ZEYREK";
                                }
                                else if (s.contains("AKÝF BEKÝ"))
                                {
                                	imv.setImageResource(R.drawable.abeki);
                                	isim = "AKÝF BEKÝ";
                                }
                                else if (s.contains("ERCAN KESAL"))
                                {
                                	imv.setImageResource(R.drawable.ekesal);
                                	isim = "ERCAN KESAL";
                                }
                                else if (s.contains("VEDAT ATASOY"))
                                {
                                	imv.setImageResource(R.drawable.vatasoy);
                                	isim = "VEDAT ATASOY";
                                }
                                else if (s.contains("MÜGE AKGÜN"))
                                {
                                	imv.setImageResource(R.drawable.makgun);
                                	isim = "MÜGE AKGÜN";
                                }
                                else if (s.contains("METÝN ERCAN"))
                                {
                                	imv.setImageResource(R.drawable.mercan);
                                	isim = "METÝN ERCAN";
                                }
           
                                else if (s.contains("ALTAN ÖYMEN"))
                                {
                                	imv.setImageResource(R.drawable.aoymen);
                                	isim = "ALTAN ÖYMEN";
                                }
                                else if (s.contains("EVRÝM SÜMER"))
                                {
                                	imv.setImageResource(R.drawable.esumer);
                                	isim = "EVRÝM SÜMER";
                                }
                                else if (s.contains("CEM ERCÝYES"))
                                {
                                	imv.setImageResource(R.drawable.cerciyes);
                                	isim = "CEM ERCÝYES";
                                }
                                else if (s.contains("BURAK KURU"))
                                {
                                	imv.setImageResource(R.drawable.bkuru);
                                	isim = "BURAK KURU";
                                }
                                else if (s.contains("M. SERDAR KUZULOÐLU"))
                                {
                                	imv.setImageResource(R.drawable.mkuzuloglu);
                                	isim = "M. SERDAR KUZULOÐLU";
                                }
                                else if (s.contains("ÜMÝT ÝZMEN"))
                                {
                                	imv.setImageResource(R.drawable.uizmen);
                                	isim = "ÜMÝT ÝZMEN";
                                }
                                else if (s.contains("YETVART DANZÝKYAN"))
                                {
                                	imv.setImageResource(R.drawable.ydanzikyan);
                                	isim = "YETVART DANZÝKYAN";
                                }
                                else if (s.contains("ERKAN GOLOÐLU"))
                                {
                                	imv.setImageResource(R.drawable.egologlu);
                                	isim = "ERKAN GOLOGLU";
                                }
                                else if (s.contains("ERDAL SAÐLAM"))
                                {
                                	imv.setImageResource(R.drawable.esaglam);
                                	isim = "ERDAL SAÐLAM";
                                }
                                else if (s.contains("ÖMER ÞAHÝN"))
                                {
                                	imv.setImageResource(R.drawable.osahin);
                                	isim = "ÖMER ÞAHÝN";
                                }
                                else if (s.contains("ORHAN KEMAL CENGÝZ"))
                                {
                                	imv.setImageResource(R.drawable.ocengiz);
                                	isim = "ORHAN KEMAL CENGÝZ";
                                }
                                else if (s.contains("GÖKÇE AYTULU"))
                                {
                                	imv.setImageResource(R.drawable.gaytulu);
                                	isim = "GÖKÇE AYTULU";
                                }
                                else if (s.contains("GÜRÜL ÖÐÜT"))
                                {
                                	imv.setImageResource(R.drawable.gogut);
                                	isim = "GÜRÜL ÖÐÜT";
                                }
                                else if (s.contains("ALÝ TOPUZ"))
                                {
                                	imv.setImageResource(R.drawable.atopuz);
                                	isim = "ALÝ TOPUZ";
                                }
                                else if (s.contains("BAHAR ÇUHADAR"))
                                {
                                	imv.setImageResource(R.drawable.bcuhadar);
                                	isim = "BAHAR ÇUHADAR";
                                }
                                else if (s.contains("BAÐIÞ ERTEN"))
                                {
                                	imv.setImageResource(R.drawable.berten);
                                	isim = "BAÐIÞ ERTEN";
                                }
                                else {
                                                                                                                                                                                                                        	 
                                      imv.setImageResource(R.drawable.radikal);
                                      }
                                   
                                title.setText(isim);
                                date.setText("");
                                description.setText(baslik);
                                
                        }

                       
                        return view;
                }
    }
}
