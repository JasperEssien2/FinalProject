package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.android.displayjokelib.JokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = new ProgressBar((ImageView) findViewById(R.id.progressbar), this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is called when the user clicks on the tell joke button
     *
     * @param view
     */
    public void tellJoke(View view) {
        progressBar.startLoading();
        new EndPointAsyncTask(new CallBacks() {
            @Override
            public void onPostExecuteCallback(String s) {
                onCallBackCalled(s);
            }
        }).execute();
    }

    /**
     * This method is called when EndPointAsyncTask done executing and onPostExecuteCallback is called
     * it performs the action of starting a new activity passing in the joke string
     *
     * @param s joke String value
     */
    private void onCallBackCalled(String s) {
        this.progressBar.stopLoading();
        Intent intent = new Intent(this, JokeActivity.class);
        intent.putExtra("Joke", s);
        startActivity(intent);
    }

    /**
     * AsyncTask to perform getting Joke from the server
     */
    static class EndPointAsyncTask extends AsyncTask<Void, Void, String> {
        private MyApi myApiService = null;

        private CallBacks callbacks;

        EndPointAsyncTask(CallBacks callbacks) {

            this.callbacks = callbacks;
        }

        @Override
        protected String doInBackground(Void... voids) {
            if (myApiService == null) {
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end options for devappserver

                myApiService = builder.build();

            }

            try {

                return myApiService.getJoke().execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (callbacks != null)
                callbacks.onPostExecuteCallback(s);
        }
    }

    /**
     * An interface for EndPointsAsyncTask .. its implementation is passed to the EndPointsAsyncTask
     * constructor.. and its method called when the background task is done
     */
    public interface CallBacks {
        void onPostExecuteCallback(String s);
    }
}
