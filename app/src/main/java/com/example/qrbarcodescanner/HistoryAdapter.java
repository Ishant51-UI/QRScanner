package com.example.qrbarcodescanner;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter
        extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    Context context;
    List<scanhistory> list;

    public HistoryAdapter(Context context, List<scanhistory> list) {
        this.context = context;
        this.list = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtData, txtType, txtDate;
        Button btnCopy, btnOpen, btnShare;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtData = itemView.findViewById(R.id.txtData);
            txtType = itemView.findViewById(R.id.txtType);
            txtDate = itemView.findViewById(R.id.txtDate);

            btnCopy = itemView.findViewById(R.id.btnCopy);
            btnOpen = itemView.findViewById(R.id.btnOpen);
            btnShare = itemView.findViewById(R.id.btnShare);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.history_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        scanhistory item = list.get(position);

        String text = item.getText();

        holder.txtData.setText(text);
        holder.txtType.setText(item.getType());
        holder.txtDate.setText(item.getDate());


        // =========================
        // COPY
        // =========================

        holder.btnCopy.setOnClickListener(v -> {

            ClipboardManager clipboard =
                    (ClipboardManager)
                            context.getSystemService(
                                    Context.CLIPBOARD_SERVICE
                            );

            ClipData clip =
                    ClipData.newPlainText(
                            "Scanned QR",
                            text
                    );

            clipboard.setPrimaryClip(clip);

            Toast.makeText(
                    context,
                    "Copied",
                    Toast.LENGTH_SHORT
            ).show();
        });


        // =========================
        // OPEN URL
        // =========================

        if (text.startsWith("http://")
                || text.startsWith("https://")) {

            holder.btnOpen.setVisibility(View.VISIBLE);

            holder.btnOpen.setOnClickListener(v -> {

                Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(text)
                );

                context.startActivity(intent);
            });

        } else {

            holder.btnOpen.setVisibility(View.GONE);
        }


        // =========================
        // SHARE
        // =========================

        holder.btnShare.setOnClickListener(v -> {

            Intent shareIntent =
                    new Intent(Intent.ACTION_SEND);

            shareIntent.setType("text/plain");

            shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    text
            );

            context.startActivity(
                    Intent.createChooser(
                            shareIntent,
                            "Share with"
                    )
            );
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}