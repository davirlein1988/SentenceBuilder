
/*
  The last verse of a well-known nursery rhyme:

    This is the farmer sowing his corn
    That kept the rooster that crowed in the morn
    That waked the judge all shaven and shorn
    That married the man all tattered and torn
    That kissed the maiden all forlorn
    That milked the cow with the crumpled horn
    That tossed the dog 
    That worried the cat
    That chased the rat
    That ate the cheese
    That lay in the house that Jack built. 

  Some rules that capture the syntax of this verse:

    <sentence> ::= <simple_sentence> [ and <sentence> ]

    <simple_sentence> ::=  this is [ <noun_phrase> ] the house that Jack built

    <noun_phrase> ::= the <noun> [ <modifier> ] that <verb> [ <noun_phrase> ]

    <noun> ::= farmer | rooster | judge | man | maiden | cow | dog | cat | cheese

    <verb> ::= kept | waked | married | milked | tossed | chased | lay in

    <modifier> ::= that crowed in the morn | all shaven and shorn |
                    all tattered and torn | all forlorn | with the crumpled horn

  This program implements these rules to generate random sentences.  All the
  verses of the rhyme can be generated, plus a lot of sentences that make no
  sense (but still follow the syntax).   Note that an optional item like
  [ <modifier> ] has a chance of being used, depending on the value of some
  randomly generated number.

  The program generates and outputs one random sentence every three seconds until
  it is halted (for example, by typing Control-C in the terminal window where it is
  running).
 */


public class SimpleRandomSentences {

	static final private String[] nouns = { "farmer", "rooster", "judge", "man", "maiden",
		"cow", "dog", "cat", "cheese" };

	static final private String[] verbs = { "kept", "waked", "married",
		"milked", "tossed", "chased", "lay in" };

	static final private String[] modifiers = { "that crowed in the morn", "sowing his corn", 
		"all shaven and shorn",
		"all forlorn", "with the crumpled horn" };                                   

	/**
	 * The main routine prints out one random sentence every three
	 * seconds, forever (or until the program is killed).
	 */
	public static void main(String[] args) {
		while (true) {
			randomSentence();
			System.out.println(".\n\n");
			try {
				Thread.sleep(3000);
			}
			catch (InterruptedException e) {
			}
		}
	}

	/**
	 * Generate a random sentence, following the grammar rule for a sentence.
	 */
	static void randomSentence() {

		/* A simple sentence */

		randomSimpleSentence();
		
		/* Optionally, "and" followed by another simple sentence.*/
		
		if (Math.random() > 0.75) { // 25% of sentences continue with another clause.
			System.out.print(" and ");
			randomSimpleSentence();
		}
	}

	/**
	 * Generate a random simple_sentence, following the grammar rule for a simple_sentence.
	 */
	static void randomSimpleSentence() {
		
		/* "this is", optionally followed by a noun phrase, followed by "the house that Jack built. */
		
		System.out.print("this is ");
		if (Math.random() > 0.15) { // 85% of sentences have a noun phrase. 
			randomNounPhrase();
		}
		System.out.print("the house that Jack built");
	}

	/**
	 * Generates a random noun_phrase, following the grammar rule for a noun_phrase.
	 */
	static void randomNounPhrase() {
		
		/* A random noun. */
		
		int n = (int)(Math.random()*nouns.length);
		System.out.print("the " + nouns[n]);
		
		/* Optionally, a modifier. */
		
		if (Math.random() > 0.75) { // 25% chance of having a modifier.
			int m = (int)(Math.random()*modifiers.length);
			System.out.print(" " + modifiers[m]);
		}
		
		/* "that", followed by a random verb */
		
		int v = (int)(Math.random()*verbs.length);
		System.out.print(" that " + verbs[v] + " ");
		
		/* Another random noun phrase */
		
		if (Math.random() > 0.5) {  // 50% chance of having another noun phrase.
			randomNounPhrase();
		}

	}

}