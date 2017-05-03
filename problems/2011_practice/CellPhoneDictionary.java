import java.util.*;
import java.io.*;

public class CellPhoneDictionary {
	// maps a letter to a numeric key
	// built once for the whole program
	private static final Map <Character, Character> mapping;

	static {
		mapping = new HashMap <Character, Character> (128);
		mapping.put('a', '2');
		mapping.put('b', '2');
		mapping.put('c', '2');
		mapping.put('d', '3');
		mapping.put('e', '3');
		mapping.put('f', '3');
		mapping.put('g', '4');
		mapping.put('h', '4');
		mapping.put('i', '4');
		mapping.put('j', '5');
		mapping.put('k', '5');
		mapping.put('l', '5');
		mapping.put('m', '6');
		mapping.put('n', '6');
		mapping.put('o', '6');
		mapping.put('p', '7');
		mapping.put('q', '7');
		mapping.put('r', '7');
		mapping.put('s', '7');
		mapping.put('t', '8');
		mapping.put('u', '8');
		mapping.put('v', '8');
		mapping.put('w', '9');
		mapping.put('x', '9');
		mapping.put('y', '9');
		mapping.put('z', '9');
	}

	/**
	 * Makes a new dictionary for each problem case.
	 */
	public CellPhoneDictionary() {
		numberSequenceToWords = new TreeMap <String, Set <String> > ();
	}

	// skeleton
	public static void main(String []args) throws IOException {


		// Open the input file.
		Scanner fin = new Scanner(new File("tnine.in"));
		
		// Number of words in the dictionary.
		int size = fin.nextInt();
		
		ArrayList<String> words = new ArrayList<String>();
		CellPhoneDictionary dictionary = new CellPhoneDictionary();
		
		// Read in each word.
		for (int i=0; i<size; i++) 
			words.add(fin.next());
		dictionary.addAllWords(words);

		int numcases = fin.nextInt();
		String garbage = fin.nextLine();
		
		// Loop through each case.
		for (int i=1; i<=numcases; i++) {
			
			// Set up the message in a StringTokenizer.
			StringTokenizer tok = new StringTokenizer(fin.nextLine());
			
			// Stores the sentence in tokenized form.
			ArrayList<String> sentence = new ArrayList<String>();
			
			// Loop as long as there are more words in the text message.
			while (tok.hasMoreElements()) 
				sentence.add(tok.nextToken());
				
			System.out.print("Message #"+i+": ");
			dictionary.process(sentence);
			
		}
		
		fin.close();

	}

	/**
	 * Adds all of these letter-based words into this dictionary. This
	 * translates the words into keypresses and everything.
	 *
	 * @param words A collection of words to add to the dictionary
	 */
	public void addAllWords(Collection <String> words) {
		for(String word : words)
			addWord(word);
	}


	/**
	 * Processes one sentence with the dictionary values.
	 *
	 * @param sentence The words of the sentence
	 */
	public void process(List <String> sentence) {
		int numWays = 1;
		for(String word : sentence)
			numWays *= countTranslations(word);

		switch(numWays) {
			case 0:
				System.out.println("not a valid text");
				break;
			case 1:
				for(String word : sentence)
					System.out.print(translate(word) + " ");
				System.out.println();
				break;
			default:
				System.out.println("there are " + numWays + " possible messages");
				break;
		}
		System.out.println();
	}

	/**
	 * Adds a single word into this dictionary, automatically determining
	 * the correct keypresses.
	 *
	 * @param word The word to add to the dictionary
	 */
	public void addWord(String word) {
		String keySequence = generateKeySequence(word);

		if(!numberSequenceToWords.containsKey(keySequence))
			numberSequenceToWords.put(keySequence, new TreeSet <String> ());

		numberSequenceToWords.get(keySequence).add(word);
	}

	/**
	 * Counts the number of ways that the specified key sequence can
	 * be translated with this dictionary.
	 *
	 * @param keySequence a sequence of digits to translate
	 * @return The number of ways to translate the given word
	 */
	public int countTranslations(String keySequence) {
		if(numberSequenceToWords.containsKey(keySequence))
			return numberSequenceToWords.get(keySequence).size();
		else
			return 0;
	}

	/**
	 * Translates a key sequence into a word based on this dictionary.
	 * If a sequence has multiple words, it'll return the lowest
	 * sorted lexographically word.
	 *
	 * @param keySequence a sequence of digits to translate
	 * @return The translated word
	 * @throws NullPointerException If keySequence has no mapping
	 */
	public String translate(String keySequence) {
		assert(countTranslations(keySequence) == 1);
		return numberSequenceToWords.get(keySequence).iterator().next();
	}

	/**
	 * Converts a word into the keypresses required for a cell phone.
	 *
	 * @param word The word to index
	 * @return The key sequence to recover the word
	 * @throws RuntimeException If word contains non alphanumeric characters
	 */
	private static String generateKeySequence(String word) {
		String keyPresses = "";
		for(char x : word.toCharArray())
			if(Character.isLetter(x))
				keyPresses += mapping.get(Character.toLowerCase(x));
			else if(Character.isDigit(x))
				keyPresses += x;
			else
				throw new RuntimeException("Could not process character " + x);

		return keyPresses;
	}

	private Map <String, Set <String> > numberSequenceToWords;

}
