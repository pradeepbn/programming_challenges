import math
import sys
from Queue import Queue

class merge_sort:
	def __init__(self):
		self.inversions = 0;
		self.result = [];
	def _merge(self, array, low, middle, high):
		q1 = Queue();
		q2 = Queue();
		print low, middle, high
		for i in range(low - 1, middle):
			q1.put(array[i]);
		for i in range(middle, high):
			q2.put(array[i]);

		i = low - 1;
		while (q1.empty() == False) and (q2.empty() == False):
			q1_data = q1.queue[0];	
			q2_data = q2.queue[0];	

			if q1_data < q2_data:
				try:
					array[i] = q1.get_nowait();
				except:
					pass;
			else:
				try:
					array[i] = q2.get_nowait();
				except:
					pass;
			i = i + 1;

		while q1.empty() == False:
			try:
				array[i] = q1.get_nowait();
			except:
				pass;
			i = i + 1;

		while q2.empty() == False:
			try:
				array[i] = q2.get_nowait();
			except:
				pass;
			i = i + 1;
		print array

	def _merge_sort(self, array, low, high):
		middle = int(math.floor((low + high) / 2));

		if (low < high):
			self._merge_sort(array, low, middle);
			self._merge_sort(array, middle + 1, high);
			self._merge(array, low, middle, high);
		return;

def main():
	m_sort = merge_sort();
	array = [10, 2, 13, 4, 100, 80, 50];
	m_sort._merge_sort(array, 1, len(array)); 
	print array;
	return 0;

if __name__ == "__main__":
	sys.exit(main());

