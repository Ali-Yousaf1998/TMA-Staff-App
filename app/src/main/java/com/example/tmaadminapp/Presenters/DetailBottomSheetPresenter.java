package com.example.tmaadminapp.Presenters;

import com.google.firebase.database.DatabaseReference;

public interface DetailBottomSheetPresenter
{
    void getWorkerDetails(DatabaseReference dbRef , String pushKey);

}
