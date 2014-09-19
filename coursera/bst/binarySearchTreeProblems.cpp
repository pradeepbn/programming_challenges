#include <iostream>
#include <queue>
using namespace std;

template <class T>
class Node {
     public:
          T item;
          Node *right;
          Node *left;
};

template <class T>
class BinaryTree {
     public:
          Node<T> *root;

};

template <class T>
class BinarySearchTree: public BinaryTree<T> {
     private:
          void computeSize(Node<T> *node);
          void swap(Node<T> **left, Node<T> **right);
          int max(int lhs, int rhs);
          unsigned int treeSize;
          queue<Node<T> *> bfsQueue;

     public:
          BinarySearchTree() {
               this->root = NULL;
          }

          void insert(Node<T> **currentNode, T item);
          void preOrderTraversal(Node<T> *node);
          void inOrderTraversal(Node<T> *node);
          void postOrderTraversal(Node<T> *node);
          unsigned int size(Node<T> *root) {
               treeSize = 0;
               computeSize(root);
               return treeSize;
          }
          void mirror(Node<T> **node);
          void bfs(Node<T> *node);
          int maxDepth(Node<T> *node);
          Node<T> *cloneTree(Node<T> *originalNode);
          void printTree();
          bool hasPathSum(Node<T> *node, int sum);
};
/*
+-------------------+
| PRIVATE FUNCTIONS |
+-------------------+
*/
template <class T>
void BinarySearchTree<T> :: computeSize(Node<T> *node) {
     if (node == NULL) {
        return;
     }        
     treeSize++;
     computeSize(node->left);
     computeSize(node->right);
}

template <class T>
void BinarySearchTree<T> :: swap(Node<T> **left, Node<T> **right) {
     Node<T> *tempNode;
     tempNode = *left;
     *left = *right;
     *right = tempNode;
}

template <class T>
int BinarySearchTree<T> :: max(int lhs, int rhs) {
    if (lhs > rhs) { return lhs; }
    else { return rhs; }
}
/*
+------------------+
| PUBLIC FUNCTIONS |
+------------------+
*/
template <class T>
void BinarySearchTree<T> :: insert(Node<T> **currentNode, T item) {
     if (*currentNode == NULL) {
          cout << "Inserting Item: "  << item <<  endl; 
          *currentNode = new Node<T>();
          (*currentNode)->item = item;
          (*currentNode)->left = NULL;
          (*currentNode)->right = NULL;
          return;
     }

     if (item < (*currentNode)->item) {
          insert(&((*currentNode)->left), item);
     } else {
          insert(&((*currentNode)->right), item);
     }
     return;
}

template <class T>
void BinarySearchTree<T> :: preOrderTraversal(Node<T> *node) {
     if (node == NULL) {
          return;
     }
     cout << node->item  << ", ";
     preOrderTraversal(node->left);
     preOrderTraversal(node->right);
}

template <class T>
void BinarySearchTree<T> :: inOrderTraversal(Node<T> *node) {
     if (node == NULL) {
          return;
     }
     inOrderTraversal(node->left);
     cout << node->item << ", ";
     inOrderTraversal(node->right);
}

template <class T>
void BinarySearchTree<T> :: postOrderTraversal(Node<T> *node) {
     if (node == NULL) {
          return;
     }
     postOrderTraversal(node->left);
     postOrderTraversal(node->right);
     cout << node->item << ", ";
}

template <class T>
void BinarySearchTree<T> :: mirror(Node<T> **node) {
     if ((*node) == NULL) {
          return;
     }

     swap(&(*node)->left, &(*node)->right);
     mirror(&(*node)->left);
     mirror(&(*node)->right);
}

template <class T>
void BinarySearchTree<T> :: bfs(Node<T> *node) {
     Node<T> *tempNode;
     bfsQueue.push(node);
     while (!bfsQueue.empty()) {
          tempNode = bfsQueue.front();
          bfsQueue.pop();
          if (tempNode == NULL) {
                continue;
          }

          cout << tempNode->item << ", ";
          bfsQueue.push(tempNode->left);
          bfsQueue.push(tempNode->right);
     }
}

template <class T>
Node<T> *BinarySearchTree<T> :: cloneTree(Node<T> *originalNode) {
    if (originalNode == NULL) {
        return originalNode;
    }

    Node<T> *node = new Node<T>();
    node->item = originalNode->item;
    node->left = cloneTree(originalNode->left);
    node->right = cloneTree(originalNode->right);
    return node;
}

template <class T>
int BinarySearchTree<T> :: maxDepth(Node<T> *node) {//, int size) {
    int l_size;
    int r_size;
    if (node == NULL) {
        return 0;
    }

    l_size = maxDepth(node->left);//, size);
    r_size = maxDepth(node->right);//, size);
    return (max(l_size, r_size) + 1);
}

template <class T>
bool BinarySearchTree<T> :: hasPathSum(Node<T> *node, int sum) {
    if (node == NULL) { return ((sum == 0) ? true : false); }
    sum -= node->item;
    return hasPathSum(node->left, sum) ||
            hasPathSum(node->right, sum);
}

int main(int argc, char *argv[]) {
     BinarySearchTree<int> *bst = new BinarySearchTree<int>();
     int a[] = {4,2,6,1,3,5,7,100,400};
     //int a[] = {4,2,6,1,3,5,7};//,100,400};

     for (int i: a) {
          bst->insert(&(bst->root), i);
     }
     cout << "Pre Order traversal" << endl;
     bst->preOrderTraversal(bst->root);
     cout << endl;
     cout << "In Order traversal" << endl;
     bst->inOrderTraversal(bst->root);
     cout << endl;
     cout << "Post Order traversal" << endl;
     bst->postOrderTraversal(bst->root);
     cout << endl;
     cout << "Tree size: " << bst->size(bst->root) << endl;
     //bst->mirror(&bst->root);
     //cout << "Mirrored Tree" << endl;
     //bst->inOrderTraversal(bst->root);
     //cout << endl;

     cout << "Breadth first search" << endl;
     bst->bfs(bst->root);
     cout << endl;
     cout << "Max Tree Depth: " << bst->maxDepth(bst->root) << endl;

     Node<int> *clonedTree = bst->cloneTree(bst->root);
     cout << "In Order traversal of the cloned tree" << endl;
     bst->inOrderTraversal(clonedTree);
     cout << endl;
     cout << "Cloned Tree max depth: " << bst->maxDepth(clonedTree) << endl;
     cout << "Has path: " << ((bst->hasPathSum(clonedTree, 8) == true) ? "True" : "False") << endl;
     return 0;
}
