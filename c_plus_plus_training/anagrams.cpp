#include <iostream>
#include <vector>
#include <unordered_map>
#include <algorithm>

using namespace std;

vector<string>
anagrams(vector<string> &strs) {
    vector<string> result;
    unordered_multimap<string, vector<string>> anagramHash;
    string sLocal;

	anagramHash.reserve(20);

	//cout << strs.size() << endl;
    for (string str: strs) {
        sLocal.assign(str);
        sort(sLocal.begin(), sLocal.end());
		//cout << "sLocal:" << sLocal << endl;
		auto it = anagramHash.find(sLocal);
		if (it != anagramHash.end()) {
			(it->second).push_back(str);
		} else {
			vector<string> lVector;
			lVector.push_back(str);
			anagramHash.emplace(sLocal, lVector);
		} 
			
    }

    for (unsigned i = 0; i < anagramHash.bucket_count(); i++) {
		//cout << "Bucket size:" << anagramHash.bucket_size(i) << endl;
        if (anagramHash.bucket_size(i) > 0) {
			for (auto it = anagramHash.begin(i); it != anagramHash.end(i); ++it) {
				//cout << "Item size:" << (it->second).size() << endl;
				if ((it->second).size() > 1) {
					for (auto str : (it->second)) {
						result.push_back(str);
					}
				}
            }
        }
    }

    return result;
}

int main(int argc, char *argv[]) {
    //string sArray[] = {"hello", "hello", "lehlo", "llohe", "name"};
    vector<string> sVector;

    for (unsigned i = 1; i < argc; i++) {
        //cout << argv[i] << endl;
        sVector.push_back(argv[i]);
    }

    vector<string> result = anagrams(sVector);

	cout << "Result size:" << result.size() << endl;
    for (auto &str : result) {
        cout << str << endl;
    }
    return 0;
}
