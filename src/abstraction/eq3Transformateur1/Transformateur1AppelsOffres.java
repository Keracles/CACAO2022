package abstraction.eq3Transformateur1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.eq8Romu.appelsOffres.IVendeurAO;
import abstraction.eq8Romu.appelsOffres.OffreVente;
import abstraction.eq8Romu.appelsOffres.PropositionAchatAO;
import abstraction.eq8Romu.appelsOffres.SuperviseurVentesAO;
import abstraction.eq8Romu.filiere.Filiere;
import abstraction.eq8Romu.produits.Chocolat;

import abstraction.eq8Romu.produits.ChocolatDeMarque;




public class Transformateur1AppelsOffres extends Transformateur1ContratCadreAcheteur implements IVendeurAO {
	protected String marque;
	protected SuperviseurVentesAO superviseurAO;
	protected ArrayList<PropositionAchatAO> listeAO; // correspond à la liste des AO signés. elle est reset en debut de tour et actualisee lros des AO juste avant qu'on recalcule quantiteDemandeeChoco puisqu'elle est necessaire pour elle
	
	
	public Transformateur1AppelsOffres() { 
		super();
	}
	/** auteur Ilyas */
	public void initialiser() {
		super.initialiser();
		this.superviseurAO = (SuperviseurVentesAO)(Filiere.LA_FILIERE.getActeur("Sup.AO"));}
	
	public void next() {
		super.next();
		
	}

	/** renvoie la meilleure proposition si celle-ci satisfait au vendeur; auteur Ilyas 
	/** on veut faire par type de chocolat */

	public PropositionAchatAO choisir(List<PropositionAchatAO> propositions) {
		if (propositions==null) {
			journalAO.ajouter("Il n'y a pas de propositions d'achat par appel d'offre.");
			return null;
		} else {
			journalAO.ajouter("Ca va on a des propositions");
			PropositionAchatAO retenue = propositions.get(0);
			if (retenue.getPrixKg()>this.prixVenteMin.get(propositions.get(0).getOffre().getChocolat().getChocolat())){
				journalAO.ajouter("  --> je choisis l'offre "+ retenue.getOffre() + " de l'acheteur "+ retenue.getAcheteur().getNom() + " au prix "+ retenue.getPrixKg() + " au kg ");
				listeAO.add(retenue);
				return retenue;
			} else {
				journalAO.ajouter("  --> je ne retiens rien en fin de compte");
				
				return null;
			}
		}
	}
		}
