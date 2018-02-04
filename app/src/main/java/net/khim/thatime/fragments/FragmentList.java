package net.khim.thatime.fragments;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import net.khim.thatime.PlaceManager;
import net.khim.thatime.R;
import net.khim.thatime.values.ValuePlace;
import net.khlim.view.list.Adapter;

import java.util.ArrayList;

public class FragmentList extends Fragment implements net.khim.thatime.fragments.Menu{

    PlaceManager placeManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        placeManager = PlaceManager.getInstance(getActivity());


        View v = inflater.inflate(R.layout.layout_list, container, false);

        ListView listview ;
        Adapter adapter;

        // Adapter 생성
        adapter = new Adapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) v.findViewById(R.id.listview1);
        listview.setAdapter(adapter);



        // 데이터 추가
        ArrayList<ValuePlace> vpList = placeManager.getPlaceAll();

        for(int i = 0; i<vpList.size() ; i++){

            adapter.addItem(vpList.get(i).getKey(), ContextCompat.getDrawable(getActivity(), R.drawable.home),
                    vpList.get(i).getTitle(), vpList.get(i).getMemo()) ;
        }

        // 데이터 추가 종료


        //adapter.addItem();


        /*
        // 첫 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.home),
                "Box", "Account Box Black 36dp") ;
        // 두 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.home),
                "Circle", "Account Circle Black 36dp") ;
        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.home),
                "Ind", "Assignment Ind Black 36dp") ;
        */
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ValuePlace vp = (ValuePlace) parent.getItemAtPosition(position) ;

                long key = vp.getKey() ;
//                String descStr = vp.getMemo() ;
//                Drawable iconDrawable = vp.getImg() ;
                Log.d("AA", ">>" + key + "클릭!");
                Log.d("AA", ">>" + vp.toString() + "클릭!");


                FragmentRgst fragmentRgst = new FragmentRgst();
                Bundle bundle = new Bundle(1);
                bundle.putLong("KEY", key);
                fragmentRgst.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content, fragmentRgst).commit();


                /*
                MenuItem m1  = navigation.getMenu().getItem(R.id.btn_bottom_list);
                m1.setChecked(false);
                MenuItem m2  = navigation.getMenu().getItem(R.id.btn_bottom_register);
                m2.setChecked(true);
                */


            }
        });

        BottomNavigationView navigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();
        menu.getItem(MAP).setChecked(false); // 2번째 매뉴 비활성화
        menu.getItem(RGST).setChecked(true);  // 3번재 매뉴 활성화 (마지막에 실행되어야 잘 적용됨)

        return v;
    }

}