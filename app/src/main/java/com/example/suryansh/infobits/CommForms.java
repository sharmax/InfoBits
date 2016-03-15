package com.example.suryansh.infobits;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;

/**
 * Created by Abhishek on 3/10/2016.
 */
public class CommForms extends CommunicationPanel {

    int[] Forms = {R.layout.breco, R.layout.dlost, R.layout.database, R.layout.service, R.layout.breview, R.layout.feedback};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Forms[convform]);
    }
}
