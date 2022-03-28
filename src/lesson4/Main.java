/*
   Создать три потока, каждый из которых выводит определенную букву
   (A, B и C) 5 раз (порядок – ABСABСABС).
   Используйте wait/notify/notifyAll.
*/
package lesson4;

class Main {
	final Integer counter = 5;
	volatile char curLetter = 'A'; // изменяется во всех потоках

	void print(char letter) {
		synchronized (counter) {
			try {
				for (int i = 0; i < counter; i++) {
					while (curLetter != letter) counter.wait();
					System.out.print(curLetter);
					if (letter == 'C') curLetter = 'A'; else curLetter++;
					counter.notifyAll();
				}
			} catch (InterruptedException ex) { ex.printStackTrace(); }
		}
	}

	public static void main(String[] args) {
		Main printer = new Main();
		new Thread(() -> printer.print('A')).start();
		new Thread(() -> printer.print('B')).start();
		new Thread(() -> printer.print('C')).start();
	}
}