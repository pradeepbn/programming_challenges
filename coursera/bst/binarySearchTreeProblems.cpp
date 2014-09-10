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

//template <class T> class queue;

template <class T>
class BinarySearchTree: public BinaryTree<T> {
	private:
		void computeSize(Node<T> *node);
		void swap(Node<T> **left, Node<T> **right);
		unsigned int treeSize;
		queue<Node> bfsQueue;// = new queue<T>();
		
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
		void printTree();
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
		tempNode = bfsQueue.pop();	
		if (tempNode == NULL) {
		 	continue;
		}

		cout << tempNode->item << ", ";
		bfsQueue.push(tempNode->left);
		bfsQueue.push(tempNode->right);
	}
}

int main(int argc, char *argv[]) {
	BinarySearchTree<int> *bst = new BinarySearchTree<int>();
	int a[] = {4,2,6,1,3,5,7,100,400};

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
	bst->mirror(&bst->root);
	cout << "Mirrored Tree" << endl;
	bst->inOrderTraversal(bst->root);
	cout << endl;

	cout << "Breadth first search" << endl;
	bst->bfs(bst->root);
	cout << endl;
	return 0;
}
