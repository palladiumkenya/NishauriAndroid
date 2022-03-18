package com.example.mhealth.appointment_diary.wellnesstab;


import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.wellnesfragments.notokFragment;
import com.example.mhealth.appointment_diary.wellnesfragments.unrecognisedFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WellnesTabs extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter adapter;

    String[] tabTitle = {"Not Ok","Unrecognised"};
    int[] Counts = {0, 0};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wellness_tab);


        setToolBar();
        DisplayContent();
        adapter.SetOnSelectView(tabLayout,0);
        tabLayout.addOnTabSelectedListener(OnTabSelectedListener);
    }


    public void setToolBar(){

        try{

            getSupportActionBar().setTitle("Wellness responses");
//            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back);
//            upArrow.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
//            getSupportActionBar().setHomeAsUpIndicator(upArrow);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
        catch(Exception e){


        }
    }


    public void pagerListener(){
        try{

            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    try{

                    }
                    catch(Exception e){
                        Toast.makeText(WellnesTabs.this, "error updating fragment "+e, Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
        catch(Exception e){


        }
    }


    private void setupTabIcons() {

        for (int i = 0; i < tabTitle.length; i++) {

            tabLayout.getTabAt(i).setCustomView(prepareTabView(i));
        }
    }



    private TabLayout.OnTabSelectedListener OnTabSelectedListener = new TabLayout.OnTabSelectedListener(){
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int c = tab.getPosition();
            adapter.SetOnSelectView(tabLayout,c);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            int c = tab.getPosition();
            adapter.SetUnSelectView(tabLayout,c);
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };



    private View prepareTabView(int pos) {
        View view = getLayoutInflater().inflate(R.layout.custom_tab, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_count = (TextView) view.findViewById(R.id.tv_count);
        tv_title.setText(tabTitle[pos]);
        if (Counts[pos] > 0) {
            tv_count.setVisibility(View.VISIBLE);
            tv_count.setText("" + Counts[pos]);
        } else
            tv_count.setVisibility(View.GONE);


        return view;
    }


    public void MyViewPager(){

        try{

            viewPager = (ViewPager) findViewById(R.id.vaccviewpager);
            setupViewPager(viewPager);


            tabLayout = (TabLayout) findViewById(R.id.vacctabs);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#f2f2f2"));
            tabLayout.setSelectedTabIndicatorHeight(8);
        }
        catch(Exception e){


        }
    }



    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());


        adapter.addFragment(new notokFragment(), "Not Ok");
        adapter.addFragment(new unrecognisedFragment(), "Unrecognised");
//        adapter.addFragment(new NewOrderFragment(), "In Progress");
//        adapter.addFragment(new NewOrderFragment(), "Delivered");
//        adapter.addFragment(new NewOrderFragment(), "Rejected");






        viewPager.setAdapter(adapter);
    }




    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        HashMap<Integer, Fragment> mPageReferenceMap = new HashMap<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            mPageReferenceMap.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            mPageReferenceMap.remove(position);
        }

        public Fragment getFragment(int key) {
            return mPageReferenceMap.get(key);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);//enables displaying the title
            //return null;//disables the displaying of title on tabs
        }


        public void SetOnSelectView(TabLayout tabLayout,int position)
        {
            TabLayout.Tab tab = tabLayout.getTabAt(position);
            View selected = tab.getCustomView();
            TextView iv_text = (TextView) selected.findViewById(R.id.tv_title);
            iv_text.setTextColor(getResources().getColor(R.color.textcolorblack));
        }


        public void SetUnSelectView(TabLayout tabLayout,int position)
        {
            TabLayout.Tab tab = tabLayout.getTabAt(position);
            View selected = tab.getCustomView();
            TextView iv_text = (TextView) selected.findViewById(R.id.tv_title);
            iv_text.setTextColor(getResources().getColor(R.color.textColorPrimary));
        }


    }


    public void DisplayContent(){

        try{

            MyViewPager();
            pagerListener();
            setupTabIcons();

        }
        catch(Exception e){

            Toast.makeText(this, "error displaying content "+e, Toast.LENGTH_SHORT).show();


        }
    }


}

