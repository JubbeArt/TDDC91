public class QSort {
	  
	/**
	 * Quicksort the array a[] using m as cutoff to insertion sort.
	 */
	public static void quicksort(int[] a, int m) {
		quicksort(a, 0, a.length - 1, m);
	}

	/**
	 * Quicksort the subarray a[low .. high].
	 * Uses median-of-three partitioning
	 * and a cutoff to insertion sort of m.
	 **/
	private static void quicksort(int[] a, int low, int high, int m)  {
		// high - low = size of current array
		if(high - low <= m) {
				insertionsort(a, low, high);
		} else {
			int j = partition(a, low, high);
			
			quicksort(a, low, j-1, m); // quicksort array left of j
			quicksort(a, j+1, high, m); // quicksort array right of j
		}
	} 

	/**
	 * Sort from a[low] to a[high] using insertion sort.
	 */
	private static void insertionsort(int[] a, int low, int high) {
		for(int i = low + 1; i <= high; i++) { // Go from left to right (low + 1 to high)
			for(int j = low; j < i; j++) { // Begin at low, go to current i
				if(a[j] > a[i]) // Current is bigger than i
					swap(a, i ,j); // Switch current and i
			}
		}
	}

	// Copied from Labbinstruktionerna, dom gav oss bokstavligen svaret....
	private static int partition(int[] a, int low, int high) {
		int pivotIndex = findMedian(a, low, high);
		int pivotValue = a[pivotIndex];
		swap(a, pivotIndex, high); // Move pivot to end
		int storeIndex = low;
		
		for (int i = low; i <= high - 1; i++){
			if (a[i] <= pivotValue) {
				swap(a, i, storeIndex);
				storeIndex++;
			}
		}
		swap(a, storeIndex, high); // Pivot to final place
		
		return storeIndex;
	}
	
	private static int findMedian(int[] a, int low, int high) {
		int index = (int) Math.floor((low + high)/ 2.0); // The index between low and high
		int x = a[low], y = a[index], z = a[high]; // Values of the indexes
		
		// Median
		int middle = (x + y + z) - Math.min(x,Math.min(y,z)) - Math.max(x,Math.max(y,z));
		
		// Return index of median
		if(x == middle)
			return low;
		else if(y == middle)
			return index;
		else
			return high;
	}
	
	// Swaps two values
	private static void swap(int[] a, int x, int y) {
		int tmp = a[x];
		a[x] = a[y];
		a[y] = tmp;
	}
	
}