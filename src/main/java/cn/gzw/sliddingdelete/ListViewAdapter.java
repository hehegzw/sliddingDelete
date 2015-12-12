package cn.gzw.sliddingdelete;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gzw on 2015/12/12.
 */
public class ListViewAdapter extends BaseAdapter {
    private List<String> list;
    private LayoutInflater inflater;

    public ListViewAdapter(Context context,List<String> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = inflater.inflate(android.R.layout.simple_list_item_1,null);
            TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
            viewHolder = new ViewHolder();
            viewHolder.tv = tv;
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv.setText(list.get(position));
        return convertView;
    }
    private class ViewHolder{
        public TextView tv;
    }
}
