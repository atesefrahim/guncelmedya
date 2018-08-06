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


public class rssreaderkyvatan extends ListActivity {
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
                        retrieveRSSFeed("http://rss.gazetevatan.com/rss/yazar.xml",itemlist);
                       
                        rssadaptor = new RSSListAdaptor(rssreaderkyvatan.this, R.layout.rssitemviewkopya,itemlist);
                       
                        return null;
                }
       
                @Override
                protected void onCancelled() {
                        super.onCancelled();
                }
               
                @Override
                protected void onPreExecute() {
                        progress = ProgressDialog.show(
                        		rssreaderkyvatan.this, null, "Yükleniyor...");
                       
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
                                LayoutInflater vi = (LayoutInflater)rssreaderkyvatan.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
//HÃƒÂ¼rriyet Yazarlar BaÃ…Å¸langÃ„Â±ÃƒÂ§
                               if (s.contains("Güngör Mengi"))
                                {
                                    imv.setImageResource(R.drawable.gmengi);
                                	isim = "Güngör Mengi";
                                }else if (s.contains("Dilek Önder"))
                                {
                                	imv.setImageResource(R.drawable.donder);
                                	isim = "Dilek Önder";
                                }else if (s.contains("Ruhat Mengi"))
                                {
                                	imv.setImageResource(R.drawable.rmengi);
                                	isim = "Ruhat Mengi";
                                }else if (s.contains("Mutlu Tönbekici"))
                                {
                                	imv.setImageResource(R.drawable.mtonbekici);
                                	isim = "Mutlu Tönbekici";
                                }else if (s.contains("Reha Muhtar"))
                                {
                                	imv.setImageResource(R.drawable.rmuhtar);
                                	isim = "Reha Muhtar";
                                }else if (s.contains("Sanem Altan"))
                                {
                                	imv.setImageResource(R.drawable.saltan);
                                	isim = "Sanem Altan";
                                }else if (s.contains("Mustafa Mutlu"))
                                {
                                	imv.setImageResource(R.drawable.mmutlu);
                                	isim = "Mustafa Mutlu";
                                }else if (s.contains("Selahattin Duman"))
                                {
                                	imv.setImageResource(R.drawable.sduman);
                                	isim = "Selahattin Duman";
                                }else if (s.contains("Ruşen Çakır"))
                                {
                                	imv.setImageResource(R.drawable.rcakir);
                                	isim = "Ruşen Çakır";
                                }else if (s.contains("Bilal Çetin"))
                                {
                                	imv.setImageResource(R.drawable.vatan);
                                	isim = "Bilal Çetin";
                                }else if (s.contains("Kadir Çetinçalı"))
                                {
                                	imv.setImageResource(R.drawable.vatan);
                                	isim = "Kadir Çetinçalı";
                                }else if (s.contains("Lütfü Özel"))
                                {
                                	imv.setImageResource(R.drawable.vatan);
                                	isim = "Lütfü Özel";
                                }else if (s.contains("Serhat Ulueren"))
                                {
                                	imv.setImageResource(R.drawable.sulueren);
                                	isim = "Serhat Ulueren";
                                }
                                else if (s.contains("Müge İplikçi"))
                                {
                                	imv.setImageResource(R.drawable.miplikci);
                                	isim = "Müge İplikçi";
                                }
                                else if (s.contains("Ali Ağaoğlu"))
                                {
                                	imv.setImageResource(R.drawable.aagaoglu);
                                	isim = "Ali Ağaoğlu";
                                }
                                else if (s.contains("İclal Aydın"))
                                {
                                	imv.setImageResource(R.drawable.iaydin);
                                	isim = "İclal Aydın";
                                }
                                else if (s.contains("Okay Gönensin"))
                                {
                                	imv.setImageResource(R.drawable.ogonensin);
                                	isim = "Okay Gönensin";
                                }
                                else if (s.contains("Erol Çevikçe"))
                                {
                                	imv.setImageResource(R.drawable.ecevikce);
                                	isim = "Erol Çevikçe";
                                }
                                else if (s.contains("Tayfun Bayındır"))
                                {
                                	imv.setImageResource(R.drawable.vatan);
                                	isim = "Tayfun Bayındır";
                                }
                                else if (s.contains("Ömer Güvenç"))
                                {
                                	imv.setImageResource(R.drawable.oguvenc);
                                	isim = "Ömer Güvenç";
                                }
                                else if (s.contains("Mine Şenocaklı"))
                                {
                                	imv.setImageResource(R.drawable.vatan);
                                	isim = "Mine Şenocaklı";
                                }
                                else if (s.contains("Güntekin Onay"))
                                {
                                	imv.setImageResource(R.drawable.gonay);
                                	isim = "Güntekin Onay";
                                }
                                else if (s.contains("Ersin Düzen"))
                                {
                                	imv.setImageResource(R.drawable.eduzen);
                                	isim = "Ersin Düzen";
                                }
                                else if (s.contains("İbrahim Seten"))
                                {
                                	imv.setImageResource(R.drawable.vatan);
                                	isim = "İbrahim Seten";
                                }
                                else if (s.contains("Ufuk Korcan"))
                                {
                                	imv.setImageResource(R.drawable.vatan);
                                	isim = "Ufuk Korcan";
                                }
                                else if (s.contains("Murat Çelik"))
                                {
                                	imv.setImageResource(R.drawable.vatan);
                                	isim = "Murat Çelik";
                                }
                                else if (s.contains("Zülfü Livaneli"))
                                {
                                	imv.setImageResource(R.drawable.zlivaneli);
                                	isim = "Zülfü Livaneli";
                                }
                                else if (s.contains("Elif Ergu"))
                                {
                                	imv.setImageResource(R.drawable.vatan);
                                	isim = "Elif Ergu";
                                }
                                else if (s.contains("Asaf Savaş Akat"))
                                {
                                	imv.setImageResource(R.drawable.vatan);
                                	isim = "Asaf Savaş Akat";
                                }
                                else if (s.contains("Ufuk Şanlı"))
                                {
                                	imv.setImageResource(R.drawable.vatan);
                                	isim = "Ufuk Şanlı";
                                }
                                else if (s.contains("Ersin Umdu"))
                                {
                                	imv.setImageResource(R.drawable.vatan);
                                	isim = "Ersin Umdu";
                                }
                               
                              else 
                                {
                                                                                                                                                                                                                        	 
                                      imv.setImageResource(R.drawable.vatan);
                                      }
                                //HÃƒÂ¼rriyet Yazarlar Son       
                                title.setText(isim);
                                date.setText("");
                                description.setText(baslik);
                                
                        }

                       
                        return view;
                }
    }
}
