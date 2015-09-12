package org.littermappingtool;

/**
 * Created by rob on 05/08/15.
 */
public class ActionHandler {

    private boolean mActionPerformed = false;
    // Current Action object is used to keep track of any pending operations
    private Action mCurrentAction;

    public ActionHandler() {
        mCurrentAction = new Action();
    }

    public Action getCurrentAction() {
        return mCurrentAction;
    }

    public void setCurrentAction(Action mCurrentAction) {
        this.mCurrentAction = mCurrentAction;
    }

    public boolean isActionPerformed() {
        return mActionPerformed;
    }

    public void setActionPerformed(boolean actionPerformed) {
        this.mActionPerformed = actionPerformed;
    }

}
