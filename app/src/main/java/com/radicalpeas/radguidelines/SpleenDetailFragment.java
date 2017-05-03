package com.radicalpeas.radguidelines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by huanx on 4/28/2017.
 */

public class SpleenDetailFragment extends OrganDetailFragment
{
    private int benign_features;
    private int prior_imaging;
    private int cancer_history;
    private int suspicious_features;
    private int lesion_size;

    private enum Tab
    {
        CT(0);

        private final int value;
        private Tab(int value)
        {
            this.value = value;
        }

        public int getValue()
        {
            return value;
        }

        public Tab valueOf(int num)
        {
            return values()[num];
        }

        @Override
        public String toString()
        {
            switch(this)
            {
                case CT:
                    return "CT";
                default:
                    return "";
            }
        }
    }

    public static final SpleenDetailFragment newInstance()
    {
        SpleenDetailFragment f = new SpleenDetailFragment();

        tab_titles = new ArrayList<>();
        tab_titles.add(Tab.CT.getValue(), Tab.CT.toString());

        return f;
    }

    // must be overriden
    // called by tabbedFragment
    // create the organ specific layout for each tab position
    public View createView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState, int tabPosition)
    {
        View view;

        Tab currentTab = Tab.values()[tabPosition];
        switch(currentTab)
        {
            case CT:

                view = inflater.inflate(R.layout.spleen_ct_layout, container, false);
                final View rootView = view;

                final Spinner spleen_benign_features_spinner = (Spinner) view.findViewById(R.id.spinner_spleen_ct_benign_features);
                final Spinner spleen_prior_imaging_spinner = (Spinner) view.findViewById(R.id.spinner_spleen_ct_prior_imaging);
                final Spinner spleen_cancer_history_spinner = (Spinner) view.findViewById(R.id.spinner_spleen_ct_cancer_history);
                final Spinner spleen_suspicious_features_spinner = (Spinner) view.findViewById(R.id.spinner_spleen_ct_suspicious_features);
                final Spinner spleen_lesion_size_spinner = (Spinner) view.findViewById(R.id.spinner_spleen_ct_lesion_size);

                spleen_benign_features_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        benign_features = position;
                        setLayoutStatus(rootView);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }

                });

                spleen_prior_imaging_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        prior_imaging = position;
                        setLayoutStatus(rootView);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }

                });

                spleen_cancer_history_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        cancer_history = position;
                        setLayoutStatus(rootView);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }

                });

                spleen_suspicious_features_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        suspicious_features = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }
                });

                spleen_lesion_size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        lesion_size = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }
                });

                break;


            default:
                view = null;
        }


        return view;
    }

    private void setLayoutStatus(View view)
    {
        //final TextView liver_ct_attenuation_info = (TextView) view.findViewById(R.id.textview_liver_ct_attenuation_info);

        if(benign_features == 0)
        {

            enableField(view, R.id.label_spleen_ct_prior_imaging, R.id.spinner_spleen_ct_prior_imaging);
            enableField(view, R.id.label_spleen_ct_cancer_history, R.id.spinner_spleen_ct_cancer_history);
            enableField(view, R.id.label_spleen_ct_suspicious_features, R.id.spinner_spleen_ct_suspicious_features);
            enableField(view, R.id.label_spleen_ct_lesion_size, R.id.spinner_spleen_ct_lesion_size);

            if(prior_imaging == 0)
            {
                enableField(view, R.id.label_spleen_ct_cancer_history, R.id.spinner_spleen_ct_cancer_history);
                enableField(view, R.id.label_spleen_ct_suspicious_features, R.id.spinner_spleen_ct_suspicious_features);
                enableField(view, R.id.label_spleen_ct_lesion_size, R.id.spinner_spleen_ct_lesion_size);

                if(cancer_history == 0)
                {
                    enableField(view, R.id.label_spleen_ct_suspicious_features, R.id.spinner_spleen_ct_suspicious_features);
                    disableField(view, R.id.label_spleen_ct_lesion_size, R.id.spinner_spleen_ct_lesion_size);
                }
                else
                {
                    // has priors - hide all other fields
                    disableField(view, R.id.label_spleen_ct_suspicious_features, R.id.spinner_spleen_ct_suspicious_features);
                    enableField(view, R.id.label_spleen_ct_lesion_size, R.id.spinner_spleen_ct_lesion_size);
                }
            }
            else
            {
                // has priors - hide all other fields
                disableField(view, R.id.label_spleen_ct_cancer_history, R.id.spinner_spleen_ct_cancer_history);
                disableField(view, R.id.label_spleen_ct_suspicious_features, R.id.spinner_spleen_ct_suspicious_features);
                disableField(view, R.id.label_spleen_ct_lesion_size, R.id.spinner_spleen_ct_lesion_size);
            }
        }
        else
        {
            // benign - hide all other fields
            disableField(view, R.id.label_spleen_ct_prior_imaging, R.id.spinner_spleen_ct_prior_imaging);
            disableField(view, R.id.label_spleen_ct_cancer_history, R.id.spinner_spleen_ct_cancer_history);
            disableField(view, R.id.label_spleen_ct_suspicious_features, R.id.spinner_spleen_ct_suspicious_features);
            disableField(view, R.id.label_spleen_ct_lesion_size, R.id.spinner_spleen_ct_lesion_size);
        }

    }

    // must be overriden
    // if completed info, returns guideline recommendations, reference, link
    // else send error message
    public String[] getResults()
    {
        String[] guidelines = new String[OrganDetailActivity.RESULTS_ARRAY_SIZE];

        for (int i = 0; i < guidelines.length; i++)
        {
            guidelines[i] = "";
        }

        guidelines[OrganDetailActivity.RESULTS_STATUS_MESSAGE] = "VALID";

        // tab position
        Tab currentTab = Tab.values()[mViewPager.getCurrentItem()];
        switch(currentTab)
        {
            case CT:

                break;

            default:
                break;
        }


        return guidelines;
    }

}

