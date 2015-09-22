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
      offsets[i] = offsets[i-1] * numberOfActions[i-1];
    }
    System.out.println("Offsets:\n" + dumpArray(offsets));
  }
  
  public void setPayOffs(int[] choices, int[] payoffs) {
    int location = findLocation(choices);
    System.out.println("Set Location " + location);
    this.payoffs[location] = copy(payoffs);
  }
  
  private int[] findIndeces(int location) {
    int[] indeces = new int[numberOfPlayers];
    for (int i=indeces.length-1,j=0; i>=0; j++,i--) {
      indeces[j] = location / offsets[i];
      location -= (indeces[j] * offsets[i]);
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
      //s += dumpArray(payoffs[i]);
      for (int j=0; j<payoffs[i].length; j++) {
         s += "\t" + payoffs[i][j];
      }
      s += "\n";
    }

    s += "\n";
    return s;
  }
  
  public String reportStrictlyDominated(int who) {
    String s = "Strictly Dominated Strategies:\n";
    boolean[] checked = new boolean[payoffs.length];
    for (int i=0; i<checked.length; i++) {
      checked[i] = false;
    }
    for (int k=0; k<checked.length; k++) {
      if (! checked[k]) {
        int[] strategyA = findIndeces(k);
        int[] strategyB = findIndeces(k);
    
        boolean dominated = true;
        for (int i=0; i<numberOfActions[who]; i++) {
          strategyA[who] = i;
          checked[findLocation(strategyA)] = true;
          for (int j=0; j<numberOfActions[who]; j++) {
            if (i != j) {
              strategyB[who] = j;
              if (payoffs[findLocation(strategyA)][who] >= payoffs[findLocation(strategyB)][who]) {
                dominated = false;
              }
            } else {
              dominated = false;
            }
          }
          if (dominated) {
            s += "Player " + who + "'s strategy number " + i + " is strictly dominated\n";
          }
        }
      }
    }
    return s;
  }
  public String reportWeaklyDominated(int who) {
    String s = "Weakly Dominated Strategies:\n";
    boolean[] checked = new boolean[payoffs.length];
    for (int i=0; i<checked.length; i++) {
      checked[i] = false;
    }
    for (int k=0; k<checked.length; k++) {
      if (! checked[k]) {
        int[] strategyA = findIndeces(k);
        int[] strategyB = findIndeces(k);
    
        for (int i=0; i<numberOfActions[who]; i++) {
          strategyA[who] = i;
          checked[findLocation(strategyA)] = true;
          boolean dominated = true;
          for (int j=0; j<numberOfActions[who]; j++) {
            if (i != j) {
              strategyB[who] = j;
              if (payoffs[findLocation(strategyA)][who] > payoffs[findLocation(strategyB)][who]) {
                dominated = false;
              }
            }
          }
          if (dominated) {
            s += "Player " + "ABCDEFG".substring(who,who+1) + "'s strategy number " + i + " is weakly dominated (" + dumpArray(strategyA) + ")\n";
          }
        }
      }
    }
    return s;
  }
  public void reportStrictlyDominant(int who) {
  }
  public void reportWeaklyDominant(int who) {
  }
  public String report() {
    String s = "\nDone";
    for (int i=0; i<numberOfPlayers; i++) {
      s = reportStrictlyDominated(i);
      s += reportWeaklyDominated(i);
      reportWeaklyDominant(i);
      reportStrictlyDominant(i);
    }
    return s;
  }

  public static void main(String[] args) {
    /*
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
    */
    int[] actions = {4, 3};
    int[][] payOffData = {
      {0,0},{1,2},
      {0,1},{2,2},
      {0,2},{5,1},
      {1,0},{4,1},
      {1,1},{3,5},
      {1,2},{3,3},
      {2,0},{5,2},
      {2,1},{4,4},
      {2,2},{7,0},
      {3,0},{2,3},
      {3,1},{0,4},
      {3,2},{3,0},
    };

    NormalForm nf = new NormalForm(actions);
    for (int i=0; i<payOffData.length; i+=2) {
      nf.setPayOffs(payOffData[i], payOffData[i+1]);
    }    
    System.out.print("The Game:\n" + nf);
    System.out.println(nf.report());
  }
}