
import java.io.IOException;

public class myDTWtest {
//protected static final int MFCCLength = 13;

    public static void main(String[] args) throws IOException, InterruptedException {
        myDTW dtw = new myDTW();

//        String nomF1 = "/corpus/dronevolant_nonbruite/F01_avance.csv";
//        String nomF2 = "/myAudio/avance.csv";
//        Field f1 = dtw.getFieldMFCCs(nomF1);
//        Field f2 = dtw.getFieldMFCCs(nomF2);
//        float distance = dtw.calcDistanceField(f1, f2);
//
//
//        System.out.println("Distance entre "+nomF1+"      et      "+nomF2+" :        " + distance);
        int nbOrdre = 12;
        float[][] matrice = dtw.matriceConfusion("corpus/dronevolant_nonbruite/","corpus/dronevolant_nonbruite/", nbOrdre);
        for (int i = 0; i < nbOrdre; i++) {
            for (int j = 0; j < nbOrdre; j++) {
                System.out.print(" "+matrice[i][j]+" ");
            }
            System.out.println();
        }
    }
}

