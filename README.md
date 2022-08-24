# Week 17

## API Design - VideoContentService

Run this command to execute the completion tests:

```
./gradlew apidesign-videocontentservice-test
```


## DynamoDB Index Design - Little League

Run this command to create the DynamoDB Tables:

```
aws cloudformation create-stack --stack-name dynamodbindexdesign-littleleague --template-body file://DynamoDBIndexDesign/LittleLeague/match_table.yaml --capabilities CAPABILITY_IAM
aws cloudformation wait stack-create-complete --stack-name dynamodbindexdesign-littleleague
```

Run these commands to execute the tests for each phase:

```
./gradlew dynamodbindexdesign-littleleague-dao-test 

./gradlew dynamodbindexdesign-littleleague-phase0-test
./gradlew dynamodbindexdesign-littleleague-phase1-test
./gradlew dynamodbindexdesign-littleleague-phase2-test
./gradlew dynamodbindexdesign-littleleague-phase3-test
```


## DynamoDB Table Design - Ice Cream Parlor

Run these commands to create the DynamoDB Tables:

```
aws cloudformation create-stack --stack-name dynamodbtabledesign-recipestables01 --template-body file://DynamoDBTableDesign/IceCreamParlor/RecipesTable.yaml --capabilities CAPABILITY_IAM
aws cloudformation create-stack --stack-name dynamodbtabledesign-cartonstable01 --template-body file://DynamoDBTableDesign/IceCreamParlor/CartonsTable.yaml --capabilities CAPABILITY_IAM
```

Run these commands to execute the tests for each phase:

```
./gradlew dynamodbtabledesign-icecreamparlor-phase0
./gradlew dynamodbtabledesign-icecreamparlor-phase1
./gradlew dynamodbtabledesign-icecreamparlor-phase3
./gradlew dynamodbtabledesign-icecreamparlor-extension-test
```

## Group Work - KenzieEventPlanner

Run these commands to create the DynamoDB Tables:

```
aws cloudformation create-stack --stack-name groupwork-events --template-body file://GroupWork/KenzieEventPlanner/events_table.yaml --capabilities CAPABILITY_IAM
aws cloudformation create-stack --stack-name groupwork-invites --template-body file://GroupWork/KenzieEventPlanner/invites_table.yaml --capabilities CAPABILITY_IAM
aws cloudformation create-stack --stack-name groupwork-members --template-body file://GroupWork/KenzieEventPlanner/members_table.yaml --capabilities CAPABILITY_IAM
```

Run these commands to execute the tests for each phase:

```
./gradlew groupwork-kenzieeventplanner-phase0
./gradlew groupwork-kenzieeventplanner-phase2
./gradlew groupwork-kenzieeventplanner-phase3
./gradlew groupwork-kenzieeventplanner-phase4
```
