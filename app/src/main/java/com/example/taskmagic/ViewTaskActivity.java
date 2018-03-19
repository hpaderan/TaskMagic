package com.example.taskmagic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by hyusuf on 2018-03-11.
 */

public class ViewTaskActivity extends AppCompatActivity {
    private FireBaseManager fmanager;
    private DatabaseReference db;
    private FirebaseAuth auth;
    private TextView titleText;
    private TextView descriptionText;
    private TextView dateText ;
    private ImageView photo;
    private Button button;
    private UserTask task;
    private ProgressDialog mProgress;
    private Boolean taskOwenr;
    private String date = "2018-08-08";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        final UserSingleton singleton = UserSingleton.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
        auth = singleton.getmAuth();
        fmanager = new FireBaseManager(singleton.getmAuth(),db,getApplicationContext());
        mProgress = new ProgressDialog(this);

        task = (UserTask) getIntent().getSerializableExtra("task");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fireworks);
        Photo image = new Photo(bitmap);
        task.setPhoto(image);

        Button button_viewLocation = (Button) findViewById(R.id.button_viewLocation);


        button = (Button) findViewById(R.id.button_viewTask);
        titleText = (TextView) findViewById(R.id.textView_titleContent);
        descriptionText = (TextView) findViewById(R.id.textView_descriptionContent);
        dateText = (TextView) findViewById(R.id.textView_dateContent);
        photo = (ImageView) findViewById(R.id.imageView1);

        if (task.getRequester().equals(singleton.getUserId())) {
            taskOwenr = true;

        } else {
            taskOwenr = false;
            Log.d("viewtask", "onCreate: "+task.getRequester()+singleton.getUserId());
        }
        final BidDialog bidDialog = new BidDialog(this, task, new BidDialog.onDialogListener() {
            @Override
            public void onEnsure(String amount) {
                Bid bid = new Bid(task.getId(), Float.valueOf(amount), singleton.getUserId());
                bidOnTask(bid);
            }
        });

        final EditDialog editDialog = new EditDialog(this, date, task, new EditDialog.onDialogListener() {
            @Override
            public void onEnsure(String title, String date, String description, Photo image ) {
                //UPDATE EDITING
                task.setDate(date);
                task.setDescription(description);
                task.setTitle(title);
                task.setPhoto(image);
                fmanager.editTask(task);
            }
        });
        initView();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskOwenr) {
                    if (task.allowEditing()) {
                        editDialog.show();

                    } else {
                        //shows message that you're not allowed to edit this task
                    }

                } else {
                    if (task.allowBidding()) {
                        bidDialog.show();
                    } else {
                        //shows message that the task is done or assigned.
                    }
                }
            }
        });

        button_viewLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
            }
        });
    }

    private void initView() {
        titleText.setText(task.getTitle());
        descriptionText.setText(task.getDescription());
        dateText.setText(task.getDate());
        if (task.getPhoto() != null) {
            photo.setImageBitmap(task.getPhoto().getImage());
        }
        if (taskOwenr) {
            button.setText("EDIT");
        } else {
            button.setText("BID");
        }
    }

    private void bidOnTask(Bid bid) {
        mProgress.setMessage("Adding");
        mProgress.show();
        fmanager.addBid(bid);
        mProgress.dismiss();
    }
}
