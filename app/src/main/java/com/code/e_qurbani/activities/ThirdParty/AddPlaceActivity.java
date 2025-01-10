package com.code.e_qurbani.activities.ThirdParty;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.code.e_qurbani.R;
import com.code.e_qurbani.activities.seller.AddAnimalDetailActivity;
import com.code.e_qurbani.activities.seller.AnimalListActivity;
import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.databinding.ActivityAddPlaceBinding;
import com.code.e_qurbani.firebase.entity.Animal;
import com.code.e_qurbani.firebase.entity.Place;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.DialogUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.Objects;

public class AddPlaceActivity extends AppCompatActivity {

    ActivityAddPlaceBinding binding;
    FirebaseStorage storage;

    FirebaseFirestore db;
    ActivityResultLauncher<String> launcher;

    String fileName;

    Uri imageUri;

    private boolean isImageUploaded = false;

    FirebaseAuth auth;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPlaceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });


        dialog = new Dialog(this);
        DialogUtils.initLoadingDialog(dialog);

        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    binding.ImAnimalSource.setImageURI(result);
                    binding.tvImageStatus.setText("Completed");
                    binding.tvImageStatus.setTextColor(ContextCompat.getColor(AddPlaceActivity.this, R.color.main));
                    isImageUploaded = true;
                    imageUri = result;
                    fileName = new File(result.getPath()).getName();
                    Toast.makeText(AddPlaceActivity.this, fileName, Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnUploadImage.setOnClickListener(view -> {
            launcher.launch(Constant.IMAGE);
        });

        binding.btnRegister.setOnClickListener(view -> {
            dialog.show();
            if (isProfileValid()) {
                addPlaceData(Constant.THIRD_PARTY);
            }
            else {
                dialog.dismiss();
                Toast.makeText(this, "Form Validation Required", Toast.LENGTH_LONG).show();
            }
        });

    }


    private void addPlaceData (String collection)
    {
        new Handler().post(() -> {
            final StorageReference reference = storage.getReference().child(fileName);
            reference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> reference.getDownloadUrl().addOnSuccessListener(uri -> {
//                        Animal animal = new Animal(binding.tvName.getText().toString(), genderType, Integer.parseInt(binding.tvChildNumberLayout.getText().toString()),
//                                isVaccinated, breedName, isPregnant, dateOfBirth, Double.parseDouble(binding.tvWeight.getText().toString()),
//                                binding.tvDescription.getText().toString(), uri.toString(), "", binding.tvHealth.getText().toString(),Constant.FALSE);

                        Place place = new Place(binding.tvName.getText().toString(), binding.location.getText().toString(), uri.toString(), "");



                        Log.d("TAG", "addAnimalData: " + place);
                        db.collection(Constant.THIRD_PARTY).document(Objects.requireNonNull(auth.getCurrentUser()).getUid()).collection(collection).document().set(place);
                        Log.d("TAG", "addAnimalData: " + db.collection(Constant.SELLER).document(auth.getCurrentUser().getUid()).collection(collection).document().getPath());

                        clearField();
                        dialog.dismiss();
                        Toast.makeText(this, "task successful", Toast.LENGTH_SHORT).show();
//                        Snackbar.make(binding.tvChildLayout, "Task Successful", Snackbar.LENGTH_LONG).setTextColor(ContextCompat.getColor(AddAnimalDetailActivity.this, R.color.green)).show();
                        startActivity(new Intent(this, ThirdPartyListActivity.class).putExtra(Constant.GET_ROLE, Constant.THIRD_PARTY));
                        finish();
                    }
            ).addOnFailureListener(view -> {
                dialog.dismiss();
                Toast.makeText(this, "Image Upload Failed", Toast.LENGTH_SHORT).show();
            })).addOnFailureListener(e -> {
                dialog.dismiss();
                Log.d("TAG", "addAnimalData: ");
                Toast.makeText(AddPlaceActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        });
    }


    private void clearField() {
        binding.tvName.setText(null);
        binding.tvImageStatus.setText(null);
        binding.ImAnimalSource.setImageURI(null);
        binding.location.setText(null);
    }

    private boolean isProfileValid() {
        if (binding.tvName.getText().toString().length() < 4) {
            messageLayout(binding.lName, binding.tvName, "Valid Name Required");
            return false;
        }

        if (binding.location.getText().toString().isEmpty()) {
            messageLayout(binding.lLocation, binding.location, "Child Number Required");
            return false;
        }

        if (!isImageUploaded) {
            Toast.makeText(this, "Image Required", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


    private void messageLayout(TextInputLayout layoutName, TextInputEditText inputEditText, String message) {
        inputEditText.requestFocus();
        layoutName.setError(message);

    }

    private void messageLayout(TextInputLayout layoutName, AutoCompleteTextView inputEditText, String message) {
        inputEditText.requestFocus();
        layoutName.setError(message);

    }
}