def binarySearchReplaceMedian(k, len_a, len_b, mergedArray):
    if (len_b == 0):
        #Get this working tomorrow
def findKthLargestElement(k, len_a, len_b, mergedArray): 
    #a[0] = 30;  
    if (mergedArray[len_a - 1] > mergedArray[k - 1]):
        binarySearchReplaceMedian(k, len_a, 0, mergedArray);
    else:
        return;

    if (mergedArray[k] < mergedArray[k - 1]):
        binarySearchReplaceMedian(k, 0, len_b, mergedArray);

    findKthLargestElement(k, len_a, len_b, mergedArray);
    print (k);


def main():
    a = [1, 4, 7, 10];
    b = [2, 3, 5, 6, 8, 9];
    k = 3;
    c = a + b;
    findKthLargestElement(len(c) - k, len(a), len(b), c);
    print c;

if __name__ == "__main__":
    main()
