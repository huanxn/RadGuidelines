package com.radicalpeas.radguidelines;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

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
    final static int ORGAN_THYROID = 11;
    int organ_id = -1;

    Fragment fragment;

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
                if(organ_id == ORGAN_RENAL)
                {
                    List<String> guidelines = new ArrayList<String>();
                    guidelines = ((RenalDetailFragment) fragment).getGuidelines();

                    String message;

                    if(guidelines.get(0).contentEquals("VALID"))
                    {
                        message = guidelines.get(1);
                    }
                    else
                    {
                        message = "missing something..";
                    }

                    Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
                else if(organ_id == ORGAN_THYROID)
                {
                    List<String> guidelines = new ArrayList<String>();
                    guidelines = ((ThyroidUSDetailFragment) fragment).getGuidelines();

                    String message;

                    if(guidelines.get(0).contentEquals("VALID"))
                    {
                        message = guidelines.get(1);
                    }
                    else
                    {
                        message = "missing something..";
                    }

                    Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();


                    // Create the results fragment and add it to the activity
                    // using a fragment transaction.
                    Bundle arguments = new Bundle();


                    // pass ORGAN id
                    arguments.putString(OrganDetailFragment.ARG_ITEM_ID, String.valueOf(ORGAN_THYROID));

                    Fragment result_fragment = new ResultsDetailFragment();

                    result_fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.organ_detail_container, result_fragment)
                            .addToBackStack(null)
                            .commit();


  //                  Intent intent = new Intent(view.getContext(), ResultsDetailFragment.class);
//                    intent.putExtra("IMPRESSION", guidelines.get(1));
  //                  intent.putExtra("FOLLOWUP", guidelines.get(2));
//                    startActivity(intent);

                }
                else
                {
                    Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
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
            arguments.putString(OrganDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(OrganDetailFragment.ARG_ITEM_ID));

            organ_id = Integer.valueOf(arguments.getString("item_id"));

            if(organ_id == ORGAN_RENAL)
            {
                fragment = new RenalDetailFragment();
            }
            else if(organ_id == ORGAN_THYROID)
            {
                fragment = new ThyroidUSDetailFragment();
            }
            else
            {

                fragment = new OrganDetailFragment();
            }

            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.organ_detail_container, fragment)
                    .commit();
        }
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
        getSupportFragmentManager().popBackStack();
        return;
    }
}
