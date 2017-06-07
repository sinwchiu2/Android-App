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

public class Private extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private int lastExpandedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private);
        initToolbar();
        initExpListView();
        initComponent();
        setEventListener();
    }

    public void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle(R.string.letter_informal);
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

                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

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
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();

                if (groupPosition == 0 && childPosition == 0)
                {
                    try {
                        childClicked(v, groupPosition, childPosition);
                    } catch (ClassNotFoundException e) {
                        Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
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
        listDataHeader.add("私函");
        listDataHeader.add("請柬");
        listDataHeader.add("邀請信");
        listDataHeader.add("賀辭");
        listDataHeader.add("ComingSoon");

        // Adding child data
        List<String> data0 = new ArrayList<String>();
        data0.add("兒童畫版");

        List<String> data1 = new ArrayList<String>();
        data1.add("範本一");
        data1.add("範本二");
        data1.add("範本三");

        List<String> data2 = new ArrayList<String>();
        data2.add("範本一");
        data2.add("範本二");
        data2.add("範本三");

        List<String> data3 = new ArrayList<String>();
        data3.add("範本一");
        data3.add("範本二");
        data3.add("範本三");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("ComingSoon");
        comingSoon.add("ComingSoon");
        comingSoon.add("ComingSoon");

        listDataChild.put(listDataHeader.get(0), data0); // Header, Child data
        listDataChild.put(listDataHeader.get(1), data1);
        listDataChild.put(listDataHeader.get(2), data2);
        listDataChild.put(listDataHeader.get(3), data3);
        listDataChild.put(listDataHeader.get(4), comingSoon);
    }

    public void childClicked(View view, int gid, int cid) throws ClassNotFoundException {
        Class<?> c = Class.forName("com.example.user.kraken");
        Intent intent = new Intent(this, c);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
