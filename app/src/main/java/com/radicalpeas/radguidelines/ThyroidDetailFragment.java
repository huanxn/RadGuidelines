package com.radicalpeas.radguidelines;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.radicalpeas.radguidelines.dummy.DummyContent;

import java.util.Locale;

/**
 * Created by huanx on 4/13/2017.
 */


public class ThyroidDetailFragment extends Fragment
{
     /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ThyroidDetailFragment()
    {
    }

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
        View view = inflater.inflate(R.layout.results_tabs, container, false);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

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

    // if completed info, returns guideline recommendations, reference, link
    // else send error message
    public String[] getResults()
    {
        // get fragment of active tab (current item in pager adapter)
        TabbedContentFragment tabbedContentFragment = mSectionsPagerAdapter.getTabbedContentFragment(mViewPager.getCurrentItem()); // either US or CT
        return tabbedContentFragment.getResults();
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {
        private String [] tab_titles;
        private TabbedContentFragment [] tabbedContentFragments;// = new TabbedContentFragment[tab_titles.length];

        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);

            tab_titles = getResources().getStringArray(R.array.thyroid_tab_titles_array);
            tabbedContentFragments = new TabbedContentFragment[tab_titles.length];
        }

        public TabbedContentFragment getTabbedContentFragment(int tabPosition)
        {
            return tabbedContentFragments[tabPosition];
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

        int tabPosition = 0;    // 0: US, 1: CT

        // US variables
        private int composition = 0;
        private int echogenicity = 0;
        private int shape = 0;
        private int margin = 0;
        private int echogenic_foci = 0;
        private float noduleSize = 0;

        // CT variables
        //...

        public TabbedContentFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            View view;

            if(getArguments().containsKey(ARG_TAB_NUMBER))
            {
                tabPosition = getArguments().getInt(ARG_TAB_NUMBER);

                switch(tabPosition)
                {
                    case 0:
                        view = inflater.inflate(R.layout.thyroid_us_layout, container, false);

                        final Spinner thyroid_composition_spinner = (Spinner) view.findViewById(R.id.spinner_thyroid_composition);
                        final Spinner thyroid_echogenicity_spinner = (Spinner) view.findViewById(R.id.spinner_thyroid_echogenicity);
                        final Spinner thyroid_shape_spinner = (Spinner) view.findViewById(R.id.spinner_thyroid_shape);
                        final Spinner thyroid_margin_spinner = (Spinner) view.findViewById(R.id.spinner_thyroid_margin);
                        final Spinner thyroid_echogenic_foci_spinner = (Spinner) view.findViewById(R.id.spinner_thyroid_echogenic_foci);

                        final EditText nodule_size_EditText = (EditText) view.findViewById(R.id.edittext_thyroid_nodule_size);

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
                                String noduleSizeString = nodule_size_EditText.getText().toString();
                                if(!noduleSizeString.isEmpty() && noduleSizeString != null)
                                {
                                    noduleSize = Float.valueOf(noduleSizeString);
                                }
                                else
                                {
                                    noduleSize = 0;
                                }
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


                        break;

                    case 1:
                        view = inflater.inflate(R.layout.results_references, container, false);

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

        String[] getResults()
        {
            if(tabPosition == 0)
            {// if completed info, returns guideline recommendations, reference, link
                // else send error message

                //ArrayList<String> stringList = new ArrayList<String>();
                String[] guidelines = new String[7];

                for (int i = 0; i < guidelines.length; i++)
                {
                    guidelines[i] = "";
                }

                String findings;
                int TIRADS_points = 0;

                if (composition == 2)    // mixed cystic and solid
                {
                    TIRADS_points += 1;
                }
                else if (composition == 3)   // solid or almost completely solid
                {
                    TIRADS_points += 2;
                }

                TIRADS_points += echogenicity;

                if (shape == 1)  // taller than wide
                {
                    TIRADS_points += 3;
                }

                if (margin == 2 || margin == 3)
                {
                    TIRADS_points += margin;
                }

                TIRADS_points += echogenic_foci;

                guidelines[0] = "VALID";

                if (TIRADS_points <= 1)
                {
                    guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "TR1: Benign.";
                    guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "No FNA";
                }
                else if (TIRADS_points == 2)
                {
                    guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "TR2: Not suspicious.";
                    guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "No FNA";
                }
                else if (TIRADS_points == 3)
                {
                    if (noduleSize >= 2.5)
                    {
                        guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = "TR3: Mildly Suspicious.";
                        guidelines[OrganDetailActivity.RESULTS_FOLLOWUP] = "Given the large size over 2.5 cm, FNA biopsy is recommended.";
                    }
                    else if (noduleSize >= 1.5)
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
                else if (TIRADS_points >= 4 && TIRADS_points <= 6)
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

            } // US tab
            else
            {
                return null;
            } // CT tab
        } // end getResults

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
