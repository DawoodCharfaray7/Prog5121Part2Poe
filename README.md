 
* GitHub Repository: https://github.com/DawoodCharfaray7/Prog5121Part2Poe

# Prog5121Part2Poe
ChatApp

This is the **Part 2 Practical Programming Project** for PROG5121.

 Features
- Registration and login with validation
- Message creation, validation, and JSON storage
- Error handling with retry loops
- Automated JUnit 4 tests via GitHub Actions

 Testing
 Automated tests run in GitHub using the file:

GitHub Repository: https://github.com/DawoodCharfaray7/Prog5121Part2Poe


PART 3 - POE

Final Changes

Message Arrays Implemented
Five parallel arrays were created (messageIDs, recipients, messages, actions, hashes) to store message data exactly as provided in the POE.
Each index represents a complete message record.

- Extracting Sent Messages

The program loops through the actions array to identify entries marked as "SENT".
Only the corresponding messages are collected and returned.
Ensures correct filtering of messages based on status.

- Finding the Longest Message

Every message in the messages array is compared by length.
The longest message is identified and returned.

Matches the expected longest message from the POE dataset.

- Searching for a Message by Message ID

The user can search for a message using its messageID.
The system finds the index of the matching ID and returns the associated:
recipient
message text
Ensures accurate ID-based lookup.

- Searching All Messages for a Specific Recipient

The program scans the recipients array for all occurrences of a particular phone number.
Every matching message is added to a result list.
Allows users to see all messages sent to a specific person.

 - Deleting a Message Using Its Hash

The message hash acts as a unique identifier for deletion.
When a hash is matched, the corresponding message is “deleted” by shifting all elements after it one position left.
Demonstrates typical array deletion logic.

 - Generating a Formatted Report

A report is generated showing only messages marked "SENT".
Includes the message text and associated data.
Confirms correct filtering and formatting of output.

- JUnit Tests Created

Each feature above has an associated JUnit test:

Extracting SENT messages
Identifying the longest message
Searching by message ID
Searching by recipient
Deleting by hash
Displaying the SENT message report
These tests confirm that Part 3 features work exactly as intended.