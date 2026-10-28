package com.b24dccc117.quanlydanhba.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.b24dccc117.quanlydanhba.R;
import com.b24dccc117.quanlydanhba.model.Contact;
import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    public interface OnContactClickListener {
        void onContactClick(Contact contact, int position);
    }

    private final Context context;
    private ArrayList<Contact> contactList;
    private final OnContactClickListener listener;

    public ContactAdapter(Context context, ArrayList<Contact> contactList, OnContactClickListener listener) {
        this.context = context;
        this.contactList = contactList;
        this.listener = listener;
    }

    public void updateData(ArrayList<Contact> newList) {
        this.contactList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contactList.get(position);

        holder.tvAvatar.setText(contact.getInitialLetter());

        Drawable bgDrawable = ContextCompat.getDrawable(context, R.drawable.bg_avatar_circle);
        if (bgDrawable != null) {
            Drawable mutableBg = bgDrawable.mutate();
            int color = ContextCompat.getColor(context, contact.getAvatarColorResId());
            mutableBg.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            holder.tvAvatar.setBackground(mutableBg);
        }

        holder.tvName.setText(contact.getName());
        holder.tvPhone.setText(contact.getPhone());
        holder.tvGroup.setText(contact.getGroup());

        if (contact.isFavorite()) {
            holder.ivFavorite.setVisibility(View.VISIBLE);
        } else {
            holder.ivFavorite.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onContactClick(contact, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList != null ? contactList.size() : 0;
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView tvAvatar;
        TextView tvName;
        TextView tvPhone;
        TextView tvGroup;
        ImageView ivFavorite;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAvatar = itemView.findViewById(R.id.tvAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvGroup = itemView.findViewById(R.id.tvGroup);
            ivFavorite = itemView.findViewById(R.id.ivFavorite);
        }
    }
}
