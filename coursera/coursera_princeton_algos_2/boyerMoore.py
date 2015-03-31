R = 26;
rMatrix = [];
charToInt = lambda char : (ord(char) - ord('a'));

def computeRightMostCharacterMatrix(pattern):
    for i in range(0, 26):
        rMatrix.append(-1);
    for j in range(0, len(pattern)):
        rMatrix[charToInt(pattern[j])] = j; 

def matchSubstring(text, pattern):
    computeRightMostCharacterMatrix(pattern);
#print rMatrix;
    M = len(pattern);
    skip = 0;
    i = 0;
    while i < len(text):
        for j in range(M - 1, -1, -1):
            if (text[i + j] != pattern[j]):
                if (rMatrix[charToInt(text[i + j])] == -1):
                    '''
                        Case 1: Mismatch character not found in the pattern 
                    '''
                    skip = j;
                else:
                    skip = max(1, j - rMatrix[charToInt(text[i + j])]);

                i = i + skip; 
                break;
            if (j == 0):
                return i;


if __name__ == '__main__':
    #pattern = "ababac"
    pattern = "jkvnjkhdanfbklhadfgbklnd";
#    text = "mnopqrstababactuvew";
    #text = "ad;kjfghladfhgljahdfgkjvndckjvnkjadfhguoaehfgouhqeiu hdfkasdfjkha iashdfil aslihdfilashdf hdfjklha kjhdf sahfjkasf mnopqrstababactuvew";
    text = "adkasdfsadfmsdfsdafjhadfgkjdafbdafjkvnjkhdanfbklhadfgbklndakbnvadjkfnvbjdbydhabfkhdafklgbhadlkfghdakfhglkjadhfgklajdhfglakjdhgadnopqrstababactuvew";
#print text[matchSubstring(text, pattern)];
#    print rMatrix;
    import timeit
    matchString = "matchSubstring(text, pattern)";
    timer = timeit.Timer(lambda : matchSubstring(text, pattern));
    result = timer.repeat(repeat=100, number=1);
    #print result;
    print sum(result)/ len(result);
#    print(timeit.timeit(matchString, setup="from __main__ import matchSubstring"))
    #result 
