package com.example.fernando.recervascancha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MenuCliente extends AppCompatActivity {

    private LinkedHashMap<String, GroupInfo> subjects = new LinkedHashMap<String, GroupInfo>();
    private ArrayList<GroupInfo> deptList = new ArrayList<GroupInfo>();

    String usuario;
    int tipo;

    private CustomAdapter listAdapter;
    private ExpandableListView simpleExpandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cliente);

        Bundle datos = getIntent().getExtras();
        usuario = datos.getString("usuario");
        tipo = datos.getInt("tipo");

        // add data for displaying in expandable list view
        loadData();
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //get reference of the ExpandableListView
        simpleExpandableListView = (ExpandableListView) findViewById(R.id.simpleExpandableListView);
        // create the adapter by passing your ArrayList data
        listAdapter = new CustomAdapter(MenuCliente.this, deptList);/*****/
        // attach the adapter to the expandable list view
        simpleExpandableListView.setAdapter(listAdapter);

        //expand all the Groups
        //expandAll();

        //collarpse all the Groups
        collapseAll();

        // setOnChildClickListener listener for child row click
        simpleExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //get the group header
                GroupInfo headerInfo = deptList.get(groupPosition);
                //get the child info
                ChildInfo detailInfo =  headerInfo.getProductList().get(childPosition);
                //display it or do something with it
                /****** MUESTA MENSAJE INDICANDO A DONDE DISTE CLICK ******/
                /*Toast.makeText(getBaseContext(), " Clicked on :: " + headerInfo.getName()
                        + "/" + detailInfo.getName(), Toast.LENGTH_LONG).show();*/

                if(groupPosition == 0 && childPosition == 0){
                    Intent objCrudUsuarios = new Intent(getApplicationContext(),CrudUsuarios.class);
                    objCrudUsuarios.putExtra("usuario",usuario);
                    objCrudUsuarios.putExtra("tipo",tipo);
                    startActivity(objCrudUsuarios);
                }else if (groupPosition == 1 && childPosition == 0){
                    Intent objListaUsuarios = new Intent(getApplicationContext(),Reservas.class);
                    objListaUsuarios.putExtra("usuario",usuario);
                    objListaUsuarios.putExtra("tipo",tipo);
                    startActivity(objListaUsuarios);
                }else if (groupPosition == 1 && childPosition == 1){
                    Intent objListaUsuarios = new Intent(getApplicationContext(),ListaReservas.class);
                    objListaUsuarios.putExtra("usuario",usuario);
                    objListaUsuarios.putExtra("tipo",tipo);
                    startActivity(objListaUsuarios);
                }else if (groupPosition == 1 && childPosition == 2){
                    Intent objListaUsuarios = new Intent(getApplicationContext(),Historial.class);
                    objListaUsuarios.putExtra("usuario",usuario);
                    objListaUsuarios.putExtra("tipo",tipo);
                    startActivity(objListaUsuarios);
                }

                return false;
            }
        });
        // setOnGroupClickListener listener for group heading click
        simpleExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //get the group header
                GroupInfo headerInfo = deptList.get(groupPosition);
                //display it or do something with it
                /****** MUESTA MENSAJE INDICANDO A DONDE DISTE CLICK ******/
                /*Toast.makeText(getBaseContext(), " Header is :: " + headerInfo.getName(),
                        Toast.LENGTH_LONG).show();*/

                return false;
            }
        });
    }

    //method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            simpleExpandableListView.expandGroup(i);
        }
    }

    //method to collapse all groups
    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            simpleExpandableListView.collapseGroup(i);
        }
    }

    //load some initial data into out list
    private void loadData(){

        addProduct("USUARIO","MANTENIMIENTO");
        //addProduct("USUARIO","LISTADO");

        addProduct("RESERVAS","REALIZAR RESERVA");
        addProduct("RESERVAS","LISTADO");
        addProduct("RESERVAS","HISTORIAL");

    }



    //here we maintain our products in various departments
    private int addProduct(String department, String product){

        int groupPosition = 0;

        //check the hash map if the group already exists
        GroupInfo headerInfo = subjects.get(department);
        //add the group if doesn't exists
        if(headerInfo == null){
            headerInfo = new GroupInfo();
            headerInfo.setName(department);
            subjects.put(department, headerInfo);
            deptList.add(headerInfo);
        }

        //get the children for the group
        ArrayList<ChildInfo> productList = headerInfo.getProductList();
        //size of the children list
        int listSize = productList.size();
        //add to the counter
        listSize++;

        //create a new child and add that to the group
        ChildInfo detailInfo = new ChildInfo();
        detailInfo.setSequence(String.valueOf(listSize));
        detailInfo.setName(product);
        productList.add(detailInfo);
        headerInfo.setProductList(productList);

        //find the group position inside the list
        groupPosition = deptList.indexOf(headerInfo);
        return groupPosition;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);

    }
}
