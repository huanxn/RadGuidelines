package com.radicalpeas.radguidelines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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


public class PancreasDetailFragment extends OrganDetailFragment
{
    private enum Tab
    {
        CTorMRorUS(0);

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
                case CTorMRorUS:
                    return "CT or MR or US";
                default:
                    return "";
            }
        }
    }

    public static final PancreasDetailFragment newInstance()
    {
        PancreasDetailFragment f = new PancreasDetailFragment();

        tab_titles = new ArrayList<>();
        tab_titles.add(Tab.CTorMRorUS.getValue(), Tab.CTorMRorUS.toString());

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
            case CTorMRorUS:

                view = inflater.inflate(R.layout.pancreas_cyst_layout, container, false);

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
            case CTorMRorUS:

                break;

            default:
                break;
        }


        return guidelines;
    }

}
