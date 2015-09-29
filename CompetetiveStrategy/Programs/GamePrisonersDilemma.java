public class GamePrisonersDilemma {
  private static String title = "Prisoner's Dilemma Game";
    
  private static String[][] labels = {
    {"Prsn A", "Loyal", "Betray"},
    {"Prsn B", "Loyal", "Betray"}
  };

  private static Coordinates1toN coordinates;
  private static String[] playerNames;
  private static int[] actionCounts;
  private static String[][] actionNames;
  private static int[][] payoffs;
  
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public static void main(String[] args) {
    NormalForm nf = GamePrisonersDilemma.createNormalForm();

    String [] strategy = {"Loyal", "Betray"};
    String[] names = {"Prsn B", "Prsn B"};
    
    T.prn("Payoffs when strategy is (" + T.sa2s(strategy) + "): (" + T.ia2s(nf.getPayoffs(strategy)) + ").");
    T.prn(names[0] + " gets " + nf.getPayoff(names[0], strategy) + " when players do (" + T.sa2s(strategy) + ").");
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
  private static int[][] calculatePayoffs(){
    int T = 0;
    int R = -1;
    int P = -5;
    int S = -10;
    /// result locations 0 , 1,  2 , 3 == maps to coords (0,0)(0,1)(1,0)(1,1) ... recall we are doing colum row (except for print which prints row, col
    int[][] result = {{R,R}, {T,S}, {S,T}, {P,P}};
    return result;
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
    for (int i=0; i<result.length; i++) {
      result[i] = labels[i].length - 1;
    }
    return result;
  }
}
