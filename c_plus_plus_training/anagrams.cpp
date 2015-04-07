#include <iostream>
#include <vector>
#include <unordered_map>
#include <algorithm>

using namespace std;

vector<string>
anagrams(vector<string> &strs) {
    vector<string> result;
    unordered_multimap<string, string> anagramHash;
    string sLocal;

    for (auto& str: strs) {
        sLocal.assign(str);
        sort(sLocal.begin(), sLocal.end());
        cout << sLocal << endl;
        anagramHash.emplace(sLocal, str);
    }

    cout << anagramHash.bucket_count() << endl;
    for (unsigned i = 0; i < anagramHash.bucket_count(); i++) {
        if (anagramHash.bucket_size(i) > 1) {
            cout << anagramHash.bucket_size(i) << endl;
            for (auto it = anagramHash.begin(i); it != anagramHash.end(i); ++it) {
                cout << anagramHash.hash_function()(it->first) << ";";
                cout << it->second << endl;
                result.push_back(it->second);
            }
            cout << endl;
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

    for (auto &str : result) {
        cout << str << endl;
    }
    return 0;
}
