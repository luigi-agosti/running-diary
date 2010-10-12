package com.la.runners.server.dao.jdo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.la.runners.shared.Profile;

public class JdoProfileDaoTest {
    
    private LocalServiceTestHelper helper = new LocalServiceTestHelper(
            new LocalDatastoreServiceTestConfig());
    
    protected DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
    
    @Before
    public void setUp() {
        helper.setUp();
    }
    
    @After
    public void tearDown() {
        helper.tearDown();
    }
    
    private JdoProfileDao profileDao = new JdoProfileDao();
    
    @Test
    public void shouldGetFollowers() {
        Profile p1 = new Profile("1", "arturo");
        p1.setFollowers(Arrays.asList("2"));
        profileDao.save(p1);
        
        Profile p2 = new Profile("2", "archimede");
        p2.setFollowers(Arrays.asList("1"));
        profileDao.save(p2);
        
        List<Profile> fs = profileDao.getFollowers("2");
        assertNotNull(fs);
        assertEquals(1, fs.size());
        assertEquals("arturo", fs.get(0).getNickname());
    }
    
}

