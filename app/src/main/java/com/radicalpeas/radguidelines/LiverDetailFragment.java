package com.radicalpeas.radguidelines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


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
    private int size;

    // INCIDENTAL CT FINDING
    private int risk_level = 0;
    private int attenuation = 0;
    private int features = 0;

    private final String ct_legend7 = "Suspicious imaging features: Ill-defined margins, enhancement (more than about 20 HU), heterogeneous, enlargement.";
    private final String ct_legend8 = "Hemangioma features: Nodular discontinuous peripheral enhancement with progressive enlargement of enhancing foci on subsequent phases. Nodule isodense with vessels, not parenchyma.";
    private final String ct_legend9 = "Small robustly enhancing lesion in average risk, young patient: hemangioma, focal nodular hyperplasia, transient hepatic attenuation difference flow artifact, and in average risk, older patient: hemangioma, THAD flow artifact. Other possible diagnoses: adenoma, AVM, nodular regenerative hyperplasia. ";


    // LIRADS
    private int initial_observation = 0;
    private int arterial = 0;
    private int washout = 0;
    private int capsule = 0;
    private int growth = 0;
    private int growth_5g = 0;
    private int ultrasound_5us = 0;

    private enum Tab
    {
        CT(0), LIRADS(1);

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
                    return "Incidental on CT";
                case LIRADS:
                    return "LI-RADS";
                default:
                    return "";
            }
        }
    }

    public static final LiverDetailFragment newInstance()
    {
        LiverDetailFragment f = new LiverDetailFragment();

        tab_titles = new ArrayList<>();
        tab_titles.add(Tab.CT.getValue(), Tab.CT.toString());
        tab_titles.add(Tab.LIRADS.getValue(), Tab.LIRADS.toString());

        return f;
    }

    /*
    @Override
    public static void setTabTitles()
    {
        tab_titles = new ArrayList<>();
        tab_titles.add(Tab.CT.getValue(), Tab.CT.toString());
        tab_titles.add(Tab.LIRADS.getValue(), Tab.LIRADS.toString());
    }
    */

    // must be overriden
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
            // Incidental liver mass detected on CT
            case CT:
                view = inflater.inflate(R.layout.liver_ct_layout, container, false);

                final View liver_ct_view = view;

                final Spinner liver_ct_risk_spinner = (Spinner) view.findViewById(R.id.spinner_liver_ct_risk_level);
                final Spinner liver_ct_size_spinner = (Spinner) view.findViewById(R.id.spinner_liver_ct_size);
                final Spinner liver_ct_attenuation_spinner = (Spinner) view.findViewById(R.id.spinner_liver_ct_attenuation);
                final Spinner liver_ct_features_spinner = (Spinner) view.findViewById(R.id.spinner_liver_ct_features);

                // RISK LEVEL
                ArrayAdapter<CharSequence> risk_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.liver_ct_risk_level_array, R.layout.spinner_dropdown_item_multiline);
                risk_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                liver_ct_risk_spinner.setAdapter(risk_adapter);

                liver_ct_risk_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        liver_ct_risk_spinner.setSelection(position);
                        risk_level = position;

                        TextView risk_level_textview = (TextView) liver_ct_view.findViewById(R.id.textview_liver_ct_risk_level);
                        final String low_risk = "Young patient (\u2264 40 years old), with no known malignancy, hepatic dysfunction, hepatic malignant risk factors, or symptoms attributable to the liver.";
                        final String average_risk = "Patient > 40 years old, with no known malignancy, hepatic dysfunction, abnormal liver function tests or hepatic malignant risk factors or symptoms attributable to the liver.";
                        final String high_risk = "Known primary malignancy with a propensity to metastasize to the liver, cirrhosis, and/or other hepatic risk factors. Hepatic risk factors include hepatitis, chronic active hepatitis, sclerosing cholangitis, primary biliary cirrhosis, hemochromatosis, hemosiderosis, oral contraceptive use, anabolic steroid use.";

                        if(risk_level == 0)
                        {
                            risk_level_textview.setText(low_risk);
                        }
                        if(risk_level == 1)
                        {
                            risk_level_textview.setText(average_risk);
                        }
                        if(risk_level == 2)
                        {
                            risk_level_textview.setText(high_risk);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }
                });

                // SIZE
                ArrayAdapter<CharSequence> size_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.liver_ct_size_array, R.layout.spinner_dropdown_item_multiline);
                size_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                liver_ct_size_spinner.setAdapter(size_adapter);

                liver_ct_size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        liver_ct_size_spinner.setSelection(position);
                        size = position;

                        set_liver_ct_layout_status(liver_ct_view);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }
                });

                // ATTENUATION / ENHANCEMENT
                ArrayAdapter<CharSequence> attenuation_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.liver_ct_attenuation_array, R.layout.spinner_dropdown_item_multiline);
                attenuation_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                liver_ct_attenuation_spinner.setAdapter(attenuation_adapter);

                liver_ct_attenuation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        liver_ct_attenuation_spinner.setSelection(position);
                        attenuation = position;

                        set_liver_ct_layout_status(liver_ct_view);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }
                });

                // IMAGING FEATURES
                ArrayAdapter<CharSequence> features_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.liver_ct_imaging_features_array, R.layout.spinner_dropdown_item_multiline);
                features_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                liver_ct_features_spinner.setAdapter(features_adapter);

                liver_ct_features_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        liver_ct_features_spinner.setSelection(position);
                        features = position;

                        set_liver_ct_layout_status(liver_ct_view);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }
                });

                break;

            // LI-RADS
            case LIRADS:
                view = inflater.inflate(R.layout.liver_lirads_layout, container, false);
                final View rootView = view;

                final SpinnerWithPrompt initial_observation_spinner = (SpinnerWithPrompt) view.findViewById(R.id.spinner_liver_lirads_observation);

                final Spinner liver_size_spinner = (Spinner) view.findViewById(R.id.spinner_liver_lirads_size);
                final Spinner liver_arterial_spinner = (Spinner) view.findViewById(R.id.spinner_liver_lirads_arterial_enhancement);
                final Spinner liver_washout_spinner = (Spinner) view.findViewById(R.id.spinner_liver_lirads_washout);
                final Spinner liver_capsule_spinner = (Spinner) view.findViewById(R.id.spinner_liver_lirads_capsule);
                final Spinner liver_growth_spinner = (Spinner) view.findViewById(R.id.spinner_liver_lirads_growth);

                final Spinner liver_growth_rate_spinner = (Spinner) view.findViewById(R.id.spinner_liver_lirads_growth_rate);
                final Spinner liver_ultrasound_spinner = (Spinner) view.findViewById(R.id.spinner_liver_lirads_ultrasound);

                // LIRADS Initial Observation
                List<String> observation_list = new ArrayList<String>();
                observation_list.add("Treated observation");   // 0
                observation_list.add("Definitely benign");    // 1
                observation_list.add("Probably benign");    // 2
                observation_list.add("Probable malignancy, not specific for HCC");    // 3
                observation_list.add("Tumor in vein");    // 4
                observation_list.add("Possible HCC");    // 5
                initial_observation_spinner.setItems(observation_list);

                initial_observation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                      @Override
                      public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
                      {
                          initial_observation_spinner.setSelection(position);
                          initial_observation = position;

                          LinearLayout LIRADS_layout = (LinearLayout) rootView.findViewById(R.id.liver_lirads345_layout);

                          if(initial_observation == 5)
                          {
                              LIRADS_layout.setVisibility(View.VISIBLE);
                          }
                          else
                          {
                              LIRADS_layout.setVisibility(View.GONE);
                          }
                      }

                      @Override
                      public void onNothingSelected(AdapterView<?> parent)
                      {

                      }
                    });

                // LIRADS SIZE
                ArrayAdapter<CharSequence> lirads_size_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.liver_lirads_size_array, R.layout.spinner_dropdown_item_multiline);
                lirads_size_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                liver_size_spinner.setAdapter(lirads_size_adapter);

                liver_size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        liver_size_spinner.setSelection(position);
                        size = position;

                        set_liver_lirads_layout_status(rootView);
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

                        set_liver_lirads_layout_status(rootView);
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

                        set_liver_lirads_layout_status(rootView);

                        if(washout == 1)
                        {
                            ((TextView) rootView.findViewById(R.id.textview_liver_lirads_washout_info)).setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            ((TextView) rootView.findViewById(R.id.textview_liver_lirads_washout_info)).setVisibility(View.GONE);
                        }
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

                        set_liver_lirads_layout_status(rootView);

                        if(capsule == 1)
                        {
                            ((TextView) rootView.findViewById(R.id.textview_liver_lirads_capsule_info)).setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            ((TextView) rootView.findViewById(R.id.textview_liver_lirads_capsule_info)).setVisibility(View.GONE);
                        }

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

                        set_liver_lirads_layout_status(rootView);


                        if(growth == 1)
                        {
                            ((TextView) rootView.findViewById(R.id.textview_liver_lirads_growth_info)).setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            ((TextView) rootView.findViewById(R.id.textview_liver_lirads_growth_info)).setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }

                });

                // LIRADS GROWTH RATE
                ArrayAdapter<CharSequence> growth_rate_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.liver_lirads_growth_rate_array, R.layout.spinner_dropdown_item_multiline);
                growth_rate_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                liver_growth_rate_spinner.setAdapter(growth_rate_adapter);

                liver_growth_rate_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        liver_growth_rate_spinner.setSelection(position);
                        growth_5g = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }

                });

                // LIRADS GROWTH
                ArrayAdapter<CharSequence> ultrasound_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.liver_lirads_ultrasound_array, R.layout.spinner_dropdown_item_multiline);
                ultrasound_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                liver_ultrasound_spinner.setAdapter(ultrasound_adapter);

                liver_ultrasound_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        liver_ultrasound_spinner.setSelection(position);
                        ultrasound_5us = position;
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

    public void set_liver_ct_layout_status(View view)
    {
        final Spinner liver_ct_attenuation_spinner = (Spinner) view.findViewById(R.id.spinner_liver_ct_attenuation);
        final TextView liver_ct_attenuation_info = (TextView) view.findViewById(R.id.textview_liver_ct_attenuation_info);
        final Spinner liver_ct_features_spinner = (Spinner) view.findViewById(R.id.spinner_liver_ct_features);
        final TextView liver_ct_features_info = (TextView) view.findViewById(R.id.textview_liver_ct_features_info);

        // legend 5
        String low_attenuation_benign = "Benign imaging features: Typical hemangioma, sharply marginated, homogeneous low attenuation (up to about 20 HU), no enhancement.  May have sharp but irregular margins.";
        // legend 7
        String suspicious_features = ct_legend7;

        String enhancing_benign = ct_legend8 + "\n\n" + ct_legend9;
        if(attenuation == 0)
        {
            liver_ct_attenuation_info.setText(low_attenuation_benign);
        }
        else if(attenuation == 1)
        {
            liver_ct_attenuation_info.setText(suspicious_features);
        }

        if(features == 0)
        {
            liver_ct_features_info.setText(enhancing_benign);
        }
        else if(features == 1)
        {
            liver_ct_features_info.setText(suspicious_features);
        }

        if(size == 0)
        {
            // too small to characterize other features
            ((TextView) view.findViewById(R.id.label_liver_ct_attenuation)).setTextColor(getResources().getColor(R.color.text_dark_disabled));
            liver_ct_attenuation_spinner.setVisibility(View.INVISIBLE);
            liver_ct_attenuation_info.setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.label_liver_ct_features)).setTextColor(getResources().getColor(R.color.text_dark_disabled));
            liver_ct_features_spinner.setVisibility(View.INVISIBLE);
            liver_ct_features_info.setVisibility(View.GONE);
        }
        else if(size == 2 && attenuation == 2)
        {
            // allow input for attenuation and additional features
            ((TextView) view.findViewById(R.id.label_liver_ct_attenuation)).setTextColor(getResources().getColor(R.color.text_dark));
            liver_ct_attenuation_spinner.setVisibility(View.VISIBLE);
            liver_ct_attenuation_info.setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.label_liver_ct_features)).setTextColor(getResources().getColor(R.color.text_dark));
            liver_ct_features_spinner.setVisibility(View.VISIBLE);
            liver_ct_features_info.setVisibility(View.VISIBLE);
        }
        else
        {
            // allow input for attenuation, but not additional features
            ((TextView) view.findViewById(R.id.label_liver_ct_attenuation)).setTextColor(getResources().getColor(R.color.text_dark));
            liver_ct_attenuation_spinner.setVisibility(View.VISIBLE);
            if(attenuation == 2)
            {
                liver_ct_attenuation_info.setVisibility(View.GONE);
            }
            else
            {
                liver_ct_attenuation_info.setVisibility(View.VISIBLE);
            }

            ((TextView) view.findViewById(R.id.label_liver_ct_features)).setTextColor(getResources().getColor(R.color.text_dark_disabled));
            liver_ct_features_spinner.setVisibility(View.INVISIBLE);
            liver_ct_features_info.setVisibility(View.GONE);
        }

    }

    private void set_liver_lirads_layout_status(View view)
    {
        LinearLayout growth_rate_layout = (LinearLayout) view.findViewById(R.id.liver_lirads_growth_rate_layout);
        LinearLayout ultrasound_layout = (LinearLayout) view.findViewById(R.id.liver_lirads_ultrasound_layout);

        int score = washout + capsule + growth;

        growth_rate_layout.setVisibility(View.GONE);
        ultrasound_layout.setVisibility(View.GONE);

        if(initial_observation == 5 && arterial == 1 && size == 1 && score == 1)
        {
            if(growth == 1)
            {
                growth_rate_layout.setVisibility(View.VISIBLE);
            }
            if(washout == 1)
            {
                ultrasound_layout.setVisibility(View.VISIBLE);
            }
        }

    }

    // must be overriden
    // if completed info, returns guideline recommendations, reference, link
    // else send error message
    @Override
    public String[] getResults()
    {
        String[] guidelines = new String[RESULTS_ARRAY_SIZE];

        for (int i = 0; i < guidelines.length; i++)
        {
            guidelines[i] = "";
        }

        // tab position
        Tab currentTab = Tab.values()[mViewPager.getCurrentItem()];

        switch(currentTab)
        {
            case CT:

                String ct_legend4 = "Follow up CT or MRI in 6 months.  May need more frequent follow-up in some situations, such as a cirrhotic patient who is a liver transplant candidate.";
                String ct_legend6 = "Differential diagnosis for a benign-appearing low attenuation mass includes: cyst, hemangioma, hamartoma, bile duct hamartomas";
                String nofollowup = "No follow up required.";

                String lowaveragerisk_nofollowup = "In a patient with low to average risk, this is most likely benign.";
                String highrisk_followup = "In a patient with high risk, follow up is recommended.";

                guidelines[0] = "VALID";

                if(size == 0)
                {
                    if(risk_level == 0 || risk_level == 1)
                    {
                        guidelines[RESULTS_IMPRESSION] = "There is a low-attenuating liver lesion smaller than 0.5 cm.  " + lowaveragerisk_nofollowup;
                        guidelines[RESULTS_FOLLOWUP] = nofollowup;
                    }
                    else
                    {
                        guidelines[RESULTS_IMPRESSION] = "There is a low-attenuating liver lesion smaller than 0.5 cm.  " + highrisk_followup;
                        guidelines[RESULTS_FOLLOWUP] = ct_legend4;
                    }
                }
                else if(size == 1)
                {
                    if(attenuation == 0)
                    {
                        guidelines[RESULTS_IMPRESSION] = "There is a benign appearing low-attenuating liver lesion between 0.5 cm and 1.5 cm.  " + ct_legend6;
                        guidelines[RESULTS_FOLLOWUP] = nofollowup;
                    }
                    else if(attenuation == 1)
                    {
                        guidelines[RESULTS_IMPRESSION] = "There is a suspicious appearing low-attenuating liver lesion between 0.5 cm and 1.5 cm.  ";
                        guidelines[RESULTS_FOLLOWUP] = ct_legend4;
                    }
                    else if(attenuation == 2)
                    {
                        if(risk_level == 0 || risk_level == 1)
                        {
                            guidelines[RESULTS_IMPRESSION] = "There is a flash-filling robustly enhancing lesion between 0.5 cm and 1.5 cm.  " + "8,9.  " +  lowaveragerisk_nofollowup;
                            guidelines[RESULTS_CLASSIFICATION] = ct_legend8 + ". " + ct_legend9;
                            guidelines[RESULTS_FOLLOWUP] = nofollowup;
                        }
                        else
                        {
                            guidelines[RESULTS_IMPRESSION] = "There is a flash-filling robustly enhancing lesion between 0.5 cm and 1.5 cm.  " + highrisk_followup;
                            guidelines[RESULTS_FOLLOWUP] = ct_legend4 + "\n" + ct_legend7 + " To evaluate, prefer multiphasic MRI";
                        }
                    }


                }
                else if(size == 2)
                {
                    if(attenuation == 0)
                    {
                        guidelines[RESULTS_IMPRESSION] = "There is a benign appearing low-attenuating liver lesion larger than 1.5 cm.";
                        guidelines[RESULTS_CLASSIFICATION] = ct_legend6;
                        guidelines[RESULTS_FOLLOWUP] = nofollowup;
                    }
                    else if(attenuation == 1)
                    {
                        if(risk_level == 0)
                        {
                            guidelines[RESULTS_IMPRESSION] = "There is a suspicious appearing low-attenuating liver lesion larger than 1.5 cm in a low risk patient.";
                            guidelines[RESULTS_FOLLOWUP] = ct_legend4;
                        }
                        else if(risk_level == 1)
                        {
                            guidelines[RESULTS_IMPRESSION] = "There is a suspicious appearing low-attenuating liver lesion larger than 1.5 cm in an average risk patient.";
                            guidelines[RESULTS_CLASSIFICATION] = ct_legend7;
                            guidelines[RESULTS_FOLLOWUP] = "Recommend multiphasic MRI for further evaluation.";
                        }
                        else if(risk_level == 2)
                        {
                            guidelines[RESULTS_IMPRESSION] = "There is a suspicious appearing low-attenuating liver lesion larger than 1.5 cm in a high risk patient.";
                            guidelines[RESULTS_FOLLOWUP] = "Recommend core biopsy.";
                        }
                    }
                    else if(attenuation == 2)
                    {
                        if(features == 0)
                        {
                            guidelines[RESULTS_IMPRESSION] = "There is a flash-filling robustly enhancing lesion larger than 1.5 cm with benign diagnostic features.";
                            guidelines[RESULTS_CLASSIFICATION] = ct_legend8 + "\n" + ct_legend9;
                            guidelines[RESULTS_FOLLOWUP] = "Differentiation of FNH from adenoma may be important especially if larger than 4 cm and subcapsular.";
                        }
                        else
                        {
                            guidelines[RESULTS_IMPRESSION] = "There is a flash-filling robustly enhancing lesion larger than 1.5 cm without benign diagnostic features.";
                            guidelines[RESULTS_IMPRESSION] = "Differential diagnosis includes hepatocellular or metastatic enhancing malignancy: islet cell, neuroendocrine, carcinoid, renal cell carcinoma, melanoma, choriocarcinoma, sarcoma, breast, some pancreatic lesions.";
                            guidelines[RESULTS_FOLLOWUP] = "Consider further evaluation with multiphasic MRI or core biopsy. Otherwise " + ct_legend4.substring(0,1).toLowerCase() + ct_legend4.substring(1);
                        }
                    }
                }

                guidelines[RESULTS_REFERENCE_TEXT] = "Managing Incidental Findings on Abdominal CT: White Paper of the ACR Incidental Findings Committee";
                guidelines[RESULTS_REFERENCE_LINK] = "http://www.jacr.org/article/S1546-1440(10)00330-3/fulltext#sec12";
                guidelines[RESULTS_REFERENCE_IMAGE] = "drawable/liver_ct_mass_guidelines";



                break;

            // LI-RADS
            case LIRADS:
                final String LR3 = "LR-3: Intermediate probability for hepatocellular carcinoma\n\nObservation that does not meet criteria for other LI-RADS categories.";
                final String LR4 = "LR-4: Probably hepatocellular carcinoma\n\nObservation with imaging features suggestive but not diagnostic of HCC.";
                final String LR5 = "LR-5: Definitely hepatocellular carcinoma.\n\nObservation with imaging features diagnostic of HCC or proven to be HCC at histology.";

                guidelines[0] = "VALID";

                if(initial_observation == 5)
                {

                    String findings;
                    String criteria_description = "";
                    int score = washout + capsule;
                    if(growth != 0)
                    {
                        score += 1;
                    }

                    if (washout == 0)
                    {
                        criteria_description += ", without washout";
                    }
                    else
                    {
                        criteria_description += ", with washout";
                    }

                    if (capsule == 0)
                    {
                        criteria_description += ", without capsule";
                    }
                    else
                    {
                        criteria_description += ", with capsule";
                    }

                    if (growth == 0)
                    {
                        criteria_description += ", without threshold growth";
                    }
                    else
                    {
                        criteria_description += ", with threshold growth";
                    }

                    if (arterial == 0)
                    {
                        // arterial hypo or iso-enhancement
                        findings = "There is an arterial hypoenhancing or isoenhancing lesion";

                        if (size == 0 || size == 1)
                        {
                            // size less than 2.0 cm
                            guidelines[RESULTS_IMPRESSION] = findings + " smaller than 2.0 cm" + criteria_description + ".";

                            if (score == 0 || score == 1)
                            {
                                // LR3
                                guidelines[RESULTS_CLASSIFICATION] = LR3;
                            }
                            else
                            {
                                // LR4
                                guidelines[RESULTS_CLASSIFICATION] = LR4;
                            }
                        }
                        if (size == 2)
                        {
                            // size more than 2.0 cm
                            guidelines[RESULTS_IMPRESSION] = findings + " at least 2.0 cm in size" + criteria_description + ".";

                            if (score == 0)
                            {
                                // LR3
                                guidelines[RESULTS_CLASSIFICATION] = LR3;
                            }
                            else
                            {
                                // LR4
                                guidelines[RESULTS_CLASSIFICATION] = LR4;
                            }
                        }
                    }
                    else
                    {
                        // arterial hyperenhancement
                        findings = "There is an arterial hyperenhancing lesion";

                        if (size == 0)
                        {
                            // less than 1.0 cm
                            guidelines[RESULTS_IMPRESSION] =  findings + " smaller than 1.0 cm" + criteria_description + ".";

                            if (score == 0)
                            {
                                // LR3
                                guidelines[RESULTS_CLASSIFICATION] = LR3;
                            }
                            else
                            {
                                // LR4
                                guidelines[RESULTS_CLASSIFICATION] = LR4;
                            }
                        }
                        else if (size == 1)
                        {
                            // size between 1.0 and 2.0 cm
                            guidelines[RESULTS_IMPRESSION] = findings + " measuring between 1.0 to 2.0 cm" + criteria_description;

                            if (score == 0)
                            {
                                // LR3
                                guidelines[RESULTS_IMPRESSION] += ".";
                                guidelines[RESULTS_CLASSIFICATION] = LR3;
                            }
                            else if (score == 1)
                            {
                                // LR4/LR5
                                // depending on 50% growth in less than 6 months (LR-5g), or washout and visibility on US (LR-5us)

                                if(growth == 1 && growth_5g == 1)
                                {

                                    guidelines[RESULTS_IMPRESSION] += " of at least 50% increase in diameter within 6 months.";
                                    guidelines[RESULTS_CLASSIFICATION] = LR5.substring(0,4) + "g" + LR5.substring(4);
                                }
                                else if(washout == 1 && ultrasound_5us == 1)
                                {
                                    guidelines[RESULTS_IMPRESSION] += ".  Antecedent surveillance ultrasound demonstrates a corresponding visible discrete nodule.";
                                    guidelines[RESULTS_CLASSIFICATION] = LR5.substring(0,4) + "us" + LR5.substring(4);
                                }
                                else
                                {
                                    guidelines[RESULTS_IMPRESSION] += ".";
                                    guidelines[RESULTS_CLASSIFICATION] = LR4;
                                }

                            }
                            else
                            {
                                // LR5
                                guidelines[RESULTS_IMPRESSION] += ".";
                                guidelines[RESULTS_CLASSIFICATION] = LR5;
                            }
                        }
                        else
                        {
                            // size larger than 2.0 cm
                            guidelines[RESULTS_IMPRESSION] = findings + " at least 2.0 cm in size" + criteria_description + ".";

                            if (score == 0)
                            {
                                //LR4
                                guidelines[RESULTS_CLASSIFICATION] = LR4;
                            }
                            else
                            {
                                //LR5
                                guidelines[0] = "VALID";
                                guidelines[RESULTS_CLASSIFICATION] = LR5;
                                guidelines[RESULTS_FOLLOWUP] = "";
                            }
                        }

                    }
                }
                else if(initial_observation == 0)
                {
                    guidelines[RESULTS_IMPRESSION] = "There is a treated liver observation.";
                    guidelines[RESULTS_CLASSIFICATION] = "LR-Treated: Treated Observation\n\nObservation of any category that has undergone loco-regional treatment.";
                }
                else if(initial_observation == 1)
                {
                    guidelines[RESULTS_IMPRESSION] = "There is a liver observation that is definitely benign.";
                    guidelines[RESULTS_CLASSIFICATION] = "LR-1: Definitely Benign\n\nObersvation with imaging features diagnostic of a benign entity, or definite disappearance at follow up in absence of treatment.";
                }
                else if(initial_observation == 2)
                {
                    guidelines[RESULTS_IMPRESSION] = "There is a liver observation that is probably benign.";
                    guidelines[RESULTS_CLASSIFICATION] = "LR-2: Probably Benign\n\nObservation with imaging features suggestive but not diagnostic of a benign entity.";
                }
                else if(initial_observation == 3)
                {
                    guidelines[RESULTS_IMPRESSION] = "There is a liver lesion of probable malignancy, but not specific for HCC.";
                    guidelines[RESULTS_CLASSIFICATION] = "LR-M:Probably Malignant, not specific for hepatocellular carcinoma\n\nObservation with imaging features suggestive of non-HCC malignancy.";
                }
                else if(initial_observation == 4)
                {
                    guidelines[RESULTS_IMPRESSION] = "There is a liver lesion with tumor in vein.";
                    guidelines[RESULTS_CLASSIFICATION] = "LR-5V: Definitely hepatocellular carcinoma with Tumor in Vein\n\nObservation with imaging features diagnostic of HCC invading vein.";
                }

                guidelines[RESULTS_REFERENCE_TEXT] = "ACR LI-RADS v2014 for CT and MRI";
                guidelines[RESULTS_REFERENCE_LINK] = "https://www.acr.org/Quality-Safety/Resources/LIRADS";

                break;

            default:
                break;
        }

        return guidelines;

    }

}
