package com.mhealth.nishauri.Fragments.Chat;

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

        faqArrayList.add(new FAQ("What should I do If I get side effects from the medicine given?","Please visit your clinic for review by your doctor. "));

        faqArrayList.add(new FAQ("What's the name of the treatment regimen I am taking?","To know your regimen, click on the home page and you will see the name of your current regimen."));

        faqArrayList.add(new FAQ("Who is an established client when it comes to treatment?","An established client is one who is on their current ART regimen for a period greater than 6 months, had no active OI or in the previous 6 months, has adhered to scheduled clinic visits for the previous 6 months and Viral load results has been less than 200 copies/ml within the last 6 months."));

        faqArrayList.add(new FAQ("How often should I visit the facility if I am an established client? ","As often as recommended by your doctor while ensuring that you keep all the appointments given"));

        faqArrayList.add(new FAQ("What is a high viral load?","A high viral load is any results greater than 200 cp/m"));

        faqArrayList.add(new FAQ("What is a low detectable viral load ","Any results for VL with below 50 cp/ml"));

        faqArrayList.add(new FAQ("How to interpret Viral Load results","•\tLow detectable level (LDL) → below 50 cp/mL\n" +
                "•\tLow level viremia → 50 -199 cp/ml\n" +
                "•\tHigh level viremia → 199 - 999cp/mL\n" +
                "•\tSuspected Treatment failure → greater than 1000 cp/ml\n"));

       // faqArrayList.add(new FAQ("What's the difference between Suppressed viral load and Undetectable viral load? ","Suppressed Viral Load is same as LDL which means you are under the 800mark while Undetectable VL means that they can not tell how much HIV is in your blood because it's very low for any cp/ml. "));

        faqArrayList.add(new FAQ("If my baby's/dependant's EID results is positive should I visit the health facility?","Yes, you should visit the facility so that they can immediately get started on Art treatment"));
        faqArrayList.add(new FAQ("How often should I go for my VL sample to be taken?","If you are newly initiated on ART, the first viral load sample should be taken after 3 months of taking ART\n" +
                "If your previous result was \n" +
                "•\tBelow 50 to 199 cp/ml - Viral Load sample should be taken after every 12 months\n" +
                "•\tAbove 200cp/ml - Viral load sample should be taken after three months\n"));
        faqArrayList.add(new FAQ("Who is a suppressed and non-suppressed client?","A Suppressed client is the one who has a valid viral load results which are 199 cp/ml and below.\n" +
                "A non-suppressed client is the one who has a valid viral load results being 200 cp/ml and above.\t\n"));

        //faqArrayList.add(new FAQ("How often should I go for my VL sample to be taken? ","For a stable client it should be as often as after every 3 months while for unstable clients, the treatment provider shall advise depending on how well you are adhering to taking your medication."));

        mAdapter.notifyDataSetChanged();

    }
}
