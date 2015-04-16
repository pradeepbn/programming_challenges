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
 
ListNode *reverseList(ListNode *node, int num) {
	if (num == 0 || (node) == NULL || (node)->next == NULL) {
		return node;
	}

	ListNode *lNode;
	
	lNode = reverseList(node->next, --num);
	node->next->next = node;
	(node)->next = NULL;

	return lNode;
}

static 
ListNode *reverse(ListNode *node, int m, int n) {
	/*
	 * TODO: Implement this guy
	 */
	if (n <= m || m == 0) {
		return node;
	}

	ListNode *headNode = node;
	ListNode *lNode;
	ListNode *sNode;
	if (m != 1) {
		for (int i = 0; i < m - 2; i++) {
			node = node->next;
		}
	}
	sNode = node;
	for (int i = (m == 1) ? m - 1 : m - 2; i < n; i++) {
		node = node->next;
	}
	if (m == 1) {
		sNode = reverseList(sNode, (n - m));
		headNode = sNode;
	} else {
		sNode->next = reverseList(sNode->next, (n - m));
	}
	while (sNode->next != NULL) {
		sNode = sNode->next;
	}

	sNode->next = node;

	return headNode;
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
	int m = stoi(argv[1]);
	int n = stoi(argv[2]);
	cout << m << ";" << n << endl;

	for (int i = 3; i < argc; i++) {
	//for (int i = 1; i <= 100000; i++) {
		val = stoi(argv[i]);// << endl;	
		*node = new struct ListNode(val);
		if (i == 3) {
			headNode = *node;
		}
		node = &((*node)->next);

	}
	clock_t start = clock();
	headNode = reverse(headNode, m, n);
	cout << (double)(clock() - start) / CLOCKS_PER_SEC << endl;
	cout << endl;

	printListNode(headNode);
	clearList(&headNode);
	//delete node;
	//delete *node;
	//delete headNode;
	
	return 0;
}
