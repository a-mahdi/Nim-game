import java.util.Scanner;

import static java.lang.System.exit;

public class NimTree {
    static int numberOfNodes = 0;
    NimNode root;


    public NimTree() {
        root = new NimNode();
        root.minMax = 0;
        numberOfNodes++;
        root.player1=true;

    }

    public NimTree(int numOfPieces) {
        root = new NimNode(numOfPieces);
        root.minMax = 0;
        numberOfNodes++;
        root.player1=true;


    }

    public void generateGameTree()
    {
            this.generateNextMoves(root, 1);
            this.generateNextMoves(root, 2);
            if (root.numPieces%2==0)
                numberOfNodes--;
    }

    public void generateNextMoves(NimNode current, int playerValue) {
        if(current.numPieces>0) {
            if(playerValue==1) {
                current.left = new NimNode(current.numPieces,!current.player1,current.minMax,playerValue);
                numberOfNodes++;
                generateNextMoves(current.left, 1);
                if (current.numPieces>1) {
                    current.right = new NimNode(current.numPieces,! current.player1, current.minMax, 2);
                    generateNextMoves(current.right, 2);
                }
            }
            if(playerValue==2) {
                if (current.numPieces!=1) {
                    current.right = new NimNode(current.numPieces, !current.player1, current.minMax, playerValue);
                    numberOfNodes++;
                    generateNextMoves(current.right, 2);}
                    current.left = new NimNode(current.numPieces, !current.player1, current.minMax, 1);
                    generateNextMoves(current.left, 1);

            }


        }
    }
    public void assignMinMaxValues(int level){
        if (level==1)                   //for weak strategy
            assignMinMaxWeak(root);
        else {                          //for strong strategy
            assignMinMaxValuesLeaves(root);
            assignMinMaxValuesHelp1(root);
        }
    }

   public void assignMinMaxWeak(NimNode root){

       if (root==null)
           return ;
       else {
           root.minMax=(int)(Math.random()*10);
           assignMinMaxWeak(root.left);
           assignMinMaxWeak(root.right);
       }



   }

    public void assignMinMaxValuesLeaves(NimNode node){
        if (node==null)
            return;
        if (node.right==null&&node.left==null){
            if (node.player1){
                node.minMax=1;
                return;
            }else {
                node.minMax=-1;
                return;
            }

        }else
        assignMinMaxValuesLeaves(node.right);
        assignMinMaxValuesLeaves(node.left);

    }

    public void assignMinMaxValuesHelp1(NimNode root){
        if (root == null)
            return;
        root.minMax=assignMinMaxValuesHelp(root);
        assignMinMaxValuesHelp1(root.left);
        assignMinMaxValuesHelp1(root.right);


    }

    public int assignMinMaxValuesHelp(NimNode node){
            if (node.right==null&&node.left==null)
                return node.minMax;

         if (node.player1) {
             if (node.right==null)
                 return assignMinMaxValuesHelp(node.left);
             else if (node.left==null)
                 return assignMinMaxValuesHelp(node.right);
             else
            return Math.max(assignMinMaxValuesHelp(node.left), assignMinMaxValuesHelp(node.right));
        }
        else {
             if (node.right==null)
                return assignMinMaxValuesHelp(node.left);
             else if (node.left==null)
                 return assignMinMaxValuesHelp(node.right);
             else
                 return Math.min(assignMinMaxValuesHelp(node.left), assignMinMaxValuesHelp(node.right));

        }



    }

    public void isWin(NimNode temp,boolean first){
        if (temp.numPieces==0){
                if (temp.player1 == false && first == true || temp.player1 == true && first == false)
                    System.out.println("congratulations");
                else
                    System.out.println("you cannot beat me!..try again");

                exit(-1);

            }

    }

    public void showStatistics(NimNode temp){
        System.out.println("the number of Pieces are: " + temp.numPieces);
        System.out.println("the number of nodes are:  "+numberOfNodes(temp,0));
       System.out.println("the height of the tree is: "+height(temp));



    }

    public int height(NimNode node){
        if (node==null)
            return 0;
        else return heightHelper(node) ;


    }
    public int heightHelper(NimNode node){
        if (node==null)
            return 0;


        return 1 + Math.max(heightHelper(node.left),heightHelper(node.right));


    }


    public int numberOfNodes(NimNode temp,int value){

        if (temp==null)
            return 0;
        else

           return 1+ numberOfNodes(temp.left,value+1) + numberOfNodes(temp.right,value+1);




    }






    public void initNim() {
         NimNode root = new NimNode();
        Scanner scan = new Scanner(System.in);
        System.out.println("enter the number of pieces to play");
        root.numPieces=scan.nextInt();
        root.player1=true;



    }



    public static void main(String[] args) {


        Scanner scan = new Scanner(System.in);
        boolean first;
        System.out.println("Hello ...nice to meet you ... I think you cannot beat me...try your best \n" +
                "in this game the one who takes the last piece will win \nyou can choose either one or two only");
        System.out.println("who do you want to start:\n 1.you 2.the computer");
        if (scan.nextInt()==1)
             first=true;
        else
            first=false;


        System.out.println("enter the number of pieces to play with ");
        int numPieces = scan.nextInt();
        System.out.println("enter the level of the computer 1.weak 2.strong ");
        int level = scan.nextInt();

        NimTree t = new NimTree(numPieces);
        t.generateGameTree();
        t.assignMinMaxValues(level);
        NimNode temp = t.root;
        t.showStatistics(temp);



        if (first) {
            int play;
            do {
                System.out.println("\n enter 3 to display the hint or choose the number of Pieces you want to take..1 or 2  ");
                 play = scan.nextInt();
                if (temp.left==null)
                    temp=temp.right;
                else if (temp.right==null)
                    temp=temp.left;
                else if (play == 1)
                    temp = temp.left;
                else if (play == 2)
                    temp = temp.right;
                else {
                    if (first) { //look for the min
                        if (temp.right==null)
                            System.out.println("choose 1");
                        else if (temp.left==null)
                            System.out.println("choose 2");
                        else if (temp.right.minMax < temp.left.minMax)
                            System.out.println("choose 2");
                        else System.out.println("choose 1");

                    } else {//look for the max
                        if (temp.right.minMax > temp.left.minMax)
                            System.out.println("choose 2");
                        else System.out.println("choose 1");

                    }

                }
            } while (play == 3);
            t.showStatistics(temp);
            t.isWin(temp,first);


        }
          do {


              System.out.println("\nthe computer turn:");
              if (!first) {
                  if (temp.left==null){
                      temp=temp.right;
                      System.out.println("the computer choice is:2");
                  }
                  else if (temp.right==null){
                      temp=temp.left;
                      System.out.println("the computer choice is:1");
                  }
                  else if (temp.right.minMax < temp.left.minMax) {
                      System.out.println("the computer choice is:2");
                      temp = temp.right;
                  } else {
                      temp = temp.left;
                      System.out.println("the computer choice is:1");
                  }
              } else {
                  if (temp.left==null)
                      temp=temp.right;
                  else if (temp.right==null)
                      temp=temp.left;
                  else if (temp.right.minMax > temp.left.minMax) {
                      temp = temp.right;
                      System.out.println("the computer choice is:2");

                  } else {
                      temp = temp.left;
                      System.out.println("the computer choice is: 1");

                  }

              }
              t.showStatistics(temp);
              t.isWin(temp,first);

              int play;
              do {
                  System.out.println("\nenter 3 to display the hint or choose the number of Pieces you want to take..1 or 2  ");
                   play = scan.nextInt();
                  if (temp.left==null)
                      temp=temp.right;
                  else if (temp.right==null)
                      temp=temp.left;
                  else if (play == 1)
                      temp = temp.left;
                  else if (play == 2)
                      temp = temp.right;
                  else {
                      if (temp.right==null)
                          System.out.println("choose 1");
                      else if (temp.left==null)
                          System.out.println("choose 2");
                     else if (first) {
                          if (temp.right.minMax < temp.left.minMax)
                              System.out.println("choose 2");
                          else System.out.println("choose 1");

                      } else {
                          if (temp.right.minMax > temp.left.minMax)
                              System.out.println("choose 2");
                          else System.out.println("choose 1");

                      }

                  }
              } while (play==3);
              t.showStatistics(temp);
              t.isWin(temp,first);


          }while (temp.numPieces>0);










    }






    }
