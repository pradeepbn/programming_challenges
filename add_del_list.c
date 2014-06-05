#include <stdio.h>
#include <stdlib.h>
#include <time.h>

typedef int item_type;
typedef struct list {
    item_type data;
    struct list *next;
} list;

list *head_list = NULL;

/* Insert to the head of the list */
void insert_list(list **l, const item_type data)
{
    list *temp;

    if (*l == NULL) {
        *l = (list *)malloc(sizeof(list));
        (*l)->next = NULL;
    } else {
        temp = (list *)malloc(sizeof(list));
        temp->next = *l;
        *l = temp;
    }
    (*l)->data = data;
}

list *search_list(list *head_list, const item_type f_data) 
{
    list *iter;
    if (head_list == NULL) {
        return NULL;
    }

    iter = head_list;

    if (iter->data == f_data) {
        return iter;
    } else {
        return search_list(iter->next, f_data);
    }

}

void print_list(list *head_list)
{
    while (head_list != NULL) {
        printf("%d,", head_list->data);
        head_list = head_list->next;
    }
    printf("\n");
}

list *find_predecessor_list(list *head_list, list *f_list)
{
    if (head_list == NULL){
        return NULL;
    }

    if (head_list->next == f_list) {
        return head_list;
    } else {
        return find_predecessor_list(head_list->next, f_list);
    }
}

int main(int argc, char *argv[])
{
    int i;
    list *l, *pred_list;
    time_t t;
    int rand_val;

    srand((unsigned) time(&t));

    /* Inserting item in a single linked list
     * O(1) to the head
     */
    l = head_list;
    for (i = 0; i < 100; i++) {
        insert_list(&l ,i);
        head_list = l;
    }


    /* print the members of the list */
    print_list(head_list);

    /* Searching item through a singly linked list */
    for (i = 0; i < 101; i++) {
        if ((l = search_list(head_list, i)) == NULL) {
            printf("Item not found: %d\n", i);
        } else {
            printf("Found item: %d\n", l->data);
        }
    }

    /* Deleting an item from the linked list */
    rand_val = rand() % 100;
    l = search_list(head_list, rand_val);
    printf("Rand data found: %d; Rand value:%d\n", l->data, rand_val);
    pred_list = find_predecessor_list(head_list, l);
    pred_list->next = l->next;
    free(l);

    print_list(head_list);

    return 0;
}
