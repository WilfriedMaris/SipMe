package com.test2.wilfriedmaris.sipme2.fragments;

import android.support.v4.app.Fragment;
import android.util.Log;

public class BaseFragment extends Fragment {
    public void log(String message) {
        String person = "WM: ";
        Log.d(this.getClass().getSimpleName(), person + message);
    }
}
