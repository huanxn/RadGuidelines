package com.radicalpeas.radguidelines;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.radicalpeas.radguidelines.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * A fragment representing a single Organ detail screen.
 * This fragment is either contained in a {@link OrganListActivity}
 * in two-pane mode (on tablets) or a {@link OrganDetailActivity}
 * on handsets.
 */

/**
 * Created by huanx on 4/13/2017.
 */


public class OrganDetailFragment extends Fragment
{

    final static int RESULTS_STATUS_MESSAGE = 0;
    final static int RESULTS_IMPRESSION = 1;
    final static int RESULTS_CLASSIFICATION = 2;
    final static int RESULTS_FOLLOWUP = 3;
    final static int RESULTS_COMMENTS = 4;
    final static int RESULTS_REFERENCE_TEXT = 5;
    final static int RESULTS_REFERENCE_LINK = 6;
    final static int RESULTS_REFERENCE_IMAGE= 7;
    final static int RESULTS_ARRAY_SIZE = RESULTS_REFERENCE_IMAGE + 10; // use extra slots for reference images

    protected static List<String> tab_titles;

    // todo declare organ specific spinner position variables



    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OrganDetailFragment()
    {
    }

    /*
    public static final OrganDetailFragment newInstance()
    {
        OrganDetailFragment f = new OrganDetailFragment();
        setTabTitles();
        return f;
    }

    */

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.viewpager_tabs, container, false);

        //mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(), this, tab_titles_array_id);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(), this, tab_titles);


        // Initialize the ViewPager and set the adapter
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        // Bind the PagerSlidingTabStrip to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        tabs.setViewPager(mViewPager);
        tabs.setTextColor(ContextCompat.getColor(getContext(), R.color.text_light));
        tabs.setIndicatorColor(ContextCompat.getColor(getContext(), R.color.white_text));
        tabs.setShouldExpand(true);

        return view;
    }

    // must be overriden
    // called by tabbedFragment
    // create the organ specific layout for each tab position
    public View createView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState, int tabPosition)
    {
        View view;

        switch(tabPosition)
        {
            //
            case 0:
                // TODO set to organ specific layout
                view = inflater.inflate(R.layout.organ_detail, container, false);

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

        // tab position
        switch(mViewPager.getCurrentItem())
        {
            case 0:
                break;

            default:
                break;
        }

        return guidelines;

    }

    protected void enableField(View view, int textView_resource, int spinner_resource)
    {
        ((TextView) view.findViewById(textView_resource)).setVisibility(View.VISIBLE);
        ((TextView) view.findViewById(textView_resource)).setTextColor(getResources().getColor(R.color.text_dark));
        ((Spinner) view.findViewById(spinner_resource)).setVisibility(View.VISIBLE);
    }
    protected void disableField(View view, int textView_resource, int spinner_resource)
    {
        ((TextView) view.findViewById(textView_resource)).setTextColor(getResources().getColor(R.color.text_dark_disabled));
        ((Spinner) view.findViewById(spinner_resource)).setVisibility(View.INVISIBLE);
    }

    protected void hideField(View view, int textView_resource, int spinner_resource)
    {
        ((TextView) view.findViewById(textView_resource)).setVisibility(View.GONE);
        ((Spinner) view.findViewById(spinner_resource)).setVisibility(View.GONE);
    }

    private void fixStringUnicode(String[] inString)
    {
        for(int i = 0; i < inString.length; i++)
        {
            inString[i] = inString[i].replace("<=", "\u2264");
            inString[i] = inString[i].replace(">=", "\u2265");
        }
    }

    /**
     * Use this method to set multiline, and to use unicode character for <= and >=
     * otherwise, set entries in XML layout
     * @param spinner
     * @param arrayResId
     */
    protected void  setSpinnerEntries(Spinner spinner, int arrayResId)
    {
        /*
                ArrayAdapter<CharSequence> composition_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.thyroid_us_composition_array, R.layout.spinner_dropdown_item_multiline);
                composition_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_multiline);
                thyroid_composition_spinner.setAdapter(composition_adapter);

         */
        String[] stringArray = getResources().getStringArray(arrayResId);
        fixStringUnicode(stringArray);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), R.layout.spinner_dropdown_item_multiline);
        adapter.addAll(stringArray);
        spinner.setAdapter(adapter);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {
        private OrganDetailFragment rootFragment; // parent fragment with overriden organ specific methods to create layout and form results
        private String [] tab_titles;
        private TabbedContentFragment [] tabbedContentFragments;// = new TabbedContentFragment[tab_titles.length];

        public SectionsPagerAdapter(FragmentManager fm, OrganDetailFragment parentFragment, int tab_titles_array_resource_id)
        {
            super(fm);

            rootFragment = parentFragment;

            tab_titles = getResources().getStringArray(tab_titles_array_resource_id);
            tabbedContentFragments = new TabbedContentFragment[tab_titles.length];
        }

        public SectionsPagerAdapter(FragmentManager fm, OrganDetailFragment parentFragment, List<String> tabTitlesList)
        {
            super(fm);

            rootFragment = parentFragment;

            tab_titles = new String[tabTitlesList.size()];
            for(int i = 0; i < tabTitlesList.size(); i++)
            {
                tab_titles[i] = tabTitlesList.get(i);
            }

            tabbedContentFragments = new TabbedContentFragment[tab_titles.length];
        }

        public TabbedContentFragment getTabbedContentFragment(int tabPosition)
        {
            return tabbedContentFragments[tabPosition];
        }

        @Override
        public Fragment getItem(int position)
        {
            if(position < getCount())
            {
                Fragment fragment = new TabbedContentFragment();

                Bundle args = new Bundle();
                args.putInt(TabbedContentFragment.ARG_TAB_NUMBER, position);
                fragment.setArguments(args);

                tabbedContentFragments[position] = (TabbedContentFragment) fragment;
                tabbedContentFragments[position].setRootFragment(rootFragment);

                return fragment;
            }
            else
            {
                return null;
            }

        }

        @Override
        public int getCount()
        {
            return tab_titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            Locale l = Locale.getDefault();

            if(position < getCount())
                return tab_titles[position].toUpperCase(l);
            else
                return null;
        }
    } // end SectionsPagerAdapter

    public static class TabbedContentFragment extends Fragment
    {
        public static final String ARG_TAB_NUMBER = "tab_number_position";
        OrganDetailFragment rootFragment;

        public TabbedContentFragment() {
        }

        public void setRootFragment(OrganDetailFragment fragment)
        {
            rootFragment = fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {

            View view;

            if(getArguments().containsKey(ARG_TAB_NUMBER))
            {
                int tabPosition = getArguments().getInt(ARG_TAB_NUMBER);

                view = rootFragment.createView(inflater, container, savedInstanceState, tabPosition);
                /*


                switch(tabPosition)
                {
                    //
                    case 0:
                        // TODO set to organ specific layout
                        view = inflater.inflate(R.layout.organ_detail, container, false);

                        break;


                    default:
                        view = null;
                }
                */
            }
            else
            {
                view = null;
            }

            return view;



        }


    } // end TabbedContentFragment

}






/*
public class OrganDetailFragment extends Fragment
{
    // The fragment argument representing the item ID that this fragment represents.
    public static final String ARG_ITEM_ID = "item_id";

    // The dummy content this fragment is presenting.
    private DummyContent.DummyItem mItem;

    //Mandatory empty constructor for the fragment manager to instantiate the fragment (e.g. upon screen orientation changes).
    public OrganDetailFragment()
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
        View rootView = null;


        rootView = inflater.inflate(R.layout.organ_detail, container, false);


        // Show the dummy content as text in a TextView.
        if (mItem != null)
        {
            ((TextView) rootView.findViewById(R.id.organ_detail)).setText(mItem.details);
        }

        return rootView;
    }
}
*/
