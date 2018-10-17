import fr.enseeiht.danck.voice_analyzer.DTWHelper;
import fr.enseeiht.danck.voice_analyzer.Field;

import java.io.File;

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


    public String getFilesFromFolder(String folderPath) {
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();
        StringBuffer files = new StringBuffer();


        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                files.append(listOfFiles[i].getName());
            }
        }
        return (files.toString());
    }


    public void distanceFolders(String folder1, String folder2){
        int folder1Length = new File(folder1).listFiles().length;
        int folder2Length = new File(folder2).listFiles().length;

        for (int i = 0; i < folder1Length; i++) {
            for (int j = 0; j < folder2Length; j++) {
                
            }
        }
    }

    public float[][] matriceConfusion(String folderRef, String folderTest) {
        return new float[0][];
    }

}

