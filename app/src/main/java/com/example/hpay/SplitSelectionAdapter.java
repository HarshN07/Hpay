package com.example.hpay;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SplitSelectionAdapter extends RecyclerView.Adapter<SplitSelectionAdapter.SplitViewHolder> {

    private List<LoginResult> userList;
    private List<String> selectedUsers;

    public SplitSelectionAdapter(List<LoginResult> userList, List<String> selectedUsers) {
        this.userList = userList;
        this.selectedUsers = selectedUsers;
    }
    public SplitViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list,parent,false);
        return new SplitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SplitViewHolder holder, int position) {
        LoginResult user = userList.get(position);
        holder.textView.setText(user.getName());

        if(selectedUsers.contains(user.getEmail())){
            holder.imageView.setVisibility(View.VISIBLE);
        }
        else{
            holder.imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    //View Holder Class
    class SplitViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private ImageView imageView;

        public SplitViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.uName);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        LoginResult selectedUser=userList.get(position);
                        String selectedEmail=selectedUser.getEmail();
                        if (selectedUsers.contains(selectedEmail)){
                            selectedUsers.remove(selectedEmail);
                        }
                        else{
                            selectedUsers.add(selectedEmail);
                        }
                        notifyDataSetChanged();
                    }
                }
            });

        }
    }
}
