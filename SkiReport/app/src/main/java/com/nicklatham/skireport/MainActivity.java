package com.nicklatham.skireport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import static com.nicklatham.skireport.GetInfo.*;

public class MainActivity extends AppCompatActivity {
    protected static TextView silverTextView;
    private TextView schweitzerTextView;
    private TextView lookoutTextView;
    private TextView fortyNineTextView;
    private TextView spokaneTextView;
    private RadioGroup mountainSelectionRadioGroup;
    private Button refreshButton;
    private Spinner dropdown;
    private Button edit;
    private final String[] names = {"Silver Mountain", "Schweitzer Mountain", "Lookout Pass",
            "Forty Nine Degrees North", "Mount Spokane", "Red Mountain", "Click here to add a mountain"}; //list of names on the dropdown
    private ArrayList<String> selections;
    private String savedData;
    private String fileName = "savedData.txt";
//    private SilverMountain silver;
//    private SchweitzerMountain schweitzer;
//    private MountSpokane spokane;
//    private LookoutPass lookout;
//    private FortyNineDegreesNorth fortyNine;
protected static int[] mountainPositions = {0,0,0,0,0,0}; //keeps track of where the different mountains are in the mountains list
    private int current = 0;
    protected static NOAA silverNOAA;
    protected static NOAA lookoutNOAA;
    protected static NOAA schweitzerNOAA;
    protected static NOAA fortyNineNOAA;
    protected static NOAA spokaneNOAA;
    protected static NOAA redMountainNOAA;
    protected static ArrayList<Mountain> mountains;
    private static ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        savedData = "";
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = getApplicationContext().openFileInput("savedData.txt");
            savedData = readFromFileInputStream(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        silverTextView = (TextView) findViewById(R.id.SilverTextView);
        schweitzerTextView = (TextView) findViewById(R.id.schweitzerTextView2);
        lookoutTextView = (TextView) findViewById(R.id.LookoutTextView);
        fortyNineTextView = (TextView) findViewById(R.id.FortyNineTextView);
        spokaneTextView = (TextView) findViewById(R.id.MountSpokaneTextView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mountainSelectionRadioGroup = (RadioGroup) findViewById(R.id.mountainSelectionRadioGroup);
        refreshButton = (Button) findViewById(R.id.refreshButton);
        selections = new ArrayList<>();

        mountains = new ArrayList<Mountain>();

        progressBar.setVisibility(View.GONE);

        setupSelections();

        setupDropdown();

        loadMounains();

        setupMountains();

        setupRefresh();

        setupEditButton();


    }

    private void setupDropdown(){
        dropdown = (Spinner) findViewById(R.id.mountainDropDown);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, names){
            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }
        };
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_text_color);
        dropdown.setAdapter(arrayAdapter);
        dropdown.setSelection(arrayAdapter.getCount());;
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = parent.getItemAtPosition(position).toString();
                if(name != names[names.length-1]) { //dont want to add the prompt
                    if(!selections.contains(name)) { //make sure that the selection isnt already in there
                        selections.add(name);
                        savedData+=name+"\n";
                        FileOutputStream fileOutputStream = null;
                        try {
                            fileOutputStream = getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
                            writeDataToFile(fileOutputStream, savedData);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        dropdown.setSelection(arrayAdapter.getCount());                       setupMountains();
                    }
                }else{
                    dropdown.setSelection(arrayAdapter.getCount());
                }
                int index = parent.getSelectedItemPosition();
                ((TextView) dropdown.getSelectedView()).setTextColor(Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });


    }


    //loads the mountains into the arraylist
    //order is as follows:
    //     silver,schweitzer, lookout, spokane, forty nine,
    //      be really careful before changing this order
    private void loadMounains(){
        int count = 0;
        try {
            mountainPositions[0] = count;
            count++;
            SilverMountain silver = new SilverMountain();
            mountains.add(silver);
            mountains.get(mountains.size()-1).setName("Silver Mountain");
        } catch (IOException e) {
            mountainPositions[0] = -1;
            count--;
            e.printStackTrace();
        }
        try{
            mountainPositions[1] = count;
            count++;
            SchweitzerMountain schweitzer = new SchweitzerMountain();
            mountains.add(schweitzer);
            mountains.get(mountains.size()-1).setName("Schweitzer Mountain");
        } catch (IOException e) {
            e.printStackTrace();
            mountainPositions[1] = -1;
            count--;
        }
        try {
            mountainPositions[2] = count;
            count++;
            LookoutPass lookout = new LookoutPass();
            mountains.add(lookout);
            mountains.get(mountains.size()-1).setName("Lookout Pass");
        } catch (IOException e) {
            e.printStackTrace();
            mountainPositions[2] = -1;
            count--;
        }
        try {
            mountainPositions[3] = count;
            count++;
            MountSpokane spokane = new MountSpokane();
            mountains.add(spokane);
            mountains.get(mountains.size()-1).setName("Mount Spokane");
        } catch (IOException e) {
            e.printStackTrace();
            mountainPositions[3] = -1;
            count--;
        }
        try {
            mountainPositions[4] = count;
            count++;
            FortyNineDegreesNorth fortyNine = new FortyNineDegreesNorth();
            mountains.add(fortyNine);
            mountains.get(mountains.size()-1).setName("Forty Nine Degrees North");
        } catch (IOException e) {
            e.printStackTrace();
            mountainPositions[4] = -1;
            count--;
        }
        try {
            mountainPositions[5] = count;
            count++;
            RedMountain redMountain = new RedMountain();
            mountains.add(redMountain);
        } catch (IOException e) {
            e.printStackTrace();
            mountainPositions[5] = -1;
            count--;
        }
        try {
            silverNOAA = new NOAA("https://forecast.weather.gov/MapClick.php?lat=47.4889049944156&lon=-116.1284065246582#.Uw6QJ_ldXWg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            lookoutNOAA = new NOAA("https://forecast.weather.gov/MapClick.php?lon=-115.67800993099809&lat=47.42499031160827");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            schweitzerNOAA = new NOAA("https://forecast.weather.gov/MapClick.php?lon=-116.65670871734619&lat=48.337081912071");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fortyNineNOAA = new NOAA("https://forecast.weather.gov/MapClick.php?lat=48.28319289548349&lon=-117.57705688476562&site=otx&lg=en");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            spokaneNOAA = new NOAA("https://forecast.weather.gov/MapClick.php?lat=47.923704717745686&lon=-117.11563110351562&site=otx&lg=en");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            redMountainNOAA = new NOAA("https://forecast.weather.gov/MapClick.php?lon=-109.23380208476034&lat=45.18351520413211");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //sets up the refresh button
    private void setupRefresh(){
        refreshButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(selections.size() > current) {
                    if (selections.get(current) == "Silver Mountain") {
                        getSilverInfo();
                    } else if (selections.get(current) == "Schweitzer Mountain") {
                        GetInfo.getSchweitzerInfo();
                    } else if (selections.get(current) == "Lookout Pass") {
                        GetInfo.getLookoutInfo();
                    } else if (selections.get(current) == "Forty Nine Degrees North") {
                        GetInfo.getFortyNineInfo();
                    } else if (selections.get(current) == "Mount Spokane") {
                        GetInfo.getMountSpokaneInfo();
                    } else if (selections.get(current) == "Red Mountain") {
                        GetInfo.getRedMountainInfo();
                    }
                }
            }
        });
    }

    //sets up the radio group buttons
    private void setupMountains(){
        RadioGroup.LayoutParams rprms;
        mountainSelectionRadioGroup.removeAllViews();
        for(int i = 0; i < selections.size(); i++){
            TableRow t = new TableRow(this);
            RadioButton radioButton = new RadioButton(getApplicationContext());
            radioButton.setText(selections.get(i));
            radioButton.setTextColor(Color.WHITE);
            radioButton.setOutlineAmbientShadowColor(Color.WHITE);
            radioButton.setId(View.generateViewId());
            rprms= new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
//            t.addView(radioButton);
//            table.addView(t);
            mountainSelectionRadioGroup.addView(radioButton, rprms);
        }
//        mountainSelectionRadioGroup.addView(radioButton,rprms);

        mountainSelectionRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = mountainSelectionRadioGroup.findViewById(checkedId);
                int idx = mountainSelectionRadioGroup.indexOfChild(radioButton);
//                progressBar.setVisibility(View.GONE);
                current = idx;
                message(selections.get(idx));
                goToSelection(idx);
            }
        });
    }

    private void goToSelection(int idx){
        String text = "";
        if(selections.get(idx) == "Silver Mountain"){
            message("getting silver information...");
            text = getSilverInfo();
        }else if(selections.get(idx) == "Schweitzer Mountain"){
            message("getting schweitzer information...");
            text = getSchweitzerInfo();
        }else if(selections.get(idx)  == "Lookout Pass"){
            text = getLookoutInfo();
            message("getting lookout information...");
        }else if(selections.get(idx) == "Forty Nine Degrees North"){
            text = getFortyNineInfo();
        }else if(selections.get(idx) == "Mount Spokane") {
            message("getting Mount Spokane information...");
            text = getMountSpokaneInfo();
        }else if(selections.get(idx) == "Red Mountain"){
            message("getting Red Mountain information");
            text = getRedMountainInfo();
        }else{
            message("not any of the selctions " + selections.get(idx).length() + ":" + selections.get(idx) + ":");
        }
        Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("text", text);
        intent.putExtras(bundle);
        startActivityForResult(intent, 80);
    }

    private void message(String m){
        Toast toast = Toast.makeText(getApplicationContext(), m, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void setupEditButton(){
        edit = (Button) findViewById(R.id.editMountainsButton);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditMountainsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("selections", selections);
                intent.putExtras(bundle);
                startActivityForResult(intent, 69);
//                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 69){
            savedData = "";
            Bundle bundle = data.getExtras();
            selections = new ArrayList<>();
            ArrayList<String> s = (ArrayList<String>) bundle.getSerializable("selections");
            for(int i = 0; i < s.size(); i++){
                for(int j = 0; j < names.length; j++){
                    if(s.get(i).contains(names[j])) {
                        selections.add(names[j]);
                        savedData += names[j] + "";
                    }
                }
            }
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
                writeDataToFile(fileOutputStream, savedData);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            silverTextView.setText("");

            setupMountains();
        }else if(resultCode  == 70){

        }else if(resultCode == 81){
            goToSelection(current);
        }
    }

    // This method will write data to file.
    private void writeDataToFile(File file, String data)
    {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            this.writeDataToFile(fileOutputStream, data);
            fileOutputStream.close();
        }catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    // This method will write data to FileOutputStream.
    private void writeDataToFile(FileOutputStream fileOutputStream, String data)
    {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            bufferedWriter.write(data);

            bufferedWriter.flush();
            bufferedWriter.close();
            outputStreamWriter.close();
        }catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    // This method will read data from FileInputStream.
    private String readFromFileInputStream(FileInputStream fileInputStream)
    {
        StringBuffer retBuf = new StringBuffer();

        try {
            if (fileInputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String lineData = bufferedReader.readLine();
                while (lineData != null) {
                    retBuf.append(lineData);
                    lineData = bufferedReader.readLine();
                }
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }finally
        {
            return retBuf.toString();
        }
    }

    private void setupSelections(){
        for(int j = 0; j < names.length; j++){
            if(savedData.contains(names[j])) {
                selections.add(names[j]);
            }
        }
    }


}
