package com.example.project.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.UniversityModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerAdapterVh> implements Filterable {

    private List<UniversityModel> universityModelList = new ArrayList<>();
    private List<UniversityModel> getUniversityModelList = universityModelList;
    private Context context;
    private SelectedUser selectedUser;


    public RecyclerAdapter(List<UniversityModel> universityModels, SelectedUser selectedUser) {
        this.universityModelList = universityModels;
        this.getUniversityModelList = universityModels;
        this.selectedUser = selectedUser;

    }

    public interface SelectedUser{
        void selectedUser(UniversityModel universityModel);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.values = getUniversityModelList;
                } else {
                    String search = constraint.toString().toLowerCase();
                    List<UniversityModel> userModels = new ArrayList<>();
                    for (UniversityModel userModel : getUniversityModelList) {
                        if (userModel.getName().toLowerCase().contains(search)) {
                            userModels.add(userModel);
                        }
                    }
                    filterResults.values = userModels;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                universityModelList = (List<UniversityModel>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }


    @NonNull
    @Override
    public RecyclerAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
        return new RecyclerAdapterVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterVh holder, int position) {
        UniversityModel universityModel = universityModelList.get(position);
        Integer logo = universityModel.getSrc();
        String univername = universityModel.getName();
        String uid = universityModel.getUid();


        holder.logo.setImageResource(logo);
        holder.univerName.setText(univername);
        holder.uid.setText(uid);
    }

    @Override
    public int getItemCount() {
        return universityModelList.size();
    }


    public class RecyclerAdapterVh extends RecyclerView.ViewHolder{

        private final ImageView logo;
        private final TextView univerName;
        private final TextView uid;

        public RecyclerAdapterVh(@NonNull View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.logo);
            univerName = itemView.findViewById(R.id.univerName);
            uid = itemView.findViewById(R.id.uid);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedUser.selectedUser(universityModelList.get(getAdapterPosition()));
                }
            });
        }

    }

}
