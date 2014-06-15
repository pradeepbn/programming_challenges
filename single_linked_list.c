#include "linked_list.h"
#include <assert.h>

typedef struct node {
    item_type item;
    NODE *next;
} NODE;

void setup_sig
