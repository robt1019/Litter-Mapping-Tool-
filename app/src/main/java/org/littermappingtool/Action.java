package org.littermappingtool;

/**
 * Created by rob on 13/07/15.
 */
public class Action {

    public enum Type {
        LOG_LITTER_TYPE,
        LOG_LITTER_BRAND,
        LOG_BIN_ITEM,
        LITTER_TYPE_SEARCH,
        LITTER_BRAND_SEARCH,
        MENU_SEARCH,
        LOG_OTHER_LITTER_TYPE,
        LOG_OTHER_BRAND,
        LOG_OTHER_BIN_TYPE,
        BIN_SEARCH
    }

    private Type mType;

    public void setActionType(Type actionType) {
        mType = actionType;
    }

    public Type getActionType() {
        return mType;
    }
}
