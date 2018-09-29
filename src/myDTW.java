import fr.enseeiht.danck.voice_analyzer.DTWHelper;
import fr.enseeiht.danck.voice_analyzer.Field;

public class myDTW extends DTWHelper {

	@Override
	public float DTWDistance(Field unknown, Field known) {
		// Methode qui calcule le score de la DTW
		// entre 2 ensembles de MFCC
//
//        int w0 = 1;
//        int w1 = 1;
//        int w2 = 1;
//
//        float [][] dtw = new Float([unknown.getLength()+1][known.getLength()+1]);
//        dtw[0][0] = 0.f;
//
//        for(int j = 0; j < unknown.getLength()+1; j++){
//            dtw[0][j] = Float.MAX_VALUE;
//        }
//
//        for(int i = 0; i < unknown.getLength(); i++){
//            dtw[i][0] = Float.MAX_VALUE;
//            for(int j = 0; j < known.getLength(); j++){
//                // d[i][j] -> Distance entre les 2 valeurs
//
//                //dtw[i][j] = Math.min(dtw[i-1][j] + w0 * d,Math.min(dtw[i-1][j-1] + w1 * d, dtw[i][j-1] + w2 * d));
//            }
//        }
//
//		return 0;
        return 0;
	}
}
