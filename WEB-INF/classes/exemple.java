            // if(consommation > listeTarif[i].getConsomLimite()){
            //     prixTranche[0] = listeTarif[i].getConsomLimite() * listeTarif[i].getPrixUnitaire();
            //     resteConso = consommation - listeTarif[i].getConsomLimite();
            //     resteTranche = listeTarif[i+1].getConsomLimite() - listeTarif[i].getConsomLimite();

            //     if(resteConso > resteTranche){
            //         prixTranche[1] = resteTranche * listeTarif[i+1].getPrixUnitaire();
            //         resteConso = resteConso - listeTarif[i+1].getConsomLimite();
                    
            //         prixTranche[2] = resteConso * listeTarif[i+2].getPrixUnitaire();
            //     }else{
            //         prixTranche[1] = resteConso * listeTarif[i+1].getPrixUnitaire();
            //         prixTranche[2] = 0;
            //     } 
            // }else{
            //     prixTranche[0] = listeTarif[i].getPrixUnitaire();
            //     prixTranche[1] = 0;
            //     prixTranche[2] = 0;
            // }


    //         public DetailFacture getDetailFacture(String id_facture, String id_prelevement) throws Exception {
	// 	// rend un prelevement en detail facture
	// 	Utils u = new Utils();
	// 	Prelevement p = null;
	// 	// Compteur cmp = null; // compteur en relation au prelevement
	// 	Client client = null; // le client concerné
	// 	DetailTarif tafClient = null; // tarif du client, tarif en 2 ou en 3?
		
	// 	Connection c = null;
	// 	try {
	// 		c = (new DBConnect()).getConnection();
			
	// 		// le prelevement concerné
	// 		Object[] obj = u.selectGeneral(c, "Prelevement", new Prelevement(), "id_prelevement='"+id_prelevement+"'");
	// 		p = (Prelevement) obj[0];
			
	// 		// le compteur concerné
	// 		// cmp = p.getLinkedCompteur(c);
	// 		client = p.getLinkedClient(c);
	// 		tafClient = client.getLinkedDetailTarif(c);
			
	// 	} catch(Exception e) {
	// 		throw e;
	// 	} finally {
	// 		if(c != null) c.close();
	// 	}
	// 	// fabrication du detail		
	// 	int conso = p.getConsommation();
	// 	int nbrTranche = tafClient.getNbr_tranche();
		
	// 	int[][] data = computeTARIF(conso, 1);
		
	// 	int[] prixFinalParTranche = data[0]; //prix final par tranche
	// 	int[] limitesConso = data[1]; //table de la limite des consommations
	// 	int[] prixUnitaires = data[2]; //table des prix unitaires
		
	// 	// cas ou la consommation est faible :: ne depasse pas la premiere limite
	// 	if(conso <= limitesConso[0]) {
	// 		prixFinalParTranche[0] = conso * prixUnitaires[0];
	// 	}
		
	// 	// cas particulier :: le client est en 2 tranches
	// 	if(nbrTranche == 2) {
	// 		limitesConso[1] = 0; limitesConso[2] = 0;
	// 		prixUnitaires[2] = 0;
	// 		prixFinalParTranche[0] = conso; prixFinalParTranche[1] = 0; prixFinalParTranche[2] = 0;
	// 		if(conso > limitesConso[0]) {
	// 			prixFinalParTranche[0] = limitesConso[0];
	// 			prixFinalParTranche[1] = conso-prixFinalParTranche[0];
	// 		}
	// 		prixFinalParTranche[0] *= prixUnitaires[0];
	// 		prixFinalParTranche[1] *= prixUnitaires[1];
	// 	}		
		
	// 	int total = prixFinalParTranche[0] + prixFinalParTranche[1] + prixFinalParTranche[2];
	// 	DetailFacture det = new DetailFacture(
	// 					"'DetailFacture_' || DetailFactureSequence.nextval", 
	// 					id_facture, 
	// 					id_prelevement, 
	// 					nbrTranche, 
						
	// 					limitesConso[0], 
	// 					limitesConso[1], 
						
	// 					prixUnitaires[0], 
	// 					prixUnitaires[1], 
	// 					prixUnitaires[2], 
						
	// 					prixFinalParTranche[0], 
	// 					prixFinalParTranche0[1], 
	// 					prixFinalParTranche[2], 
						
	// 					total, 
	// 					conso
	// 				);
		
	// 	return det;
	// }


    // public int[][] computeTARIF(int cons, int type) throws Exception {
	// 	Utils u = new Utils();
	// 	Prelevement p = null;
	// 	Tarif[] tarifs = null;
	// 	try {
	// 		Object[] obj = u.selectGeneral("Tarif", new Tarif(), "type_tarif="+type+"");
	// 		tarifs = new Tarif[obj.length];
	// 		for(int i=0; i < tarifs.length;i++) {
	// 			tarifs[i] = (Tarif) obj[i];
	// 		}
			
	// 	} catch(Exception e) {
	// 		throw e;
	// 	}
		
	// 	int[] prixUnit = new int[0];
	// 	int[] consLIMITE = new int[0];
	// 	prixUnit = new int[tarifs.length]; //prix unitaire par tranche
	// 	consLIMITE = new int[tarifs.length]; // conso limites par tranche
	// 	int k=0;
	// 	for( ; k < tarifs.length;k++) {
	// 		prixUnit[k] = tarifs[k].getPrix_unit();
	// 		consLIMITE[k] = tarifs[k].getConsom_limite();
	// 	}
		
	// 	int[] detailParTranche = calculerTranche(prixUnit, consLIMITE, cons);
	// 	int[][] resfinal = new int[3][consLIMITE.length];
		
	// 	resfinal[0] = detailParTranche; //prix final par tranche
	// 	resfinal[1] = consLIMITE; //table de la limite des consommations
	// 	resfinal[2] = prixUnit; //table des prix unitaires
		
	// 	return resfinal;
	// }


    // public int[] calculerTranche(int[] prixUnit, int[] consLIMITE, int cons) {
	// 	//fonction généralisée permettant de raisoner en n tranches, n un entier > 1
	// 	int[] res = new int[consLIMITE.length];
	// 	if(cons < consLIMITE[0]) {
	// 		res[0] = cons;
	// 		return res;
	// 	} else {
	// 		int ntranche = 1;
	// 		for(int i=0; i < consLIMITE.length; i++) {
	// 			if(cons > consLIMITE[i] && consLIMITE[i] > 0) {
	// 				ntranche++;
	// 			}
	// 		}
	// 		System.out.println(ntranche);
	// 		res[0] = consLIMITE[0];
	// 		for(int k=1; k < ntranche-1; k++) {
	// 			res[k] = consLIMITE[k]-consLIMITE[k-1];
	// 		}
	// 		res[ntranche-1] = cons;
	// 		for(int i=0; i < ntranche-1; i++) {
	// 			res[ntranche-1] -= res[i];
	// 		}
	// 	}
	// 	for(int i=0; i < res.length; i++) {
	// 		res[i] *= prixUnit[i];
	// 	}
	// 	return res;
	// }


    // for(int i = 0; i < obj2.length;i++){
    //         listeTarif[i] = (Tarif)obj2[i];
    //         //System.out.println("Taille liste boucle : "+listeTarif.length);

    //         if(consommation > listeTarif[i].getConsomLimite()){
    //             System.out.println("Consommation : "+consommation);
                
    //             prix = listeTarif[i].getConsomLimite() * listeTarif[i].getPrixUnitaire();
    //             System.out.println("prix : "+prix);
                
    //             addDouble(prixTranche,prix);

    //             consommation = consommation - listeTarif[i].getConsomLimite();
    //             System.out.println("consom restant : "+consommation);

    //             if(i+1 < listeTarif.length){
    //                 resteTranche = listeTarif[i+1].getConsomLimite() - listeTarif[i].getConsomLimite();
    //                 listeTarif[i+1].setConsomLimite(resteTranche);     
    //             }
                
    //             System.out.println("");
    //         }else{
    //             prix = listeTarif[i].getPrixUnitaire();
    //             System.out.println("Prix dans else : "+prix);
    //             addDouble(prixTranche,prix);
    //             System.out.println("Je suis paC ici");
    //             System.out.println("");
    //         }    
        // }

// public String afficherPrelevementFacturer(Connection c)throws Exception{
    //     PrelevAllClient p = new PrelevAllClient();
    //     int etat = 1;
    //     p.setEtat(etat);
    //     Object[] obj = f.find("prelevAllClient",p,null,c);
    //     PrelevAllClient[] pac = new PrelevAllClient[obj.length];
    //     String table = "<h3>Prelevement facturer</h3><table class=\"table\" border=\"1\"><tr><td>Date de prelevement</td><td>Numero compteur</td><td>Client n°</td><td>Adresse du client</td><td>Type compteur</td><td>Consommation</td><td>Mois</td><td>Annee</td><td>Facturer</td></tr>";

    //     for(int i = 0; i < obj.length;i++){
    //         pac[i] = (PrelevAllClient)obj[i];
    //         p = pac[i];

    //         table += "<tr>";
    //         table += "<td>"+p.getDatePrelev()+"</td>";
    //         table += "<td>"+p.getIdCompteur()+"</td>";
    //         table += "<td>"+p.getIdClient()+"</td>";
    //         table += "<td>"+p.getAdresseClient()+"</td>";
    //         table += "<td>"+p.getTypeCompteur()+"</td>";
    //         table += "<td>"+p.getConsommation()+"</td>";
    //         table += "<td>"+p.getMois()+"</td>";
    //         table += "<td>"+p.getAnnee()+"</td>";
    //         table += "<td><a href=\"facturation.jsp?idPrelevement="+p.getIdPrelevement()+"\">Voir facture</a></td>";
    //         table += "</tr>";
    //     }

    //     table += "</table>";
        
    //     return table;
    // } 
		
		//&& typeCompteur == op.getTypeOffre()
		





    // public double calculPrixAvecOffre(String idPrelevement,Connection c)throws Exception{
    //     Generalise g = new Generalise();
        
    //     PrelevAllClient pac = getPrelevClient(idPrelevement,c);
    //     String idCompteur = pac.getIdCompteur();
    //     Timestamp datePrelev = pac.getDatePrelev();
    //     double consomPrelev = pac.getConsommation();

        //Pour prendre l'abonnement correspondant au prelevement
        // String where = "idCompteur = '"+idCompteur+"' and '"+datePrelev+"' >= dateDebut and '"+datePrelev+"' <= dateFin order by dateDebut";
        // Object[] obj = g.selectOpt(c,new Abonnement(), where);
        // Abonnement[] ab = new Abonnement[obj.length];
        // Abonnement abonnement = (Abonnement)ab[obj.length-1];

        //Prendre l'offre correspondant
        // String idOffre = abonnement.getIdOffre();
        // String condition = "idOffre = '"+idOffre+"'";
        // Object[] obj1 = g.selectOpt(c,new OffrePrepaye(), condition);
        // OffrePrepaye op = (OffrePrepaye)obj1[0];

        //Prendre tout a propos de l'offre
        // double consomAcheter = op.getOffreConsom();
        // double prixDeVente = op.getPrix();
        // double majoration = op.getMajoration();
        // int duree = op.getDuree();
        // int typeOffre = op.getTypeOffre();
        
        // commencement du calcul
    //     double prix = 0;
    //     double difConsom = 0;
        
    //     if(consomPrelev < consomAcheter){
    //         prix = 0;
    //     }
    //     else{
    //         difConsom = consomPrelev - consomAcheter;
    //         double[][] donnees = getTarifAvecConsom(idPrelevement,difConsom,c);
		
    //         double[] prixParTranche = donnees[0]; //prix final par tranche
                    
    //         double total = 0;
    //         for(int i=0; i < prixParTranche.length; i++){
    //             total += prixParTranche[i];
    //             total = total; 
    //         }

    //         total = total * (majoration/100);
            
    //         prix = total + prixDeVente;
    //     }

    //     return prix;
    // }


	//     public int controllMonth(String mois)throws Exception{
    //     int moisFinal = 0;
    //     try{
    //         int moisCores = new Integer(mois).intValue();
    //         System.out.println("Exception 1 : moisCores = "+moisCores);
    //         if(moisCores < 0 || moisCores > 12){
    //             throw new Exception("Le mois inserer ne doit être ni inferieur à 0 ni superieur a 12");
    //         }
    //         else{
    //             moisFinal = moisCores;
    //         } 
    //     }
    //     catch(Exception e){
    //         try{
    //             System.out.println("Exception 2");
    //             String[] listeMois = getListeMois();
    //             for(int i = 0; i < listeMois.length; i++){
    //                 if(mois.compareToIgnoreCase(listeMois[i]) == 0){
    //                     moisFinal = i+1;
    //                     System.out.println("Mois correspondant trouve");
    //                     break;
    //                 }
    //                 else{
    //                     System.out.println("Exception 2 ici");
    //                     throw new Exception("Le mois inserer est incorrect, veuillez reverifie ce que vous avez ecrit");
    //                 }
    //             }
    //         }
    //         catch(Exception ex){
    //             throw new Exception("Mois inserer invalide");
    //         }
                         
    //     }

    //     System.out.println("Mois Final : "+moisFinal);
    //     return moisFinal;
    // }

    //     public FacturePrelev[] getFacturePrelev(int mois,int annee,String idClient,Connection c)throws Exception{
    //     Generalise g = new Generalise();
    //     Function f = new Function();
    //     FacturePrelev fp = new FacturePrelev();
    //     fp.setIdClient(idClient);
    //     Object[] obj = f.find("facturePrelev",fp,null,c);
    //     FacturePrelev[] fc = new FacturePrelev[obj.length];
    //     FacturePrelev[] valiny = new FacturePrelev[0];

    //     Timestamp datePrelev = null;

    //     for(int i=0; i < obj.length; i++){
    //         fc[i] = (FacturePrelev)obj[i];
    //         fp = fc[i];

    //         datePrelev = fp.getDatePrelev();
    //         int monthDp = datePrelev.getMonth() + 1;
    //         System.out.println("monthD : "+monthDp);
    //         System.out.println("mois : "+mois);

    //         int yearDp = datePrelev.getYear() + 1900;
    //         System.out.println("yearDp : "+yearDp);
    //         System.out.println("annee : "+annee);

    //         if(annee == yearDp && mois == monthDp){
    //             valiny = addFacturePrelev(valiny,fp);
    //             System.out.println("Ajout effectuer");
    //         }else{
    //             System.out.println("Aucune facture disponible!");
    //         }
    //     }
    //     System.out.println("Taille de la table originale : "+valiny.length);

    //     return valiny;
    // }


