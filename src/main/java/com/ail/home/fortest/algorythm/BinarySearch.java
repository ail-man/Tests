package com.ail.home.fortest.algorythm;

public class BinarySearch {
	private static int ARRAY_LENGTH = 30;
	private static int[] arr = new int[ARRAY_LENGTH];

	public static void main(String[] args) {
		for (int i = 0; i < ARRAY_LENGTH; i++) {
			arr[i] = i * 2;
			System.out.println(arr[i]);
		}

		System.out.println(binarySearch(arr, 12));
		System.out.println(binarySearch(arr, 32));
	}

	public static int binarySearch(int[] arr, int val) {
		int low = 0;
		int high = arr.length - 1;

		while (low <= high) {
			int mid = (low + high) >>> 1;
			int midVal = arr[mid];

			if (midVal < val)
				low = mid + 1;
			else if (midVal > val)
				high = mid - 1;
			else
				return mid; // key found
		}
		return -(low + 1);  // key not found.
	}

}
