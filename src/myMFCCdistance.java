import fr.enseeiht.danck.voice_analyzer.MFCC;
import fr.enseeiht.danck.voice_analyzer.MFCCHelper;

public class myMFCCdistance extends MFCCHelper {

	@Override
	public float distance(MFCC mfcc1, MFCC mfcc2) {
		// calcule la distance entre 2 MFCC
		float dMfcc1 = distanceVectorielle(mfcc1);
		float dMfcc2 = distanceVectorielle(mfcc2);

		return Math.abs(dMfcc1-dMfcc2);
	}

	private float distanceVectorielle(MFCC mfcc){
	    float prodScal = 0.f;
		for(int i = 0; i < mfcc.getLength(); i++){
            prodScal += mfcc.getCoef(i);
		}
		return prodScal;
	}

	@Override
	public float norm(MFCC mfcc) {
		// retourne la valeur de mesure de la MFCC (coef d'indice 0 dans la MFCC) 
		// cette mesure permet de determiner s'il s'agit d'un mot ou d'un silence
		
		return 0;
	}

	@Override
	public MFCC unnoise(MFCC mfcc, MFCC noise) {
		// supprime le bruit de la MFCC passee en parametre
		// soustrait chaque coef du bruit a chaque coef du la MFCC 
		// passee en parametre
		
		return null;
	}

}
