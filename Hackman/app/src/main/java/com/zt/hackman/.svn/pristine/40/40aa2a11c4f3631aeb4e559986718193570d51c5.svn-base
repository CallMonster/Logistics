package com.zt.hackman.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zt.hackman.R;


/**
 * Created by Eugene on 2016/4/22.
 */
public class CyTabView extends RadioGroup {

    private Context context;
    private LayoutInflater inflater;

    private RadioButton homeView,searchView,intractView,personalView;
    public CyTabView(Context context) {
        super(context);
        this.context = context;
        init();
    }



    public CyTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context =context;
        init();
    }

    private void init(){
        initViews();
    }
    private void initViews(){
        inflater = LayoutInflater.from(context);

        homeView = (RadioButton) inflater.inflate(R.layout.radio_home,this,false);
        searchView = (RadioButton) inflater.inflate(R.layout.radio_order,this,false);
        intractView  =(RadioButton) inflater.inflate(R.layout.radio_personal,this,false);
        this.setOrientation(RadioGroup.HORIZONTAL);
        this.addView(homeView,0);
        this.addView(searchView,1);
        this.addView(intractView,2);
        //this.setOnCheckedChangeListener();

    }
}
