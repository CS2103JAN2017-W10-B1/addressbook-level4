## Test Script

1. view help message for other commands
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
  

2. Adding a task

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
  * add recurring task1 due/today dueT/09:00 f/daily
  * add recurring task2 due/tomorrow dueT/10:00 p/3 f/every week
  * add recurring Event1 due/tmr start/today startT/09:00 dueT/10:00 f/daily
  * add recurring Event2 due/2/5 start/1/5 startT/9:00 dueT/9:00 f/month #testing
  

3. Deleting a task

  3.1 Delete a task/event by specifying its index
  * delete 8
  
  3.2 Delete one occurrence of a recurring task/event
  * delete 3

  3.3 Delete all occurrences of a recurring task/event
  * delete 4 all
  

4. Edit a task

  4.1 Edit certain field of a task/event
  * edit 1 *f
  * edit 2 n/task1 edited #testing *f
  * edit 8 *u
  * edit 4 startT/8:00 start/tmr
  * edit 6 d/edited
  
  4.2 Edit one occurrence of a recurring task/event by specifying "once"
  * edit 5 once/t n/recurring task2 change

  4.3 Edit field of all occurrences of a recurring task/event
  * edit 6 f/month
  * edit 6 @nus
  

5. Undo the previous command
  * undo
  * undo
  

6. Redo the previous undo
  * redo
  * redo
  

7. Finish a task

  7.1 Finish a task/event
  * finish 3
  * finish 7

  7.2 Finish a recurring task/event
  * finish 1
 

8. Find tasks by names

  8.1 Find all tasks containing keywords
  * find task
  * find task1

  8.2 Find unfinished/finished tasks containing keywords
  * find task1 unfinished
  * find task1 finished
  

9. List tasks by tags
  * list
  * list favorite
  * list finished
  * list all
  * list inbox
  * list all inbox 
  * list finished inbox
  * list testing
  * list favorite testing
  

10. Clear the current data
  * clear
  

11. Load an xml data file (path can be both relative path or absolute path)
  * load data/sample_data.xml
  

12. Scroll to the specific task
  * scroll 2
  * scroll 6
  * scroll 11
  * scroll 14
  

13. View tasks by due dates

  13.1 View tasks with due before the given date
  * view next/10
  * view next/4/5
  * view next/sunday

  13.2 View tasks with due on the given date
  * view on/10
  * view on/4/5
  

14. Exit the program
  * exit
  
