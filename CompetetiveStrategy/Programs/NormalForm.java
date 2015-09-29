//*
public class NormalForm {
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
  
  public String[] pureNashEquilibria() {
    boolean[] nash = new boolean[payoffs.length];
    String[] result = null;
    
    for (int i=0; i<payoffs.length; i++) {
      nash[i] = true;
      int[] strategy = coordinates.toNDim(i);
      for (int j=0; j<names.length; j++) {
        int pay = payoffs[i][j];
        int[] newStrategy = T.cia(strategy);
        for (int k=0; k<labels[j].length; k++) {
          newStrategy[j] = k;
          if (payoffs[coordinates.to1Dim(newStrategy)][j] > pay) {
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
        result = T.asa(result, T.sa2s(coordinatesToStrategy(coordinates.toNDim(i))));
      }
    }
    return result;
  }

  public String mixedNashEquilibria() {
    String result = null;
    return result;
  }
  
  public String[] strictlyDominatedStrategies(String name) {
    int who = getNameIndex(name);
    String [] actions = labels[who];
    String[] result = null;
    
    for (int theStrategy=0; theStrategy<actions.length; theStrategy++) {
      boolean dominated = true;
      for (int location=0; location<payoffs.length; location++) {
        int[] indices = coordinates.toNDim(location);
        if (indices[who] == theStrategy) {
          for (int otherStrategy=0; otherStrategy<actions.length; otherStrategy++) {
            if (otherStrategy != theStrategy) {
              indices[who] = otherStrategy;
              if (payoffs[location][who] >= payoffs[coordinates.to1Dim(indices)][who]) {
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
        result = T.asa(result, actions[theStrategy]);
      }
    }
    return result;
  }

  public String[] weaklyDominatedStrategies(String name) {
    int who = getNameIndex(name);
    String [] actions = labels[who];
    String[] result = null;

    for (int theStrategy=0; theStrategy<actions.length; theStrategy++) {
      boolean dominated = true;
      boolean weakly = false;
      for (int location=0; location<payoffs.length; location++) {
        int[] indices = coordinates.toNDim(location);
        if (indices[who] == theStrategy) {
          for (int otherStrategy=0; otherStrategy<actions.length; otherStrategy++) {
            if (otherStrategy != theStrategy) {
              int[] temp = T.cia(indices);
              temp[who] = otherStrategy;
              int tloc = coordinates.to1Dim(temp);
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
        result = T.asa(result, actions[theStrategy]);
      }
    }
    return result;
  }

  public String[] strictlyDominantStrategies(String name) {
    int who = getNameIndex(name);
    String [] actions = labels[who];
    String[] result = null;

    for (int theStrategy=0; theStrategy<actions.length; theStrategy++) {
      boolean dominant = true;
      for (int location=0; location<payoffs.length; location++) {
        int[] indices = coordinates.toNDim(location);
        if (indices[who] == theStrategy) {
          for (int otherStrategy=0; otherStrategy<actions.length; otherStrategy++) {
            if (otherStrategy != theStrategy) {
              int[] temp = T.cia(indices);
              temp[who] = otherStrategy;
              int tloc = coordinates.to1Dim(temp);
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
        result = T.asa(result, actions[theStrategy]);
      }
    }
    return result;
  }

  public String[] weaklyDominantStrategies(String name) {
    int who = getNameIndex(name);
    String [] actions = labels[who];
    String[] result = null;

    for (int theStrategy=0; theStrategy<actions.length; theStrategy++) {
      boolean dominant = true;
      boolean weakly = false;
      for (int location=0; location<payoffs.length; location++) {
        int[] indices = coordinates.toNDim(location);
        if (indices[who] == theStrategy) {
          for (int otherStrategy=0; otherStrategy<actions.length; otherStrategy++) {
            if (otherStrategy != theStrategy) {
              int[] temp = T.cia(indices);
              temp[who] = otherStrategy;
              int tloc = coordinates.to1Dim(temp);
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
        result = T.asa(result, actions[theStrategy]);
      }
    }
    return result;
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
    for (int i=0; i<labels[player].length; i++) {
      if (labels[player][i].equals(action)) {
        return i;
      }
    }
    return -1;
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

  public String[] coordinatesToStrategy(int[] coords) {
    String[] strategies = new String[coords.length];
    for (int i=0; i<strategies.length; i++) {
      strategies[i] = labels[i][coords[i]];
    }
    return strategies;
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
    switch (coordinates.getNumberOfDimensions()) {
      case 2:
        print2Player();
        break;
      case 3:
        print3Player();
        break;
      case 4:
        break;
      default:
        break;
    }
    T.pr("Nash Equilibrium: {");
    String[] ne = pureNashEquilibria();
    for (int i=0; i<ne.length; i++) {
      if (i != 0) {
        T.pr(", ");
      }
      T.pr("(" + ne[i] + ")");
    }
    T.prn("}");
  }

  private void print2Player() {
    int[] strategy = new int[coordinates.getNumberOfDimensions()];

    T.prn("\t\t" + names[1]);
    for (int i=0; i<labels[1].length; i++) {
      T.pr("\t\t" + labels[1][i]);
    }
    T.prn();
    
    T.pr(names[0]);
    for (int i=0; i<labels[0].length; i++) {
      T.pr("\t" + labels[0][i]);
      strategy[0] = i;
      for (int j=0; j<labels[1].length; j++) {
        strategy[1] = j;
        //*
        T.pr("\t(" + T.ia2s(payoffs[coordinates.to1Dim(strategy)]) + ")");
        /*/
        T.pr("(" + i + ", " + j + ") = " + coordinates.to1Dim(strategy));
        //*/
      }
      T.prn();
    }
    T.prn();
  }
  
  private void print3Player() {
    int[] strategy = new int[coordinates.getNumberOfDimensions()];
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
    T.prn();
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

  public static String[] asa(String[] array, String value) {
    String[] result;
    if (array == null) {
      result = new String[1];
      result[0] = value;
    } else {
      result = new String[array.length+1];
      for (int i=0; i<array.length; i++) {
        result[i] = array[i];
      }
      result[array.length] = value;
    }
    return result;
  }
      
  public static int[] cia(int[] array) {
    int[] result = new int[array.length];
    for (int i=0; i<array.length; i++) {
      result[i] = array[i];
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
//*/