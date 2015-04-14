#include <iostream>
#include <string>
#include <ctime>

using namespace std;

// Definition for singly-linked list.
struct ListNode {
    int val;
    ListNode *next;
    ListNode(int x) : val(x), next(NULL) {}
};
 
ListNode *reverseList(ListNode *node) {
	if ((node) == NULL || (node)->next == NULL) {
		return node;
	}
	ListNode *lNode;

	lNode = reverseList(node->next);
	(node)->next->next = node;
	(node)->next = NULL;
	//*node = (*node)->next;
	
	return lNode;
}

void printListNode(ListNode *node) {
	if (node == NULL) {
		return;
	}
	cout << node->val << endl;
	printListNode(node->next);
}

void clearList(ListNode **node) {
	if ((*node) == NULL) {
		return;
	}

	clearList(&(*node)->next);
	delete *node;
}

int main(int argc, char *argv[]) {
	if (argc <= 1) {
		cout << "Enter arguments" << endl;	
	}
	struct ListNode **node;
	struct ListNode *headNode = NULL;
	int val;

	//for (int i = 1; i < argc; i++) {
	for (int i = 1; i <= 100000; i++) {
		//val = stoi(argv[i]);// << endl;	
		*node = new struct ListNode(i);
		if (i == 1) {
			headNode = *node;
		}
		node = &((*node)->next);

	}
	clock_t start = clock();
	headNode = reverseList(headNode);
	cout << (double)(clock() - start) / CLOCKS_PER_SEC << endl;
	cout << endl;

	//printListNode(headNode);
	clearList(&headNode);
	//delete node;
	delete *node;
	delete headNode;
	return 0;
}
