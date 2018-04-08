package com.example.trile.foodlocation.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.trile.foodlocation.LoginFragment;
import com.example.trile.foodlocation.RegisterFragment;

/**
 * Created by TRILE on 07/04/2018.
 */

public class AdapterTabLogin extends FragmentPagerAdapter {

    private String listtab[] = {"Đăng nhập","Đăng kí"};
    private RegisterFragment registerFragment;
    private LoginFragment loginFragment;

    public AdapterTabLogin(FragmentManager fm) {
        super(fm);
        registerFragment = new RegisterFragment();
        loginFragment = new LoginFragment();
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
        {
            return loginFragment;
        }else if(position == 1)
        {
            return registerFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return listtab.length;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return listtab[position];
    }
}
