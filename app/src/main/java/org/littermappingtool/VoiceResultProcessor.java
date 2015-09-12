package org.littermappingtool;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.littermappingtool.backend.binApi.model.Bin;
import org.littermappingtool.backend.binTypeApi.BinTypeApi;
import org.littermappingtool.backend.binTypeApi.model.BinType;
import org.littermappingtool.backend.binTypeApi.model.CollectionResponseBinType;
import org.littermappingtool.backend.brandApi.BrandApi;
import org.littermappingtool.backend.brandApi.model.Brand;
import org.littermappingtool.backend.brandApi.model.CollectionResponseBrand;
import org.littermappingtool.backend.litterApi.model.Litter;
import org.littermappingtool.backend.litterTypeApi.LitterTypeApi;
import org.littermappingtool.backend.litterTypeApi.model.CollectionResponseLitterType;
import org.littermappingtool.backend.litterTypeApi.model.LitterType;



/**
 * Created by rob on 05/08/15.
 */
public class VoiceResultProcessor {

    private static final String TAG = VoiceResultProcessor.class.getName();

    private static VoiceResultProcessor sResultProcessor;

    private Context mContext;
    private ActionHandler mActionHandler;
    private Litter mLitter;
    private Bin mBin;
    private ArrayList<LocalEntity> mBrands;
    private ArrayList<LocalEntity> mLitterTypes;
    private ArrayList<LocalEntity> mBinTypes;
    private OutingManager mOutingManager;

    private Long mEventId;

    private Double mockLat = null;
    private Double mockLong = null;

    // Set up Search objects. These have set responses and expected results
    private Search menuSearch;
    private Search binSearch;
    private Search typeSearch;
    private Search brandSearch;
    private ConfirmationSearch yesNoSearch;

    private static final String[] YES_TERMS = {"ok", "ok then", "oh k", "okai", "ok I", "yes", "uh huh",
            "haha", "ha ha",
            "yess", "yeah", "ya", "yah", "yea", "yep fine", "fine", "fine fine", "yes please", "ok then", "sure",
            "sure thing", "please", "yeahh", "yep", "yepp", "okie dokie", "you got it", "whatever", "I did"};
    private static final String[] NO_TERMS = {"no", "no thanks", "no thankyou", "no thank you",
            "know", "no no", "Nano", "nope", "note", "notebook", "hope", "correction", "try again",
            "no way", "I don't think so", "nooope", "nah", "nah nah", "naaaah", "noop", "night",
            "I did not", "I didn't", "knife"};
    private static final String[] CONFIRMATION_TERMS = ArrayUtils.addAll(YES_TERMS, NO_TERMS);

    private VoiceResultProcessor(Context context) {

        mContext = context;

        // Bin types, litter types and brands all pulled from cloud
        mBrands = new ArrayList<>();
        mLitterTypes = new ArrayList<>();
        mBinTypes = new ArrayList<>();
        populateBrandList();
        populateLitterTypes();
        populateBinTypes();


        ArrayList<LocalEntity> menuCommands = new ArrayList<>();
        LocalEntity brand = new LocalEntity("brand");
        brand.setName(context.getString(R.string.brand_command));
        brand.addSearchTerms(new String[]{"friends", "ground", "run", "front", "round"});
        menuCommands.add(brand);
        LocalEntity litterType = new LocalEntity("type");
        litterType.setName(mContext.getString(R.string.type_command));
        litterType.addSearchTerms(new String[] {"typ", "types", "tight", "tired"});
        menuCommands.add(litterType);
        LocalEntity bin = new LocalEntity("bin");
        bin.addSearchTerms(new String[]{"pin", "binh", "spin", "Ben", "friend", "pen", "fin"});
        menuCommands.add(bin);

        ArrayList<LocalEntity> confirmation = new ArrayList<>();
        LocalEntity confirmationTerms = new LocalEntity("yes");
        confirmationTerms.addSearchTerms(CONFIRMATION_TERMS);
        confirmation.add(confirmationTerms);

        // Populate Search objects for use in logic and UI
        menuSearch = new Search("menu", menuCommands, mContext.getString(R.string.menu_caption));
        binSearch = new Search(mContext.getString(R.string.bin_command), mBinTypes, mContext.getString(R.string.bin_caption));
        typeSearch = new Search(mContext.getString(R.string.type_command), mLitterTypes, mContext.getString(R.string.type_caption));
        brandSearch = new Search(mContext.getString(R.string.brand_command), mBrands, mContext.getString(R.string.brand_caption));
        yesNoSearch = new ConfirmationSearch("yesno", confirmation, mContext.getString(R.string.yes_no_caption));

        yesNoSearch.setYesTerms(YES_TERMS);
        yesNoSearch.setNoTerms(NO_TERMS);

        mActionHandler = new ActionHandler();
        mOutingManager = OutingManager.get(context);

    }

    public static VoiceResultProcessor get (Context context, Long eventId) {
        if (sResultProcessor == null) {
            // Use application context to avoid leaking activities
            sResultProcessor = new VoiceResultProcessor(context.getApplicationContext());
        }
        sResultProcessor.setEventId(eventId);
        return sResultProcessor;
    }

    // populate brands list from cloud
    // This uses the Brand Entity endpoints class to populate a normal Java copy of the object
    public void populateBrandList() {
        new ListOfBrandsAsyncRetriever().execute();
    }

    // populate litter types from cloud
    public void populateLitterTypes() {
        new ListOfLitterTypesAsyncRetriever().execute();
    }

    // populate bin types from cloud
    public void populateBinTypes() {
        new ListOfBinTypesAsyncRetriever().execute();
    }


    public String processVoiceResults (ArrayList<String> matchedStrings) {
        Log.d(TAG, "Initial results = " + matchedStrings);
        // Default result is the most likely match of those returned
        String result = matchedStrings.get(0);
        boolean resultFound = false;
        for (int i=0; i<Search.getCurrentSearch().getSearchObjects().size() && !resultFound; i++) {
            LocalEntity currentSearchItem = Search.getCurrentSearch().getSearchObjects().get(i);
            for (int j=0; j<matchedStrings.size() && !resultFound; j++) {
                for(int k=0; k<currentSearchItem.getSearchTerms().size() && !resultFound; k++) {
//                    Log.d(TAG, "matched string = " + matchedStrings.get(j));
//                    Log.d(TAG, "search string = " + currentSearchItem.getSearchTerms().get(k));
                    // If magic algorithm finds a match
                    if (StringUtils.getLevenshteinDistance(matchedStrings.get(j),
                            currentSearchItem.getSearchTerms().get(k)) < (currentSearchItem.getSearchTerms().get(k).length() / 3)
                            || currentSearchItem.getSearchTerms().get(k).equals(matchedStrings.get(j))) {
                        resultFound = true;
                        if(Search.getCurrentSearch().getName().equals("yesno")) {
                            Log.d(TAG,"got here");
                            result = currentSearchItem.getSearchTerms().get(k);
                        }
                        else {
                            result = Search.getCurrentSearch().getSearchObjects().get(i).getName();
                        }
                        Log.d(TAG, "result = " + result);
                        // If result found then update next search based on match
                        processExpectedResult(result);
                        return null;
                    }
                }
            }
        }
        return processUnexpectedResult(result);
    }

    private void processExpectedResult(String result) {

        // If result is affirmative then assume it is in response to a confirmation question and
        // perform pending action associated with current search
        if (yesNoSearch.getYesTerms().getSearchTerms().contains(result)) {
            yesNoSearch.setActive(false);
            Log.d(TAG, "user answered yes");
            performAction(yesNoSearch.getYesAction(), result);
            return;
        }
        // If result is negative, assume it is in response to a confirmation as above
        if (yesNoSearch.getNoTerms().getSearchTerms().contains(result)) {
            yesNoSearch.setActive(false);
            Log.d(TAG, "user answered no");
            Log.d(TAG, "current no action = " + yesNoSearch.getNoAction());
            performAction(yesNoSearch.getNoAction(), result);
            return;
        }
        // Otherwise set next search, update UI and start listening for next input
        mActionHandler.setActionPerformed(false);
        setCurrentSearchByString(result);
        Log.d(TAG, "new current search before UI update = " + Search.getCurrentSearch().getName());
    }


    // Deal with unexpected results
    private String processUnexpectedResult(String result) {

        String toastText = "";

        // Cancel item and start again if needed
        if (result.equals("cancel") || result.equals("council")
                || result.equals("Council") || result.equals("cancer")
                || result.equals("pencil")) {
            mOutingManager.speak("item cancelled");
            setCurrentSearch(brandSearch, result);
            mBin = null;
            mLitter = null;
            return null;
        }

        for (LocalEntity menuItem : menuSearch.getSearchObjects()) {
            if (menuItem.getSearchTerms().contains(result)) {
                setCurrentSearchByString(menuItem.getName());
                return null;
            }
        }

        if (!Search.getCurrentSearch().getName().equals("menu")) {
            toastText = "I haven't heard of " + result + ". Should I log it as other?";
            // log current item as 'other' and record the result
            yesNoSearch.setActive(true);
            yesNoSearch.setResponse(toastText);
            yesNoSearch.setPotentialOtherField(result);

            if (Search.getCurrentSearch().equals(brandSearch)) {
                mActionHandler.getCurrentAction().setActionType(Action.Type.LOG_OTHER_BRAND);
            }
            if (Search.getCurrentSearch().equals(typeSearch)) {
                mActionHandler.getCurrentAction().setActionType(Action.Type.LOG_OTHER_LITTER_TYPE);
            }
            if (Search.getCurrentSearch().equals(binSearch)) {
                mActionHandler.getCurrentAction().setActionType(Action.Type.LOG_OTHER_BIN_TYPE);
            }

            Log.d(TAG, "result at other: " + result);
            Log.d(TAG, "yesnosearch potentialotherfield set to " + yesNoSearch.getPotentialOtherField());
            Log.d(TAG, "currentSearch at unexpected result = " + Search.getCurrentSearch().getName());

            setCurrentSearch(yesNoSearch, "");
            return toastText;
        }
        else {
            setCurrentSearch(brandSearch, result);
            return null;
        }
    }

    // Update search based on keyword found
    public void setCurrentSearchByString(String searchString) {

        Search newSearch;

        if (searchString.equals(binSearch.getName())) {
            newSearch = binSearch;
        }
        else if (searchString.equals(typeSearch.getName())) {
            newSearch = typeSearch;
        }
        else if (searchString.equals(brandSearch.getName())) {
            newSearch = brandSearch;
        }
        else if (!mActionHandler.isActionPerformed()) {
            newSearch = yesNoSearch;
        }
        else {
            newSearch = brandSearch;
        }
        setCurrentSearch(newSearch, searchString);
    }

    // Returns an Action type that allows logic to redirect user to the current search
    private Action.Type getActionFromSearch(Search search) {
        // Defaults to MENU_SEARCH home screen if nothing else found
        Action.Type currentSearchAction = Action.Type.MENU_SEARCH;
        if (search == brandSearch) {
            currentSearchAction = Action.Type.LITTER_BRAND_SEARCH;
        }
        if (search == typeSearch) {
            currentSearchAction = Action.Type.LITTER_TYPE_SEARCH;
        }
        if (search == binSearch) {
            currentSearchAction = Action.Type.BIN_SEARCH;
        }
        return currentSearchAction;
    }


    public void setCurrentSearch(Search newSearch, String result) {

        Action.Type actionType = null;
        yesNoSearch.setActive(true);
        yesNoSearch.setPotentialField(result);
        yesNoSearch.setResponse("Did you mean " + result + "?");
        // Set parent search for re-routing after user response
        if(Search.getCurrentSearch() != null && Search.getCurrentSearch() != yesNoSearch) {
            yesNoSearch.setParentSearch(Search.getCurrentSearch());
            Log.d(TAG, "parent search of yesNo search set to " + Search.getCurrentSearch().getName());
        }
        if (newSearch == binSearch) {
            actionType = Action.Type.LOG_BIN_ITEM;
        }
        else if (newSearch == typeSearch) {
            actionType = Action.Type.LOG_LITTER_TYPE;
        }
        else if (newSearch == brandSearch) {
            actionType = Action.Type.LOG_LITTER_BRAND;
        }
        else if (newSearch == yesNoSearch) {
            yesNoSearch.setYesAction(mActionHandler.getCurrentAction().getActionType());
            yesNoSearch.setNoAction(getActionFromSearch(yesNoSearch.getParentSearch()));
        }
        if (actionType != null) {
            mActionHandler.getCurrentAction().setActionType(actionType);
        }
        Search.setCurrentSearch(newSearch);
    }

    // process current active action
    public void performAction(Action.Type currentActionType, String result) {

        switch (currentActionType) {
            case LOG_LITTER_TYPE:
                populateLitterItem(yesNoSearch.getPotentialField(), null, "type");
                break;
            case LOG_OTHER_LITTER_TYPE:
                populateLitterItem("other", yesNoSearch.getPotentialOtherField(), "type");
                break;
            case LOG_LITTER_BRAND:
                populateLitterItem(yesNoSearch.getPotentialField(), null, "brand");
                break;
            case LOG_OTHER_BRAND:
                Log.d(TAG, "Logging other brand");
                populateLitterItem("other", yesNoSearch.getPotentialOtherField(), "brand");
                break;
            case LOG_BIN_ITEM:
                populateBinItem(yesNoSearch.getPotentialField(), null);
                logBin(mBin);
                setCurrentSearch(brandSearch, result);
                break;
            case LOG_OTHER_BIN_TYPE:
                populateBinItem("other", yesNoSearch.getPotentialOtherField());
                break;
            case LITTER_BRAND_SEARCH:
                setCurrentSearch(brandSearch, result);
                break;
            case LITTER_TYPE_SEARCH:
                setCurrentSearch(typeSearch, result);
                break;
            case BIN_SEARCH:
                setCurrentSearch(binSearch, result);
                break;
            case MENU_SEARCH:
                setCurrentSearch(menuSearch, result);
                break;
            default:
                Log.d(TAG, "invalid action type");
        }

        mActionHandler.setActionPerformed(true);
        Log.d(TAG, "action performed");
        Toast.makeText(mContext, "action performed", Toast.LENGTH_SHORT).show();
    }

    public void logLitterItem(Litter litterItem) {
        mOutingManager.logLitter(mLitter);
        mOutingManager.speak("litter logged");
        mLitter = null;
        yesNoSearch.setPotentialOtherField(null);
        setCurrentSearch(brandSearch, "");
    }

    public void logBin(Bin bin) {
        mOutingManager.logBin(bin);
        mOutingManager.speak("bin logged");
        mBin = null;
        yesNoSearch.setPotentialOtherField(null);
        setCurrentSearch(brandSearch, "");
    }

    public void checkForLitterType () {
        mActionHandler.getCurrentAction().setActionType(Action.Type.LITTER_TYPE_SEARCH);
        setCurrentSearch(yesNoSearch, "");
        yesNoSearch.setResponse(mContext.getString(R.string.confirm_log_litter_type_caption));
        yesNoSearch.setYesAction(Action.Type.LITTER_TYPE_SEARCH);
        // If an 'other' item is being processed, then set no action to Log other brand,
        // Otherwise log regular brand
        if (yesNoSearch.getPotentialOtherField() != null) {
            yesNoSearch.setNoAction(Action.Type.LOG_OTHER_BRAND);
        }
        else {
            yesNoSearch.setNoAction(Action.Type.LOG_LITTER_BRAND);
        }
    }

    public void checkForLitterBrand() {
        mActionHandler.getCurrentAction().setActionType(Action.Type.LITTER_BRAND_SEARCH);
        setCurrentSearch(yesNoSearch, "");
        yesNoSearch.setResponse(mContext.getString(R.string.confirm_log_litter_brand_caption));
        yesNoSearch.setYesAction(Action.Type.LITTER_BRAND_SEARCH);
        // If an 'other' item is being processed, then set no action to Log other type,
        // Otherwise log regular type
        if (yesNoSearch.getPotentialOtherField() != null) {
            yesNoSearch.setNoAction(Action.Type.LOG_OTHER_LITTER_TYPE);
        }
        else {
            yesNoSearch.setNoAction(Action.Type.LOG_LITTER_TYPE);
        }
    }

    public ConfirmationSearch getYesNoSearch() {
        return yesNoSearch;
    }

    public Search getMenuSearch() {
        return menuSearch;
    }

    public Search getBrandSearch() {
        return brandSearch;
    }

    public void populateLitterItem(String fieldValue, String otherFieldValue, String fieldType) {

        if (mLitter == null) {
            mLitter = new Litter();

            setPassiveLitterInformation();

            if (fieldType.equals("brand")) {
                mLitter.setBrand(fieldValue);
                mLitter.setOtherBrandName(otherFieldValue);
                checkForLitterType();
            }
            if (fieldType.equals("type")) {
                mLitter.setLitterType(fieldValue);
                mLitter.setOtherLitterType(otherFieldValue);
                checkForLitterBrand();
            }
        }
        else {
            if (fieldType.equals("brand")) {
                mLitter.setBrand(fieldValue);
                Log.d(TAG, "otherFieldBrand: " + otherFieldValue);
                mLitter.setOtherBrandName(otherFieldValue);
            }
            if (fieldType.equals("type")) {
                mLitter.setLitterType(fieldValue);
                mLitter.setOtherLitterType(otherFieldValue);
            }
            Log.d(TAG, "litter other brand: " + mLitter.getOtherBrandName());
            logLitterItem(mLitter);
        }
    }

    private void setPassiveLitterInformation() {
        try {
            mLitter.setUserEmail(mOutingManager.getGmailAccount());
            mLitter.setCreated(new Date().getTime());
            mLitter.setLatitude(mockLat);
            mLitter.setLongitude(mockLong);
            if (mOutingManager.getLocation() != null) {
                mLitter.setLatitude(mOutingManager.getLocation().getLatitude());
                mLitter.setLongitude(mOutingManager.getLocation().getLongitude());
            }
            mLitter.setEventId(mEventId);
        }
        catch (Exception e) {
            Log.w(TAG, e);
        }
    }

    public void populateBinItem(String type, String otherType) {
        if (mBin == null) {
            mBin = new Bin();
        }
        setPassiveBinInformation();
        mBin.setBinType(type);
        mBin.setOtherBinType(otherType);
        logBin(mBin);
    }

    private void setPassiveBinInformation() {
        try {
            mBin.setUserEmail(mOutingManager.getGmailAccount());
            mBin.setCreated(new Date().getTime());
            mBin.setLatitude(mockLat);
            mBin.setLongitude(mockLong);
            if (mOutingManager.getLocation() != null) {
                mBin.setLatitude(mOutingManager.getLocation().getLatitude());
                mBin.setLongitude(mOutingManager.getLocation().getLongitude());
            }
        }
        catch (Exception e) {
            Log.w(TAG, e);
        }
    }

    public Long getEventId() {
        return mEventId;
    }

    public void setEventId(Long mEventId) {
        this.mEventId = mEventId;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * AsyncTask for retrieving the list of litters and updating the
     * corresponding results list.
     */
    private class ListOfBrandsAsyncRetriever extends AsyncTask<Void, Void, CollectionResponseBrand> {

        @Override
        protected CollectionResponseBrand doInBackground(Void... params) {

            BrandApi.Builder endpointBuilder = new BrandApi.Builder(AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
            endpointBuilder = CloudEndpointUtils.updateBuilder(endpointBuilder);
            BrandApi brandApiService = endpointBuilder.build();

            try {
                // Number of brands limited to 1000 possible. Edit this value to increase/decrease
                return brandApiService.list().setLimit(1000).execute();
            } catch (IOException e) {
                Log.w(TAG, e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(CollectionResponseBrand collectionResponseBrand) {
            createBrandList(collectionResponseBrand.getItems());
            super.onPostExecute(collectionResponseBrand);
        }

        private void createBrandList(List<Brand> brandEntities) {
            if (brandEntities != null && brandEntities.size() > 0) {
                for (Brand brandEntity : brandEntities) {
                    LocalEntity brand = new LocalEntity(brandEntity.getName());
                    if(brandEntity.getSearchTerms() != null) {
                        brand.setSearchTerms((ArrayList<String>) brandEntity.getSearchTerms());
                    }
                    mBrands.add(brand);
                    Log.d(TAG, brand.getName());
                }
            }
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * AsyncTask for retrieving the list of litter types and updating the
     * corresponding results list.
     */
    private class ListOfLitterTypesAsyncRetriever extends AsyncTask<Void, Void, CollectionResponseLitterType> {


        @Override
        protected CollectionResponseLitterType doInBackground(Void... params) {

            LitterTypeApi.Builder endpointBuilder = new LitterTypeApi.Builder(AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
            endpointBuilder = CloudEndpointUtils.updateBuilder(endpointBuilder);
            LitterTypeApi litterTypeApiService = endpointBuilder.build();

            try {
                return litterTypeApiService.list().execute();
            } catch (IOException e) {
                Log.w(TAG, e);
                return null;
            }
        }


        @Override
        protected void onPostExecute(CollectionResponseLitterType collectionResponseLitterType) {
            createLitterTypeList(collectionResponseLitterType.getItems());
            super.onPostExecute(collectionResponseLitterType);
        }

        private void createLitterTypeList(List<LitterType> litterTypeEntities) {
            if (litterTypeEntities != null && litterTypeEntities.size() > 0) {
                for (LitterType litterTypeEntity : litterTypeEntities) {
                    LocalEntity litterType = new LocalEntity(litterTypeEntity.getName());
                    if(litterTypeEntity.getSearchTerms() != null) {
                        litterType.setSearchTerms((ArrayList<String>) litterTypeEntity.getSearchTerms());
                    }
                    mLitterTypes.add(litterType);
                    Log.d(TAG, litterType.getName());
                }
            }
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * AsyncTask for retrieving the list of bin types and updating the
     * corresponding results list.
     */
    private class ListOfBinTypesAsyncRetriever extends AsyncTask<Void, Void, CollectionResponseBinType> {


        @Override
        protected CollectionResponseBinType doInBackground(Void... params) {

            BinTypeApi.Builder endpointBuilder = new BinTypeApi.Builder(AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
            endpointBuilder = CloudEndpointUtils.updateBuilder(endpointBuilder);
            BinTypeApi BinTypeApiService = endpointBuilder.build();

            try {
                return BinTypeApiService.list().execute();
            } catch (IOException e) {
                Log.w(TAG, e);
                return null;
            }
        }


        @Override
        protected void onPostExecute(CollectionResponseBinType collectionResponseBinType) {
            createBinTypeList(collectionResponseBinType.getItems());
            super.onPostExecute(collectionResponseBinType);
        }

        private void createBinTypeList(List<BinType> binTypeEntities) {
            if (binTypeEntities != null && binTypeEntities.size() > 0) {
                for (BinType binTypeEntity : binTypeEntities) {
                    LocalEntity binType = new LocalEntity(binTypeEntity.getName());
                    if(binTypeEntity.getSearchTerms() != null) {
                        binType.setSearchTerms((ArrayList<String>) binTypeEntity.getSearchTerms());
                    }
                    mBinTypes.add(binType);
                    Log.d(TAG, binType.getName());
                }
            }
        }
    }
}
