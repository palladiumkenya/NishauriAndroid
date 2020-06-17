package com.mhealth.nishauri.Fragments.Settings;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mhealth.nishauri.Models.FAQ;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.adapters.FAQsAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class FaqsFragment extends Fragment {

    private Unbinder unbinder;
    private View root;
    private Context context;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private FAQsAdapter mAdapter;
    private ArrayList<FAQ> faqArrayList;



    public FaqsFragment() {
        // Required empty public constructor
    }


    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        this.context = ctx;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_faqs, container, false);
        unbinder = ButterKnife.bind(this, root);

        faqArrayList = new ArrayList<>();
        mAdapter = new FAQsAdapter(context, faqArrayList);


        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayout.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);

        //set data and list adapter
        recyclerView.setAdapter(mAdapter);

        loadFAQs();


        mAdapter.setOnItemClickListener(new FAQsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FAQ clickedItem = faqArrayList.get(position);


            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void loadFAQs() {

        faqArrayList.add(new FAQ("What should I do If I get side effects from the medicine given?","Call the facility and report to your care provider. "));

        faqArrayList.add(new FAQ("What's the name of the treatment regimen am taking?","Go to your treatment profile on the application to see this information. Or ask your treatment provider at the facility to educate you more on this. "));

        faqArrayList.add(new FAQ("Who is a stable client when it comes to treatment? ","A stable client is a person living with HIV on ART and is adherent and does not require frequent clinical consultation."));

        faqArrayList.add(new FAQ("How often should I visit the facility if I am a stable client? ","Clients who are stable are usually put under differentiated care model, where they attend clinical consultations after every other 6months."));

        faqArrayList.add(new FAQ("What's a High viral load and LDL viral load?","A High viral load is any results for VL with over 836 cp/ml while a LDL VL - Low Detectable Viral Load is any results for VL with a number of less than 800cp/ml"));

        faqArrayList.add(new FAQ("What's the difference between Suppressed viral load and Undetectable viral load? ","Suppressed Viral Load is same as LDL which means you are under the 800mark while Undetectable VL means that they can not tell how much HIV is in your blood because it's very low for any cp/ml. "));

        faqArrayList.add(new FAQ("If my baby's/dependant's EID results is positive should I visit the health facility?","Yes you should so that they can be immediately get started on Art treatment."));

        faqArrayList.add(new FAQ("How often should I go for my VL sample to be taken? ","For a stable client it should be as often as after every 3 months while for unstable clients, the treatment provider shall advise depending on how well you are adhering to taking your medication."));

        mAdapter.notifyDataSetChanged();

    }
}
