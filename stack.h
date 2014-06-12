/*
 * Generic Stack header file
 */
#include <stdbool.h>

typedef int item_type;

typedef struct node {
	item_type item;
	struct node *next;
} NODE;

typedef struct stack {
	NODE *stack_ptr;
	unsigned int stack_size;
} stack;

void new_stack(stack **s);
item_type pop(stack *s);
void push(stack *s, item_type item);
bool empty(const stack *s);
unsigned int size(const stack *s);
