package com.radicalpeas.radguidelines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

    // must be overriden
    // called by tabbedFragment
    // create the organ specific layout for each tab position
    public View createView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState, int tabPosition)
    {
        View view;

        switch(tabPosition)
        {
            case 0:

                view = inflater.inflate(R.layout.prostate_pirads_layout, container, false);

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

        // tab position
        switch(mViewPager.getCurrentItem())
        {
            case 0:

                break;

            default:
                break;
        }

        guidelines[OrganDetailActivity.RESULTS_REFERENCE_TEXT] = "";
        guidelines[OrganDetailActivity.RESULTS_REFERENCE_LINK] = "";


        return guidelines;

    }

}
