class LongestChain {
	private Queue q; // kö som används i breddenförstsökningen
	private String goalWord; // slutord i breddenförstsökningen
	int wordLength;
	final char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
			's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'å', 'ä', 'ö', 'é' };
	int alphabetLength = alphabet.length;
		
	public LongestChain(int wordLength) {
		this.wordLength = wordLength;
		q = new Queue();
	}

	// IsGoal kollar om w är slutordet.
	private boolean IsGoal(String w) {
		return w.equals(goalWord);
	}

	// MakeSons skapar alla ord som skiljer på en bokstav från x.
	// Om det är första gången i sökningen som ordet skapas så läggs det
	// in i kön q.
	private WordRec MakeSons(WordRec x) {
		
		for (int i = 0; i < wordLength; i++) {
			for (int c = 0; c < alphabetLength; c++) {
				if (alphabet[c] != x.word.charAt(i)) {
					String res = WordList.Contains(x.word.substring(0, i) + alphabet[c] + x.word.substring(i + 1));
		
					if (res != null && WordList.MarkAsUsedIfUnused(res)) {
						WordRec wr = new WordRec(res, x);
												
						if (goalWord != null && IsGoal(res))
							return wr;
						q.Put(wr);
					}
				}
				
			}			
		}
		
		return null;
	}

	// BreadthFirst utför en breddenförstsökning från startWord för att
	// hitta kortaste vägen till endWord. Den kortaste vägen returneras
	// som en kedja av ordposter (WordRec).
	// Om det inte finns något sätt att komma till endWord returneras null.
	public WordRec BreadthFirst(String startWord, String endWord) {
		WordList.EraseUsed();
		WordRec start = new WordRec(startWord, null);
		WordList.MarkAsUsedIfUnused(startWord);
		goalWord = endWord;
		q.Empty();
		q.Put(start);
		try {
			while (true) {
				WordRec wr = MakeSons((WordRec) q.Get());
				if (wr != null)
					return wr;
			}
		} catch (Exception e) {
			return null;
		}
	}

	// CheckAllStartWords hittar den längsta kortaste vägen från något ord
	// till endWord. Den längsta vägen skrivs ut.
	public void CheckAllStartWords(String endWord) throws Exception {
		WordRec maxChainRec = null;
		//--------------OLD CODE---------------
		//(brute-force lösning)
		
		/*for (int i = 0; i < WordList.size; i++) {
			WordRec x = BreadthFirst(WordList.WordAt(i), endWord);
			if (x != null) {
				int len = x.ChainLength();
				if (len > maxChainLength) {
					maxChainLength = len;
					maxChainRec = x;
					// x.PrintChain(); // skriv ut den hittills längsta kedjan
				}
			}
		}*/
		
		//------------ NEW CODE--------------
		WordList.EraseUsed();
		WordRec start = new WordRec(endWord, null);
		WordList.MarkAsUsedIfUnused(endWord);
		q.Empty();
		goalWord = null;
		q.Put(start); // Börjar på slutordet
		
		while(true) {
						
			if(q.size() == 1) { // Vi har bara en nod att kolla på, potentiell sista nod			
				maxChainRec = (WordRec) q.Get(); // Spara nuvarande noden
				MakeSons(maxChainRec); // Kolla nodens barn
			} else if(q.IsEmpty()) { // Finns inga barn kvar
				System.out.println(endWord + ": " + maxChainRec.ChainLength() + " ord");
				break;
			} else {
				MakeSons((WordRec) q.Get());
			}
		}
		
		// Ny print-funktion eftersom vi går motsatt håll som den gamla koden
		maxChainRec.PrintReversedChain(); 
	}
}
