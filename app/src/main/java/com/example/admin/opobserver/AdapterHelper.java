package com.example.admin.opobserver;

import android.content.Context;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AdapterHelper {
    public ArrayList<String> groupItem, tempChild;
    final String ATTR_GROUP_NAME = "groupName";
    final String ATTR_CHILD_NAME = "childName";
    final String ATTR_CHILD_DESC = "childDesc";
    final String ATTR_CHILD_ICON = "childIcon";

    //collection for groups
    ArrayList<Map<String, String>> groupData;

    //collection for one group of elements
    ArrayList<Map<String, String>> childDataItem;

    //collection for all elements
    ArrayList<ArrayList<Map<String, String>>> childData;

    //attributes
    Map<String, String> m;
    Context ctx;

    SimpleExpandableListAdapter adapter;
    AdapterHelper(Context _ctx) {
        ctx = _ctx;
    }


    SimpleExpandableListAdapter getAdapter(String[] groups,String[][] elements, String[][] elements2) {
        //fill collection of groups
        groupData = new ArrayList<Map<String, String>>();
        for (
                String group : groups)

        {
            //fill list of attributes for each group
            m = new HashMap<String, String>();
            m.put(ATTR_GROUP_NAME, group); // name of group
            groupData.add(m);
        }

        // list of group's attributes
        String groupFrom[] = new String[]{ATTR_GROUP_NAME};
        // List of ID view-elements
        int groupTo[] = new int[]{android.R.id.text1};
        childData = new ArrayList<ArrayList<Map<String, String>>>();
        childDataItem = new ArrayList<Map<String, String>>();
        //fill list of attributes for each element
        for(int i=0; i< elements[0].length; i++){
                m = new HashMap<String, String>();

                m.put(ATTR_CHILD_NAME, elements[0][i]); // name of activities
                m.put(ATTR_CHILD_DESC, elements[1][i]); // description of activities
                childDataItem.add(m);
        }
        // add to collection of collections
        childData.add(childDataItem);
        // Collection of elements for group 2
        childDataItem = new ArrayList<Map<String, String>>();
        for(int i=0; i<elements2[0].length; i++){
            m = new HashMap<String, String>();
            m.put(ATTR_CHILD_NAME, elements2[0][i]); // name of activities
            m.put(ATTR_CHILD_DESC, elements2[1][i]); // name of activities
            childDataItem.add(m);
        }
        childData.add(childDataItem);
        // List of attributes for reading
        String childFrom[] = new String[]{ATTR_CHILD_NAME,ATTR_CHILD_DESC};
        // List of ID view-elements,in which put attributes
        int childTo[] = new int[]{android.R.id.text1,android.R.id.text2};
        adapter = new SimpleExpandableListAdapter(
                ctx,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                groupFrom,
                groupTo,
                childData,
                android.R.layout.simple_list_item_2,
                childFrom,
                childTo);

        return  adapter;
    }
















    SimpleExpandableListAdapter getAdapter(String[] groups,String[] elements, String[] elements2) {
        //fill collection of groups
        groupData = new ArrayList<Map<String, String>>();
        for (
                String group : groups)

        {
            //fill list of attributes for each group
            m = new HashMap<String, String>();
            m.put(ATTR_GROUP_NAME, group); // name of group
            groupData.add(m);
        }

        // list of group's attributes
        String groupFrom[] = new String[]{ATTR_GROUP_NAME};
        // список ID view-элементов, в которые будет помещены атрибуты групп
        int groupTo[] = new int[]{android.R.id.text1};

        // создаем коллекцию для коллекций элементов
        childData = new ArrayList<ArrayList<Map<String, String>>>();

        // создаем коллекцию элементов для первой группы

        childDataItem = new ArrayList<Map<String, String>>();
        // заполняем список атрибутов для каждого элемента
        for (
                String s1 : elements)

        {
            m = new HashMap<String, String>();
            m.put(ATTR_CHILD_NAME, s1); // name of activities
            childDataItem.add(m);
        }

        // добавляем в коллекцию коллекций
        childData.add(childDataItem);


        // создаем коллекцию элементов для второй группы
        childDataItem = new ArrayList<Map<String, String>>();
        for (
                String s1 : elements2)

        {
            m = new HashMap<String, String>();
            m.put(ATTR_CHILD_NAME, s1);
            childDataItem.add(m);
        }
        childData.add(childDataItem);



        // список атрибутов элементов для чтения
        String childFrom[] = new String[]{ATTR_CHILD_NAME};
        // список ID view-элементов, в которые будет помещены атрибуты элементов
        int childTo[] = new int[]{android.R.id.text1};

        adapter = new SimpleExpandableListAdapter(
                ctx,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                groupFrom,
                groupTo,
                childData,
                android.R.layout.simple_list_item_1,
                childFrom,
                childTo);

        return  adapter;
    }

    SimpleExpandableListAdapter getAdapter(String[] groups,String[] elements) {
        //fill collection of groups
        groupData = new ArrayList<Map<String, String>>();
        for (
                String group : groups)

        {
            //fill list of attributes for each group
            m = new HashMap<String, String>();
            m.put(ATTR_GROUP_NAME, group); // name of group
            groupData.add(m);
        }

        // list of group's attributes
        // список атрибутов групп для чтения
        String groupFrom[] = new String[]{ATTR_GROUP_NAME};
        // список ID view-элементов, в которые будет помещены атрибуты групп
        int groupTo[] = new int[]{android.R.id.text1};

        // создаем коллекцию для коллекций элементов
        childData = new ArrayList<ArrayList<Map<String, String>>>();

        // создаем коллекцию элементов для первой группы

        childDataItem = new ArrayList<Map<String, String>>();
        // заполняем список атрибутов для каждого элемента
        for (
                String s1 : elements)

        {
            m = new HashMap<String, String>();
            m.put(ATTR_CHILD_NAME, s1); // name of activities
            childDataItem.add(m);
        }

        // добавляем в коллекцию коллекций
        childData.add(childDataItem);



        // список атрибутов элементов для чтения
        String childFrom[] = new String[]{ATTR_CHILD_NAME};
        // список ID view-элементов, в которые будет помещены атрибуты элементов
        int childTo[] = new int[]{android.R.id.text1};

        adapter = new SimpleExpandableListAdapter(
                ctx,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                groupFrom,
                groupTo,
                childData,
                android.R.layout.simple_list_item_2,
                childFrom,
                childTo);

        return  adapter;
    }

    String getGroupText(int groupPos) {
        return ((Map<String,String>)(adapter.getGroup(groupPos))).get(ATTR_GROUP_NAME);
    }

    String getChildText(int groupPos, int childPos) {
        return ((Map<String,String>)(adapter.getChild(groupPos, childPos))).get(ATTR_CHILD_NAME);
    }

    String getChildText2(int groupPos, int childPos) {
        return ((Map<String,String>)(adapter.getChild(groupPos, childPos))).get(ATTR_CHILD_DESC);
    }


    String getGroupChildText(int groupPos, int childPos) {
        return getGroupText(groupPos) + " " +  getChildText(groupPos, childPos);
    }
        }
