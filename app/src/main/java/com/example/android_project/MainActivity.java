package com.example.android_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android_project.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView mSearchResultsDisplay;
    private EditText mSearchTermEditText;
    private Button mSearchButton;
    private Button mResetButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO access visual elements
        mSearchResultsDisplay = (TextView) findViewById(R.id.display_things);
        mSearchTermEditText = (EditText) findViewById(R.id.et_search_box);
        mSearchButton = (Button) findViewById(R.id.search_button);
        mResetButton = (Button) findViewById(R.id.reset_button);

        mSearchResultsDisplay.setText("\nRESULTS : \n\n");

        // reset button
        mResetButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        mSearchTermEditText.setText("");
                        mSearchResultsDisplay.setText("\nRESULTS : \n\n");
                    }
                }
        );

        // search button
        mSearchButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        makeNetworkSearchQuery();
                    }
                }
        );


    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu); // id of the menu resource that should be inflated
        return true;
    } // end of onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int menuItemSelected = item.getItemId();

        if(menuItemSelected == R.id.menu_about){ // id from main_menu.xml for the About item


            //spl - launching activity in our app - then launch the About Activity
            Class destinationActivity = AboutActivity.class;

            // create intent to go to next page
            Intent startAboutActivityIntent = new Intent(MainActivity.this, destinationActivity);

            String msg = mSearchTermEditText.getText().toString();
            startAboutActivityIntent.putExtra(Intent.EXTRA_TEXT, msg);

            startActivity(startAboutActivityIntent);
            Log.d("info", "About Activity launched");
        } // end if
        return true;
    } // end of onOptions


    public void makeNetworkSearchQuery(){
        String searchTerm = mSearchTermEditText.getText().toString();
        mSearchResultsDisplay.setText("\nRESULTS : \n\n");
        new FetchNetworkData().execute(searchTerm);

    }

    public class FetchNetworkData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params){
            //get the search term
            if(params.length == 0) return null;
            String searchTerm = params[0];
            // get the Url
            URL searchUrl = NetworkUtils.buildAcronymsUrl(searchTerm); // write class and method
            Log.d("debug", "search url in main activity" + searchUrl);

            // get the response from the URl
            String responseString = null;
            try{
                responseString = NetworkUtils.getResponseFromUrl(searchUrl); //  write this method

            }catch(Exception e){
                e.printStackTrace();
            }
            Log.d("debug", "search url string in main activity " + responseString);
            return responseString;//
        }

        @Override
        protected void onPostExecute(String responseData){
            ArrayList<String> titles = NetworkUtils.parseAcronymsJson(responseData); //
            // display entries in GUI
            if (titles.size() == 0){
                mSearchResultsDisplay.append("\nNo results found.");
            }
            for(String title: titles){
                mSearchResultsDisplay.append("\n\n" + title);
            }
        }
    }

}
