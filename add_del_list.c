#include <stdio.h>
#include <stdlib.h>

typedef struct list {
    int data;
    struct list *next;
} list;

list *head_list = NULL;

/* Insert to the head of the list */
void insert_list(list **l, const int data)
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

int main(int argc, char *argv[])
{
    int i;
    list *l;

    l = head_list;
    for (i = 0; i < 100; i++) {
        insert_list(&l ,i);
        head_list = l;
    }


    /* print the members of the list */
    l = head_list;
    while (l != NULL)
    {
        printf("%d,", l->data);
        l = l->next;
    }
    printf("\n");
    return 0;
}
