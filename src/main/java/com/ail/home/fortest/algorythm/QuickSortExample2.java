package com.ail.home.fortest.algorythm;

import java.util.Random;

public class QuickSortExample2 {
	public static void main(String[] args) {
		int N = 10;
		int[] A;
		A = new int[N + 1];
		// заполнение массива
		for (int i = 0; i <= N; i = i + 1) {
			A[i] = N - i;
			System.out.print(A[i] + " ");
		}
		System.out.println("\nBefore qSort\n");
		// перемешивание массива
		Random r = new Random(); //инициализация от таймера
		int yd, xs;
		for (int i = 0; i <= N; i = i + 1) {
			yd = A[i];
			xs = r.nextInt(N + 1);
			A[i] = A[xs];
			A[xs] = yd;
		}
		for (int i = 0; i <= N; i = i + 1)
			System.out.print(A[i] + " ");
		System.out.println("\nAfter randomization\n");
		long start, end;
		int low = 0;
		int high = N;

		start = System.nanoTime();   // получить начальное время
		qSort(A, low, high);
		end = System.nanoTime();    // получить конечное время

		for (int i = 0; i <= N; i++)
			System.out.print(A[i] + " ");
		System.out.println("\nAfter qSort");
		System.out.println("\nTime of running: " + (end - start) + "nanosec");
	}

	//описание функции qSort
	public static void qSort(int[] A, int left, int right) {
		int l = left;
		int r = right;
		int cur = A[left + (right - left) / 2];
		do {
			while (A[l] < cur)
				++l;
			while (A[r] > cur)
				--r;
			if (l <= r) {
				int temp = A[l];
				A[l] = A[r];
				A[r] = temp;
				l++;
				r--;
			}
		} while (l <= r);
		//рекурсивные вызовы функции qSort
		if (left < r)
			qSort(A, left, r);
		if (l < right)
			qSort(A, l, right);
	}
}