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


public class AdrenalDetailFragment extends OrganDetailFragment
{
    private enum Tab
    {
        INCIDENTAL(0), ADRENAL_PROTOCOL(1);

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
                case ADRENAL_PROTOCOL:
                    return "Adrenal Protocol CT";
                default:
                    return "";
            }
        }
    }

    public static final AdrenalDetailFragment newInstance()
    {
        AdrenalDetailFragment f = new AdrenalDetailFragment();

        tab_titles = new ArrayList<>();
        tab_titles.add(Tab.INCIDENTAL.getValue(), Tab.INCIDENTAL.toString());
        tab_titles.add(Tab.ADRENAL_PROTOCOL.getValue(), Tab.ADRENAL_PROTOCOL.toString());

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

                view = inflater.inflate(R.layout.adrenal_ct_layout, container, false);

                break;


            case ADRENAL_PROTOCOL:

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
        String[] guidelines = new String[OrganDetailActivity.RESULTS_ARRAY_SIZE];

        for (int i = 0; i < guidelines.length; i++)
        {
            guidelines[i] = "";
        }

        guidelines[OrganDetailActivity.RESULTS_STATUS_MESSAGE] = "VALID";

        // tab position
        Tab currentTab = Tab.values()[mViewPager.getCurrentItem()];
        switch(currentTab)
        {
            case INCIDENTAL:

                break;

            case ADRENAL_PROTOCOL:

                break;

            default:
                break;
        }

        guidelines[OrganDetailActivity.RESULTS_REFERENCE_TEXT] = "Managing Incidental Findings on Abdominal CT: White Paper of the ACR Incidental Findings Committee";
        guidelines[OrganDetailActivity.RESULTS_REFERENCE_LINK] = "http://www.jacr.org/article/S1546-1440(10)00330-3/fulltext#sec13";
        guidelines[OrganDetailActivity.RESULTS_REFERENCE_IMAGE] = "drawable/adrenal_ct_guidelines";

        return guidelines;
    }
}

