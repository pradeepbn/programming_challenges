#include <iostream>
#include <vector>

using namespace std;

template <class T>
class Node {
	public:
		T item;
		Node *next;
};

template <class T>
class ListOps : public Node<T> {
	private:
		int compare(T item1, T item2) {
			if (item1 < item2) { return -1; }
			else if (item1 > item2) { return 1; }
			else { return 0; }
		}

		void printList(Node<T> *root) {
			if (root == NULL) { return; }
			cout << root->item << endl;
			root = root->next;
			printList(root);
		}

		Node<T> *split(Node<T> *root) {
			if (root == NULL) { return NULL; }

			Node<T> *slow = root;
			Node<T> *fast = root;
			Node<T> *prevSlow;

			if (!root->next) { return NULL; }

			while (fast && fast->next) {
				fast = fast->next->next;
				prevSlow = slow;
				slow = slow->next;
			}
			prevSlow->next = NULL;	
			return slow;
		}

		Node<T> *merge(Node<T> *leftNode, Node<T> *rightNode) {
			if (!rightNode) {
				return leftNode;
			}
			Node<T> *headMergeList;
			int comp;

			while (leftNode && rightNode) {
				comp = compare(leftNode->item, rightNode->item);
				if (comp == -1) {
				}
			}

		}

	public:
		Node<T> *root;
		
		void add(Node<T> **root, T item) {
			//tail recursion
			if (*root == NULL) {
				*root = new Node<T>();
				(*root)->item = item;
				(*root)->next = NULL;
				return;
			} else if ((*root)->next == NULL) {
				(*root)->next = new Node<T>();
				(*root)->next->item = item;
				(*root)->next->next = NULL;
				return;
			}
			add(&(*root)->next, item);
		}

		Node<T> *sort(Node<T> *root) {
			Node<T> *rightHeadNode;
			Node<T> *leftHeadNode;
			if (root == NULL) {
				return NULL;
			}
			rightHeadNode = split(root);
			leftHeadNode = sort(root);
			rightHeadNode = sort(rightHeadNode);
			merge(leftHeadNode, rightHeadNode);
			return leftHeadNode;
		}

		Node<T> *test(vector<T> &array) {
			Node<T> *root = NULL;
			for (auto i : array) {
				add(&root, i);
			}

			printList(root);

			cout << root->next << endl;
			cout << split(root)->item << endl;
			return NULL;
		}
};

int main(int argc, char *argv[]) {
	vector<int> array = {3, 4, 0, 1, 2, 10, 6};
	//vector<int> array = {3};//, 4, 0, 1, 2, 10};
	Node<int> *list;// = new Node<int>();
	ListOps<int> *listOps = new ListOps<int>();

	//Creating an array
	for (auto i: array) {
		listOps->add(&listOps->root, i);
	}

	//list->root = list->sort(list->root);			
	listOps->test(array);
}
