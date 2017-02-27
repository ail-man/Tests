package com.ail.home.fortest.thread;

/* Есть класс Resource (см. ниже по коду).
* Два потока параллельно запускают метод increment()
* по 1000 раз каждый.
* Есть ли гарантия того, что поле i будет равно 2000 - ?
* Ответ: гарантии нет!!!
* Чтобы была гарантия, метод нужно синхронизировать! */
public class SBTQuiz {

	public static void main(String[] args) throws InterruptedException {
		Resource resource = new Resource();

		Runnable runnable = () -> {
			for (int i = 0; i < 1000; i++) {
				resource.increment();
			}
		};

		Thread t1 = new Thread(runnable);
		Thread t2 = new Thread(runnable);

		t1.start();
		t2.start();

		t1.join();
		t2.join();

		System.out.println(resource.getCount());
	}

	private static class Resource {
		private volatile int count = 0;

		private void increment() {
			count++;
		}

		private int getCount() {
			return count;
		}
	}
}
