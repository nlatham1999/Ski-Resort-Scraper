package com.nicklatham.skireport;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class FortyNineDegreesNorth {
    private static boolean selectedYet = false;

    private String snow24;
    private String snow48;
    private Document doc = null;

    public FortyNineDegreesNorth() throws IOException {
        snow24 = "";
        snow48 = "";

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

    public String getSnow24(){
        return snow24;
    }

    public String getSnow48(){
        return snow48;
    }

    private class Sub extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try{
                doc = Jsoup.connect("https://www.ski49n.com/").get();
            }catch(IOException e){
                System.out.println("Uh oh something got fucked. \naccesing the link didnt work");
            }
            if(doc != null){
                Elements list = doc.select("div.day");

                String text = list.get(1).text();
                snow24 = query(text, "24h");
                text = list.get(2).text();
                snow48 = query(text, "48h");

                snow24 = snow24.substring(0, snow24.length()-1);
                snow48 = snow48.substring(0, snow48.length()-1);
//            test(list);
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {

        }
    }
}
