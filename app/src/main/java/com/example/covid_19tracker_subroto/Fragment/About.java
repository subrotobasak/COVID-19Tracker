package com.example.covid_19tracker_subroto.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.covid_19tracker_subroto.R;


public class About extends Fragment {

    public About() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        view.findViewById(R.id.gmailButtonID).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent gmailIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "basaksubroto@gmail.com"));
                    gmailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                    gmailIntent.putExtra(Intent.EXTRA_TEXT, "Compose email");
                    startActivity(gmailIntent);

                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Error" + e.toString(), Toast.LENGTH_SHORT).show();

                }

            }
        });


        view.findViewById(R.id.linkedInButtonID).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent linkedInIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/subrotobasak"));
                startActivity(linkedInIntent);

            }
        });

        view.findViewById(R.id.facebookButtonId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent fbIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/SubrotoBasak.Shawon"));
                startActivity(fbIntent);

            }
        });
        view.findViewById(R.id.githubButtonId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent fbIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/subrotobasak"));
                startActivity(fbIntent);

            }
        });

        return view;
    }
}
