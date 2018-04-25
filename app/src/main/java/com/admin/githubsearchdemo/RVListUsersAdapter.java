package com.admin.githubsearchdemo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.admin.githubsearchdemo.GitHubPojo.Item;
import com.admin.githubsearchdemo.ListREpositoriesSecondActivity.ListRepositoriesActivity;
import com.squareup.picasso.Picasso;
import java.util.List;

public class RVListUsersAdapter
        extends RecyclerView.Adapter<RVListUsersAdapter.ItemViewHolder> {

    private List<Item> itemList;
    private Context context;

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout llContainer;
        public ImageView ivAvatar;
        public TextView tvLogin;
        public TextView tvId;
        public TextView tvUrl;

        public ItemViewHolder(View itemView) {
            super(itemView);
            llContainer = itemView.findViewById(R.id.llContainer);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            tvLogin = itemView.findViewById(R.id.tvLogin);
            tvId = itemView.findViewById(R.id.tvId);
            tvUrl = itemView.findViewById(R.id.tvUrl);
        }
    }


    public RVListUsersAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    @Override
    public RVListUsersAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_users_layout, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        // Load logo
        Picasso.with(context)
                .load(itemList.get(position).getAvatar_url())
                .resize(250, 250)
                .into(holder.ivAvatar);

        // Set info
        holder.tvLogin.setText(itemList.get(position).getLogin());
        holder.tvId.setText("ID " + itemList.get(position).getId());
        holder.tvUrl.setText(itemList.get(position).getUrl());

        // Set listener on the container
        holder.llContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ListRepositoriesActivity.class);
                i.putExtra("login", itemList.get(position).getLogin());
                context.startActivity(i);

            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}