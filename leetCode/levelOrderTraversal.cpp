#include <iostream>
#include <queue>
#include <vector>
using namespace std;

struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode(int x): val(x), right(NULL), left(NULL) {}
};

void printLevelOrder(TreeNode *root) {
    if (root == NULL) {
        return;
    }
    queue<TreeNode *> currentLevel, nextLevel;
    TreeNode *tempNode;
    
    currentLevel.push(root);
    while(!currentLevel.empty()) {
        tempNode = currentLevel.front();
        cout << tempNode->val << ",";
        currentLevel.pop();
        if (tempNode->left) {
            nextLevel.push(tempNode->left);
        }
        if (tempNode->right) {
            nextLevel.push(tempNode->right);
        }
        if (currentLevel.empty()) {
            cout << endl;
            swap(currentLevel, nextLevel);
        }
    }
    // STUB
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
    if (compare(root->val, val) < 0) {
        construct(root->right, val);
    } else {
        construct(root->left, val);
    }
}

int main() {
    // your code goes here
    vector<int> array = {4, 1, 2, 6, 5, 7};
    TreeNode *root = NULL;
    for (auto i : array) {
        construct(root, i);
    }

    printLevelOrder(root);
    return 0;
}
