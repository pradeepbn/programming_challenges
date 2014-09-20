#include <BinaryTree.h>
using namespace std;

/*
+---------------------+                            
| PROTECTED FUNCTIONS |                            
+---------------------+                            
*/
template <class T>
void BinaryTree<T> :: swap(Node<T> **left, Node<T> **right) {
     Node<T> *tempNode;
     tempNode = *left;
     *left = *right;
     *right = tempNode;
}

template <class T>
int BinaryTree<T> :: max(int lhs, int rhs) {
    if (lhs > rhs) { return lhs; }
    else { return rhs; }
}

/*
+------------------+
| PUBLIC FUNCTIONS |
+------------------+
*/
template <class T>
unsigned int BinaryTree<T> :: size(Node<T> *node) {
     if (node == NULL) {
        return 0;
     }        
     return (size(node->left) + size(node->right) + 1);
}

template <class T>
void BinaryTree<T> :: preOrderTraversal(Node<T> *node) {
     if (node == NULL) {
          return;
     }
     cout << node->item  << ", ";
     preOrderTraversal(node->left);
     preOrderTraversal(node->right);
}

template <class T>
void BinaryTree<T> :: inOrderTraversal(Node<T> *node) {
     if (node == NULL) {
          return;
     }
     inOrderTraversal(node->left);
     cout << node->item << ", ";
     inOrderTraversal(node->right);
}

template <class T>
void BinaryTree<T> :: postOrderTraversal(Node<T> *node) {
     if (node == NULL) {
          return;
     }
     postOrderTraversal(node->left);
     postOrderTraversal(node->right);
     cout << node->item << ", ";
}

template <class T>
void BinaryTree<T> :: mirror(Node<T> **node) {
     if ((*node) == NULL) {
          return;
     }

     swap(&(*node)->left, &(*node)->right);
     mirror(&(*node)->left);
     mirror(&(*node)->right);
}

template <class T>
void BinaryTree<T> :: bfs(Node<T> *node) {
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
Node<T> *BinaryTree<T> :: cloneTree(Node<T> *originalNode) {
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
int BinaryTree<T> :: maxDepth(Node<T> *node) {
    int l_size;
    int r_size;
    if (node == NULL) {
        return 0;
    }

    l_size = maxDepth(node->left);
    r_size = maxDepth(node->right);
    return (max(l_size, r_size) + 1);
}

template <class T>
bool BinaryTree<T> :: hasPathSum(Node<T> *node, int sum) {
    if (node == NULL) { return ((sum == 0) ? true : false); }
    sum -= node->item;
    return hasPathSum(node->left, sum) ||
            hasPathSum(node->right, sum);
}
