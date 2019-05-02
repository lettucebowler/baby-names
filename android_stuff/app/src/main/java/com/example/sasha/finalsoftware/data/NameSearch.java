package com.example.sasha.finalsoftware.data;

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

    public void getFromFirebase(TextView tv) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> list = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Iterable<DataSnapshot> userId = ds.getChildren();
                    for (DataSnapshot uId : userId) {
                        tv.append((CharSequence) uId.getValue());
                        return;
                    }
                }
                Log.w("TAG", String.valueOf(list));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        rootRef.addListenerForSingleValueEvent(eventListener);
    }

}
