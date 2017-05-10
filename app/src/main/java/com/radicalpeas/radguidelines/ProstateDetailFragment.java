package com.radicalpeas.radguidelines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by huanx on 4/17/2017.
 */


/**
 * A fragment representing a single Organ detail screen.
 * This fragment is either contained in a {@link OrganListActivity}
 * in two-pane mode (on tablets) or a {@link OrganDetailActivity}
 * on handsets.
 */

/**
 * Created by huanx on 4/16/2017.
 */


public class ProstateDetailFragment extends OrganDetailFragment
{
    private int location = 0;
    private int T2 = 0;
    private int DWI = 0;
    private int enhancement = 0;
    private int size = 0;

    private enum Tab
    {
        PIRADS(0);

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
                case PIRADS:
                    return "PI-RADS";
                default:
                    return "";
            }
        }
    }

    public static final ProstateDetailFragment newInstance()
    {
        ProstateDetailFragment f = new ProstateDetailFragment();

        tab_titles = new ArrayList<>();
        tab_titles.add(Tab.PIRADS.getValue(), Tab.PIRADS.toString());

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
            case PIRADS:

                view = inflater.inflate(R.layout.prostate_pirads_layout, container, false);
                final View pirads_view = view;

                final Spinner location_spinner = (Spinner) view.findViewById(R.id.spinner_pirads_location);
                final Spinner t2_spinner = (Spinner) view.findViewById(R.id.spinner_pirads_t2);
                final Spinner dwi_spinner = (Spinner) view.findViewById(R.id.spinner_pirads_dwi);
                final Spinner enhancement_spinner = (Spinner) view.findViewById(R.id.spinner_pirads_enhancement);
                final Spinner size_spinner = (Spinner) view.findViewById(R.id.spinner_pirads_size);


                setSpinnerEntries(location_spinner, R.array.prostate_location_array);
                location_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        location = position;

                        if(location == 0)
                        {
                            // peripheral zone
                            setSpinnerEntries(t2_spinner, R.array.prostate_peripheral_T2_array);
                            t2_spinner.setSelection(T2);
                        }
                        else
                        {
                            // transition zone
                            setSpinnerEntries(t2_spinner, R.array.prostate_transition_T2_array);
                            t2_spinner.setSelection(T2);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                    }
                });

                setSpinnerEntries(t2_spinner, R.array.prostate_peripheral_T2_array);
                t2_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        T2 = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                    }
                });

                setSpinnerEntries(dwi_spinner, R.array.prostate_dwi_array);
                dwi_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        DWI = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                    }
                });

                setSpinnerEntries(enhancement_spinner, R.array.prostate_dce_array);
                enhancement_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        enhancement = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                    }
                });

                /*
                setSpinnerEntries(size_spinner, R.array.prostate_size_array);
                size_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        size = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                    }
                });
                */

                break;


            default:
                view = null;
        }


        return view;
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
            case PIRADS:

                String locationString;

                int piradsScore = 0;
                String[] piradsString = new String[5];
                piradsString[0] = "PIRADS 1 – Very low (clinically significant cancer is highly unlikely to be present)";
                piradsString[1] = "PIRADS 2 – Low (clinically significant cancer is unlikely to be present)";
                piradsString[2] = "PIRADS 3 – Intermediate (the presence of clinically significant cancer is equivocal)";
                piradsString[3] = "PIRADS 4 – High (clinically significant cancer is likely to bepresent)";
                piradsString[4] = "PIRADS 5 – Very high (clinically significant cancer is highly likely to be present)";

                if(location == 0)
                {
                    // peripheral zone
                    // PIRADS score for a PZ lesion is based on DWI unless the DWI score is PIRADS 3
                    // in this scenario, DCE is used to decide between PIRADS 3 (no focal or early enhancement) or upgrade to PIRADS 4 (focal and early enhancement present
                    locationString = "peripheral zone";
                    piradsScore = DWI+1;
                    if(piradsScore == 3 && enhancement == 1)
                    {
                        piradsScore += 1;
                    }
                }
                else
                {
                    // transition zone
                    // PIRADS score for a TZ lesion is based on T2W unless the T2W score is PIRADS 3.
                    // In this scenario, DWI is used to decide between PIRADS 3 (DWI score <5) or upgrade to PIRADS 4 (DWI score 5).
                    locationString = "transition zone";
                    piradsScore = T2+1;
                    if(piradsScore == 3 && DWI == 4)
                    {
                        piradsScore += 1;
                    }
                }

                guidelines[RESULTS_IMPRESSION] = "There is a PI-RADS " + piradsScore + " lesion in the " + locationString + ".";
                guidelines[RESULTS_CLASSIFICATION] = piradsString[piradsScore-1];
                guidelines[RESULTS_FOLLOWUP] = "";


                guidelines[RESULTS_REFERENCE_TEXT] = "PI-RADS v2";
                guidelines[RESULTS_REFERENCE_LINK] = "https://www.acr.org/~/media/ACR/Documents/PDF/QualitySafety/Resources/PIRADS/PIRADS%20V2.pdf";
                break;

            default:
                break;
        }


        return guidelines;
    }

}
