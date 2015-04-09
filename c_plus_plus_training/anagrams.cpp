#include <iostream>
#include <vector>
#include <unordered_map>
#include <algorithm>
#include <cstring>

using namespace std;

#define RADIX 256


char* bucketSort(string str) {
	int rCount[RADIX + 1] = {0};
	int length = str.length();

	char *aux = (char *)malloc(length + 1);

	for (auto &chr : str) {
		rCount[((int) chr) + 1]++;
	}

	for (int i = 0; i < RADIX; i++) {
		rCount[i + 1] += rCount[i];
	}

	for (auto &chr : str) {
		aux[rCount[chr]++] = chr;
	}

	aux[length] = '\0';

	return aux;
}

vector<string>
anagrams(vector<string> &strs) {
	unordered_multimap<string, vector<string>> anagramHash;
	string sLocal;
	vector<string> result;

	anagramHash.reserve(20);
	
	if (strs.size() == 1) {
		return strs;
	}

	for (string str: strs) {
		//sLocal.assign(str);
		//sort(sLocal.begin(), sLocal.end());
		string sLocal;
		if (str.length() > 1) {
			sLocal = string(bucketSort(str));
		} else {
			sLocal.assign(str);
		}

		auto it = anagramHash.find(sLocal);
		if (it != anagramHash.end()) {
			if ((it->second).size() == 1) {
				result.push_back((it->second).back());
			}
			(it->second).push_back(str);
			result.push_back(str);
		} else {
			vector<string> lVector;
			lVector.push_back(str);
			anagramHash.emplace(sLocal, lVector);
		}

	}

	return result;
} 


int main(int argc, char *argv[]) {
    vector<string> sVector;

    for (unsigned i = 1; i < argc; i++) {
        sVector.push_back(argv[i]);
    }

	string t;
	//while (cin >> t) {
	//	cout << t << endl;
	//}

    vector<string> result = anagrams(sVector);

	cout << "Result size:" << result.size() << endl;
    for (auto &str : result) {
        cout << str << endl;
    }
    return 0;
}
