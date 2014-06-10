#include <iostream>
#include <vector>
#include <fstream>
#include <string.h>

using namespace std;
static unsigned int total_inversions = 0;
#define INPUT_ARRAY_SIZE 100000

void swap(int *a, int *b)
{
    int temp;
    temp = *a;
    *a = *b;
    *b = temp;
}

int *merge(int *left, int *right, int size)
{
    int i = 0, j;
    int l_count, r_count;
    int left_mid, right_mid;
    int *result = (int *)malloc(size * sizeof(int));

    if (size % 2 == 0) {
        left_mid = right_mid = size / 2;
    } else {
        left_mid = size / 2;
        right_mid = size /2 + 1;
    }

    l_count = r_count = 0;
    while (i < size) {
        if (*left < *right) {
            result[i] = *left;
            left++;
            l_count++;
        } else {
            result[i] = *right;
            total_inversions += left_mid - l_count;
            right++;
            r_count++;

        }
        i++;
        if (l_count == left_mid || r_count == right_mid) {
            break;
        }
    }
    if (i != size) {
        if (l_count < left_mid) {
            for (j = l_count; j < left_mid; j++) {
                result[i] = *left;
                left++;
                l_count++;
                i++;
            }
        } else {
            for (j = r_count; j < right_mid; j++) {
                result[i] = *right;
                right++;
                r_count++;
                i++;
            }
        }
    }
    return result;
}

int *merge_sort(int *my_array, int size)
{
    int *left, *right;
    int mid;

    if (size <= 1){
        return my_array;
    }

    mid = size / 2;
    left = merge_sort(my_array, mid);
    if (size % 2 != 0) {
        right = merge_sort((my_array + mid), mid + 1);
    } else {
        right = merge_sort((my_array + mid), mid);
    }

    return merge(left, right, size);
}

int main(int argc, char *argv[])
{
    int my_int_array[] = {1, 3, 4, 5, 6, 2};
    int *result_array;
    vector<int> fifth(my_int_array, my_int_array + sizeof(my_int_array) / sizeof(int));
    ifstream fp;
    char **line_ptr;
    int i = 0;
    char string[256];
    int f_int_array[INPUT_ARRAY_SIZE];

    fp.open("IntegerArrayAssignment1.txt");

    while (fp.getline(string, 256, '\n')) {
        f_int_array[i] = stoi(string);
        i++;
    }
    fp.close();

    for (unsigned int i = 0; i < fifth.size(); i++) {
        cout << fifth.at(i) << " ";
    }
    result_array = merge_sort(f_int_array, sizeof(f_int_array) / sizeof(int));
    for (unsigned int i = 0; i < sizeof(f_int_array) / sizeof(int); i++) {
        cout << result_array[i] << " ";
    }

    cout << "Total inversions:"<< total_inversions << endl;
    cout << endl;
}
