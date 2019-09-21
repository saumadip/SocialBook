package com.hsbc.sb.memory;


import com.hsbc.sb.core.User;
import com.hsbc.sb.exception.ApplicationRuntimeException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Deque;
import java.util.Map;

public class SocialBookInMemoryServiceTest {

    SocialBookInMemoryService socialBookInMemoryService;

    @Before
    public void setup()
    {
        socialBookInMemoryService = new SocialBookInMemoryService();
    }

    @Test
    public void shouldCreateAndGetNewUser() {
        long idmy = socialBookInMemoryService.create("Saumadip");
        socialBookInMemoryService.create("Romi");
        Assert.assertEquals("Saumadip",socialBookInMemoryService.getUser(idmy).getName());
    }

    @Test
    public void shouldPostNewMessage_displayInReverseChronologicalOrder() {
        long idmy = socialBookInMemoryService.create("Saumadip");

        socialBookInMemoryService.post(idmy,"Test1");
        socialBookInMemoryService.post(idmy,"Test2");

        User user = socialBookInMemoryService.getUser(idmy);

        Deque<String> messages = user.displayListOfMessages();

        Assert.assertEquals("Test2",messages.getFirst());
        Assert.assertEquals("Test1",messages.getLast());
    }

    @Test(expected = ApplicationRuntimeException.class)
    public void shouldNotPostNewMessageGreaterThan_140Chars() {
        long idmy = socialBookInMemoryService.create("Saumadip");

        socialBookInMemoryService.post(idmy, "1N2IsWyai2ilCB4pHWCjtUaHbnaweofN6W" +
                "T3zkWHjHKC3eIBrOqaD7OobSGabJ0ua4tnfObrqMCZCb" +
                "QRPDBChhXQ9pdglUtVSEyfqCDSNeLTQaEUME4qkczLoXyogC53Dx9Qb" +
                "xs5iEbSn");
    }

    @Test
    public void shouldNotPostNewMessageLessThanOrEqual_140Chars() {
        long idmy = socialBookInMemoryService.create("Saumadip");

        try {
            socialBookInMemoryService.post(idmy, "1N2IsWyai2ilCB4pHWCjtUaHbnaweofN6W" +
                    "T3zkWHjHKC3eIBrOqaD7OobSGabJ0ua4tnfObrqMCZCb" +
                    "QRPDBChhXQ9pdglUtVSEyfqCDSNeLTQaEUME4qkczLoXyogC53Dx9Qb" +
                    "xs5iEbS");
        }
        catch (Exception e)
        {
            Assert.fail();
        }
    }

    @Test
    public void shouldBeAbleToFollowUser() {
        long idmy = socialBookInMemoryService.create("Saumadip");
        long idFriend1 = socialBookInMemoryService.create("Romi");
        long idFriend2 = socialBookInMemoryService.create("Ron");
        long idFriend3 = socialBookInMemoryService.create("Harry");

        socialBookInMemoryService.followUser(idmy,idFriend1);
        socialBookInMemoryService.followUser(idmy,idFriend2);

        Map<String, Deque<String>> stringDequeMap = socialBookInMemoryService.viewTimeLine(idmy);

        Assert.assertTrue(stringDequeMap.containsKey("Romi"));
        Assert.assertTrue(stringDequeMap.containsKey("Ron"));
        Assert.assertFalse(stringDequeMap.containsKey("Harry"));

    }

    @Test(expected = ApplicationRuntimeException.class)
    public void shouldNotFollowSelf() {
        long idmy = socialBookInMemoryService.create("Saumadip");

        socialBookInMemoryService.followUser(idmy,idmy);

    }

    @Test
    public void shouldBeAbleToViewTimeLineOfFollowedUser() {
        long idmy = socialBookInMemoryService.create("Saumadip");
        long idFriend1 = socialBookInMemoryService.create("Romi");
        long idFriend2 = socialBookInMemoryService.create("Ron");

        socialBookInMemoryService.post(idmy,"Test2");
        socialBookInMemoryService.post(idmy,"Test3");

        socialBookInMemoryService.post(idFriend1,"Test4");
        socialBookInMemoryService.post(idFriend1,"Test5");

        socialBookInMemoryService.post(idFriend2,"Test6");
        socialBookInMemoryService.post(idFriend2,"Test7");

        socialBookInMemoryService.followUser(idmy,idFriend1);
        socialBookInMemoryService.followUser(idmy,idFriend2);

        Map<String, Deque<String>> stringDequeMap = socialBookInMemoryService.viewTimeLine(idmy);

        Deque<String> romi = stringDequeMap.get("Romi");
        Assert.assertEquals("Test5",romi.getFirst());
        Assert.assertEquals("Test4",romi.getLast());

        Deque<String> ron = stringDequeMap.get("Ron");
        Assert.assertEquals("Test7",ron.getFirst());
        Assert.assertEquals("Test6",ron.getLast());

    }




}