package com.aymentlili.aamoomor.Fragments.Home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.aymentlili.aamoomor.Activitys.Home;
import com.aymentlili.aamoomor.Entitys.Estate;
import com.aymentlili.aamoomor.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class Estateee extends Fragment {
    public Estate e;

    public Estateee(Estate s) {
        this.e=s;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.estateee, viewGroup, false);
        TabLayout tabLayout = view.findViewById(R.id.Estate_TabLayout);
        ViewPager viewPager = view.findViewById(R.id.Estate_View_Pager);
        Home h = (Home) getActivity();
        View_Pager_Adapter viewPagerAdapter = new View_Pager_Adapter(getChildFragmentManager(),BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        Estate_profile estate_profile = new Estate_profile(e);
        Log.d("Estate clicked",estate_profile.e.name);
        viewPagerAdapter.addFragment(estate_profile,e.name );
        viewPagerAdapter.addFragment(new Chats(e.owner),e.owner );
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;

    }
    public class View_Pager_Adapter extends FragmentPagerAdapter {
        public ArrayList<Fragment> fragments ;
        public ArrayList<String> titlles ;


        public View_Pager_Adapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
            fragments = new ArrayList<>();
            titlles = new ArrayList<>();
        }

        public View_Pager_Adapter(@NonNull FragmentManager fm, int behavior, ArrayList<Fragment> fragments, ArrayList<String> titlles) {
            super(fm, behavior);
            this.fragments = fragments;
            this.titlles = titlles;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
        public void addFragment(Fragment fragment,String title)
        {
            fragments.add(fragment);
            titlles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titlles.get(position);
        }
    }
}
