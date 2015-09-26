//*
public class NormalForm {
  public static void main(String[] args) {
    NormalForm nf = GameVote.createNormalForm();

    String [] strategy = {"Vote A", "Vote B", "Vote B"};
    String[] names = {"Bob", "Mary", "Sue"};
    
    T.prn("Payoffs when strategy is (" + T.sa2s(strategy) + "): (" + T.ia2s(nf.getPayoffs(strategy)) + ").");
    for (int i=0; i<names.length; i++) {
      T.prn(names[i] + " gets " + nf.getPayoff(names[i], strategy) + " with this strategy: (" + T.sa2s(strategy) + ").");
      T.prn(names[i] + "'s best reponse(s) is(are) {" + T.sa2s(nf.bestResponse(names[i], strategy)) + "} when players do (" + T.sa2s(strategy) + ").");
      T.prn(names[i] + "'s strictly dominated strategies: (" + T.sa2s(nf.strictlyDominatedStrategies(names[i])) + ").");
      T.prn(names[i] + "'s weakly dominated strategies: (" + T.sa2s(nf.weaklyDominatedStrategies(names[i])) + ").");
      T.prn(names[i] + "'s strictly dominant strategies: (" + T.sa2s(nf.strictlyDominantStrategies(names[i])) + ").");
      T.prn(names[i] + "'s weakly dominant strategies: (" + T.sa2s(nf.weaklyDominantStrategies(names[i])) + ").");
    }
    nf.print();
  }

  private String[] names;
  private String[][] labels;
  private int payoffs[][];
  private Coordinates1toN coordinates;
  private String title;
  
  public NormalForm(String title, String[] playerNames, String[][] actionLabels, int[][] payoffs, Coordinates1toN coordinates) {
    this.title = title;
    names = playerNames;
    labels = actionLabels;
    this.payoffs = payoffs;
    this.coordinates = coordinates;
  }
  
  public String[] strictlyDominatedStrategies(String name) {
    int player = getNameIndex(name);
    String [] actions = labels[player];
    
    for (int actionIndex=0; actionIndex<actions.length; actionIndex++) {
      boolean dominated = true;
      for (int strategyIndex=0; strategyIndex<payoffs.length; strategyIndex++) {
        int[] strategy = coordinates.toNDim(strategyIndex);
        if [strategy[player] 
      }
    }
    return null;
  }
  public String reportStrictlyDominated(int who) {
    String s = "Strictly Dominated Strategies of player " + who + ":\n";
    for (int theStrategy=0; theStrategy<numberOfActions[who]; theStrategy++) {
      boolean dominated = true;
      //System.out.println("\n\nTesting Strategy " + theStrategy);
      for (int location=0; location<payoffs.length; location++) {
        int[] indices = findIndices(location);
        //System.out.println("Checking indices " + arrayToString(indices));
        if (indices[who] == theStrategy) {
          //System.out.println("Same as theStrategy");
          for (int otherStrategy=0; otherStrategy<numberOfActions[who]; otherStrategy++) {
            if (otherStrategy != theStrategy) {
              int[] temp = copy(indices);
              temp[who] = otherStrategy;
              int tloc = findLocation(temp);
              //System.out.println("Comparing " + arrayToString(temp) + " with payoff " + payoffs[tloc][who]);
              if (payoffs[location][who] >= payoffs[tloc][who]) {
                //System.out.println("Is Bigger or equal");
                dominated = false;
                break;
              }
            }
          }
          if (! dominated) {
            break;
          }
        }
      }
      if (dominated) {
        //System.out.println("****" + "Player " + who + "'s strategy number " + theStrategy + " is dominated");
        s += "\tPlayer " + who + "'s strategy number " + theStrategy + " is strictly dominated.\n";
      }
    }
    //System.out.println("######" + s + "########");
    return s;
  }

  public String[] weaklyDominatedStrategies(String name) {
    return null;
  }

  public String[] strictlyDominantStrategies(String name) {
    return null;
  }

  public String[] weaklyDominantStrategies(String name) {
    return null;
  }

  public String getTitle() {
    return title;
  }
  
  public int getNameIndex(String name) {
    for(int i=0; i<names.length; i++) {
      if (names[i].equals(name)) {
        return i;
      }
    }
    return -1;
  }
  public int getActionIndex(int player, String action) {
    for (int i=0
  }
  public String[] bestResponse(String name, String[] strategy) {
    int player = -1;
    for (int i=0; i<names.length; i++) {
      if (names[i].equals(name)) {
        player = i;
        break;
      }
    }
 
    int[] coords = strategyToCoordinates(strategy);
    coords[player] = 0;
    int[] workSpace = new int[labels[player].length];
    int maxPay = payoffs[coordinates.to1Dim(coords)][player];
    for (int j=1; j<labels[player].length; j++) {
      coords[player] = j;
      int tmpPay = payoffs[coordinates.to1Dim(coords)][player];
      if (tmpPay > maxPay) {
        maxPay = tmpPay;
      }
    }

    int[] result = null;
    for (int j=0; j<labels[player].length; j++) {
      coords[player] = j;
      if (payoffs[coordinates.to1Dim(coords)][player] == maxPay) {
        result = T.aia(result, j);
      }
    }
    
    String[] answer = new String[result.length];
    for (int i=0; i<answer.length; i++) {
      answer[i] = labels[player][result[i]];
    }
    return answer;
  }
  
  public int[] strategyToCoordinates(String[] strategy) {
    int[] coords = new int[strategy.length];
    for (int i=0; i<coords.length; i++) {
      coords[i] = strategyToIndex(i, strategy[i]);
    }
    return coords;
  }
  
  public int strategyToIndex(int player, String action) {
    for (int i=0; i<labels[player].length; i++) {
      if (labels[player][i].equals(action)) {
        return i;
      }
    }
    return -1;
  }
  
  public int[] getPayoffs(String[] strategy) {
    int[] coords = new int[strategy.length];
    for (int i=0; i<strategy.length; i++) {
      for (int j=0; j<labels[i].length; j++) {
        if (labels[i][j].equals(strategy[i])) {
          coords[i] = j;
          break;
        }
      }
    }
    return payoffs[coordinates.to1Dim(coords)];
  }
  
  public int getPayoff(String player, String[] strategy) {
    for (int i=0; i<names.length; i++) {
      if (names[i].equals(player)) {
        return getPayoffs(strategy)[i];
      }
    }
    return -1;
  }
  
  public void print() {
    int tableSize = coordinates.getNumberOfDimensions();
    T.prn("*** " + getTitle() + " ***");
    if (tableSize == 2) {
    } else if (tableSize == 3) {
      int[] strategy = new int[tableSize];
      strategy[0] = 0;
      strategy[1] = 0;
      strategy[2] = 0;
    
      // print the Z actions across the top
      for (int i=0; i<labels[2].length; i++) {
        T.pr("\t\t" + names[2] + " '" + labels[2][i] + "'\t\t");
      }
      T.prn();
      T.prn("\t\t\t" + names[1] + "\t\t\t\t\t" + names[1]);
      T.prn("\t\t" + labels[1][0] + "\t\t" + labels[1][1] + "\t\t\t" + labels[1][0] + "\t\t" + labels[1][1]);
      
      T.pr(names[0] + "\t " + labels[0][0]);
      strategy[2] = 0;
      for (int i=0; i<labels[0].length; i++) {
        strategy[0] = i;
        T.pr("\t(" + T.ia2s(payoffs[coordinates.to1Dim(strategy)]) + ")");
      }
      T.pr("\t");
      
      strategy[2] = 1;
      for (int i=0; i<labels[0].length; i++) {
        strategy[0] = i;
        T.pr("\t(" + T.ia2s(payoffs[coordinates.to1Dim(strategy)]) + ")");
      }
      T.prn();

      T.pr("\t " + labels[1][1]);
      strategy[1] = 1;
      strategy[2] = 0;
      for (int i=0; i<labels[0].length; i++) {
        strategy[0] = i;
        T.pr("\t(" + T.ia2s(payoffs[coordinates.to1Dim(strategy)]) + ")");
      }
      T.pr("\t");
           
      strategy[2] = 1;
      for (int i=0; i<labels[0].length; i++) {
        strategy[0] = i;
        T.pr("\t(" + T.ia2s(payoffs[coordinates.to1Dim(strategy)]) + ")");
      }
      T.prn();
    } else if (tableSize == 4) {
    } else {
    }
  }
}

class GameVote {
  public static NormalForm createNormalForm() {
    String title = "Voting Game";
    
    String[][] labels = {
      {"Bob", "Vote A", "Vote B"},
      {"Mary", "Vote A", "Vote B"},
      {"Sue", "Vote A", "Vote B"}
    };
    int[] actionCounts = new int[labels.length];
    for (int i=0; i<actionCounts.length; i++) {
      actionCounts[i] = labels[i].length - 1;
    }
    //T.prn(T.ia2s(actionCounts));
    
    String[] playerNames = new String[labels.length];
    for (int i=0; i<labels.length; i++) {
      playerNames[i] = labels[i][0];
    }
    //T.prn(T.sa2s(playerNames));
    
    String[][] actionNames = new String[labels.length][];
    for (int i=0; i<actionNames.length; i++) {
      actionNames[i] = new String[labels[i].length-1];
      for (int j=0; j<actionNames[i].length; j++) {
        actionNames[i][j] = labels[i][j+1];
      }
      //T.prn(T.sa2s(actionNames[i]));
    }
    Coordinates1toN coordinates = new Coordinates1toN(actionCounts);
    return new NormalForm(title, playerNames, actionNames, calculatePayoffs(coordinates), coordinates);
  }
  
  private static int[][] calculatePayoffs(Coordinates1toN coordinates){
    int[][] result = new int[coordinates.getMaxIndex()][];
    for (int index=0; index<result.length; index++) {
      result[index] = countVotesAndPay(coordinates.toNDim(index));
    }
    return result;
  }
  private static int[] countVotesAndPay(int [] votes) {
    int count = 0;
    for (int i=0; i<votes.length; i++) {
      if (votes[i] == 0) {
        count++;
      }
    }
    int[] pay = new int[votes.length];
    if (count < 2) {
      pay[0] = 0;
      pay[1] = 1;
      pay[2] = 1;
    } else {
      pay[0] = 1;
      pay[1] = 0;
      pay[2] = 0;
    }
    return pay;
  }
}

class T {
  public static String ia2s(int[] array) {
    String s = "";
    if (array.length > 0) {
      s += "" + array[0];
      for (int i=1; i<array.length; i++) {
        s += ", " + array[i];
      }
    }
    return s;
  }

  public static String sa2s(String[] array) {
    String s = "";
    if (array != null && array.length > 0) {
      s += "" + array[0];
      for (int i=1; i<array.length; i++) {
        s += ", " + array[i];
      }
    }
    return s;
  }
  
  public static int[] aia(int[] array, int value) {
    int[] result;
    if (array == null) {
      result = new int[1];
      result[0] = value;
    } else {
      result = new int[array.length+1];
      for (int i=0; i<array.length; i++) {
        result[i] = array[i];
      }
      result[array.length] = value;
    }
    return result;
  }
      
  public static void pr(String s) {
    System.out.print(s);
  }
  public static void prn() {
    System.out.println();
  }
  public static void prn(String s) {
    T.pr(s);
    T.prn();
  }
  public static void pr(int i, int space) {
    String s = "          " + i;
    T.prn(s.substring(s.length()-space));
  }
}

class Coordinates1toN {
  private int[] offsets;
  private int[] dimensions;
  private int maxIndex;
  
  public Coordinates1toN(int[] dimensionSizes) {
    dimensions = new int[dimensionSizes.length];
    offsets = new int[dimensions.length];
    dimensions[0] = dimensionSizes[0];
    offsets[0] = 1;
    maxIndex = dimensions[0];
    for (int i=1; i<offsets.length; i++) {
      dimensions[i] = dimensionSizes[i];
      offsets[i] = offsets[i-1] * dimensionSizes[i-1];
      maxIndex *= dimensions[i];
    }
    //T.prn("Offsets are: " + T.ia2s(offsets) + ".");
    //T.prn("Dimensions are: " + T.ia2s(dimensions) + ".");
    //T.prn("Max Index is: " + maxIndex);
  }
  
  public int getMaxIndex() {
    return maxIndex;
  }
  
  public int getNumberOfDimensions() {
    return dimensions.length;
  }
  
  public int to1Dim(int[] coordinates) {
    int index = 0;
    for (int i=0; i<coordinates.length; i++) {
      index += (offsets[i] * coordinates[i]);
    }
    return index;
  }

  public int[] toNDim(int index) {
    int[] coordinates = new int[dimensions.length];
    for (int i=coordinates.length-1; i>=0; i--) {
      coordinates[i] = index / offsets[i];
      index -= (coordinates[i] * offsets[i]);
    }
    return coordinates;
  }
}
/*/
public class NormalForm
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
    //System.out.println("Offsets:\n" + arrayToString(offsets));
  }
  
  public void setPayOffs(int[] choices, int[] payoffs) {
    int location = findLocation(choices);
    //System.out.println("Set payoffs (" + arrayToString(payoffs) + ") at indices (" + arrayToString(choices) + ") to location " + location);
    this.payoffs[location] = copy(payoffs);
  }
  
  private int[] findIndices(int location) {
    //System.out.print("Location " + location + " references indexed by ");
    int[] indices = new int[numberOfPlayers];
    for (int i=indices.length-1; i>=0; i--) {
      indices[i] = location / offsets[i];
      location -= (indices[i] * offsets[i]);
    }
    //System.out.println("(" + arrayToString(indices) + ") with data (" + arrayToString(payoffs[findLocation(indices)]) + ")");
    return indices;
  }
  
  private int findLocation(int[] Indices) {
    int location = 0;
    for (int i=0; i<Indices.length; i++) {
      location += (offsets[i] * Indices[i]);
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
  
  private String arrayToString(int[] array) {
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
      s += arrayToString(findIndices(i));
      for (int j=0; j<payoffs[i].length; j++) {
         s += "\t" + payoffs[i][j];
      }
      s += "\n";
    }

    s += "\n";
    return s;
  }
  
  public String reportStrictlyDominated(int who) {
    String s = "Strictly Dominated Strategies of player " + who + ":\n";
    for (int theStrategy=0; theStrategy<numberOfActions[who]; theStrategy++) {
      boolean dominated = true;
      //System.out.println("\n\nTesting Strategy " + theStrategy);
      for (int location=0; location<payoffs.length; location++) {
        int[] indices = findIndices(location);
        //System.out.println("Checking indices " + arrayToString(indices));
        if (indices[who] == theStrategy) {
          //System.out.println("Same as theStrategy");
          for (int otherStrategy=0; otherStrategy<numberOfActions[who]; otherStrategy++) {
            if (otherStrategy != theStrategy) {
              int[] temp = copy(indices);
              temp[who] = otherStrategy;
              int tloc = findLocation(temp);
              //System.out.println("Comparing " + arrayToString(temp) + " with payoff " + payoffs[tloc][who]);
              if (payoffs[location][who] >= payoffs[tloc][who]) {
                //System.out.println("Is Bigger or equal");
                dominated = false;
                break;
              }
            }
          }
          if (! dominated) {
            break;
          }
        }
      }
      if (dominated) {
        //System.out.println("****" + "Player " + who + "'s strategy number " + theStrategy + " is dominated");
        s += "\tPlayer " + who + "'s strategy number " + theStrategy + " is strictly dominated.\n";
      }
    }
    //System.out.println("######" + s + "########");
    return s;
  }
  public String reportWeaklyDominated(int who) {
    String s = "Weakly Dominated Strategies of player " + who + ":\n";
    for (int theStrategy=0; theStrategy<numberOfActions[who]; theStrategy++) {
      boolean dominated = true;
      boolean weakly = false;
      for (int location=0; location<payoffs.length; location++) {
        int[] indices = findIndices(location);
        if (indices[who] == theStrategy) {
          for (int otherStrategy=0; otherStrategy<numberOfActions[who]; otherStrategy++) {
            if (otherStrategy != theStrategy) {
              int[] temp = copy(indices);
              temp[who] = otherStrategy;
              int tloc = findLocation(temp);
              if (payoffs[location][who] > payoffs[tloc][who]) {
                dominated = false;
                break;
              } else if (payoffs[location][who] == payoffs[tloc][who]) {
                weakly = true;
              }
            }
          }
          if (! dominated) {
            break;
          }
        }
      }
      if (dominated && weakly) {
        s += "\tPlayer " + who + "'s strategy number " + theStrategy + " is weakly dominated.\n";
      }
    }
    return s;
  }
  public String reportStrictlyDominant(int who) {
    String s = "Strictly Dominant Strategies of player " + who + ":\n";
    for (int theStrategy=0; theStrategy<numberOfActions[who]; theStrategy++) {
      boolean dominant = true;
      for (int location=0; location<payoffs.length; location++) {
        int[] indices = findIndices(location);
        if (indices[who] == theStrategy) {
          for (int otherStrategy=0; otherStrategy<numberOfActions[who]; otherStrategy++) {
            if (otherStrategy != theStrategy) {
              int[] temp = copy(indices);
              temp[who] = otherStrategy;
              int tloc = findLocation(temp);
              if (payoffs[location][who] <= payoffs[tloc][who]) {
                dominant = false;
                break;
              }
            }
          }
          if (! dominant) {
            break;
          }
        }
      }
      if (dominant) {
        s += "\tPlayer " + who + "'s strategy number " + theStrategy + " is strictly dominant.\n";
      }
    }
    return s;
  }
  public String reportWeaklyDominant(int who) {
    String s = "Weakly Dominated Strategies of player " + who + ":\n";
    for (int theStrategy=0; theStrategy<numberOfActions[who]; theStrategy++) {
      boolean dominant = true;
      boolean weakly = false;
      for (int location=0; location<payoffs.length; location++) {
        int[] indices = findIndices(location);
        if (indices[who] == theStrategy) {
          for (int otherStrategy=0; otherStrategy<numberOfActions[who]; otherStrategy++) {
            if (otherStrategy != theStrategy) {
              int[] temp = copy(indices);
              temp[who] = otherStrategy;
              int tloc = findLocation(temp);
              if (payoffs[location][who] < payoffs[tloc][who]) {
                dominant = false;
                break;
              } else if (payoffs[location][who] == payoffs[tloc][who]) {
                weakly = true;
              }
            }
          }
          if (! dominant) {
            break;
          }
        }
      }
      if (dominant && weakly) {
        s += "\tPlayer " + who + "'s strategy number " + theStrategy + " is weakly dominant.\n";
      }
    }
    return s;
  }

  public String report() {
    String s = "\nDone\n";
    for (int i=0; i<numberOfPlayers; i++) {
      s += reportStrictlyDominated(i);
      s += reportWeaklyDominated(i);
      s += reportStrictlyDominant(i);
      s += reportWeaklyDominant(i);
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
    //*/
    
    /*
    int[] actions = {3, 4}; // 4 rows of three columns
    int[][] payOffData = { // note: data is represented as column row so that x,y,z is column,row,table
      {0,0},{2,1},
      {1,0},{2,2},
      {2,0},{1,5},
      {0,1},{1,4},
      {1,1},{5,3},
      {2,1},{3,3}, // change 3,3 to 0,3 to see strict dominated and to 1,3 to see weak dominated
      {0,2},{2,5}, // leave 2,5 as is to see strict dominant to 2,4 for weak dominant and to 2,3 to show no dominant
      {1,2},{4,4},
      {2,2},{0,7},
      {0,3},{3,2},
      {1,3},{4,0},
      {2,3},{0,3},
    };
    //*/
    
    /*
    int[] actions = {5, 5};
    int[][] payOffData = {
      {0,0},{0,0},
      {1,0},{1,0},
      {2,0},{2,0},
      {3,0},{3,0},
      {4,0},{4,0},
      {0,1},{0,1},
      {1,1},{1,1},
      {2,1},{2,1},
      {3,1},{3,1},
      {4,1},{0,0},
      {0,2},{0,2},
      {1,2},{1,2},
      {2,2},{2,2},
      {3,2},{0,0},
      {4,2},{0,0},
      {0,3},{0,3},
      {1,3},{1,3},
      {2,3},{0,0},
      {3,3},{0,0},
      {4,3},{0,0},
      {0,4},{0,4},
      {1,4},{0,0},
      {2,4},{0,0},
      {3,4},{0,0},
      {4,4},{0,0}
    };
    //*/

    /*
    int[] actions = {4, 4};
    int[][] payOffData = {
      {0,0},{-2,-2},
      {0,1},{0,-4},
      {0,2},{0,-4},
      {0,3},{0,-4},
      {1,0},{-4,0},
      {1,1},{0,0},
      {1,2},{0,0},
      {1,3},{0,0},
      {2,0},{-4,0},
      {2,1},{0,0},
      {2,2},{2,2},
      {2,3},{0,4},
      {3,0},{-4,0},
      {3,1},{0,0},
      {3,2},{4,0},
      {3,3},{4,4}
    };
    //*
    
    NormalForm nf = new NormalForm(actions);
    for (int i=0; i<payOffData.length; i+=2) {
      nf.setPayOffs(payOffData[i], payOffData[i+1]);
    }    
    System.out.print("The Game:\n" + nf);
    System.out.println(nf.report());
    
    int[] strategy = {0,3};
    System.out.println(nf.bestResponse(strategy, 0));
    System.out.println(nf.nashEquilibria());
    nf.show2by2();
    testTable();
  }
  
  public String bestResponse(int[] strategies, int who) {
    int chosenAction = strategies[who];
    strategies[who] = 0;
    int bestPayOff = payoffs[findLocation(strategies)][who];
    for (int i=1; i<numberOfActions[who]; i++) {
      strategies[who] = i;
      if (payoffs[findLocation(strategies)][who] > bestPayOff) {
        bestPayOff = payoffs[findLocation(strategies)][who];
      }
    }
    
    String s = "Player " + who + "'s best response to";
    for (int i=0; i<strategies.length; i++) {
      if (i == who) {
        s += " X";
      } else {
        s += " " + strategies[i];
      }
    }
    s += " is";
    for (int i=0; i<numberOfActions[who]; i++) {
      strategies[who] = i;
      if (payoffs[findLocation(strategies)][who] == bestPayOff) {
        s += " (" + arrayToString(strategies) + ")";
      }
    }
    strategies[who] = chosenAction;
    return s + "\n";
  }
  
  public String nashEquilibria() {
    String s = "The Nash Equilibrium/a is/are ";
    boolean[] nash = new boolean[payoffs.length];
    for (int i=0; i<payoffs.length; i++) {
      nash[i] = true;
      int[] strategy = findIndices(i);
      for (int j=0; j<numberOfPlayers; j++) {
        int pay = payoffs[i][j];
        int[] newStrategy = copy(strategy);
        for (int k=0; k<numberOfActions[j]; k++) {
          newStrategy[j] = k;
          if (payoffs[findLocation(newStrategy)][j] > pay) {
            nash[i] = false;
            break;
          }
        }
        if (! nash[i]) {
          break;
        }
      }
    }
    for (int i=0; i<nash.length; i++) {
      if (nash[i]) {
        s += " (" + arrayToString(findIndices(i)) + ")";
      }
    }
    return s;
  }
  public void show2by2() {
    int[] strategy = findIndices(0);
    int PlayerOne = 0;
    int PlayerTwo = 1;
    if (numberOfPlayers == 2) {
      for (int x=0; x<numberOfActions[PlayerOne]; x++) {
        strategy[PlayerOne] = x;
        for (int y=0; y<numberOfActions[PlayerTwo]; y++) {
          strategy[PlayerTwo] = y;
          System.out.print("\t" + payoffs[findLocation(strategy)][PlayerTwo] + "," + payoffs[findLocation(strategy)][PlayerOne]);
        }
        System.out.println();
      }
    } else {
      System.out.println("NOT a TWO player game (can't show as a matrix)");
    }
  }
  
  public static void testTable() {
    System.out.println("Demonstration");
    int c = 1;
    int D = 4;
    for (int y=0; y<4; y++) {
      for (int x=0; x<4; x++) {
        if (x == y) {
          System.out.print("\t" + (D*(y-c)/2) + "," + (D*(x-c)/2));
        } else if (x < y) {
          System.out.print("\t" + 0 + "," + (D*(x-c)));
        } else {
          System.out.print("\t" + (D*(y-c)) + "," + 0);
        }
      }
      System.out.println();
    }
  }
}
//*/