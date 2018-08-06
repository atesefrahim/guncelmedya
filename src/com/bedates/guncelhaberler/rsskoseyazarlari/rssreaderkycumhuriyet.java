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
                        		rssreaderkycumhuriyet.this, null, "Y�kleniyor...");
                       
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
//Cumhuriyet Yazarlar Ba�lang��
                                if (s.contains("C�neyt Arcay�rek"))
                                {
                                    imv.setImageResource(R.drawable.carcayurek);
                                	isim = "C�neyt Arcay�rek";
                                }
                                else if (s.contains("Oktay Akbal"))
                                {
                                	imv.setImageResource(R.drawable.oakbal);
                                	isim = "Oktay Akbal";
                                }
                                else if (s.contains("Bekir Co�kun"))
                                {
                                	imv.setImageResource(R.drawable.bcoskun);
                                	isim = "Bekir Co�kun";
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
                                else if (s.contains("Hikmet �etinkaya"))
                                {
                                	imv.setImageResource(R.drawable.hcetinkaya);
                                	isim = "Hikmet �etinkaya";
                                }
                                else if (s.contains("Orhan Bursal�"))
                                {
                                	imv.setImageResource(R.drawable.obursali);
                                	isim = "Orhan Bursal�";
                                }
                                else if (s.contains("Orhan Birgit"))
                                {
                                	imv.setImageResource(R.drawable.obirgit);
                                	isim = "Orhan Birgit";
                                }
                                else if (s.contains("�zgen Acar"))
                                {
                                	imv.setImageResource(R.drawable.oacar);
                                	isim = "�zgen Acar";
                                }
                                else if (s.contains("Utku �ak�r�zer"))
                                {
                                	imv.setImageResource(R.drawable.ucakirozer);
                                	isim = "Utku �ak�r�zer";
                                }
                                else if (s.contains("K�r�at Ba�ar"))
                                {
                                	imv.setImageResource(R.drawable.kbasar);
                                	isim = "K�r�at Ba�ar";
                                }
                                else if (s.contains("��kran Soner"))
                                {
                                	imv.setImageResource(R.drawable.ssoner);
                                	isim = "��kran Soner";
                                }
                                else if (s.contains("Nilg�n Cerraho�lu"))
                                {
                                	imv.setImageResource(R.drawable.ncerrahoglu);
                                	isim = "Nilg�n Cerraho�lu";
                                }
                                else if (s.contains("Mustafa Pamuko�lu"))
                                {
                                	imv.setImageResource(R.drawable.mpamukoglu);
                                	isim = "Mustafa Pamuko�lu";
                                }
                                else if (s.contains("Serdar K�z�k"))
                                {
                                	imv.setImageResource(R.drawable.skizik);
                                	isim = "Serdar K�z�k";
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
                                else if (s.contains("�nci Aral"))
                                {
                                	imv.setImageResource(R.drawable.iaral);
                                	isim = "�nci Aral";
                                }
                                else if (s.contains("I��l �zgent�rk"))
                                {
                                	imv.setImageResource(R.drawable.iozgenturk);
                                	isim = "I��l �zgent�rk";
                                }
                                else if (s.contains("Adnan Din�er"))
                                {
                                	imv.setImageResource(R.drawable.cumhuriyet);
                                	isim = "Adnan Din�er";
                                }
                                else if (s.contains("Haydar Erg�len"))
                                {
                                	imv.setImageResource(R.drawable.cumhuriyet);
                                	isim = "Haydar Erg�len";
                                }
                                else if (s.contains("Mustafa Balbay"))
                                {
                                	imv.setImageResource(R.drawable.mbalbay);
                                	isim = "Mustafa Balbay";
                                }
                                else if (s.contains("Orhan Erin�"))
                                {
                                	imv.setImageResource(R.drawable.oerinc);
                                	isim = "Orhan Erin�";
                                }
                                else if (s.contains("Erdal Atabek"))
                                {
                                	imv.setImageResource(R.drawable.eatabek);
                                	isim = "Erdal Atabek";
                                }
                                else if (s.contains("Mustafa S�nmez"))
                                {
                                	imv.setImageResource(R.drawable.msonmez);
                                	isim = "Mustafa S�nmez";
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
                                else if (s.contains("�ztin Akg��"))
                                {
                                	imv.setImageResource(R.drawable.oakguc);
                                	isim = "�ztin Akg��";
                                }else if (s.contains("Deniz Kavuk�uo�lu"))
                                {
                                	imv.setImageResource(R.drawable.dkavukcuoglu);
                                	isim = "Deniz Kavuk�uo�lu";
                                }else if (s.contains("Oktay Ekinci"))
                                {
                                	imv.setImageResource(R.drawable.oekinci);
                                	isim = "Oktay Ekinci";
                                }else if (s.contains("Mine G. K�r�kkanat"))
                                {
                                	imv.setImageResource(R.drawable.cumhuriyet);
                                	isim = "Mine G. K�r�kkanat";
                                }else if (s.contains("Zeynep Oral"))
                                {
                                	imv.setImageResource(R.drawable.zoral);
                                	isim = "Zeynep Oral";
                                }else if (s.contains("O�uz Tongsir"))
                                {
                                	imv.setImageResource(R.drawable.cumhuriyet);
                                	isim = "O�uz Tongsir";
                                }else if (s.contains("I��k Kansu"))
                                {
                                	imv.setImageResource(R.drawable.ikansu);
                                	isim = "I��k Kansu";
                                }else if (s.contains("Zeynep G����"))
                                {
                                	imv.setImageResource(R.drawable.zgogus);
                                	isim = "Zeynep G����";
                                }else if (s.contains("Barbaros Tal�"))
                                {
                                	imv.setImageResource(R.drawable.btali);
                                	isim = "Barbaros Tal�";
                                }else if (s.contains("M�mtaz Soysal"))
                                {
                                	imv.setImageResource(R.drawable.msoysal);
                                	isim = "M�mtaz Soysal";
                                }
                                else if (s.contains("Ataol Behramo�lu"))
                                {
                                	imv.setImageResource(R.drawable.abehramoglu);
                                	isim = "Ataol Behramo�lu";
                                }
                                else if (s.contains("�lk� Tamer"))
                                {
                                	imv.setImageResource(R.drawable.cumhuriyet);
                                	isim = "�lk� Tamer";
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
