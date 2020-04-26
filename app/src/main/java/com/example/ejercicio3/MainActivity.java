package com.example.ejercicio3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Estudiante> estudianteList;
    RecyclerView recyclerView;
    static public Estudiante currentStudent;
    Button buttonCreateStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById( R.id.recyclerId );

        //recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        estudianteList = getStudenList();
        buttonCreateStudent = findViewById(R.id.buttonCreateStudent);

        AdapterEstudiante adapterEstudiante = new AdapterEstudiante(estudianteList);

        adapterEstudiante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 MainActivity.currentStudent = estudianteList.get(
                        recyclerView.getChildAdapterPosition(v));

                Intent intent = new Intent(getApplicationContext(),
                                DetalleEstudiante.class);
                startActivity(intent);

            }
        });

        buttonCreateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),
                        CreateEstudiante.class);

                startActivity(intent);
            }
        });


        recyclerView.setAdapter(adapterEstudiante);

    }

   public List<Estudiante> getStudenList(){
      return new BDHelper(getApplicationContext()).read();
    }
}
