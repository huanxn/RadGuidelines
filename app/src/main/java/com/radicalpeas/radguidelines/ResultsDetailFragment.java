package com.radicalpeas.radguidelines;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import java.util.Locale;

/**
 * Created by huanx on 4/13/2017.
 */


public class ResultsDetailFragment extends Fragment
{

    public static final String ARG_IMPRESSION = "IMPRESSION";
    public static final String ARG_FOLLOWUP = "FOLLOWUP";
    public static final String ARG_STATISTICS = "STATISTICS";
    public static final String ARG_REFERENCE_TEXT = "REFERENCE_TEXT";
    public static final String ARG_REFERENCE_LINK = "REFERENCE_LINK";
    public static final String ARG_REFERENCE_IMAGE = "REFERENCE_IMAGE";



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ResultsDetailFragment()
    {
    }

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.results_tabs, container, false);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        /*
        if (getArguments().containsKey(ARG_GUIDELINES_ARRAY))
        {
            ArrayList<String> guidelines = getArguments().getStringArrayList(ARG_GUIDELINES_ARRAY);
            mSectionsPagerAdapter.passArguments(guidelines);
        }
        */
        mSectionsPagerAdapter.passArguments(getArguments());

        // Initialize the ViewPager and set the adapter
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Bind the PagerSlidingTabStrip to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        tabs.setViewPager(mViewPager);
        //	tabs.setBackgroundColor(activity.getResources().getColor(R.attr.colorPrimary));
        tabs.setTextColor(ContextCompat.getColor(getContext(), R.color.text_light));

        // TODO get from attr theme
        tabs.setIndicatorColor(ContextCompat.getColor(getContext(), R.color.white_text));
        //tabs.setIndicatorColor(activity.getResources().getColor(R.color.white_text));
        tabs.setShouldExpand(true);


        //TypedValue textColor = new TypedValue();
        //tabs.setDividerColor(textColor.);

        return view;
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {
        String [] tab_titles;
        TabbedContentFragment [] tabbedContentFragments;// = new TabbedContentFragment[tab_titles.length];

        private String impression;
        private String statistics;
        private String followup;
        private String reference_text;
        private String reference_link;
        private String reference_image;


        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);

            tab_titles = getResources().getStringArray(R.array.results_tab_titles_array);
            tabbedContentFragments = new TabbedContentFragment[tab_titles.length];
        }

        public void passArguments(Bundle args)
        {
            impression = args.getString(ARG_IMPRESSION);
            followup = args.getString(ARG_FOLLOWUP);
            statistics = args.getString(ARG_STATISTICS);
            reference_text = args.getString(ARG_REFERENCE_TEXT);
            reference_link = args.getString(ARG_REFERENCE_LINK);
            reference_image = args.getString(ARG_REFERENCE_IMAGE);

        }

        @Override
        public Fragment getItem(int position)
        {

/*
            switch(position)
            {
                case 0:
                    //viewpager's page 1 (first tab)
                    return new TabbedContentFragment();
                case 1:
                    //viewpager's page 2 (second tab)
                    return new ReferenceTabFragment();
                default:
                    return null;
            }
*/

            if(position < getCount())
            {
                Fragment fragment = new TabbedContentFragment();

                Bundle args = new Bundle();

                args.putInt(TabbedContentFragment.ARG_TAB_NUMBER, position);
                if(position == 0)
                {
                    args.putString(ARG_IMPRESSION, impression);
                    args.putString(ARG_FOLLOWUP, followup);
                    args.putString(ARG_STATISTICS, statistics);
                }
                else if(position == 1)
                {
                    args.putString(ARG_REFERENCE_TEXT, reference_text);
                    args.putString(ARG_REFERENCE_LINK, reference_link);
                    args.putString(ARG_REFERENCE_IMAGE, reference_image);
                }
                fragment.setArguments(args);

                tabbedContentFragments[position] = (TabbedContentFragment) fragment;

                return fragment;
            }
            else
            {
                return null;
            }

        }

        public TabbedContentFragment getList(int position)
        {
            return tabbedContentFragments[position];
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

        public TabbedContentFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            View view;

            if(getArguments().containsKey(ARG_TAB_NUMBER))
            {
                int position = getArguments().getInt(ARG_TAB_NUMBER);

                switch(position)
                {
                    case 0:
                        view = inflater.inflate(R.layout.results_management, container, false);
                        if(getArguments().containsKey(ARG_IMPRESSION))
                        {
                            ((TextView) view.findViewById(R.id.impression_text)).setText(getArguments().getString(ARG_IMPRESSION));
                        }
                        if(getArguments().containsKey(ARG_FOLLOWUP))
                        {
                            ((TextView) view.findViewById(R.id.followup_text)).setText(getArguments().getString(ARG_FOLLOWUP));
                        }
                        if(getArguments().containsKey(ARG_STATISTICS))
                        {
                            ((TextView) view.findViewById(R.id.statistics_text)).setText(getArguments().getString(ARG_STATISTICS));
                        }

                        break;

                    case 1:
                        view = inflater.inflate(R.layout.results_references, container, false);
                        if(getArguments().containsKey(ARG_REFERENCE_TEXT))
                        {
                            ((TextView) view.findViewById(R.id.reference_article_text)).setText(getArguments().getString(ARG_REFERENCE_TEXT));
                        }
                        if(getArguments().containsKey(ARG_REFERENCE_LINK))
                        {
                            ((TextView) view.findViewById(R.id.reference_link_text)).setText(getArguments().getString(ARG_REFERENCE_LINK));
                        }
                        if(getArguments().containsKey(ARG_REFERENCE_IMAGE))
                        {
                            // set image
                            String image_uri = getArguments().getString(ARG_REFERENCE_IMAGE);
                            int imageResource = getResources().getIdentifier(image_uri, null, getActivity().getPackageName());
                            Drawable image = getResources().getDrawable(imageResource);

                            ImageView imageView = (ImageView) view.findViewById(R.id.reference_imageview);
                            imageView.setImageDrawable(image);
                        }
                        break;

                    default:
                        view = null;
                }
            }
            else
            {
                view = null;
            }

            return view;
        }

    } // end TabbedContentFragment

    /*
    public static class ReferenceTabFragment extends Fragment
    {
        public ReferenceTabFragment() {
        }

        public ReferenceTabFragment newInstance()
        {
            return new ReferenceTabFragment();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.results_references, container, false);
            TextView dummyTextView = (TextView) rootView.findViewById(R.id.reference_article_text);
            dummyTextView.setText("reference tab");

            return rootView;
        }

    } // end ReferenceTabFragment
    */

    //   }


}
