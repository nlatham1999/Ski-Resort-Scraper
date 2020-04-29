package com.nicklatham.skireport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {

    private TextView textView;
    private String text;
    private Button returnButton;
    private Button refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        text = (String) bundle.getSerializable("text");

        textView = (TextView) findViewById(R.id.infoFragmentTextView);
        textView.setTextColor(Color.WHITE);
        textView.setText(text);

        returnButton = (Button) findViewById(R.id.infoFragmentReturnHome);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(79, intent);
                finish();
            }
        });

        refresh = (Button) findViewById(R.id.infoFragmentRefresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(81, intent);
                finish();
            }
        });

    }
}
