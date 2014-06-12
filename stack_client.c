#include <stdio.h>
#include "stack.h"

int main(int argc, char *argv[])
{
    stack *s;
	item_type item;
	item = 10;

    if (new_stack(&s) < 0 ) {
		printf("Error in initializing the stack\n");
		return 0;
	}
    push(s, &item);
    printf("Item:%d, Empty?%s, size:%d\n", size(s), empty(s)? "Yes": "No", item);
	if (pop(s, &item) < 0) {
		printf("Invalid pop access\n");
		return 0;
	}

    printf("Item:%d, Empty?%s, size:%d\n", size(s), empty(s)? "Yes": "No", item);

	if (pop(s, &item) ) {
		printf("Invalid pop access\n");
		return 0;
	}
    printf("Item:%d, Empty?%s, size:%d\n", size(s), empty(s)? "Yes": "No", item);
    return 0;
}
