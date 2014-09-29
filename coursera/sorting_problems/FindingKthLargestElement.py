even = lambda lo, hi: (lo + hi) % 2;
mid_even = lambda lo, hi: ((lo + hi) / 2);
mid_odd = lambda lo, hi: ((lo + hi) / 2);

def binarySearchReplaceMedian(k, lo, hi, mergedArray):
    print lo, hi, mergedArray[k - 1];
    print mergedArray;
    if (lo + 1 == hi):
        print "swapping"
        temp = mergedArray[k - 1]
        mergedArray[k - 1] = mergedArray[lo + 1];
        mergedArray[lo + 1] = temp;
        print mergedArray;
        return;

    if (even(lo, hi) == 0):
        mid = mid_even(lo, hi);
    else:
        mid = mid_odd(lo, hi);
    print mid;
    if (k - 1 == mid):
        if (mergedArray[k - 1] < mergedArray[mid - 1]):
            temp = mergedArray[k - 1];
            mergedArray[k - 1] = mergedArray[mid - 1];
            mergedArray[mid - 1] = temp;
            return;
        elif (mergedArray[k - 1] > mergedArray[mid + 1]) or (k - 1 == mid):
            temp = mergedArray[k - 1];
            mergedArray[k - 1] = mergedArray[mid + 1];
            mergedArray[mid + 1] = temp;
            return;

    if (mergedArray[k - 1] < mergedArray[mid]):
        binarySearchReplaceMedian(k, lo, mid, mergedArray);
    elif (mergedArray[k - 1] > mergedArray[mid]):
        binarySearchReplaceMedian(k, mid, hi, mergedArray);

def findKthLargestElement(k, len_a, len_b, mergedArray): 
    #a[0] = 30;  
    print "Finding";
    if (mergedArray[len_a - 1] > mergedArray[k - 1]):
        print "In first block"
        binarySearchReplaceMedian(k, 0, len_a - 1, mergedArray);
    elif (mergedArray[k] < mergedArray[k - 1]):
        print "In Second block"
        binarySearchReplaceMedian(k, len_a, (len_a + len_b - 1), mergedArray);
    else:
        return;

    findKthLargestElement(k, len_a, len_b, mergedArray);
    print (k);

def main():
    a = [1, 4, 10, 78, 100];
    b = [2, 3, 70, 75, 80];
    k = 3;
    c = a + b;
    print even(0, 5);
    #print mid_odd(0, 5);
    findKthLargestElement(len(c) - k + 1, len(a), len(b), c);
    print k, "the largest element is:", c[len(c) - k];

if __name__ == "__main__":
    main()
