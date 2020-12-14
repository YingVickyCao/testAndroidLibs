package com.hades.android.example.butterknife;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TestFragmemt extends Fragment {
    public static final String TAG = TestFragmemt.class.getSimpleName();

    Unbinder unbinder;

    @BindView(R.id.btn)
    Button btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);

        if (null == btn) {
            Log.d(TAG, "onCreateView: btn is null");
        } else {
            Log.d(TAG, "onCreateView: btn is @" + btn.hashCode()); // onCreateView: btn is @220787194
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (null == btn) {
            Log.d(TAG, "onViewCreated:1 btn is null");
        } else {
            Log.d(TAG, "onViewCreated:1 btn is @" + btn.hashCode());  // onViewCreated: btn is @220787194
        }

        unbinder = ButterKnife.bind(this, view);

        super.onViewCreated(view, savedInstanceState);
        if (null == btn) {
            Log.d(TAG, "onViewCreated: 2 btn is null");
        } else {
            Log.d(TAG, "onViewCreated: 2 btn is @" + btn.hashCode());  // onViewCreated: btn is @220787194
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != unbinder) {
            unbinder.unbind();
        }

        if (null == btn) {
            Log.d(TAG, "onDestroyView: btn is null");   // btn is null
        } else {
            Log.d(TAG, "onDestroyView: btn is @" + btn.hashCode());
        }
    }

    @OnClick(R.id.btn)
    public void btnClick() {
        Toast.makeText(getActivity(), "@OnClick", Toast.LENGTH_SHORT).show();
    }

}
