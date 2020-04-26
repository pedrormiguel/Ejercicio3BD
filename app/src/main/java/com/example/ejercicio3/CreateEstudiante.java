package com.example.ejercicio3;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;

public class CreateEstudiante extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 0;
    private static final int COD_SELE = 10;
    Button buttonGetPath, buttonFormSaveStudent;
    Bitmap imageToStore;
    String selected_image_path;


    EditText editTextNombreCreate;
    TextView textViewRutaPhoto;
    EditText editTextCityCreate;
    EditText editTextNumberIdCreate;
    TextInputLayout textInputLayoutExpression;
    ImageView imageViewIdPhoto;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_estudiante);

        editTextNombreCreate    = findViewById(R.id.editTextNombreCreate);
        editTextCityCreate      = findViewById(R.id.editTextCityCreate);
        editTextNumberIdCreate  = findViewById(R.id.editTextNumberIdCreate);
        textInputLayoutExpression = findViewById(R.id.textInputLayoutExpression);
        textViewRutaPhoto       = findViewById(R.id.textViewRutaPhoto);
        imageViewIdPhoto        = findViewById(R.id.imageViewIdPhoto);

        buttonFormSaveStudent = findViewById(R.id.buttonFormSaveStudent);
        buttonGetPath = findViewById(R.id.buttonGetPath);

        buttonFormSaveStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDatos();
                //new BDHelper(getApplicationContext()).deleteTable();
            }
        });

        buttonGetPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();

//                Toast.makeText(getApplicationContext(),
//                        String.valueOf(GALLERY_REQUEST_CODE),
//                        Toast.LENGTH_LONG).show();
            }
        });



    }

    private void validarDatos() {

        String name       = editTextNombreCreate.getText().toString();
        String pathPhoto  = textViewRutaPhoto.getText().toString();
        String numberId   = editTextNumberIdCreate.getText().toString();
        String city       = editTextCityCreate.getText().toString();
        String expression = textInputLayoutExpression.getEditText()
                 .getText().toString();


        boolean a = validarCampo(editTextNombreCreate, name, 30);
        boolean b = validarCampo(editTextNumberIdCreate, numberId, 30);
        boolean c = validarCampo(editTextCityCreate, city, 30);
        boolean d = validarCampo(textInputLayoutExpression, expression, 600);
        boolean e = validarCampo(textViewRutaPhoto,pathPhoto,200);

        if (a && b && c && d && e) {

            Toast.makeText(getApplicationContext(), "Se guarda el registro", Toast.LENGTH_LONG).show();

            saveStudent(
                    new Estudiante(
                            name,
                            numberId,
                            pathPhoto,
                            city,
                            expression
                           // imageToStore
                            ));

            limpiarCampos();
        }
    }

    boolean saveStudent(Estudiante estudiante){
        return new BDHelper(getApplicationContext()).create(estudiante);
    }

    private void limpiarCampos() {
        editTextNombreCreate.setText("");
        editTextNumberIdCreate.setText("");
        editTextCityCreate.setText("");
        textInputLayoutExpression.getEditText().setText("");
        textViewRutaPhoto.setText("");
        imageViewIdPhoto.setImageResource(R.drawable.noimage);
    }

    private boolean validarCampo(TextInputLayout campo, String nombre, int lengt) {
        //Pattern patron = Pattern.compile("^[a-zA-Z ]+$]");

        if (nombre.isEmpty() || nombre == null || nombre.length() > lengt) {
            campo.getEditText().setError("Nombre inválido");
            return false;
        } else {
            campo.setError(null);
        }

        return nombre.length() < lengt;
    }

    private boolean validarCampo(EditText campo, String nombre, int lengt) {
        //Pattern patron = Pattern.compile("^[a-zA-Z ]+$]");

        if (nombre.isEmpty() || nombre == null || nombre.length() > lengt) {
            campo.setError("Nombre inválido");
            return false;
        } else {
            campo.setError(null);
        }

        return nombre.length() < lengt;
    }

    private boolean validarCampo(TextView campo, String nombre, int lengt) {
        //Pattern patron = Pattern.compile("^[a-zA-Z ]+$]");

        if (nombre.isEmpty() || nombre == null || nombre.length() > lengt) {
            campo.setError("Nombre inválido");
            return false;
        } else {
            campo.setError(null);
        }

        return nombre.length() < lengt;
    }


    private void pickFromGallery() {
        //Create an Intent with action as ACTION_PICK
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        // Sets the type as image/*. This ensures only components of type image are selected
//        intent.setType("image/*");
//        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
//        String[] mimeTypes = {"image/jpeg", "image/png"};
//        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
//        // Launching the Intent
//        startActivityForResult(intent, GALLERY_REQUEST_CODE);

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        intent.setType("image/*");

        startActivityForResult(Intent.createChooser(intent,"Seleccione"),COD_SELE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != 0) {
            Uri myPath = data.getData();
            textViewRutaPhoto.setText(myPath.getPath());

            Uri myPath2 = Uri.parse(textViewRutaPhoto.getText().toString());

            imageViewIdPhoto.setImageURI(myPath2);

            Uri imagePathFile = data.getData();

            try {
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePathFile);

                imageViewIdPhoto.setImageBitmap(imageToStore);

                textViewRutaPhoto.setText(imageToStore.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }}

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 2) {
//            if (resultCode == Activity.RESULT_OK)
//            {
//                Uri selected_image = data.getData();
//                if (selected_image != null) {
//                    final String[] file_path_columns = { MediaStore.Images.Media.DATA };
//                    Cursor cursor = getContentResolver().query(selected_image, file_path_columns, null, null, null);
//                    if (cursor != null) {
//                        cursor.moveToFirst();
//                        int file_path_column_index = cursor.getColumnIndex(file_path_columns[0]);
//                        selected_image_path = cursor.getString(file_path_column_index);
//                        cursor.close();/**/
//                        set_selected_image();
//                    }
//                }
//            }
//        }
//        else
//            super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    private void set_selected_image()
//    {
//        if (selected_image_path != null) {
//            File image = new File(selected_image_path);
//            if (image.exists() && image.canRead()) {
//                Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
//                imageViewIdPhoto.setImageBitmap(bitmap);
//            }
//        }
//    }
//}
