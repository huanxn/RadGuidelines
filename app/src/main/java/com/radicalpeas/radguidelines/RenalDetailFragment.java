package com.radicalpeas.radguidelines;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.radicalpeas.radguidelines.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a single Organ detail screen.
 * This fragment is either contained in a {@link OrganListActivity}
 * in two-pane mode (on tablets) or a {@link OrganDetailActivity}
 * on handsets.
 */

public class RenalDetailFragment extends Fragment
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




    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RenalDetailFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(OrganDetailActivity.ARG_ITEM_ID))
        {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(OrganDetailActivity.ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null)
            {
                appBarLayout.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.renal_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null)
        {
            ((TextView) rootView.findViewById(R.id.organ_detail)).setText("renal stuff");

            final FrameLayout renalLesionContainer = (FrameLayout) rootView.findViewById(R.id.renal_lesion_container);


            final SpinnerWithPrompt cyst_or_solid_spinner = (SpinnerWithPrompt) rootView.findViewById(R.id.spinner_cyst_or_solid);
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
                            ArrayAdapter<CharSequence> cyst_wall_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.renal_cyst_wall_array, R.layout.multiline_spinner_dropdown_item);
                            cyst_wall_adapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
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
                            ArrayAdapter<CharSequence> cyst_attenuation_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.renal_cyst_attenuation_array, R.layout.multiline_spinner_dropdown_item);
                            cyst_attenuation_adapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
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
                            ArrayAdapter<CharSequence> cyst_septation_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.renal_cyst_septation_array, R.layout.multiline_spinner_dropdown_item);
                            cyst_septation_adapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
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
                            ArrayAdapter<CharSequence> cyst_calcifications_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.renal_cyst_calcification_array, R.layout.multiline_spinner_dropdown_item);
                            cyst_calcifications_adapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
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
                            ArrayAdapter<CharSequence> cyst_enhancement_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.renal_cyst_enhancement_array, R.layout.multiline_spinner_dropdown_item);
                            cyst_enhancement_adapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
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
                            ArrayAdapter<CharSequence> cyst_size_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.renal_cyst_size_array, R.layout.multiline_spinner_dropdown_item);
                            cyst_size_adapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
                            cyst_size_spinner.setAdapter(cyst_size_adapter);

                            cyst_size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                    cyst_size_spinner.setSelection(position);
                                    cyst_size = position;

                                    List<String> testResult = new ArrayList<String>();
                                    testResult = getGuidelines();
                                    ((TextView) rootView.findViewById(R.id.organ_detail)).setText(testResult.get(1));
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

                            Spinner risk_level_spinner = (Spinner) renalLesionContainer.findViewById(R.id.spinner_risk_level);
                            ArrayAdapter<CharSequence> risk_level_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.risk_level_array, R.layout.multiline_spinner_dropdown_item);
                            risk_level_adapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
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
                            ArrayAdapter<CharSequence> size_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.renal_solid_size_array, R.layout.multiline_spinner_dropdown_item);
                            size_adapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
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

        }

        return rootView;
    }

    // if completed info, returns guideline recommendations, reference, link
    // else send error message
    public List<String> getGuidelines()
    {
        List<String> stringList = new ArrayList<String>();
        String findings;

        if(cyst_or_solid == 0)
        {
            if (cyst_wall == 0)
            {
                // BOSNIAK 1
                if (cyst_attenuation == 0 && cyst_septation == 0 && cyst_calcification == 0 && cyst_enhancement == 0)
                {
                    stringList.add("VALID");
                    stringList.add("Bosniak 1: simple cyst");
                    stringList.add("No follow up");
                }
                else if (cyst_attenuation == 0 && cyst_septation == 1 && cyst_calcification == 0 && cyst_enhancement <= 1)
                {
                    findings = "There is a simple fluid attenuating cyst with a few thin septa, classified as Bosniak 2 cyst.";

                    stringList.add("VALID");
                    stringList.add(findings);
                    stringList.add("No follow up");
                }
                else if (cyst_attenuation == 0 && cyst_septation == 0 && cyst_calcification == 1 && cyst_enhancement <= 1)
                {
                    findings = "There is a simple fluid attenuating cyst with a few thin calcifications, classified as Bosniak 2 cyst.";

                    stringList.add("VALID");
                    stringList.add(findings);
                    stringList.add("No follow up");
                }
                else if (cyst_attenuation == 0 && cyst_septation == 1 && cyst_calcification == 1 && cyst_enhancement <= 1)
                {
                    findings = "There is a simple fluid attenuating cyst with a few thin septa and calcifications, classified as Bosniak 2 cyst.";

                    stringList.add("VALID");
                    stringList.add(findings);
                    stringList.add("No follow up");
                }
                else if (cyst_attenuation == 1 && cyst_size == 0 && cyst_septation <= 1 && cyst_calcification <= 1 && cyst_enhancement <= 1)
                {
                    findings = "There is a hyperdense cyst less than 3 cm";
                    if (cyst_septation == 1 || cyst_calcification == 1)
                    {
                        findings += " with a few thin septa or thin calcifications";
                    }

                    findings += ", classified as Bosniak 2 cyst.";

                    stringList.add("VALID");
                    stringList.add(findings);
                    stringList.add("No follow up");
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
                    findings += ", classified as Bosniak 2F cyst.";
                    stringList.add("VALID");
                    stringList.add(findings);
                    stringList.add("Follow up US or CT in 6 months");
                    stringList.add("Percentage malignant: 5%");
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

                    findings += ", classified as Bosniak 2F cyst.";
                    stringList.add("VALID");
                    stringList.add(findings);
                    stringList.add("Follow up US or CT in 6 months");
                    stringList.add("Percentage malignant: 5%");
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
                    findings += ", classified as Bosniak 2F";

                    stringList.add("VALID");
                    stringList.add(findings);
                    stringList.add("Follow up US or CT in 6 months");
                    stringList.add("Percentage malignant: 5%");
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

                    findings += "classified as Bosniak 3: Indeterminate";

                    stringList.add("VALID");
                    stringList.add(findings);
                    stringList.add("Partial nephrectomy or radiofrequency ablation in elderly or poor surgical candidates");
                    stringList.add("Percentage malignant: 55%");
                }
                else
                {
                    stringList.add("ERROR");
                }
            }
            else if (cyst_wall == 1)
            {
                findings = "There is a ";
                if (cyst_attenuation == 1)
                {
                    findings += "hyperdense ";
                }
                findings += "cyst with thickened irregular or smooth walls or septa, with measurable enhancement, classified as Bosniak 3: Indeterminate";

                stringList.add("VALID");
                stringList.add(findings);
                stringList.add("Partial nephrectomy or radiofrequency ablation in elderly or poor surgical candidates");
                stringList.add("Percentage malignant: 55%");
            }
            else if (cyst_wall == 2)
            {
                stringList.add("VALID");
                stringList.add("There is a cystic lesion with enhancing soft tissue components adjacent to or separate from the wall or septa.");
                stringList.add("Partial or total nephrectomy");
            }
            else
            {
                stringList.add("ERROR");
            }
        }
        else
        {
            // solid

            if(risk_level == 0)
            {
                if(solid_size == 0)
                {
                    stringList.add("VALID");
                    stringList.add("Low risk, small size.");

                }
                else if(solid_size == 1)
                {
                    stringList.add("VALID");
                    stringList.add("Low risk, med size.");

                }
                else if(solid_size == 2)
                {
                    stringList.add("VALID");
                    stringList.add("Low risk, large size.");
                }
            }
            else if(risk_level == 1)
            {
                if(solid_size == 0)
                {
                    stringList.add("VALID");
                    stringList.add("high risk, small size.");

                }
                else if(solid_size == 1)
                {
                    stringList.add("VALID");
                    stringList.add("high risk, med size.");

                }
                else if(solid_size == 2)
                {
                    stringList.add("VALID");
                    stringList.add("high risk, large size.");
                }
            }
            else
            {
                stringList.add("ERROR");
            }

        }

        return stringList;
    }
}
