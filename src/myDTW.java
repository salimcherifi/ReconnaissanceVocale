import fr.enseeiht.danck.voice_analyzer.*;
import fr.enseeiht.danck.voice_analyzer.defaults.DTWHelperDefault;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class myDTW extends DTWHelper {

    public float DTWDistance(Field unknown, Field known) {
        // Methode qui calcule le score de la DTW
        // entre 2 ensembles de MFCC

        int w0 = 1;
        int w1 = 1;
        int w2 = 1;
        myMFCCdistance myMFCC = new myMFCCdistance();
        float[][] dtw = new float[unknown.getLength() + 1][known.getLength() + 1];
        dtw[0][0] = 0.f;

        for (int j = 1; j < known.getLength() + 1; j++) {
            dtw[0][j] = Float.MAX_VALUE;
        }
        for (int j = 1; j < unknown.getLength() + 1; j++) {
            dtw[j][0] = Float.MAX_VALUE;

        }


        for (int i = 1; i < unknown.getLength() + 1; i++) {
            for (int j = 1; j < known.getLength() + 1; j++) {
                // d[i][j] -> Distance entre les 2 valeurs
                float d = myMFCC.distance(unknown.getMFCC(i - 1), known.getMFCC(j - 1));
                dtw[i][j] = Math.min(dtw[i - 1][j] + w0 * d, Math.min(dtw[i - 1][j - 1] + w1 * d, dtw[i][j - 1] + w2 * d));
            }
        }
        return (dtw[unknown.getLength()][known.getLength()] / (known.getLength() + unknown.getLength()));
    }

    public int FieldLength(String fileName) throws IOException {
        int counter = 0;
        File file = new File(System.getProperty("user.dir") + fileName);
        for (String line : Files.readAllLines(file.toPath(), Charset.defaultCharset())) {
            counter++;
        }
        return 2 * Math.floorDiv(counter, 512);
    }

    public Field getFieldMFCCs(String fichier) throws IOException, InterruptedException {

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

    public float calcDistanceField(Field f1, Field f2) {
        DTWHelper myDTWHelper = new myDTW();
        DTWHelper DTWHelperDefault = new DTWHelperDefault();
        float distance = myDTWHelper.DTWDistance(f1, f2);
        return distance;
    }


    public String[] getFilesFromFolder(String folderPath, String choice) {
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();
        List<String> myFolders = new ArrayList<>();
        List<String> myFiles = new ArrayList<>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                myFiles.add(listOfFiles[i].getName());
            } else {
                myFolders.add(listOfFiles[i].getName());
            }
        }
        if (choice.equals("file")) {
            return myFiles.toArray(new String[0]);
        } else if (choice.equals("folder")) {
            return myFolders.toArray(new String[0]);
        } else {
            return null;
        }
    }














    //Comparer un fichier avec un dossier

    public float distanceFolders(String file1, String folder2) throws IOException, InterruptedException {

        String[] files2 = getFilesFromFolder(folder2, "file");
        Field fieldFile1 = getFieldMFCCs(file1);
        int folder2Length = files2.length;

        float valMin = Float.MAX_VALUE;
        for (int i = 0; i < folder2Length; i++) {
            System.out.println(file1 + "       "+ files2[i]);
            Field fileFromFolder = getFieldMFCCs("/" + folder2 + "/" + files2[i]);
            float distance = calcDistanceField(fieldFile1, fileFromFolder);
            if (distance < valMin) {
                valMin = distance;
            }
        }

        return valMin;
    }


    public float[][] matriceConfusion(String folderRef, String folderTest) throws IOException, InterruptedException {
        int nbOrdre = 11;
        String[] references = new String[nbOrdre];
        references = getFilesFromFolder(folderRef, "folder");

        String[] tests = new String[nbOrdre];
        tests = getFilesFromFolder(folderTest, "folder");

        float[][] matriceConf = new float[nbOrdre][nbOrdre];
        float min;
        float value;
        int referencesOfMin = 0;
        int testsOfMin = 0;

        for (int i = 0; i < references.length; i++) {
            min = Float.MAX_VALUE;
            referencesOfMin = 0;
            testsOfMin = 0;
            for (int j = 0; j < tests.length; j++) {
                value = distanceFolders(folderRef + tests[j], folderRef + references[i]);

                if (value < min) {
                    min = value;
                    referencesOfMin = i;
                    testsOfMin = j;
                }
            }
            matriceConf[referencesOfMin][testsOfMin] += 1;
        }

        return matriceConf;
    }
}
