package com.example.covid_19tracker_subroto.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.covid_19tracker_subroto.Fragment.Country;
import com.example.covid_19tracker_subroto.R;

public class CountryDetailActivity extends AppCompatActivity {

    private int positionCountry;
    private TextView tvCountry, tvCases, tvRecovered, tvCritical, tvActive, tvTodayCases, tvTotalDeaths, tvTodayDeaths, tvTests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);

        getSupportActionBar().setTitle("Country Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        positionCountry = intent.getIntExtra("i", 0);

        fetchData();
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {

        tvCountry = findViewById(R.id.tvCountry);
        tvCases = findViewById(R.id.tvCases);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvCritical = findViewById(R.id.tvCritical);
        tvActive = findViewById(R.id.tvActive);
        tvTodayCases = findViewById(R.id.tvTodayCases);
        tvTotalDeaths = findViewById(R.id.tvDeaths);
        tvTodayDeaths = findViewById(R.id.tvTodayDeaths);
        tvTests = findViewById(R.id.tvTest);


        tvCountry.setText(Country.countryModelsList.get(positionCountry).getCountry());
        tvCases.setText(Country.countryModelsList.get(positionCountry).getCases());
        tvRecovered.setText(Country.countryModelsList.get(positionCountry).getRecovered());
        tvCritical.setText(Country.countryModelsList.get(positionCountry).getCritical());
        tvActive.setText(Country.countryModelsList.get(positionCountry).getActive());
        tvTodayCases.setText(Country.countryModelsList.get(positionCountry).getTodayCases());
        tvTotalDeaths.setText(Country.countryModelsList.get(positionCountry).getDeaths());
        tvTodayDeaths.setText(Country.countryModelsList.get(positionCountry).getTodayDeaths());
        tvTests.setText(Country.countryModelsList.get(positionCountry).getTests());
    }
}
