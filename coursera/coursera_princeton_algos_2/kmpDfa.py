#
# KMP for substring matching for ASCII in Python
#
R = 26;
dfa = [];
 
charVal = lambda c : ord(c) - ord('a');

def computeDfa(pattern):
    a = pattern;
    for i in range(0,R):
        dfa.append([]);
        for j in range (0, len(a)):
            dfa[i].append(0);
          
    X = 0;
    j = 0;
    dfa[charVal(a[j])][0] = j + 1;
    j += 1;
    for j in range(j, len(a)):
        for c in range(0, R):
            dfa[c][j] = dfa[c][X];
        dfa[charVal(a[j])][j] = j + 1;
        X = dfa[charVal(a[j])][X];
        j += 1;

def matchSubstring(text, pattern):
    computeDfa(pattern);
    j = 0;
    for i in range(0, len(text)):
        j = dfa[charVal(text[i])][j];
        if (j == len(pattern)):
            return (i - len(pattern) + 1);
            break;

if __name__ == '__main__':
    pattern = "ababac"
    #text = "mnopqrstababactuvew";
    pattern = "jkvnjkhdanfbklhadfgbklnd";
    text = "adkasdfsadfmsdfsdafjhadfgkjdafbdafjkvnjkhdanfbklhadfgbklndakbnvadjkfnvbjdbydhabfkhdafklgbhadlkfghdakfhglkjadhfgklajdhfglakjdhgadnopqrstababactuvew";
#    text = "ad;kjfghladfhg;ljahdfgkjvndckjvnkjadfhguoaehfgouhqeiu hdfkasdfjkha iashdfil; aslihdfilashdf hdfjklha kjhdf sahfjkasf mnopqrstababactuvew";
#print text[matchSubstring(text, pattern)];
#    print rMatrix;
    import timeit
    matchString = "matchSubstring(text, pattern)";
    timer = timeit.Timer(lambda : matchSubstring(text, pattern));
    result = timer.repeat(repeat=100, number=1);
    #print result;
    print sum(result)/ len(result);
#    print(timeit.timeit(matchString, setup="from __main__ import matchSubstring"))
