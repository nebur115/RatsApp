package app.stundenplan.ms.rats.ratsapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ruben on 29.03.2018.
 */

public class fragment_parent_stundenplan extends Fragment {
static final int NUM_ITEMS = 26;
    private ViewPager viewPager;
    private MyAdapter frameAdapter;
    private FragmentActivity myContext;


 //Fragmentlayouts.add(new fragment_stundenplan().newInstance(0));



    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.stundenplan_frame, container,Boolean.parseBoolean(null));
        viewPager = (ViewPager) view.findViewById(R.id.myViewPager);
        viewPager.setPageMargin(15);
        viewPager.setPageMarginDrawable(R.color.grey);
        frameAdapter = new MyAdapter(myContext.getSupportFragmentManager());

        viewPager.setAdapter(frameAdapter);

        viewPager.setCurrentItem(0);


        return view;
    }

 public static class MyAdapter extends android.support.v4.app.FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            return fragment_stundenplan.newInstance(position);
        }
    }

    public void reload(){
        if(!(frameAdapter==null))
            frameAdapter.notifyDataSetChanged();

    }

}



