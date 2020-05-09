# Squawker Code

This is a exercise repository for the Squawker example app which is part of Udacity's Advanced Android course. 
The Squawker example app uses Firebase Cloud Messenger to receive Twitter-like messages, sent from [this server](https://squawkerfcmserver.udacity.com/), in real time. 
You can learn more about how to use this repository [here](https://classroom.udacity.com/courses/ud857/lessons/8b2a9d63-0ff5-48ff-90d3-a9855b701dae/concepts/41b82e3c-2797-46e5-8a66-684098ca8cbb).

# 3. Quiz:  ___Polling___ vs.  ___Pushing___

Take a look at the following apps. 
For which of these app would it be important, possible and worth the extra work to implement pushing? 
Check all that apply.

[&emsp;]      &nbsp;    An app that uses [the Movie Database](https://www.themoviedb.org/?language=en) to get information about movies and then displays a list of recent movies
[✓]           &nbsp;    A simple chat application
[✓]           &nbsp;    Squarker
[&emsp;]      &nbsp;    A simple alarm clock application

Squawker and a simple chat app are both examples of applications where updates may occur at any time and where it is important that users receive these updates in close to real time. 
Therefore, they are great candidates for implementing pushing.


# 4. Introduction to FCM

## Server-Side

Needs to keep track of all client devices, including delivering messages even if device temporary looses connectivity or is off.

## Client-Side 

Needs to be configured to handle messages from the server and process the information contained within the messages

## FCM - Firebase Cloud Messaging

