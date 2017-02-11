package com.ail.home.fortest.algorythm;

import java.util.Random;

public class QuickSortExample1 {
	public static int ARRAY_LENGTH = 30;
	private static int[] array = new int[ARRAY_LENGTH];
	private static Random generator = new Random();

	public static void initArray() {
		for (int i=0; i<ARRAY_LENGTH; i++) {
			array[i] = generator.nextInt(100);
		}
	}

	public static void printArray() {
		for (int i=0; i<ARRAY_LENGTH-1; i++) {
			System.out.print(array[i] + ", ");
		}
		System.out.println(array[ARRAY_LENGTH-1]);
	}

	public static void quickSort() {
		int startIndex = 0;
		int endIndex = ARRAY_LENGTH - 1;
		sort(startIndex, endIndex, array);
	}

	private static void doSort(int start, int end) {
		if (start >= end)
			return;
		int l = start, r = end;
		int cur = l + (r - l) / 2;
		while (l < r) {
			while (l < cur && (array[l] <= array[cur])) {
				l++;
			}
			while (r > cur && (array[cur] <= array[r])) {
				r--;
			}
			if (l < r) {
				int temp = array[l];
				array[l] = array[r];
				array[r] = temp;
				if (l == cur)
					cur = r;
				else if (r == cur)
					cur = l;
			}
		}
		doSort(start, cur);
		doSort(cur+1, end);
	}

	public static void main(String[] args) {
		initArray();
		printArray();
		quickSort();
		printArray();
	}

	public static void sort(int start, int end, int[] array) {
		if (start >= end) {
			return;
		}

		int l = start, r = end;
		int cur = l + (r - l)/2;

		while (l < r) {
			while (l < cur && (array[l] <= array[cur])) {
				l++;
			}
			while (r > cur && (array[r] >= array[cur])) {
				r--;
			}

			if (l < r) {
				int temp = array[l];
				array[l] = array[r];
				array[r] = temp;
				if (l == cur) {
					cur = r;
				} else if (r == cur) {
					cur = l;
				}
			}
		}

		sort(start, cur, array);
		sort(cur + 1, end, array);
	}
}
