
# Mailer microservice

Java microservice, that storage email addresses and sent emails to them. At this point, it is recommended to use Postman to sent queries. For further information, go to Usage/Examples.


## Installation

Install Mailer with git

```bash
  git clone https://gitlab.com/lukasz9152106/mailer.git
  cd mailer
  ./gradlew build
```
If error with wrong gradle version appears, use
./gradlew wrapper --gradle-version 7.6
and try again with
./gradlew bulid

Alternatively, You could import it to IDE, like eg. IntelliJ


## Usage/Examples

#To run project use
```bash
  ./gradlew bootRun
```
If error with failed tests appears, please try again. It happens from time to time, but always pass after another attempt.

At this point You could use Postman to use Mailer, however other similar tools should work as well.


#To add emails to database, use POST method:
```
localhost:8080/users/GetAll
```
and input JSON:
```
    {
        "email": "{}"
    }
```
as Body -> raw -> JSON, where {} should be replaced by an email address, eg:
```
    {
        "email": "example@email.com"
    }
```
You can add only 1 email address at once, emails should not duplicate and they must match regex pattern:
```
^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$
```
Added email should be shown in response body.
If trying to add multiple emails, only first one will count, and if the email is a duplicate or doesn't match regex pattern, an error should appears.

#To list emails added use GET method:
```
localhost:8080/users/GetAll
```
No additional parameters required, emails in database will be listed in response body, unless the list is empty, than an error should appears.

#To delete email use DELETE method:
```
localhost:8080/users/DeleteEmail/{}
```
where instead of {} You should put email address, eg:
```
localhost:8080/users/DeleteEmail/example@email.com
```
No additional parameters required, deleted email will be listed in response body.
You can delete only 1 email at once and if trying to delete email that is not in the database, an error should appears.

#To change email use PUT method:
```
localhost:8080/users/ChangeEmail?email={}&newEmail={}
```
where instead of {} You put current email address and the email address that You want to replace with, eg:
```
localhost:8080/users/ChangeEmail?email=old@email.com&newEmail=new@email.com
```
Alternativelly, You could use only
```
localhost:8080/users/ChangeEmail
```
and input Query Params with keys
```
email
newEmail
```
and respective values to them, the result will be excatly the same.
If there is not an email in the database, or new email is not matching mentioned above regex patter, an error should appears.

#To send emails use GET method:
```
localhost:8080/email/sendEmails
```
and input JSON:
{
"subject":"{}",
"body": "{}"
}
as as Body -> raw -> JSON, where {} should be replaced by Your mail's subject and body, eg:
{
"subject":"Some sample email subject",
"body": "Some sample email body"
}
Sending emails could take a while. In response body should appers information about number of emails sent, or an error, if list of emails is empty.
By default, emails will be sent from email zauwazyczewszystkiewyjatki@outlook.com. If You want to change it, go to Configuration section below.
## Configuration
As mentioned in Usage/Examples section, default sender email would be zauwazyczewszystkiewyjatki@outlook.com, which was created especially for this application as a random quote from the book.
If You want to change it, go to com.microservices.mailer.config.ApplicationConfig and there You have to change
```
mailSender.setUsername("{}");
mailSender.setPassword("{}");
```
where in setUsername instead of {} You have to input Your mail and in setPassword instead of {} You have to input Your password.
Remember, that You also have to change Your mail smtp server settings here:
```
mailSender.setHost("{}");
mailSender.setPort({});
```
with server and port respectively.
You can also change regex pattern to validate email addresses, if You want to, as used one is very basic. To do it go to com.microservices.mailer.services.UserService and replace String regexPattern content:
```
String regexPattern = "{}";
```
## Tests

Application was tested manually and some JUnit test were performed as well. Unfortunatelly, due to lack of time and knowledge, not all of them.
Additionally, sometimes it happens, that assertion "JUnit test for checking if email exist operation" is failing. Reason for that is unknown, however after restarting tests, everything should be fine.