#define __SINGLE_LINKED_LIST_H
#ifdef __SINGLE_LINKED_LIST_H

typedef struct node {
    item_type element;
    struct node *next;
};

int ll_length(NODE *head);

#endif
