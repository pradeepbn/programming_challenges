/*
 * Generic Stack header file
 */

typedef item_type int;

typedef struct stack {
	NODE *stack_ptr;
	unsigned int stack_size;
} stack;

typedef struct node {
	item_type item;
	NODE *next;
} NODE;
