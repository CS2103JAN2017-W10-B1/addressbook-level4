## Test Script

#### 1. view help message for other commands
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


#### 2. Adding a task

  2.1 Add floating task by not adding due date
  * add floating task1
  * add floating task2 #testing2 @anywhere p/3 *f d/a floating task

  2.2 Aadd task by specifying due date
  * add task1 due/today dueT/09:00
  * add task2 due/20/12 #testing @anywhere p/1 *f d/a task

  2.3 Add event by specifying start date
  * add Event1 due/tmr start/today startT/09:00 dueT/10:00
  * add Event2 due/wednesday start/tue startT/09:00 dueT/10:00

  2.4 Add recurring task/event by specifying frequency
  * add recurring task1 due/6/6 dueT/09:00 f/daily
  * add recurring task2 due/tomorrow dueT/10:00 p/3 f/every week
  * add recurring Event1 due/tmr start/today startT/09:00 dueT/10:00 f/daily
  * add recurring Event2 due/2/5 start/1/5 startT/9:00 dueT/9:00 f/month #testing


#### 3. Deleting a task

  3.1 Delete a task/event by specifying its index
  * delete 9

  3.2 Delete one occurrence of a recurring task/event
  * delete 6

  3.3 Delete all occurrences of a recurring task/event
  * delete 7 all


#### 4. Edit a task

  4.1 Edit certain field of a task/event
  * edit 1 *f
  * edit 1 n/task1 edited #testing
  * edit 7 *u
  * edit 6 startT/8:00 start/30/5
  * edit 8 d/edited

  4.2 Edit one occurrence of a recurring task/event by specifying "once"
  * edit 6 once/t n/recurring event2 change

  4.3 Edit field of all occurrences of a recurring task/event
  * edit 7 f/week
  * edit 7 @nus


#### 5. Undo the previous command
  * undo
  * undo


#### 6. Redo the previous undo
  * redo
  * redo



#### 7. Clear the current data
  * clear


#### 8. Load an xml data file (path can be both relative path or absolute path)
  * load sample_data.xml


#### 9. Scroll to the specific task
  * scroll 5
  * scroll 22
  * scroll 33
  * scroll 1

#### 10. Finish a task

  10.1 Finish a task/event
  * finish 5
  * finish 21

  10.2 Finish a recurring task/event
  * finish 1


#### 11. Find tasks by names

  11.1 Find all tasks containing keywords
  * find cs
  * find cs2103

  11.2 Find unfinished/finished tasks containing keywords
  * find final unfinished
  * find quiz finished


#### 12. List tasks by tags
  * list
  * list favorite
  * list finished
  * list all
  * list inbox
  * list all cs2103 
  * list finished cs2103
  * list personal
  * list favorite travelinsummer



#### 13. View tasks by due dates

  13.1 View tasks with due before the given date
  * view next/10
  * view next/4/5
  * view by/sunday

  13.2 View tasks with due on the given date
  * view on/10
  * view on/sunday
  * view on/4/5

  13.3 View tasks due today
  * view


#### 14. Exit the program
  * exit

