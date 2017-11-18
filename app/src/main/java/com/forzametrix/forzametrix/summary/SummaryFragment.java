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
    List<String> _listDataHeader;
    HashMap<String, List<String>> _listDataChild;

    SummaryContract.Presenter mPresenter;
    ExpandableListView lv;
    Context con;
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

        prepareListData();//here get the values and set this values to adoptor and set it visible
        con=getActivity();


        ///set presenter must be called before set adapter.
        //TODO set the presneter in the adapter constructor
        mAdapter=new ExpandableListAdapter(con,_listDataHeader, _listDataChild) ;
        //pass this presenter to the adapter
        mAdapter.setPresenter(mPresenter);
        // setting list adapter
        lv.setAdapter(mAdapter);
    }

    public SummaryContract.View getAdapter(){
        return (SummaryContract.View)lv.getAdapter();
    }

    public void prepareListData()
    {
        // testing purpose
        _listDataHeader = new ArrayList<String>();
        _listDataChild = new HashMap<String, List<String>>();
       // _listDataChild = new ArrayList<String>();

        //declare the references
        //add the parent values to List
        _listDataHeader.add("11/13/17");
        _listDataHeader.add("11/12/17");
        _listDataHeader.add("11/10/17");
        _listDataHeader.add("11/9/17");


        //set Child views to parent
        List<String> reps=new ArrayList<String>();
        reps.add("#1: 6.543 m/s");
        reps.add("#2: 7.543 m/s");
        reps.add("#3: 6.721 m/s");

        List<String> reps2=new ArrayList<String>();
        reps2.add("#1 4.329 m/s");

        List<String> reps3 =new ArrayList<String>();
        reps3.add("#1: 8.432 m/s");

        //set to adoptor
        _listDataChild.put(_listDataHeader.get(0),  reps);
        _listDataChild.put(_listDataHeader.get(1),  reps2);
        _listDataChild.put(_listDataHeader.get(2),  reps3);
        _listDataChild.put(_listDataHeader.get(3),  reps);


    }


    public int getDates(){
        int number = 0;

        number = mAdapter.getGroupCount();

        return 0;
    }

    public void setPresenter(@NonNull SummaryContract.Presenter presenter){
        //set this objects presenter
        mPresenter = checkNotNull(presenter);
    }

}
class ExpandableListAdapter extends BaseExpandableListAdapter
{
    private SummaryContract.Presenter mPresenter;
    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, List<String>> _listDataChild;

    ExpandableListAdapter(Context con, List<String> listDataHeader , HashMap<String, List<String>>  listDataChild)
    {
        this._context=con;
        this._listDataChild=listDataChild;
        this._listDataHeader=listDataHeader;
    }

    //these methods pull data from the underlying data model
    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        String date = (String) getGroup(groupPosition);

        String repString = mPresenter.getRepString(date,childPosititon);

        return repString;
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    public void addRep(String rep){

    }


    //i think this just creates a view out of the xml once the data has been pulled from
    //the data model
    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(layout.summary_list_item, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.summaryList_Child);
        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String date = (String) getGroup(groupPosition);
        int childrenCount = mPresenter.getRepsCount(date);
        return childrenCount;
    }

    @Override
    public Object getGroup(int groupPosition) {

        // TODO Auto-generated method stub
        String date = mPresenter.getDate(groupPosition);
        //return this._listDataHeader.get(groupPosition);
        return date;
    }

    //this is how it determine how many iterations to go through
    @Override
    public int getGroupCount() {
        //make a query to the db to return a cursor with the number of unique dates
        int dates = mPresenter.getDatesCount();
        return dates;
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
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }


    public void setPresenter(@NonNull SummaryContract.Presenter presenter){
        mPresenter = checkNotNull(presenter);
    }
}


