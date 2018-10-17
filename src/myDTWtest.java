import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import fr.enseeiht.danck.voice_analyzer.DTWHelper;
import fr.enseeiht.danck.voice_analyzer.Extractor;
import fr.enseeiht.danck.voice_analyzer.Field;
import fr.enseeiht.danck.voice_analyzer.MFCC;
import fr.enseeiht.danck.voice_analyzer.WindowMaker;
import fr.enseeiht.danck.voice_analyzer.defaults.DTWHelperDefault;

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

        dtw.matriceConfusion("./corpus/dronevolant_nonbruite","./corpus/dronevolant_nonbruite");



    }
}

