
public class RandomSentence {
    
    static final private String[] conjunction = {"and", "or", "but", "because"};

    static final private String[] commonNoun = {"man", "woman", "fish", "elephant", "unicorn", "dog", "cat", "child", "girl", "donkey"};
    
    static final private String[] properNoun = {"Fred", "Jane", "Richar Nixon", "Miss America", "Cookie, my pitbull dog", "Bruce", "SuperMan"};
    
    static final private String[] determiner = {"a", "the", "every","some"};
    
    static final private String[] adjective = {"big", "tiny", "pretty", "bald", "small", "horrendous", "tall", "short", "fat","fit","cute", "slim"};
    
    static final private String[] intransitiveVerb = {"runs", "jumps", "talks", "sleeps a lot", "walks", "runs", "hops", "wanders"};
    
    static final private String[] transitiveVerb = {"loves", "hates", "sees", "knows", "looks for", "finds"};
    
    /*
     * The main subroutine prints some sentences, in this case 20 sentences 
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        for(int i = 1; i < 21; i++) {
            System.out.print(i +"-) ");
            sentence();
            System.out.print(".");
            System.out.println();
        }
    }
    static void sentence () {
        SimpleSentence();
        
        if (Math.random() > 0.75) {
            System.out.print(" ");
            RandomSelectfromList(conjunction);
            System.out.print(" ");
            sentence();
            
        }
    }
    
    /**
     * SimpleSentence subroutine is made out of a call to nounPhrase and
     * a verbPhrase, at the same time it is fed to the Sentence method  
     */
    
    static void SimpleSentence() {
        nounPhrase();
        verbPhrase();
    }
    /**
     * This subroutine creates a phrase by picking randomly from proper noun
     * or determiner or a common noun of the list, to pick a random word we 
     * implement the method RandomSelectfromList 
     */
    static void nounPhrase() {
        int selection =  randomInt(1,2);
        switch (selection) {
        case 1: RandomSelectfromList(properNoun);
        System.out.print(" ");
        break;
        case 2: RandomSelectfromList(determiner);
        System.out.print(" ");
        if (Math.random() > 0.5) {
            RandomSelectfromList(adjective);
            System.out.print(" ");
        }
        RandomSelectfromList(commonNoun);
        System.out.print(" ");
        if (Math.random() > 0.5) {
            System.out.print("who ");
            verbPhrase();
            System.out.print(" ");
        }
        break;
        }



    }
    /*
     * This subroutine builds a phrase by selecting items from the list of words declared
     * in the constants built randomly according to the choices given in the lab 3 part 1 
     */
    static void verbPhrase() {
        int selection = randomInt(1,4);
        switch(selection) {
        case 1: RandomSelectfromList(intransitiveVerb);
        break;
        case 2: RandomSelectfromList(transitiveVerb);
                System.out.print(" ");
                nounPhrase();
                break;
        case 3: System.out.print("is ");
                RandomSelectfromList(adjective);
                break;
        case 4: System.out.print("Believes that ");
                SimpleSentence();
                break;
        
        }
    }
    
    
    /***
     * This subroutine picks a random item from an array
     * of strings
     * @param list can be either of the array of words in the rules given
     */
    static void RandomSelectfromList(String[] list) {
        int i = (int)(Math.random()* list.length);
        System.out.print(list[i]);
    }
    
    /**
     * This subroutine generates a random number in a given interval
     * @param min minimum value
     * @param max  maximum value
     * @return a random number in the given interval
     */
    static int randomInt (int min, int max) {
        int i = (int)(Math.random() * ((max - min) + min)) + min;  
        return i;
    }

}
