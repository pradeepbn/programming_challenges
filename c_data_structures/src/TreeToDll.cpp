#include <iostream>
#include <vector>
#include <cstring>

using namespace std;

template <class T>
class Node {
	public:
		T item;
		Node<T> *left;
		Node<T> *right;
};

template <class T>
class List {
	public:
		T item;
		List<T> *prev;
		List<T> *next;
};

template <class T>
class BST : public List<T>, public Node<T> {
	public:
		union {
			List<T> list;
			Node<T> node;
		};
};

template <class T>
class BtOperations : public BST<T> {
	private:
		int compare(T item1, T item2) {
			if (item1 < item2) { return -1; }
			else if (item1 > item2) { return 1;}
			else { return 0; }
		}

	public:
		void printTree(const Node<T> *tree) {
			if (tree == NULL) { return; }

			printTree(tree->left);
			cout << tree->item << endl;
			printTree(tree->right);
		}

		Node<T> *insert(Node<T> **root, T item) {
			if (*root == NULL) { 
				*root = new Node<T>();
				(*root)->item = item;
				(*root)->left = NULL;
				(*root)->right = NULL;
				return *root;
		   	}

			if (compare((*root)->item, item) == 1) {
				(*root)->left = insert(&(*root)->left, item);
			} else {
				(*root)->right = insert(&(*root)->right, item);
			}
			return (*root);
		}

		List<T> *btToList(const Node<T> *node, List<T> *parent) {
			if (node == NULL) {
				return NULL;
			}
			List<T> *ll = new List<T>();
			ll->item = node->item;
			List<T> *leftList = btToList(node->left, parent);
			if (leftList) {
				ll->prev = leftList;
				leftList->next = ll;
			} else if (parent) {
				ll->prev = parent;
				parent->next = ll;
			}
			List<T> *rightList = btToList(node->right, ll);
			return rightList;
		}
};

int main(int argc, char *argv[]) {
	BST<int> *ll = new BST<int>();
	Node<int> *tree = NULL;
	List<int> *dll;
	BtOperations<int> *btOps = new BtOperations<int>();	
	vector<int> array = {7, 4, 9, 2, 6, 8, 10}; 

	for (auto &it : array) {
		//cout << it << endl;
		btOps->insert(&tree, it);
		it++;
	}

	btOps->printTree(tree);
	dll = btOps->btToList(tree, NULL);
	cout << dll << endl;
	//cout << ll->list.item << endl;
	//cout << tree->node.item << endl;
	//cout << sizeof(*ll) << endl;
	return 0;
}
