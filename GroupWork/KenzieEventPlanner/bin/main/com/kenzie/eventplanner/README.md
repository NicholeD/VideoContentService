# We're still having parties, right?
      
**branch name:** dynamodbindexdesignandusage-classroom

**AWS account:** (account number for AWS account that ATA gave you for this unit --
[find on Conduit](https://conduit.security.a2z.com/accounts) )

**role:** IibsAdminAccess-DO-NOT-DELETE

**RDE workflows:**
- `dynamodbindexdesignandusage-classroom-phase0`
- `dynamodbindexdesignandusage-classroom-phase2`
- `dynamodbindexdesignandusage-classroom-phase3`
- `dynamodbindexdesignandusage-classroom-phaseextra`

## Introduction

Today we are building more into a prior lesson's app that allows members create events
(e.g. parties, zoom hangouts, clubs, dining out). This activity will focus on some 
operations that aren't handled well by the current DynamoDB table designs.

We will still be using three key entities in our app:
* Members: The people who can invite one another to events
    * Partition key: id
* Events: The events that people are being invited to
    * Partition key: id
* Invites: A request for a particular Member to attend a particular Event
    * Partition key: eventId
    * Sort key: memberId

See this [Plant UML diagram of the 3 entities](https://tiny.amazon.com/16m6jkssl/Models)

The functionality we are adding today:
* Events
    * GetEventsForOrganizer - Retrieves all `Event` data for a provided `organizerId`

The code base follows the Activity-DAO-DynamoDBMapper pattern that we've come
to know and love. The various Activity classes each implement one operation
that our service supports. That Activity may depend on several of
`MemberDao`'s, `EventDao`'s, `InviteDao`'s methods to accomplish their
use cases. Each DAO is responsible for one model type, and only interacts
with that model's DynamoDB table.

Disclaimer: One difference that you'll notice here is that our Activity
classes don't yet accept/return Request/Result objects. They're
accepting/returning individual values. We'll do this until it's time to
create our service infrastructure and create the necessary Coral models.
That retrofit is beyond the scope of this activity, but will be fairly
easily accomplished in the Activity classes themselves when the time comes.

## Phase 0: Preliminaries

1. Make sure `ada` is running with the credentials specified at the top of this README
(should be your AWS account that ATA gave you for this unit).
1. Create the tables we'll be using for this activity by running these aws CLI commands:
   ```none
   aws cloudformation create-stack --region us-west-2 --stack-name dynamodbindexes-eventstable01 --template-body file://cloudformation/dynamodbindexdesignandusage/classroom/events_table.yaml --capabilities CAPABILITY_IAM
   aws cloudformation create-stack --region us-west-2 --stack-name dynamodbindexes-invitestable01 --template-body file://cloudformation/dynamodbindexdesignandusage/classroom/invites_table.yaml --capabilities CAPABILITY_IAM
   aws cloudformation create-stack --region us-west-2 --stack-name dynamodbindexes-memberstable01 --template-body file://cloudformation/dynamodbindexdesignandusage/classroom/members_table.yaml --capabilities CAPABILITY_IAM
   ```
1. Make sure that each runs without error.
1. Log into your AWS account on Conduit. Visit the CloudFormation console.
   Ensure that each stack you created (see the stack-name in the commands above) 
   has a status of 'CREATE_COMPLETE'. Then proceed to the next step.
1. Visit the DynamoDB console and verify that the tables exist and have
   sample data. It may take a few minutes for the data population logic to execute, so be patient.
   Each table will be prefixed with "DynamoDBIndexes-" so they will not clash with any other classes
   that also use the Events Service.
1. As a final verification, run the `dynamodbindexdesignandusage-classroom-phase0` RDE workflow
   and make sure it passes.

GOAL: Events, Invites, and Members tables are all created in your AWS Account, and
the attributes make sense.

Phase 0 is complete when:
- You understand the 3 data types and their relationships
- Events, Invites, Members tables exist with some sample data
- `dynamodbindexdesignandusage-classroom-phase0` RDE workflow passes

## Phase 1: I planned what??

We are planning to expand our service with the following operation:
1. `GetEventsForOrganizer`
   * Retrieve all `Event` data for a provided `organizerId`
   * Return the `Event` data sorted in the order that they occur (earliest to latest)
   * We plan to enhance this operation in the near future by allowing users to request 
     all events, cancelled events, or not cancelled events

As a group, answer the questions in 
`src/main/java/com/amazon/ata/dynamodbindexdesignandusage/classroom/resources/gsiDesign.txt`. Your recorder should 
share their screen and document your group's answers in the doc. They will share the contents with the group at the end
of this phase.

Phase 1 is complete when:
* Your recorder has shared the answers with the group.
* Each group member has updated the `src/main/java/com/amazon/ata/dynamodbindexdesignandusage/classroom/resources/gsiDesign.txt` 
  file with the group's answers.

## Phase 2: Add new GSI for `GetEventsForOrganizer`

In the next two phases, we're going to implement `GetEventsForOrganizer` with a query. Before we can get into the Java
code, we'll need to update the CloudFormation table definition according to your design from Phase 2.

To write and test your CloudFormation changes, we will update the table's CloudFormation file, update the stack that
creates the table, verify in the console, (maybe clean up mistakes), and iterate if necessary.

The CloudFormation file you will be editing is: `cloudformation/dynamodbindexdesignandusage/classroom/events_table.yaml`
**Hint:** You can use the `DynamoDBIndexes-Invites` CloudFormation as a reference as you write a new GSI.

Development workflow:
1. Add your GSI to the scratch CloudFormation definition
1. Update your stack, so your table gets updated by running:
    ```none
      aws cloudformation update-stack --region us-west-2 --stack-name dynamodbindexes-eventstable01 --template-body file://cloudformation/dynamodbindexdesignandusage/classroom/events_table.yaml --capabilities CAPABILITY_IAM
    ```
   **Or**
   If you needed to delete your stack for any reason, you'll need to run this command to create your stack.
   ```none
     aws cloudformation create-stack --region us-west-2 --stack-name dynamodbindexes-eventstable01 --template-body file://cloudformation/dynamodbindexdesignandusage/classroom/events_table.yaml --capabilities CAPABILITY_IAM
   ```
1. Visit the CloudFormation console and verify your stack (dynamodbindexes-eventstable01) has the status 
   'CREATE_COMPLETE'.
1. Visit the DynamoDB console to view your index in the Index tab. Verify it is as expected.
1. Repeat steps 1 - 4 until your table has the GSI you expect when you view it in the DynamoDB console.
   1. If you successfully created an index, but you want to change the name, projection, or keys, you
      need to delete the stack before proceeding. See the troubleshooting section below.

Troubleshooting:
1. **Deleting your stack**: Select your stack in the CloudFormation console. Click the delete button. This will take
   a minute. Wait for it to delete before proceeding. You may need to refresh your page to verify it is deleted.
1. If your stack fails to update (Status: UpdateRollbackComplete), click on the Stack and view the entries in 
   the Events tab to find an error message. Then manually delete your stack using the directions above.
1. If you see the error below when running your aws command, this means you deleted your stack and need to instead
   run the `create-stack` command:
   - `An error occurred (ValidationError) when calling the UpdateStack operation: Stack [dynamodbindexes-eventstable01] does not exist`
1. CloudFormation only lets us update 1 GSI at a time. Since we're only adding one, this should not be a problem.

Phase 2 is complete when:
* The CloudFormation definition in `events_table.yaml` has been updated with a new GSI to serve `GetEventsForOrganizer`
* Your `DynamoDBIndex-Events` table has the new GSI needed for `GetEventsForOrganizer`
* `dynamodbindexdesignandusage-classroom-phase2` RDE workflow passes.

## Phase 3: Use your GSI in `GetEventsForOrganizer`

`GetEventsForOrganizer`'s `handleRequest()` method makes a call to `getEventsForOrganizer` in `EventDao` to retrieve 
the `Event` data. `getEventsForOrganizer` currently contains a dummy implementation. In order to get it working 
you will need to:

1. Update the `Event` model to add the annotations necessary for your GSI.
1. Implement `getEventsForOrganizer` in `EventDao` using a query.

The workflow `dynamodbindexdesignandusage-classroom-phase3` tests `getEventsForOrganizer`.
You can use it to verify that everything is working once you've made your changes.
If you see a failed test with this message: `Cannot read from backfilling global secondary index:`
you will need to wait for your index to finish creating, backfilling data, and then run your test again.

Phase 3 is complete when:
* You have updated `getEventsForOrganizer` to use a query against the new GSI of `DynamoDBIndexes-Events`.
* `dynamodbindexdesignandusage-classroom-phase3` RDE workflow passes.

### Phase Extra: Getting invites for our members is getting slower, and slower, and slower...

Since we've written the code, we've noticed that `GetInvitesForMemberActivity` is having performance issues. As more
events and invites get added to our database, the problem is getting worse. If members can't get their invites, then
they won't be able to RSVP!

We've investigated, and identified that the call to `getInvitesSentToMember` in `InviteDao` is causing the issue. Take
a look at the implementation and see if you can come up with the reason why before you continuing reading.

If you came up with: "That scan seems to be the problem!", you got it! The method is implemented using a scan 
operation. This means that every time the `getInvitesSentToMember` method gets called, our DAO has to read 
the entire Invites table. Each item in the table is inspected to see if it is an invite for the requested member, 
and if so, DynamoDB will return it.

Depending on your table design, this might be your only option. **Note**: if the scan is performed infrequently 
enough, and if you can tolerate the time it takes to complete, it may be the more economical solution 
than creating a new GSI that doesn't get used often. However, getting invites for a user, is a frequent operation, and
our users will not tolerate the time a scan takes to complete. Using a DynamoDB Global Secondary Index (GSI) 
with a different key schema can make data that was only available via a scan on the original table available 
via a query. 

We've gone ahead and created an index for the Invite table called, `MemberIdIndex`. Visit the DynamoDB console to view 
more information about this index. You can do this by clicking on the table, and then the Index tab.

Answer these questions:

1. What is the partition key?
   - ANSWER: 
1. What is the sort key, if any?
   - ANSWER: 
1. What attributes are projected into the index?
   - ANSWER:

Before we can write a query against the index, we need to update our `Invite` model to be aware of the index. 
Update `Invite` to add the `@DynamoDBIndexHashKey` to your partition key, and the `@DynamoDBIndexRangeKey`
to your sort key, if applicable. Using a public constant to define the name of your index is a good practice
to follow. This allows your POJO and DAO to share the string. 

Now we can replace the scan with a query! Update the `getInvitesSentToMember` in `InviteDao` to use a query.

The workflow `dynamodbindexdesignandusage-classroom-phaseextra` tests `getInvitesSentToMember` and should be passing 
before you make changes. You can use it to verify that everything is still working after you make your changes.

Phase Extra is complete when:
* You have updated `getInvitesSentToMember` to use a query against the `MemberIdIndex` GSI of `DynamoDBIndexes-Invites`.
* `dynamodbindexdesignandusage-classroom-phaseextra` RDE workflow passes.

## Extension

Determine if the following use cases can be fulfilled with a query or if a scan through
all items in the table is necessary, given the current tables' key schemas/GSIs. 
If a scan is necessary, would it be beneficial to implement a GSI (or more than one).

1. As a member, I want to see all event invites I have received when I login.
1. As an events team PM, I want to create a monthly report that counts the number of events on each day.
1. As a member, I want to retrieve all the events I am attending.
