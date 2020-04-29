package com.nicklatham.skireport;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.ExecutionException;


public class LookoutPass extends Mountain{
    private static boolean selectedYet = false;
    private static boolean infoAvailable = false;

    private String snow24;
    private String snow48;
    private String tempAtBase;
    private String tempAtSummit;
    private Document doc = null;
    private String news;

    public LookoutPass() throws IOException {

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
        if(input == "IGNORE")
            i=0;
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

    public String getTempAtBase(){
        return tempAtBase;
    }

    public String getTempAtSummit(){
        return tempAtSummit;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    private class Sub extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                doc = SSLHelper.getConnection("https://skilookout.com/snow-report").get();
            } catch (IOException e) {
                System.out.println("Uh oh something got fucked. \naccesing the link didnt work");
            }
            if (doc != null) {
                Elements list = doc.select("div.right");

                String text = list.get(1).text();
                snow24 = query(text, "Summit |");
                text = list.get(2).text();
                snow48 = query(text, "Summit |");
                text = list.get(6).text();
                tempAtBase = text;
                text = list.get(7).text();
                tempAtSummit = text;

                snow24 = snow24.substring(0, snow24.length() - 1);
                snow48 = snow48.substring(0, snow48.length() - 1);

                list = doc.getElementsByClass("open");
                if(list.size() > 2){
                    news = "";
                    for(int i = 2; i < list.size(); i++){
                        news += "\t" + list.get(i).text() + "\n";
                    }
                }
//                tempAtBase = tempAtBase.substring(0, tempAtBase.length() - 1);
//                tempAtSummit = tempAtSummit.substring(0, tempAtSummit.length() - 1);
//            test(list);
            }

            infoAvailable = true;
            return null;

        }

        @Override
        protected void onPostExecute(Void result) {

        }
    }
}
