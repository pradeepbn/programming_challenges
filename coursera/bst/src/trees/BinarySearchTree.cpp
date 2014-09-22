using namespace std;
#include <BinaryTree.h>
#include <BinarySearchTree.h>
#include <cstddef>

/*
+------------------+
| PUBLIC FUNCTIONS |
+------------------+
*/
template <class T>
void BinarySearchTree<T> :: insert(Node<T> **currentNode, T item) {
     if (*currentNode == NULL) {
          //cout << "Inserting Item: "  << item <<  endl; 
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

template class BinarySearchTree<int>;
