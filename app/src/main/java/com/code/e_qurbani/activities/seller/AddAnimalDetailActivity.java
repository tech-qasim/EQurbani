package com.code.e_qurbani.activities.seller;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.code.e_qurbani.R;
import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.databinding.ActivityAddAnimalDetailBinding;
import com.code.e_qurbani.firebase.entity.Animal;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.DialogUtils;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.Objects;

public class AddAnimalDetailActivity extends AppCompatActivity {
    ActivityAddAnimalDetailBinding binding;
    private Dialog dialog;
    private MaterialDatePicker dateOfBirthPicker;
    private final String[] genderList = new String[]{Constant.MALE, Constant.FEMALE};
    private final String[] pregnantList = new String[]{Constant.YES, Constant.NO};
    private final String[] vaccinatedList = new String[]{Constant.YES, Constant.NO};
    private String[] breedList;
    private String animalType = null;
    private boolean genderSelected = false;
    private boolean breedSelected = false;
    private boolean isImageUploaded = false;
    private boolean isFemale = false;
    private String breedName, genderType, dateOfBirth, isPregnant, isVaccinated;
    FragmentManager fragmentManager;
    ActivityResultLauncher<String> launcher;
    Uri imageUri;
    String fileName;
    String properFormat = "";
    FirebaseStorage storage;
    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAnimalDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /**
         * set breed spinner according to role
         */
        setupAnimalBreed();
        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        /**
         binding.fab.setOnClickListener(view -> {
         startActivity(new Intent(this, ChatBotScreen.class));
         });
         */

        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });
        dialog = new Dialog(this);
        DialogUtils.initLoadingDialog(dialog);
        handleGenderMenu();
        handleBreedMenu();
        handlePregnantMenu();
        handleVaccinatedMenu();
        dateOfBirthPicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").build();
        binding.datePicker.setOnClickListener(view -> {
            showDatePickerDialogue(dateOfBirthPicker);
        });
        /**
         * date picker click listener
         */
        datePickerClickListener();
        /**
         * image gallery launcher
         */
        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    binding.ImAnimalSource.setImageURI(result);
                    binding.tvImageStatus.setText("Completed");
                    binding.tvImageStatus.setTextColor(ContextCompat.getColor(AddAnimalDetailActivity.this, R.color.main));
                    isImageUploaded = true;
                    imageUri = result;
                    fileName = new File(result.getPath()).getName();
                }
            }
        });

        binding.btnUploadImage.setOnClickListener(view -> {
            launcher.launch(Constant.IMAGE);
        });
        binding.btnRegister.setOnClickListener(view -> {
            dialog.show();
            if (isProfileValid()) {
                switch (animalType) {
                    case Constant.COW:
                        addAnimalData(Constant.COW);
                        break;
                    case Constant.GOAT: {
                        addAnimalData(Constant.GOAT);
                        break;
                    }
                    case Constant.SHEEP: {
                        addAnimalData(Constant.SHEEP);
                        break;
                    }
                    case Constant.CAMEL:
                        addAnimalData(Constant.CAMEL);
                        break;
                    default:
                        Toast.makeText(this, "NONE BREED", Toast.LENGTH_SHORT).show();
                }
            } else {
                dialog.dismiss();
                Toast.makeText(this, "Form Validation Required", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addAnimalData(String collection) {
        new Handler().post(() -> {
            final StorageReference reference = storage.getReference().child(fileName);
            reference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> reference.getDownloadUrl().addOnSuccessListener(uri -> {
                        Animal animal = new Animal(binding.tvName.getText().toString(), genderType, Integer.parseInt(binding.tvChildNumberLayout.getText().toString()),
                                isVaccinated, breedName, isPregnant, dateOfBirth, Double.parseDouble(binding.tvWeight.getText().toString()),
                                binding.tvDescription.getText().toString(), uri.toString(), "", binding.tvHealth.getText().toString(),Constant.FALSE);
                Log.d("TAG", "addAnimalData: " +animal);
                        db.collection(Constant.SELLER).document(Objects.requireNonNull(auth.getCurrentUser()).getUid()).collection(collection).document().set(animal);
                Log.d("TAG", "addAnimalData: " + db.collection(Constant.SELLER).document(auth.getCurrentUser().getUid()).collection(collection).document().getPath());

                        clearField();
                        dialog.dismiss();
                        Snackbar.make(binding.tvChildLayout, "Task Successful", Snackbar.LENGTH_LONG).setTextColor(ContextCompat.getColor(AddAnimalDetailActivity.this, R.color.green)).show();
                        startActivity(new Intent(this, AnimalListActivity.class).putExtra(Constant.GET_ROLE, animalType));
                        finish();
                    }
            ).addOnFailureListener(view -> {
                dialog.dismiss();
                Toast.makeText(this, "Image Upload Failed", Toast.LENGTH_SHORT).show();
            })).addOnFailureListener(e -> {
                dialog.dismiss();
                Log.d("TAG", "addAnimalData: ");
                Toast.makeText(AddAnimalDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        });

    }

    private void clearField() {
        binding.tvName.setText(null);
        binding.tvHealth.setText(null);
        binding.tvDescription.setText(null);
        binding.tvHealth.setText(null);
        binding.tvChildNumberLayout.setText(null);
        binding.tvWeight.setText(null);
        binding.tvImageStatus.setText(null);
        binding.ImAnimalSource.setImageURI(null);
        binding.selectGender.setText(null);
        binding.selectBreed.setText(null);
        binding.selectPregnant.setText(null);
    }

    private void datePickerClickListener() {
        dateOfBirthPicker.addOnPositiveButtonClickListener(picker -> {
            properFormat = dateOfBirthPicker.getHeaderText().replace(" ", "-");
            binding.datePicker.setText(properFormat);
            dateOfBirth = properFormat;
        });
    }

    private boolean isProfileValid() {
        if (binding.tvName.getText().toString().length() < 4) {
            messageLayout(binding.lName, binding.tvName, "Valid Name Required");
            return false;
        }
        if (!genderSelected) {
            messageLayout(binding.lgender, binding.selectGender, "Gender Required");
            return false;
        }
        if (isFemale) {
            if (isPregnant.isEmpty()) {
                messageLayout(binding.pregnantLayout, binding.selectPregnant, "Required");
                return false;
            }

        }
        if (binding.tvChildNumberLayout.getText().toString().isEmpty()) {
            messageLayout(binding.tvChildLayout, binding.tvChildNumberLayout, "Child Number Required");
            return false;
        }
        if (isVaccinated == null) {
            messageLayout(binding.tvVaccineLayout, binding.tvVaccinate, "Required");
            return false;
        }
        if (breedName == null) {
            messageLayout(binding.menuBreed, binding.selectBreed, "Required");
            return false;
        }
        if (binding.tvWeight.getText().toString().isEmpty()) {
            messageLayout(binding.lweight, binding.tvWeight, "Required");
        }
        if (!isImageUploaded) {
            Toast.makeText(this, "Image Required", Toast.LENGTH_LONG).show();
            return false;
        }
        if (binding.tvDescription.getText().toString().length() < 20) {
            messageLayout(binding.descLayout, binding.tvDescription, "Proper Description Required");
            return false;
        }
        if (binding.tvHealth.getText().toString().isEmpty()) {
            messageLayout(binding.lHealth, binding.tvHealth, "Proper Description Required");
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

    private void handleGenderMenu() {
        ArrayAdapter roleAdapter = new ArrayAdapter(this, R.layout.marketing_list, genderList);
        binding.selectGender.setAdapter(roleAdapter);
        ((AutoCompleteTextView) binding.lgender.getEditText()).setOnItemClickListener((adapterView, view, position, l) -> {
            genderType = roleAdapter.getItem(position).toString();
            Toast.makeText(this, genderType, Toast.LENGTH_SHORT).show();
            switch (genderType) {
                case Constant.MALE: {
                    binding.pregnantLayout.setVisibility(View.GONE);
                    genderSelected = true;
                    isFemale = false;
                    break;
                }
                case Constant.FEMALE: {
                    binding.pregnantLayout.setVisibility(View.VISIBLE);
                    genderSelected = true;
                    isFemale = true;
                }
            }
        });
    }

    private void showDatePickerDialogue(MaterialDatePicker dateMaterialPicker) {
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
            try {
                dateMaterialPicker.show(fragmentManager, "Material Date Picker>");

            } catch (Exception exception) {
                Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            fragmentManager = null;
        }

    }

    private void handleBreedMenu() {
        ArrayAdapter roleAdapter = new ArrayAdapter(this, R.layout.marketing_list, breedList);
        binding.selectBreed.setAdapter(roleAdapter);
        ((AutoCompleteTextView) binding.menuBreed.getEditText()).setOnItemClickListener((adapterView, view, position, l) -> {
            breedName = roleAdapter.getItem(position).toString();
        });
    }

    private void handleVaccinatedMenu() {
        ArrayAdapter roleAdapter = new ArrayAdapter(this, R.layout.marketing_list, vaccinatedList);
        binding.tvVaccinate.setAdapter(roleAdapter);
        ((AutoCompleteTextView) binding.tvVaccineLayout.getEditText()).setOnItemClickListener((adapterView, view, position, l) -> {
            isVaccinated = roleAdapter.getItem(position).toString();
        });
    }

    private void handlePregnantMenu() {
        ArrayAdapter roleAdapter = new ArrayAdapter(this, R.layout.marketing_list, pregnantList);
        binding.selectPregnant.setAdapter(roleAdapter);
        ((AutoCompleteTextView) binding.pregnantLayout.getEditText()).setOnItemClickListener((adapterView, view, position, l) -> {
            isPregnant = roleAdapter.getItem(position).toString();
        });
    }

    private void setupAnimalBreed() {
        animalType = getIntent().getStringExtra(Constant.GET_ROLE);
        switch (animalType) {
            case Constant.COW:
                breedList = new String[]{"Chollistani", "Lohani", "Red Sindhi", "Tharparkar"};
                break;
            case Constant.GOAT: {
                breedList = new String[]{"Beetal", "Barbari", "Kamori", "Nachi"};
                break;
            }
            case Constant.SHEEP: {
                breedList = new String[]{"Balkhi", "Baluchi", "Cholistani ", "Damani "};
                break;
            }
            case Constant.CAMEL:
                breedList = new String[]{"Dromedary", "Arabian", "Bactrian"};
                break;
            default:
                Toast.makeText(this, "NONE BREED", Toast.LENGTH_SHORT).show();
        }
    }
}