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
 * Created by huanx on 5/1/2017.
 */

public class AdnexaDetailFragment extends OrganDetailFragment
{
    private int incidental_imaging_features;
    private int incidental_diagnostic_features;
    private int incidental_menopause;
    private int incidental_size;

    private enum Tab
    {
        INCIDENTAL(0),
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
                case INCIDENTAL:
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
        tab_titles.add(Tab.INCIDENTAL.getValue(), Tab.INCIDENTAL.toString());

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
            case INCIDENTAL:
                view = inflater.inflate(R.layout.adnexa_ct_layout, container, false);
                final View adnexa_ct_view = view;

                final Spinner adnexa_ct_imaging_features_spinner = (Spinner) view.findViewById(R.id.spinner_adnexa_ct_imaging_features);
                final TextView adnexa_ct_imaging_features_textview = (TextView) view.findViewById(R.id.textview_adnexa_ct_imaging_features);
                final Spinner adnexa_ct_diagnostic_features_spinner = (Spinner) view.findViewById(R.id.spinner_adnexa_ct_diagnostic_features);
                final TextView adnexa_ct_diagnostic_features_textview = (TextView) view.findViewById(R.id.textview_adnexa_ct_diagnostic_features);
                final Spinner adnexa_ct_menopause_spinner = (Spinner) view.findViewById(R.id.spinner_adnexa_ct_menopause);
                final Spinner adnexa_ct_size_spinner = (Spinner) view.findViewById(R.id.spinner_adnexa_ct_size);

                setSpinnerEntries(adnexa_ct_imaging_features_spinner, R.array.adnexa_ct_imaging_features_array);
                adnexa_ct_imaging_features_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        incidental_imaging_features = position;

                        //setLayoutStatus(adnexa_ct_view, currentTab);
                        final String benignAppearingString = "Should have all of the following features: oval or round; unilocular, with uniform fluid attenuation or signal (layering hemorrhage acceptable if premenopausal); regular or imperceptible wall; no solid area or mural nodule; \u003c 10 cm diameter.";
                        final String probablyBenignString = "Benign-appearing cyst except for one or more of the following: angulated margins, not round or oval in shape, a portion of the cyst is poorly imaged, or image has reduced signal-to-noise ratio";
                        final String otherImagingFeaturesString = "Features include a solid component, mural nodule, septations, higher than fluid attenuation, or layering hemorrhage if postmenopausal";

                        if(incidental_imaging_features == 0)
                        {
                            adnexa_ct_imaging_features_textview.setText(benignAppearingString);
                            adnexa_ct_imaging_features_textview.setVisibility(View.VISIBLE);

                            hideField(adnexa_ct_view, R.id.label_adnexa_ct_diagnostic_features, R.id.spinner_adnexa_ct_diagnostic_features);
                            adnexa_ct_diagnostic_features_textview.setVisibility(View.GONE);

                            enableField(adnexa_ct_view, R.id.label_adnexa_ct_menopause, R.id.spinner_adnexa_ct_menopause);
                            enableField(adnexa_ct_view, R.id.label_adnexa_ct_size, R.id.spinner_adnexa_ct_size);

                            setLayoutStatus(adnexa_ct_view, currentTab);
                        }
                        else if(incidental_imaging_features == 1)
                        {
                            adnexa_ct_imaging_features_textview.setText(probablyBenignString);
                            adnexa_ct_imaging_features_textview.setVisibility(View.VISIBLE);

                            hideField(adnexa_ct_view, R.id.label_adnexa_ct_diagnostic_features, R.id.spinner_adnexa_ct_diagnostic_features);
                            adnexa_ct_diagnostic_features_textview.setVisibility(View.GONE);

                            enableField(adnexa_ct_view, R.id.label_adnexa_ct_menopause, R.id.spinner_adnexa_ct_menopause);
                            enableField(adnexa_ct_view, R.id.label_adnexa_ct_size, R.id.spinner_adnexa_ct_size);

                            setLayoutStatus(adnexa_ct_view, currentTab);
                        }
                        else if(incidental_imaging_features == 2)
                        {
                            adnexa_ct_imaging_features_textview.setText(otherImagingFeaturesString);
                            adnexa_ct_imaging_features_textview.setVisibility(View.VISIBLE);

                            enableField(adnexa_ct_view, R.id.label_adnexa_ct_diagnostic_features, R.id.spinner_adnexa_ct_diagnostic_features);
                            adnexa_ct_diagnostic_features_textview.setVisibility(View.VISIBLE);

                            hideField(adnexa_ct_view, R.id.label_adnexa_ct_menopause, R.id.spinner_adnexa_ct_menopause);
                            hideField(adnexa_ct_view, R.id.label_adnexa_ct_size, R.id.spinner_adnexa_ct_size);
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }

                });

                setSpinnerEntries(adnexa_ct_diagnostic_features_spinner, R.array.adenexa_ct_diagnostic_features_array);
                adnexa_ct_diagnostic_features_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        incidental_diagnostic_features = position;

                        if(incidental_diagnostic_features == 0)
                        {
                            adnexa_ct_diagnostic_features_textview.setText("");
                            adnexa_ct_diagnostic_features_textview.setVisibility(View.GONE);
                        }
                        else if(incidental_diagnostic_features == 1)   // paraovarian cyst
                        {
                            adnexa_ct_diagnostic_features_textview.setText("CT: Thin-walled adnexal cyst with separate adjacent normal appearing ovary\n\nMR: Same as CT");
                            adnexa_ct_diagnostic_features_textview.setVisibility(View.VISIBLE);
                        }
                        else if(incidental_diagnostic_features == 2)   // hydrosalpinx
                        {
                            adnexa_ct_diagnostic_features_textview.setText("CT: Tubular shape, waist sign, incomplete septations, separate ovary\n\nMR: Same as CT");
                            adnexa_ct_diagnostic_features_textview.setVisibility(View.VISIBLE);
                        }
                        else if(incidental_diagnostic_features == 3)   // peritoneal inclusion cyst
                        {
                            adnexa_ct_diagnostic_features_textview.setText("CT: Angular margins of pelvic fluid collection, spider-web pattern (septated fluid around central ovary), appropriate clinical history\n\nMR: Same as CT");
                            adnexa_ct_diagnostic_features_textview.setVisibility(View.VISIBLE);
                        }
                        else if(incidental_diagnostic_features == 4)   // cystic teratoma
                        {
                            adnexa_ct_diagnostic_features_textview.setText("CT: Ovarian mass containing fat, calcification\n\nMR: Ovarian mass containing lipid and/or fat; calcium better detected on CT");
                            adnexa_ct_diagnostic_features_textview.setVisibility(View.VISIBLE);
                        }
                        else if(incidental_diagnostic_features == 5)   // endometrioma
                        {
                            adnexa_ct_diagnostic_features_textview.setText("CT: No specific features\n\nMR: Hyperintense T1 signal, T2 shading, multiplicity, other findings of endometriosis");
                            adnexa_ct_diagnostic_features_textview.setVisibility(View.VISIBLE);
                        }
                        else if(incidental_diagnostic_features == 6)   // leiomyoma
                        {
                            adnexa_ct_diagnostic_features_textview.setText("CT: Characteristic calcifications, uterine origine\n\nMR: Low T2 signal, beak of uterine tissue around portion of the mass, bridging vessels between mass and uterus, separate ovary, enhance more than ovarian fibromas");
                            adnexa_ct_diagnostic_features_textview.setVisibility(View.VISIBLE);
                        }
                        else if(incidental_diagnostic_features == 7)   // ovarian fibroma
                        {
                            adnexa_ct_diagnostic_features_textview.setText("CT: No specific features\n\nMR: Low T2 signal in well-circumscribed mass in ovary, enhancement less than myometrium; larger lesions can have heterogeneous high T2 signal and heterogeneous enhancement");
                            adnexa_ct_diagnostic_features_textview.setVisibility(View.VISIBLE);
                        }
                        else if(incidental_diagnostic_features == 8)   // malignancy
                        {
                            adnexa_ct_diagnostic_features_textview.setText("CT: Enhancing mural nodules, thick septa, ascites, peritoneal implants, para-aortic adenopathy\n\nMR: Same as CT");
                            adnexa_ct_diagnostic_features_textview.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }
                });

                setSpinnerEntries(adnexa_ct_menopause_spinner, R.array.adnexa_ct_menopause_array);
                adnexa_ct_menopause_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        incidental_menopause = position;

                        setLayoutStatus(adnexa_ct_view, currentTab);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }

                });

                // size spinner entries will change depending on benign vs probably benign, and menopausal state
                adnexa_ct_size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        incidental_size = position;
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
            case INCIDENTAL:
                // set size spinner entries
                final Spinner size_spinner = (Spinner) view.findViewById(R.id.spinner_adnexa_ct_size);

                if (incidental_imaging_features == 0)
                {
                    if(incidental_menopause == 0)
                    {
                        // benign-appearing and pre-menopausal
                        setSpinnerEntries(size_spinner, R.array.adnexa_ct_size_5_array);
                    }
                    else if(incidental_menopause == 1)
                    {
                        // benign-appearing and early post-menopausal
                        setSpinnerEntries(size_spinner, R.array.adnexa_ct_size_35_array);
                    }
                    else if(incidental_menopause == 2)
                    {
                        // benign-appearing and late post-menopausal
                        setSpinnerEntries(size_spinner, R.array.adnexa_ct_size_3_array);
                    }
                }
                if (incidental_imaging_features == 1)
                {
                    if(incidental_menopause == 0)
                    {
                        // probably benign and pre-menopausal
                        setSpinnerEntries(size_spinner, R.array.adnexa_ct_size_35_array);
                    }
                    else if(incidental_menopause == 1)
                    {
                        // probably benign and early post-menopausal
                        setSpinnerEntries(size_spinner, R.array.adnexa_ct_size_3_array);
                    }
                    else if(incidental_menopause == 2)
                    {
                        // probably benign and late post-menopausal
                        setSpinnerEntries(size_spinner, R.array.adnexa_ct_size_1_array);
                    }
                }

                break;
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
            case INCIDENTAL:

                String findings = "";
                String benign = "This is likely benign, and no follow-up is recommended.";
                String furtherEvalUS = "Recommend further evaluation with dedicated ultrasound exam.";
                String nofollowup = "No follow-up is recommended.";

                if (incidental_imaging_features == 0)
                {
                    findings = "There is a benign-appearing adnexal cyst";

                    if(incidental_menopause == 0)
                    {
                        // benign-appearing and pre-menopausal
                        if(incidental_size == 0)
                        {
                            findings += ", measuring 5 cm or less";
                            guidelines[RESULTS_FOLLOWUP] = benign;
                        }
                        else
                        {
                            findings += ", measuring over 5 cm";
                            guidelines[RESULTS_FOLLOWUP] = "Recommend ultrasound follow-up in 6 to 12 weeks.";
                        }

                        findings += " in a pre-menopausal female";
                    }
                    else if(incidental_menopause == 1)
                    {
                        // benign-appearing and early post-menopausal
                        if(incidental_size == 0)
                        {
                            findings += ", measuring 3 cm or less";
                            guidelines[RESULTS_FOLLOWUP] = benign;
                        }
                        else if(incidental_size == 1)
                        {
                            findings += ", measuring between 3 to 5 cm";
                            guidelines[RESULTS_FOLLOWUP] = "Recommend ultrasound follow-up in 6 to 12 months.";

                        }
                        else
                        {
                            findings += ", measuring over 5 cm";
                            guidelines[RESULTS_FOLLOWUP] = furtherEvalUS;
                        }

                        findings += " in an early post-menopausal female";
                    }
                    else if(incidental_menopause == 2)
                    {
                        // benign-appearing and late post-menopausal
                        if(incidental_size == 0)
                        {
                            findings += ", measuring 3 cm or less";
                            guidelines[RESULTS_FOLLOWUP] = benign;
                        }
                        else
                        {
                            findings += ", measuring over 3 cm";
                            guidelines[RESULTS_FOLLOWUP] = furtherEvalUS;
                        }

                        findings += " in an late post-menopausal female";
                    }
                }
                else if (incidental_imaging_features == 1)
                {
                    findings = "There is a probable benign adnexal cyst";

                    if(incidental_menopause == 0)
                    {
                        // probably benign and pre-menopausal
                        if(incidental_size == 0)
                        {
                            findings += ", measuring 3 cm or less";
                            guidelines[RESULTS_FOLLOWUP] = benign;
                        }
                        else if(incidental_size == 1)
                        {
                            findings += ", measuring between 3 to 5 cm";
                            guidelines[RESULTS_FOLLOWUP] = "Recommend ultrasound follow-up in 6 to 12 weeks.";
                        }
                        else
                        {
                            findings += ", measuring over 5 cm";
                            guidelines[RESULTS_FOLLOWUP] = furtherEvalUS;
                        }

                        findings += " in a pre-menopausal female";
                    }
                    else if(incidental_menopause == 1)
                    {
                        // probably benign and early post-menopausal
                        if(incidental_size == 0)
                        {
                            findings += ", measuring 3 cm or less";
                            guidelines[RESULTS_FOLLOWUP] = benign;
                        }
                        else
                        {
                            findings += ", measuring over 3 cm";
                            guidelines[RESULTS_FOLLOWUP] = furtherEvalUS;
                        }

                        findings += " in an early post-menopausal female";
                    }
                    else if(incidental_menopause == 2)
                    {
                        // probably benign and late post-menopausal
                        if(incidental_size == 0)
                        {
                            findings += ", measuring 1 cm or less";
                            guidelines[RESULTS_FOLLOWUP] = benign;
                        }
                        else
                        {
                            findings += ", measuring over 1 cm";
                            guidelines[RESULTS_FOLLOWUP] = furtherEvalUS;
                        }

                        findings += " in a late post-menopausal female";
                    }
                }
                else
                {
                    // other
                    if(incidental_diagnostic_features == 0)
                    {
                        // indeterminate
                        findings = "Adnexal cystic mass with non-specific imaging features";
                        guidelines[RESULTS_FOLLOWUP] = furtherEvalUS;
                    }
                    else if(incidental_diagnostic_features == 1)   // paraovarian cyst
                    {
                        findings = "Adnexal cystic mass with features suggestive of a paraovarian cyst";
                        guidelines[RESULTS_FOLLOWUP] = nofollowup;
                    }
                    else if(incidental_diagnostic_features == 2)   // hydrosalpinx
                    {
                        findings = "Adnexal cystic mass with features suggestive of a hydrosalpinx";
                        guidelines[RESULTS_FOLLOWUP] = nofollowup;
                    }
                    else if(incidental_diagnostic_features == 3)   // peritoneal inclusion cyst
                    {
                        findings = "Adnexal cystic mass with features suggestive of a peritoneal inclusion cyst";
                        guidelines[RESULTS_FOLLOWUP] = nofollowup;
                    }
                    else if(incidental_diagnostic_features == 4)   // cystic teratoma
                    {
                        findings = "Adnexal cystic mass with features suggestive of a cystic teratoma";
                        guidelines[RESULTS_FOLLOWUP] = "Depending on patient age and comorbidities, surgical consultation versus periodic follow-up may be considered.";
                    }
                    else if(incidental_diagnostic_features == 5)   // endometrioma
                    {
                        findings = "Adnexal cystic mass with features suggestive of an endometrioma";
                        guidelines[RESULTS_FOLLOWUP] = furtherEvalUS;
                    }
                    else if(incidental_diagnostic_features == 6)   // leiomyoma
                    {
                        findings = "Adnexal cystic mass with features suggestive of a leiomyoma";
                        guidelines[RESULTS_FOLLOWUP] = nofollowup;
                    }
                    else if(incidental_diagnostic_features == 7)   // ovarian fibroma
                    {
                        findings = "Adnexal cystic mass with features suggestive of an ovarian fibroma";
                        guidelines[RESULTS_FOLLOWUP] = nofollowup;
                    }
                    else if(incidental_diagnostic_features == 8)   // malignancy
                    {
                        findings = "Adnexal cystic mass with features suggestive of malignancy";
                        guidelines[RESULTS_FOLLOWUP] = "Consider dedicated imaging study for staging in addition to surgical evaluation.";
                    }
                }

                guidelines[RESULTS_IMPRESSION] = findings + ".";

                guidelines[RESULTS_REFERENCE_TEXT] = "Managing Incidental Findings on Abdominal and Pelvic CT and MRI, Part 1: White Paper of the ACR Incidental Findings Committee II on Adnexal Findings";
                break;

            case US:

                break;

            default:
                break;
        }


        return guidelines;
    }

}
