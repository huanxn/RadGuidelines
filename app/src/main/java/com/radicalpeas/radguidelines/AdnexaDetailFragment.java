package com.radicalpeas.radguidelines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by huanx on 5/1/2017.
 */

public class AdnexaDetailFragment extends OrganDetailFragment
{
    private int ct_imaging_features;

    private enum Tab
    {
        CT(0),
        US(1);

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
                    return "Incidental on CT or MR";
                case US:
                    return "US";
                default:
                    return "";
            }
        }
    }

    public static final AdnexaDetailFragment newInstance()
    {
        AdnexaDetailFragment f = new AdnexaDetailFragment();

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

        final Tab currentTab = Tab.values()[tabPosition];
        switch(currentTab)
        {
            case CT:
                view = inflater.inflate(R.layout.adnexa_ct_layout, container, false);
                final View adnexa_ct_view = view;

                final Spinner adnexa_ct_imaging_features_spinner = (Spinner) view.findViewById(R.id.spinner_adnexa_ct_imaging_features);
                final TextView adnexa_ct_imaging_features_textview = (TextView) view.findViewById(R.id.textview_adnexa_ct_imaging_features);
                final Spinner adnexa_ct_diagnostic_features_spinner = (Spinner) view.findViewById(R.id.spinner_adnexa_ct_diagnostic_features);
                final TextView adnexa_ct_diagnostic_features_textview = (TextView) view.findViewById(R.id.textview_adnexa_ct_diagnostic_features);
                final Spinner adnexa_ct_menopause_spinner = (Spinner) view.findViewById(R.id.spinner_adnexa_ct_menopause);
                final Spinner adnexa_ct_size_spinner = (Spinner) view.findViewById(R.id.spinner_adnexa_ct_size);

                adnexa_ct_imaging_features_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        ct_imaging_features = position;

                        //setLayoutStatus(adnexa_ct_view, currentTab);
                        final String benignAppearingString = "Should have all of the following features: oval or round; unilocular, with uniform fluid attenuation or signal (layering hemorrhage acceptable if premenopausal); regular or imperceptible wall; no solid area or mural nodule; \u003c 10 cm diameter.";
                        final String probablyBenignString = "Benign-appearing cyst except for one or more of the following: angulated margins, not round or oval in shape, a portion of the cyst is poorly imaged, or image has reduced signal-to-noise ratio";
                        final String otherImagingFeaturesString = "Features include a solid component, mural nodule, septations, higher than fluid attenuation, or layering hemorrhage if postmenopausal";

                        if(ct_imaging_features == 0)
                        {
                            adnexa_ct_imaging_features_textview.setText(benignAppearingString);
                            adnexa_ct_imaging_features_textview.setVisibility(View.VISIBLE);

                            hideField(adnexa_ct_view, R.id.label_adnexa_ct_diagnostic_features, R.id.spinner_adnexa_ct_diagnostic_features);
                            adnexa_ct_diagnostic_features_textview.setVisibility(View.GONE);
                        }
                        else if(ct_imaging_features == 1)
                        {
                            adnexa_ct_imaging_features_textview.setText(probablyBenignString);
                            adnexa_ct_imaging_features_textview.setVisibility(View.VISIBLE);

                            hideField(adnexa_ct_view, R.id.label_adnexa_ct_diagnostic_features, R.id.spinner_adnexa_ct_diagnostic_features);
                            adnexa_ct_diagnostic_features_textview.setVisibility(View.GONE);
                        }
                        else if(ct_imaging_features == 2)
                        {
                            adnexa_ct_imaging_features_textview.setText(otherImagingFeaturesString);
                            adnexa_ct_imaging_features_textview.setVisibility(View.VISIBLE);

                            enableField(adnexa_ct_view, R.id.label_adnexa_ct_diagnostic_features, R.id.spinner_adnexa_ct_diagnostic_features);
                            adnexa_ct_diagnostic_features_textview.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }

                });

                break;

            case US:

                //todo change to US layout
                view = inflater.inflate(R.layout.adnexa_ct_layout, container, false);
                break;
            default:
                view = null;
        }


        return view;
    }

    private void setLayoutStatus(View view, Tab tab)
    {
        switch(tab)
        {
            case CT:
                final TextView adnexa_ct_imaging_features_textview = (TextView) view.findViewById(R.id.textview_adnexa_ct_imaging_features);
                final TextView adnexa_ct_diagnostic_features_textview = (TextView) view.findViewById(R.id.textview_adnexa_ct_imaging_features);

                final String benignAppearingString = "Should have all of the following features: oval or round; unilocular, with uniform fluid attenuation or signal (layering hemorrhage acceptable if premenopausal); regular or imperceptible wall; no solid area or mural nodule; \u003c 10 cm diameter.";


                if (ct_imaging_features == 0)
                {
                    adnexa_ct_imaging_features_textview.setText(benignAppearingString);
                    adnexa_ct_imaging_features_textview.setVisibility(View.VISIBLE);
                }

                break;
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

                guidelines[OrganDetailActivity.RESULTS_REFERENCE_TEXT] = "Managing Incidental Findings on Abdominal and Pelvic CT and MRI, Part 1: White Paper of the ACR Incidental Findings Committee II on Adnexal Findings";
                break;

            case US:

                break;

            default:
                break;
        }


        return guidelines;
    }

}
