package com.knoxpo.rajivsonawala.easytow;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends SingleFragmentActivity {


    @Override
    public Fragment createFragment() {
        return new TodoListFragment();
    }
}