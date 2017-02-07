# Reminder
Spring boot app that can send reminders to your inbox.

If you would like to send yourself reminders in the future, feel free to use this app!

## You will need

** A gmail account that you will use to send reminders.

** A small server that can support in memory database with your reminders and users.
Feel free to configure a real database.

** 5 minutes to set it up.


## How to run

1) Clone this repo

2) mvn install -DskipTests

3) java -jar --email=sample@gmail.com --password=password // replace with the reminder email+password you setup before.

4) visit http://localhost:8080/



Once email verification is done, you can set up reminders.

Feel free to modify, reuse and share.
