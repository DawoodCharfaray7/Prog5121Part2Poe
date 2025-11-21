
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DC
 */
public class MessageArrayTest {
    
    // Create arrays exactly as QuickChatApp uses
    private final String[] messageIDs = {
        "MID1", "MID2", "MID3", "MID4", "MID5"
    };
    
    private final String[] recipients = {
        "+27834557896",
        "+27838884567",
        "+27834484567",
        "0838884567",
        "+27838884567"
    };

    private final String[] messages = {
        "Did you get the cake?",
        "Where are you? You are late! I have asked you to be on time.",
        "Yohoooo, I am at your gate.",
        "It is dinner time !",
        "Ok, I am leaving without you."
    };

    private final String[] actions = {
        "SENT",
        "STORED",
        "DISREGARDED",
        "SENT",
        "STORED"
    };

    private final String[] hashes = {
        "H1", "H2", "H3", "H4", "H5"
    };


    // 1) Sent messages correctly populated
    @Test
    public void testSentMessagesCorrect() {
        String[] expected = {
            "Did you get the cake?",
            "It is dinner time !"
        };

        int idx = 0;
        String[] actual = new String[2];

        for (int i = 0; i < actions.length; i++) {
            if ("SENT".equals(actions[i])) {
                actual[idx++] = messages[i];
            }
        }

        assertArrayEquals(expected, actual);
    }


    // 2) Longest message test
    @Test
    public void testLongestMessage() {
        String expected = "Where are you? You are late! I have asked you to be on time.";

        String longest = "";
        for (String msg : messages) {
            if (msg.length() > longest.length()) {
                longest = msg;
            }
        }

        assertEquals(expected, longest);
    }


    // 3) Search by Message ID (Message 4)
    @Test
    public void testSearchByMessageID() {
        String targetID = "MID4";

        int index = -1;
        for (int i = 0; i < messageIDs.length; i++) {
            if (messageIDs[i].equals(targetID)) {
                index = i;
                break;
            }
        }

        assertEquals(3, index);
        assertEquals("0838884567", recipients[index]);
        assertEquals("It is dinner time !", messages[index]);
    }


    // 4) Search all messages for recipient +27838884567
    @Test
    public void testSearchByRecipient() {
        String rcpt = "+27838884567";

        String[] expected = {
            "Where are you? You are late! I have asked you to be on time.",
            "Ok, I am leaving without you."
        };

        String[] found = new String[2];
        int idx = 0;

        for (int i = 0; i < recipients.length; i++) {
            if (recipients[i].equals(rcpt)) {
                found[idx++] = messages[i];
            }
        }

        assertArrayEquals(expected, found);
    }


    // 5) Delete message using hash (Message 2)
    @Test
    public void testDeleteMessageByHash() {
        String hashToDelete = "H2";

        int deleteIndex = -1;
        for (int i = 0; i < hashes.length; i++) {
            if (hashes[i].equals(hashToDelete)) {
                deleteIndex = i;
                break;
            }
        }

        assertEquals(1, deleteIndex);

        // simulate deletion: shift left
        for (int i = deleteIndex; i < messages.length - 1; i++) {
            messages[i] = messages[i + 1];
        }

        assertEquals("Yohoooo, I am at your gate.", messages[1]);
    }


    // 6) Display Report (return only SENT messages)
    @Test
    public void testDisplayReport() {
        String[] expected = {
            "Did you get the cake?",
            "It is dinner time !"
        };

        String[] found = new String[2];
        int idx = 0;

        for (int i = 0; i < actions.length; i++) {
            if ("SENT".equals(actions[i])) {
                found[idx++] = messages[i];
            }
        }

        assertArrayEquals(expected, found);
    }
    
}

