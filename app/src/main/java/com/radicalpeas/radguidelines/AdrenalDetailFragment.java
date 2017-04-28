package com.radicalpeas.radguidelines;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
    // INCIDENTAL
    private int diagnostic = 0;
    private int size = 0;
    private int cancer_history = 0;
    private int prior_imaging = 0;
    private int features = 0;

    // ADRENAL PROTOCOL
    private String preconString = null;
    private String portalvenousString = null;
    private String delayedString = null;
    private String absoluteWashoutString = null;
    private String relativeWashoutString = null;
    private double absolute_washout;
    private double relative_washout;

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
                final View incidentalView = view;

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

                        set_adrenal_incidental_layout_status(incidentalView);
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

                        set_adrenal_incidental_layout_status(incidentalView);
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

                        set_adrenal_incidental_layout_status(incidentalView);
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

                        set_adrenal_incidental_layout_status(incidentalView);
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

                        TextView features_info_textview = (TextView) incidentalView.findViewById(R.id.textview_adrenal_features_info);

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
                view = inflater.inflate(R.layout.adrenal_protocol_layout, container, false);
                final View protocolView = view;

                final EditText precon_edittext = (EditText) view.findViewById(R.id.edittext_adrenal_precontrast);
                final EditText portalvenous_edittext = (EditText) view.findViewById(R.id.edittext_adrenal_portalvenous);
                final EditText delayed_edittext = (EditText) view.findViewById(R.id.edittext_adrenal_delayed);

                precon_edittext.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s)
                    {
                        preconString = precon_edittext.getText().toString();
                        calculateAdrenalWashout(protocolView);
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                    }
                });

                portalvenous_edittext.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s)
                    {
                        portalvenousString = portalvenous_edittext.getText().toString();
                        calculateAdrenalWashout(protocolView);
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                    }
                });

                delayed_edittext.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s)
                    {
                        delayedString = delayed_edittext.getText().toString();
                        calculateAdrenalWashout(protocolView);
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {
                    }
                });
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

    public void calculateAdrenalWashout(View view)
    {
        int precon_attenuation;
        int portalvenous_attenuation;
        int delayed_attenuation;

        final TextView absolute_washout_textview = (TextView) view.findViewById(R.id.textview_absolute_washout);
        final TextView relative_washout_textview = (TextView) view.findViewById(R.id.textview_relative_washout);

        if (preconString != null && !preconString.isEmpty()
                && portalvenousString != null && !portalvenousString.isEmpty()
                && delayedString != null && !delayedString.isEmpty())
        {

            precon_attenuation = Integer.valueOf(preconString);
            portalvenous_attenuation = Integer.valueOf(portalvenousString);
            delayed_attenuation = Integer.valueOf(delayedString);

            absolute_washout = ((double) portalvenous_attenuation - delayed_attenuation) / ((double) portalvenous_attenuation - precon_attenuation) * 100;
            absolute_washout_textview.setText(String.valueOf(absolute_washout));

            absoluteWashoutString = String.valueOf(Math.round(absolute_washout*10.0)/10.0) + "%";
            absolute_washout_textview.setText(absoluteWashoutString);
        }
        else
        {
            absoluteWashoutString = null;
            absolute_washout_textview.setText("");
        }

        if (portalvenousString != null && !portalvenousString.isEmpty()
                && delayedString != null && !delayedString.isEmpty())
        {
            portalvenous_attenuation = Integer.valueOf(portalvenousString);
            delayed_attenuation = Integer.valueOf(delayedString);

            relative_washout = ((double) portalvenous_attenuation - delayed_attenuation) / ((double) portalvenous_attenuation) * 100;

            relativeWashoutString = String.valueOf(Math.round(relative_washout*10.0)/10.0) + "%";
            relative_washout_textview.setText(relativeWashoutString);
        }
        else
        {
            relativeWashoutString = null;
            relative_washout_textview.setText("");
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

        String incidental_legend1 = "If the patient has clinical signs or symptoms of adrenal hyperfunction, consider biochemical evaluation.";
        String incidental_legend2 = "Consider biochemical testing to exclude pheochromocytoma";

        // tab position
        Tab currentTab = Tab.values()[mViewPager.getCurrentItem()];
        switch(currentTab)
        {
            case INCIDENTAL:


                if(diagnostic == 1)
                {
                    // adenoma
                    guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is a simple adrenal cyst.";
                    guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "No follow up necessary.";
                }
                if(diagnostic == 2)
                {
                    // myelolipoma
                    guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is an adrenal nodule with macroscopic fat, compatible with an adrenal myelolipoma.";
                    guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "No follow up necessary.";
                }
                else if(diagnostic == 3)
                {
                    // adenoma
                    guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is an adrenal nodule with low attenuation less than 10 HU or signal drop out on chemical shift MRI, compatible with an adrenal adenoma.";
                    guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = incidental_legend1;
                }
                else if(diagnostic == 0)
                {
                    String findings = "There is an adrenal nodule";
                    if(size == 0)
                    {
                        // size between 1 to 4 cm
                        findings += " measuring between 1 to 4 cm";

                        if(prior_imaging == 0)
                        {
                            // no prior images
                            if(cancer_history == 0)
                            {
                                // no cancer history

                                if(features == 0)
                                {
                                    // benign features
                                    guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is a homogeneous, low density adrenal nodule with smooth margins, measuring between 1 to 4 cm, in a patient without a history of cancer.";
                                    guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Findings are suggestive of a benign adrenal nodule.";
                                    guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Consider 12 month follow up CT or MR.";
                                }
                                else if(features == 1)
                                {
                                    // suspicious features
                                    guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is a heterogeneous adrenal nodule with irregular margins, measuring between 1 to 4 cm.";
                                    guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Consider further evaluation with unenhanced CT or chemical shift MRI. If there are no findings of lipid rich adenoma, consider further evaluation with adrenal washout CT.";
                                }
                            }
                            else if(cancer_history == 1)
                            {
                                // has cancer history
                                guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = findings + ", in a patient with history of cancer.";
                                guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Consider further evaluation with PET, unenhanced CT or chemical shift MRI. If there are no findings of lipid rich adenoma, consider further evaluation with adrenal protocol CT.";
                            }
                        }
                        else if(prior_imaging == 1)
                        {
                            // stable over 1 year
                            guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = findings + ", which has been stable for over 1 year, and is most likely benign";
                            guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = incidental_legend1;
                        }
                        else if(prior_imaging == 2)
                        {
                            // enlarging
                            guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = findings + ", which has enlarged compared to prior exam, concerning for malignancy";
                            guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Consider biopsy or resection. " + incidental_legend2 + ".";
                        }

                    }
                    else if(size == 1)
                    {
                        // over 4 cm
                        findings += " measuring at least 4 cm in size";
                        if(cancer_history == 0)
                        {
                            guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = findings + ".";
                            guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Consider resection. " + incidental_legend2 + ".";
                        }
                        else if(cancer_history == 1)
                            {
                                guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = findings + ", in a patient with a history of cancer.";
                                guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Consider PET or biopsy. " + incidental_legend2 + ".";
                            }
                    }

                }

                break;

            case ADRENAL_PROTOCOL:

                if(preconString != null && !preconString.isEmpty())
                {
                    // pre-contrast was performed
                    if(Integer.valueOf(preconString) <= 10)
                    {
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is an adrenal nodule with pre-contrast attenuation of 10 HU or less, diagnostic of a lipid rich adrenal adenoma.";
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "No imaging follow up necessary. " + incidental_legend1;
                    }
                    else if(absoluteWashoutString != null && !absoluteWashoutString.isEmpty())
                    {
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is an adrenal nodule with absolute washout of " + absoluteWashoutString + ".";

                        if(absolute_washout >= 60)
                        {
                            guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Absolute washout of 60% or greater is diagnostic of an adrenal adenoma.";
                            guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "No imaging follow up necessary. " + incidental_legend1;
                        }
                        else
                        {
                            guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Absolute washout of less than 60% is indeterminate.";
                            guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = incidental_legend2 + ", chemical shift MR, or biopsy if appropriate.";
                        }
                    }
                }
                else if(relativeWashoutString != null && !relativeWashoutString.isEmpty())
                {
                    guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is an adrenal nodule with relative washout of " + relativeWashoutString + ".";

                    if(relative_washout >= 40)
                    {
                        guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Relative washout of 40% or greater is diagnostic of an adrenal adenoma.";
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "No imaging follow up necessary. " + incidental_legend1;
                    }
                    else
                    {
                        guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Relative washout of less than 40% is indeterminate.";
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = incidental_legend2 + ", chemical shift MR, or biopsy if appropriate.";
                    }
                }
                else
                {
                    guidelines[OrganDetailActivity.RESULTS_STATUS_MESSAGE] = "Not enough info";
                }

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

