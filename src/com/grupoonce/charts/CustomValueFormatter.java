package com.grupoonce.charts;

import com.github.mikephil.charting.utils.ValueFormatter;

import java.text.DecimalFormat;

public class CustomValueFormatter implements ValueFormatter {

    private DecimalFormat mFormat;
    
    public CustomValueFormatter() {
        mFormat = new DecimalFormat("###,###,###");
    }
    
    @Override
    public String getFormattedValue(float value) {
        return mFormat.format(value);
    }

}