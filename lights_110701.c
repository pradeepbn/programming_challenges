#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>

#define TRUE 1
#define FALSE 0
#define random_generator(x) ((x * x) + 1)

typedef unsigned int uint;
typedef int bool;

typedef struct node {
    uint p_factor;
    uint count;
    struct node *next;
} P_FACTOR_NODE;

typedef struct {
    P_FACTOR_NODE *head_factor_node;
} P_FACTOR_HANDLE;

/* Helper functions */
static bool is_prime(uint number)
{
    /* Rabin Miller's Primality test */
    uint num;
    uint s, a, d;
    uint i, n;
    uint total_tries = 1;
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

    while (total_tries <= 10) {
START_PRIME_INSPECTION:
        while ((a = (rand() % (number - 1))) < 2);
        i = 1;
        while ((i *= 2) < (s - 1)) {
            n = pow(a, i * d);
            if ((n % number == 1) || (n % number == -1)) {
                /* 
                 * Even if one of the iteration satisfies RM criteria,
                 * repeat for another value of a
                 */
                total_tries++;
                goto START_PRIME_INSPECTION;
            }
        }
        if ((n % number != 1) || (n % number != -1)) {
            failed_tries++;
        }
        total_tries++;
    }
    if ((((double)failed_tries / (double)total_tries) * 100) >= 75.0f) {
        return FALSE;
    } else {
        return TRUE;
    }
}

static inline void create_factor_node(P_FACTOR_NODE **node)
{
   *node = malloc(sizeof(P_FACTOR_NODE)); 
   (*node)->next = NULL;
   (*node)->count = 0;
}

static uint gcd(uint a, uint b)
{
    if (b == 0) {
        return a;
    } else {
        return (gcd(b, a % b));
    }
}

static uint get_factors(const uint number)
{
    /* This is a pollard Rho implementation for factorization */
    uint x, y;
    uint factor;
    time_t t;

    srand(t);

    x = rand() % number;
    y = x;
    do {
        x = random_generator(x) % number;
        y = random_generator(random_generator(y) % number) % number;
    } while ((factor = gcd(abs(y - x), number)) == 1);

    return factor;	
}

/* Class operations */
static void create_instance(P_FACTOR_HANDLE **handle)
{
    *handle = malloc(sizeof(P_FACTOR_HANDLE));
    (*handle)->head_factor_node = malloc(sizeof(P_FACTOR_NODE));
    (*handle)->head_factor_node->count = 0;
}

static void delete_instance(P_FACTOR_HANDLE **handle)
{
    /* Delete the Factor list */
    /* Delete the Factor handle */
}

int main(int argc, char *argv[])
{
    uint input_number;
    uint q_factor, p_factor;
    P_FACTOR_HANDLE *pfactor_handle;
    P_FACTOR_NODE *node;

    create_instance(&pfactor_handle);
    node = pfactor_handle->head_factor_node;
    scanf("%d", &input_number);
    q_factor = input_number;

    while (1) {
        p_factor = get_factors(q_factor);
        node->p_factor = p_factor;

        while (q_factor % p_factor == 0) {
            node->count++;
            q_factor /= p_factor;
            printf("Factor: %d\n", p_factor);
        }

        if (is_prime(q_factor)) {
            printf("Factor: %d\n", q_factor);
            break;
        } else {
            create_factor_node(&node->next);
        }
    }
    return 0;
}
