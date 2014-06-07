#include <stdio.h>

int power_of_num(int num, int exp)
{
    if (exp == 1) {
        return num;
    } else {
        num = num * power_of_num(num, exp / 2);
        return num;
    }
}

int main(int argc, char *argv[])
{
    int number, exponent, result;

    printf("Enter the number:");
    scanf("%d", &number);

    printf("Enter the exponent:");
    scanf("%d", &exponent);
    printf("\n");

    if (exponent % 2 == 0) {
        result = power_of_num(number, exponent);
    } else {
        result = number * power_of_num(number, exponent - 1);
    }

    printf("Exponential result:%d\n", result);
    return 0;
}

