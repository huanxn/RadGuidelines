package com.radicalpeas.radguidelines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by huanx on 4/25/2017.
 */

public class VascularDetailFragment extends OrganDetailFragment
{
    private int aorta_size = 0;
    private int iliac_size = 0;
    private int splenic_size = 0;
    private int renal_size = 0;
    private int visceral_size = 0;

    private int carotid_velocity;
    private int carotid_ratio;

    private enum Tab
    {
        ABDOMEN(0), CAROTID(1);

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
                case ABDOMEN:
                    return "Abdominal CT";
                case CAROTID:
                    return "Carotid US";
                default:
                    return "";
            }
        }
    }

    public static final VascularDetailFragment newInstance()
    {
        VascularDetailFragment f = new VascularDetailFragment();

        tab_titles = new ArrayList<>();
        tab_titles.add(Tab.ABDOMEN.getValue(), Tab.ABDOMEN.toString());
        tab_titles.add(Tab.CAROTID.getValue(), Tab.CAROTID.toString());

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
            case ABDOMEN:
                view = inflater.inflate(R.layout.vascular_abdomen_layout, container, false);
                final View abdomen_view = view;

                final Spinner vascular_aorta_spinner = (Spinner) view.findViewById(R.id.spinner_vascular_aorta_size);
                final Spinner vascular_iliac_spinner = (Spinner) view.findViewById(R.id.spinner_vascular_iliac_size);
                final Spinner vascular_renal_spinner = (Spinner) view.findViewById(R.id.spinner_vascular_renal_size);
                final Spinner vascular_visceral_spinner = (Spinner) view.findViewById(R.id.spinner_vascular_visceral_size);

                // AORTA
                vascular_aorta_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        vascular_aorta_spinner.setSelection(position);
                        aorta_size = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }
                });

                // ILIAC
                vascular_iliac_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        vascular_iliac_spinner.setSelection(position);
                        iliac_size = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }
                });

                // RENAL
                vascular_renal_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        vascular_renal_spinner.setSelection(position);
                        renal_size = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }
                });

                // OTHER VISCERAL ARTERY
                vascular_visceral_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        vascular_visceral_spinner.setSelection(position);
                        visceral_size = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }
                });


                break;

            case CAROTID:

                view = inflater.inflate(R.layout.vascular_carotid_layout, container, false);
                final View carotidView = view;

                final Spinner vascular_carotid_velocity_spinner = (Spinner) view.findViewById(R.id.spinner_carotid_velocity);
                final Spinner vascular_carotid_ratio_spinner = (Spinner) view.findViewById(R.id.spinner_vascular_carotid_ratio);

                // CAROTID VELOCITY
                vascular_carotid_velocity_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        vascular_carotid_velocity_spinner.setSelection(position);
                        carotid_velocity = position;

                        // automatically set ratio to expected corresponding range
                        vascular_carotid_ratio_spinner.setSelection(carotid_velocity);

                        if(carotid_velocity == 3)   // undetectable flow
                        {
                            // disable ratio spinner
                            disableField(carotidView, R.id.label_vascular_carotid_ratio, R.id.spinner_vascular_carotid_ratio);
                        }
                        else
                        {
                            enableField(carotidView, R.id.label_vascular_carotid_ratio, R.id.spinner_vascular_carotid_ratio);
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // hide
                    }
                });

                // CAROTID RATIO
                vascular_carotid_ratio_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        vascular_carotid_ratio_spinner.setSelection(position);
                        carotid_ratio = position;

                        // ratio spinner also changed to match carotid velocity spinner

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
            case ABDOMEN:
                String followup_vary = "Follow-up intervals may vary depending on comorbidities and the growth rate of the aneurysm.";
                String followup_surgical = "In addition to planning follow-up imaging, consider surgical or endovascular referral.";

                String aortaFindings = "There is an abdominal aortic aneurysm with diameter";
                if (aorta_size == 0) // less than 2.5
                {
                    aortaFindings = "";
                }
                else if (aorta_size == 1) // 2.5-2.9
                {
                    aortaFindings = "There is ectasia of the abdominal aorta with diameter between 2.5 to 2.9 cm. Recommend 5 year imaging follow-up.";
                }
                else if (aorta_size == 2) // 3.0-3.4
                {
                    aortaFindings += " between 3.0 to 3.4 cm. Recommend 3 year imaging follow-up.";
                }
                else if (aorta_size == 3) // 3.5-3.9
                {
                    aortaFindings += " between 3.5 to 3.9 cm. Recommend 2 year imaging follow-up.";
                }
                else if (aorta_size == 4) // 4.0-4.4
                {
                    aortaFindings += " between 4.0 to 4.4 cm. Recommend 1 year imaging follow-up. " + followup_surgical;
                }
                else if (aorta_size == 5) // 4.5-4.9
                {
                    aortaFindings += " between 4.5 to 4.9 cm. Recommend 3 to 6 month imaging follow-up. " + followup_surgical;
                }
                else if (aorta_size == 6) // 5.0-5.5
                {
                    aortaFindings += " between 5.0 to 5.5 cm. Recommend 5 year imaging follow-up.";
                }
                else if (aorta_size == 7) // larger than 5.5
                {
                    aortaFindings += " larger than 5.5 cm. Prompt vascular surgical or endovascular referral is recommended.";
                }


                String iliacFindings = "\n\nThere is an iliac artery aneurysm with diameter";
                if (iliac_size == 0) // less than 2.5
                {
                    iliacFindings = "";
                }
                else if (iliac_size == 1) // 2.5 - 2.9
                {
                    iliacFindings += " between 2.5 to 2.9 cm. Iliac artery aneurysms of this size tend to be asymptomatic, rarely rupture, and expand slowly. No imaging follow-up is recommended.";
                }
                else if (iliac_size == 2) // 3.0 - 3.5
                {
                    iliacFindings += " between 3.0 to 3.5 cm. Recommend follow-up with cross-sectional imaging initially at 6 months. If stable at that time, surveillance can be performed annually.";
                }
                else if (iliac_size == 3) // larger than 3.5
                {
                    iliacFindings += " larger than 3.5 cm. Iliac artery aneurysms of this size have a greater tendency to rupture and should be followed more closely or treated expeditiously.";
                }

                String splenicFindings = "\n\nThere is a splenic artery aneurysm with diameter";
                if (splenic_size == 0) // no aneurysm
                {
                    splenicFindings = "";
                }
                else if (splenic_size == 1) // less than 2.0
                {
                    splenicFindings += " less than 2.0 cm. Recommend yearly surveillance.";
                }
                else if (splenic_size == 2) // 2.0 or more
                {
                    splenicFindings += " of 2.0 cm or larger. Consider surgical or endovascular repair.";
                }

                String renalFindings = "\n\nThere is a renal artery aneurysm with diameter";
                if (renal_size == 0) // no aneurysm
                {
                    renalFindings = "";
                }
                else if (renal_size == 1) // 1.0 - 1.5
                {
                    renalFindings += " between 1.0 to 1.5 cm. If the patient has uncontrolled hypertension, consider vascular surgery referral for possible repair. Otherwise, for asymptomatic patients, imaging follow-up every 1 to 2 years is recommended.";
                }
                else if (renal_size == 2) // 1.5 or more
                {
                    renalFindings += " of 1.5 cm or larger. Consider surgical or endovascular repair.";
                }

                String visceralFindings = "\n\nThere is a visceral artery aneurysm with diameter";
                if (visceral_size == 0) // no aneurysm
                {
                    visceralFindings = "";
                }
                else if (visceral_size == 1) // < 2.0
                {
                    visceralFindings += " less than 2.0 cm. Recommend annual surveillance.\n- If pseudoaneurysm is suspected, shorter interval follow-up is recommended.\n- Pancreaticoduodenal aneurysms are felt to be at higher risk for rupture, and can be considered for surgical or endovascular treatment, regardless of size.";
                }
                else if (visceral_size == 2) // 2.0 or more
                {
                    visceralFindings += " of 2.0 cm or larger. Consider surgical or endovascular repair.";
                }

                guidelines[RESULTS_IMPRESSION] = aortaFindings + iliacFindings + renalFindings + visceralFindings;

                if (guidelines[RESULTS_IMPRESSION].isEmpty())
                {
                    guidelines[RESULTS_IMPRESSION] = "No evidence of vascular aneurysm.";
                }
                else
                {
                    guidelines[RESULTS_FOLLOWUP] += followup_vary;
                }

                guidelines[RESULTS_REFERENCE_TEXT] = "Managing Incidental Findings on Abdominal and Pelvic CT and MRI, Part 2: White Paper of the ACR Incidental Findings Committee II on Vascular Findings";
                //guidelines[RESULTS_REFERENCE_LINK] = "https://www.acr.org/Quality-Safety/Resources/TIRADS";
                //guidelines[RESULTS_REFERENCE_IMAGE] = "drawable/...";

                break;

            case CAROTID:
                String velocityFindings;
                String velocityImpression;
                String ratioFindings;
                String ratioImpression;

                String nearOcclusion = "In cases of near occlusion of the ICA, the velocity parameters may not apply, since velocities may be high, low, or undetectable. This diagnosis is established primarily by demonstrating a markedly narrowed lumen at color or power Doppler US.";
                String totalOcclusion = "Total occlusion of the ICA should be suspected when there is no detectable patent lumen at gray-scale US and no flow with spectral, power, and color Doppler US.";
                String followupAngio = "MRA, CTA, or conventional angiography may be used for confirmation";

                if(carotid_ratio == 0)
                {
                    ratioFindings = "ICA:CCA ratio is normal";
                    ratioImpression = "Normal ICA:CCA ratio is suggestive of ICA stenosis less than 50%";
                }
                else if(carotid_ratio == 1)
                {
                    ratioFindings = "ICA:CCA ratio is elevated between 2.0 to 4.0";
                    ratioImpression = "Elevated ICA:CCA ratio is suggestive of ICA stenosis between 50% to 69%";
                }
                else if(carotid_ratio == 2)
                {
                    ratioFindings = "ICA:CCA ratio is elevated between 2.0 to 4.0";
                    ratioImpression = "Elevated ICA:CCA ratio is suggestive of ICA stenosis above 70%";
                }
                else
                {
                    ratioFindings = "";
                    ratioImpression = "";
                }

                if(carotid_velocity == 0)
                {
                    velocityFindings = "There is normal internal carotid artery peak systolic velocity";
                    velocityImpression = "There is no evidence of hemodynamically significant internal carotid artery stenosis";
                }
                else if (carotid_velocity == 1)
                {
                    velocityFindings = "There is elevated internal carotid artery peak systolic velocity between 125 to 230 cm/s";
                    velocityImpression = "There is 50% to 69% internal carotid stenosis";
                }
                else if (carotid_velocity == 2)
                {
                    velocityFindings = "There is elevated internal carotid artery peak systolic velocity above 230 cm/s";
                    velocityImpression = "There is at least 70% internal carotid stenosis";
                }
                else if (carotid_velocity == 3)
                {
                    velocityFindings = "Internal carotid artery velocity is undetectable";
                    velocityImpression = totalOcclusion + " " + followupAngio;
                }
                else
                {
                    velocityFindings = ""; // shouldn't ever go here
                    velocityImpression = "";
                }

                if(carotid_velocity == carotid_ratio)
                {
                    guidelines[RESULTS_IMPRESSION] = velocityFindings + ".  " + ratioFindings + ".";
                    guidelines[RESULTS_CLASSIFICATION] = velocityImpression + ".";

                    guidelines[RESULTS_FOLLOWUP] = nearOcclusion;
                }
                else if(carotid_velocity == 3)
                {
                    guidelines[RESULTS_IMPRESSION] = velocityFindings + ".";
                    guidelines[RESULTS_CLASSIFICATION] = velocityImpression + ".";
                }
                else
                {
                    guidelines[RESULTS_IMPRESSION] = velocityFindings + ". " + velocityImpression + " by ICA peak systolic velocity. However, the " + ratioFindings + ". " + ratioImpression + ".";
                    guidelines[RESULTS_CLASSIFICATION] = "";

                    guidelines[RESULTS_FOLLOWUP] = nearOcclusion;
                }


                guidelines[RESULTS_REFERENCE_TEXT] = "Carotid Artery Stenosis: Gray-Scale and Doppler US Diagnosis - Society of Radiologists in Ultrasound Consensus Conference";
                //guidelines[RESULTS_REFERENCE_LINK] = "https://www.acr.org/Quality-Safety/Resources/TIRADS";
                //guidelines[RESULTS_REFERENCE_IMAGE] = "drawable/...";

                break;

            default:
                break;
        }


        return guidelines;
    }
}
