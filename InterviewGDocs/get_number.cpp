/*
 * You are given as an input a byte array and length. You are also given an index and a number representing a bit width.
 *
 * The function has this prototype:
 *
 * uint8_t get_number(const uint8_t * buffer, size_t length, size_t index, size_t bit_width);
 *
 * You can assume 1 <= bit_width <= 8
 *
 * You are to implement the get_number function above, that gets the number of bit width bit_width at index index. 
 *
 * example:
 * buffer = { 0xAB, 0xCD }, length = 2
 *
 * bit_width = 4, index = 0
 *
 * return value = 0xB
 *
 * index = 1
 * 0xA
 *
 * index = 3
 *
 * 0xC
 */
#include <iostream>

using namespace std;

uint8_t get_number(const uint8_t *buffer, size_t length,
					size_t index, size_t bit_width) {
	uint8_t result;
	size_t bit_count = 0;
	unsigned i = 0;

	do {
		result = 0;
		for (size_t j = 0; j < bit_width; j++) {
			if (bit_count == 8) { buffer++; bit_count = 0;}
			result |= (((*buffer & (uint8_t)(1 << (bit_count))) >> bit_count) << j);
			bit_count++;
		}
		i++;
	} while (i <= index);

	return result;
}

int main(int argc, char *argv[]) {
	uint8_t buffer[] = {0xab, 0xcd};
	//uint8_t result = get_number(buffer, 2, 7, 2);
	printf("%x\n", get_number(buffer, 2, 1, 4));
	//printf("%x\n", result);
	return 0;
}
