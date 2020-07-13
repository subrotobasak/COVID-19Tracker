package com.example.covid_19tracker_subroto.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_19tracker_subroto.Activity.CountryDetailActivity;
import com.example.covid_19tracker_subroto.Adapter.MyCustomAdapter;
import com.example.covid_19tracker_subroto.InternetCheck.InternetCheck;
import com.example.covid_19tracker_subroto.Model.CountryModel;
import com.example.covid_19tracker_subroto.R;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Country extends Fragment {

    private EditText searchEditText;
    private ListView listView;
    private SimpleArcLoader simpleArcLoader;
    private View view;

    public static List<CountryModel> countryModelsList = new ArrayList<>();
    CountryModel countryModel;
    MyCustomAdapter myCustomAdapter;


    public Country() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_country, container, false);
        intView();

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

    private void intView() {
        searchEditText = view.findViewById(R.id.editSearchId);
        listView = view.findViewById(R.id.listViewId);
        simpleArcLoader = view.findViewById(R.id.loader);

       // fetchData();

        // Set OnItemClickListener for Country DetailActivity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getContext(), CountryDetailActivity.class).putExtra("i",i));

            }
        });


        //For Search Filtered
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                myCustomAdapter.getFilter().filter(charSequence);
                myCustomAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void fetchData() {

        String url = "https://corona.lmao.ninja/v2/countries/";

        simpleArcLoader.start();

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String countryName = jsonObject.getString("country");
                                String cases = jsonObject.getString("cases");
                                String todayCases = jsonObject.getString("todayCases");
                                String deaths = jsonObject.getString("deaths");
                                String todayDeaths = jsonObject.getString("todayDeaths");
                                String recovered = jsonObject.getString("recovered");
                                String active = jsonObject.getString("active");
                                String critical = jsonObject.getString("critical");
                                String tests = jsonObject.getString("tests");

                                JSONObject object = jsonObject.getJSONObject("countryInfo");
                                String flagUrl = object.getString("flag");

                                countryModel = new CountryModel(flagUrl, countryName, cases, todayCases, deaths, todayDeaths, recovered, active, critical, tests);
                                countryModelsList.add(countryModel);


                            }

                            myCustomAdapter = new MyCustomAdapter(getContext(), countryModelsList);
                            listView.setAdapter(myCustomAdapter);
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);


                        } catch (JSONException e) {
                            e.printStackTrace();

                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Slow Internet or Server Error!", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);


    }

}