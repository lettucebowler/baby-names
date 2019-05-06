package com.example.sasha.finalsoftware.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.*;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class NameSearch {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    public Context context;
    public List nameList;
    public Name myName;

    public NameSearch(Context context) {
        this.context = context;
        nameList = new ArrayList<Name>();
        myName = new Name();
    }

    public void getFromFirebase(String name, String yearStart, String yearEnd) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Iterable<DataSnapshot> userId = ds.getChildren();
                    String temp;
                    temp = ds.child("name").getValue(String.class);
                    if (temp.equals(name)) {
                        Log.w("temp is " + temp, "should be " + ds.child("name").getValue(String.class));
                    }
                        //Log.w("NAME IS(should be John)", ds.child("name").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        rootRef.addListenerForSingleValueEvent(eventListener);
    }

}
