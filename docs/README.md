# TungTung User Guide

// Update the title above to match the actual product name

// Product screenshot goes here

// Product intro goes here

## Adding deadlines

// Describe the action and its outcome.

// Give examples of usage

Example: `keyword (optional arguments)`

// A description of the expected outcome goes here

```
expected output
```

## Sorting tasks

Sort dated tasks chronologically with:

```text
sort by deadline
```

Deadlines use their deadline date and events use their start date. Todos, which
have no date, are placed at the end. Tasks with the same date keep their
existing order.

```text
Here are your tasks sorted by deadline:
1.[E][ ] project meeting (from: Sep 10 2026 - to: Sep 11 2026)
2.[D][ ] return book (by: Sep 10 2026)
3.[T][ ] buy groceries
```

The reordered task list is saved using the existing storage format. Future
`list`, `find`, `mark`, and `delete` commands use the sorted positions.

## Feature ABC

// Feature details


## Feature XYZ

// Feature details

## Search result numbers

`find KEYWORD` shows matching tasks with their positions in the full task list.
Use those same numbers with `mark`, `unmark`, and `delete`. Numbers can change
after deleting or sorting tasks; run `list` or `find` again to see the current positions.

## Storage errors

If a command cannot be saved, its changes are undone. If the console cannot load
the saved file, it exits without changing it. Back up and repair `data/tungtung.txt`
(or restore a valid copy) before restarting.

If another copy of TungTung (or an editor) changes the saved file, further saves
from the older session are rejected. Restart that session to load the latest data.
The adjacent `.lock` file coordinates saves and should be left in place.

Event dates require exactly one `/from` followed by exactly one `/to`.
Descriptions cannot contain ` | ` or end with ` |`; commands must be on one line.
If the GUI cannot load saved tasks, it shows an error and blocks task changes until
the file is repaired and the application is restarted.
