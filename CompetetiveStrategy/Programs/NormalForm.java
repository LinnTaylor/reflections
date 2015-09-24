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
    //*
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
    NormalForm nf = new NormalForm(actions);
    for (int i=0; i<payOffData.length; i+=2) {
      nf.setPayOffs(payOffData[i], payOffData[i+1]);
    }    
    System.out.print("The Game:\n" + nf);
    System.out.println(nf.report());
    
    int[] strategy = {0,3};
    System.out.println(nf.bestResponse(strategy, 0));
    System.out.println(nf.nashEquilibria());
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
}