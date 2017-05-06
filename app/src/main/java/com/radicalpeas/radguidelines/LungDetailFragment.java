package com.radicalpeas.radguidelines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        Tab currentTab = Tab.values()[tabPosition];
        switch(currentTab)
        {
            case INCIDENTAL:
                view = inflater.inflate(R.layout.lung_incidental_ct_layout, container, false);

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

                break;

            case SCREEN:

                break;

            default:
                break;
        }

        guidelines[RESULTS_REFERENCE_TEXT] = "";
        guidelines[RESULTS_REFERENCE_LINK] = "";


        return guidelines;

    }

}
