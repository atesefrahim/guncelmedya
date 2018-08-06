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
                                        rssreaderkyradikal.this, null, "Y�kleniyor...");
                       
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
//H�rriyet Yazarlar Ba�lang��
                                if (s.contains("AVN� �ZG�REL"))
                                {
                                	imv.setImageResource(R.drawable.aozgurel);
                                	isim = "AVN� �ZG�REL";
                                }
                                else if (s.contains("FAT�H �ZATAY"))
                                {
                                	imv.setImageResource(R.drawable.fozatay);
                                	isim = "FAT�H �ZATAY";
                                }
                                else if (s.contains("MURAT YETK�N"))
                                {
                                	imv.setImageResource(R.drawable.myetkin);
                                	isim = "MURAT YETK�N";
                                }
                                else if (s.contains("CENG�Z �ANDAR"))
                                {
                                	imv.setImageResource(R.drawable.ccandar);
                                	isim = "CENG�Z �ANDAR";
                                }
                                else if (s.contains("ORAL �ALI�LAR"))
                                {
                                	imv.setImageResource(R.drawable.ocalislar);
                                	isim = "ORAL �ALI�LAR";
                                }
                                else if (s.contains("AHMET �NSEL"))
                                {
                                	imv.setImageResource(R.drawable.ainsel);
                                	isim = "AHMET �NSEL";
                                }
                                else if (s.contains("KORAY �ALI�KAN"))
                                {
                                	imv.setImageResource(R.drawable.kcaliskan);
                                	isim = "KORAY �ALI�KAN";
                                }
                                else if (s.contains("EY�P CAN"))
                                {
                                	imv.setImageResource(R.drawable.ecan);
                                	isim = "EY�P CAN";
                                }
                                else if (s.contains("EZG� BA�ARAN"))
                                {
                                    imv.setImageResource(R.drawable.ebasaran);
                                	isim = "EZG� BA�ARAN";
                                }
                                else if (s.contains("G�VEN SAK"))
                                {
                                	imv.setImageResource(R.drawable.gsak);
                                	isim = "G�VEN SAK";
                                }
                                else if (s.contains("KENAN BA�ARAN"))
                                {
                                	imv.setImageResource(R.drawable.kbasaran);
                                	isim = "KENAN BA�ARAN";
                                }
                                else if (s.contains("TAYFUN ATAY"))
                                {
                                	imv.setImageResource(R.drawable.tatay);
                                	isim = "TAYFUN ATAY";
                                }
                                else if (s.contains("�ZG�R MUMCU"))
                                {
                                	imv.setImageResource(R.drawable.omumcu);
                                	isim = "�ZG�R MUMCU";
                                }
                                else if (s.contains("FEH�M TA�TEK�N"))
                                {
                                	imv.setImageResource(R.drawable.ftastekin);
                                	isim = "FEH�M TA�TEK�N";
                                }
                                else if (s.contains("TAN MORG�L"))
                                {
                                	imv.setImageResource(R.drawable.tmorgul);
                                	isim = "TAN MORG�L";
                                }
                                else if (s.contains("PINAR ���N�"))
                                {
                                	imv.setImageResource(R.drawable.pogunc);
                                	isim = "PINAR ���N�";
                                }
                                else if (s.contains("G�ND�Z VASSAF"))
                                {
                                	imv.setImageResource(R.drawable.gvassaf);
                                	isim = "G�ND�Z VASSAF";
                                }
                                else if (s.contains("C�NEYT �ZDEM�R"))
                                {
                                	imv.setImageResource(R.drawable.cozdemir);
                                	isim = "C�NEYT �ZDEM�R";
                                }
                                else if (s.contains("U�UR G�RSES"))
                                {
                                	imv.setImageResource(R.drawable.ugurses);
                                	isim = "U�UR G�RSES";
                                }
                                else if (s.contains("FAT�H �ZG�VEN"))
                                {
                                	imv.setImageResource(R.drawable.fozguven);
                                	isim = "FAT�H �ZG�VEN";
                                }
                                else if (s.contains("TARHAN ERDEM"))
                                {
                                	imv.setImageResource(R.drawable.terdem);
                                	isim = "TARHAN ERDEM";
                                }
                                else if (s.contains("BERR�N KARAKA�"))
                                {
                                	imv.setImageResource(R.drawable.bkarakas);
                                	isim = "BERR�N KARAKA�";
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
                                else if (s.contains("SEYFETT�N G�RSEL"))
                                {
                                	imv.setImageResource(R.drawable.sgursel);
                                	isim = "SEYFETT�N G�RSEL";
                                }
                                else if (s.contains("DR. ALPER HASANO�LU"))
                                {
                                	imv.setImageResource(R.drawable.ahasanoglu);
                                	isim = "DR. ALPER HASANO�LU";
                                }
                                else if (s.contains("TUGBA KIRA�"))
                                {
                                	imv.setImageResource(R.drawable.tkirac);
                                	isim = "TU�BA KIRA�";
                                }
                                else if (s.contains("AY�E H�R"))
                                {
                                	imv.setImageResource(R.drawable.ahur);
                                	isim = "AY�E H�R";
                                }
                                else if (s.contains("U�UR VARDAN"))
                                {
                                	imv.setImageResource(R.drawable.uvardan);
                                	isim = "U�UR VARDAN";
                                }
                                else if (s.contains("JALE �ZGENT�RK"))
                                {
                                	imv.setImageResource(R.drawable.jozgenturk);
                                	isim = "JALE �ZGENT�RK";
                                }
                                else if (s.contains("DEN�Z ZEYREK"))
                                {
                                	imv.setImageResource(R.drawable.dzeyrek);
                                	isim = "DEN�Z ZEYREK";
                                }
                                else if (s.contains("AK�F BEK�"))
                                {
                                	imv.setImageResource(R.drawable.abeki);
                                	isim = "AK�F BEK�";
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
                                else if (s.contains("M�GE AKG�N"))
                                {
                                	imv.setImageResource(R.drawable.makgun);
                                	isim = "M�GE AKG�N";
                                }
                                else if (s.contains("MET�N ERCAN"))
                                {
                                	imv.setImageResource(R.drawable.mercan);
                                	isim = "MET�N ERCAN";
                                }
           
                                else if (s.contains("ALTAN �YMEN"))
                                {
                                	imv.setImageResource(R.drawable.aoymen);
                                	isim = "ALTAN �YMEN";
                                }
                                else if (s.contains("EVR�M S�MER"))
                                {
                                	imv.setImageResource(R.drawable.esumer);
                                	isim = "EVR�M S�MER";
                                }
                                else if (s.contains("CEM ERC�YES"))
                                {
                                	imv.setImageResource(R.drawable.cerciyes);
                                	isim = "CEM ERC�YES";
                                }
                                else if (s.contains("BURAK KURU"))
                                {
                                	imv.setImageResource(R.drawable.bkuru);
                                	isim = "BURAK KURU";
                                }
                                else if (s.contains("M. SERDAR KUZULO�LU"))
                                {
                                	imv.setImageResource(R.drawable.mkuzuloglu);
                                	isim = "M. SERDAR KUZULO�LU";
                                }
                                else if (s.contains("�M�T �ZMEN"))
                                {
                                	imv.setImageResource(R.drawable.uizmen);
                                	isim = "�M�T �ZMEN";
                                }
                                else if (s.contains("YETVART DANZ�KYAN"))
                                {
                                	imv.setImageResource(R.drawable.ydanzikyan);
                                	isim = "YETVART DANZ�KYAN";
                                }
                                else if (s.contains("ERKAN GOLO�LU"))
                                {
                                	imv.setImageResource(R.drawable.egologlu);
                                	isim = "ERKAN GOLOGLU";
                                }
                                else if (s.contains("ERDAL SA�LAM"))
                                {
                                	imv.setImageResource(R.drawable.esaglam);
                                	isim = "ERDAL SA�LAM";
                                }
                                else if (s.contains("�MER �AH�N"))
                                {
                                	imv.setImageResource(R.drawable.osahin);
                                	isim = "�MER �AH�N";
                                }
                                else if (s.contains("ORHAN KEMAL CENG�Z"))
                                {
                                	imv.setImageResource(R.drawable.ocengiz);
                                	isim = "ORHAN KEMAL CENG�Z";
                                }
                                else if (s.contains("G�K�E AYTULU"))
                                {
                                	imv.setImageResource(R.drawable.gaytulu);
                                	isim = "G�K�E AYTULU";
                                }
                                else if (s.contains("G�R�L ���T"))
                                {
                                	imv.setImageResource(R.drawable.gogut);
                                	isim = "G�R�L ���T";
                                }
                                else if (s.contains("AL� TOPUZ"))
                                {
                                	imv.setImageResource(R.drawable.atopuz);
                                	isim = "AL� TOPUZ";
                                }
                                else if (s.contains("BAHAR �UHADAR"))
                                {
                                	imv.setImageResource(R.drawable.bcuhadar);
                                	isim = "BAHAR �UHADAR";
                                }
                                else if (s.contains("BA�I� ERTEN"))
                                {
                                	imv.setImageResource(R.drawable.berten);
                                	isim = "BA�I� ERTEN";
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
