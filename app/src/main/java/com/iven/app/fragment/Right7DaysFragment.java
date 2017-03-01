package com.iven.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iven.app.R;

/**
 * @author Iven
 * @date 2017/3/1 17:14
 * @Description
 */

public class Right7DaysFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_right7days, null, false);
        return view;
    }
}
