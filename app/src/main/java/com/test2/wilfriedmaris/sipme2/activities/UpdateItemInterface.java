package com.test2.wilfriedmaris.sipme2.activities;

public interface UpdateItemInterface {
    String RATING_KEY = "rating";
    String ID_KEY = "id";
    int RESULT_CODE = 0;

    void updateItem(float rating, int cocktailId);
}
