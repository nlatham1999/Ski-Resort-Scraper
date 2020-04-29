package com.nicklatham.skireport;

import android.view.View;

import java.io.IOException;
import java.util.ArrayList;

public class GetInfo extends MainActivity {
    protected static String getSilverInfo(){
        if(mountainPositions[0] == -1)
            return "";
        try {
            SilverMountain silver = (SilverMountain) mountains.get(mountainPositions[0]);
            if (!silver.isSelectedYet()) {
//                progressBar.setVisibility(View.VISIBLE);
                silver = new SilverMountain();
            }

            String temp = "";
            if(silver.isInfoAvailable() || silverNOAA.isInfoAvailable()) {
                if (silver.isInfoAvailable()) {
                    temp += "Silver Mountain:\n";
                    temp += "snow within last 24 hours: " + silver.getSnow24() + "\"\n";
                    temp += "snow within last 48 hours: " + silver.getSnow48() + "\"\n";
                    temp += "temperature at the mountain house " + silver.getTempAtMountainHouse() + "˚F \n";
                    temp += "temperature at Kellog Peak " + silver.getTempAtKellogPeak() + "˚F \n";
                    temp += "\nAnouncements:\n";
                    temp += silver.getAnnouncements();
                }
                if (silverNOAA != null && silverNOAA.isInfoAvailable()) {
                    //                progressBar.setVisibility(View.VISIBLE);
                    temp += "\n\nNOAA Forecast \n\n";
//                    temp += Html.fromHtml(silverNOAA.getInfo(), HtmlCompat.FROM_HTML_MODE_LEGACY);
                    ArrayList<String> info = silverNOAA.getInfo();
                    for(int i = 0; i < info.size(); i++){
                        temp += "\n" + info.get(i) + "\n";
                    }
                }
            }else{
//                progressBar.setVisibility(View.VISIBLE);
            }
//            silverTextView.setText(temp);
            return temp;
        }catch(IOException e){
            System.out.println("Silver exception");
        }
        return "";
    }

    protected static String getSchweitzerInfo(){
        if(mountainPositions[1] == -1)
            return "";
        SchweitzerMountain schweitzer = (SchweitzerMountain) mountains.get(mountainPositions[1]);
        try {
            if(!schweitzer.isSelectedYet()) {
                schweitzer = new SchweitzerMountain();
            }

            String temp = "";
            temp += "Schweitzer Mountain:\n";
            temp += "snow within last 24 hours: " + schweitzer.getSnow24() + "\"\n";
            temp += "snow within last 48 hours: " + schweitzer.getSnow48() + "\"\n";
            temp += "temperature at the village " + schweitzer.getTempAtVillage() + "˚F \n";
            temp += "temperature at the summit " + schweitzer.getTempAtSummit() + "˚F \n";
            temp += "\nNews\n\n" + schweitzer.getNews();

            if (schweitzerNOAA != null && schweitzerNOAA.isInfoAvailable()) {
                temp += "\n\nNOAA Forecast \n\n";
                ArrayList<String> info = schweitzerNOAA.getInfo();
                for(int i = 0; i < info.size(); i++){
                    temp += "\n" + info.get(i) + "\n";
                }
            }

//            silverTextView.setText(temp);
            return temp;
        }catch(IOException e){
            System.out.println("Schweitzer catch");
        }
        return "";
    }

    protected static String getMountSpokaneInfo(){
        if(mountainPositions[3] == -1)
            return "";
        MountSpokane spokane = (MountSpokane) mountains.get(mountainPositions[3]);
        try {
            if(!spokane.isSelectedYet()){
                spokane = new MountSpokane();
            }
            String temp = "";
            temp += "Mount Spokane:\n";
            temp += "snow within last 24 hours: " + spokane.getSnow24() + "\"\n";
            temp += "snow within last 48 hours: " + spokane.getSnow48() + "\"\n";
            temp += "temperature at the lodge: " + spokane.getTempLodge() + '\n';
            temp += "temperature at the summit: " + spokane.getTempSummit() + '\n';
            if (spokaneNOAA != null && spokaneNOAA.isInfoAvailable()) {
                temp += "\n\nNOAA Forecast \n\n";
                ArrayList<String> info = spokaneNOAA.getInfo();
                for(int i = 0; i < info.size(); i++){
                    temp += "\n" + info.get(i) + "\n";
                }
            }
//            silverTextView.setText(temp);
            return temp;
        }catch(IOException e){
            System.out.println("spokane catch");
        }
        return "";
    }

    protected static String getLookoutInfo(){
        if(mountainPositions[2] == -1)
            return "";
        LookoutPass lookout = (LookoutPass) mountains.get(mountainPositions[2]);
        try {
            if(!lookout.isSelectedYet()) {
                lookout = new LookoutPass();
            }

            String temp = "";
            temp += "Lookout Mountain:\n";
            temp += "snow within last 24 hours: " + lookout.getSnow24() + "\"\n";
            temp += "snow within last 48 hours: " + lookout.getSnow48() + "\"\n";
            temp += "temperature at the base " + lookout.getTempAtBase() + "˚F \n";
            temp += "temperature at the summit " + lookout.getTempAtSummit() + "˚F \n";
            temp += "\nNews\n\n" + lookout.getNews() + "\n";

            if (lookoutNOAA != null && lookoutNOAA.isInfoAvailable()) {
                temp += "\n\nNOAA Forecast \n\n";
                ArrayList<String> info = lookoutNOAA.getInfo();
                for(int i = 0; i < info.size(); i++){
                    temp += "\n" + info.get(i) + "\n";
                }
            }

//            silverTextView.setText(temp);
            return temp;
        }catch(IOException e){
            System.out.println("lookout catch");
        }
        return "";
    }

    protected static String getFortyNineInfo(){
        if(mountainPositions[4] == -1)
            return "";
        FortyNineDegreesNorth fortyNine = (FortyNineDegreesNorth) mountains.get(mountainPositions[4]);
        try {
            if(!fortyNine.isSelectedYet()) {
                fortyNine = new FortyNineDegreesNorth();
            }

            String temp = "";
            temp += "Forty Nine Degrees North:\n";
            temp += "snow within last 24 hours: " + fortyNine.getSnow24() + "\"\n";
            temp += "snow within last 48 hours: " + fortyNine.getSnow48() + "\"\n";
            temp += "temperature: " + fortyNine.getTemperature() + "\n";

            if (fortyNineNOAA != null && fortyNineNOAA.isInfoAvailable()) {
                temp += "\n\nNOAA Forecast \n\n";
                ArrayList<String> info = fortyNineNOAA.getInfo();
                for(int i = 0; i < info.size(); i++){
                    temp += "\n" + info.get(i) + "\n";
                }
            }

//            silverTextView.setText(temp);
            return temp;
        }catch(IOException e){
            System.out.println("forty catch");
        }
        return "";
    }

    protected static String getRedMountainInfo(){
        if(mountainPositions[5] == -1)
            return "";
        RedMountain redMountain = (RedMountain) mountains.get(mountainPositions[5]);
//        FortyNineDegreesNorth fortyNine = (FortyNineDegreesNorth) mountains.get(mountainPositions[4]);
        try {
            if(!redMountain.isSelectedYet()) {
                redMountain = new RedMountain();
            }

            String temp = "";
            temp += "Red Mountain:\n";
            temp += "snow within last 24 hours: " + redMountain.getSnow24() + "\"\n";
            temp += "snow within last 48 hours: " + redMountain.getSnow48() + "\"\n";
            temp += "temperature at lodge: " + redMountain.getTempLodge() + "\n";
            temp += "temperature at Summit: " + redMountain.getTempSummit() + "\n";
            temp += "news: \n" + redMountain.getNews() + "\n";

            if (redMountainNOAA != null && redMountainNOAA.isInfoAvailable()) {
                temp += "\n\nNOAA Forecast \n\n";
                ArrayList<String> info = redMountainNOAA.getInfo();
                for(int i = 0; i < info.size(); i++){
                    temp += "\n" + info.get(i) + "\n";
                }
            }

//            silverTextView.setText(temp);
            return temp;
        }catch(IOException e){
            System.out.println("forty catch");
        }
        return "";
    }

}
