package com.nicklatham.skireport;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class SilverMountain extends Mountain{

    private static boolean selectedYet = false;
    private static boolean infoAvailable = false;

    private static String snow24;
    private static String snow48;
    private static String tempAtMountainHouse;
    private static String tempAtKellogPeak;
    private static String announcements;
    private static Document doc;


    public SilverMountain() throws IOException
    {
        selectedYet = true;
        infoAvailable = false;
        doc = null;
//        try {
//            new Sub().execute().get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        new Sub().execute();


    }

    public static boolean isInfoAvailable(){
        return infoAvailable;
    }

    public static boolean isSelectedYet(){
        return selectedYet;
    }

    public String getSnow24(){
        return snow24;
    }

    public String getSnow48(){
        return snow48;
    }

    public String getTempAtMountainHouse(){
        return tempAtMountainHouse;
    }

    public String getTempAtKellogPeak(){
        return tempAtKellogPeak;
    }

    public String getAnnouncements(){return announcements;}

    //asysnc function that runs in the background
    static private class Sub extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute () {

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground (Void... params){
//            MainActivity.setProgress(true);
            try {
                doc = Jsoup.connect("https://www.silvermt.com/snow-report").get();
            } catch (IOException e) {
                System.out.println("Uh oh something got fucked. \naccesing the link didnt work");
            }
            if (doc != null) {
                Element temp = doc.getElementById("dnn_ctr708_FullView_lblSnowLast24Hr");
                snow24 = temp.text();

                temp = doc.getElementById("dnn_ctr708_FullView_lblSnowLast48Hr");
                snow48 = temp.text();

                temp = doc.getElementById("dnn_ctr708_FullView_lblTempCurrentMtHaus");
                tempAtMountainHouse = temp.text();

                temp = doc.getElementById("dnn_ctr708_FullView_lblTempCurrentKelloggPeak");
                tempAtKellogPeak = temp.text();

                temp = doc.getElementById("dnn_ctr708_FullView_lblNoteISAAComments");
                announcements = temp.text();
            } else{
                snow24 = "NULL";
                snow48 = "NULL";
                tempAtMountainHouse = "NULL";
                tempAtKellogPeak = "NULL";
            }
            infoAvailable = true;
//            MainActivity.setProgress(false);
            return null;

        }

        @Override
        protected void onPostExecute (Void result){

        }
    }

}