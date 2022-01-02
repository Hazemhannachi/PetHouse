package com.example.pethouse;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<Model,MainAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull Model model) {
        holder.nomPet.setText(model.getNomPet());
        holder.age.setText(model.getAge());
        holder.sexe.setText(model.getSexe());

        Glide.with(holder.image.getContext())
                .load(model.getImage())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.image);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.image.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_pet))
                        .setExpanded(true,1200)
                        .create();


                View view = dialogPlus.getHolderView();

                EditText name = view.findViewById(R.id.txtname);
                EditText age = view.findViewById(R.id.age);
                EditText sexe = view.findViewById(R.id.sexe);
                EditText image = view.findViewById(R.id.image);

                Button btnUpdate = view.findViewById(R.id.btnUpdate);

                name.setText(model.getNomPet());
                age.setText(model.getAge());
                sexe.setText(model.getSexe());
                image.setText(model.getImage());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("nomPet",name.getText().toString());
                        map.put("age",age.getText().toString());
                        map.put("sexe",sexe.getText().toString());
                        map.put("image",image.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Pets")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.nomPet.getContext(),"Updated successfully",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.nomPet.getContext(),"Erreur while Updated ",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();

                                    }
                                });

                    }
                });








            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.nomPet.getContext());
                builder.setTitle("Are you Sure ?");
                builder.setMessage("Deleted data can't be undo. ");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Pets")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.nomPet.getContext(),"Cancelled",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image ;
        TextView nomPet,age,sexe ;

        Button btnEdit,btnDelete,btnConsulter;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            image = (CircleImageView)itemView.findViewById(R.id.img1);
            nomPet=(TextView) itemView.findViewById(R.id.namePet);
            age=(TextView) itemView.findViewById(R.id.age);
            sexe=(TextView) itemView.findViewById(R.id.sexe);

            btnEdit = (Button)itemView.findViewById(R.id.btn);
            btnDelete = (Button)itemView.findViewById(R.id.delete);
            btnConsulter = (Button)itemView.findViewById(R.id.consulter);




        }
    }
}
