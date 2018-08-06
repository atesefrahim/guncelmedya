package com.bedates.guncelhaberler;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;



@SuppressLint("ParserError")
public class rssreader extends Activity{

	ArrayList<String> xmlList=new ArrayList<String>();
	ArrayList<String> xmlLink=new ArrayList<String>();	
public class arkaPlanIsleri extends  AsyncTask<Void, Void, Void> {
	
	 private ProgressDialog dialog = new ProgressDialog(rssreader.this);
	
	 @Override
	 protected void onPostExecute(Void result) {
	  // TODO Auto-generated method stub
	  ListView listView1 = (ListView)findViewById(R.id.list);
	  MyCustomAdapter adapter = new MyCustomAdapter(rssreader.this, R.layout.list, xmlList);
	  listView1.setAdapter(adapter);
	  
	  dialog.dismiss();
	 }

	 @Override
	 protected void onPreExecute() {
	  // TODO Auto-generated method stub
		dialog.setMessage("Yükleniyor...");
	
	 	dialog.show();
	 }
	
	 @Override
	 protected Void doInBackground(Void... params) {
		 
	  // TODO Auto-generated method stub
		 xmlList=getListFromXml(haber.adres);
		 xmlLink=getLinkFromXml(haber.adres);
		 
		 
	  return null;
	 }

}
	
public class MyCustomAdapter extends ArrayAdapter<String> {

public MyCustomAdapter(Context context, int textViewResourceId,
  ArrayList<String> xmlList) {
 super(context, textViewResourceId, xmlList);
 // TODO Auto-generated constructor stub

}

@Override
public View getView(int position, View convertView, ViewGroup parent) {
 // TODO Auto-generated method stub
 //return super.getView(position, convertView, parent);

 View row = convertView;
 
 
 if(row==null){
  LayoutInflater inflater=getLayoutInflater();
  row=inflater.inflate(R.layout.list, parent, false);
 }

 TextView label=(TextView)row.findViewById(R.id.text1);
 label.setText(xmlList.get(position));
 
 ImageView image =(ImageView)row.findViewById(R.id.img);
 image.setImageResource(R.drawable.haber);
/* try{
 Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL("http://www.hurriyet.com.tr/_yazarlar/images/232b.jpg").getContent());
 image.setImageBitmap(bitmap); 
} catch (MalformedURLException e) {
	 //log exception here
	}
 catch (IOException e) {
	  //log exception here
	}
*/
 return row;
}
}

/** Called when the activity is first created. */
@SuppressLint("ParserError")
@Override
	 public void onCreate(Bundle savedInstanceState) {
	
	 super.onCreate(savedInstanceState);
	 setContentView(R.layout.rssreader);
	
	 new arkaPlanIsleri().execute();
	 
	 ListView listView1 = (ListView)findViewById(R.id.list);
	
	 listView1.setOnItemClickListener(new OnItemClickListener() {

	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
		 
		Uri link = Uri.parse(xmlLink.get(position));
		  
		Intent i = new Intent(getApplicationContext(),UsingMyWebview.class);
		UsingMyWebview.adres  = link.toString();
		startActivity(i);

	}

	});
}

// TODO Auto-generated method stub
//super.onListItemClick(l, v, position, id);

public ArrayList<String> getListFromXml(String strng)  {

	ArrayList<String> list=new ArrayList<String>();
	
	try {

		URL url=new URL(strng);
		DocumentBuilderFactory dFactory=DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder=dFactory.newDocumentBuilder();

		Document document=dBuilder.parse(new InputSource(url.openStream()));
		document.getDocumentElement().normalize();
		
		NodeList nodeListCountry=document.getElementsByTagName("item");
		for (int i = 0; i < nodeListCountry.getLength(); i++) {
			Node node=nodeListCountry.item(i);
			Element elementMain=(Element) node;

			NodeList nodeListText=elementMain.getElementsByTagName("title");
			Element elementText=(Element) nodeListText.item(0);
			
			list.add(elementText.getChildNodes().item(0).getNodeValue());
			
			
		}
		
	} catch (MalformedURLException e) {
		e.printStackTrace();
	}catch (ParserConfigurationException e) {
		e.printStackTrace();
	} catch (SAXException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}

	
	return list;
}

public ArrayList<String> getLinkFromXml(String strng)  {

	ArrayList<String> list=new ArrayList<String>();
	
	try {

		URL url=new URL(strng);
		DocumentBuilderFactory dFactory=DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder=dFactory.newDocumentBuilder();

		Document document=dBuilder.parse(new InputSource(url.openStream()));
		document.getDocumentElement().normalize();
		
		NodeList nodeListCountry=document.getElementsByTagName("item");
		for (int i = 0; i < nodeListCountry.getLength(); i++) {
			Node node=nodeListCountry.item(i);
			Element elementMain=(Element) node;

			NodeList nodeListText=elementMain.getElementsByTagName("link");
			Element elementText=(Element) nodeListText.item(0);
			
			list.add(elementText.getChildNodes().item(0).getNodeValue());
			
			
		}
		
	} catch (MalformedURLException e) {
		e.printStackTrace();
	}catch (ParserConfigurationException e) {
		e.printStackTrace();
	} catch (SAXException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}

	
	return list;
}
}