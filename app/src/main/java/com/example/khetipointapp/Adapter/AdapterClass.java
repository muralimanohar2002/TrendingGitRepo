package com.example.khetipointapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khetipointapp.ModelClass.ModelClass;
import com.example.khetipointapp.R;
import com.example.khetipointapp.databinding.RepoLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.viewHolder> {
    List<ModelClass> data;
    Context context;

    public AdapterClass(List<ModelClass> data, Context context) {
        this.data = data;
        this.context = context;
    }




    //........................................................................................................................................
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ModelClass mClass = data.get(position);
        holder.binding.owner.setText(mClass.getAuthorName());
        holder.binding.wisWord.setText(mClass.getAbout());
        holder.binding.gitRepName.setText(mClass.getRepoName());
        holder.binding.urlRepo.setText(mClass.getRepoUrl());
        holder.binding.codeLang.setText(mClass.getLanguageUsed());
        holder.binding.rStar.setText(mClass.getCurrStars());
        holder.binding.rFork.setText(mClass.getCurrForks());
        Picasso.get().load(data.get(position).getImageUrl()).placeholder(R.drawable.profilepic).into(holder.binding.gitDp);

        boolean isExpandable = data.get(position).isExpandable();
        holder.binding.expandableLayoutNew.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }




    //.........................................................................................................................................
    public class viewHolder extends RecyclerView.ViewHolder{
        RepoLayoutBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RepoLayoutBinding.bind(itemView);
            binding.ConLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ModelClass modelClass = data.get(getAdapterPosition());
                    modelClass.setExpandable(!modelClass.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
