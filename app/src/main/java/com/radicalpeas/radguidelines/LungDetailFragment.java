package com.radicalpeas.radguidelines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

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


public class LungDetailFragment extends OrganDetailFragment
{
    private int incidental_nodule_type = 0;
    private int incidental_nodule_subtype = 0;
    private int incidental_risk_level = 0;
    private int incidental_number = 0;
    private int incidental_size = 0;

    private enum Tab
    {
        INCIDENTAL(0), SCREEN(1);

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
                case SCREEN:
                    return "Screening on CT";
                default:
                    return "";
            }
        }
    }

    public static final LungDetailFragment newInstance()
    {
        LungDetailFragment f = new LungDetailFragment();

        tab_titles = new ArrayList<>();
        tab_titles.add(Tab.INCIDENTAL.getValue(), Tab.INCIDENTAL.toString());
        tab_titles.add(Tab.SCREEN.getValue(), Tab.SCREEN.toString());

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
                view = inflater.inflate(R.layout.lung_incidental_ct_layout, container, false);

                final View incidental_view = view;

                final Spinner incidental_type_spinner = (Spinner) view.findViewById(R.id.spinner_lung_incidental_nodule_type);
                final Spinner incidental_risk_spinner = (Spinner) view.findViewById(R.id.spinner_lung_incidental_risk_level);
                final Spinner incidental_number_spinner = (Spinner) view.findViewById(R.id.spinner_lung_incidental_number);
                final Spinner incidental_size_spinner = (Spinner) view.findViewById(R.id.spinner_lung_incidental_size);

                incidental_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        incidental_nodule_type = position;

                        setLayoutStatus(incidental_view, currentTab);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                    }
                });

                incidental_risk_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        incidental_risk_level = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                    }
                });

                incidental_number_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        incidental_number = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                    }
                });

                incidental_size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        incidental_size = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                    }
                });

                break;

            case SCREEN:

                //view = inflater.inflate(R.layout.lung_screen_ct_layout, container, false);
                view = null;

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
                final Spinner size_spinner = (Spinner) view.findViewById(R.id.spinner_lung_incidental_size);

                if(incidental_nodule_type == 0)
                {
                    // solid nodule
                    enableField(view, R.id.label_lung_incidental_risk_level, R.id.spinner_lung_incidental_risk_level);

                    // change size entry thresholds
                    setSpinnerEntries(size_spinner, R.array.lung_incidental_solid_size_array);

                    if(incidental_size > 0)
                    {
                        // was >= 6
                        incidental_size = 1;    // set to 6 - 8
                    }
                    else
                    {
                        incidental_size = 0;
                    }

                    size_spinner.setSelection(incidental_size);
                }
                else
                {
                    // subsolid nodule
                    disableField(view, R.id.label_lung_incidental_risk_level, R.id.spinner_lung_incidental_risk_level);

                    setSpinnerEntries(size_spinner, R.array.lung_incidental_subsolid_size_array);
                    if(incidental_size > 0)
                    {
                        // was 6 - 8 or >= 8
                        incidental_size = 1;    // set to >= 6
                    }
                    else
                    {
                        incidental_size = 0;
                    }

                    size_spinner.setSelection(incidental_size);

                }
                break;


            case SCREEN:

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

                String findings= "";
                String noFollowup = "No routine follow-up.";
                String option12moCT = "Optional follow-up CT at 12 months.";
                String followup6to12mo_18to24mo = "Follow-up CT at 6 to 12 months, then consider CT at 18 to 24 months.";
                String followup6to12mo_18to24mo_high = "Follow-up CT at 6 to 12 months, then CT at 18 to 24 months.";
                String followup3to6mo_18to24mo = "Follow-up CT at 3 to 6 months, then consider CT at 18 to 24 months.";
                String followup3to6mo_18to24mo_high = "Follow-up CT at 3 to 6 months, then CT at 18 to 24 months.";
                String followup3mo_PET_biopsy = "Consider CT at 3 months, PET-CT, or tissue sampling.";

                String followup3to6mo_2and4yr = "Follow-up CT at 3 to 6 months. If stable, consider CT at 2 and 4 years.";

                if(incidental_nodule_type == 0)
                {
                    // solid nodule

                    if(incidental_number == 0)
                    {
                        // single

                        findings = "There is a solid nodule";

                        if(incidental_risk_level == 0)
                        {
                            // single, low risk
                            if(incidental_size == 0)
                            {
                                // <6mm
                                findings += " less than 6 mm";
                                guidelines[RESULTS_FOLLOWUP] = noFollowup;
                            }
                            else if(incidental_size == 1)
                            {
                                // 6 to 8 mm
                                findings += " between 6 to 8 mm";
                                guidelines[RESULTS_FOLLOWUP] = followup6to12mo_18to24mo;
                            }
                            else
                            {
                                // >8mm
                                findings += " larger than 8 mm";
                                guidelines[RESULTS_FOLLOWUP] = followup3mo_PET_biopsy;
                            }

                            findings += " in a low risk patient";
                        }
                        else
                        {
                            // single high risk
                            if(incidental_size == 0)
                            {
                                // <6mm
                                findings += " less than 6 mm";
                                guidelines[RESULTS_FOLLOWUP] = option12moCT;
                                guidelines[RESULTS_COMMENTS] = "Nodules less than 6 mm do not require routine follow-up, but certain patients at high risk with suspicious nodule morphology, upper lobe location, or both may warrant 12-month follow-up.";
                            }
                            else if(incidental_size == 1)
                            {
                                // 6 to 8 mm
                                findings += " between 6 to 8 mm";
                                guidelines[RESULTS_FOLLOWUP] = followup6to12mo_18to24mo_high;
                            }
                            else
                            {
                                // >8mm
                                findings += " more than 8 mm";
                                guidelines[RESULTS_FOLLOWUP] = followup3mo_PET_biopsy;
                            }


                            findings += " in a high risk patient";
                        }
                    }
                    else
                    {
                        // multiple

                        findings = "There are multiple solid nodules, with the largest measuring";

                        if(incidental_risk_level == 0)
                        {
                            // multiple, low risk
                            if(incidental_size == 0)
                            {
                                // <6mm
                                findings += " less than 6 mm";
                                guidelines[RESULTS_FOLLOWUP] = noFollowup;
                            }
                            else if(incidental_size == 1)
                            {
                                // 6 to 8 mm
                                findings += " between 6 to 8 mm";
                                guidelines[RESULTS_FOLLOWUP] = followup3to6mo_18to24mo;
                            }
                            else
                            {
                                // >8mm
                                findings += " larger than 8 mm";
                                guidelines[RESULTS_FOLLOWUP] = followup3to6mo_18to24mo;
                            }

                            findings += " in a low risk patient";

                        }
                        else
                        {
                            // multiple, high risk
                            if(incidental_size == 0)
                            {
                                // <6mm
                                findings += " less than 6 mm";
                                guidelines[RESULTS_FOLLOWUP] = option12moCT;
                            }
                            else if(incidental_size == 1)
                            {
                                // 6 to 8 mm
                                findings += " between 6 to 8 mm";
                                guidelines[RESULTS_FOLLOWUP] = followup3to6mo_18to24mo_high;
                            }
                            else
                            {
                                // >8mm
                                findings += " larger than 8 mm";
                                guidelines[RESULTS_FOLLOWUP] = followup3to6mo_18to24mo_high;
                            }

                            findings += " in a high risk patient";
                        }

                        guidelines[RESULTS_COMMENTS] = "Use most suspicious nodule as guide to management. Follow-up intervals may vary according to size and risk.";
                    }

                }
                else
                {
                    // subsolid nodule
                    if(incidental_number == 0)
                    {
                        // single
                        if(incidental_nodule_type == 1)
                        {
                            // ground glass subsolid nodule

                            findings = "There is a ground glass subsolid nodule";

                            if(incidental_size == 0)
                            {
                                // <6mm
                                findings += " measuring less than 6 mm";
                                guidelines[RESULTS_FOLLOWUP] = noFollowup;
                            }
                            else
                            {
                                // >=6mm
                                findings += " measuring 6 mm or more";
                                guidelines[RESULTS_FOLLOWUP] = "Follow-up CT at 6 to 12 months to confirm persistence, then CT every 2 years until 5 years.";
                            }

                            guidelines[RESULTS_COMMENTS] = "In certain suspicious nodules less than 6 mm, consider follow-up at 2 and 4 years. If there is a solid component or growth develps, consider resection.";
                        }
                        else if(incidental_nodule_type == 2)
                        {
                            // part solid subsolid nodule

                            findings = "There is a part-solid nodule";

                            if(incidental_size == 0)
                            {
                                // <6mm
                                findings += " measuring less than 6 mm";
                                guidelines[RESULTS_FOLLOWUP] = noFollowup;
                            }
                            else
                            {
                                // >=6mm
                                findings += " measuring 6 mm or more";
                                guidelines[RESULTS_FOLLOWUP] = "Follow-up CT at 3 to 6 months to confirm persistence. If unchanged and solid component remains less than 6 mm, annual CT should be performed for 5 years.";
                            }

                            guidelines[RESULTS_COMMENTS] = "In practice, part-solid nodules cannot be defined as such until 6 mm or larger, and nodules less than 6 mm do not usually require follow-up. Persistent part-solid nodules with solid components 6 mm or larger should be considered highly suspicious.";

                        }
                    }
                    else
                    {
                        // multiple

                        findings = "There are multiple subsolid nodules with the largest";

                        if(incidental_size == 0)
                        {
                            // <6mm
                            findings += " measuring less than 6 mm";
                            guidelines[RESULTS_FOLLOWUP] = followup3to6mo_2and4yr;
                            guidelines[RESULTS_COMMENTS] = "Multiple 6 mm pure ground-glass nodules are usually benign, but consider follow-up in selected patients at high risk at 2 and 4 years.";
                        }
                        else
                        {
                            // >=6mm
                            findings += " measuring 6 mm or more";
                            guidelines[RESULTS_FOLLOWUP] = "Follow-up CT at 3 to 6 months. Subsequent management based on the most suspicious nodules.";
                        }
                    }

                }

                guidelines[RESULTS_IMPRESSION] = findings + ".";

                guidelines[RESULTS_REFERENCE_TEXT] = "Guidelines for Management of Incidental Pulmonary Nodules Detected on CT Images: From the Fleischner Society 2017";
                guidelines[RESULTS_REFERENCE_LINK] = "http://pubs.rsna.org/doi/pdf/10.1148/radiol.2017161659";
                break;

            case SCREEN:

                break;

            default:
                break;
        }




        return guidelines;

    }

}
