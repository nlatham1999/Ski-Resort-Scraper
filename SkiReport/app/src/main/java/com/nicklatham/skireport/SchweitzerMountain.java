package com.nicklatham.skireport;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class SchweitzerMountain {
    private static boolean selectedYet = false;

    private String snow24;
    private String snow48;
    private String tempAtVillage;
    private String tempAtSummit;
    private Document doc = null;

    public SchweitzerMountain() throws IOException {
        selectedYet = true;

        try {
            new Sub().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static boolean isSelectedYet(){
        return selectedYet;
    }

    private void test(Elements list){
        int i = 0;
        for(Element element : list){
            System.out.println(i + " " + element.text());
            i++;
        }
    }

    public String getSnow24(){
        return snow24;
    }

    public String getSnow48(){
        return snow48;
    }

    public String getTempAtVillage(){
        return tempAtVillage;
    }

    public String getTempAtSummit(){
        return tempAtSummit;
    }

    private class Sub extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute () {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground (Void... params){
            try{
                doc = Jsoup.connect("https://www.schweitzer.com/explore/snow-report/").get();
            }catch(IOException e){
                System.out.println("Uh oh something got fucked. \naccesing the link didnt work");
            }
            if(doc != null){

                Elements list = doc.select("span");

                snow24 = list.get(13).text();
                snow48 = list.get(17).text();
                tempAtSummit = list.get(52).text();
                tempAtVillage = list.get(57).text();

                snow24 = snow24.substring(0, snow24.length()-1);
                snow48 = snow48.substring(0, snow48.length()-1);
                tempAtSummit = tempAtSummit.substring(0, tempAtSummit.length()-2);
                tempAtVillage = tempAtVillage.substring(0, tempAtVillage.length()-2);

                //test(list);
            }
            return null;

        }

        @Override
        protected void onPostExecute (Void result){

        }
    }

}
