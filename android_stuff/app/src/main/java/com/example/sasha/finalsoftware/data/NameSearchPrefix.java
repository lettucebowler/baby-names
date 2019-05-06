package com.example.sasha.finalsoftware.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class NameSearchPrefix extends NameSearch {

    private static List<Name> searchList = new ArrayList<>();
    private static FirebaseDatabase db = FirebaseDatabase.getInstance();
    private static DatabaseReference mDatabase = db.getReference();

    public static List<Name> prefixSearch(String prefix) {
        searchList.clear();
        mDatabase.orderByChild("name").startAt(prefix).endAt(prefix + "z")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                        Name tempName = dataSnapshot.getValue(Name.class);
                        System.out.println(tempName.getName() + "\n");
                        searchList.add(tempName);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        return searchList;
    }
}
