public class NormalForm {
  private int numberOfPlayers;
  private int[] numberOfActions; // player n has numberOfActions[n] actions
  private int[][] payoffs; // this is a table/n dimensional space that is product(numberOfActions) big and each space has numberOfPlayers payoffs (one for each)
  private int[] offsets;
  
  NormalForm(int[] playerActionCount) {
    numberOfPlayers = playerActionCount.length;
    numberOfActions = copy(playerActionCount);
    int totalElements = 1;
    for (int i=0; i<playerActionCount.length; i++) {
      totalElements *= numberOfActions[i];
    }
    payoffs = new int[totalElements][];
    offsets = new int[numberOfPlayers];
    offsets[0] = 1;
    for (int i=1; i<numberOfPlayers; i++) {
      offsets[i] = offsets[i-1] * numberOfActions[i];
    }
    //System.out.println("Offsets:\n" + dumpArray(offsets));
  }
  
  public void setPayOffs(int[] choices, int[] payoffs) {
    int location = findLocation(choices);
    this.payoffs[location] = copy(payoffs);
  }
  
  private int[] findIndeces(int location) {
    int[] indeces = new int[numberOfPlayers];
    for (int i=indeces.length-1; i>=0; i--) {
      indeces[i] = location / offsets[i];
      location -= (indeces[i] * offsets[i]);
    }
    //System.out.println(dumpArray(indeces));
    return indeces;
  }
  
  private int findLocation(int[] indeces) {
    int location = 0;
    for (int i=0; i<indeces.length; i++) {
      location += (offsets[i] * indeces[i]);
    }
    return location;
  }
  
  private int[] copy(int[] array) {
    int[] result = new int[array.length];
    for (int i=0; i<array.length; i++) {
      result[i] = array[i];
    }
    return result;
  }
  
  private String dumpArray(int[] array) {
    String s = "";
    for (int i=0; i<array.length; i++) {
      s += " " + array[i];
    }
    return s;
  }
  public String toString() {
    String s = "Player Count:" + numberOfPlayers + "\n";
    s += "Actions per Player:";
    for (int i=0; i<numberOfActions.length; i++) {
      s += " " + numberOfActions[i];
    }
    s += "\nPayoffs per Action Set:\nActions\tPayoffs\n A B C\tA\tB\tC\n";
    for (int i=0; i<payoffs.length; i++) {
      s += dumpArray(findIndeces(i));
      for (int j=0; j<payoffs[i].length; j++) {
         s += "\t" + payoffs[i][j];
      }
      s += "\n";
    }
    s += "\n";
    return s;
  }
  
  public void reportStrictlyDominated(int who) {
    int[] strategy = new int[numberOfPlayers];
    for (int i=0; i<strategy.length; i++) {
      strategy[i] = 0;
    }
    for (int i=0; i<numberOfActions[who]; i++) {
      
    }
  }
  public void reportWeaklyDominated(int who) {
  }
  public void reportStrictlyDominant(int who) {
  }
  public void reportWeaklyDominant(int who) {
  }
  
  public static void main(String[] args) {
    int[] actions = {2, 2, 2};
    int[][] payOffData = {
      {0,0,0},{1,0,0},
      {0,0,1},{1,0,0},
      {0,1,0},{1,0,0},
      {0,1,1},{0,1,1},
      {1,0,0},{1,0,0},
      {1,0,1},{0,1,1},
      {1,1,0},{0,1,1},
      {1,1,1},{0,1,1}
    };

    NormalForm nf = new NormalForm(actions);
    for (int i=0; i<payOffData.length; i+=2) {
      nf.setPayOffs(payOffData[i], payOffData[i+1]);
    }    
    System.out.print("The Game:\n" + nf);
    for (int i=0; i<numberOfPlayers; i++) {
      reportStrictlyDominated(i);
      reportWeaklyDominated(i);
      reportWeaklyDominant(i);
      reportStrictlyDominant(i);
    }
  }
}