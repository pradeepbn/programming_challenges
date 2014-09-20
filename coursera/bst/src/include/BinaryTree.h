#include <queue>

template <class T>
class Node {
     public:
          T item;
          Node *right;
          Node *left;
};

template <class T>
class BinaryTree {
	 protected:
          void computeSize(Node<T> *node);
          void swap(Node<T> **left, Node<T> **right);
          int max(int lhs, int rhs);
          queue<Node<T> *> bfsQueue;
     public:
          Node<T> *root;
          BinaryTree() {
				root = NULL;
          }
          unsigned int size(Node<T> *root);
          void preOrderTraversal(Node<T> *node);
          void inOrderTraversal(Node<T> *node);
          void postOrderTraversal(Node<T> *node);
          void mirror(Node<T> **node);
          void bfs(Node<T> *node);
          int maxDepth(Node<T> *node);
          Node<T> *cloneTree(Node<T> *originalNode);
          void printTree();
          bool hasPathSum(Node<T> *node, int sum);
};
