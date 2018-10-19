
import fr.enseeiht.danck.voice_analyzer.Field;

import java.io.IOException;

public class myDTWtest {
//protected static final int MFCCLength = 13;

    public static void main(String[] args) throws IOException, InterruptedException {
        myDTW dtw = new myDTW();

        String nomF1 = "/corpus/dronevolant_nonbruite/F01_avance.csv";
        String nomF2 = "/myAudio/avance.csv";
        Field f1 = dtw.getFieldMFCCs(nomF1);
        Field f2 = dtw.getFieldMFCCs(nomF2);
        float distance = dtw.calcDistanceField(f1, f2);

        dtw.matriceConfusion("corpusReduit/","myAudio/");

    }
}

