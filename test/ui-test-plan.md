# Console UI test plan

This file is the source of truth for Tung Tung's console UI tests. Each case runs in a fresh program session; include `bye` in every input block so the chatbot exits cleanly.

## Program setup

- Java version: 25
- Source directory: `src/main/java`
- Main class: `TungTung`

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
Replace this block with the complete expected console output for this session before running the test.
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
Replace this block with the complete expected console output for this session before running the test.
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
Replace this block with the complete expected console output for this session before running the test.
```

## Test case: add-deadline

### Aim

Verify that a `deadline` command separates the description and `/by` value and displays the deadline task correctly.

### Input

```text
deadline return book /by Sunday
bye
```

### Expected output

```text
Replace this block with the complete expected console output for this session before running the test.
```

## Test case: add-event

### Aim

Verify that an `event` command separates its description, `/from` value, and `/to` value.

### Input

```text
event project meeting /from Mon 2pm /to 4pm
bye
```

### Expected output

```text
Replace this block with the complete expected console output for this session before running the test.
```
