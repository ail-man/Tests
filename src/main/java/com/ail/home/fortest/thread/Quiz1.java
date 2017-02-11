package com.ail.home.fortest.thread;

public class Quiz1 {
	public static void main(String[] args) {
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(5000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			@Override
			public synchronized void start() {
			}
		};
		t.start();
	}
}
