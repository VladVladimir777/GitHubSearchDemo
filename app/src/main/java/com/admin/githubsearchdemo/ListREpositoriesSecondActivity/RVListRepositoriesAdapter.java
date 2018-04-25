package com.admin.githubsearchdemo.ListREpositoriesSecondActivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.admin.githubsearchdemo.GitHubPojo.Repos;
import com.admin.githubsearchdemo.R;
import java.util.List;

public class RVListRepositoriesAdapter
        extends RecyclerView.Adapter<RVListRepositoriesAdapter.ReposViewHolder> {


    private List<Repos> reposList;

    public static class ReposViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvDescription;

        public ReposViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }
    }


    public RVListRepositoriesAdapter(List<Repos> reposList) {
        this.reposList = reposList;

    }

    @Override
    public int getItemCount() {
        return reposList.size();
    }


    @Override
    public RVListRepositoriesAdapter.ReposViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_repositories_layout, parent, false);
        ReposViewHolder reposViewHolder = new ReposViewHolder(view);
        return reposViewHolder;
    }

    @Override
    public void onBindViewHolder(ReposViewHolder holder, int position) {
        holder.tvName.setText(reposList.get(position).getName());
        holder.tvDescription.setText(reposList.get(position).getDescription());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
