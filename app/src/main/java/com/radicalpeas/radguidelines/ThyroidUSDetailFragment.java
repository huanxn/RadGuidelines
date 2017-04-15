package com.radicalpeas.radguidelines;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

public class ThyroidUSDetailFragment extends Fragment
{
    private int composition = 0;
    private int echogenicity = 0;
    private int shape = 0;
    private int margin = 0;
    private int echogenic_foci = 0;
    private float noduleSize = 0;

    private View view;

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ThyroidUSDetailFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID))
        {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

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
        final View rootView = inflater.inflate(R.layout.thyroid_detail, container, false);
        view = rootView;

        // Show the dummy content as text in a TextView.
        if (mItem != null)
        {
            ((TextView) rootView.findViewById(R.id.organ_detail)).setText("Thyroid US");

            final FrameLayout thyroidLesionContainer = (FrameLayout) rootView.findViewById(R.id.thyroid_lesion_container);


            final SpinnerWithPrompt US_or_CT_spinner = (SpinnerWithPrompt) rootView.findViewById(R.id.spinner_thyroid_US_or_CT);
            List<String> US_or_CT_list = new ArrayList<String>();
            US_or_CT_list.add("Thyroid ultrasound");   // 0
            US_or_CT_list.add("Incidental CT thyroid nodule");    // 1
            US_or_CT_spinner.setItems(US_or_CT_list);

            US_or_CT_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    US_or_CT_spinner.setSelection(position);

                    if(position == 0)
                    {
                        // US
                        // ((ViewGroup) rootView.findViewById(R.id.renal_cyst_group)).setVisibility(View.VISIBLE);

                        if(thyroidLesionContainer != null)
                        {
                            thyroidLesionContainer.addView(getActivity().getLayoutInflater().inflate(R.layout.thyroid_us_layout, null, false));

                            final Spinner thyroid_composition_spinner = (Spinner) thyroidLesionContainer.findViewById(R.id.spinner_thyroid_composition);
                            final Spinner thyroid_echogenicity_spinner = (Spinner) thyroidLesionContainer.findViewById(R.id.spinner_thyroid_echogenicity);
                            final Spinner thyroid_shape_spinner = (Spinner) thyroidLesionContainer.findViewById(R.id.spinner_thyroid_shape);
                            final Spinner thyroid_margin_spinner = (Spinner) thyroidLesionContainer.findViewById(R.id.spinner_thyroid_margin);
                            final Spinner thyroid_echogenic_foci_spinner = (Spinner) thyroidLesionContainer.findViewById(R.id.spinner_thyroid_echogenic_foci);

                            final EditText nodule_size_EditText = (EditText) thyroidLesionContainer.findViewById(R.id.edittext_thyroid_nodule_size);

                            // THYROID COMPOSITION
                            ArrayAdapter<CharSequence> composition_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.thyroid_us_composition_array, R.layout.multiline_spinner_dropdown_item);
                            composition_adapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
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
                            ArrayAdapter<CharSequence> echogenicity_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.thyroid_us_echogenicity_array, R.layout.multiline_spinner_dropdown_item);
                            echogenicity_adapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
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
                            ArrayAdapter<CharSequence> shape_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.thyroid_us_shape_array, R.layout.multiline_spinner_dropdown_item);
                            shape_adapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
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
                            ArrayAdapter<CharSequence> margin_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.thyroid_us_margin_array, R.layout.multiline_spinner_dropdown_item);
                            margin_adapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
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
                            ArrayAdapter<CharSequence> echogenic_foci_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.thyroid_us_echogenic_foci_array, R.layout.multiline_spinner_dropdown_item);
                            echogenic_foci_adapter.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
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
                                    noduleSize = Float.valueOf(nodule_size_EditText.getText().toString());
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

                        }
                    }
                    else
                    {
                        // INCIDENTAL THYROID NODULE ON CT

                        //((ViewGroup) rootView.findViewById(R.id.renal_cyst_group)).setVisibility(View.INVISIBLE);

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
    public String[] getGuidelines()
    {
        //ArrayList<String> stringList = new ArrayList<String>();
        String[] guidelines = new String[7];

        for(int i = 0; i < guidelines.length; i++)
        {
            guidelines[i] = "";
        }

        String findings;
        int TIRADS_points = 0;

        if(composition == 2)    // mixed cystic and solid
        {
            TIRADS_points += 1;
        }
        else if(composition == 3)   // solid or almost completely solid
        {
            TIRADS_points += 2;
        }

        TIRADS_points += echogenicity;

        if(shape == 1)  // taller than wide
        {
            TIRADS_points += 3;
        }

        if(margin == 2 || margin == 3)
        {
            TIRADS_points += margin;
        }

        TIRADS_points += echogenic_foci;

        guidelines[0] = "VALID";

        if(TIRADS_points <= 1)
        {
            guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "TR1: Benign.";
            guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "No FNA";
        }
        else if(TIRADS_points == 2)
        {
            guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "TR2: Not suspicious.";
            guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "No FNA";
        }
        else if(TIRADS_points == 3)
        {
            if(noduleSize >= 2.5)
            {
                guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "TR3: Mildly Suspicious.";
                guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Given the large size over 2.5 cm, FNA biopsy is recommended.";
            }
            else if(noduleSize >= 1.5)
            {
                guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "TR3: Mildly Suspicious.";
                guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Given the size less than 2.5 cm, but greater than 1.5 cm, follow up is recommended.";
            }
            else
            {
                guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "TR3: Mildly Suspicious.";
                guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Given the size less than 1.5 cm, no follow up is recommended.";
            }
        }
        else if(TIRADS_points >= 4 && TIRADS_points <= 6)
        {
            guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "TR4: Moderately Suspicious.";
            guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "FNA if > 1.5 cm.  Follow if > 1 cm";
        }
        else
        {
            guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "TR5: Highly Suspicious.";
            guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "FNA if > 1 cm.  Follow if > 0.5 cm";
            guidelines[OrganDetailActivity.RESULTS_STATISTICS] = " ??% mallignant";
        }

        guidelines[OrganDetailActivity.RESULTS_REFERENCE_TEXT] = "Thyroid Imaging Reporting and Data System (TI-RADS) 2017";
        guidelines[OrganDetailActivity.RESULTS_REFERENCE_LINK] = "https://www.acr.org/Quality-Safety/Resources/TIRADS";
        guidelines[OrganDetailActivity.RESULTS_REFERENCE_IMAGE] = "drawable/thyroid_tirads_2017";


        return guidelines;
    }
}
