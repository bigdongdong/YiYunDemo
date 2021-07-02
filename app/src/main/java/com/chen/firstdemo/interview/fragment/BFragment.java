package com.chen.firstdemo.interview.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chen.firstdemo.R;

public class BFragment extends Fragment {
    private final String TAG = "BFragment";
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.i(TAG, "onAttach: B");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate: B");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.i(TAG, "onCreateView: B");

        return LayoutInflater.from(this.getContext()).inflate(R.layout.fragment_b ,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i(TAG, "onActivityCreated: B");
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.i(TAG, "onStart: B");
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i(TAG, "onResume: B");
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.i(TAG, "onPause: B");
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.i(TAG, "onStop: B");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Log.i(TAG, "onDestroyView: B");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "onDestroy: B");
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.i(TAG, "onDetach: B");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        Log.i(TAG, "setUserVisibleHint: B "+isVisibleToUser);
    }

    @Override
    public boolean getUserVisibleHint() {
        Log.i(TAG, "getUserVisibleHint: B");

        return super.getUserVisibleHint();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: B");
    }
}
