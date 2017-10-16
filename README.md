# Essential Linux (Ubuntu) commands:

Run a java jar in silent mode and save progress in a nohup file:
  nohup java -jar spring-boot-1.0-SNAPSHOT.jar &

Read from nohup file: less nohup.out

Check if application is running:
ps -ef | grep spring – this command is useful check whether the application is running or not.

Find a running process id PID by application name: ps ax | grep firefox

Kill/Stop an application:
kill -9 PID – use this command to kill running process. The PID is processing ID.

rm nohup.out - use for delete nohup.out file.

less nohup.out – open the nohup.out file

Find all users: cut -d: -f1 /etc/passwd
