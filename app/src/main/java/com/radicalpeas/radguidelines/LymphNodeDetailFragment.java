package com.radicalpeas.radguidelines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by huanx on 4/28/2017.
 */

public class LymphNodeDetailFragment extends OrganDetailFragment
{
    private int imaging_features;
    private int prior_imaging;
    private int malignancy_history;
    private int clinical;

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

    public static final LymphNodeDetailFragment newInstance()
    {
        LymphNodeDetailFragment f = new LymphNodeDetailFragment();

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

                view = inflater.inflate(R.layout.lymphnode_ct_layout, container, false);
                final View rootView = view;

                final Spinner node_imaging_features_spinner = (Spinner) view.findViewById(R.id.spinner_node_ct_imaging_features);
                final Spinner node_prior_imaging_spinner = (Spinner) view.findViewById(R.id.spinner_node_ct_prior_imaging);
                final Spinner node_cancer_history_spinner = (Spinner) view.findViewById(R.id.spinner_node_ct_cancer_history);
                final Spinner node_clinical_spinner = (Spinner) view.findViewById(R.id.spinner_node_ct_clinical);

                node_imaging_features_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        imaging_features = position;

                        TextView info = (TextView) rootView.findViewById(R.id.textview_node_ct_imaging_features_info);

                        if(imaging_features == 0)
                        {
                            // benign
                            info.setText("normal short-axis diameter (< 1 cm in retroperitoneum), normal architecture (elongated, fatty hilum), normal enhancement, normal node number");
                        }
                        else
                        {
                            // suspicious
                            info.setText("enlarged short-axis diameter (\u22651 cm in retroperitoneum, architecture (round, indistinct hilum), enhancement (necrosis/hypervascular), increased number (cluster \u22653 lymph nodes in single nodal station or cluster \u22652 lymph nodes in \u22652 regions)");
                        }

                        setLayoutStatus(rootView);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }

                });

                node_prior_imaging_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

                node_cancer_history_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        malignancy_history = position;
                        setLayoutStatus(rootView);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }

                });

                setSpinnerEntries(node_clinical_spinner, R.array.node_clinical_array);
                node_clinical_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        clinical = position;
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

        if(imaging_features == 1)
        {

            enableField(view, R.id.label_node_ct_prior_imaging, R.id.spinner_node_ct_prior_imaging);
            enableField(view, R.id.label_node_ct_cancer_history, R.id.spinner_node_ct_cancer_history);
            enableField(view, R.id.label_node_ct_clinical, R.id.spinner_node_ct_clinical);

            if(prior_imaging == 0)
            {
                enableField(view, R.id.label_node_ct_cancer_history, R.id.spinner_node_ct_cancer_history);
                enableField(view, R.id.label_node_ct_clinical, R.id.spinner_node_ct_clinical);

                if(malignancy_history == 0)
                {
                    enableField(view, R.id.label_node_ct_clinical, R.id.spinner_node_ct_clinical);
                }
                else
                {
                    disableField(view, R.id.label_node_ct_clinical, R.id.spinner_node_ct_clinical);
                }
            }
            else
            {
                // has priors - hide all other fields
                disableField(view, R.id.label_node_ct_cancer_history, R.id.spinner_node_ct_cancer_history);
                disableField(view, R.id.label_node_ct_clinical, R.id.spinner_node_ct_clinical);
            }
        }
        else
        {
            // benign - hide all other fields
            disableField(view, R.id.label_node_ct_prior_imaging, R.id.spinner_node_ct_prior_imaging);
            disableField(view, R.id.label_node_ct_cancer_history, R.id.spinner_node_ct_cancer_history);
            disableField(view, R.id.label_node_ct_clinical, R.id.spinner_node_ct_clinical);
        }

    }

    // must be overriden
    // if completed info, returns guideline recommendations, reference, link
    // else send error message
    public String[] getResults()
    {
        String[] guidelines = new String[RESULTS_ARRAY_SIZE];

        for (int i = 0; i < guidelines.length; i++)
        {
            guidelines[i] = "";
        }

        guidelines[RESULTS_STATUS_MESSAGE] = "VALID";

        // tab position
        Tab currentTab = Tab.values()[mViewPager.getCurrentItem()];
        switch(currentTab)
        {
            case CT:

                String noFollowUpString = "No follow-up imaging is recommended";
                String evaluateString = "Consider biopsy or other evaluation such as PET/CT, nuclear scintigraphy, or endoscopic ultrasound.";
                String imagingFollowUpString = "Recommend 3 month follow-up with CT or MRI.";
                String findings = "";

                if(imaging_features == 0)
                {
                    // benign imaging features
                    findings = "Incidental lymph node finding with benign features";
                    guidelines[RESULTS_FOLLOWUP] = noFollowUpString;

                }
                else if(imaging_features == 1)
                {
                    // suspicious imaging features
                    if(prior_imaging == 0)
                    {
                        // no prior imaging available
                        if(malignancy_history == 0)
                        {
                            // no history of malignancy
                            if(clinical == 0)
                            {
                                // clinical/laboratory suggesting benign
                                findings = "Incidental lymph node finding with suspicious imaging features in a patient with no history of malignancy.  Clinical or laboratory correlation suggests benign etiology";
                                guidelines[RESULTS_CLASSIFICATION] = "Differential diagnosis includes nonneoplastic disease such as infection, inflammation, or connective tissue disorder.";
                                guidelines[RESULTS_FOLLOWUP] = noFollowUpString;
                            }
                            else
                            {
                                // clinical/laboratory suggesting lymphoproliferative disorder
                                findings = "Incidental lymph node finding with suspicious imaging features in a patient with no history of malignancy.  Clinical or laboratory correlation suggests a lymphoproliferative disorder";
                                guidelines[RESULTS_FOLLOWUP] = evaluateString;
                            }
                        }
                        else if(malignancy_history == 1)
                        {
                            // history of malignancy
                            findings = "Incidental lymph node finding with suspicious imaging features in a patient with a history of malignancy";
                            guidelines[RESULTS_FOLLOWUP] = evaluateString;
                        }
                    }
                    else if(prior_imaging == 1)
                    {
                        // stable for 1 year
                        findings = "Incidental lymph node finding which has been stable for over 1 year";
                        guidelines[RESULTS_FOLLOWUP] = noFollowUpString;
                    }
                    else if(prior_imaging == 2)
                    {
                        // not stable
                        findings = "Incidental lymph node finding which has not been stable for at least 1 year";
                        guidelines[RESULTS_FOLLOWUP] = evaluateString;
                    }
                }

                guidelines[RESULTS_IMPRESSION] = findings + ".";

                break;

            default:
                break;
        }


        return guidelines;
    }

}

