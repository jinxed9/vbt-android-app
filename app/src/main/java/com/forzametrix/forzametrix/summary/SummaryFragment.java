package com.forzametrix.forzametrix.summary;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.forzametrix.forzametrix.R;
import com.forzametrix.forzametrix.R.layout;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */

public class SummaryFragment extends Fragment implements SummaryContract.View {

    View v;
    ExpandableListAdapter mAdapter;
    SummaryContract.Presenter mPresenter;
    ExpandableListView lv;
    Context con;

    int number;

    public SummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(layout.summary_fragment,
                container, false);

        return v;
    }




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        super.onActivityCreated(savedInstanceState);
        ExpandableListView lv = (ExpandableListView) v.findViewById(R.id.summaryList);

        con=getActivity();



        ///set presenter must be called before set adapter.
        //TODO set the presneter in the adapter constructor
        mAdapter=new ExpandableListAdapter(con) ;


        //pass this presenter to the adapter
        mAdapter.setPresenter(mPresenter);
        // setting list adapter
        lv.setAdapter(mAdapter);
        number = 0;

        mAdapter.setNewItems(mPresenter.initHeaders(),mPresenter.initChildren());

    }

    public SummaryContract.View getAdapter(){
        return (SummaryContract.View)lv.getAdapter();
    }


    public void setPresenter(@NonNull SummaryContract.Presenter presenter){
        //set this objects presenter
        mPresenter = checkNotNull(presenter);
    }


    public void refreshView(){
        if(mPresenter != null)
            mAdapter.setNewItems(mPresenter.initHeaders(),mPresenter.initChildren());
    }

}
















class ExpandableListAdapter extends BaseExpandableListAdapter
{
    private SummaryContract.Presenter mPresenter;
    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, List<String>> _listDataChild;

    ExpandableListAdapter(Context con) {
        this._context=con;
    }

    //these methods pull data from the underlying data model
    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        if(_listDataHeader != null && _listDataChild != null){
            String key = (String) getGroup(groupPosition);
            return _listDataChild.get(key).get(childPosititon);
        }
        return "";
        //String repString = mPresenter.getRepString(date,childPosititon);
        //return repString;
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    public void addRep(String rep){

    }

    public void deleteRep(int groupPosition, int childPosition){
        String date = (String)getGroup(groupPosition);
        mPresenter.deleteRep(date, childPosition);
    }


    //i think this just creates a view out of the xml once the data has been pulled from
    //the data model
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(layout.summary_list_item, null);
        }

        //handle buttons and add onClickListeners
        Button deleteBtn = (Button)convertView.findViewById(R.id.delete_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //execute the delete command
                deleteRep(groupPosition,childPosition);
                notifyDataSetChanged();
            }
        });

        TextView txtListChild = (TextView) convertView.findViewById(R.id.summaryList_Child);
        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String key = (String)getGroup(groupPosition);
        if(_listDataChild != null) {
            if (_listDataChild.containsKey(key)){
                return _listDataChild.get(key).size();
            }
            return 0;
        }
        return 0;
        /*
        String date = (String) getGroup(groupPosition);
        int childrenCount = mPresenter.getRepsCount(date);
        return childrenCount;
        */
    }

    @Override
    public Object getGroup(int groupPosition) {
        if(_listDataHeader != null)
            return _listDataHeader.get(groupPosition);
        return 0;
        /*
        // TODO Auto-generated method stub
        String date = mPresenter.getDate(groupPosition);
        return date;
        */
    }

    //this is how it determine how many iterations to go through
    @Override
    public int getGroupCount() {
        if(_listDataHeader != null)
            return _listDataHeader.size();
        return 0;

        /*
        //make a query to the db to return a cursor with the number of unique dates
        int dates = mPresenter.getDatesCount();
        return dates;
        */
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.summary_list_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }


    public void setPresenter(@NonNull SummaryContract.Presenter presenter){
        mPresenter = checkNotNull(presenter);
    }

    public void setNewItems(List<String> listDataHeader, HashMap<String,List<String>> listDataChild){
        this._listDataChild = listDataChild;
        this._listDataHeader = listDataHeader;
        this.notifyDataSetChanged();
    }

}


