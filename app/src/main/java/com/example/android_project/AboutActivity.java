package com.example.android_project;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    private TextView mDisplayAboutTextView;
    private Button mOpenWebpageButton;
    private TextView mShowAbbreviations;
    private Button mOpenAcronyms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // connect with UI elements
        mDisplayAboutTextView = (TextView) findViewById(R.id.tv_about_text);
        mOpenWebpageButton = (Button) findViewById(R.id.button_open_webpage);
        mShowAbbreviations = (TextView) findViewById(R.id.show_abbreivations);
        mOpenAcronyms = (Button) findViewById(R.id.button_open_acronyms);


        final String[] abbreivations = {"AKA", "IRB", "MOM", "IBM", "HI", "FBI", "NASA", "ND", "NYPD", "MIA", "ASAP", "TMI", "SOS"};
        for (String abbr : abbreivations){
            mShowAbbreviations.append(" - " + abbr + "\n");
        }

        final String urlSource = "http://www.nactem.ac.uk/software/acromine/"; // url string for API source
        final String abrevSource = "https://acronyms.thefreedictionary.com"; // url string for other popular acronyms
        // open webpage button
        mOpenWebpageButton.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        openWebPage(urlSource);
                    } // end of onClick method

                } // end of View
        ); // end of setOnClickListener

        mOpenAcronyms.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        openWebPage(abrevSource);
                    } // end of onClick method
                } // end of View
        ); // end of setOnClickListener

    } // end of onCreate method


    public void openWebPage(String urlString){
        Uri webpage = Uri.parse(urlString);

        Intent openWebPageIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(openWebPageIntent);
    } // end of open web page

} // end of class AboutActivity
