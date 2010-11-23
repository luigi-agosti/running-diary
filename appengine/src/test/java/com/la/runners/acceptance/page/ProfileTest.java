package com.la.runners.acceptance.page;

import org.junit.Test;

import com.la.runners.acceptance.Constants;
import com.la.runners.acceptance.UserWithProfileSetPageTest;

public class ProfileTest extends UserWithProfileSetPageTest {

    public ProfileTest() {
        super(Constants.Jsp.profile);
    }
    
    @Override
    protected String[] getIds() {
        return new String[] {};
    }
    
    @Test
    public void changeSystemOfUnit() {
        
    }
    
    @Test
    public void changeCheckboxSettings() {
        
    }
    
    @Test
    public void deleteProfile() {
        
    }

}
