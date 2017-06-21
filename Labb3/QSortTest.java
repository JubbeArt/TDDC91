import java.lang.management.*;
import java.util.Random;

class QSortTest {

	static final int firstM = 1;

	static final int lastM = 40;

	/**
	 * Get user time in nanoseconds.
	 */
	static long getUserTime() {
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		return bean.isCurrentThreadCpuTimeSupported() ? bean
				.getCurrentThreadUserTime() : 0L;
	}

	static void writeSequence(int[] a) {
		System.out.print("  ");
		for (int i = 0; i < a.length; i++) {
			for (int j = 10; j <= 10000; j = j * 10) {
				if (a[i] < j)
					System.out.print(" ");
			}
			System.out.print(a[i] + "  ");
			if ((i + 1) % 10 == 0) {
				System.out.print("\n  ");
				System.out.flush();
			}
		}
	}

	private static boolean checkOrder(int[] a) {
		boolean res = true;
		for (int i = 1; i < a.length; i++) {
			if (a[i] < a[i - 1]) {
				System.out.println("Order violated at index " + i
						+ " with value " + a[i]);
				res = false;
			}
		}
		return res;
	}

	public static void main(String[] args) throws Exception {
		int m;
		Random generator;

		if (args.length != 0) { // check command line flags
			if (!((args[0].equals("-d")) || (args[0].equals("-s")))
					|| (args.length > 1)) {
				System.out.println("Usage: java QSortTest [-d|-s]");
			} else if (args[0].equals("-d")) { // flag "-d"
				long seed = 2010848087680992582L;
				generator = new Random(seed);
				int[] seq = new int[300];
				int[] cpy1 = new int[300];
				int[] cpy2 = new int[300];

				for (int i = 0; i < seq.length; i++)
					seq[i] = generator.nextInt(32768);

				System.arraycopy(seq, 0, cpy1, 0, cpy1.length);
				System.arraycopy(seq, 0, cpy2, 0, cpy2.length);

				System.out.println("Sequence to sort:");
				writeSequence(seq);

				m = 1;
				System.out.println("m = " + m);
				QSort.quicksort(seq, m);
				checkOrder(seq);
				writeSequence(seq);

				m = 10;
				System.out.println("m = " + m);
				QSort.quicksort(cpy1, m);
				checkOrder(cpy1);
				writeSequence(cpy1);

				m = 300;
				System.out.println("m = " + m);
				QSort.quicksort(cpy2, m);
				checkOrder(cpy2);
				writeSequence(cpy2);

			} else { // flag "-s"
				final int iter = 2000;
				long start;
				long diff;
				long t = (long) 0;
				generator = new Random();
				int[] seq = new int[10000];
				int[] cpy1 = new int[10000];

				for (int j = 0; j < seq.length; j++)
					seq[j] = generator.nextInt(2147483647);

				for (int i = firstM; i <= lastM; i++) {
					m = i;
					t = (long) 0;
					for (int k = 1; k <= iter; k++) {
						System.arraycopy(seq, 0, cpy1, 0, cpy1.length);
						start = getUserTime();
						QSort.quicksort(cpy1, m);
						diff = getUserTime() - start;
						t = t + diff;
					}
					System.out.print("m = ");
					if (m < 10)
						System.out.print(" ");
					System.out.print(m + "   time used: ");
					System.out.println((float) t / (1000000 * iter) + " ms");
				}
			}
		} else { // no flags

		        System.out.println("Usage: java QSortTest [-d|-s]");

		}
	}
}
