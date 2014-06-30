#include <cstdlib>
#include <iostream>
#include <fstream>
using namespace std;

#define ARRAY_SIZE 10000

class quick_sort {
	public:
		unsigned int number_of_comparisons;
		quick_sort(): number_of_comparisons(0) { }
		virtual unsigned int partition(unsigned int *array, unsigned int length) {};
		template <class T>
		void swap(T &left, T &right);
		template <class T>
		void qsort(T *array, unsigned int length);
		template <class T>
		void print_array(T &array, unsigned int length);
		template  <class T>
		void data_fetch(T &array, unsigned int length, ifstream &in_file);
		template <class T>
		int data_fetch_random(T &array, unsigned int length);
};

class first_element_pivot : public quick_sort {
	public:
		//first_element_pivot(unsigned int init_comparisons):
		//		quick_sort(init_comparisons) { }
		unsigned int partition(unsigned int *array, unsigned int length);
		template <class T>
		unsigned int find_pivot_point(T &array, unsigned int length);
};

class last_element_pivot : public quick_sort {
	public:
		//first_element_pivot(unsigned int init_comparisons):
		//		quick_sort(init_comparisons) { }
		unsigned int partition(unsigned int *array, unsigned int length);
		template <class T>
		unsigned int find_pivot_point(T &array, unsigned int length);
};

class middle_element_pivot : public quick_sort {
	public:
		//first_element_pivot(unsigned int init_comparisons):
		//		quick_sort(init_comparisons) { }
		first_element_pivot f_pivot;
		last_element_pivot l_pivot;
		unsigned int partition(unsigned int *array, unsigned int length);
		template <class T>
		unsigned int find_pivot_point(T &array, unsigned int length);
};

template <class T>
void quick_sort :: swap(T &left, T &right)
{
	T temp;
	temp = left;
	left = right;
	right = temp;
}

template <class T>
void quick_sort :: print_array(T &array, unsigned int length)
{
	unsigned int i = 0;
	while (i < length) {
		cout << array[i] << " ";
		i++;
	}
	cout << endl;
}

template <class T>
void quick_sort :: data_fetch(T &array, unsigned int length, ifstream &in_file)
{
	char s[10];
 	for (unsigned int i = 0; i < length; i++) {
		in_file.getline(s, 10);  
		array[i] = stoi(s);
	}
}
template <class T>
int quick_sort :: data_fetch_random(T &array, unsigned int length)
{
	srand(time(NULL));
	if (length > ARRAY_SIZE) {
		return -1;
	}
	for (unsigned int i = 0; i < length; i++) {
		array[i] = rand() % length;
	}
}

template <class T>
void quick_sort :: qsort(T *array, unsigned int length)
{
	unsigned int partition_point;

	number_of_comparisons += length - 1;
	if (length <= 1) {
		return;
	}
	
	/*
	 * partition_point is the length of the left sub-array
	 */
	partition_point = partition((unsigned int *)array, length);
	/*
	 * Sort left
	 */
	cout << "Left sort: " << partition_point << endl;
	qsort(array, partition_point);
	/*
	 * Sort right
	 * Deduce the length of the right partition
	 * (length_array - partition_point)
	 */
	cout << "Right sort: " << partition_point << ": " << *(array + partition_point) << endl;
	print_array(array, 5);
	qsort((array + partition_point), (length - partition_point));
	return;
}

unsigned int first_element_pivot :: partition(unsigned int *array, unsigned int length)
{
	unsigned int i, j;
	unsigned int pivot_point;
	unsigned int pivot;

	pivot_point = find_pivot_point(array, length);
	pivot = array[pivot_point];
	print_array(array, length);
	if (length == 2) {
		if (array[pivot_point] < array[length - pivot_point]) {
			swap(array[pivot_point], array[length - pivot_point]);
			return 1;
		}
	}
	i = j = 1;
	cout << "In partition:- pivot: " << pivot << endl;
	while (j < length) {
		cout << array[j] << " " << pivot << endl;
		if (array[j] < pivot) { //use a generic comparison function
			swap(array[i], array[j]);
			i++;
			cout << "Swapped: " << i << " - ";
			print_array(array, length);
		}	
		j++;
	}
	swap(array[pivot_point], array[i - 1]);
	print_array(array, length);
	cout << "##########" << endl;
	/*
	 * Return the length of the left partition
	 */
	return i;
}

unsigned int last_element_pivot :: partition(unsigned int *array, unsigned int length)
{
	unsigned int i, j;
	unsigned int pivot_point;
	unsigned int pivot;

	pivot_point = find_pivot_point(array, length);
	pivot = array[pivot_point];
	i = j = 0;
	//print_array(array, length);
	//cout << "In partition:- pivot: " << pivot << endl;
	while (j < (length - 1)) {
		//cout << array[j] << " " << pivot << endl;
		if (array[j] < pivot) { //use a generic comparison function
			swap(array[i], array[j]);
			i++;
			//cout << "Swapped: " << i << " - ";
			//print_array(array, length);
		}	
		j++;
	}
	if (i < pivot_point) {
		swap(array[pivot_point], array[i]);
		//print_array(array, length);
		return ++i;
	} else {
		//print_array(array, length);
		return i;
	}
	//cout << "##########" << endl;
}

unsigned int middle_element_pivot :: partition(unsigned int *array, unsigned int length)
{
	unsigned int i, j;
	unsigned int pivot_point;
	unsigned int pivot;

	pivot_point = find_pivot_point(array, length);
	if (pivot_point == 0) {
		return f_pivot.partition(array, length);
	}

	if (pivot_point == (length - 1)) {
		return l_pivot.partition(array, length);
	}

	pivot = array[pivot_point];
	i = j = 0;
	//print_array(array, length);
	//cout << "In partition:- pivot: " << pivot << endl;
	while (j < (length - 1)) {
		//cout << array[j] << " " << pivot << endl;
		if (j == pivot_point) {
			j++;
			continue;
		}

		if (array[j] < pivot) { //use a generic comparison function
			swap(array[i], array[j]);
			i++;
			//cout << "Swapped: " << i << " - ";
			//print_array(array, length);
		}	
		j++;
	}
	if (i < pivot_point) {
		swap(array[pivot_point], array[i]);
		//print_array(array, length);
		return ++i;
	} else {
		//print_array(array, length);
		return i;
	}
}

template <class T>
unsigned int first_element_pivot :: find_pivot_point(T &array, unsigned int length)
{
	// 1st element pivot
	return 0;
	// Return last element as pivot
	//return (length - 1);
}

template <class T>
unsigned int last_element_pivot :: find_pivot_point(T &array, unsigned int length)
{
	// Return last element as pivot
	return (length - 1);
}

template <class T>
unsigned int middle_element_pivot :: find_pivot_point(T &array, unsigned int length)
{
	int first_element = array[0];
	int last_element  = array[length - 1];
	int middle_element_position;
	int middle_element;
	int temp_mid;
	if (length == 2) {
		if (array[0] <= array[1]) {
			return 0;
		} else {
			return 1;
		}
	}
	if (length % 2 != 0) {
		middle_element_position = ((length + 1) >> 1) - 1;
	} else {
		middle_element_position = (length >> 1) - 1;
	}
	middle_element = array[middle_element_position];

	/*
	 * Determine if the first element is the median
	 */
	if (((first_element >= middle_element) && 
		(first_element <= last_element)) ||
	   ((first_element <= middle_element) &&
		(first_element >= last_element))) {
		return 0;
	} 
	
	/*
	 * Determine if the middle element is the median
	 */
	if (((middle_element >= first_element) &&
	   (middle_element <= last_element)) ||
		((middle_element <= first_element) &&
		 (middle_element >= last_element))) {
			return middle_element_position;
	}

	/*
	 * Determine if the last element is the median
	 */
	if (((last_element >= first_element) &&
	   (last_element <= middle_element)) ||
		((last_element <= first_element) &&
		 (last_element >= middle_element))) {
		return (length - 1);
	}
}

int main(int argc, char *argv[])
{
	//quick_sort sort(0);// = new sort();
	first_element_pivot first_sort;
	last_element_pivot last_sort;
	middle_element_pivot middle_sort;
	int *array;
	int array1[5] {2, 5, 4, 3, 1};
	//int array1[2] {5, 4};//, 1, 3, 2};
	ifstream in_file;
	char s[10];
	int temp;

	array = (int *)malloc(sizeof(int) * ARRAY_SIZE);
	in_file.open("QuickSort.txt", ifstream::in);
	first_sort.data_fetch(array, ARRAY_SIZE, in_file);
	//first_sort.data_fetch_random(array, ARRAY_SIZE);
	try {
		first_sort.qsort(array1, 5);
	} catch (int x) {
		cout << "Error in QSORT" << endl;
		return -1;
	}
	//first_sort.print_array(array, ARRAY_SIZE);
	cout << "First Number of comparisons: " << first_sort.number_of_comparisons << endl;
	in_file.close();

	in_file.open("QuickSort.txt", ifstream::in);
	//last_sort.data_fetch_random(array, ARRAY_SIZE);
	last_sort.data_fetch(array, ARRAY_SIZE, in_file);
	//last_sort.print_array(array, ARRAY_SIZE);
	try {
		//last_sort.qsort(array, ARRAY_SIZE);
	} catch (int x) {
		cout << "Error in QSort" << endl;
	}

	//cout << "Last Number of comparisons: " << last_sort.number_of_comparisons << endl;

	//middle_sort. print_array(array1, 5);
	//last_sort.print_array(array, ARRAY_SIZE);
	in_file.close();

	in_file.open("QuickSort.txt", ifstream::in);
	middle_sort.data_fetch(array, ARRAY_SIZE, in_file);
	try {
		//middle_sort.qsort(array, ARRAY_SIZE);
	} catch (int x) {
		cout << "Error in QSort" << endl;
	}

	//cout << "Middle Number of comparisons: " << middle_sort.number_of_comparisons << endl;
	in_file.close();
	//middle_sort. print_array(array, ARRAY_SIZE);
	//cout << endl;
	return 0;
}
