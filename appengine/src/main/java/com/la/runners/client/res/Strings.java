package com.la.runners.client.res;

import com.google.gwt.i18n.client.Constants;

public interface Strings extends Constants {
    
    /*
     * Other constants 
     */
    String googleMapsKeyOnAppSpot();
    String googleMapsKeyOnSocialRunnersDomain();
    String googleMapsVersion();
    
    /*
     * Search Form
     */
    String searchFormTitle();
    String searchFormNickname();
    String searchFormSearchButton();
    String searchFormSendInviteSubtitle();
    String searchFormEmailLabel();
    String searchFormSendInviteButton();
    String searchFormPersonalMessage();
    String searchFormInviteSuccess();
    String searchFormInviteFailure();
    
    /*
     * Profile Form
     */
    @DefaultStringValue("Profile Editor")
    String profileFormTitle();
    String profileFormNickname();
    String profileFormDeleteAccountButton();
    String profileFormSubtitle();
    String profileFormSubtitlePrivacy();
    String profileFormDeleteProfileSuccess();
    String profileFormDeleteProfileFailure();
    String profileFormLoadingProfileFailure();
    String profileFormSaveButton();
    String profileFormSaveSuccess();
    String profileFormSaveFailure();
    String profileFormInputLabelHeartRate();
    String profileFormInputLabelWeight();
    String profileFormInputLabelWeather();
    String profileFormInputLabelShoes();
    
    String profileFormUnitSystem();
    String profileFormInternationalSystem();
    String profileFormImperialSystem();
    String profileFormUSSystem();
    
    /*
     * Dialog
     */
    String dialogCloseButton();
    String dialogCancelButton();
    String dialogSaveButton();
    String dialogContinueButton();
    String dialogDeleteProfileInfo();
    String dialogNewProfileInfo();
    
    /*
     * Validation messages
     */
    String validationMandatoryField();
    String validationNumericField();
   
    /*
     * Run form
     */
    String runFormTitle();
    String runFormDate();
    String runFormStart();
    String runFormDistance();
    String runFormTime();
    String runFormOptional();
    String runFormHeartRate();
    String runFormWeight();
    String runFormShoes();
    String runFormNote();
    String runFormShare();
    String runFormSave();
    String runFormCancel();
    String runFormEditMap();
    String runFormSuccess();
    String runFormFailure();
    String runFormLocationFailure();
    String runFormLocationSuccess();
    
    /*
     * Run Grid
     */    
    String runGridLoading();
    String runGridDone();
    String runGridProblem();
    String runGridNoResult();
    String runGridDate();
    String runGridSelectionMissing();
    String runGridSelect();
    String runGridDistance();
    String runGridTime();
    String runGridSpeed();
    String runGridHeartRate();
    String runGridWeight();
    String runGridShoes();
    String runGridNote();
    String runGridMap();
    String runGridClone();
    String runGridEdit();
    
    /*
     * Units of measure
     */
    String speedInternational();
    String distanceInternational();
    String speedImperial();
    String distanceImperial();
    
    /*
     * Charts
     */
    String chartDistance();
    String chartDailyDistanceTitle();
}
