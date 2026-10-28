package com.b24dccc117.quanlydanhba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.b24dccc117.quanlydanhba.adapter.ContactAdapter;
import com.b24dccc117.quanlydanhba.data.ContactRepository;
import com.b24dccc117.quanlydanhba.model.Contact;

public class MainActivity extends AppCompatActivity implements ContactAdapter.OnContactClickListener {

    public static final String EXTRA_CONTACT_ID = "EXTRA_CONTACT_ID";

    private TextView tvTotalContacts;
    private TextView tvFavoriteContacts;
    private RecyclerView rvContacts;
    private TextView tvEmptyList;
    private AppCompatButton btnAddContact;

    private ContactAdapter contactAdapter;
    private ContactRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = ContactRepository.getInstance();

        initViews();
        setupRecyclerView();
        setupListeners();
        updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void initViews() {
        tvTotalContacts = findViewById(R.id.tvTotalContacts);
        tvFavoriteContacts = findViewById(R.id.tvFavoriteContacts);
        rvContacts = findViewById(R.id.rvContacts);
        tvEmptyList = findViewById(R.id.tvEmptyList);
        btnAddContact = findViewById(R.id.btnAddContact);
    }

    private void setupRecyclerView() {
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        contactAdapter = new ContactAdapter(this, repository.getAllContacts(), this);
        rvContacts.setAdapter(contactAdapter);
    }

    private void setupListeners() {
        btnAddContact.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onContactClick(Contact contact, int position) {
        if (contact != null) {
            Intent intent = new Intent(MainActivity.this, ContactDetailActivity.class);
            intent.putExtra(EXTRA_CONTACT_ID, contact.getId());
            startActivity(intent);
        }
    }

    private void updateUI() {
        int total = repository.getTotalCount();
        int favorite = repository.getFavoriteCount();

        tvTotalContacts.setText(String.valueOf(total));
        tvFavoriteContacts.setText(String.valueOf(favorite));

        if (total == 0) {
            tvEmptyList.setVisibility(View.VISIBLE);
            rvContacts.setVisibility(View.GONE);
        } else {
            tvEmptyList.setVisibility(View.GONE);
            rvContacts.setVisibility(View.VISIBLE);
        }

        if (contactAdapter != null) {
            contactAdapter.updateData(repository.getAllContacts());
        }
    }
}
