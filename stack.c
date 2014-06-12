/*
 * Generic implementation of the stack
 */

#include "stack.h"
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

#ifdef LINKED_LIST_STACK
static int linked_list_new_stack(stack **s)
{
	*s = (stack *)malloc(sizeof(stack));
    if (*s == NULL) {
        return ENOMEM;
    }
	(*s)->stack_ptr = NULL;
	(*s)->stack_size = 0;
}

static int linked_list_push(stack *s, item_type item)
{
	NODE *current_stack_ptr;
    void *stk_ptr;

	current_stack_ptr = s->stack_ptr;
	stk_ptr = malloc(sizeof(NODE));
    if (stk_ptr == NULL) {
        return ENOMEM;
    }
	s->stack_ptr->next = current_stack_ptr;
	s->stack_ptr->item = item;
	s->stack_size++;
    return 0;
}

static int linked_list_pop(stack *s, item_type *item)
{
	item_type ret_item;
	NODE *current_stack_ptr;

	if (s == NULL) {
		return EINVAL;
	}

	if (s->stack_size == 0) {
		return EINVAL;
	}
	*item = s->stack_ptr->item;
	current_stack_ptr = s->stack_ptr;
	s->stack_ptr = s->stack_ptr->next;
	s->stack_size -= 1;
	free(current_stack_ptr);
	return 0;
}
#endif

#ifdef ARRAY_STACK
static int array_new_stack(stack **s)
{
	*s = (stack *)malloc(sizeof(stack));
    if (*s == NULL) {
        return ENOMEM;
    }
    (*s)->allocated_ptr = malloc(DEFAULT_ARRAY_STACK_SIZE);
    if ((*s)->allocated_ptr) {
        return ENOMEM;
    }
	(*s)->stack_ptr = (item_type *)(*s)->allocated_ptr;
	(*s)->stack_size = 0;
    (*s)->allocated_size_bytes = DEFAULT_ARRAY_STACK_SIZE;
    return 0;
}

static int array_push(stack *s, item_type *item)
{
    unsigned int stack_size_bytes;
    void *stk_ptr;

    /*
     * Reallocate the STACK array if the size of stack grows
     * beyond the allocated size
     */
    stack_size_bytes = (s->stack_size * sizeof(item_type));
    if ((stack_size_bytes + sizeof(item_type)) > s->allocated_size_bytes) {
        stk_ptr = realloc(s->stack_ptr, stack_size_bytes << 1);
        if (stk_ptr == NULL) {
            return ENOMEM;
        } else {
            s->allocated_ptr = stk_ptr;
            s->allocated_size_bytes = stack_size_bytes;
            s->stack_ptr = (item_type *)stk_ptr + s->stack_size;
        }
    }

    /*
     * Executing the PUSH operation
     */
    (s->stack_ptr)++;
    memcpy(s->stack_ptr, item, sizeof(item_type));
    s->stack_size++;
    return 0;
}

static int array_pop(stack *s, item_type *item)
{
    unsigned int allocated_size_bytes;
	void *stk_ptr;

	if (s == NULL) {
		return EINVAL;
	}

	if (s->stack_size == 0) {
		return EINVAL;
	}

    *item = *(s->stack_ptr);
    s->stack_size -= 1;
    s->stack_ptr -= 1;

    /*
     * Reduce the size of the stack
     * if the stack occupancy is less than half
     */
    allocated_size_bytes = s->allocated_size_bytes;
    if ((s->stack_size * sizeof(item_type)) <
            allocated_size_bytes >> 1) {
        stk_ptr = realloc(s->allocated_ptr, allocated_size_bytes >> 1);
        if (stk_ptr != NULL) {
            s->allocated_ptr = stk_ptr;
            s->stack_ptr = (item_type *)stk_ptr + s->stack_size;
        }
    }

    return 0;
}
#endif

static bool stack_empty(const stack *s)
{
	if (s->stack_size != 0) {
		return false;
	} else {
		return true;
	}
}

static unsigned int stack_size(const stack *s)
{
	return s->stack_size;
}

STACK_API stack_api = {
#ifdef LINKED_LIST_STACK
     linked_list_new_stack,
     linked_list_pop,
     linked_list_push,
#endif
#ifdef ARRAY_STACK
     array_new_stack,
     array_pop,
     array_push,
#endif
     stack_empty,
     stack_size,
};

