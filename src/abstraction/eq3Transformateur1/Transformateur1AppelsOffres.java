package abstraction.eq3Transformateur1;

import java.util.HashMap;
import java.util.List;

import abstraction.eq8Romu.appelsOffres.IVendeurAO;
import abstraction.eq8Romu.appelsOffres.PropositionAchatAO;
import abstraction.eq8Romu.appelsOffres.SuperviseurVentesAO;
import abstraction.eq8Romu.filiere.Filiere;
import abstraction.eq8Romu.produits.Chocolat;

import abstraction.eq8Romu.produits.ChocolatDeMarque;




public class Transformateur1AppelsOffres extends Transformateur1ContratCadreAcheteur implements IVendeurAO {



	protected String marque;
	protected SuperviseurVentesAO superviseurAO;
	
	public Transformateur1AppelsOffres() { 
		super();
	}
	/** auteur Ilyas */
	public void initialiser() {
		super.initialiser();
		this.superviseurAO = (SuperviseurVentesAO)(Filiere.LA_FILIERE.getActeur("Sup.AO"));}
	
	public void next() {
		super.next();
		journal.ajouter("test journal Appeldoffres");
	}

	/** renvoie la meilleure proposition si celle-ci satisfait au vendeur; auteur Ilyas 
	/** on veut faire par type de chocolat */

	public PropositionAchatAO choisir(List<PropositionAchatAO> propositions) {
		
		if (propositions==null) {
			journal.ajouter("Il n y a pas de propositions d'achat par appel d'offre");
			return null;
		} else {
			journal.ajouter("propositions : "+propositions);
			PropositionAchatAO retenue = propositions.get(0);
			if (retenue.getPrixKg()>this.prixVenteMin.get(propositions.get(0).getOffre().getChocolat().getChocolat())){
				journal.ajouter("  --> je choisis l'offre "+retenue);
				return retenue;
			} else {
				journal.ajouter("  --> je ne retiens rien");
				
				return null;
			}
		}
	}
		}
