package org.littermappingtool;

import java.util.ArrayList;



/**
 * Created by rob on 07/07/15.
 */
public class Search {

    private String mName;
    private String mResponse;
    private static Search mCurrentSearch;
    private ArrayList<LocalEntity> mSearchObjects;
    private ArrayList<String> mCategories;

    public Search(String name, ArrayList<LocalEntity> searchObjects, String response) {
        mName = name;
        mSearchObjects = searchObjects;
        mResponse = response;
    }


    public String getResponse() {
        return mResponse;
    }

    public void setResponse(String response) {
        this.mResponse = response;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setSearchObjects(ArrayList<LocalEntity> searchObject) {
        this.mSearchObjects = searchObject;
    }

    public ArrayList<LocalEntity> getSearchObjects() {
        return mSearchObjects;
    }

    public static Search getCurrentSearch() {
        return mCurrentSearch;
    }

    public static void setCurrentSearch(Search mCurrentSearch) {
        Search.mCurrentSearch = mCurrentSearch;
    }

    public ArrayList<String> getCategories() {
        return mCategories;
    }

    public void setCategories(ArrayList<String> mCategories) {
        this.mCategories = mCategories;
    }

    public void addCategry(String category) {
        mCategories.add(category);
    }
}