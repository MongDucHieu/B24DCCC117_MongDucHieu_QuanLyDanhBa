package com.b24dccc117.quanlydanhba;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import com.b24dccc117.quanlydanhba.data.ContactRepository;
import com.b24dccc117.quanlydanhba.model.Contact;

public class ContactDetailActivity extends AppCompatActivity {

    private ImageView btnBackDetailTop;
    private TextView tvDetailAvatar;
    private TextView tvDetailName;
    private TextView tvDetailPhone;
    private TextView tvDetailGroup;
    private TextView tvDetailGender;
    private TextView tvDetailNotes;
    private CheckBox cbDetailFavorite;
    private AppCompatButton btnCall;
    private AppCompatButton btnBack;
    private AppCompatButton btnDelete;

    private ContactRepository repository;
    private Contact currentContact;
    private String contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        repository = ContactRepository.getInstance();

        contactId = getIntent().getStringExtra(MainActivity.EXTRA_CONTACT_ID);
        currentContact = repository.getContactById(contactId);

        if (currentContact == null) {
            Toast.makeText(this, "Không tìm thấy thông tin liên hệ", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
        displayContactData();
        setupListeners();
    }

    private void initViews() {
        btnBackDetailTop = findViewById(R.id.btnBackDetailTop);
        tvDetailAvatar = findViewById(R.id.tvDetailAvatar);
        tvDetailName = findViewById(R.id.tvDetailName);
        tvDetailPhone = findViewById(R.id.tvDetailPhone);
        tvDetailGroup = findViewById(R.id.tvDetailGroup);
        tvDetailGender = findViewById(R.id.tvDetailGender);
        tvDetailNotes = findViewById(R.id.tvDetailNotes);
        cbDetailFavorite = findViewById(R.id.cbDetailFavorite);
        btnCall = findViewById(R.id.btnCall);
        btnBack = findViewById(R.id.btnBack);
        btnDelete = findViewById(R.id.btnDelete);
    }

    private void displayContactData() {
        tvDetailAvatar.setText(currentContact.getInitialLetter());

        Drawable bgDrawable = ContextCompat.getDrawable(this, R.drawable.bg_avatar_circle);
        if (bgDrawable != null) {
            Drawable mutableBg = bgDrawable.mutate();
            int color = ContextCompat.getColor(this, currentContact.getAvatarColorResId());
            mutableBg.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            tvDetailAvatar.setBackground(mutableBg);
        }

        tvDetailName.setText(currentContact.getName());
        tvDetailPhone.setText(currentContact.getPhone());
        tvDetailGroup.setText(currentContact.getGroup());
        tvDetailGender.setText(currentContact.getGender());

        if (TextUtils.isEmpty(currentContact.getNotes())) {
            tvDetailNotes.setText(getString(R.string.no_notes));
        } else {
            tvDetailNotes.setText(currentContact.getNotes());
        }

        cbDetailFavorite.setChecked(currentContact.isFavorite());
    }

    private void setupListeners() {
        btnBackDetailTop.setOnClickListener(v -> finish());
        btnBack.setOnClickListener(v -> finish());

        cbDetailFavorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (currentContact != null) {
                currentContact.setFavorite(isChecked);
                repository.updateContact(currentContact);
                Toast.makeText(this, R.string.msg_favorite_updated, Toast.LENGTH_SHORT).show();
            }
        });

        btnCall.setOnClickListener(v -> {
            String phone = currentContact.getPhone();
            if (!TextUtils.isEmpty(phone)) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + phone));
                startActivity(dialIntent);
            }
        });

        btnDelete.setOnClickListener(v -> showDeleteConfirmationDialog());
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.confirm_delete_title)
                .setMessage(getString(R.string.confirm_delete_msg) + " (" + currentContact.getName() + ")")
                .setPositiveButton(R.string.action_confirm, (dialog, which) -> {
                    repository.deleteContact(currentContact.getId());
                    Toast.makeText(this, R.string.msg_contact_deleted, Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton(R.string.action_cancel, null)
                .show();
    }
}
