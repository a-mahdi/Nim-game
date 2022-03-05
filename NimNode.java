public class NimNode {
    int numPieces;
    boolean player1;
    int minMax;
    NimNode right=null;
    NimNode left=null;



    public NimNode() {
        NimNode right;
        NimNode left;
        minMax=0;
        player1=true;
    }

    public NimNode(int numPieces, boolean player1, int minMax,int value) {
        this.numPieces = numPieces-value;
        this.player1 = player1;
        this.minMax = minMax;

    }

    public NimNode(int numPieces,int value) {
        this.numPieces = numPieces-value;

    }

    public NimNode(int numOfPieces) {
       this.numPieces=numOfPieces;

    }


    public int computeNumberOfPieces(){
return numPieces;

    }

    @Override
    public String toString() {
        return "NimNode{" +
                " numPieces= " + numPieces +
                ", player1=" + player1 +
                ", minMax=" + minMax +
                '}';
    }


}
