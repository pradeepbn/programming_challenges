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
        
pattern = "ababac";
text = "mnopqrstababactuvew";
print text[matchSubstring(text, pattern)];
