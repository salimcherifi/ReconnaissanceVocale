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

    static int FieldLength(String fileName) throws IOException {
        int counter = 0;
        File file = new File(System.getProperty("user.dir") + fileName);
        for (String line : Files.readAllLines(file.toPath(), Charset.defaultCharset())) {
            counter++;
        }
        return 2 * Math.floorDiv(counter, 512);
    }

    static Field getFieldMFCCs(String fichier) throws IOException, InterruptedException{

        int MFCCLength;
        DTWHelper myDTWHelper = new myDTW();
        DTWHelper DTWHelperDefault = new DTWHelperDefault();


        // Appel a l'extracteur par defaut (calcul des MFCC)
        Extractor extractor = Extractor.getExtractor();

        // Etape 1. Lecture de Alpha
        List<String> files = new ArrayList<>();
        files.add(fichier);
        WindowMaker windowMaker = new MultipleFileWindowMaker(files);

        // Etape 2. Recuperation des MFCC du mot Alpha
        MFCCLength = FieldLength(fichier);
        MFCC[] mfccs = new MFCC[MFCCLength];

        for (int i = 0; i < mfccs.length; i++) {
            mfccs[i] = extractor.nextMFCC(windowMaker);
        }
        // Etape 3. Construction du Field (ensemble de MFCC) de alpha
        Field fichierField = new Field(mfccs);

        return fichierField;
    }



    public static void main(String[] args) throws IOException, InterruptedException {

        DTWHelper myDTWHelper = new myDTW();
        DTWHelper DTWHelperDefault = new DTWHelperDefault();
        String nomF1 = "/corpus/dronevolant_nonbruite/F01_avance.csv";
        String nomF2 = "/myAudio/avance.csv";
        Field f1 = getFieldMFCCs(nomF1);
        Field f2 = getFieldMFCCs(nomF2);
        float distance = myDTWHelper.DTWDistance(f1, f2);


        System.out.println("Distance entre "+nomF1+"      et      "+nomF2+" :        " + distance);

    }
}

