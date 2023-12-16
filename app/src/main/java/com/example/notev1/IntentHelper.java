package com.example.notev1;

import android.content.Intent;

public class IntentHelper
{

    public Intent getIntent(MainActivity mainActivity,Class<?> cls )
    {
        return new Intent(mainActivity, cls);
    }
}
