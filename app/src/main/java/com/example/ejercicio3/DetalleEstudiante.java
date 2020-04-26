package com.example.ejercicio3;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class DetalleEstudiante extends AppCompatActivity {

    Estudiante estudiante;
    ImageView  imageViewDetallePhoto;
    EditText   editTextDetalleNombre;
    EditText   editTextDetalleMatricula;
    EditText   editTextDetalleCiudad;
    EditText   editTextLayoutDetalleExprecion;
    Estudiante currentStudent;
    Button     buttonSaveStudent;
    Button     buttonDeleteSutdent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detalle_estudiante);

        currentStudent = MainActivity.currentStudent;

        editTextDetalleNombre = findViewById(R.id.editTextDetalleNombre);
        editTextDetalleNombre.setText(currentStudent.nombre);

        editTextDetalleMatricula = findViewById(R.id.editTextDetalleMatricula);
        editTextDetalleMatricula.setText(currentStudent.matricula);

        editTextDetalleCiudad = findViewById(R.id.editTextDetalleCiudad);
        editTextDetalleCiudad.setText(currentStudent.ciudad);

        editTextLayoutDetalleExprecion = findViewById(R.id.editTextLayoutDetalleExprecion);
        editTextLayoutDetalleExprecion.setText(currentStudent.exprecion);

        imageViewDetallePhoto = findViewById(R.id.imageViewDetallePhoto);

//        try {
//            imageViewDetallePhoto = findViewById(R.id.imageViewDetallePhoto);
//            Uri myPathPhoto = Uri.parse(currentStudent.foto);
//            imageViewDetallePhoto.setImageURI(myPathPhoto);
//        }catch (Exception e){

            imageViewDetallePhoto.setImageResource(R.drawable.ic_launcher_background);
       // }


        buttonSaveStudent = findViewById(R.id.buttonSaveStudent);

        buttonSaveStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStudent();
            }
        });

        buttonDeleteSutdent = findViewById(R.id.buttonDeleteStudent);

        buttonDeleteSutdent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStudent(currentStudent.id);
            }
        });

    }

    boolean updateStudent(){

        Estudiante newStudent = currentStudent;

        newStudent.nombre      = editTextDetalleNombre.getText().toString();
        newStudent.matricula   = editTextDetalleMatricula.getText().toString();
        newStudent.ciudad      = editTextDetalleCiudad.getText().toString();
        newStudent.exprecion   = editTextLayoutDetalleExprecion.getText().toString();

        boolean a = validarCampo(editTextDetalleNombre, newStudent.nombre, 30);
        boolean b = validarCampo(editTextDetalleMatricula, newStudent.matricula, 30);
        boolean c = validarCampo(editTextDetalleCiudad,  newStudent.ciudad, 30);
        boolean d = validarCampo(editTextLayoutDetalleExprecion, newStudent.exprecion , 600);

        if (a && b && c && d) {
           // limpiarCampos();
            Toast.makeText(getApplicationContext(),
                    "Estudiante Actualizado",Toast.LENGTH_LONG).show();
            return new BDHelper(getApplicationContext()).update(newStudent);
        }

        return false;

    }

    boolean deleteStudent(int estudiante){

        limpiarCampos();
        buttonDeleteSutdent.setEnabled(false);
        buttonSaveStudent.setEnabled(false);

        editTextDetalleNombre.setEnabled(false);
        editTextDetalleMatricula.setEnabled(false);
        editTextDetalleCiudad.setEnabled(false);
        editTextLayoutDetalleExprecion.setEnabled(false);

        Toast.makeText(getApplicationContext(),
                "Estudiante Eliminado",Toast.LENGTH_LONG).show();
        return new BDHelper(getApplicationContext()).delete(estudiante);
    }

    private boolean validarCampo(EditText campo, String nombre, int lengt) {
        //Pattern patron = Pattern.compile("^[a-zA-Z ]+$]");

        if (nombre.isEmpty() || nombre == null || nombre.length() > lengt) {
            campo.setError("Empty");
            return false;
        } else {
            campo.setError(null);
        }

        return nombre.length() < lengt;
    }

    void limpiarCampos(){
        //imageViewDetallePhoto.setImageResource(R.drawable.ic_launcher_background);
        editTextDetalleMatricula.setText("");
        editTextDetalleCiudad.setText("");
        editTextDetalleNombre.setText("");
        editTextLayoutDetalleExprecion.setText("");
    }

}
