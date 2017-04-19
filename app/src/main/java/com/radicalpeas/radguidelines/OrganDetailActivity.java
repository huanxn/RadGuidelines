package com.radicalpeas.radguidelines;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a single Organ detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link OrganListActivity}.
 */
public class OrganDetailActivity extends AppCompatActivity
{
    final static int ORGAN_LUNG = 1;
    final static int ORGAN_RENAL = 2;
    final static int ORGAN_LIVER = 3;
    final static int ORGAN_ADRENAL = 4;
    final static int ORGAN_PANCREAS = 5;
    final static int ORGAN_ADNEXA = 6;
    final static int ORGAN_VASCULAR = 7;
    final static int ORGAN_SPLEEN = 8;
    final static int ORGAN_NODAL= 9;
    final static int ORGAN_GALLBLADDER = 10;
    final static int ORGAN_THYROID = 11;
    final static int ORGAN_PROSTATE = 12;
    final static int ORGAN_BREAST = 13;

    final static int RESULTS_IMPRESSION = 1;
    final static int RESULTS_CLASSIFICATION = 2;
    final static int RESULTS_FOLLOWUP = 3;
    final static int RESULTS_STATISTICS = 4;
    final static int RESULTS_REFERENCE_TEXT = 5;
    final static int RESULTS_REFERENCE_LINK = 6;
    final static int RESULTS_REFERENCE_IMAGE= 7;
    final static int RESULTS_ARRAY_SIZE = 8;

    int organ_id = -1;

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    OrganDetailFragment organFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organ_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String[] results = null;

                if(organFragment != null)
                {
                    results = organFragment.getResults();

                    if (results != null && results[0].contentEquals("VALID"))
                    {
                        // hide button
                        findViewById(R.id.fab).setVisibility(View.INVISIBLE);

                        // Create the results fragment and add it to the activity
                        // using a fragment transaction.
                        Bundle arguments = new Bundle();

                        // pass results to fragment
                        arguments.putString(ResultsDetailFragment.ARG_IMPRESSION, results[RESULTS_IMPRESSION]);
                        arguments.putString(ResultsDetailFragment.ARG_CLASSIFICATION, results[RESULTS_CLASSIFICATION]);
                        arguments.putString(ResultsDetailFragment.ARG_FOLLOWUP, results[RESULTS_FOLLOWUP]);
                        arguments.putString(ResultsDetailFragment.ARG_STATISTICS, results[RESULTS_STATISTICS]);
                        arguments.putString(ResultsDetailFragment.ARG_REFERENCE_TEXT, results[RESULTS_REFERENCE_TEXT]);
                        arguments.putString(ResultsDetailFragment.ARG_REFERENCE_LINK, results[RESULTS_REFERENCE_LINK]);
                        arguments.putString(ResultsDetailFragment.ARG_REFERENCE_IMAGE, results[RESULTS_REFERENCE_IMAGE]);

                        Fragment result_fragment = new ResultsDetailFragment();

                        result_fragment.setArguments(arguments);

                        // todo save fragment state in backstack?
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.organ_detail_container, result_fragment)
                                .addToBackStack(null)
                                .commit();

                    }
                    else
                    {
                        // display error message
                        Snackbar.make(view, results[0], Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
/*
// TEST:  starting new ResultsDetailActivity and ResultsDetailFragment
                    Intent intent = new Intent(view.getContext(), ResultsDetailActivity.class);
                    intent.putExtra(OrganDetailFragment.ARG_ITEM_ID, String.valueOf(organ_id));
                //    intent.putExtra("FOLLOWUP", guidelines.get(2));
                   startActivity(intent);
*/

            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null)
        {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ARG_ITEM_ID,
                    getIntent().getStringExtra(ARG_ITEM_ID));

            organ_id = Integer.valueOf(arguments.getString(ARG_ITEM_ID));

            if(organ_id == ORGAN_LUNG)
            {
                setImageView(R.id.toolbar_imageview, R.drawable.lung_ct_header);
                organFragment = new LungDetailFragment();
                organFragment.setTabTitlesArray(R.array.lung_tab_titles_array);
            }
            else if(organ_id == ORGAN_LIVER)
            {
                setImageView(R.id.toolbar_imageview, R.drawable.liver_ct_header);
                organFragment = new LiverDetailFragment();
                organFragment.setTabTitlesArray(R.array.liver_tab_titles_array);
            }
            else if(organ_id == ORGAN_RENAL)
            {
                setImageView(R.id.toolbar_imageview, R.drawable.renal_ct_header);
                organFragment = new RenalDetailFragment();
                organFragment.setTabTitlesArray(R.array.ct_tab_titles_array);
            }
            else if(organ_id == ORGAN_THYROID)
            {
                setImageView(R.id.toolbar_imageview, R.drawable.thyroid_us_header);
                organFragment = new ThyroidDetailFragment();
                organFragment.setTabTitlesArray(R.array.thyroid_tab_titles_array);
            }
            else if(organ_id == ORGAN_ADRENAL)
            {
                //setImageView(R.id.toolbar_imageview, R.drawable.adrenal_ct_header);
                organFragment = new AdrenalDetailFragment();
                organFragment.setTabTitlesArray(R.array.adrenal_tab_titles_array);
            }
            else if(organ_id == ORGAN_PANCREAS)
            {
                //setImageView(R.id.toolbar_imageview, R.drawable.liver_ct_header);
                organFragment = new PancreasDetailFragment();
                organFragment.setTabTitlesArray(R.array.ctmrus_tab_titles_array);
            }
            else if(organ_id == ORGAN_PROSTATE)
            {
                //setImageView(R.id.toolbar_imageview, R.drawable.prostate_mr_header);
                organFragment = new ProstateDetailFragment();
                organFragment.setTabTitlesArray(R.array.prostate_tab_titles_array);
            }
            else
            {
                organFragment = new OrganDetailFragment();
                organFragment.setTabTitlesArray(R.array.ctmrus_tab_titles_array);
            }

            organFragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.organ_detail_container, organFragment)
                    .commit();
        }
    }

    public void setImageView(int imageViewID, int drawable)
    {
        //int imageResource = drawable;
        Drawable image = getResources().getDrawable(drawable);
        ImageView imageView = (ImageView) findViewById(imageViewID);
        imageView.setImageDrawable(image);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, OrganListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed()
    {
        View fab = findViewById(R.id.fab);

        if(fab.getVisibility() == View.VISIBLE)
        {
            // floating action button is visible in user input screen
            // back press goes back to organ list
            super.onBackPressed();
        }
        else
        {
            // floating action button is invisible in results screen
            // back press goes back to user input screen
            fab.setVisibility(View.VISIBLE);
            getSupportFragmentManager().popBackStack();
        }

        return;
    }
}
