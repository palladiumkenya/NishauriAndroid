package com.example.mhealth.appointment_diary.wellnesfragments;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.RecyclerListener.RecyclerTouchListener;
import com.example.mhealth.appointment_diary.adapter.WellnesNotOkAdapter;
import com.example.mhealth.appointment_diary.customdialogs.CustomDialogs;
import com.example.mhealth.appointment_diary.models.WellnessNotOkModel;
import com.example.mhealth.appointment_diary.tables.notOkWellnessMessage;
import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.List;

public class notokFragment extends Fragment {

    private RecyclerView recyclerView;
    private WellnesNotOkAdapter adapter;
    private ArrayList<WellnessNotOkModel> itemsList;
    CustomDialogs cd;

    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.notok_wellness_fragment,container,false);
        initialise();
        setListItems();
        Stetho.initializeWithDefaults(getActivity());
        return v;
    }

    public void initialise(){

        try{
            cd=new CustomDialogs(getActivity());
            recyclerView = (RecyclerView) v.findViewById(R.id.notok_wellnes_recyclerview);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            itemsList = new ArrayList<>();
        }
        catch(Exception e){


        }
    }


    public void setListItems(){

        try{
            itemsList = new ArrayList<>();
            setMyAdapter();
            setMyRecyclerView();
            getMyWellnessData();
            setRecyclerClickListener();



        }
        catch(Exception e){

            Toast.makeText(getActivity(), "error setting list"+e, Toast.LENGTH_SHORT).show();
        }
    }



    private void getMyWellnessData(){

        List<notOkWellnessMessage> myl= notOkWellnessMessage.findWithQuery(notOkWellnessMessage.class,"select * from not_ok_wellness_message where removed=?","false");
        for(int x=0;x<myl.size();x++){

            String mccn=myl.get(x).getCcno();
            String mfname=myl.get(x).getFname();
            String mphone=myl.get(x).getPhoneno();
            String mmsg=myl.get(x).getMsg();
            String mmsgid=myl.get(x).getMsgid();

            WellnessNotOkModel cm=new WellnessNotOkModel(mccn,mfname,mphone,mmsg,mmsgid);
            itemsList.add(cm);



        }

        adapter.notifyDataSetChanged();

    }




    public void setMyAdapter(){

        try{

            adapter = new WellnesNotOkAdapter(getActivity(), itemsList);
        }
        catch(Exception e){

            Toast.makeText(getActivity(), "error setting adapter", Toast.LENGTH_SHORT).show();
        }
    }


    public void setMyRecyclerView(){

        try{


            recyclerView.setAdapter(adapter);
        }
        catch(Exception e){

            Toast.makeText(getActivity(), "error setting recyclerview", Toast.LENGTH_SHORT).show();
        }
    }



    public void setRecyclerClickListener(){

        try{




            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {

                    WellnessNotOkModel mitem=itemsList.get(position);

                    String msgid=mitem.getMsgid();
                    String phoneNo=mitem.getPhoneno();
                    cd.displayWellnessEditDetails(msgid,phoneNo,itemsList,position,adapter);
//                    Toast.makeText(getActivity(), "clicked on "+mitem.getCcno(), Toast.LENGTH_SHORT).show();



                }

                @Override
                public void onLongClick(View view, int position) {

                    WellnessNotOkModel mitem=itemsList.get(position);

                    Toast.makeText(getActivity(), "Long clicked on "+mitem.getCcno(), Toast.LENGTH_SHORT).show();




                }
            }));



        }
        catch(Exception e){

            Toast.makeText(getActivity(), "error setting recycler listener", Toast.LENGTH_SHORT).show();

        }
    }



    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
