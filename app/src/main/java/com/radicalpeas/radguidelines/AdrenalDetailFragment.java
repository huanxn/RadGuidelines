package com.radicalpeas.radguidelines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huanx on 4/17/2017.
 */

public class AdrenalDetailFragment extends OrganDetailFragment
{
    private int cyst_or_solid = 0;

    private int cyst_wall = 0;
    private int cyst_attenuation = 0;
    private int cyst_septation = 0;
    private int cyst_calcification = 0;
    private int cyst_enhancement = 0;
    private int cyst_size = 0;

    private int risk_level = 0;
    private int solid_size = 0;

    // called by tabbedFragment
    // create the organ specific layout for each tab position
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState, int tabPosition)
    {
        View view;

        switch(tabPosition)
        {
            // RENAL CT
            case 0:
                view = inflater.inflate(R.layout.renal_ct_layout, container, false);

                final FrameLayout renalLesionContainer = (FrameLayout) view.findViewById(R.id.renal_lesion_container);

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
                            // Cystic
                            // ((ViewGroup) rootView.findViewById(R.id.renal_cyst_group)).setVisibility(View.VISIBLE);

                            if(renalLesionContainer != null)
                            {
                                renalLesionContainer.removeAllViews();
                                renalLesionContainer.addView(getActivity().getLayoutInflater().inflate(R.layout.renal_cyst_layout, null, false));

                                final Spinner cyst_wall_spinner = (Spinner) renalLesionContainer.findViewById(R.id.spinner_cyst_wall);
                                final Spinner cyst_attenuation_spinner = (Spinner) renalLesionContainer.findViewById(R.id.spinner_cyst_attenuation);
                                final Spinner cyst_septation_spinner = (Spinner) renalLesionContainer.findViewById(R.id.spinner_cyst_septations);
                                final Spinner cyst_calcifications_spinner = (Spinner) renalLesionContainer.findViewById(R.id.spinner_cyst_calcifications);
                                final Spinner cyst_enhancement_spinner = (Spinner) renalLesionContainer.findViewById(R.id.spinner_cyst_enhancement);
                                final Spinner cyst_size_spinner = (Spinner) renalLesionContainer.findViewById(R.id.spinner_cyst_size);


                                // CYST WALL
                                ArrayAdapter<CharSequence> cyst_wall_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.renal_cyst_wall_array, R.layout.spinner_dropdown_item_multiline);
                                cyst_wall_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                                cyst_wall_spinner.setAdapter(cyst_wall_adapter);

                                cyst_wall_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                        cyst_wall_spinner.setSelection(position);
                                        cyst_wall = position;
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
                                cyst_enhancement_spinner.setAdapter(cyst_septation_adapter);

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
                            }
                        }
                        else
                        {
                            // Solid

                            //((ViewGroup) rootView.findViewById(R.id.renal_cyst_group)).setVisibility(View.INVISIBLE);

                            if (renalLesionContainer != null)
                            {

                                renalLesionContainer.removeAllViews();
                                renalLesionContainer.addView(getActivity().getLayoutInflater().inflate(R.layout.renal_solid_layout, null, false));

                                Spinner risk_level_spinner = (Spinner) renalLesionContainer.findViewById(R.id.spinner_demographic);
                                ArrayAdapter<CharSequence> risk_level_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.demographic_array, R.layout.spinner_dropdown_item_multiline);
                                risk_level_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                                risk_level_spinner.setAdapter(risk_level_adapter);
                                risk_level_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
                                    {
                                        risk_level = position;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parentView) {
                                    }
                                });

                                Spinner size_spinner = (Spinner) renalLesionContainer.findViewById(R.id.spinner_renal_solid_size);
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

                            }
                        }
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
        if(mViewPager.getCurrentItem() == 0)
        {
            String findings;

            if(cyst_or_solid == 0)
            {
                if (cyst_wall == 0)
                {
                    // BOSNIAK 1
                    if (cyst_attenuation == 0 && cyst_septation == 0 && cyst_calcification == 0 && cyst_enhancement == 0)
                    {
                        guidelines[0] = "VALID";
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is a simple fluid attenuating cyst without septations, calcifications, or enhancement.";
                        guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Bosniak 1: simple cyst";
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "No follow up necessary.";
                    }
                    else if (cyst_attenuation == 0 && cyst_septation == 1 && cyst_calcification == 0 && cyst_enhancement <= 1)
                    {
                        findings = "There is a simple fluid attenuating cyst with a few thin septa";

                        guidelines[0] = "VALID";
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = findings;
                        guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Bosniak 2: minimally complex cyst";
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "No follow up necessary.";
                    }
                    else if (cyst_attenuation == 0 && cyst_septation == 0 && cyst_calcification == 1 && cyst_enhancement <= 1)
                    {
                        findings = "There is a simple fluid attenuating cyst with a few thin calcifications.";

                        guidelines[0] = "VALID";
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = findings;
                        guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Bosniak 2: minimally complex cyst";
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "No follow up necessary.";
                    }
                    else if (cyst_attenuation == 0 && cyst_septation == 1 && cyst_calcification == 1 && cyst_enhancement <= 1)
                    {
                        findings = "There is a simple fluid attenuating cyst with a few thin septa and calcifications.";

                        guidelines[0] = "VALID";
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = findings;
                        guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Bosniak 2: minimally complex cyst";
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "No follow up necessary.";
                    }
                    else if (cyst_attenuation == 1 && cyst_size == 0 && cyst_septation <= 1 && cyst_calcification <= 1 && cyst_enhancement <= 1)
                    {
                        findings = "There is a hyperdense cyst less than 3 cm";
                        if (cyst_septation == 1 || cyst_calcification == 1)
                        {
                            findings += " with a few thin septa or thin calcifications.";
                        }

                        guidelines[0] = "VALID";
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = findings;
                        guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Bosniak 2: minimally complex cyst";
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "No follow up necessary.";
                    }
                    else if (cyst_septation == 2 && cyst_calcification <= 2 && cyst_enhancement <= 1)
                    {
                        findings = "There is a ";
                        if (cyst_attenuation == 1)
                        {
                            findings += "hyperdense ";
                        }
                        findings += "cyst with multiple thin or minimally smooth thickened septa,";
                        if (cyst_calcification == 1)
                        {
                            findings += " with thin calcifications";
                        }
                        else if (cyst_calcification == 2)
                        {
                            findings += " with thick or nodular calcifications";
                        }

                        if (cyst_enhancement == 1)
                        {
                            findings += ", perceived but not measurable enhancement of thin smooth septa";
                        }

                        guidelines[0] = "VALID";
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = findings;
                        guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Bosniak 2F: mildly complex cyst";
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Follow up CT or MR at 6 and 12 months, then yearly for 5 years.";
                        guidelines[OrganDetailActivity.RESULTS_STATISTICS] = "Percentage malignant: 5%";
                    }
                    else if (cyst_septation <= 1 && cyst_calcification == 2 && cyst_enhancement <= 1)
                    {
                        findings = "There is a ";
                        if (cyst_attenuation == 1)
                        {
                            findings += "hyperdense ";
                        }
                        findings += "cyst ";

                        if (cyst_septation == 1)
                        {
                            findings += "with a few thin septa and ";
                        }
                        findings += "with thick or nodular calcifications";

                        if (cyst_enhancement == 1)
                        {
                            findings += ", perceived but not measurable enhancement of thin smooth septa";
                        }

                        guidelines[0] = "VALID";
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = findings;
                        guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Bosniak 2F: mildly complex cyst";
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Follow up CT or MR at 6 and 12 months, then yearly for 5 years.";
                        guidelines[OrganDetailActivity.RESULTS_STATISTICS] = "Percentage malignant: 5%";
                    }
                    else if (cyst_attenuation == 1 && cyst_size == 1 && (cyst_septation <= 2 || cyst_calcification <= 2) && cyst_enhancement <= 1)
                    {
                        findings = "There is a hyperdense cyst larger than 3 cm";
                        if (cyst_septation == 2 || cyst_calcification == 2)
                        {
                            findings += ", with multiple thin or minimally smooth thickened septa, thick or nodular calcifications";
                        }
                        if (cyst_enhancement == 1)
                        {
                            findings += ", perceived but not measurable enhancement of thin smooth septa";
                        }

                        guidelines[0] = "VALID";
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = findings;
                        guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Bosniak 2F: mildly complex cyst";
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Follow up CT or MR at 6 and 12 months, then yearly for 5 years.";
                        guidelines[OrganDetailActivity.RESULTS_STATISTICS] = "Percentage malignant: 5%";
                    }
                    else if (cyst_septation == 3 || cyst_enhancement == 2)
                    {
                        findings = "There is a ";
                        if (cyst_attenuation == 1)
                        {
                            findings += "hyperdense ";
                        }
                        findings += "cyst with ";

                        if (cyst_septation == 3)
                        {
                            findings += "thickened irregular or smooth septa, ";
                        }

                        if (cyst_enhancement == 2)
                        {
                            findings += "measurable enhancement, ";
                        }

                        guidelines[0] = "VALID";
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = findings;
                        guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Bosniak 3: Indeterminate cyst";
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Partial nephrectomy or radiofrequency ablation in elderly or poor surgical candidates";
                        guidelines[OrganDetailActivity.RESULTS_STATISTICS] = "Percentage malignant: 55%";
                    }
                    else
                    {
                        guidelines[0] = "ERROR";
                    }
                }
                else if (cyst_wall == 1)
                {
                    findings = "There is a ";
                    if (cyst_attenuation == 1)
                    {
                        findings += "hyperdense ";
                    }
                    findings += "cyst with thickened irregular or smooth walls or septa, with measurable enhancement.";

                    guidelines[0] = "VALID";
                    guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = findings;
                    guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Bosniak 3: Indeterminate cyst";
                    guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Partial nephrectomy or radiofrequency ablation in elderly or poor surgical candidates";
                    guidelines[OrganDetailActivity.RESULTS_STATISTICS] = "Percentage malignant: 55%";
                }
                else if (cyst_wall == 2)
                {
                    guidelines[0] = "VALID";
                    guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is a cystic lesion with enhancing soft tissue components adjacent to or separate from the wall or septa.";
                    guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Bosniak 4: Likely malignant";
                    guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Partial or total nephrectomy";
                }
                else
                {
                    guidelines[0] = "ERROR";
                }

                guidelines[OrganDetailActivity.RESULTS_REFERENCE_TEXT] = "Managing Incidental Findings on Abdominal CT: White Paper of the ACR Incidental Findings Committee";
                guidelines[OrganDetailActivity.RESULTS_REFERENCE_LINK] = "http://www.jacr.org/article/S1546-1440(10)00330-3/fulltext#sec11";
                //         guidelines[OrganDetailActivity.RESULTS_REFERENCE_IMAGE] = "drawable/renal_ct_guidelines";
            }
            else
            {
                // solid

                if(risk_level == 0)
                {
                    if(solid_size == 0)
                    {
                        guidelines[0] = "VALID";
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is a very small solid mass measuring less than 1 cm.";
                        guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Differential diagnosis includes renal cell carcinoma, oncocytoma, and angiomyolipoma.";
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Recommend observation until size is greater than 1 cm.";

                    }
                    else if(solid_size == 1)
                    {
                        guidelines[0] = "VALID";
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is a small solid mass measuring between 1 to 3 cm.";
                        guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Most likely renal cell carcinoma.";
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "If hyperattenuating and homogenously enhance, consider MRI and percutaneous biopsy to diagnose angiomyolipoma with minimal fat.  Otherwise surgical resection is recommended.";
                    }
                    else if(solid_size == 2)
                    {
                        guidelines[0] = "VALID";
                        guidelines[0] = "VALID";
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is a large mass larger than 3 cm.";
                        guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Most likely renal cell carcinoma.";
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Surgical resection is recommended. Angiomyolipoma with minimal fat, oncocytoma, other benign neoplasms may be found at surgery.";
                    }
                }
                else if(risk_level == 1)
                {
                    if(solid_size == 0)
                    {
                        guidelines[0] = "VALID";
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is a very small solid mass measuring less than 1 cm.";
                        guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Differential diagnosis includes renal cell carcinoma, oncocytoma, and angiomyolipoma.";
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "In a patient with high risk comorbities, recommend observation until size is greater than 1.5 cm.";

                    }
                    else if(solid_size == 1)
                    {
                        guidelines[0] = "VALID";
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is a small solid mass measuring between 1 to 3 cm.";
                        guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Most likely renal cell carcinoma.";
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "If hyperattenuating and homogenously enhance, consider MRI and percutaneous biopsy to diagnose angiomyolipoma with minimal fat.  Otherwise surgical resection or observation is recommended in a patient with high risk comorbities.";
                    }
                    else if(solid_size == 2)
                    {
                        guidelines[0] = "VALID";
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "There is a large mass larger than 3 cm.";
                        guidelines[OrganDetailActivity.RESULTS_CLASSIFICATION] = "Most likely renal cell carcinoma.";
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Surgical resection or observation is recommended in a patient with high risk comorbities. Angiomyolipoma with minimal fat, oncocytoma, other benign neoplasms may be found at surgery.";
                    }
                }
                else
                {
                    guidelines[0] = "ERROR";
                }

                guidelines[OrganDetailActivity.RESULTS_REFERENCE_TEXT] = "Managing Incidental Findings on Abdominal CT: White Paper of the ACR Incidental Findings Committee";
                guidelines[OrganDetailActivity.RESULTS_REFERENCE_LINK] = "http://www.jacr.org/article/S1546-1440(10)00330-3/fulltext#sec11";
            }

        }
        else
        {
        }

        return guidelines;

    }
}
