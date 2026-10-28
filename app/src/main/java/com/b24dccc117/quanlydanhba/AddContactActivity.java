package com.b24dccc117.quanlydanhba;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.b24dccc117.quanlydanhba.data.ContactRepository;
import com.b24dccc117.quanlydanhba.model.Contact;

public class AddContactActivity extends AppCompatActivity {

    private ImageView btnBackAdd;
    private EditText edtAddName;
    private EditText edtAddPhone;
    private Spinner spnAddGroup;
    private RadioGroup rgAddGender;
    private RadioButton rbMale;
    private RadioButton rbFemale;
    private RadioButton rbOther;
    private CheckBox cbAddFavorite;
    private EditText edtAddNotes;
    private AppCompatButton btnAddSave;

    private ContactRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        repository = ContactRepository.getInstance();

        initViews();
        setupSpinner();
        setupListeners();
    }

    private void initViews() {
        btnBackAdd = findViewById(R.id.btnBackAdd);
        edtAddName = findViewById(R.id.edtAddName);
        edtAddPhone = findViewById(R.id.edtAddPhone);
        spnAddGroup = findViewById(R.id.spnAddGroup);
        rgAddGender = findViewById(R.id.rgAddGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        rbOther = findViewById(R.id.rbOther);
        cbAddFavorite = findViewById(R.id.cbAddFavorite);
        edtAddNotes = findViewById(R.id.edtAddNotes);
        btnAddSave = findViewById(R.id.btnAddSave);
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.contact_groups,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAddGroup.setAdapter(adapter);
    }

    private void setupListeners() {
        btnBackAdd.setOnClickListener(v -> finish());
        btnAddSave.setOnClickListener(v -> handleSaveContact());
    }

    private void handleSaveContact() {
        String name = edtAddName.getText().toString().trim();
        String phone = edtAddPhone.getText().toString().trim();
        String group = spnAddGroup.getSelectedItem() != null ? spnAddGroup.getSelectedItem().toString() : "Khác";

        String gender = "Nam";
        int selectedGenderId = rgAddGender.getCheckedRadioButtonId();
        if (selectedGenderId == R.id.rbFemale) {
            gender = "Nữ";
        } else if (selectedGenderId == R.id.rbOther) {
            gender = "Khác";
        }

        boolean isFavorite = cbAddFavorite.isChecked();
        String notes = edtAddNotes.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            edtAddName.setError(getString(R.string.err_empty_name));
            edtAddName.requestFocus();
            return;
        }

        if (!isValidPhoneNumber(phone)) {
            edtAddPhone.setError(getString(R.string.err_invalid_phone));
            edtAddPhone.requestFocus();
            return;
        }

        if (repository.isPhoneExist(phone, null)) {
            edtAddPhone.setError(getString(R.string.err_duplicate_phone));
            edtAddPhone.requestFocus();
            return;
        }

        Contact newContact = new Contact(name, phone, group, gender, isFavorite, notes);
        repository.addContact(newContact);

        Toast.makeText(this, R.string.msg_contact_added, Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean isValidPhoneNumber(String phone) {
        if (TextUtils.isEmpty(phone)) return false;
        return phone.matches("^0[0-9]{9}$");
    }
}
