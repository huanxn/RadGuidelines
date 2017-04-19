package com.radicalpeas.radguidelines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


/**
 * A fragment representing a single Organ detail screen.
 * This fragment is either contained in a {@link OrganListActivity}
 * in two-pane mode (on tablets) or a {@link OrganDetailActivity}
 * on handsets.
 */

/**
 * Created by huanx on 4/16/2017.
 */


public class LiverDetailFragment extends OrganDetailFragment
{
    int size;


    // LIRADS
    int arterial = 0;
    int washout = 0;
    int capsule = 0;
    int growth = 0;

    // must be overriden
    // called by tabbedFragment
    // create the organ specific layout for each tab position
    public View createView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState, int tabPosition)
    {
        View view;

        switch(tabPosition)
        {
            // Incidental liver mass detected on CT
            case 0:
                view = inflater.inflate(R.layout.liver_ct_layout, container, false);

                break;

            // LI-RADS
            case 1:
                view = inflater.inflate(R.layout.liver_lirads_layout, container, false);

                final Spinner liver_size_spinner = (Spinner) view.findViewById(R.id.spinner_liver_lirads_size);
                final Spinner liver_arterial_spinner = (Spinner) view.findViewById(R.id.spinner_liver_lirads_arterial_enhancement);
                final Spinner liver_washout_spinner = (Spinner) view.findViewById(R.id.spinner_liver_lirads_washout);
                final Spinner liver_capsule_spinner = (Spinner) view.findViewById(R.id.spinner_liver_lirads_capsule);
                final Spinner liver_growth_spinner = (Spinner) view.findViewById(R.id.spinner_liver_lirads_growth);

                //final EditText nodule_size_EditText = (EditText) view.findViewById(R.id.edittext_thyroid_nodule_size);

                // LIRADS SIZE
                ArrayAdapter<CharSequence> size_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.liver_lirads_size_array, R.layout.spinner_dropdown_item_multiline);
                size_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                liver_size_spinner.setAdapter(size_adapter);

                liver_size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        liver_size_spinner.setSelection(position);
                        size = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }
                });

                // LIRADS ARTERIAL PHASE ENHANCEMENT
                ArrayAdapter<CharSequence> arterial_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.liver_lirads_arterial_array, R.layout.spinner_dropdown_item_multiline);
                arterial_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                liver_arterial_spinner.setAdapter(arterial_adapter);

                liver_arterial_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        liver_arterial_spinner.setSelection(position);
                        arterial = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }

                });

                // LIRADS WASHOUT
                ArrayAdapter<CharSequence> washout_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.no_yes_array, R.layout.spinner_dropdown_item_multiline);
                washout_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                liver_washout_spinner.setAdapter(washout_adapter);

                liver_washout_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        liver_washout_spinner.setSelection(position);
                        washout = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }

                });

                // LIRADS CAPSULE
                ArrayAdapter<CharSequence> capsule_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.no_yes_array, R.layout.spinner_dropdown_item_multiline);
                capsule_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                liver_capsule_spinner.setAdapter(capsule_adapter);

                liver_capsule_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        liver_capsule_spinner.setSelection(position);
                        capsule = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }

                });

                // LIRADS GROWTH
                ArrayAdapter<CharSequence> growth_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.no_yes_array, R.layout.spinner_dropdown_item_multiline);
                growth_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                liver_growth_spinner.setAdapter(growth_adapter);

                liver_growth_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        liver_growth_spinner.setSelection(position);
                        growth = position;
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

        // tab position
        switch(mViewPager.getCurrentItem())
        {
            case 0:
                break;

            // LI-RADS
            case 1:
                guidelines[0] = "VALID";

                String findings;
                String criteria_description = "";
                int score = washout + capsule + growth;

                if(washout == 0)
                {
                    criteria_description += "without washout, ";
                }
                else
                {
                    criteria_description += "with washout, ";
                }

                if(capsule == 0)
                {
                    criteria_description += "without capsule, ";
                }
                else
                {
                    criteria_description += "with capsule, ";
                }

                if(growth == 0)
                {
                    criteria_description += "without significant growth, ";
                }
                else
                {
                    criteria_description += "with significant growth, ";
                }

                if(arterial == 0)
                {
                    // arterial hypo or iso-enhancement
                    findings = "There is an arterial hypoenhancing or isoenhancing lesion, " + criteria_description;

                    if(size == 0 || size == 1)
                    {
                        // size less than 2.0 cm
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] += findings + "measuring less than 2.0 cm.";

                        if(score == 0 || score == 1)
                        {
                            // LR3
                            guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "LI-RADS 3";
                        }
                        else
                        {
                            // LR4
                            guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "LI-RADS 4";
                        }
                    }
                    if(size == 2)
                    {
                        // size more than 2.0 cm
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] += findings + "measuring 2.0 cm or larger.";

                        if(score == 0)
                        {
                            // LR3
                            guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "LI-RADS 3";
                        }
                        else
                        {
                            // LR4
                            guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "LI-RADS 4";
                        }
                    }
                }
                else
                {
                    // arterial hyperenhancement
                    findings = "There is an arterial hyperenhancing lesion, " + criteria_description;

                    if(size == 0)
                    {
                        // less than 1.0 cm
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] += findings + "measuring less than 1.0 cm.";

                        if(score == 0)
                        {
                            // LR3
                            guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "LI-RADS 3";
                        }
                        else
                        {
                            // LR4
                            guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "LI-RADS 4";
                        }
                    }
                    else if(size == 1)
                    {
                        // size between 1.0 and 2.0 cm
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] += findings + "measuring between 1.0 to 2.0 cm.";

                        if(score == 0)
                        {
                            // LR3
                            guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "LI-RADS 3";
                        }
                        else if(score == 1)
                        {
                            // LR4/LR5
                            // depending on 50% growth in less than 6 months (LR-5g), or washout and visibility on US (LR-5us)

                            guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "LI-RADS 4/5";
                        }
                        else
                        {
                            // LR5
                            guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "LI-RADS 5";
                        }
                    }
                    else
                    {
                        // size larger than 2.0 cm
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] += findings + "measuring larger than 2.0 cm.";

                        if(score == 0)
                        {
                            //LR4
                            guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "LI-RADS 4";
                        }
                        else
                        {
                            //LR5
                            guidelines[0] = "VALID";
                            guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "LI-RADS 5";
                            guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "";
                        }
                    }

                }

                break;

            default:
                break;
        }
        /*
        // get fragment of active tab (current item in pager adapter)
        TabbedContentFragment tabbedContentFragment = mSectionsPagerAdapter.getTabbedContentFragment(mViewPager.getCurrentItem()); // either US or CT
        return tabbedContentFragment.getResults();
        */

        guidelines[OrganDetailActivity.RESULTS_REFERENCE_TEXT] = "ACR LI-RADS v2014 for CT and MRI";
        guidelines[OrganDetailActivity.RESULTS_REFERENCE_LINK] = "https://www.acr.org/Quality-Safety/Resources/LIRADS";


        return guidelines;

    }

}
