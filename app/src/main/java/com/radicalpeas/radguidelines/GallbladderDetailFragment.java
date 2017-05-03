package com.radicalpeas.radguidelines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by huanx on 4/28/2017.
 */

public class GallbladderDetailFragment extends OrganDetailFragment
{
    private int contents;
    private int wall;
    private int polyp;
    private int duct_dilation = 0;

    private enum Tab
    {
        CT(0);

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
                case CT:
                    return "CT";
                default:
                    return "";
            }
        }
    }

    public static final GallbladderDetailFragment newInstance()
    {
        GallbladderDetailFragment f = new GallbladderDetailFragment();

        tab_titles = new ArrayList<>();
        tab_titles.add(Tab.CT.getValue(), Tab.CT.toString());

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
            case CT:

                view = inflater.inflate(R.layout.gallbladder_ct_layout, container, false);
                final View gallbladder_ct_view = view;

                final Spinner gallbladder_ct_contents_spinner = (Spinner) view.findViewById(R.id.spinner_gallbladder_ct_contents);
                final Spinner gallbladder_ct_wall_spinner = (Spinner) view.findViewById(R.id.spinner_gallbladder_ct_wall);
                final Spinner gallbladder_ct_polyp_spinner = (Spinner) view.findViewById(R.id.spinner_gallbladder_ct_polyp);
                final Spinner gallbladder_ct_duct_spinner = (Spinner) view.findViewById(R.id.spinner_gallbladder_ct_duct_dilation);

                gallbladder_ct_contents_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        gallbladder_ct_contents_spinner.setSelection(position);
                        contents = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }
                });

                gallbladder_ct_wall_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        gallbladder_ct_wall_spinner.setSelection(position);
                        wall = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }
                });

                setSpinnerEntries(gallbladder_ct_polyp_spinner, R.array.gallbladder_polyp_array);
                gallbladder_ct_polyp_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        gallbladder_ct_polyp_spinner.setSelection(position);
                        polyp = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }
                });

                gallbladder_ct_duct_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        gallbladder_ct_duct_spinner.setSelection(position);
                        duct_dilation = position;

                        TextView gallbladder_info_textview = (TextView) gallbladder_ct_view.findViewById(R.id.textview_gallblader_ct_info);

                        if(duct_dilation == 0)
                        {
                            gallbladder_info_textview.setVisibility(View.GONE);
                        }
                        if(duct_dilation == 1)
                        {
                            gallbladder_info_textview.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }
                });

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
            case CT:

                String contentsFindings = "";
                String wallFindings = "";
                String polypFindings = "";
                String ductFindings = "";

                String contentsImpression = "";
                String wallImpression = "";
                String polypImpression = "";
                String ductImpression = "";

                if(contents == 1)
                {
                    // stones
                    contentsFindings = "Cholelithiasis. ";
                    contentsImpression = "If symptomatic, ultrasound for further evaluation.";
                }
                else if(contents == 2)
                {
                    // dense material
                    contentsFindings = "Dense gallbladder contents, likely sludge, excreted contrast, hemorrhage, or gallstones. ";
                    contentsImpression = "No evaluation or follow-up recommended specifically for this finding.";
                }

                if(wall == 1)
                {
                    // calcification
                    wallFindings = "Focal wall calcification or diffuse porcelain gallbladder. ";
                    wallImpression = "No follow-up recommended; if followed, use contrast enhanced CT";
                }
                else if(wall == 2)
                {
                    // diffuse thickening
                    wallFindings = "Diffuse gallbladder wall thickening > 3mm, without a mass.  Findings may be secondary to hepatitis, congestive heart failure, liver disease, pancreatitis, hypoproteinemia. ";
                    wallImpression = "No evaluation or follow-up recommended specifically for this finding.";
                }
                else if(wall == 3)
                {
                    // focal thickening or mass
                    wallFindings = "Focal gallbladder wall thickening or mass. This may represent a polyp, gallbladder cancer, cholesterolosis, adenomyomatosis. ";
                    wallImpression = "Evaluation and follow-up depends on mass size and other clinical factors; ultrasound may have specific features for adenomyomatosis.";
                }

                if(polyp == 1)
                {
                    polypFindings = "Gallbladder polyp \u2264 6 mm, likely benign. ";
                    polypImpression = "No evaluation or follow-up recommended.";

                }
                else if(polyp == 2)
                {
                    polypFindings = "Gallbladder polyp between 7 to 9 mm.  This may be a benign polyp, adenoma, or small cancer. ";
                    polypImpression = "Follow yearly with ultrasound; surgical consult if the polyp grows.";

                }
                else if(polyp == 3)
                {
                    polypFindings = "Gallbladder polyp or mass \u2265 10 mm.  This may be a benign polyp, adenoma, or small cancer. ";
                    polypImpression = "Recommend surgical consult.";
                }

                if(duct_dilation == 1)
                {
                    ductFindings = "Biliary duct dilation which may be due to biliary obstruction or seen with post-cholecystectomy changes. ";
                    ductImpression = "If laboratory results are normal, then no further evaluation.  If laboratory results are abnormal, ERCP, EUS, MRCP, or CT cholangiography as appropriate.";
                }

                guidelines[OrganDetailActivity.RESULTS_IMPRESSION] = contentsFindings + contentsImpression + "\n\n" + wallFindings + wallImpression + "\n\n" + polypFindings + polypImpression + "\n\n" + ductFindings + ductImpression;
                break;

            default:
                break;
        }


        return guidelines;
    }
}
