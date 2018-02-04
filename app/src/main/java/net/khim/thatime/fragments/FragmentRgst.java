package net.khim.thatime.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.khim.thatime.CategoryManager;
import net.khim.thatime.activities.MainActivity;
import net.khim.thatime.PlaceManager;
import net.khim.thatime.R;
import net.khim.thatime.activities.SrchListActivity;
import net.khim.thatime.values.ValueCategory;
import net.khim.thatime.values.ValuePlace;
import net.khim.thatime.values.ValueSrchPlaceItem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static android.app.Activity.RESULT_OK;

public class FragmentRgst extends Fragment implements MainActivity.OnActivityResult {

    public static final int GALLERY_CODE = 10001;
    public static final int SEARCH_CODE = 10002;

    private final int EMPTY_VALUE = -1;

    long valuePlaceKey = -1;

    Button btnSave;
    Button btnDelete;
    Button btnLocation;
    Button btnDate;

    EditText edtTitle;
    EditText edtMemo;


    TextView txtAdress;
    ImageView ivGallery;

    int mYear, mMonth, mDay;

    PlaceManager placeManager;

    Spinner spnCategory;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        placeManager = PlaceManager.getInstance(getActivity());


        View v = inflater.inflate(R.layout.layout_rgst, container, false);

        edtTitle = (EditText) v.findViewById(R.id.edt_title);
        edtMemo = (EditText) v.findViewById(R.id.edt_memo);

        btnSave = (Button) v.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "저장", Toast.LENGTH_SHORT).show();
                btnSave.setEnabled(false);

                if(valuePlaceKey == EMPTY_VALUE){
                    Log.d("AA", "등록");
                    ValuePlace vp = new ValuePlace();
                    vp.setTitle(edtTitle.getText().toString());
                    vp.setMemo(edtMemo.getText().toString());
                    vp.setDate(btnDate.getText().toString());

                    placeManager.insertPlace(vp);

                }else{
                    Log.d("AA", "업데이트");
                    ValuePlace vp = placeManager.getPlace(valuePlaceKey);
                    vp.setTitle(edtTitle.getText().toString());
                    vp.setMemo(edtMemo.getText().toString());
                    placeManager.updatePlace(vp);
                }

            }
        });

        btnDelete = (Button) v.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(getActivity());
                alert_confirm.setMessage("삭제하시겠습니까?").setCancelable(false)
                    .setNegativeButton("취소",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 취소
                        }
                    }).setPositiveButton("삭제",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            placeManager.deletePlace(valuePlaceKey);
                            FragmentList fragmentList = new FragmentList();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content, fragmentList).commit();

                        }
                    });

                AlertDialog alert = alert_confirm.create();
                alert.show();

            }
        });


        btnLocation = (Button) v.findViewById(R.id.btn_location);
        btnLocation.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), SrchListActivity.class);
                String title = edtTitle.getText().toString();
                intent.putExtra("TITLE", title);
                getActivity().startActivityForResult(intent, SEARCH_CODE);


            }
        });

        btnDate = (Button) v.findViewById(R.id.btn_date);
        btnDate.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(getActivity(), mDateSetListener, mYear, mMonth, mDay).show();

            }
        });

        txtAdress = (TextView) v.findViewById(R.id.txt_address);

        ivGallery = (ImageView) v.findViewById(R.id.iv_gallery);
        ivGallery.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "이미지", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                getActivity().startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_CODE);

            }
        });



        CategoryManager cm = CategoryManager.getInstance(getActivity());
        ArrayList<ValueCategory> vc = cm.getCategoryList();

        String category[] = cm.getCategoryArray();

        spnCategory = (Spinner)v.findViewById(R.id.spn_category);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_spinner_item, // 레이아웃
                category); // 데이터   
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnCategory.setAdapter(adapter);


        // 데이터 가져오기

        try {
            valuePlaceKey = getArguments().getLong("KEY");
            Log.d("AA", "기존글 : "+ valuePlaceKey);
            ValuePlace vp = placeManager.getPlace(valuePlaceKey);
            edtTitle.setText(vp.getTitle());
            edtMemo.setText(vp.getMemo());

            BottomNavigationView navigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation);
            Menu menu = navigation.getMenu();
            menu.getItem(3).setChecked(false); // 3번째 매뉴 비활성화
            menu.getItem(2).setChecked(true);  // 2번재 매뉴 활성화 (마지막에 실행되어야 잘 적용됨)

        }catch (NullPointerException e){
            Log.d("AA", "신규글");
            initData();
        }


        // 데이터 가져오기 끝
        return v;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("AA", "신규글");

        initData();
    }


    void initData(){

        valuePlaceKey = -1;
        edtTitle.setText("");
        edtTitle.setHint(R.string.rgst_init_title);
        edtMemo.setText("");
        edtMemo.setHint(R.string.rgst_init_memo);
        btnDelete.setEnabled(false);

        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        btnDate.setText(String.format("%d/%d/%d", mYear, mMonth + 1, mDay));
    }

    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {


        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            btnDate.setText(String.format("%d/%d/%d", mYear, mMonth + 1, mDay));

        }

    };

    @Override
    public void onActivityResultForFragment(int requestCode, int resultCode, Intent data) {
        Log.d("AA", "requestCode : " + requestCode);
        Log.d("AA", "resultCode : " + resultCode);

        if (resultCode == RESULT_OK && requestCode == GALLERY_CODE) {

            try {
                Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                ivGallery.setImageBitmap(image_bitmap);

            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        if (resultCode == RESULT_OK && requestCode == SEARCH_CODE) {

            ValueSrchPlaceItem placeItem = (ValueSrchPlaceItem)data.getSerializableExtra("VSP");
            txtAdress.setText(placeItem.getAddress());



            Toast.makeText(getActivity(), placeItem.toString(), Toast.LENGTH_SHORT).show();




        }
    }
}