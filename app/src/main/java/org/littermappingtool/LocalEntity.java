package org.littermappingtool;

import java.util.ArrayList;

/**
 * Created by rob on 30/07/15.
 */
public class LocalEntity {

    private String mName;

    // Search terms are used to add in other acceptable matches for search.
    // For example name could be 'Creme Egg' with additional search term 'Cream Egg'
    private ArrayList<String> mSearchTerms;

    // Constructor populates first position in searchTerms with entity name;
    LocalEntity(String name) {
        this.mName = name;
        mSearchTerms = new ArrayList<>();
        ArrayList<String> mCategories = new ArrayList<>();
        mSearchTerms.add(name);
    }

    public void addSearchTerms(String[] searchTerms){
        for (String searchTerm : searchTerms) {
            mSearchTerms.add(searchTerm);
        }
    }

    public ArrayList<String> getSearchTerms() {
        return mSearchTerms;
    }

    public void setSearchTerms(ArrayList<String> searchTerms) {
        this.mSearchTerms = searchTerms;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

}
