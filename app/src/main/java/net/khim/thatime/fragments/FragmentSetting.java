package net.khim.thatime.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.khim.thatime.activities.CategoryActivity;
import net.khim.thatime.R;

public class FragmentSetting extends Fragment {

    TextView tvCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.layout_setting, container, false);

        tvCategory = (TextView) v.findViewById(R.id.tv_category);
        tvCategory.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "클릭", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

}