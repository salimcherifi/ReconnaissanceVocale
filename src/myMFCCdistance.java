import fr.enseeiht.danck.voice_analyzer.MFCC;
import fr.enseeiht.danck.voice_analyzer.MFCCHelper;

public class myMFCCdistance extends MFCCHelper {



	public float distance(MFCC mfcc1, MFCC mfcc2){
		float c = 0.f;
		for (int i = 0; i < 13; i++) {
			c += Math.pow(mfcc2.getCoef(i)-mfcc1.getCoef(i),2);
		}
		return (float)Math.sqrt(c);

	}

	@Override
	public float norm(MFCC mfcc) {
		// retourne la valeur de mesure de la MFCC (coef d'indice 0 dans la MFCC) 
		// cette mesure permet de determiner s'il s'agit d'un mot ou d'un silence
		
		return mfcc.getCoef(0);
	}

	@Override
	public MFCC unnoise(MFCC mfcc, MFCC noise) {
		// supprime le bruit de la MFCC passee en parametre
		// soustrait chaque coef du bruit a chaque coef du la MFCC 
		// passee en parametre
		float[] newMFCCtab = new float[13];
		for (int i = 0; i < 13; i++) {
			newMFCCtab[i] = mfcc.getCoef(i)-noise.getCoef(i);
		}
		MFCC newMFCC = new MFCC(newMFCCtab,null);
		return newMFCC;
	}

}
