package net.khim.thatime.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import net.khim.thatime.NaverApiManager;
import net.khim.thatime.R;
import net.khim.thatime.values.ValueSrchPlaceHead;
import net.khim.thatime.values.ValueSrchPlaceItem;
import net.khlim.view.list.AdapterSrch;

import java.util.ArrayList;

public class SrchListActivity extends Activity {


    EditText edtSrch;
    Button btnSrch;

    ListView listview;

    AdapterSrch adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srchlist);

        // Adapter 생성
        adapter = new AdapterSrch() ;

        edtSrch = (EditText)findViewById(R.id.edt_srch);
        Intent intent = getIntent();
        if(null != intent){
            String title = intent.getStringExtra("TITLE");
           // Log.d("KK", title);
            edtSrch.setText(title);
        }

        btnSrch = (Button)findViewById(R.id.btn_srch);
        btnSrch.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

                adapter.removeAll();
                String srchStr;
                try{
                    srchStr = edtSrch.getText().toString();
                    if(0 == edtSrch.getText().toString().length()){
                        Toast.makeText(getApplicationContext(), "검색어를 입력해주십시오.", Toast.LENGTH_SHORT).show();
                    }
                }catch (NullPointerException e){
                    Toast.makeText(getApplicationContext(), "검색어를 입력해주십시오.", Toast.LENGTH_SHORT).show();
                    return;
                }


                try{

                    NaverApiManager mn = new NaverApiManager();
                    ValueSrchPlaceHead hd = mn.execute(srchStr).get();

                    Toast.makeText(getApplicationContext(), hd.getTotal() + " 건이 검색 되었습니다.", Toast.LENGTH_SHORT).show();


                    for(int i = 0; i<hd.getItems().size() ; i++) {

                        Log.d("CC", hd.getItem(i).getTitle());
                    }


                    // 데이터 추가
                    ArrayList<ValueSrchPlaceItem> vpList = hd.getItems();

                    for(int i = 0; i<vpList.size() ; i++){

                        adapter.addItem(vpList.get(i));
                    }

                    adapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        // 리스트 선택하여 등록 플레그먼트로 이동
        listview = (ListView) findViewById(R.id.list_srch);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ValueSrchPlaceItem vsp = (ValueSrchPlaceItem) parent.getItemAtPosition(position) ;
                Intent intent = new Intent();
                intent.putExtra("VSP", vsp);
                setResult(Activity.RESULT_OK, intent);
                finish();

            }
        });
    }
}
