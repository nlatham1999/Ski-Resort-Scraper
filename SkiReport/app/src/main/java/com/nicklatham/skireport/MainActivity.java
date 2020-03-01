package com.nicklatham.skireport;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private TextView silverTextView;
    private TextView schweitzerTextView;
    private TextView lookoutTextView;
    private TextView fortyNineTextView;
    private TextView spokaneTextView;
    private RadioGroup mountainSelectionRadioGroup;
    private Button refreshButton;
    private final String[] names = {"Silver Mountain", "Schweitzer Mountain", "Lookout Mountain",
            "Forty Nine Degrees North", "Mount Spokane"};
    private SilverMountain silver;
    private SchweitzerMountain schweitzer;
    private MountSpokane spokane;
    private LookoutPass lookout;
    private FortyNineDegreesNorth fortyNine;
    private int current = 0;

//    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        silverTextView = (TextView) findViewById(R.id.SilverTextView);
        schweitzerTextView = (TextView) findViewById(R.id.schweitzerTextView2);
        lookoutTextView = (TextView) findViewById(R.id.LookoutTextView);
        fortyNineTextView = (TextView) findViewById(R.id.FortyNineTextView);
        spokaneTextView = (TextView) findViewById(R.id.MountSpokaneTextView);
//        progressBar = (ProgressBar) findViewById(R.id.silverProgressBar);
        mountainSelectionRadioGroup = (RadioGroup) findViewById(R.id.mountainSelectionRadioGroup);
        refreshButton = (Button) findViewById(R.id.refreshButton);

        setupMountains();

        setupRefresh();


    }

    private void setupRefresh(){
        refreshButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(names[current] == "Silver Mountain"){
                    getSilverInfo();
                }else if(names[current] == "Schweitzer Mountain"){
                    getSchweitzerInfo();
                }else if(names[current]  == "Lookout Mountain"){
                    getLookoutInfo();
                }else if(names[current] == "Forty Nine Degrees North"){
                    getFortyNineInfo();
                }else if(names[current] == "Mount Spokane"){
                    getMountSpokaneInfo();
                }
            }
        });
    }

    private void setupMountains(){
        RadioGroup.LayoutParams rprms;



        for(int i = 0; i < names.length; i++){
            RadioButton radioButton = new RadioButton(getApplicationContext());
            radioButton.setText(names[i]);
            radioButton.setId(View.generateViewId());
            rprms= new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            mountainSelectionRadioGroup.addView(radioButton,rprms);
        }

        mountainSelectionRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = mountainSelectionRadioGroup.findViewById(checkedId);
                int idx = mountainSelectionRadioGroup.indexOfChild(radioButton);

                current = idx;
                if(names[idx] == "Silver Mountain"){
                    getSilverInfo();
                }else if(names[idx] == "Schweitzer Mountain"){
                    getSchweitzerInfo();
                }else if(names[idx]  == "Lookout Mountain"){
                    getLookoutInfo();
                }else if(names[idx] == "Forty Nine Degrees North"){
                    getFortyNineInfo();
                }else if(names[idx] == "Mount Spokane"){
                    getMountSpokaneInfo();
                }
            }
        });
    }

    private void getSilverInfo(){
        try {
            if(!silver.isSelectedYet()){
                silver = new SilverMountain();
            }

            String temp = "";
            temp += "Silver Mountain:\n";
            temp += "snow within last 24 hours: " + silver.getSnow24() + "\"\n";
            temp += "snow within last 48 hours: " + silver.getSnow48() + "\"\n";
            temp += "temperature at the mountain house " + silver.getTempAtMountainHouse() + "˚F \n";
            temp += "temperature at Kellog Peak " + silver.getTempAtKellogPeak() + "˚F \n";
            temp += "\nAnouncements:\n";
            temp += silver.getAnnouncements();
            silverTextView.setText(temp);
        }catch(IOException e){
            System.out.println("Silver exception");
        }
    }

    private void getSchweitzerInfo(){
        try {
            if(!schweitzer.isSelectedYet()) {
                schweitzer = new SchweitzerMountain();
            }
            String temp = "";
            temp += "Schweitzer Mountain:\n";
            temp += "snow within last 24 hours: " + schweitzer.getSnow24() + '\n';
            temp += "snow within last 48 hours: " + schweitzer.getSnow48() + '\n';
            temp += "temperature at the village " + schweitzer.getTempAtVillage() + "˚F \n";
            temp += "temperature at the summit " + schweitzer.getTempAtSummit() + "˚F \n";
            silverTextView.setText(temp);

        }catch(IOException e){
            System.out.println("Schweitzer catch");
        }

    }

    private void getMountSpokaneInfo(){
        try {
            if(!spokane.isSelectedYet()){
                spokane = new MountSpokane();
            }
            String temp = "";
            temp += "Mount Spokane:\n";
            temp += "snow within last 24 hours: " + spokane.getSnow24() + '\n';
            temp += "snow within last 48 hours: " + spokane.getSnow48() + '\n';
            silverTextView.setText(temp);
        }catch(IOException e){
            System.out.println("spokane catch");
        }
    }

    private void getLookoutInfo(){
        try {
            if(!lookout.isSelectedYet()) {
                lookout = new LookoutPass();
            }

            String temp = "";
            temp += "Lookout Mountain:\n";
            temp += "snow within last 24 hours: " + lookout.getSnow24() + '\n';
            temp += "snow within last 48 hours: " + lookout.getSnow48() + '\n';
            temp += "temperature at the base " + lookout.getTempAtBase() + "˚F \n";
            temp += "temperature at the summit " + lookout.getTempAtSummit() + "˚F \n";
            silverTextView.setText(temp);

        }catch(IOException e){
            System.out.println("lookout catch");
        }
    }

    private void getFortyNineInfo(){
        try {
            if(!fortyNine.isSelectedYet()) {
                fortyNine = new FortyNineDegreesNorth();
            }

            String temp = "";
            temp += "Forty Nine Degrees North:\n";
            temp += "snow within last 24 hours: " + fortyNine.getSnow24() + '\n';
            temp += "snow within last 48 hours: " + fortyNine.getSnow48() + '\n';
            silverTextView.setText(temp);

        }catch(IOException e){
            System.out.println("forty catch");
        }
    }
}
