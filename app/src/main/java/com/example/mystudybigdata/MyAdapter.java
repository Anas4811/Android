package com.example.mystudybigdata;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ProfessorViewHolder> {
    private List<Professor> professorList;

    public MyAdapter(List<Professor> professorList) {
        this.professorList = professorList;
    }

    @NonNull
    @Override
    public ProfessorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.professor_item, parent, false);
        return new ProfessorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfessorViewHolder holder, int position) {
        Professor professor = professorList.get(position);
        holder.nameText.setText(professor.getName());
        holder.emailText.setText(professor.getEmail());
        holder.matiere.setText(professor.getMatiere());
    }

    @Override
    public int getItemCount() {
        return professorList.size();
    }

    public static class ProfessorViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, emailText,matiere;

        public ProfessorViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.professor_name);
            emailText = itemView.findViewById(R.id.professor_email);
            matiere=itemView.findViewById(R.id.matiere);
        }
    }
}