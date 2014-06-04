#include <stdio.h>
#include <string.h>

int main(int argc, char *argv[])
{
    char *line_ptr = NULL;
    char *save_ptr, *ret_ptr;
    size_t n = 0;
    size_t size;
    size = getdelim(&line_ptr, &n, '\n', stdin);
    ret_ptr = strtok(line_ptr, (const char *) "\n");
    for (int i = 0; i < n; i++) {
        printf("%d ", ret_ptr[i]);
    }
    printf("\n%s", ret_ptr);
    return 0;
}
