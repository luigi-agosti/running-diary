package com.la.runners.acceptance.page;

import org.junit.Test;

import com.la.runners.acceptance.Constants;
import com.la.runners.acceptance.UserWithProfileSetPageTest;
import com.la.runners.acceptance.framework.AssertThatPageHas;
import com.la.runners.acceptance.framework.ChromeSession;
import com.la.runners.acceptance.framework.Session;
import com.la.runners.client.widget.dialog.DeleteProfileDialog;
import com.la.runners.client.widget.form.ProfileForm;
import com.la.runners.client.widget.grid.FollowersGrid;

public class ProfileTest extends UserWithProfileSetPageTest {
    
    public ProfileTest() {
        super(Constants.Jsp.profile);
    }
    
    @Override
    protected String[] getIds() {
        return new String[] {ProfileForm.ID, FollowersGrid.ID};
    }
    
    @Test
    public void userShouldBeAbleToSetOrChangeNickname() {
        Method.setProfileNickname(session, "luigi");
    }
    
    @Test
    public void changeSystemOfUnit() {
        Method.setImperialSystem(session);
    }
    
    @Test
    public void changeCheckboxSettings() {
        Method.setProfileOptions(session);
    }
    
    @Test
    public void deleteProfile() {
        Method.deleteProfile(session);
    }
    
    public static class Method {
        
        public static final void setProfileNickname(Session session, String nickname) {
            session.setValueOnInputById(gwt(ProfileForm.NICKNAME_ID), nickname);
            save(session);
        }
        
        public static final void setProfileOptions(Session session) {
            session.clickById(gwt(ProfileForm.WEATHER_ID));
            save(session);
        }
        
        public static final void deleteProfile(Session session) {
            session.clickById(gwt(ProfileForm.DELETE_PROFILE_ID));
            AssertThatPageHas.elementWithId(session, gwt(DeleteProfileDialog.CONTINUE_ID));
            session.clickById(gwt(DeleteProfileDialog.CONTINUE_ID));
        }
        
        public static final void setImperialSystem(Session session) {
            session.dropdownSelectByDebugId(ProfileForm.UNIT_SYSTEM_ID);
            save(session);
        }
        
        private static final void save(Session session) {
            session.clickById(gwt(ProfileForm.SAVE_PROFILE_ID));
            session.findDivElementByText(getString(PROFILE_FORM_SAVE_SUCCESS));
        }

    }

}
