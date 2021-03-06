package com.nicklatham.skireport;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MountSpokane extends Mountain{
    private static boolean selectedYet = false;
    private static boolean infoAvailable = false;

    private String snow24;
    private String snow48;
    private Document doc = null;
    private String tempLodge;
    private String tempSummit;

    public MountSpokane() throws IOException {
        snow24 = "";
        snow48 = "";

        selectedYet = true;
        infoAvailable = false;

//        try {
//            new Sub().execute().get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        new Sub().execute();
    }

    public static boolean isSelectedYet(){
        return selectedYet;
    }

    private String query(String text, String input){
        int i = text.indexOf(input);
        String result = "";
        if(i != -1){
            i += input.length();
            while(text.charAt(i) == ' '){
                i++;
            }
            while(text.charAt(i) != ' '){
                result += text.charAt(i);
                i++;
            }
        }
        return result;

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

    public String getTempLodge() {
        return tempLodge;
    }

    public String getTempSummit() {
        return tempSummit;
    }

    private class Sub extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute () {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground (Void... params){
            try{
                doc = Jsoup.connect("https://www.mtspokane.com/mountain-conditions/").get();
            }catch(IOException e){
                System.out.println("Uh oh something got fucked. \naccesing the link didnt work");
            }
            if(doc != null) {

                Elements list = doc.select("div.conditions--inner");

                String text = list.get(2).text();
                snow24 = query(text, "NEW SNOW IN LAST 24 HOURS:");
                snow48 = query(text, "NEW SNOW IN LAST 48 HOURS:");
                text = list.get(1).text();
                tempLodge = query(text, "Lodge Temp:");
                tempSummit = query(text, "Summit Temp:");

//        test(list);
            }

            infoAvailable = true;
            return null;

        }

        @Override
        protected void onPostExecute (Void result){

        }
    }
}
