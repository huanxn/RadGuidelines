package com.radicalpeas.radguidelines;

/**
 * A fragment representing a single Organ detail screen.
 * This fragment is either contained in a {@link OrganListActivity}
 * in two-pane mode (on tablets) or a {@link OrganDetailActivity}
 * on handsets.
 */


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huanx on 4/13/2017.
 */


public class RenalDetailFragment extends OrganDetailFragment
{
    private int cyst_or_solid = 0;

    private int demographic = 0;
    private int cyst_wall = 0;
    private int cyst_attenuation = 0;
    private int cyst_septation = 0;
    private int cyst_calcification = 0;
    private int cyst_enhancement = 0;
    private int cyst_size = 0;

    private int nonneoplastic = 0;
    private int solid_size = 0;

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

    public static final RenalDetailFragment newInstance()
    {
        RenalDetailFragment f = new RenalDetailFragment();

        tab_titles = new ArrayList<>();
        tab_titles.add(Tab.CT.getValue(), Tab.CT.toString());

        return f;
    }

    // called by tabbedFragment
    // create the organ specific layout for each tab position
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState, int tabPosition)
    {
        View view;

        Tab currentTab = Tab.values()[tabPosition];

        switch(currentTab)
        {
            // RENAL CT
            case CT:
                view = inflater.inflate(R.layout.renal_ct_layout, container, false);
                final View rootView = view;

            //    final FrameLayout rootView = (FrameLayout) view.findViewById(R.id.renal_lesion_container);

                final SpinnerWithPrompt cyst_or_solid_spinner = (SpinnerWithPrompt) view.findViewById(R.id.spinner_cyst_or_solid);
                List<String> cyst_or_solid_list = new ArrayList<String>();
                cyst_or_solid_list.add("Cystic Lesion");   // 0
                cyst_or_solid_list.add("Solid Lesion");    // 1
                cyst_or_solid_spinner.setItems(cyst_or_solid_list);

                cyst_or_solid_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        cyst_or_solid_spinner.setSelection(position);

                        cyst_or_solid = position;

                        if(position == 0)
                        {
                            // Cyst
                            rootView.findViewById(R.id.renal_cyst_layout).setVisibility(View.VISIBLE);
                            rootView.findViewById(R.id.renal_solid_layout).setVisibility(View.GONE);
                        }
                        else if(position == 1)
                        {
                            // Solid
                            rootView.findViewById(R.id.renal_cyst_layout).setVisibility(View.GONE);
                            rootView.findViewById(R.id.renal_solid_layout).setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            rootView.findViewById(R.id.renal_cyst_layout).setVisibility(View.GONE);
                            rootView.findViewById(R.id.renal_solid_layout).setVisibility(View.GONE);

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }

                });


                // RENAL CYST
                final Spinner cyst_demographic_spinner = (Spinner) view.findViewById(R.id.spinner_renal_cyst_demographic);
                final Spinner cyst_wall_spinner = (Spinner) view.findViewById(R.id.spinner_cyst_wall);
                final Spinner cyst_attenuation_spinner = (Spinner) view.findViewById(R.id.spinner_cyst_attenuation);
                final Spinner cyst_septation_spinner = (Spinner) view.findViewById(R.id.spinner_cyst_septations);
                final Spinner cyst_calcifications_spinner = (Spinner) view.findViewById(R.id.spinner_cyst_calcifications);
                final Spinner cyst_enhancement_spinner = (Spinner) view.findViewById(R.id.spinner_cyst_enhancement);
                final Spinner cyst_size_spinner = (Spinner) view.findViewById(R.id.spinner_cyst_size);

                // CYST DEMOGRAPHIC POPULATION AND COMORBIDITIES
                ArrayAdapter<CharSequence> demographic_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.demographic_array, R.layout.spinner_dropdown_item_multiline);
                demographic_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                cyst_demographic_spinner.setAdapter(demographic_adapter);

                cyst_demographic_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        cyst_demographic_spinner.setSelection(position);
                        demographic = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }

                });

                // CYST WALL
                ArrayAdapter<CharSequence> cyst_wall_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.renal_cyst_wall_array, R.layout.spinner_dropdown_item_multiline);
                cyst_wall_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                cyst_wall_spinner.setAdapter(cyst_wall_adapter);

                cyst_wall_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        cyst_wall_spinner.setSelection(position);
                        cyst_wall = position;

                        if(cyst_wall == 2)
                        {
                            // hide other selections, since they don't matter now
                            ((TextView) rootView.findViewById(R.id.label_renal_cyst_attenuation)).setTextColor(getResources().getColor(R.color.text_dark_disabled));
                            cyst_attenuation_spinner.setVisibility(View.INVISIBLE);
                            ((TextView) rootView.findViewById(R.id.label_renal_cyst_septation)).setTextColor(getResources().getColor(R.color.text_dark_disabled));
                            cyst_septation_spinner.setVisibility(View.INVISIBLE);
                            ((TextView) rootView.findViewById(R.id.label_renal_cyst_calcification)).setTextColor(getResources().getColor(R.color.text_dark_disabled));
                            cyst_calcifications_spinner.setVisibility(View.INVISIBLE);
                            ((TextView) rootView.findViewById(R.id.label_renal_cyst_enhancement)).setTextColor(getResources().getColor(R.color.text_dark_disabled));
                            cyst_enhancement_spinner.setVisibility(View.INVISIBLE);
                            cyst_enhancement_spinner.setSelection(1);   // set to "with enhancement"
                            ((TextView) rootView.findViewById(R.id.label_renal_cyst_size)).setTextColor(getResources().getColor(R.color.text_dark_disabled));
                            cyst_size_spinner.setVisibility(View.INVISIBLE);
                        }
                        else if(cyst_wall == 1)
                        {
                            // set all to visible
                            // set enhancement selection to visible, but disabled and locked to "enhancement"
                            ((TextView) rootView.findViewById(R.id.label_renal_cyst_attenuation)).setTextColor(getResources().getColor(R.color.text_dark));
                            cyst_attenuation_spinner.setVisibility(View.VISIBLE);
                            ((TextView) rootView.findViewById(R.id.label_renal_cyst_septation)).setTextColor(getResources().getColor(R.color.text_dark));
                            cyst_septation_spinner.setVisibility(View.VISIBLE);
                            ((TextView) rootView.findViewById(R.id.label_renal_cyst_calcification)).setTextColor(getResources().getColor(R.color.text_dark));
                            cyst_calcifications_spinner.setVisibility(View.VISIBLE);
                            ((TextView) rootView.findViewById(R.id.label_renal_cyst_enhancement)).setTextColor(getResources().getColor(R.color.text_dark));
                            cyst_enhancement_spinner.setEnabled(false);
                            cyst_enhancement_spinner.setVisibility(View.VISIBLE);
                            cyst_enhancement_spinner.setSelection(1);   // set to "with enhancement"

                            // only show size option if hyperdense cyst
                            if(cyst_attenuation == 1)
                            {
                                ((TextView) rootView.findViewById(R.id.label_renal_cyst_size)).setTextColor(getResources().getColor(R.color.text_dark));
                                cyst_size_spinner.setVisibility(View.VISIBLE);
                            }

                        }
                        else
                        {
                            // set all to visible
                            // enable enhancement selection
                            ((TextView) rootView.findViewById(R.id.label_renal_cyst_attenuation)).setTextColor(getResources().getColor(R.color.text_dark));
                            cyst_attenuation_spinner.setVisibility(View.VISIBLE);
                            ((TextView) rootView.findViewById(R.id.label_renal_cyst_septation)).setTextColor(getResources().getColor(R.color.text_dark));
                            cyst_septation_spinner.setVisibility(View.VISIBLE);
                            ((TextView) rootView.findViewById(R.id.label_renal_cyst_calcification)).setTextColor(getResources().getColor(R.color.text_dark));
                            cyst_calcifications_spinner.setVisibility(View.VISIBLE);
                            ((TextView) rootView.findViewById(R.id.label_renal_cyst_enhancement)).setTextColor(getResources().getColor(R.color.text_dark));
                            cyst_enhancement_spinner.setEnabled(true);
                            cyst_enhancement_spinner.setVisibility(View.VISIBLE);
                            cyst_enhancement_spinner.setSelection(0);   // set to "with enhancement"

                            // only show size option if hyperdense cyst
                            if(cyst_attenuation == 1)
                            {
                                ((TextView) rootView.findViewById(R.id.label_renal_cyst_size)).setTextColor(getResources().getColor(R.color.text_dark));
                                cyst_size_spinner.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }

                });

                // CYST ATTENUATION
                ArrayAdapter<CharSequence> cyst_attenuation_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.renal_cyst_attenuation_array, R.layout.spinner_dropdown_item_multiline);
                cyst_attenuation_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                cyst_attenuation_spinner.setAdapter(cyst_attenuation_adapter);

                cyst_attenuation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        cyst_attenuation_spinner.setSelection(position);
                        cyst_attenuation = position;

                        TextView label = (TextView) rootView.findViewById(R.id.label_renal_cyst_size);
                        if(cyst_attenuation == 1)
                        {

                            label.setTextColor(getResources().getColor(R.color.text_dark));
                            cyst_size_spinner.setVisibility(View.VISIBLE);
                        }
                        else
                        {

                            label.setTextColor(getResources().getColor(R.color.text_dark_disabled));
                            cyst_size_spinner.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }

                });

                // CYST SEPTATIONS
                ArrayAdapter<CharSequence> cyst_septation_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.renal_cyst_septation_array, R.layout.spinner_dropdown_item_multiline);
                cyst_septation_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                cyst_septation_spinner.setAdapter(cyst_septation_adapter);

                cyst_septation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        cyst_septation_spinner.setSelection(position);
                        cyst_septation = position;
/*
                                if(cyst_septation > 0)
                                    cyst_enhancement_spinner.setVisibility(View.VISIBLE);
                                else
                                    cyst_enhancement_spinner.setVisibility(View.INVISIBLE);
*/
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }

                });

                // CYST CALCIFICATIONS
                ArrayAdapter<CharSequence> cyst_calcifications_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.renal_cyst_calcification_array, R.layout.spinner_dropdown_item_multiline);
                cyst_calcifications_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                cyst_calcifications_spinner.setAdapter(cyst_calcifications_adapter);

                cyst_calcifications_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        cyst_calcifications_spinner.setSelection(position);
                        cyst_calcification = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }

                });

                // CYST ENHANCEMENT
                ArrayAdapter<CharSequence> cyst_enhancement_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.renal_cyst_enhancement_array, R.layout.spinner_dropdown_item_multiline);
                cyst_enhancement_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                cyst_enhancement_spinner.setAdapter(cyst_enhancement_adapter);

                cyst_enhancement_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        cyst_enhancement_spinner.setSelection(position);
                        cyst_enhancement = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }

                });

                // CYST SIZE
                ArrayAdapter<CharSequence> cyst_size_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.renal_cyst_size_array, R.layout.spinner_dropdown_item_multiline);
                cyst_size_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                cyst_size_spinner.setAdapter(cyst_size_adapter);

                cyst_size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        cyst_size_spinner.setSelection(position);
                        cyst_size = position;

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }

                });


                // RENAL SOLID
                Spinner nonneoplastic_spinner = (Spinner) rootView.findViewById(R.id.spinner_renal_solid_nonneoplastic);
                final Spinner solid_demographic_spinner = (Spinner) rootView.findViewById(R.id.spinner_renal_solid_demographic);
                final Spinner size_spinner = (Spinner) rootView.findViewById(R.id.spinner_renal_solid_size);

                ArrayAdapter<CharSequence> nonneoplastic_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.renal_solid_nonneoplastic_array, R.layout.spinner_dropdown_item_multiline);
                nonneoplastic_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                nonneoplastic_spinner.setAdapter(nonneoplastic_adapter);
                nonneoplastic_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
                    {
                        nonneoplastic = position;

                        if(nonneoplastic != 0)
                        {
                            ((TextView) rootView.findViewById(R.id.label_renal_solid_demographic)).setTextColor(getResources().getColor(R.color.text_dark_disabled));
                            solid_demographic_spinner.setVisibility(View.INVISIBLE);
                            ((TextView) rootView.findViewById(R.id.label_renal_solid_size)).setTextColor(getResources().getColor(R.color.text_dark_disabled));
                            size_spinner.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            ((TextView) rootView.findViewById(R.id.label_renal_solid_demographic)).setTextColor(getResources().getColor(R.color.text_dark));
                            solid_demographic_spinner.setVisibility(View.VISIBLE);
                            ((TextView) rootView.findViewById(R.id.label_renal_solid_size)).setTextColor(getResources().getColor(R.color.text_dark));
                            size_spinner.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                    }
                });

                //ArrayAdapter<CharSequence> demographic_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.demographic_array, R.layout.spinner_dropdown_item_multiline);
                //demographic_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                solid_demographic_spinner.setAdapter(demographic_adapter);
                solid_demographic_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
                    {
                        demographic = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                    }
                });

                ArrayAdapter<CharSequence> size_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.renal_solid_size_array, R.layout.spinner_dropdown_item_multiline);
                size_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                size_spinner.setAdapter(size_adapter);
                size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
                    {
                        solid_size = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                    }
                });


                break;


            default:
                view = null;
        }


        return view;
    }

    // if completed info, returns guideline recommendations, reference, link
    // else send error message
    @Override
    public String[] getResults()
    {
        String[] guidelines = new String[OrganDetailActivity.RESULTS_ARRAY_SIZE];

        for (int i = 0; i < guidelines.length; i++)
        {
            guidelines[i] = "";
        }

        // tab position
        Tab currentTab = Tab.values()[mViewPager.getCurrentItem()];
        switch(currentTab)
        {
            case CT:
                String findings_intro;
                String findings;
                String features = "";
                String followup_2F = "";
                String followup_3_or_4 = "";

                String followup_surgery = "surgical options include open and laparoscopic nephrectomy and partial nephrectomy. Surgical or percutaneous ablation may also be considered, but biopsy would be needed to achieve a tissue diagnosis";
                String followup_observation_long = "follow up CT or MRI at 6 and 12 months, and then yearly for 5 years";
                String followup_observation_short = "follow up CT or MRI at 3 to 6 months, 12 months, and then yearly";

                if(demographic == 1)
                {
                    followup_2F = "In a patient with limited life expectancy, this may be ignored.  Otherwise, consider ";
                    followup_3_or_4 = "In a patient with limited life expectancy, this may be followed up with CT or MRI at 6 and 12 months, then yearly for 5 years.  Otherwise, consider ";
                }

                if(cyst_enhancement == 1)
                {
                    findings_intro = "There is an enhancing";
                }
                else
                {
                    findings_intro = "There is a";
                }
                if(cyst_or_solid == 0)
                {
                    // RENAL CYST
                    if(cyst_septation == 0)
                    {
                        features += ", with no septations";
                    }
                    else if(cyst_septation == 1)
                    {
                        features += ", with few thin septa";
                    }
                    else if(cyst_septation == 2)
                    {
                        features += ", with multiple thin septa, or minimal smoothly thickened septa";
                    }
                    else if(cyst_septation == 3)
                    {
                        features += ", with thick irregular septa";
                    }

                    if(cyst_enhancement == 1)
                    {
                        features += ", and";
                    }
                    else
                    {
                        features += ", ";
                    }

                    if(cyst_calcification == 0)
                    {
                        features += " no calcifications";
                    }
                    else if(cyst_calcification == 1)
                    {
                        features += " with thin calcifications";
                    }
                    else if(cyst_calcification == 2)
                    {
                        features += " with thick or nodular calcifications";
                    }

                    if(cyst_enhancement == 0)
                    {
                        features += ", and no measurable enhancement";
                    }

                    if (cyst_wall == 0)
                    {
                        if (cyst_attenuation == 0 && cyst_septation == 0 && cyst_calcification == 0 && cyst_enhancement == 0)
                        {
                            // BOSNIAK 1

                            guidelines[0] = "VALID";
                            guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is a simple fluid attenuating cyst without septations, calcifications, or enhancement.";
                            guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Bosniak 1: simple cyst";
                            guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "No follow up necessary.";
                        }
                        else if (cyst_attenuation == 0 && cyst_septation <= 1 && cyst_calcification <= 1 && cyst_enhancement == 0)
                        {
                            // thin cyst wall, few thin septa, thin calcifications
                            findings = findings_intro + " simple fluid attenuating cyst" + features + ".";

                            guidelines[0] = "VALID";
                            guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = findings;
                            guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Bosniak 2: minimally complex cyst";
                            guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "No follow up necessary.";
                        }
                        else if (cyst_attenuation == 1 && cyst_size == 0 && cyst_septation <= 1 && cyst_calcification <= 1 && cyst_enhancement == 0)
                        {
                            // thin cyst wall, small hyperdense cyst
                            findings = findings_intro + " hyperdense cyst smaller than 3 cm" + features + ".";

                            guidelines[0] = "VALID";
                            guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = findings;
                            guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Bosniak 2: minimally complex cyst";
                            guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "No follow up necessary.";
                        }
                        else if (cyst_attenuation == 1 && cyst_size == 0 && cyst_septation <= 2 && cyst_calcification <= 2 && cyst_enhancement == 0)
                        {
                            // thin cyst wall, small hyperdense cyst, either multiple septation or thick calc
                            findings = findings_intro + " hyperdense cyst smaller than 3 cm" + features + ".";
                            followup_2F += followup_observation_long;

                            guidelines[0] = "VALID";
                            guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = findings;
                            guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Bosniak 2F: mildly complex cyst";
                            guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = followup_2F.substring(0, 1).toUpperCase() + followup_2F.substring(1) + ".";
                        }
                        else if (cyst_attenuation == 1 && cyst_size == 1 && cyst_septation <= 2 && cyst_calcification <= 2 && cyst_enhancement == 0)
                        {
                            // thin cyst wall, large hyperdense cyst
                            findings = findings_intro + " hyperdense cyst larger than 3 cm" + features + ".";
                            followup_2F += followup_observation_long;

                            guidelines[0] = "VALID";
                            guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = findings;
                            guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Bosniak 2F: mildly complex cyst";
                            guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = followup_2F.substring(0, 1).toUpperCase() + followup_2F.substring(1) + ".";

                        }
                        else if (cyst_attenuation == 0 && cyst_septation <= 2 && cyst_calcification <= 2 && cyst_enhancement == 0)
                        {
                            // thin cyst wall,
                            findings = findings_intro + " simple fluid attenuating cyst" + features + ".";
                            followup_2F += followup_observation_long;

                            guidelines[0] = "VALID";
                            guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = findings;
                            guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Bosniak 2F: mildly complex cyst";
                            guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = followup_2F.substring(0, 1).toUpperCase() + followup_2F.substring(1) + ".";
                            guidelines[OrganDetailActivity.RESULTS_STATISTICS] = "Percentage malignant: 5%";
                        }
                        else if (cyst_septation == 3 || cyst_enhancement == 1)
                        {
                            findings = findings_intro;
                            if (cyst_attenuation == 1)
                            {
                                findings += " hyperdense";
                            }
                            findings += " cyst" + features + ".";

                            followup_3_or_4 += followup_surgery;

                            guidelines[0] = "VALID";
                            guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = findings;
                            guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Bosniak 3: Indeterminate cyst";
                            guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = followup_3_or_4.substring(0, 1).toUpperCase() + followup_3_or_4.substring(1) + ".";
                            guidelines[OrganDetailActivity.RESULTS_STATISTICS] = "Percentage malignant: 55%";
                        }
                        else
                        {
                            guidelines[0] = "ERROR";
                        }
                    }
                    else if (cyst_wall == 1)
                    {
                        findings = findings_intro;
                        if (cyst_attenuation == 1)
                        {
                            findings += " hyperdense";
                        }
                        findings += " cyst with thickened wall" + features + ".";

                        followup_3_or_4 += followup_surgery;

                        guidelines[0] = "VALID";
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = findings;
                        guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Bosniak 3: Indeterminate cyst";
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = followup_3_or_4.substring(0, 1).toUpperCase() + followup_3_or_4.substring(1) + ".";
                        guidelines[OrganDetailActivity.RESULTS_STATISTICS] = "Percentage malignant: 55%";
                    }
                    else if (cyst_wall == 2)
                    {
                        followup_3_or_4 += followup_surgery;

                        guidelines[0] = "VALID";
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is a cystic lesion with enhancing soft tissue components adjacent to or separate from the wall or septa.";
                        guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Bosniak 4: Likely malignant";
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = followup_3_or_4.substring(0, 1).toUpperCase() + followup_3_or_4.substring(1) + ".";
                    }
                    else
                    {
                        guidelines[0] = "ERROR";
                    }

                    guidelines[OrganDetailActivity.RESULTS_REFERENCE_TEXT] = "Managing Incidental Findings on Abdominal CT: White Paper of the ACR Incidental Findings Committee";
                    guidelines[OrganDetailActivity.RESULTS_REFERENCE_LINK] = "http://www.jacr.org/article/S1546-1440(10)00330-3/fulltext#sec11";
                    guidelines[OrganDetailActivity.RESULTS_REFERENCE_IMAGE] = "drawable/renal_ct_cyst_guidelines";
                }
                else
                {
                    // RENAL SOLID

                    if (nonneoplastic == 0)
                    {
                        if (demographic == 0)
                        {
                            if (solid_size == 0)
                            {
                                guidelines[0] = "VALID";
                                guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is a very small solid mass measuring less than 1 cm.";
                                guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Differential diagnosis includes renal cell carcinoma, oncocytoma, and angiomyolipoma.";
                                guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = followup_observation_short.substring(0, 1).toUpperCase() + followup_observation_short.substring(1) + ", while size is less than 1 cm.";

                            }
                            else if (solid_size == 1)
                            {
                                guidelines[0] = "VALID";
                                guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is a small solid mass measuring between 1 to 3 cm.";
                                guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Most likely renal cell carcinoma.";
                                guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "If hyperattenuating and homogenously enhancing, consider MRI and percutaneous biopsy to diagnose angiomyolipoma with minimal fat.  Otherwise " + followup_surgery + ".";
                            }
                            else if (solid_size == 2)
                            {
                                guidelines[0] = "VALID";
                                guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is a large mass larger than 3 cm.";
                                guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Most likely renal cell carcinoma.";
                                guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = followup_surgery.substring(0, 1).toUpperCase() + followup_surgery.substring(1) + ". Angiomyolipoma with minimal fat, oncocytoma, other benign neoplasms may be found at surgery.";
                            }
                        }
                        else if (demographic == 1)
                        {
                            if (solid_size == 0)
                            {
                                guidelines[0] = "VALID";
                                guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is a very small solid mass measuring less than 1 cm.";
                                guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Differential diagnosis includes renal cell carcinoma, oncocytoma, and angiomyolipoma.";
                                guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "In a patient with high risk comorbidities, recommend " + followup_observation_short + ", while the size is less than 1.5 cm.";

                            }
                            else if (solid_size == 1)
                            {
                                guidelines[0] = "VALID";
                                guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is a small solid mass measuring between 1 to 3 cm.";
                                guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Most likely renal cell carcinoma.";
                                guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "If hyperattenuating and homogenously enhancing, consider MRI or percutaneous biopsy to diagnose angiomyolipoma with minimal fat.  Observation may be considered in a patient with high risk comorbidities. Otherwise " + followup_surgery;
                            }
                            else if (solid_size == 2)
                            {
                                guidelines[0] = "VALID";
                                guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is a large solid mass greater than 3 cm.";
                                guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Most likely renal cell carcinoma.";
                                guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Observation may be considered in a patient with high risk comorbidities. Otherwise " + followup_surgery + ". Angiomyolipoma with minimal fat, oncocytoma, other benign neoplasms may be found at surgery.";
                            }
                        }
                        else
                        {
                            guidelines[0] = "ERROR";
                        }

                        guidelines[OrganDetailActivity.RESULTS_REFERENCE_TEXT] = "Managing Incidental Findings on Abdominal CT: White Paper of the ACR Incidental Findings Committee";
                        guidelines[OrganDetailActivity.RESULTS_REFERENCE_LINK] = "http://www.jacr.org/article/S1546-1440(10)00330-3/fulltext#sec11";
                        guidelines[OrganDetailActivity.RESULTS_REFERENCE_IMAGE] = "drawable/renal_ct_solid_guidelines";
                    }
                    else if(nonneoplastic == 1)
                    {
                        // pyelonephritis
                        guidelines[0] = "VALID";
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There are findings suspicious for pyelnephritis.";
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Correlate with clinical symptoms and urine analysis.";

                    }
                    else if(nonneoplastic == 2)
                    {
                        // AML
                        guidelines[0] = "VALID";
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is a fat containing non-calcified renal mass most likely an angiomyelolipoma.";
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Consider IR or surgical consult if symptomatic or if the size is greater than 4 cm.";

                    }
                }
                break;

            default:
                break;
        }

        return guidelines;

    }

}