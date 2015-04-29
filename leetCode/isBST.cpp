#include <iostream>
#include <tuple>
//#include <functional>
#include <vector>
#include <stack>
#include <algorithm>
#include <climits>

using namespace std;

struct TreeNode {
	int val;
	TreeNode *left;
	TreeNode *right;
	TreeNode(int x) : left(NULL), right(NULL), val(x) {}
};

int compare(int a, int b) {
	if (a < b) {
		return -1;
	} else if (a > b) {
		return 1;
	} else {
		return 0;
	}
}

bool notBst = false;


tuple<int, int> *isBst(TreeNode *root) {
	if (root == NULL) {
		return NULL;
	}

	tuple<int, int> *lTuple = isBst(root->left);
	tuple<int, int> *rTuple = isBst(root->right);
	vector<int> array;
	if (lTuple) {
		array.push_back(get<0>(*lTuple));
		array.push_back(get<1>(*lTuple));
	}
	if (rTuple) {
		array.push_back(get<0>(*rTuple));
		array.push_back(get<1>(*rTuple));
	}
	array.push_back(root->val);

	if (lTuple && compare(root->val, get<1>(*lTuple)) <= 0) {
		notBst = true;
		cout << "Not BST" << endl;
	}

	if (rTuple && compare(root->val, get<0>(*rTuple)) > 0) {
		notBst = true;
		cout << root->val << "," <<  get<0>(*rTuple) << endl;
		cout << "Not BST 1" << endl;
	}

	auto result = minmax_element(array.begin(), array.end());
	int min = array[result.first - array.begin()];
	int max = array[result.second - array.begin()];
	return  new tuple<int, int>(make_tuple(min, max));
}

bool isBstRecur(TreeNode *root, int minVal, int maxVal) {
	if (root == NULL) {
		return true;
	}

	if (minVal == maxVal) {
		return false;
	}
	if (root->val != INT_MIN &&
			root->val != INT_MAX) {
		cout << root->val << endl;
		if (root->val <= minVal ||
				root->val >= maxVal) {
			return false;
		}
	} else {
		switch (root->val) {
			case INT_MAX:
				if (minVal != INT_MIN) {
					return false;
				}
				break;
			case INT_MIN:
				if (maxVal != INT_MAX) {
					return false;
				}
				break;
			default:
				break;
		}
	}

	minVal = min(root->val, minVal);
	maxVal = max(root->val, maxVal);
	
	bool lAns = isBstRecur(root->left, minVal, root->val);
	bool rAns = isBstRecur(root->right, root->val, maxVal);
	return lAns & rAns;
}

bool isValidBST(struct TreeNode *root) {
	if (root == NULL) {
		return NULL;
	}

	return isBstRecur(root, INT_MAX, INT_MAX);
}

class BSTIterator {
private:
	stack<TreeNode *> iterStack;
	void updateStack(TreeNode *root) {
		if (root == NULL) {
			return;
		}
		iterStack.push(root);
		updateStack(root->left);
	}
public:
	BSTIterator(TreeNode *root) {
		if (root == NULL) {
			return;
		}
		updateStack(root);
	}

	bool hasNext() {
		return !iterStack.empty();
	}

	int next() {
		TreeNode *ret = iterStack.top();
		iterStack.pop();
		updateStack(ret->right);
		return ret->val;
	}
};


void construct(TreeNode *&root, int val) {
	if (root == NULL) {
		root = new TreeNode(val);
		return;
	}

	if (compare(root->val, val) > 0) {
		construct(root->left, val);
	} else {
		construct(root->right, val);
	}
}

int main(int argc, char *argv[]) {
	//tuple<int, int> tp;

	/*
	tp = make_tuple(10,20);
	for (int i = 0; i <= 1; i++) {
		cout << get<0>(tp) << endl;
		cout << get<1>(tp) << endl;
	}*/
	TreeNode *root = NULL;
	//vector<int> array = {4, 1, 2, 3, 6, 5, 7};
	vector<int> array = {INT_MIN, INT_MIN};

	for (auto i : array) {
		construct(root, i);
	}

	/*
	BSTIterator iter = BSTIterator(root);

	while (iter.hasNext()) {
		cout << iter.next() << endl;
	}*/

	//auto result = minmax_element(array.begin(), array.end());
	//cout << array[result.first - array.begin()]<< endl;
	//cout << array[result.second - array.begin()] << endl;
	//tuple<int, int> *minMax = isBst(root);
	cout << "Is BST:" << isValidBST(root) << endl;
	//cout << INT_MIN << ";" << INT_MAX << endl;
	//cout << "Min:" << get<0>(*minMax) << endl;
	//cout << "Max:" << get<1>(*minMax) << endl;

	return 0;
}
