package cn.gzw.sliddingdelete;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private MyListView listView;
    private ListViewAdapter adapter;
    private List<String> datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (MyListView) findViewById(R.id.listView);
        initData();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,R.layout.list_item,datas);
        listView.setAdapter(adapter1);
        listView.setOnItemClickListener(this);
        listView.setListViewItemDeleteListener(new MyListView.ListViewItemDeleteListener() {
            @Override
            public void onDelete(View view, int position) {
                datas.remove(position);
            }
        });
    }

    private void initData() {
        datas = new ArrayList<>();
        for (int i=0;i<10;i++){
            datas.add("item"+i);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
    }
}
