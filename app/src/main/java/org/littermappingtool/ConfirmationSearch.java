package org.littermappingtool;

import java.util.ArrayList;

/**
 * Created by rob on 29/07/15.
 */
public class ConfirmationSearch extends Search {

    private Action.Type mYesAction;
    private Action.Type mNoAction;
    private Search mParentSearch;
    private boolean mActive;
    private String mPotentialField;
    private String mPotentialOtherField;
    private LocalEntity mYesTerms;
    private LocalEntity mNoTerms;


    public ConfirmationSearch(String name, ArrayList<LocalEntity> searchObjects, String response) {
        super(name, searchObjects, response);
        mYesTerms = new LocalEntity("yes");
        mNoTerms = new LocalEntity("no");
    }

    public Action.Type getYesAction() {
        return mYesAction;
    }

    public void setYesAction(Action.Type mAction) {
        this.mYesAction = mAction;
    }

    public Action.Type getNoAction() {
        return mNoAction;
    }

    public void setNoAction(Action.Type mNoAction) {
        this.mNoAction = mNoAction;
    }

    public boolean isActive() {
        return mActive;
    }

    public void setActive(boolean mActive) {
        this.mActive = mActive;
    }

    public Search getParentSearch() {
        return mParentSearch;
    }

    public void setParentSearch(Search mParentSearch) {
        this.mParentSearch = mParentSearch;
    }

    public String getPotentialField() {
        return mPotentialField;
    }

    public void setPotentialField(String mResultinQuestion) {
        this.mPotentialField = mResultinQuestion;
    }

    public String getPotentialOtherField() {
        return mPotentialOtherField;
    }

    public void setPotentialOtherField(String mPotentialOtherField) {
        this.mPotentialOtherField = mPotentialOtherField;
    }

    public void setYesTerms(String[] yesTerms) {
        mYesTerms.addSearchTerms(yesTerms);
    }

    public void setNoTerms(String[] noTerms) {
        mNoTerms.addSearchTerms(noTerms);
    }

    public LocalEntity getYesTerms() {
        return mYesTerms;
    }

    public LocalEntity getNoTerms() {
        return mNoTerms;
    }
}
