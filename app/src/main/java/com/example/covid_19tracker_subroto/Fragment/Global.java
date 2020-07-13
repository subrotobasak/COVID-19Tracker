package com.example.covid_19tracker_subroto.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_19tracker_subroto.InternetCheck.InternetCheck;
import com.example.covid_19tracker_subroto.R;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class Global extends Fragment {

    private TextView tvCases, tvRecovered, tvCritical, tvActive, tvTodayCases, tvTotalDeaths, tvTodayDeaths, tvAffectedCountries;
    private SimpleArcLoader simpleArcLoader;
    private ScrollView scrollView;
    private PieChart pieChart;
    private View view;


    public Global() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_global, container, false);

        initViews();

        InternetCheck internetCheck = new InternetCheck();
        if (internetCheck.isInternetOn(getActivity()) == false) {
            Toast.makeText(getContext(), "Please Check Your Internet!", Toast.LENGTH_SHORT).show();
            simpleArcLoader.stop();
            simpleArcLoader.setVisibility(View.GONE);
        } else {

            fetchData();
        }
        return view;
    }

    private void initViews() {
        //Global Details

        tvCases = view.findViewById(R.id.tvCasesId);
        tvRecovered = view.findViewById(R.id.tvRecoveredId);
        tvCritical = view.findViewById(R.id.tvCriticalId);
        tvActive = view.findViewById(R.id.tvActiveId);
        tvTodayCases = view.findViewById(R.id.tvTodayCaseId);
        tvTotalDeaths = view.findViewById(R.id.tvTotalDeathsId);
        tvTodayDeaths = view.findViewById(R.id.tvTodayDeathsId);
        tvAffectedCountries = view.findViewById(R.id.tvAffectedCountriesId);

        //PieChart
        pieChart = view.findViewById(R.id.pieChartId);

        //ArcLoader
        simpleArcLoader = view.findViewById(R.id.loader);

        //Scroll View

        scrollView = view.findViewById(R.id.scrollStatsId);

        //fetchData();

    }


    private void fetchData() {

        // Corona Update Api
        String url = "https://corona.lmao.ninja/v2/all";

        simpleArcLoader.start();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());

                            //Set all data
                            tvCases.setText(jsonObject.getString("cases"));
                            tvRecovered.setText(jsonObject.getString("recovered"));
                            tvCritical.setText(jsonObject.getString("critical"));
                            tvActive.setText(jsonObject.getString("active"));
                            tvTodayCases.setText(jsonObject.getString("todayCases"));
                            tvTotalDeaths.setText(jsonObject.getString("deaths"));
                            tvTodayDeaths.setText(jsonObject.getString("todayDeaths"));
                            tvAffectedCountries.setText(jsonObject.getString("affectedCountries"));


                            //Set pieChart Values
                            pieChart.addPieSlice(new PieModel("Cases", Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#FFA726")));
                            pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#66BB6A")));
                            pieChart.addPieSlice(new PieModel("Deaths", Integer.parseInt(tvTotalDeaths.getText().toString()), Color.parseColor("#EF5350")));
                            pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#29B6F6")));

                            pieChart.startAnimation();

                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Slow Internet or Server Error!", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
