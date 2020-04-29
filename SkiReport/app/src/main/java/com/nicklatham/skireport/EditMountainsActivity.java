package com.nicklatham.skireport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EditMountainsActivity extends AppCompatActivity {

    private ArrayList<String> selections;
    private ArrayList<String> selectionsOriginal;
    private RadioGroup mountainRadioGroup;
    private Button cancel;
    private Button save;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mountains);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        selections = (ArrayList<String>) bundle.getSerializable("selections");
        selectionsOriginal = (ArrayList<String>) bundle.getSerializable("selections");
        String text = "";
        for(int i = 0; i < selections.size(); i++){
            text += selections.get(i) + "\n";
        }
//        TextView textView = findViewById(R.id.editMountainsTextView);
//        textView.setText(text);

        mountainRadioGroup = (RadioGroup) findViewById(R.id.editMountainsRadioGroup);
        setupRadioGroup();

        setupCancelButton();

        setupSaveButton();

        textView = (TextView) findViewById((R.id.editMountainsTextView));
        textView.setText("Select a mountain to remove it from the main page");
        textView.setTextColor(Color.WHITE);

    }

    private void setupCancelButton(){
        cancel = (Button) findViewById(R.id.editMountainCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("selections", selectionsOriginal);
                intent.putExtras(bundle);
                setResult(70, intent);
//                Toast toast = Toast.makeText(getApplicationContext(), selectionsOriginal.size() , Toast.LENGTH_SHORT);
//                toast.show();
                finish();
            }
        });
    }

    private void setupSaveButton(){
        save = (Button) findViewById(R.id.editMountainSave);
        save.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("selections", selections);
                intent.putExtras(bundle);
                setResult(69, intent);
                finish();
            }
        }));
    }

    private void setupRadioGroup(){
        RadioGroup.LayoutParams rprms;
        mountainRadioGroup.removeAllViews();
        for(int i = 0; i < selections.size(); i++){
            TableRow t = new TableRow(this);
            RadioButton radioButton = new RadioButton(getApplicationContext());
            radioButton.setText(selections.get(i));
            radioButton.setTextColor(Color.WHITE);
            radioButton.setId(View.generateViewId());
            rprms= new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
//            t.addView(radioButton);
//            table.addView(t);
            mountainRadioGroup.addView(radioButton, rprms);
        }

        mountainRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = mountainRadioGroup.findViewById(checkedId);
                int idx = mountainRadioGroup.indexOfChild(radioButton);
                selections.remove(idx);
                setupRadioGroup();
            }
        });
    }
}
