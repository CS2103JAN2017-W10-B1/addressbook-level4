## Test Script

0. view help message for other commands
  * help
  * help add
  * help delete
  * help edit
  * help undo
  * help redo
  * help find
  * help list
  * help view
  * help clear
  * help load
  * help scroll
  * help exit
  <br>

1.adding a task
  * add floating task1
  * add floating task2 #testing2 @anywhere p/3 *f d/a floating task
  * add task1 due/today dueT/09:00
  * add task2 #testing @anywhere p/1 *f d/a task
  * add Event1 due/tmr start/today startT/09:00 dueT/10:00
  * add Event2 due/wednesday start/tue startT/09:00 dueT/10:00
  * add recurring task1 due/today dueT/09:00 f/daily
  * add recurring task2 due/tomorrow dueT/10:00 p/3 f/every week
  * add recurring Event1 due/tmr start/today startT/09:00 dueT/10:00 f/daily
  * add recurring Event2 due/2/5 start/1/5 startT/9:00 dueT/9:00 f/month #testing
  <br>

2. deleting a task
  * delete 8
  * delete 3
  * delete 4 all
  <br>

3. edit a task
  * edit 1 *f
  * edit 2 n/task1 edited #testing *f
  * edit 8 *u
  * edit 4 startT/8:00 start/tmr
  * edit 6 d/edited
  * edit 5 once/t n/recuring task2 change
  * edit 6 f/month
  * edit 6 @nus
  <br>

4. undo the previous command
  * undo
  * undo
  <br>

5. redo the previous undo
  * redo
  * redo
  <br>

6. finish a task
  * finish 3
  * finish 7
  * finish 1
  <br>

7. find tasks by names
  * find task
  * find task1
  * find task1 unfinished
  * find task1 finished
  <br>

8. list tasks by tags
  * list
  * list favorite
  * list finished
  * list all
  * list inbox
  * list all inbox 
  * list finished inbox
  * list testing
  * list favorite testing
  <br>

9. clear the current data
  * clear
  <br>

10. load an xml data file
  * load data/sample_data.xml
  <br>

11. scroll to the specific task
  * scroll 2
  * scroll 6
  * scroll 11
  * scroll 14
  <br>

9. view tasks by due dates
  * view next/10
  * view next/50
  * view next/sunday
  * view on/10
  * view on/4/5
  <br>

13. exit the program
  * exit
  <br>
