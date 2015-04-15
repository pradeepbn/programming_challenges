#include <iostream>
#include <math.h>
#include <string>
#include <vector>
using namespace std;
long sieve(long n, long* primes) 
{
    if (n<2) return 0; 

    const char blank = 0;
    const char marked = 1;

    char* theSieve;

    theSieve = new char[n+1];

    for (long k=2; k<=(n); k++)
        theSieve[k] = blank;

    long idx = 0;

    for (long k=2; k<=(n); k++)
    {
        if (theSieve[k]==blank)
        {
            theSieve[k] = marked;
            primes[idx] = k;
            idx++;

            for(long d=2*k; d<=n; d+=k)
                theSieve[d] = marked;
        }
    }
    delete[] theSieve;
    return idx;
}

//-----------------------------------------------------------------------------

const long N = 10000000;
const long TABLE_SIZE = 800000;

int main()
{
    long* primes;
    primes = new long[500];
    long np = sieve(30 * 30, primes);

    std::cout << "Found " << np << " prime numbers" << std::endl;
    std::cout << "The largest prime number is " << primes[np-1] << std::endl;

    vector<string> strs = {"ehllo", "hello", "lehlo", "man" ,"nam", "anme", "name"};
    int i = 0;
    const char *lChar;
    long hash = 1;

    for (auto str: strs) {
        lChar = str.c_str();
        cout << str << ":";
        cout << str.length();
        i = 0;
        hash = 1;
        while (i != str.length()) {
            hash *= lChar[i] * primes[lChar[i] - 'a'] ;
            i++;
        }
        //hash %= 9999991;
        cout << hash << endl;
    }

    delete[] primes;

    return 0;
}
