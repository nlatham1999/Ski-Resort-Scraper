package com.nicklatham.skireport;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class NOAA {

    private boolean selectedYet = false;
    private boolean infoAvailable = false;
    private String url;
    private ArrayList<String> info = new ArrayList<>();

    NOAA(String url) throws IOException{
        infoAvailable = false;

        this.url = url;

        new Sub().execute();

    }

    public ArrayList<String> getInfo(){
        return info;
    }

    private void test(Elements list){
        int i = 0;
        for(Element element : list){
            System.out.println(i + " " + element.text());
            i++;
        }
    }


    public boolean isInfoAvailable(){
        return infoAvailable;
    }

    public boolean isSelectedYet(){
        return selectedYet;
    }

    private class Sub extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute () {

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground (Void... params){
          Document doc = null;
        try{
            doc = Jsoup.connect(url).get();
        }catch(IOException e){
            System.out.println("Uh oh something got fucked. \naccesing the link didnt work");
        }
        if(doc != null) {
            Elements list = doc.getElementsByClass("col-sm-10 forecast-text");
            Elements list2 = doc.getElementsByClass("col-sm-2 forecast-label");

            System.out.println("test3");
            //        test(list);
            //        test(list2);

            for (int i = 0; i < list.size(); i++) {
                info.add(list2.get(i).text() + " " + list.get(i).text());
            }
            System.out.println(info);
            infoAvailable = true;
        }
        return null;

        }

        @Override
        protected void onPostExecute (Void result){

        }
    }
}
