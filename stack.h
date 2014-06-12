/*
 * Generic Stack header file
 */
#include <stdbool.h>

#define DEFAULT_ARRAY_STACK_SIZE 4096
#define ARRAY_STACK
#undef LINKED_LIST_STACK

typedef int item_type;

typedef struct node {
	item_type item;
	struct node *next;
} NODE;

#ifdef LINKED_LIST_STACK
typedef struct stack {
	NODE *stack_ptr;
	unsigned int stack_size;
} stack;
#endif

#ifdef ARRAY_STACK
typedef struct stack {
    void *allocated_ptr;
    item_type *stack_ptr;
    /* Number of item_types */
    unsigned int stack_size;
    unsigned int allocated_size_bytes;
} stack;
#endif

typedef stack_api {
    (int) (*_new_stack(stack **s));
    (item_type) (*_pop(stack *s));
    (int) (*_push(stack *s, item_type item));
    (bool) (*_empty(const stack *s));
    (unsigned int) (*_size(const stack *s));
} STACK_API;

extern STACK_API stack_api;

#define new_stack(n_stack_ptr) \
    stack_api._new_stack(n_stack_ptr);

#define push(stack_ptr, element) \
    stack_api._push(stack_ptr, element);

#define pop(stack_ptr) \
    stack_api._pop(stack_ptr);

#define empty(stack_ptr) \
    stack_api._empty(stack_ptr);

#define size(stack_ptr) \
    stack_api._size(stack_ptr);


