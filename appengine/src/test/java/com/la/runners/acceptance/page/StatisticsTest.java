package com.la.runners.acceptance.page;

import org.junit.Test;

import com.la.runners.acceptance.Constants;
import com.la.runners.acceptance.UserWithProfileSetPageTest;

public class StatisticsTest extends UserWithProfileSetPageTest {

    public StatisticsTest() {
        super(Constants.Jsp.statistics);
    }
    
    @Test
    public void montlyStatistics() {
        //insert couple of runs
        //go to statistics and check out the data that is retrieved
    }

}
