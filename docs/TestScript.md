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

1.adding a task<br>
  add floating task by not adding due date
  * add floating task1
  * add floating task2 #testing2 @anywhere p/3 *f d/a floating task
  <br>
  add task by specifying due date
  <br>
  * add task1 due/today dueT/09:00
  * add task2 due/20/12 #testing @anywhere p/1 *f d/a task
  <br>
  add event by specifying start date
  <br>
  * add Event1 due/tmr start/today startT/09:00 dueT/10:00
  * add Event2 due/wednesday start/tue startT/09:00 dueT/10:00
  <br>
  add recurring task/event by specifying frequency
  <br>
  * add recurring task1 due/today dueT/09:00 f/daily
  * add recurring task2 due/tomorrow dueT/10:00 p/3 f/every week
  * add recurring Event1 due/tmr start/today startT/09:00 dueT/10:00 f/daily
  * add recurring Event2 due/2/5 start/1/5 startT/9:00 dueT/9:00 f/month #testing
  <br>
2. deleting a task<br>
  delete a task/event by specifying its index
  * delete 8
  <br>
  delete one occurance of a recurring task/event
  * delete 3
  <br>
  delete all occurances of a recurring task/event
  * delete 4 all
  <br>
3. edit a task<br>
  edit certain field of a task/event
  * edit 1 *f
  * edit 2 n/task1 edited #testing *f
  * edit 8 *u
  * edit 4 startT/8:00 start/tmr
  * edit 6 d/edited
  <br>
  edit one occurance of a recurring task/event by specifying "once"
  * edit 5 once/t n/recuring task2 change
  <br>
  edit field of all occurances of a recurring task/event
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
6. finish a task<br>
  finish a task/event
  * finish 3
  * finish 7
  <br>
  finish a recurring task/event
  * finish 1
  <br>
7. find tasks by names<br>
  find all tasks containing keywords
  * find task
  * find task1
  <br>
  find unfinished/finished tasks containing keywords
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
10. load an xml data file<br>
  path can be both relative path or absolute path
  * load data/sample_data.xml
  <br><br>
11. scroll to the specific task
  * scroll 2
  * scroll 6
  * scroll 11
  * scroll 14
  <br>
9. view tasks by due dates<br>
  view tasks with due before the given date
  * view next/10
  * view next/4/5
  * view next/sunday
  <br>
  view tasks with due on the given date
  * view on/10
  * view on/4/5
  <br>
13. exit the program
  * exit
  <br>
