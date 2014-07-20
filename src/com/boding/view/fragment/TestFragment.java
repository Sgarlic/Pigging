package com.boding.view.fragment;


import java.util.ArrayList;
import java.util.List;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boding.R;
import com.boding.app.CitySelectActivity;
import com.boding.constants.Constants;
import com.boding.constants.GlobalVariables;
import com.boding.constants.IntentRequestCode;
import com.boding.model.LowPriceSubscribe;
import com.boding.util.DateUtil;
import com.boding.view.dialog.SelectionDialog;
import com.boding.view.dialog.SelectionDialog.OnItemSelectedListener;

public class TestFragment extends Fragment {
    private View currentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	currentView = inflater.inflate(R.layout.fragment_test, container, false);
        return currentView;
    }
    
}
