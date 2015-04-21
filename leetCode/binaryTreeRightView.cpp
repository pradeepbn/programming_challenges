#include <iostream>
#include <vector>

using namespace std;

struct TreeNode {
     int val;
     TreeNode *left;
     TreeNode *right;
     TreeNode(int x) : val(x), left(NULL), right(NULL) {}
};

template<class T>
class Tree {
private:
	int compare(T a, T b) {
		if (a < b) {
			return -1;
		} else if (a > b) {
			return 1;
		} else {
			return 0;
		}
	}
public:
	void construct(TreeNode *&root, T val) {
		if (root == NULL) {
			root = new TreeNode(val);
			return;
		}
		if (compare(root->val, val) >= 0) {
			construct(root->left, val);
		} else {
			construct(root->right, val);
		}
	}

	void printTree(TreeNode *root) {
		if (root == NULL) {
			return;
		}
		printTree(root->left);
		cout << root->val << endl;
		printTree(root->right);
	}
};


int main(int argc, char *argv[]) {
	Tree<int> t;
	vector<int> array = {4, 1, 2, 6, 10, 11, 9, -1, 4, 5};	
	TreeNode *root = NULL;
	for (auto i : array) {
		t.construct(root, i);
	}

	t.printTree(root);
	return 0;
}
