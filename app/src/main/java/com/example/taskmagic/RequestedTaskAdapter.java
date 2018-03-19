package com.example.taskmagic;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

/**
 * Created by hyusuf on 2018-03-19.
 */

public class RequestedTaskAdapter extends RecyclerView.Adapter<RequestedTaskAdapter.ViewHolder> {
    private TaskList taskList;
    private Context context;
    private UserTask chosenTask;

    /**
     * used to get an items position id
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * used to get an items type
     * @param position
     * @return
     */

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    /**
     * the TaskAdapter constructor
     * @param taskList
     * @param context
     */

    public RequestedTaskAdapter(TaskList taskList, Context context) {
        this.taskList = taskList;
        this.context = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item,parent,false);
        return  new ViewHolder(view);
    }
    /**
     * binds the data to the view
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final UserTask task=taskList.getTask(position);
        holder.textTitle.setText(task.getTitle());
        holder.textStatus.setText("Status: "+task.getStatus());

        holder.textOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.textOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.viewrequested_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            // triggered when edit popup button clicked
                            case R.id.mnu_item_edit:
                                chosenTask= taskList.getTask(position);
                                Intent myIntent = new Intent(context, ViewTaskActivity.class);
                                myIntent.putExtra("UserTask",chosenTask);
                                context.startActivity(myIntent);
                                break;
                            // triggered when delete popup button clicked
                            case R.id.mnu_item_viewbids:
                                break;
                            case R.id.mnu_item_delete:
                                break;

                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();

            }
        });

    }

    /**
     *  This function returns the number of items in the subscription list
     * @return ArrayList of subscriptions size
     */
    @Override
    public int getItemCount() {
        return taskList.getCount();
    }

    /**
     * This class is responsible for setting item view and data on its place in the recyclerView
     *
     */

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textTitle;
        public TextView textStatus;
        public TextView textOption;

        public ViewHolder(View itemView) {
            super(itemView);
            textTitle=(TextView)itemView.findViewById(R.id.taskTitle);
            textStatus=(TextView)itemView.findViewById(R.id.status);
            textOption=(TextView)itemView.findViewById(R.id.textOption);
        }
    }
}