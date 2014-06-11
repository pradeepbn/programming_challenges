/*
 * Generic implementation of the stack
 */

#include "stack.h"

void new_stack(stack **s)
{
	*s = (stack *)malloc(sizeof(stack));
	*s->stack_size = 0;
	*s->stack_ptr = NULL;
}

item_type pop(stack *s)
{
	item_type ret_item;
	NODE *current_stack_ptr;

	if (*s == NULL) {
		return -1;
	}

	if (*s->stack_size == 0) {
		return -1;
	}
	ret_item = *s->stack_ptr->item;
	current_stack_ptr = *s->stack_ptr;
	*s->stack_ptr = *s->stack_ptr->next;
	*s->stack_size--;
	free(current_stack_ptr);
	return ret_item;
}

void push(stack *s, item_type item)
{
	NODE *current_stack_ptr;

	current_stack_ptr = *s->stack_ptr;
	*s->stack_ptr = (NODE *) malloc(sizeof(NODE));
	*s->stack_ptr->next = current_stack_ptr;
	*s->stack_ptr->item = item;
	*s->stack_size++;
}

bool empty(const stack *s)
{
	if (*s->stack_size != 0) {
		return FALSE;
	} else {
		return TRUE;
	}
}

unsigned int size(const stack *s)
{
	return *s->stack_size;
}
