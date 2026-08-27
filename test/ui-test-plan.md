# Console UI test plan

This file is the source of truth for Tung Tung's console UI tests. Each case runs in a fresh program session; include `bye` in every input block so the chatbot exits cleanly.

## Program setup

- Java version: 25
- Source directory: `src/main/java`
- Main class: `TungTung`

## Test case: load-saved-tasks

### Aim

Verify that a new chatbot session loads the tasks saved in `data/tungtung.txt`.

### Input

```text
list
bye
```

### Expected output

```text
_____________________________________________________________
  _____          _    _ ______ _____  ______ 
 / ____|   /\   | |  | |  ____|  __ \|  ____|
| (___    /  \  | |__| | |__  | |__) | |__   
 \___ \  / /\ \ |  __  |  __| |  _  /|  __|  
 ____) |/ ____ \| |  | | |____| | \ \| |____ 
|_____//_/    \_\_|  |_|______|_|  \_\|______|

Hello! Tung Tung Sahere!
How can I assist?
_____________________________________________________________
  ME: _____________________________________________________________
Here are the tasks in your list:
1.[T][ ] borrow book
2.[E][ ] project meeting (from: Oct 15 2019 - to: Oct 16 2019)
_____________________________________________________________
  ME: _____________________________________________________________
Bye! Tung Tung Sagone!
_____________________________________________________________
```

## Test case: add-and-list-todo

### Aim

Verify that a `todo` command creates an incomplete todo task and that `list` displays it with task number 1.

### Input

```text
todo borrow book
list
bye
```

### Expected output

```text
_____________________________________________________________
  _____          _    _ ______ _____  ______ 
 / ____|   /\   | |  | |  ____|  __ \|  ____|
| (___    /  \  | |__| | |__  | |__) | |__   
 \___ \  / /\ \ |  __  |  __| |  _  /|  __|  
 ____) |/ ____ \| |  | | |____| | \ \| |____ 
|_____//_/    \_\_|  |_|______|_|  \_\______|

Hello! Tung Tung Sahere!
How can I assist?
_____________________________________________________________
  ME: _____________________________________________________________
Got it. I've added this task:
  [T][ ] borrow book
Now you have 1 tasks in the list.
_____________________________________________________________
  ME: _____________________________________________________________
Here are the tasks in your list:
1.[T][ ] borrow book
_____________________________________________________________
  ME: _____________________________________________________________
Bye! Tung Tung Sagone!
_____________________________________________________________
```

## Test case: reject-invalid-task-commands

### Aim

Verify that commands with missing, non-numeric, or incomplete details show an error and the chatbot continues accepting commands.

### Input

```text
mark
todo read book
delete two
deadline return book
event project /from Monday
deadline return book /by tomorrow
event backwards /from 2019-10-16 /to 2019-10-15
todo buy | sell
bye
```

### Expected output

```text
_____________________________________________________________
  _____          _    _ ______ _____  ______ 
 / ____|   /\   | |  | |  ____|  __ \|  ____|
| (___    /  \  | |__| | |__  | |__) | |__   
 \___ \  / /\ \ |  __  |  __| |  _  /|  __|  
 ____) |/ ____ \| |  | | |____| | \ \| |____ 
|_____//_/    \_\_|  |_|______|_|  \_\|______|

Hello! Tung Tung Sahere!
How can I assist?
_____________________________________________________________
  ME: _____________________________________________________________
OOPS!!! Please provide a valid task number.
_____________________________________________________________
  ME: _____________________________________________________________
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
_____________________________________________________________
  ME: _____________________________________________________________
OOPS!!! Please provide a valid task number.
_____________________________________________________________
  ME: _____________________________________________________________
OOPS!!! Use: deadline DESCRIPTION /by yyyy-MM-dd.
_____________________________________________________________
  ME: _____________________________________________________________
OOPS!!! Use: event DESCRIPTION /from yyyy-MM-dd /to yyyy-MM-dd.
_____________________________________________________________
  ME: _____________________________________________________________
OOPS!!! Dates must use yyyy-MM-dd, for example 2019-10-15.
_____________________________________________________________
  ME: _____________________________________________________________
OOPS!!! An event's end date cannot be before its start date.
_____________________________________________________________
  ME: _____________________________________________________________
OOPS!!! Task details cannot contain " | ".
_____________________________________________________________
  ME: _____________________________________________________________
Bye! Tung Tung Sagone!
_____________________________________________________________
```

## Test case: delete-task

### Aim

Verify that `delete` removes the requested task, reports it to the user, and renumbers the remaining tasks in `list`.

### Input

```text
todo read book
todo borrow book
delete 1
list
bye
```

### Expected output

```text
_____________________________________________________________
  _____          _    _ ______ _____  ______ 
 / ____|   /\   | |  | |  ____|  __ \|  ____|
| (___    /  \  | |__| | |__  | |__) | |__   
 \___ \  / /\ \ |  __  |  __| |  _  /|  __|  
 ____) |/ ____ \| |  | | |____| | \ \| |____ 
|_____//_/    \_\_|  |_|______|_|  \_\______|

Hello! Tung Tung Sahere!
How can I assist?
_____________________________________________________________
  ME: _____________________________________________________________
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
_____________________________________________________________
  ME: _____________________________________________________________
Got it. I've added this task:
  [T][ ] borrow book
Now you have 2 tasks in the list.
_____________________________________________________________
  ME: _____________________________________________________________
Noted. I've removed this task:
  [T][ ] read book
Now you have 1 tasks in the list.
_____________________________________________________________
  ME: _____________________________________________________________
Here are the tasks in your list:
1.[T][ ] borrow book
_____________________________________________________________
  ME: _____________________________________________________________
Bye! Tung Tung Sagone!
_____________________________________________________________
```

## Test case: reject-empty-todo

### Aim

Verify that `todo` without a description reports the required error and continues accepting commands.

### Input

```text
todo
bye
```

### Expected output

```text
_____________________________________________________________
  _____          _    _ ______ _____  ______ 
 / ____|   /\   | |  | |  ____|  __ \|  ____|
| (___    /  \  | |__| | |__  | |__) | |__   
 \___ \  / /\ \ |  __  |  __| |  _  /|  __|  
 ____) |/ ____ \| |  | | |____| | \ \| |____ 
|_____//_/    \_\_|  |_|______|_|  \_\______|

Hello! Tung Tung Sahere!
How can I assist?
_____________________________________________________________
  ME: _____________________________________________________________
OOPS!!! There is nothing TODO.
_____________________________________________________________
  ME: _____________________________________________________________
Bye! Tung Tung Sagone!
_____________________________________________________________
```

## Test case: reject-unknown-command

### Aim

Verify that an unrecognised command reports the required error and does not add a task.

### Input

```text
blah
bye
```

### Expected output

```text
_____________________________________________________________
  _____          _    _ ______ _____  ______ 
 / ____|   /\   | |  | |  ____|  __ \|  ____|
| (___    /  \  | |__| | |__  | |__) | |__   
 \___ \  / /\ \ |  __  |  __| |  _  /|  __|  
 ____) |/ ____ \| |  | | |____| | \ \| |____ 
|_____//_/    \_\_|  |_|______|_|  \_\______|

Hello! Tung Tung Sahere!
How can I assist?
_____________________________________________________________
  ME: _____________________________________________________________
OOPS!!! IDK what u are on about :-(
_____________________________________________________________
  ME: _____________________________________________________________
Bye! Tung Tung Sagone!
_____________________________________________________________
```

## Test case: add-deadline

### Aim

Verify that a `deadline` command separates the description and `/by` value and displays the deadline task correctly.

### Input

```text
deadline return book /by 2019-10-15
bye
```

### Expected output

```text
_____________________________________________________________
  _____          _    _ ______ _____  ______ 
 / ____|   /\   | |  | |  ____|  __ \|  ____|
| (___    /  \  | |__| | |__  | |__) | |__   
 \___ \  / /\ \ |  __  |  __| |  _  /|  __|  
 ____) |/ ____ \| |  | | |____| | \ \| |____ 
|_____//_/    \_\_|  |_|______|_|  \_\______|

Hello! Tung Tung Sahere!
How can I assist?
_____________________________________________________________
  ME: _____________________________________________________________
Got it. I've added this task:
  [D][ ] return book (by: Oct 15 2019)
Now you have 1 tasks in the list.
_____________________________________________________________
  ME: _____________________________________________________________
Bye! Tung Tung Sagone!
_____________________________________________________________
```

## Test case: add-event

### Aim

Verify that an `event` command separates its description, `/from` value, and `/to` value.

### Input

```text
event project meeting /from 2019-10-15 /to 2019-10-16
bye
```

### Expected output

```text
_____________________________________________________________
  _____          _    _ ______ _____  ______ 
 / ____|   /\   | |  | |  ____|  __ \|  ____|
| (___    /  \  | |__| | |__  | |__) | |__   
 \___ \  / /\ \ |  __  |  __| |  _  /|  __|  
 ____) |/ ____ \| |  | | |____| | \ \| |____ 
|_____//_/    \_\_|  |_|______|_|  \_\|______|

Hello! Tung Tung Sahere!
How can I assist?
_____________________________________________________________
  ME: _____________________________________________________________
Got it. I've added this task:
  [E][ ] project meeting (from: Oct 15 2019 - to: Oct 16 2019)
Now you have 1 tasks in the list.
_____________________________________________________________
  ME: _____________________________________________________________
Bye! Tung Tung Sagone!
_____________________________________________________________
```

## Test case: mark-and-unmark-task

### Aim

Verify that `mark` and `unmark` update a task's completion status while continuing to accept commands.

### Input

```text
todo borrow book
mark 1
unmark 1
bye
```

### Expected output

```text
_____________________________________________________________
  _____          _    _ ______ _____  ______ 
 / ____|   /\   | |  | |  ____|  __ \|  ____|
| (___    /  \  | |__| | |__  | |__) | |__   
 \___ \  / /\ \ |  __  |  __| |  _  /|  __|  
 ____) |/ ____ \| |  | | |____| | \ \| |____ 
|_____//_/    \_\_|  |_|______|_|  \_\______|

Hello! Tung Tung Sahere!
How can I assist?
_____________________________________________________________
  ME: _____________________________________________________________
Got it. I've added this task:
  [T][ ] borrow book
Now you have 1 tasks in the list.
_____________________________________________________________
  ME: _____________________________________________________________
Nice! I've marked this task as done:
  [T][X] borrow book
_____________________________________________________________
  ME: _____________________________________________________________
OK, I've marked this task as not done yet:
  [T][ ] borrow book
_____________________________________________________________
  ME: _____________________________________________________________
Bye! Tung Tung Sagone!
_____________________________________________________________
```

### Expected output

```text
_____________________________________________________________
  _____          _    _ ______ _____  ______ 
 / ____|   /\   | |  | |  ____|  __ \|  ____|
| (___    /  \  | |__| | |__  | |__) | |__   
 \___ \  / /\ \ |  __  |  __| |  _  /|  __|  
 ____) |/ ____ \| |  | | |____| | \ \| |____ 
|_____//_/    \_\_|  |_|______|_|  \_\______|

Hello! Tung Tung Sahere!
How can I assist?
_____________________________________________________________
  ME: _____________________________________________________________
Got it. I've added this task:
  [E][ ] project meeting (from: Oct 15 2019 - to: Oct 16 2019)
Now you have 1 tasks in the list.
_____________________________________________________________
  ME: _____________________________________________________________
Bye! Tung Tung Sagone!
_____________________________________________________________
```
