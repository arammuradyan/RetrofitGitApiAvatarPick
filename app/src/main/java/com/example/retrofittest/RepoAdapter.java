package com.example.retrofittest;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {

    private List<RepoItem> repoItems;
    private WeakReference<RepoClickListener> repoClickListenerWeakReference;

    public RepoAdapter(RepoClickListener repoClickListener) {
        this.repoItems=new ArrayList<>();
        repoClickListenerWeakReference=new WeakReference<>(repoClickListener);
    }

    public void setRepoList(List<RepoItem> items){
        repoItems.clear();
        repoItems.addAll(items);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RepoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_item,parent,false),repoClickListenerWeakReference);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {

        holder.bind(repoItems.get(position));
    }

    @Override
    public int getItemCount() {
        return repoItems.size();
    }

    static class RepoViewHolder extends  RecyclerView.ViewHolder{

        TextView name_tv , full_name_tv;
        ImageView imageView;

        public RepoViewHolder(@NonNull final View itemView, final WeakReference<RepoClickListener> repoClickListenerWeakReference) {
            super(itemView);
            name_tv=itemView.findViewById(R.id.name_tv);
            full_name_tv=itemView.findViewById(R.id.full_name_tv);
            imageView=itemView.findViewById(R.id.rv_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 RepoClickListener reference = repoClickListenerWeakReference.get();
                 if (reference!=null){
                     reference.onRepoClick(getAdapterPosition());
                 }

                }
            });
        }

        void bind(RepoItem repoItem){
            Uri uri=Uri.parse(repoItem.repoOwner.avatarUri);
            name_tv.setText(repoItem.name);
            full_name_tv.setText(repoItem.fullName);
            Picasso.get().load(uri).into(imageView);
        }
    }
}
