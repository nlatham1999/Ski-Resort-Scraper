package com.nicklatham.skireport;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class RedMountain extends Mountain{
    private String snow24;
    private String snow48;
    private String tempLodge;
    private String tempSummit;
    private String news;
    private Boolean selectedYet = false;

    public RedMountain() throws IOException {
        selectedYet = true;
        snow24 = " ";
        snow48 = " ";

        new Sub().execute();
    }

    public String getSnow24(){
        return snow24;
    }

    public String getSnow48(){
        return snow48;
    }

    public String getTempSummit() {
        return tempSummit;
    }

    public String getTempLodge() {
        return tempLodge;
    }

    public String getNews() {
        return news;
    }

    public boolean isSelectedYet(){
        return selectedYet;
    };

    private String query(String text, String input){
        int i = text.indexOf(input);
        String result = "";
        if(i != -1){
            i += input.length();
            while(text.charAt(i) == ' '){
                i++;
            }
            while(i < text.length() && text.charAt(i) != ' '){
                result += text.charAt(i);
                i++;
            }
        }
        return result;

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
                doc = Jsoup.connect("https://www.redresort.com/mountain/report/").get();
            }catch(IOException e){
                System.out.println("Uh oh something got fucked. \naccesing the link didnt work");
                snow24  =  "didn't work";
            }
            if(doc != null){
                snow24  =  "test two";
                Elements list = doc.select("h2");
                if(list.size() > 0)
                    tempLodge = query(list.get(0).text(), "/");
                if(list.size() > 1)
                    tempSummit = query(list.get(1).text(), "/");
                list = doc.select("td");
                if(list.size() > 77)
                    snow24 = query(list.get(77).text(), "/");
                    if(snow24.length() > 0)
                        snow24 = snow24.substring(0, snow24.length()-2);
                if(list.size() > 79)
                    snow48 = query(list.get(79).text(), "/");
                    if(snow48.length() > 0)
                        snow48 = snow48.substring(0, snow48.length()-2);
                list = doc.select("p");
                if(list.size() > 4)
                    news = list.get(4).text();
//            test(list);
            }
            return null;
        }

        @Override
        protected void onPostExecute (Void result){

        }
    }
}


