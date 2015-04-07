#include <iostream>
#include <vector>
#include <unordered_map>
#include <algorithm>

using namespace std;

vector<string>
anagrams(vector<string> &strs) {
    vector<string> result;
    unordered_multimap<string, unordered_multimap<string, string>> anagramHash;
    string sLocal;

    for (auto& str: strs) {
        sLocal.assign(str);
        sort(sLocal.begin(), sLocal.end());
        //cout << sLocal << endl;
		auto it = anagramHash.find(sLocal);
		if (it != unordered_multimap::end) {
			(it->second).insert(make_pair(sLocal, str));
		} else {
			anagramHash.emplace(sLocal, make_pair(sLocal, str));
		} 
			
    }

    //cout << anagramHash.bucket_count() << endl;
    //innerItfor (unsigned i = 0; i < anagramHash.bucket_count(); i++) {
    //innerIt    if (anagramHash.bucket_size(i) > 1) {
    //innerIt        cout << anagramHash.bucket_size(i) << endl;
    //innerIt        for (auto it = anagramHash.begin(i); it != anagramHash.end(i); ++it) {
	//innerIt			for (j = 0; j < (it->second).bucket_count(); j++) {
	//innerIt				if ((it->second).bucket_size(j) > 1) {
	//innerIt					for (auto innerIt = (it->second).begin(j); 
	//innerIt							innerIt != (it->second).end(j); innerIt) {
	//innerIt						result.push_back(innerIt->second);
	//innerIt					}
	//innerIt				}
	//innerIt			}
	//innerIt			}
    //innerIt            //cout << anagramHash.hash_function()(it->first) << ";";
    //innerIt            //cout << it->second << endl;
    //innerIt            //result.push_back(it->second);
    //innerIt        }
    //innerIt        cout << endl;
    //innerIt    }
    //innerIt}

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

    for (auto &str : result) {
        cout << str << endl;
    }
    return 0;
}
