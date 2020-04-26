package com.example.ejercicio3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class AdapterEstudiante extends RecyclerView.Adapter<AdapterEstudiante.ViewHolderEstudiantes>
 implements View.OnClickListener{

    List<Estudiante> estudianteList;
    private View.OnClickListener listener;


    public AdapterEstudiante(List<Estudiante> estudianteList) {
        this.estudianteList = estudianteList;
    }

    @NonNull
    @Override
    public ViewHolderEstudiantes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.fragment_list_estudiante_con_exprecion,null,false);

        view.setOnClickListener(this);

        return new ViewHolderEstudiantes(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderEstudiantes holder, int position) {

        try{
            holder.asignarEstudiates(estudianteList.get(position));
        }catch (Exception e) {
            Toast.makeText(holder.itemView.getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public int getItemCount() {
        return estudianteList.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolderEstudiantes extends RecyclerView.ViewHolder {

        //Variable
        TextView  textViewRecyclerName;
        TextView  TextViewRecyclerExpression;
        ImageView imageViewRecycler;

        public ViewHolderEstudiantes(@NonNull View itemView) {
            super(itemView);

            textViewRecyclerName = itemView.findViewById(
                    R.id.textViewNameWithExpression);

            TextViewRecyclerExpression = itemView.findViewById(
                    R.id.textViewWithExpression);

            imageViewRecycler = itemView.findViewById(
                    R.id.imageViewWithExpression);

        }

        public void asignarEstudiates(Estudiante estudiante){

            textViewRecyclerName.setText(estudiante.nombre);

            TextViewRecyclerExpression.setText(estudiante.exprecion );
//
//            //TODO
//            if(estudiante.foto != null){
//                try{
//                    Toast.makeText(itemView.getContext(),estudiante.foto,Toast.LENGTH_SHORT).show();
//                    Uri myPath = Uri.parse(estudiante.foto);
//                    imageViewRecycler.setImageURI(myPath);
//                } catch (Exception e){
//
//
//                        imageViewRecycler.setImageResource(R.drawable.ic_launcher_background);
//                }
//            }
//            else{
                imageViewRecycler.setImageResource(R.drawable.ic_launcher_background);
           // }

        }

    }
}
