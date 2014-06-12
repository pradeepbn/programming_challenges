#include <stdio.h>
#include "stack.h"

int main(int argc, char *argv[])
{
    stack *s;

    new_stack(&s);
    push(s, 10);
    //push(s, 11);
    //printf("Item:%d,",  pop(s));
    //printf("Empty?%s,", empty(s)? "Yes": "No");
    //printf("Size:%d", size(s));
    printf("Item:%d, Empty?%s, size:%d\n", size(s), empty(s)? "Yes": "No", pop(s));
    printf("Item:%d, Empty?%s, size:%d\n", size(s), empty(s)? "Yes": "No", pop(s));
    //printf("Item:%d,",  pop(s));
    //printf("Empty?%s,", empty(s)? "Yes": "No");
    //printf("Size:%d", size(s));
    return 0;
}
