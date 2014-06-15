#define __LINKED_LIST_H
#ifdef __LINKED_LIST_H
typedef item_type int;
typedef struct node NODE;
typedef linked_list_api {
    (int) (*ll_length) (NODE *head);
} LINKED_LIST_API;

extern LINKED_LIST_API list_api;

#define ll_length(head) \
    list_api.ll_length(head)

#endif
