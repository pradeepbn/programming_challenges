#include <iostream>
#include <vector>
#include <stack>

using namespace std;

struct TreeNode {
	int val;
	TreeNode *left;
	TreeNode *right;
	TreeNode(int x) : val(x), left(NULL), right(NULL) {}
};

void inOrderTraversal(TreeNode *root) {
	stack<TreeNode *> inOrderStack;
	TreeNode *tempNode;

	inOrderStack.push(root);

	while (!inOrderStack.empty()) {
		while (root->left) {
			inOrderStack.push(root->left);
			root = root->left;
		}
		tempNode = inOrderStack.top();
		inOrderStack.pop();
		cout << tempNode->val << endl;
		while (!tempNode->right && !inOrderStack.empty()) {
			tempNode = inOrderStack.top();
			inOrderStack.pop();
			cout << tempNode->val << endl;
		}
		
		if (tempNode->right) {
			root = tempNode->right;
			inOrderStack.push(root);
		}
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

int compare(int a, int b) {
	if (a < b) {
		return -1;
	} else if (a > b) {
		return 1;
	} else {
		return 0;
	}
}

void construct(TreeNode *&root, int val) {
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

int main(int argc, char *argv[]) {
	vector<int> array = {2, 1};//{4, 6, 7};//1, 2};//, 6, 5, 7, 8, 3};
	TreeNode *root = NULL;
	for (auto i : array) {
		construct(root, i);
	}

	//printTree(root);
	inOrderTraversal(root);
	return 0;
}
