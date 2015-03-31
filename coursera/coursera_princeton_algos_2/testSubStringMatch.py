import kmpDfa;
import boyerMoore;
if __name__ == '__main__':
    pattern = "ababac"
    #text = "mnopqrstababactuvew";
    pattern = "jkvnjkhdanfbklhadfgbklnd";
    text = "adkasdfsadfmsdfsdafjhadfgkjdafbdafjkvnjkhdanfbklhadfgbklndakbnvadjkfnvbjdbydhabfkhdafklgbhadlkfghdakfhglkjadhfgklajdhfglakjdhgadnopqrstababactuvew";
#    text = "ad;kjfghladfhg;ljahdfgkjvndckjvnkjadfhguoaehfgouhqeiu hdfkasdfjkha iashdfil; aslihdfilashdf hdfjklha kjhdf sahfjkasf mnopqrstababactuvew";
#print text[matchSubstring(text, pattern)];
#    print rMatrix;
    import timeit 
    timerKmpDfa = timeit.Timer(lambda : kmpDfa.matchSubstring(text, pattern));
    resultKmpDfa = timerKmpDfa.repeat(repeat=100, number=1);
    timerBMoore = timeit.Timer(lambda : boyerMoore.matchSubstring(text, pattern));
    resultBMoore = timerBMoore.repeat(repeat=100, number=1);
    #print result;
    avgKmpDfa = sum(resultKmpDfa)/ len(resultKmpDfa);
    avgBMoore = sum(resultBMoore)/ len(resultBMoore);
    print "Improvement of Boyer Moore over KMP is:", ((avgKmpDfa - avgBMoore)/ avgBMoore) * 100, "%";
#    print(timeit.timeit(matchString, setup="from __main__ import matchSubstring"))
