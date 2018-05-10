package app.stundenplan.ms.rats.ratsapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruben on 29.03.2018.
 */

public class fragment_parent_stundenplan extends Fragment {

    List <fragment_stundenplan> Fragmentlayouts = new ArrayList<fragment_stundenplan>();
    int currentpage = 0;
    OnSwipeTouchListener onSwipeTouchListener;
    int lastCreatetPage = 2;
    boolean intranstion = false;
    boolean loaded = false;


    public fragment_parent_stundenplan(){
    }

    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.stundenplan_frame, container,Boolean.parseBoolean(null));
        Fragmentlayouts.add(new fragment_stundenplan(0));
        Fragmentlayouts.add(new fragment_stundenplan(1));
        Fragmentlayouts.add(new fragment_stundenplan(2));


        FrameLayout myFramelayout = view.findViewById(R.id.myFramelayout);
        android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.add(R.id.myFramelayout, Fragmentlayouts.get(1));
        ft.add(R.id.myFramelayout, Fragmentlayouts.get(0));
        ft.hide(Fragmentlayouts.get(1));
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);


        loaded = true;

        onSwipeTouchListener = new OnSwipeTouchListener(getContext()) {
            public void onSwipeTop() {

            }
            public void onSwipeRight() {

                if (vertretungsplan.isStundenlanactive()) {


                    if (!intranstion) {


                        intranstion = true;

                        if (currentpage > 0) {

                            currentpage--;

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                            ft.hide(Fragmentlayouts.get(currentpage + 1));
                            ft.show(Fragmentlayouts.get(currentpage));

                            ft.commitNow();


                            FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                            ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            ft2.setCustomAnimations(R.anim.do_not_show, R.anim.do_not_show);
                            if (currentpage > 0) {

                                ft2.add(R.id.myFramelayout, Fragmentlayouts.get(currentpage - 1));
                                ft2.hide(Fragmentlayouts.get(currentpage - 1));
                            }
                            ft2.remove(Fragmentlayouts.get(currentpage + 2));

                            ft2.commit();

                        }
                        intranstion = false;
                    }
                }
            }


            public void onSwipeLeft() {

                if (vertretungsplan.isStundenlanactive()) {

                    if (!intranstion) {

                        intranstion = true;
                        currentpage++;


                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.hide(Fragmentlayouts.get(currentpage - 1));
                        ft.show(Fragmentlayouts.get(currentpage));

                        ft.commitNow();


                        FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                        ft2.setCustomAnimations(R.anim.do_not_show, R.anim.do_not_show);
                        ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);


                        if (currentpage > 1) {
                            ft2.remove(Fragmentlayouts.get(currentpage - 2));
                        }
                        ft2.add(R.id.myFramelayout, Fragmentlayouts.get(currentpage + 1));
                        ft2.hide(Fragmentlayouts.get(currentpage + 1));
                        ft2.commit();

                        intranstion = false;


                        if (lastCreatetPage == currentpage + 1) {
                            lastCreatetPage++;
                            Fragmentlayouts.add(new fragment_stundenplan(currentpage + 2));
                        }
                    }
                }
            }
            public void onSwipeBottom() {

            }
        };
        view.setOnTouchListener(onSwipeTouchListener);

        return view;
    }

    public void reload() {
        if (loaded) {
            Fragmentlayouts.get(0).reload();
        }
    }

}