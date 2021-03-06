/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

/**
 * Created by steve on 2018-03-18.
 */


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * This Fragement is responsible for showing the you the task requestors list of Assigned tasks
 */

public class AssignedFrag extends Fragment {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private FireBaseManager fmanager;
    private TaskList listUserTask;


    /**
     * this function show the creates the view of the fragment to be displayed
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.assigned_frag,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerAssigned);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        UserSingleton singleton=UserSingleton.getInstance();
        fmanager = new FireBaseManager(singleton.getmAuth(), getActivity());
        listener(singleton.getUserId());
        //listens to any touches on the recyleview list items
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("Assigned frag item: ", "clicked " + position);
                UserTask chosenTask= listUserTask.getTask(position);
                Intent myIntent = new Intent(getActivity(), ViewTaskActivity.class);
                myIntent.putExtra("UserTask",chosenTask);
                startActivity(myIntent);
                updateView(listUserTask);
            }
        }));

        return view;
    }

    /**
     * Updates the ListView; selects which TaskList to display
     * @param requestor
     */
    private void listener(final String requestor) {
        fmanager.getAssignedTasks(requestor, new OnGetAssignedTaskListener() {
            @Override
            public void onSuccess(TaskList taskList) {
                Log.d("Succes", "onSuccess: "+taskList.getCount());
                listUserTask = taskList;
                updateView(taskList);
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    /**
     * Sets the list on the Adapter to specified TaskList
     * @param taskList
     */
    public void updateView(TaskList taskList){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new TaskRecyclerAdapter(taskList,getActivity());
        recyclerView.setAdapter(adapter);
    }
}