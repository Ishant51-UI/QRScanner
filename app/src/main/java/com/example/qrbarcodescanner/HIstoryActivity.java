package com.example.qrbarcodescanner;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class HIstoryActivity extends AppCompatActivity {



        RecyclerView recyclerView;

        HistoryAdapter adapter;
        ImageView image;

        List<scanhistory> list = new ArrayList<>();

        Toolbar toolbar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_history);

            recyclerView=findViewById(R.id.recyclerView);
            image=findViewById(R.id.image);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }

            ImageButton btnBack = findViewById(R.id.btnBack);

            btnBack.setOnClickListener(v -> finish());

            recyclerView.setLayoutManager(
                    new LinearLayoutManager(this));


     adapter = new HistoryAdapter(this, list);

     recyclerView.setAdapter(adapter);

     appdatabase db = databaseclient.getDatabase(this);

     Executors.newSingleThreadExecutor().execute(() -> {

         List<scanhistory> history =
                 db.scanHistoryDao().getAllHistory();


         runOnUiThread(() -> {

             list.clear();
             list.addAll(history);

             adapter.notifyDataSetChanged();


             if (list.isEmpty()) {

                 recyclerView.setVisibility(View.GONE);
                 image.setVisibility(View.VISIBLE);

             } else {

                 recyclerView.setVisibility(View.VISIBLE);
                 image.setVisibility(View.GONE);

             }

         });

     });
            ItemTouchHelper.SimpleCallback callback =
                    new ItemTouchHelper.SimpleCallback(0,
                            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                        @Override
                        public boolean onMove(@NonNull RecyclerView recyclerView,
                                              @NonNull RecyclerView.ViewHolder viewHolder,
                                              @NonNull RecyclerView.ViewHolder target) {
                            return false;
                        }

                        @Override
                        public void onSwiped(@NonNull RecyclerView.ViewHolder holder,
                                             int direction) {

                            int position = holder.getBindingAdapterPosition();

                            scanhistory item = list.get(position);

                            Executors.newSingleThreadExecutor().execute(() -> {

                                db.scanHistoryDao().delete(item);

                                runOnUiThread(() -> {

                                    list.remove(position);
                                    adapter.notifyItemRemoved(position);

                                    if(list.isEmpty()){
                                        recyclerView.setVisibility(View.GONE);
                                        image.setVisibility(View.VISIBLE);
                                    }

                                });

                            });

                        }
                    };

            new ItemTouchHelper(callback).attachToRecyclerView(recyclerView);


    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}