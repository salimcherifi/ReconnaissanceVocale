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

    private int FieldLength(String fileName) throws IOException {
        int counter = 0;
        File file = new File(System.getProperty("user.dir") + fileName);
        for (String line : Files.readAllLines(file.toPath(), Charset.defaultCharset())) {
            counter++;
        }
        return 2 * Math.floorDiv(counter, 512);
    }

    public Field getFieldMFCCs(String fichier) throws IOException, InterruptedException {

        int MFCCLength;


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

        return new Field(mfccs);
    }

    public float calcDistanceField(Field f1, Field f2) {
        DTWHelper myDTWHelper = new myDTW();
        return myDTWHelper.DTWDistance(f1, f2);
    }


    public String[] getFilesFromFolder(String folderPath, String choice) {
        if (!(new File(folderPath).isDirectory())){
            System.err.println("Ceci n'est pas un dossier!");
            System.exit(-1);
        }
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();
        List<String> myFolders = new ArrayList<>();
        List<String> myFiles = new ArrayList<>();

        assert listOfFiles != null;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                myFiles.add(listOfFile.getName());
            } else {
                myFolders.add(listOfFile.getName());
            }
        }
        switch (choice) {
            case "file":
                return myFiles.toArray(new String[0]);
            case "folder":
                return myFolders.toArray(new String[0]);
            default:
                return null;
        }
    }







    //Comparer un fichier avec un dossier

    private float distanceFolders(String file1, String folder2) throws IOException, InterruptedException {

        String[] files2 = getFilesFromFolder(folder2, "file");
        Field fieldFile1 = getFieldMFCCs("/"+file1);
        int folder2Length = files2.length;

        float valMin = Float.MAX_VALUE;
        for (String aFiles2 : files2) {
            System.out.println(file1 + "       "+ aFiles2);
            Field fileFromFolder = getFieldMFCCs("/" + folder2 + "/" + aFiles2);
            float distance = calcDistanceField(fieldFile1, fileFromFolder);
            if (distance < valMin) {
                valMin = distance;
            }
        }

        return valMin;
    }


    public float[][] matriceConfusion(String folderRef, String folderTest) throws IOException, InterruptedException {
        int x = 0;
        int y = 0;
        int nbOrdre = 50; //Taille max du tableau
        float min;
        float distance = 0.f;

        String[] references = new String[nbOrdre];
        String[] tests = new String[nbOrdre];

        float[][] matriceConf = new float[nbOrdre][nbOrdre];


        references = getFilesFromFolder(folderRef, "folder");
        tests = getFilesFromFolder(folderTest, "folder");
        int referencesLength = references.length;
        int testsLength = tests.length;
        String dossiers = new String();
        for (int i = 0; i < testsLength; i++) {
            String[] testsFiles = new String[nbOrdre];
            testsFiles = getFilesFromFolder(folderTest+tests[i], "file");
            min = Float.MAX_VALUE;
            for (String testsFile : testsFiles) {
                for (int j = 0; j < referencesLength; j++) {
                    String pathFile = folderTest + tests[i] + "/" + testsFile;
                    String pathFolder = folderRef + references[j];
                    distance = distanceFolders(pathFile, pathFolder);
                    if (distance < min) {
                        min = distance;
                        x = j;
                        y = i;
                    }
                }
                matriceConf[x][y] += 1;
            }
        }

        for (String reference : references) {
            System.out.println(reference);
        }
        System.out.println();
        for (int i = 0; i < referencesLength; i++) {
            for (int j = 0; j < testsLength; j++) {
                System.out.print(matriceConf[i][j]+"    ");
            }
            System.out.println();
        }
        return matriceConf;
    }
}
