package abstraction.eq4Transformateur2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import abstraction.eq8Romu.contratsCadres.Echeancier;
import abstraction.eq8Romu.filiere.Filiere;
import abstraction.eq8Romu.general.Journal;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Gamme;
import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
//auteur Jad
public abstract class Transformateur2Transfo<I> extends Transformateur2Stock {
	
//	protected double rdt=Filiere.LA_FILIERE.getIndicateur("rendement").getValeur();
//	protected double prix_transfo= Filiere.LA_FILIERE.getIndicateur("coutTransformation").getValeur();
//	protected double prix_ori=Filiere.LA_FILIERE.getIndicateur("coutOriginal").getValeur();
//	protected double cap=Filiere.LA_FILIERE.getIndicateur("seuilTransformation").getValeur();
	
	private Journal journalTransfo;
	private Stock<ChocolatDeMarque> commandes;
	private Stock<ChocolatDeMarque> commandes_retard;
	protected double rdt;
	protected double prix_transfo;
	protected double prix_ori;
	protected double cap;
	
	
	
	public void next() {//EN V1 on ne transforme que de façon arbitraire
		
		super.next();
		NewCap=cap;
		this.GetCommandes(mesContratEnTantQueVendeur);
		//il faut régler les qauntités transformées pour chaque types de fèves
		
		//Les transformations non originales courtes
		this.transfo(0.01*cap, false, "courte",Feve.FEVE_BASSE);
		this.transfo(0.01*cap,false,"courte",Feve.FEVE_MOYENNE);
		this.transfo(0.01*cap,false,"courte",Feve.FEVE_MOYENNE_BIO_EQUITABLE);
		this.transfo(0.01*cap,false,"courte",Feve.FEVE_HAUTE);
		this.transfo(0.01*cap,false,"courte",Feve.FEVE_MOYENNE_BIO_EQUITABLE);
		
		//les transformations originales courtes
		this.transfo(0.01*cap, true, "courte",Feve.FEVE_BASSE);
		this.transfo(0.01*cap,true,"courte",Feve.FEVE_MOYENNE);
		this.transfo(0.01*cap,true,"courte",Feve.FEVE_MOYENNE_BIO_EQUITABLE);
		this.transfo(0.01*cap,true,"courte",Feve.FEVE_HAUTE);
		this.transfo(0.01*cap,true,"courte",Feve.FEVE_MOYENNE_BIO_EQUITABLE);
		
		//les transformations originales longues
		this.transfo(0.1*cap, true, "longue",Feve.FEVE_BASSE);
		this.transfo(0.1*cap,true,"longue",Feve.FEVE_MOYENNE);
		this.transfo(0.1*cap,true,"longue",Feve.FEVE_MOYENNE_BIO_EQUITABLE);
		
		//les transformations non originales longues
		this.transfo(0.1*cap, false, "longue",Feve.FEVE_BASSE);
		this.transfo(0.1*cap,false,"longue",Feve.FEVE_MOYENNE);
		this.transfo(0.1*cap,false,"longue",Feve.FEVE_MOYENNE_BIO_EQUITABLE);
		
		Stock<ChocolatDeMarque> s=this.commandes;
		
				
	}
	public void initialiser() {
		super.initialiser();
		cap=Filiere.LA_FILIERE.getIndicateur("seuiTransformation").getValeur();
		rdt=Filiere.LA_FILIERE.getIndicateur("rendement").getValeur();
		prix_transfo=Filiere.LA_FILIERE.getIndicateur("coutTransformation").getValeur();
		prix_ori=Filiere.LA_FILIERE.getIndicateur("coutOriginal").getValeur();
		
	}
	
	public Transformateur2Transfo() {
		super();
		this.journalTransfo=new Journal("O'ptites Transformations",this);
		this.commandes = new Stock<ChocolatDeMarque>();
		
	}

	//renvoie une HashMap des chocolats de marques et de la quatité à livrer de ces derniers 
	public void GetCommandes(List<ExemplaireContratCadre> CC) {///POUR LA V2
		Stock comm=new Stock<ChocolatDeMarque>();
		for(ExemplaireContratCadre c:CC) {
			
			comm.ajouter(((ChocolatDeMarque)(c.getProduit())), c.getQuantiteALivrerAuStep());
			this.commandes=comm;
		}
		
		
		
	}
	
	
	//trouve la meilleur combinaison (qui minimise les coûts et si possible a une stratégie)
	//de transformation (types de fèves et de tranfos)
	//pour honorer les commandes
	//Commence par remplir less commandes les plus anciennes
	//honnore les commande par date croissante jusqu'à que ce ne soit plus possible (stock ou capacité de production)

	public void bestCombi(String fev) {//POUR LA V2
	//initialisation des variables:
		//transformations courtes originales
		double COBQ=0.0;
		double COMQ=0.0;
		double COMQBE=0.0;
		double COHQ=0.0;
		double COHQBE=0.0;
		//transformations courtes non originales
		double CBQ=0.0;
		double CMQ=0.0;
		double CMQBE=0.0;
		double CHQ=0.0;
		double CHQBE=0.0;
		//transformations longues originales
		double LOBQ=0.0;
		double LOMQ=0.0;
		double LOMQBE=0.0;
		//transformations longues non originales
		double LBQ=0.0;
		double LMQ=0.0;
		double LMQBE=0.0;
		
		
		
		
		
		

		
		
		
		
		
	}
	
	//Dans cette seconde version la transformation ne fonctionne plus en mode tout/rien mais fait le maximum possible
	public void transfo(double qt,boolean ori, String trans,Feve f){//qt est la quantité de CHOCOLAT voulue
		//Vérifie quel type de transformation
		//Vérifie la capacité bancaire
		//Vérifie le stock de fèves
		//vérifie capacité de production
		//Baisse le stock de fèves de qt
		//Augmente le stock de chocolat de rdt*qt (et modifie la qualité si longue)
		//Ajoute ou non l'originalité
		//Débite le compte bancaire
		Gamme g=f.getGamme();
		double s=0;
		if(ori) {//indicateur d'originalité
			s=1;
		}
		
		if (trans.equals("longue")){//dans le cas d'une transformation longue
			double max_feves=Math.min(this.getStockfeve().getQuantite(f), (1/rdt)*qt);//Récupération du max de fèves que l'on peut utilisé
			double max_stock=Math.min(this.getNotreCapaciteStockage()/2-this.getStockchocolatdemarque().getStocktotal(), max_feves);
			double max_prod=Math.min(max_stock, NewCap);//Choix du maximum qui peut être transformé (soit la capacité de production restante soit le nombre de fève dispo)

			
			if(max_prod>0) {
				if(Filiere.LA_FILIERE.getBanque().verifierCapacitePaiement(this, this.cryptogramme, max_prod*(prix_transfo+prix_ori*s))) {//(1/rdt)*qt est la quantité de fève nécessaire pour obtenir qt de chocolat
							
							NewCap-=(1/rdt)*qt;//mise à jour de la capacité de production

							this.getStockfeve().enlever(f,max_prod);//baisse le stock de feves	
							this.getStockchocolatdemarque().ajouter(this.fevechocoplus(f), max_prod);//augmente le stock de chocolat de marque
							Filiere.LA_FILIERE.getBanque().virer(this, this.cryptogramme, Filiere.LA_FILIERE.getBanque(), max_prod*(prix_transfo+s*prix_ori*s));//paye
							journalTransfo.ajouter("Transformation longue de " +max_prod+" kg de "+f+" en "+qt+"kg de"+this.fevechoco(f).toString()+ " pour "+max_prod*(prix_transfo+prix_ori*s)+"€");
						
					}	
				else if(this.getMaxPayable(trans, ori)>0 && max_prod>0) {//On récupère le maximum qu'on puisse payer
					double max_paybale=this.getMaxPayable(trans, ori);
					NewCap-=max_paybale;//mise à jour de la capacité de production
					this.getStockfeve().enlever(f,max_paybale);//baisse le stock de feves
					this.getStockchocolatdemarque().ajouter(this.fevechoco(f), max_paybale);//augmente le stock de chocolat
					journalTransfo.ajouter("Transformation Courte de " +max_paybale+" kg de "+f+" en "+this.fevechoco(f).toString()+ " pour "+max_prod*(prix_transfo+prix_ori*s)+"€");
					Filiere.LA_FILIERE.getBanque().virer(this, this.cryptogramme, Filiere.LA_FILIERE.getBanque(), max_paybale*(prix_transfo+s*prix_ori*s));//paye
				

				}
				}
				
		}
		if (trans.equals("courte")) {

			double max_feves=Math.min(this.getStockfeve().getQuantite(f), qt);//Récupération du max de fèves que l'on peut utilisé
			double max_stock=Math.min(this.getNotreCapaciteStockage()/2-this.getStockchocolatdemarque().getStocktotal(), max_feves);
			double max_prod=Math.min(max_stock, NewCap);//Choix du maximum qui peut être transformé (soit la capacité de production restante soit le nombre de fève dispo)
				
			if(max_prod>0 ) {
					
				if(Filiere.LA_FILIERE.getBanque().verifierCapacitePaiement(this, this.cryptogramme, max_prod*(prix_transfo+prix_ori*s))) {//max prod est le maximum capable d'être produit pour lequel on peut payer
						NewCap-=max_prod;//mise à jour de la capacité de production
						this.getStockfeve().enlever(f,max_prod);//baisse le stock de feves
						this.getStockchocolatdemarque().ajouter(this.fevechoco(f), max_prod);//augmente le stock de chocolat
						journalTransfo.ajouter("Transformation Courte de " +max_prod+" kg de "+f+" en "+this.fevechoco(f).toString()+ " pour "+max_prod*(prix_transfo+prix_ori*s)+"€");
						Filiere.LA_FILIERE.getBanque().virer(this, this.cryptogramme, Filiere.LA_FILIERE.getBanque(), max_prod*(prix_transfo+s*prix_ori*s));//paye
					
						
						

					}
				else if(this.getMaxPayable(trans, ori)>0) {//On récupère le maximum qu'on puisse payer
					double max_paybale=this.getMaxPayable(trans, ori);
					NewCap-=max_paybale;//mise à jour de la capacité de production
					this.getStockfeve().enlever(f,max_paybale);//baisse le stock de feves
					this.getStockchocolatdemarque().ajouter(this.fevechoco(f), max_paybale);//augmente le stock de chocolat
					journalTransfo.ajouter("Transformation Courte de " +max_paybale+" kg de "+f+" en "+this.fevechoco(f).toString()+ " pour "+max_prod*(prix_transfo+prix_ori*s)+"€");
					Filiere.LA_FILIERE.getBanque().virer(this, this.cryptogramme, Filiere.LA_FILIERE.getBanque(), max_paybale*(prix_transfo+s*prix_ori*s));//paye
				
				}
			}
					
					
	}
		}
		
	
	//est utile pour les transfor courte : renvoie le chocolat de marque correspondant à la qualité des fèves
	public ChocolatDeMarque fevechoco(Feve f) {
		if(f.getGamme().equals(Gamme.BASSE)) {
			return new ChocolatDeMarque(Chocolat.BQ,this.getMarquesChocolat().get(0));
		} else if (f.getGamme().equals(Gamme.MOYENNE) && !f.isBioEquitable()){
			return new ChocolatDeMarque(Chocolat.MQ,super.getMarquesChocolat().get(1));
		}
		else if(f.getGamme().equals(Gamme.MOYENNE) && f.isBioEquitable()) {
			return new ChocolatDeMarque(Chocolat.MQ_BE,super.getMarquesChocolat().get(2));
		}
		else if(f.getGamme().equals(Gamme.HAUTE) && !f.isBioEquitable()) {
			return new ChocolatDeMarque(Chocolat.HQ,super.getMarquesChocolat().get(3));
		}
		else if(f.getGamme().equals(Gamme.HAUTE) && f.isBioEquitable()) {
			return new ChocolatDeMarque(Chocolat.HQ_BE,super.getMarquesChocolat().get(4));
		}
		return null;
	}
	
	
	//est utile pour la transfo longue : renvoie le chocolat de marque de qualité supérieur à la fève
	public ChocolatDeMarque fevechocoplus(Feve f) {
		if(f.getGamme().equals(Gamme.BASSE)) {
			return new ChocolatDeMarque(Chocolat.MQ,super.getMarquesChocolat().get(1));
		} else if (f.getGamme().equals(Gamme.MOYENNE) && !f.isBioEquitable()){
			return new ChocolatDeMarque(Chocolat.HQ,super.getMarquesChocolat().get(3));			
		}
		else if (f.getGamme().equals(Gamme.MOYENNE) && f.isBioEquitable()) {
			return new ChocolatDeMarque(Chocolat.HQ_BE,super.getMarquesChocolat().get(4));
		}
		return null;
	}

	public Journal getJournalTransfo() {
		return journalTransfo;
	}

	
	
	
	public double getMaxPayable(String trans,boolean b) {
		double s=0;
		if(b) {
			s=1;
		}
		if(trans.equals("court")) {
			return Filiere.LA_FILIERE.getBanque().getSolde(this, this.cryptogramme)/(prix_transfo+s*prix_ori);
		}
		else if (trans.equals("longue")) {
			return Filiere.LA_FILIERE.getBanque().getSolde(this, this.cryptogramme)/((prix_transfo+s*prix_ori)*rdt);
		}
		return 0.0;

	}
		
		
}
	


	
	


