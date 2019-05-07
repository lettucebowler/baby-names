package com.example.sasha.finalsoftware.data;

import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class NameSearch {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public void getFromFirebase() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> list = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Iterable<DataSnapshot> userId = ds.getChildren();
                    for (DataSnapshot uId : userId) {
                        String data = uId.toString();
                        list.add(data);
                        //Log.w("TAG", data);

                    }
                }
                //Log.w("TAG", String.valueOf(list));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        rootRef.addListenerForSingleValueEvent(eventListener);
    }

}
