package net.khim.thatime.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import net.khim.thatime.R;
import net.khlim.view.list.Adapter;
import net.khlim.view.list.Item;

public class FragmentHome extends Fragment implements Menu{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        return inflater.inflate(R.layout.layout_main, container, false);
    }

}