package com.forzametrix.forzametrix.summary;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.ExpandableListAdapter;

import com.forzametrix.forzametrix.BasePresenter;
import com.forzametrix.forzametrix.R;
import com.forzametrix.forzametrix.R.layout;
import android.widget.Toast;
import android.widget.SimpleExpandableListAdapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */

public class SummaryFragment extends Fragment {

    View v;
    ExpandableListAdapter mAdapter;
    List<String> _listDataHeader;
    HashMap<String, List<String>> _listDataChild;

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
        //parent=new Parent();
        //child=new Child();
        ExpandableListView lv = (ExpandableListView) v.findViewById(R.id.summaryList);

        //here setting all the values to Parent and child classes
        setDataValues();
        prepareListData();//here get the values and set this values to adoptor and set it visible
        con=getActivity();

        mAdapter=new ExpandabelListAdoptor(con,_listDataHeader, _listDataChild) ; //here i didnt set list values to this adoptor

        // mAdapter = new ExpandableListAdapter(this, _listDataHeader, _listDataChild);

        // setting list adapter
        lv.setAdapter(mAdapter);

    }

    public void prepareListData()
    {
        // testing purpose
        _listDataHeader = new ArrayList<String>();
        _listDataChild = new HashMap<String, List<String>>();
       // _listDataChild = new ArrayList<String>();

        // declare the references
        //add the parent values to List
        _listDataHeader.add("Testing");
        _listDataHeader.add("100");
        _listDataHeader.add("200");
        _listDataHeader.add("300");


        //set Child views to parent
        List<String> cardDetails=new ArrayList<String>();
        cardDetails.add("");

        List<String> mininum_sal_details=new ArrayList<String>();
        mininum_sal_details.add("1000");

        List<String> interest_details=new ArrayList<String>();
        interest_details.add(".432");

        //set to adoptor

        _listDataChild.put(_listDataHeader.get(0),  cardDetails);
        _listDataChild.put(_listDataHeader.get(1),mininum_sal_details);
        _listDataChild.put(_listDataHeader.get(2),interest_details);
        _listDataChild.put(_listDataHeader.get(3),cardDetails);

    }

    public void setDataValues()
    {
        //set Parent values
       // parent.setCardName("Platinum credit Card");
      //  parent.setMinimum_salary(15000.00);
      //  parent.setInterest_Rate(1.2);

        //set Child values
      //  child.set_card_details("You require minimum salary of 1500 per month");
      //  child.set_interest_rate_details("interest rate is 2.0%");
    }

}
class ExpandabelListAdoptor extends BaseExpandableListAdapter
{

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, List<String>> _listDataChild;

    ExpandabelListAdoptor(Context con,List<String> listDataHeader ,HashMap<String, List<String>>  listDataChild )
    {
        this._context=con;
        this._listDataChild=listDataChild;
        this._listDataHeader=listDataHeader;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }


    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(layout.summary_list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.summaryList_Child);
        txtListChild.setText(childText);
        return convertView;

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //this method is causing the crashing. Not sure why.
        String item = this._listDataHeader.get(groupPosition);
        List<String> item2 = this._listDataChild.get(item);
        int size = item2.size();

        //return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
        return size;
    }

    @Override
    public Object getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return this._listDataHeader.size();
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
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

}


