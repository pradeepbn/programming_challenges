using namespace std;

#include <iostream>
#include <BinaryTree.h>
#include <BinarySearchTree.h>

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
