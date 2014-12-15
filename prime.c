#include <stdio.h>
#include <math.h>
#include <stdlib.h>

#define TRUE 1
#define FALSE 0

typedef unsigned int uint;
typedef int bool;

static bool is_prime(uint number)
{
    /* Rabin Miller's Primality test */
    uint num;
    uint s, a, d;
    uint i, n, p;
    uint total_tries = 0;
    uint failed_tries = 0;
    size_t t;

    srand(t);

    if (number % 2 == 0) {
        return FALSE;
    }

    d = number - 1;
    s = 0;
    while (d % 2 == 0) {
        s++;
        d /= 2;
    }

    printf("s=%d d=%d\n", s, d);
    while (total_tries <= ceil(log(number))) {
START_PRIME_INSPECTION:
        while ((a = (rand() % (number))) < 2);
        printf("a = %d\n", a);
        n = pow(a, d);
        if ((n % number == 1) || (n % number == number - 1)) {
            total_tries++;
            continue;
        }

        i = 0;
        p = pow(2, i);
        while (p <= (s - 1)) {
            n = pow(a, p);
            i++;
            p = pow(2, i);
            //n = n * n;
            if ((n % number == 1) || (n % number == number  - 1)) {
                /* 
                 * Even if one of the iteration satisfies RM criteria,
                 * repeat for another value of a
                 */
                total_tries++;
                printf("Primality test succeeded\n");
                break;//goto START_PRIME_INSPECTION;
            }
        }
        if ((n % number != 1) || (n % number != number - 1)) {
            failed_tries++;
        }
        total_tries++;
    }
    printf("Failed Tries:%d Total Tries: %d Avg:%f \n", failed_tries, total_tries,
                                    ((double)failed_tries / (double)total_tries) * 100);
    if ((((double)failed_tries / (double)total_tries) * 100) >= 75.0f) {
        return FALSE;
    } else {
        return TRUE;
    }
}

int main(int argc, char *argv[])
{
    uint input_number;

    scanf("%d", &input_number);
    printf("Prime:%s\n", is_prime(input_number) ? "TRUE" : "FALSE");
    return 0;
}

