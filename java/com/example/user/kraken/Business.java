package com.example.user.kraken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Business extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private int lastExpandedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        initToolbar();
        initExpListView();
        initComponent();
        setEventListener();
    }

    public void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle(R.string.letter_formal);
    }

    public void initComponent(){
    }

    public void setEventListener(){
    }

    public void initExpListView() {
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    expListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;

            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();

                try {
                    childClicked(v, groupPosition, childPosition);
                } catch (ClassNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                return false;
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("公函");
        listDataHeader.add("求職信");
        listDataHeader.add("申請信");
        listDataHeader.add("投訴信");
        listDataHeader.add("新聞稿");
        listDataHeader.add("表揚信");
        listDataHeader.add("啟事");
        listDataHeader.add("公告");
        listDataHeader.add("佈告");
        listDataHeader.add("推薦信");
        listDataHeader.add("ComingSoon");

        // Adding child data
        List<String> data0 = new ArrayList<String>();
        data0.add("範本一");

        List<String> data1 = new ArrayList<String>();
        data1.add("範本一");

        List<String> data2 = new ArrayList<String>();
        data2.add("範本一");

        List<String> data3 = new ArrayList<String>();
        data3.add("範本一");

        List<String> data4 = new ArrayList<String>();
        data4.add("範本一");

        List<String> data5 = new ArrayList<String>();
        data5.add("範本一");

        List<String> data6 = new ArrayList<String>();
        data6.add("範本一");

        List<String> data7 = new ArrayList<String>();
        data7.add("範本一");

        List<String> data8 = new ArrayList<String>();
        data8.add("範本一");

        List<String> data9 = new ArrayList<String>();
        data9.add("範本一");

        List<String> data10 = new ArrayList<String>();
        data10.add("範本一");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("ComingSoon");
        comingSoon.add("ComingSoon");
        comingSoon.add("ComingSoon");

        listDataChild.put(listDataHeader.get(0), data0); // Header, Child data
        listDataChild.put(listDataHeader.get(1), data1);
        listDataChild.put(listDataHeader.get(2), data2);
        listDataChild.put(listDataHeader.get(3), data3);
        listDataChild.put(listDataHeader.get(4), data4);
        listDataChild.put(listDataHeader.get(5), data5);
        listDataChild.put(listDataHeader.get(6), data6);
        listDataChild.put(listDataHeader.get(7), data7);
        listDataChild.put(listDataHeader.get(8), data8);
        listDataChild.put(listDataHeader.get(9), data9);
        listDataChild.put(listDataHeader.get(10), comingSoon);
    }

    public void childClicked(View view, int gid, int cid) throws ClassNotFoundException {
        Class<?> c = Class.forName("com.example.user.kraken.business.Business"+"_"+gid+"_"+cid);
        Intent intent = new Intent(this, c);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);
    }
}