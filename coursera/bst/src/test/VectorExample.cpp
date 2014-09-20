#include <iostream>
#include <vector>
using namespace std;

int main(int argc, char **argv) {
	vector<int> array;
	array.reserve(20);
	cout << array.capacity() << endl;
	array.resize(10);
	array.push_back(10);
	cout << array.size() << endl;
	cout << array.capacity() << endl;
	return 0;
}
