package com.b24dccc117.quanlydanhba.data;

import com.b24dccc117.quanlydanhba.model.Contact;
import java.util.ArrayList;

public class ContactRepository {
    private static ContactRepository instance;
    private final ArrayList<Contact> contactList;

    private ContactRepository() {
        contactList = new ArrayList<>();
        initSampleData();
    }

    public static synchronized ContactRepository getInstance() {
        if (instance == null) {
            instance = new ContactRepository();
        }
        return instance;
    }

    private void initSampleData() {
        contactList.add(new Contact(
                "Nguyễn Văn An",
                "0901234567",
                "Bạn bè",
                "Nam",
                false,
                ""
        ));

        contactList.add(new Contact(
                "Trần Thị Bình",
                "0912345678",
                "Gia đình",
                "Nữ",
                true,
                "Chị gái, hay gọi buổi tối"
        ));

        contactList.add(new Contact(
                "Lê Minh Châu",
                "0987654321",
                "Công việc",
                "Nam",
                false,
                ""
        ));
    }

    public ArrayList<Contact> getAllContacts() {
        return contactList;
    }

    public Contact getContactById(String id) {
        if (id == null) return null;
        for (Contact c : contactList) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public void addContact(Contact contact) {
        if (contact != null) {
            contactList.add(contact);
        }
    }

    public void updateContact(Contact updatedContact) {
        if (updatedContact == null) return;
        for (int i = 0; i < contactList.size(); i++) {
            if (contactList.get(i).getId().equals(updatedContact.getId())) {
                contactList.set(i, updatedContact);
                return;
            }
        }
    }

    public boolean deleteContact(String id) {
        if (id == null) return false;
        for (int i = 0; i < contactList.size(); i++) {
            if (contactList.get(i).getId().equals(id)) {
                contactList.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean isPhoneExist(String phone, String excludeId) {
        if (phone == null) return false;
        String cleanPhone = phone.replaceAll("\\s+", "");
        for (Contact c : contactList) {
            if (excludeId != null && c.getId().equals(excludeId)) {
                continue;
            }
            if (c.getPhone().replaceAll("\\s+", "").equals(cleanPhone)) {
                return true;
            }
        }
        return false;
    }

    public int getTotalCount() {
        return contactList.size();
    }

    public int getFavoriteCount() {
        int count = 0;
        for (Contact c : contactList) {
            if (c.isFavorite()) {
                count++;
            }
        }
        return count;
    }
}
