package net.khim.thatime.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import net.khim.thatime.CategoryManager;
import net.khim.thatime.R;
import net.khim.thatime.values.ValueCategory;
import net.khlim.view.list.AdapterSrch;

import java.util.ArrayList;

public class CategoryActivity extends Activity {


    EditText edtCategory;
    Button btnOk;

    ListView listview;

    AdapterSrch adapter;

    CategoryManager cm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        cm = CategoryManager.getInstance(this);
        final ArrayList<String> items = new ArrayList<String>() ;
        // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, items) ;


        edtCategory = findViewById(R.id.edt_category);

        // listview 생성 및 adapter 지정.
        final ListView listview = (ListView) findViewById(R.id.listview1) ;
        listview.setAdapter(adapter) ;


        Button addButton = (Button)findViewById(R.id.btn_add) ;
        addButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                if(0!=edtCategory.getText().length()){

                    // 아이템 추가.
                    String category = edtCategory.getText().toString();
                    items.add(category);

                    // listview 갱신
                    adapter.notifyDataSetChanged();
                }else{

                    Toast.makeText(getApplicationContext(), "카테고리를 입력해주세요.", Toast.LENGTH_SHORT);
                }
            }
        }) ;

        /*
        Button modifyButton = (Button)findViewById(R.id.modify) ;
        modifyButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int count, checked ;
                count = adapter.getCount() ;

                if (count > 0) {
                    // 현재 선택된 아이템의 position 획득.
                    checked = listview.getCheckedItemPosition();
                    if (checked > -1 && checked < count) {
                        // 아이템 수정
                        items.set(checked, Integer.toString(checked+1) + "번 아이템 수정") ;

                        // listview 갱신
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }) ;

*/
        Button deleteButton = (Button)findViewById(R.id.btn_delete) ;
        deleteButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int count, checked ;
                count = adapter.getCount() ;

                if (count > 0) {
                    // 현재 선택된 아이템의 position 획득.
                    checked = listview.getCheckedItemPosition();

                    if (checked > -1 && checked < count) {
                        // 아이템 삭제
                        items.remove(checked) ;

                        // listview 선택 초기화.
                        listview.clearChoices();

                        // listview 갱신.
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }) ;

        btnOk = (Button)findViewById(R.id.btn_ok) ;
        btnOk.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                cm.deleteAllCategory();

                for(int i = 0 ; i < items.size() ; i++){
                    ValueCategory vc = new ValueCategory();
                    vc.setName(items.get(i));
                    cm.insertCategory(vc);
                }

                finish();

            }
        }) ;

        ArrayList<ValueCategory> vcList = cm.getCategoryList();

        for(int i = 0 ; i < vcList.size() ; i++){
            items.add(vcList.get(i).getName());
        }
        adapter.notifyDataSetChanged();


    }
}
