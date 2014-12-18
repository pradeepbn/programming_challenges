#include <iostream>
#include <vector>
#include <cstring>

using namespace std;

template <class T>
class Node {
	public:
		T item;
        union {
            Node<T> *left;
            Node<T> *prev;
        };
        union {
            Node<T> *right;
            Node<T> *next;
        };
};

//template <class T>
//class List {
//	public:
//		T item;
//		List<T> *prev;
//		List<T> *next;
//};
//
//template <class T>
//class BST : public List<T>, public Node<T> {
//	public:
//		union {
//			List<T> list;
//			Node<T> node;
//		};
//        //List<T> list;
//        //Node<T> node;
//};

template <class T>
class BtOperations : public Node<T> {
	private:
		int compare(T item1, T item2) {
			if (item1 < item2) { return -1; }
			else if (item1 > item2) { return 1;}
			else { return 0; }
		}

        void append(Node<T> *left, Node<T> *right) {

            if (left == right) {
                //Single node
                left->prev = left;
                left->next = left;
            } else {
                Node<T> *leftLastNode = left->prev;
                Node<T> *rightLastNode = right->prev;
                right->prev = leftLastNode;
                rightLastNode->next = left;
                leftLastNode->next = right;
                left->prev = rightLastNode;
            }
        }

	public:
		Node<T> *btToList(Node<T> *node) {
			if (node == NULL) {
				return NULL;
			}
            Node<T> *leftNode = node->left;
            Node<T> *rightNode = node->right;
            Node<T> *leftList = btToList(leftNode); 
            append(node, node);
            if (!leftList) {
                leftList = node;
            } else {
                append(leftList, node);
            }
            Node<T> *rightList = btToList(rightNode); 
            if (rightList) {
                append(leftList, rightList);
            }
            return leftList;
		}

		void printTree(Node<T> *root) {
			if (root == NULL) { return; }

			printTree(root->left);
            cout << root->item << endl;
			printTree(root->right);
		}

        void printList(Node<T> *node) {
            int count = 0;
            while (node) {
                if (count++ == 14) {
                    break;
                }
                cout << node->item << endl;
                node = node->next;
            }
        }

		Node<T> *insert(Node<T> **root, T item) {
			if (*root == NULL) { 
				*root = new Node<T>();
                (*root)->item = item;
				return *root;
		   	}

			if (compare((*root)->item, item) == 1) {
				(*root)->left = insert(&(*root)->left, item);
			} else {
				(*root)->right = insert(&(*root)->right, item);
			}
			return (*root);
		}
};

int main(int argc, char *argv[]) {
	//BST<int> *ll = NULL;
	Node<int> *tree = NULL;
	Node<int> *dll;
	BtOperations<int> *btOps = new BtOperations<int>();	
	vector<int> array = {7, 4, 9, 2, 6, 8, 10}; 

	for (auto &it : array) {
		btOps->insert(&tree, it);
		it++;
	}

	btOps->printTree(tree);
    cout << endl;
	dll = btOps->btToList(tree);
    btOps->printList(dll);
	return 0;
}
