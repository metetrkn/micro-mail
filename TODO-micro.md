db and error handling, than apply all this into other mail types as well

error handling, retry system for mail sender,http, logging db opertions
logging in producer side after sending successfully and after consumer gets the messages, db saving after mailgun succeffully sends the message


what can duplicate as big files
property service
sent mail repository

Microservice should not get collapse if somehing changed in backend or get different type of mail, should ignore that message
but conitnue to send other messages

if error occurs, should know how api answers, how error logs etc, important as well
1 sc is good

token should send to microservice or not ?

virtual threads, you can use it!

what should be do with mailattachment, how the actual data is formed, should it formed totaly in microservice or it would be formed in backend app and sent to microservice

when consumer can not deserilize, hantere message from rqueue, message get stuck in queu and it infintally retry. 
it should drop not hanterad message after some point and log it

log-mail/mail-failures.log file has problem, after 3 try, it should log error one (the last one into here), but it 
doesnt. 

currently we log in producer side, consumer side and if error occour in the queu with config/RqeuueGlobalErrorLogger


create mailattechmnent in backend app and send it in rqueueu message or if otherway around possible do like this

Inject the Redis Template instead of the high-level Enqueuer <much faster>

in microservice side, create an async service that chehcks mailsgun realy sent mails
even though it returns 200 ok, somotimes it can not send succesfully

TODO
1- set wiremock ports work
2- Setup db operations, there are in the mailgunsender
3- make system works including publishin mail event, sending it with mailgun
4- After sendm1 is done, focus on virtual threads
5- make system robust, in error scenierios
6- make it effecient

TODO
COmmonDTO fields should be set in main app not in here, in ms should only recieved those who created other side
do not extend commondto from mailrqeuestdto, rather use in send mail in this side, create your own dto and use in here
use sendmail class to utilize all these created, updated etc from commondto


H0W SYSTEM WORKS
1- there is no blocking in queue, if a message is bad, retries 3 times, while this time it does not block other ones
2- Wrong type of mail request in producer side (main app) does not cause crash in the system, it logs it and continues


The setting spring.threads.virtual.enabled=true in your application.properties is actually a global switch for Spring Boot 3.2+. This means you are already utilizing virtual threads in more places than just your consumer.
Here is a breakdown of where they are active and how you can explicitly use them elsewhere in your project (specifically for your external API integrations).
1. Where you are ALREADY using them (Automatically)
   Because you enabled the property, Spring Boot has automatically configured the following:
   •
   Incoming HTTP Requests: Every request hitting your server.port=8081 (Tomcat) is now handled by a virtual thread. You don't need to change your Controllers; they are already non-blocking at the OS level.
   •
   Database Calls: Your MySQL calls (via Hibernate/JDBC) run on these virtual threads. When the application waits for the database to respond, the underlying OS thread is released to handle other work.
   •
   @Async and @Scheduled: If you use these annotations, Spring uses the SimpleAsyncTaskExecutor by default, which is now configured to use virtual threads.
2. Where you CAN use them (Manual Implementation)
   Based on your application.properties, you have credentials for TikTok, LinkedIn, Instagram, and Google. This suggests your application likely fetches data from these services.
   This is the perfect use case for virtual threads: Scatter-Gather. You can fire off requests to all these social platforms in parallel. Because virtual threads are cheap, you can spin up a new thread for every single API call without worrying about resource exhaustion.
   Here is a new service example demonstrating how to fetch data from multiple sources in parallel using Executors.newVirtualThreadPerTaskExecutor().

sendWebOrderPayed
I HAVE SET DTO 'S ' HITEVENTMAILDTO AND ORDERMAILDTO SET RELATED FIELDS FROM PRODUCER SIDE AND USE THEM IN CONSUMER SIDE