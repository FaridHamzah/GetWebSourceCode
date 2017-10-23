package com.farid.www.myapplication;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    Spinner spenner;
    ArrayAdapter<CharSequence> Finnaly;
    EditText Link_URL;
    TextView webpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spenner = (Spinner)findViewById(R.id.spenner);
        Link_URL = (EditText)findViewById(R.id.URL);
        webpage = (TextView)findViewById(R.id.finaly);

        Finnaly= ArrayAdapter.createFromResource(this, R.array.model, android.R.layout.simple_spinner_item);
        Finnaly.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spenner.setAdapter(Finnaly);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                Log.e("Error" + Thread.currentThread().getStackTrace()[2], paramThrowable.getLocalizedMessage());
            }
        });

        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new Activity_adapter(this,args.getString("url_link"));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        webpage.setText(data);

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
    }
    public void mantap(View view) {
        String protokol , urls;
        protokol = spenner.getSelectedItem().toString();
        urls = Link_URL.getText().toString();

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        if (checkConnection()){
            webpage.setText("Loading");
            Bundle bundle = new Bundle();
            bundle.putString("url_link", protokol+urls);
            getSupportLoaderManager().restartLoader(0, bundle, this);
        } else {
            webpage.setText("No Connection");
        }
    }
    public boolean checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
}
