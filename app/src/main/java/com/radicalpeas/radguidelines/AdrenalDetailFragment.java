package com.radicalpeas.radguidelines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A fragment representing a single Organ detail screen.
 * This fragment is either contained in a {@link OrganListActivity}
 * in two-pane mode (on tablets) or a {@link OrganDetailActivity}
 * on handsets.
 */

/**
 * Created by huanx on 4/16/2017.
 */


public class AdrenalDetailFragment extends OrganDetailFragment
{
    private int diagnostic = 0;
    private int size = 0;
    private int cancer_history = 0;
    private int prior_imaging = 0;
    private int features = 0;

    private enum Tab
    {
        INCIDENTAL(0), ADRENAL_PROTOCOL(1);

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
                case INCIDENTAL:
                    return "Incidental on CT";
                case ADRENAL_PROTOCOL:
                    return "Adrenal Protocol CT";
                default:
                    return "";
            }
        }
    }

    public static final AdrenalDetailFragment newInstance()
    {
        AdrenalDetailFragment f = new AdrenalDetailFragment();

        tab_titles = new ArrayList<>();
        tab_titles.add(Tab.INCIDENTAL.getValue(), Tab.INCIDENTAL.toString());
        tab_titles.add(Tab.ADRENAL_PROTOCOL.getValue(), Tab.ADRENAL_PROTOCOL.toString());

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
            case INCIDENTAL:
                view = inflater.inflate(R.layout.adrenal_incidental_layout, container, false);
                final View rootView = view;

                final Spinner diagnostic_spinner = (Spinner) view.findViewById(R.id.spinner_adrenal_diagnostic);
                final Spinner size_spinner = (Spinner) view.findViewById(R.id.spinner_adrenal_size);
                final Spinner cancer_history_spinner = (Spinner) view.findViewById(R.id.spinner_adrenal_cancer_history);
                final Spinner prior_imaging_spinner = (Spinner) view.findViewById(R.id.spinner_adrenal_prior);
                final Spinner features_spinner = (Spinner) view.findViewById(R.id.spinner_adrenal_features);

                // DIAGNOSTIC FEATURES
                ArrayAdapter<CharSequence> diagnostic_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.adrenal_incidental_diagnostic_array, R.layout.spinner_dropdown_item_multiline);
                diagnostic_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                diagnostic_spinner.setAdapter(diagnostic_adapter);

                diagnostic_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        diagnostic_spinner.setSelection(position);
                        diagnostic = position;

                        set_adrenal_incidental_layout_status(rootView);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {}
                });

                // SIZE
                ArrayAdapter<CharSequence> size_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.adrenal_incidental_size_array, R.layout.spinner_dropdown_item_multiline);
                size_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                size_spinner.setAdapter(size_adapter);

                size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        size_spinner.setSelection(position);
                        size = position;

                        set_adrenal_incidental_layout_status(rootView);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {}
                });

                // CANCER HISTORY
                final ArrayAdapter<CharSequence> cancer_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.adrenal_incidental_cancer_array, R.layout.spinner_dropdown_item_multiline);
                cancer_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                cancer_history_spinner.setAdapter(cancer_adapter);

                cancer_history_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        cancer_history_spinner.setSelection(position);
                        cancer_history = position;

                        set_adrenal_incidental_layout_status(rootView);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {}
                });

                // PRIOR IMAGING
                final ArrayAdapter<CharSequence> prior_imaging_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.adrenal_incidental_prior_array, R.layout.spinner_dropdown_item_multiline);
                prior_imaging_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                prior_imaging_spinner.setAdapter(prior_imaging_adapter);

                prior_imaging_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        prior_imaging_spinner.setSelection(position);
                        prior_imaging = position;

                        set_adrenal_incidental_layout_status(rootView);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {}
                });

                // IMAGING FEATURES - BENIGN VS SUSPICIOUS
                final ArrayAdapter<CharSequence> features_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.adrenal_incidental_features_array, R.layout.spinner_dropdown_item_multiline);
                features_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                features_spinner.setAdapter(features_adapter);

                features_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        features_spinner.setSelection(position);
                        features = position;

                        TextView features_info_textview = (TextView) rootView.findViewById(R.id.textview_adrenal_features_info);

                        if(features == 0)
                        {
                            features_info_textview.setVisibility(View.VISIBLE);
                            features_info_textview.setText("homogeneous, low density, smooth margins");
                        }
                        else
                        {
                            features_info_textview.setVisibility(View.VISIBLE);
                            features_info_textview.setText("heterogenous, necrosis, irregular margins");
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {}
                });

                break;


            case ADRENAL_PROTOCOL:

                view = null;

                break;
            default:
                view = null;
        }


        return view;
    }

    public void set_adrenal_incidental_layout_status(View view)
    {
        if(diagnostic == 0)
        {
            enableField(view, R.id.label_adrenal_size, R.id.spinner_adrenal_size);
            enableField(view, R.id.label_cancer_history, R.id.spinner_adrenal_cancer_history);
            enableField(view, R.id.label_adrenal_prior_imaging, R.id.spinner_adrenal_prior);
            enableField(view, R.id.label_adrenal_features, R.id.spinner_adrenal_features);
            view.findViewById(R.id.textview_adrenal_features_info).setVisibility(View.VISIBLE);
        }
        else
        {
            disableField(view, R.id.label_adrenal_size, R.id.spinner_adrenal_size);
            disableField(view, R.id.label_cancer_history, R.id.spinner_adrenal_cancer_history);
            disableField(view, R.id.label_adrenal_prior_imaging, R.id.spinner_adrenal_prior);
            disableField(view, R.id.label_adrenal_features, R.id.spinner_adrenal_features);
            view.findViewById(R.id.textview_adrenal_features_info).setVisibility(View.GONE);
        }

        if(size == 0 && prior_imaging != 0)
        {
            // cancer history and other features is not a factor if size is 1-4 cm and there is prior imaging available
            disableField(view, R.id.label_cancer_history, R.id.spinner_adrenal_cancer_history);
            disableField(view, R.id.label_adrenal_features, R.id.spinner_adrenal_features);
            view.findViewById(R.id.textview_adrenal_features_info).setVisibility(View.GONE);
        }

        if(size == 0 && prior_imaging == 0 && cancer_history == 1)
        {
            disableField(view, R.id.label_adrenal_features, R.id.spinner_adrenal_features);
            view.findViewById(R.id.textview_adrenal_features_info).setVisibility(View.GONE);
        }

        if(size == 1)
        {
            disableField(view, R.id.label_adrenal_prior_imaging, R.id.spinner_adrenal_prior);
            disableField(view, R.id.label_adrenal_features, R.id.spinner_adrenal_features);
            view.findViewById(R.id.textview_adrenal_features_info).setVisibility(View.GONE);
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
            case INCIDENTAL:

                if(diagnostic == 1)
                {
                    // myelolipoma
                    guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "myelolipoma";
                }
                else if(diagnostic == 2)
                {
                    // adenoma
                    guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "adenoma";
                }
                else if(diagnostic == 0)
                {
                    if(size == 0)
                    {
                        if(prior_imaging == 0)
                        {
                            // no prior images
                            if(cancer_history == 0)
                            {
                                // no cancer history

                                if(features == 0)
                                {
                                    // benign features
                                    guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "1 to 4 cm with benign features.  No history of cancer.";
                                    guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Consider 12 month follow up CT or MR.";
                                }
                                else if(features == 1)
                                {
                                    // suspicious features
                                    guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "1 to 4 cm with suspicious features.  No history of cancer.";
                                    guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Consider further evaluation with unenhanced CT or chemical shift MRI. If there are no findings of lipid rich adenoma, consider further evaluation with adrenal protocol CT.";
                                }
                            }
                            else if(cancer_history == 1)
                            {
                                // has cancer history
                                guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "1 to 4 cm with history of cancer.";
                                guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Consider PET.  Consider further evaluation with unenhanced CT or chemical shift MRI. If there are no findings of lipid rich adenoma, consider further evaluation with adrenal protocol CT.";
                            }
                        }
                        else if(prior_imaging == 1)
                        {
                            // stable over 1 year
                            guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "1 to 4 cm. stable over 1 year.";
                            guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "if no symptoms... benign.";
                        }
                        else if(prior_imaging == 2)
                        {
                            // enlarging
                            guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "1 to 4 cm. enlarging, concerning for malignancy";
                            guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "consider biopsy or resection.";
                        }

                    }
                    else if(size == 1)
                    {
                        // over 4 cm
                        if(cancer_history == 0)
                        {
                            guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "large 4 cm. no cancer history";
                            guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "consider resection.";
                        }
                        else if(cancer_history == 1)
                            {
                                guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "large 4 cm. with cancer history";
                                guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "consider PET or biopsy.";
                            }
                    }

                }

                break;

            case ADRENAL_PROTOCOL:

                break;

            default:
                break;
        }

        guidelines[OrganDetailActivity.RESULTS_REFERENCE_TEXT] = "Managing Incidental Findings on Abdominal CT: White Paper of the ACR Incidental Findings Committee";
        guidelines[OrganDetailActivity.RESULTS_REFERENCE_LINK] = "http://www.jacr.org/article/S1546-1440(10)00330-3/fulltext#sec13";
        guidelines[OrganDetailActivity.RESULTS_REFERENCE_IMAGE] = "drawable/adrenal_ct_guidelines";

        return guidelines;
    }
}

