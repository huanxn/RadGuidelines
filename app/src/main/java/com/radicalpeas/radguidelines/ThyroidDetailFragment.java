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

import java.util.ArrayList;

/**
 * Created by huanx on 4/13/2017.
 */


public class ThyroidDetailFragment extends OrganDetailFragment
{
    // US variables
    private int composition = 0;
    private int echogenicity = 0;
    private int shape = 0;
    private int margin = 0;
    private int echogenic_foci = 0;
    private float noduleSize = 0;

    // CT variables (spinner positions)
    private int suspicious_features = 0;
    private int population_risk = 0;
    private int age = 0;
    private int size = 0;

    private enum Tab
    {
        US(0), CTorMR(1);

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
                case US:
                    return "Ultrasound";
                case CTorMR:
                    return "CT or MR";
                default:
                    return "";
            }
        }
    }

    public static final ThyroidDetailFragment newInstance()
    {
        ThyroidDetailFragment f = new ThyroidDetailFragment();

        tab_titles = new ArrayList<>();
        tab_titles.add(Tab.US.getValue(), Tab.US.toString());
        tab_titles.add(Tab.CTorMR.getValue(), Tab.CTorMR.toString());

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
            // THYROID US
            case US:
                view = inflater.inflate(R.layout.thyroid_us_layout, container, false);

                final Spinner thyroid_composition_spinner = (Spinner) view.findViewById(R.id.spinner_thyroid_composition);
                final Spinner thyroid_echogenicity_spinner = (Spinner) view.findViewById(R.id.spinner_thyroid_echogenicity);
                final Spinner thyroid_shape_spinner = (Spinner) view.findViewById(R.id.spinner_thyroid_shape);
                final Spinner thyroid_margin_spinner = (Spinner) view.findViewById(R.id.spinner_thyroid_margin);
                final Spinner thyroid_echogenic_foci_spinner = (Spinner) view.findViewById(R.id.spinner_thyroid_echogenic_foci);

                final EditText nodule_size_EditText = (EditText) view.findViewById(R.id.edittext_thyroid_nodule_size);

                // THYROID COMPOSITION
                ArrayAdapter<CharSequence> composition_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.thyroid_us_composition_array, R.layout.spinner_dropdown_item_multiline);
                composition_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                thyroid_composition_spinner.setAdapter(composition_adapter);

                thyroid_composition_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        thyroid_composition_spinner.setSelection(position);
                        composition = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }
                });

                // THYROID ECHOGENICITY
                ArrayAdapter<CharSequence> echogenicity_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.thyroid_us_echogenicity_array, R.layout.spinner_dropdown_item_multiline);
                echogenicity_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                thyroid_echogenicity_spinner.setAdapter(echogenicity_adapter);

                thyroid_echogenicity_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        thyroid_echogenicity_spinner.setSelection(position);
                        echogenicity = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }

                });

                // THYROID SHAPE
                ArrayAdapter<CharSequence> shape_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.thyroid_us_shape_array, R.layout.spinner_dropdown_item_multiline);
                shape_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                thyroid_shape_spinner.setAdapter(shape_adapter);

                thyroid_shape_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        thyroid_shape_spinner.setSelection(position);
                        shape = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }

                });

                // THYROID MARGIN
                ArrayAdapter<CharSequence> margin_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.thyroid_us_margin_array, R.layout.spinner_dropdown_item_multiline);
                margin_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                thyroid_margin_spinner.setAdapter(margin_adapter);

                thyroid_margin_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        thyroid_margin_spinner.setSelection(position);
                        margin = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }

                });

                // THYROID ECHOGENIC FOCI
                ArrayAdapter<CharSequence> echogenic_foci_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.thyroid_us_echogenic_foci_array, R.layout.spinner_dropdown_item_multiline);
                echogenic_foci_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                thyroid_echogenic_foci_spinner.setAdapter(echogenic_foci_adapter);

                thyroid_echogenic_foci_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        thyroid_echogenic_foci_spinner.setSelection(position);
                        echogenic_foci = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }

                });

                // THYROID NODULE SIZE
                nodule_size_EditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s)
                    {
                        String noduleSizeString = nodule_size_EditText.getText().toString();
                        if(noduleSizeString != null && !noduleSizeString.isEmpty())
                        {
                            noduleSize = Float.valueOf(noduleSizeString);
                        }
                        else
                        {
                            noduleSize = 0;
                        }
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


            // THYROID CT OR MR
            case CTorMR:
                view = inflater.inflate(R.layout.thyroid_ctmr_layout, container, false);
                final Spinner thyroid_suspicious_features_spinner = (Spinner) view.findViewById(R.id.spinner_suspicious_features);
                final Spinner thyroid_population_risk_spinner = (Spinner) view.findViewById(R.id.spinner_thyroid_population);
                final Spinner thyroid_age_spinner = (Spinner) view.findViewById(R.id.spinner_thyroid_age);
                final Spinner thyroid_size_spinner = (Spinner) view.findViewById(R.id.spinner_thyroid_ct_size);

                // CT or MR SUSPICIOUS FEATURES
                ArrayAdapter<CharSequence> suspicious_features_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.no_yes_array, R.layout.spinner_dropdown_item_multiline);
                suspicious_features_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                thyroid_suspicious_features_spinner.setAdapter(suspicious_features_adapter);

                thyroid_suspicious_features_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        thyroid_suspicious_features_spinner.setSelection(position);
                        suspicious_features = position;

                        if(suspicious_features == 0)
                        {
                            thyroid_population_risk_spinner.setEnabled(true);
                            thyroid_age_spinner.setEnabled(true);
                            thyroid_size_spinner.setEnabled(true);
                        }
                        else if(suspicious_features == 1)
                        {
                            thyroid_population_risk_spinner.setEnabled(false);
                            thyroid_age_spinner.setEnabled(false);
                            thyroid_size_spinner.setEnabled(false);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }
                });

                // POPULATION / RISK LEVEL
                ArrayAdapter<CharSequence> population_risk_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.demographic_array, R.layout.spinner_dropdown_item_multiline);
                population_risk_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                thyroid_population_risk_spinner.setAdapter(population_risk_adapter);

                thyroid_population_risk_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        thyroid_population_risk_spinner.setSelection(position);
                        population_risk = position;

                        if(population_risk == 0)
                        {
                            thyroid_age_spinner.setEnabled(true);
                            thyroid_size_spinner.setEnabled(true);
                        }
                        else if(population_risk == 1 && suspicious_features == 0)
                        {
                            thyroid_age_spinner.setEnabled(false);
                            thyroid_size_spinner.setEnabled(false);
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }
                });

                // PATIENT AGE
                ArrayAdapter<CharSequence> age_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.thyroid_ct_age_array, R.layout.spinner_dropdown_item_multiline);
                age_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                thyroid_age_spinner.setAdapter(age_adapter);

                thyroid_age_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        thyroid_age_spinner.setSelection(position);
                        age = position;

                        if(age == 0)
                        {
                            // if <35 years old, size threshold is 1 cm
                            ArrayAdapter<CharSequence> size_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.thyroid_ct_young_size_array, R.layout.spinner_dropdown_item_multiline);
                            size_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                            thyroid_size_spinner.setAdapter(size_adapter);
                        }
                        else
                        {
                            // if >35 years old, size threshold is 1.5 cm
                            ArrayAdapter<CharSequence> size_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.thyroid_ct_old_size_array, R.layout.spinner_dropdown_item_multiline);
                            size_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                            thyroid_size_spinner.setAdapter(size_adapter);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }
                });

                // NODULE SIZE
                // default young patient size threshold, but will change if user selects older patient age
                ArrayAdapter<CharSequence> size_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.thyroid_ct_young_size_array, R.layout.spinner_dropdown_item_multiline);
                size_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                thyroid_size_spinner.setAdapter(size_adapter);

                thyroid_size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        thyroid_size_spinner.setSelection(position);
                        size = position;
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

    @Override
    public String[] getResults()
    {
        //ArrayList<String> stringList = new ArrayList<String>();
        String[] guidelines = new String[OrganDetailActivity.RESULTS_ARRAY_SIZE];

        for (int i = 0; i < guidelines.length; i++)
        {
            guidelines[i] = "";
        }

        Tab currentTab = Tab.values()[mViewPager.getCurrentItem()];
        switch(currentTab)
        {// if completed info, returns guideline recommendations, reference, link
            // else send error message

            case US:
                String findings = "";
                int TIRADS_points = 0;

                if(composition == 0)
                {
                    findings = "There is a cystic or almost completely cystic";
                }
                else if (composition == 1)
                {
                    findings = "There is a spongiform";
                }
                else if (composition == 2)    // mixed cystic and solid
                {
                    TIRADS_points += 1;
                    findings = "There is a mixed cystic and solid";
                }
                else if (composition == 3)   // solid or almost completely solid
                {
                    TIRADS_points += 2;

                    findings = "There is a solid or almost completely solid";
                }

                TIRADS_points += echogenicity;
                if(echogenicity == 0)
                {
                    findings += " anechoic nodule";
                }
                else if (echogenicity == 1)
                {
                    findings += " hyperechoic or isoechoic nodule";
                }
                else if (echogenicity == 2)    // mixed cystic and solid
                {
                    findings += " hypoechoic nodule";
                }
                else if (echogenicity == 3)   // solid or almost completely solid
                {
                    findings += " very hypoechoic nodule";
                }

                if (shape == 1)  // taller than wide
                {
                    TIRADS_points += 3;
                    findings += ", taller than wide";
                }

                if (margin == 2 || margin == 3)
                {
                    TIRADS_points += margin;
                }
                if(margin == 0)
                {
                    findings += ", with smooth margins";
                }
                else if (margin == 1)
                {
                    findings += ", with ill-defined margins";
                }
                else if (margin == 2)
                {
                    findings += ", with lobulated or irregular margins";
                }
                else if (margin == 3)
                {
                    findings += ", with extra-thyroidal extension";
                }


                TIRADS_points += echogenic_foci;
                if(echogenic_foci == 0)
                {
                    findings += ", and no calcifications or only large comet-tail artifacts";
                }
                else if (echogenic_foci == 1)
                {
                    findings += ", and coarse macrocalcifications";
                }
                else if (echogenic_foci == 2)
                {
                    findings += ", and peripheral calcifications";
                }
                else if (echogenic_foci == 3)
                {
                    findings += ", and punctate microcalcifications";
                }

                findings += ".";

                guidelines[0] = "VALID";

                if (TIRADS_points <= 1)
                {
                    guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = findings;
                    guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "TR1: Benign.";
                    guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "No FNA is recommended";
                }
                else if (TIRADS_points == 2)
                {
                    guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = findings;
                    guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "TR2: Not suspicious.";
                    guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "No FNA is recommended";
                }
                else if (TIRADS_points == 3)
                {
                    guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = findings;
                    guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "TR3: Mildly Suspicious.";
                    guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "FNA if > 2.5 cm.  Follow if > 1.5 cm";

                    if (noduleSize >= 2.5)
                    {
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Given the size larger than 2.5 cm, FNA biopsy is recommended.";
                    }
                    else if (noduleSize >= 1.5)
                    {
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Given the size between 1.5 and 2.5 cm, follow up is recommended.";
                    }
                    else if (noduleSize > 0)
                    {
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Given the size less than 1.5 cm, no follow up is recommended.";
                    }
                }
                else if (TIRADS_points >= 4 && TIRADS_points <= 6)
                {
                    guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = findings;
                    guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "TR4: Moderately Suspicious.";
                    guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "FNA if > 1.5 cm.  Follow if > 1 cm";

                    if (noduleSize >= 2.5)
                    {
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Given the size larger than 2.5 cm, FNA biopsy is recommended.";
                    }
                    else if (noduleSize >= 1.5)
                    {
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Given the size between 1.0 and 1.5 cm, follow up is recommended.";
                    }
                    else if (noduleSize > 0)
                    {
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Given the size less than 1.5 cm, no follow up is recommended.";
                    }
                }
                else
                {
                    guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = findings;
                    guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "TR5: Highly Suspicious.";
                    guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "FNA if > 1 cm.  Follow if > 0.5 cm";

                    if (noduleSize >= 1.0)
                    {
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Given the size larger than 1.0 cm, FNA biopsy is recommended.";
                    }
                    else if (noduleSize >= 0.5)
                    {
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Given the size between 0.5 and 1.0 cm, follow up is recommended.";
                    }
                    else if (noduleSize > 0)
                    {
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Given the size less than 0.5 cm, no follow up is recommended.";
                    }

                    guidelines[OrganDetailActivity.RESULTS_STATISTICS] = " ??% mallignant";
                }

                guidelines[OrganDetailActivity.RESULTS_REFERENCE_TEXT] = "Thyroid Imaging Reporting and Data System (TI-RADS) 2017";
                guidelines[OrganDetailActivity.RESULTS_REFERENCE_LINK] = "https://www.acr.org/Quality-Safety/Resources/TIRADS";
                guidelines[OrganDetailActivity.RESULTS_REFERENCE_IMAGE] = "drawable/thyroid_tirads_2017";


                return guidelines;

            case CTorMR:

                guidelines[0] = "VALID";
                if(suspicious_features == 1)
                {
                    guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is an incidental thyroid nodule with suspicious features.";
                    guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Recommend evaluation with thyroid ultrasound.";
                }
                else    // not suspicious
                {
                    if(population_risk == 1)
                    {
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is an incidental thyroid nodule with no suspicious features in a patient with limited life expectancy and comorbidities.";
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "No further evaluation is recommended.";
                    }
                    else    // general population
                    {
                        if(age == 0) // age less than 35
                        {
                            if(size == 0)   // size less than 1 cm
                            {
                                guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is an incidental thyroid nodule measuring less than 1 cm, with no suspicious features.";
                                guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "No further evaluation is recommended.";
                            }
                            else    // size greater than 1 cm
                            {
                                guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is an incidental thyroid nodule measuring more than 1 cm, in a patient less than 35 years old.";
                                guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Recommend evaluation with thyroid ultrasound.";
                            }
                        }
                        else    // age more than 35
                        {
                            if(size == 0)   // size less than 1.5 cm
                            {
                                guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is an incidental thyroid nodule measuring less than 1.5 cm, with no suspicious features, in a patient over 35 years old.";
                                guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "No further evaluation is recommended.";
                            }
                            else    // size greater than 1.5 cm
                            {
                                guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is an incidental thyroid nodule measuring more than 1.5 cm, in a patient over 35 years old.";
                                guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Recommend evaluation with thyroid ultrasound.";
                            }
                        }

                    }
                }

                guidelines[OrganDetailActivity.RESULTS_REFERENCE_TEXT] = "Managing Incidental Thyroid Nodules Detected on Imaging: White Paper of the ACR Incidental Thyroid Findings Committee";
                guidelines[OrganDetailActivity.RESULTS_REFERENCE_LINK] = "http://www.jacr.org/article/S1546-1440(14)00627-9/fulltext";
                guidelines[OrganDetailActivity.RESULTS_REFERENCE_IMAGE] = "drawable/thyroid_ct_guidelines";

                return guidelines;

            default:
                return guidelines;

        }
    } // end getResults

}
