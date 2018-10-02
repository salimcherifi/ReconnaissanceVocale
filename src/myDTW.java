import fr.enseeiht.danck.voice_analyzer.DTWHelper;
import fr.enseeiht.danck.voice_analyzer.Field;

public class myDTW extends DTWHelper {

	public float DTWDistance(Field unknown, Field known) {
		// Methode qui calcule le score de la DTW
		// entre 2 ensembles de MFCC
//test

        int w0 = 1;
        int w1 = 2;
        int w2 = 1;
        myMFCCdistance myMFCC = new myMFCCdistance();
        float [][] dtw = new float[unknown.getLength()+1][known.getLength()+1];
        dtw[0][0] = 0.f;

        for(int j = 1; j < unknown.getLength()+1; j++){
            dtw[0][j] = Float.MAX_VALUE;
            dtw[j][0] = Float.MAX_VALUE;
        }
      

        for(int i = 1; i < unknown.getLength()+1; i++){
            for(int j = 1; j < known.getLength()+1; j++){
                // d[i][j] -> Distance entre les 2 valeurs
				float d = myMFCC.distance(unknown.getMFCC(i-1),known.getMFCC(j-1));
                dtw[i][j] = Math.min(dtw[i-1][j] + w0 * d,Math.min(dtw[i-1][j-1] + w1 * d, dtw[i][j-1] + w2 * d));
            }
        }
caca
		return(dtw[unknown.getLength()][known.getLength()]/(known.getLength()+unknown.getLength()));
	}
}
