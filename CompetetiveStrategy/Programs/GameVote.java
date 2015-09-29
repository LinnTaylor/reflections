public class GameVote {
  private static String title = "Voting Game";
    
  private static String[][] labels = {
    {"Bob", "Vote A", "Vote B"},
    {"Mary", "Vote A", "Vote B"},
    {"Sue", "Vote A", "Vote B"}
  };

  private static Coordinates1toN coordinates;
  private static String[] playerNames;
  private static int[] actionCounts;
  private static String[][] actionNames;
  private static int[][] payoffs;
  
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public static void main(String[] args) {
    NormalForm nf = GameVote.createNormalForm();

    String [] strategy = {"Vote A", "Vote B", "Vote B"};
    String[] names = {"Bob", "Mary", "Sue"};
    
    T.prn("Payoffs when strategy is (" + T.sa2s(strategy) + "): (" + T.ia2s(nf.getPayoffs(strategy)) + ").");
    T.prn(names[0] + " gets " + nf.getPayoff(names[0], strategy) + " with this strategy: (" + T.sa2s(strategy) + ").");
    T.prn(names[0] + "'s best reponse(s) is(are) {" + T.sa2s(nf.bestResponse(names[0], strategy)) + "} when players do (" + T.sa2s(strategy) + ").");
    T.prn();
    nf.print();
    T.prn();

    String[] answer;
    for (int i=0; i<names.length; i++) {
      answer = nf.strictlyDominatedStrategies(names[i]);
      if (answer != null) {
        T.prn(names[i] + "'s strictly dominated strategies: (" + T.sa2s(answer) + ").");
      }
      answer = nf.weaklyDominatedStrategies(names[i]);
      if (answer != null) {
        T.prn(names[i] + "'s weakly dominated strategies: (" + T.sa2s(answer) + ").");
      }
      answer = nf.strictlyDominantStrategies(names[i]);
      if (answer != null) {
        T.prn(names[i] + "'s strictly dominant strategies: (" + T.sa2s(answer) + ").");
      }
      answer = nf.weaklyDominantStrategies(names[i]);
      if (answer != null) {
        T.prn(names[i] + "'s weakly dominant strategies: (" + T.sa2s(answer) + ").");
      }
    }
    T.prn();
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  private static int[][] calculatePayoffs() {
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
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public static NormalForm createNormalForm() {
    playerNames = collectPlayerNames(labels);
    actionCounts = collectActionCounts(labels);
    actionNames = collectActionLabels(labels);
    coordinates = new Coordinates1toN(actionCounts);
    payoffs = calculatePayoffs();
    return new NormalForm(title, playerNames, actionNames, payoffs, coordinates);
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  private static String[][] collectActionLabels(String[][] labels) {
    String[][] result = new String[labels.length][];
    for (int i=0; i<labels.length; i++) {
      result[i] = new String[labels[i].length-1];
      for (int j=0; j<result[i].length; j++) {
        result[i][j] = labels[i][j+1];
      }
    }
    return result;
  }
  
  private static String[] collectPlayerNames(String[][] labels) {
    String[] result = new String[labels.length];
    for (int i=0; i<labels.length; i++) {
      result[i] = labels[i][0];
    }
    return result;
  }

  private static int[] collectActionCounts(String[][] labels) {
    int[] result = new int[labels.length];
    for (int i=0; i<labels.length; i++) {
      result[i] = labels[i].length - 1;
    }
    return result;
  }
}
