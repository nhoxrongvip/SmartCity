package ca.chesm.it.smartcity.models;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserTest
{
    private User user;

    @Before
    public void setUp() throws Exception
    {
        // executed before each test case
        user = new User();
        System.out.println("Rune with every test case");
    }

    @After
    public void shutdown() throws Exception
    {
        // executed after each test case
        System.out.println("Run at the end of each test case");
    }

    @Test
    public void Testgetfname1() throws Exception
    {
        //Expected: Danny, Set name with Danny.
        user.setFullname("Danny");
        assertEquals("Danny",user.getFullname());
    }

    @Test
    public void Testgetfname2() throws Exception
    {
        //Expected: Phat, Set name with Danny.
        user.setFullname("Danny");
        assertEquals("Phat",user.getFullname());
    }

    @Test
    public void getPhoneNo() throws Exception
    {
        //Expected: 6478189795, with 6478189795 PhoneNo.
        user.setPhoneNo("6478189795");
        assertEquals("6478189795",user.getPhoneNo());
    }

    @Test
    public void getPhoneNo2() throws Exception
    {
        //Expected: 6478189799, with 6478189795 PhoneNo.
        user.setPhoneNo("6478189799");
        assertEquals("6478189795",user.getPhoneNo());
    }

    @Test
    public void getPassword() throws Exception
    {
        assertNotNull(user);
    }

}