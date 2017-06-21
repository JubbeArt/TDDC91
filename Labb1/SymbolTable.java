/*
	Av: JESPER "MANNEN" WRANG aka KINGEN aka SWAG_BOY_6969 (jeswr740) och Samuel Johansson (samjo788)
*/

public class SymbolTable {
	private static final int INIT_CAPACITY = 7;

	/* Number of key-value pairs in the symbol table */
	private int N;
	/* Size of linear probing table */
	private int M;
	/* The keys */
	private String[] keys;
	/* The values */
	private Character[] vals;
	/* Tombstones */
	private boolean[] deleted;

	/**
	 * Create an empty hash table - use 7 as default size
	 */
	public SymbolTable() {
		this(INIT_CAPACITY);
	}

	/**
	 * Create linear probing hash table of given capacity
	 */
	public SymbolTable(int capacity) {
		N = 0;
		M = capacity;
		keys = new String[M];
		vals = new Character[M];
		deleted = new boolean[M];
	}

	/**
	 * Return the number of key-value pairs in the symbol table
	 */
	public int size() {
		return N;
	}

	/**
	 * Is the symbol table empty?
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Does a key-value pair with the given key exist in the symbol table?
	 */
	public boolean contains(String key) {
		return get(key) != null;
	}

	/**
	 * Hash function for keys - returns value between 0 and M-1
	 */
	public int hash(String key) {
		int i;
		int v = 0;

		for (i = 0; i < key.length(); i++) {
			v += key.charAt(i);
		}
		return v % M;
	}

	/**
	 * Insert the key-value pair into the symbol table
	 */
	public void put(String key, Character val) {
		if(key == null) { // No key sent
			return;
		} else if(val == null) { // No value sent => remove the key
			delete(key);
		} else if(N < M) {  // Table is not full
			int i = hash(key);	
		
			if(contains(key)) { // Key allready exists
				while(true) {
					if(keys[i].equals(key)) { 
						vals[i] = val;
						break;
					}
					
					i++;
					
					if(i >= M)
						i = 0;
					
				}
			} else { // Need to find empty spot for key
				while(true) {
					if(keys[i] == null) {
						vals[i] = val;
						keys[i] = key;
						N++;
						break;
					}
					
					i++;
					
					if(i >= M) 
						i = 0;
					
				}
			}
		}
	}

	/**
	 * Return the value associated with the given key, null if no such value
	 */
	public Character get(String key) {
		int h = hash(key);
		int i = h;
		
		while(true) {
			if(keys[i] == null) { // Current index is empty, check deleted flag
				if(!deleted[i])
					return null;
			} else if(keys[i].equals(key))  // Current index has key
				return vals[i]; 
			
			i++;
			if(i >= M) 
				i = 0;
			
			if(i == h) { // Looped through the hole table
				return null;
			}
		}		
	} 

	/**
	 * Delete the key (and associated value) from the symbol table
	 */
	public void delete(String key) {
		if(contains(key)) {
			int i = hash(key);

			while(true) {
				if(keys[i] != null && keys[i].equals(key)) { // Current key exists and is equal to the value sent in
					keys[i] = null;
					vals[i] = null;
					deleted[i] = true;
					N--;
					break;
				}
				
				i++;
				if(i >= M)
					i = 0;
			}
			
		} 		
	}

	/**
	 * Print the contents of the symbol table
	 */
	public void dump() {
		String str = "";

		for (int i = 0; i < M; i++) {
			str = str + i + ". " + vals[i];
			if (keys[i] != null) {
				str = str + " " + keys[i] + " (";
				str = str + hash(keys[i]) + ")";
			} else {
				str = str + " -";
			}
			System.out.println(str);
			str = "";
		}
	}
}
