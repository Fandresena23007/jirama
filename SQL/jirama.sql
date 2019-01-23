ALTER SESSION SET NLS_TIMESTAMP_FORMAT = 'YYYY/MM/DD HH24:MI:SS.ff';

create table client(
    idClient varchar(20) primary key,
    adresse varchar(20),
    nom varchar(20)
);
create sequence idClient;

create table compteur(
    idCompteur varchar(20) primary key,
    idClient varchar(20),
    typeCompteur int,
    adresseClient varchar(20),
    foreign key (idClient) references client(idClient)
);
create sequence idCompteur;

create table tarif(
    idTarif varchar(20) primary key,
    prixUnitaire decimal,
    consomLimite decimal,
    typeTarif int
);
create sequence idTarif;

create table detailTarif(
    idDetailT varchar(20) primary key,
    idClient varchar(20),
    nbrTranche int,
    foreign key (idClient) references client(idClient)
);
create sequence idDetailT;

create table prelevement(
    idPrelevement varchar(20) primary key,
    idCompteur varchar(20),
    consommation decimal,
    datePrelev timestamp,
    mois int,
    annee int,
    etat int
);
create sequence idPrelevement;

create table facture(
    idFacture varchar(20) primary key,
    idClient varchar(20),
    dateFacture timestamp,
    foreign key (idClient) references client(idClient)
);
create sequence idFacture;

create table detailFacture(
    idDetailF varchar(20) primary key,
    idFacture varchar(20),
    idPrelevement varchar(20),
    nbrTranche int,
    consomLimite1 decimal,
    consomLimite2 decimal,
    prixUnitaire1er decimal,
    prixUnitaire2eme decimal,
    prixUnitaire3eme decimal,
    premierTranche decimal,
    deuxiemeTranche decimal,
    troisiemeTranche decimal,
    total decimal,
    consom decimal,
    foreign key (idPrelevement) references prelevement(idPrelevement),
    foreign key (idFacture) references facture(idFacture)
);
create sequence idDetailF;

create table prelevFacturer(
    idPrelevement varchar(20) primary key,
    foreign key (idPrelevement) references prelevement(idPrelevement)
);
create sequence idPrelevFacturer;

-- type offre 1 elec 0 eau
create table offrePrepaye(
    idOffre varchar(20) primary key,
    offreConsom decimal,
    prix decimal,
    majoration decimal,
    duree int,
    typeOffre int
);
create sequence idOffre;

create table abonnement(
    idAbonnement varchar(40) primary key,
    idCompteur varchar(20),
    idOffre varchar(20),
    quantite decimal,
    dateDebut timestamp,
    dateFin timestamp,
    foreign key (idCompteur) references compteur(idCompteur),
    foreign key (idOffre) references offrePrepaye(idOffre)
);
create sequence idAbonnement;

create table factureAvoir(
    idFactureAvoir varchar(40) primary key,
    idFacture varchar(20),
    montant decimal,
    dateAnnulation timestamp,
    foreign key (idFacture) references facture(idFacture)
);
create sequence idFactureAvoir;

create table prelevAnnuler(
    idPrelevAnnuler varchar(40) primary key,
    idPrelevement varchar(20),
    dateAnnulation timestamp,
    foreign key (idPrelevement) references prelevement(idPrelevement)
);
create sequence idPrelevAnnuler;

create view prelevClient as 
select prelevement.idPrelevement,compteur.idCompteur,compteur.typeCompteur,compteur.idClient 
from prelevement,compteur 
where prelevement.idCompteur = compteur.idCompteur;

create view prelevAllClient as
select prelevement.*,compteur.idCompteur compteurId,compteur.idClient,compteur.typeCompteur,compteur.adresseClient
from prelevement,compteur,client
where prelevement.idCompteur = compteur.idCompteur
and client.idClient = compteur.idClient;

create view compteurClient as 
select client.*,compteur.idCompteur,compteur.idClient idCom,compteur.typeCompteur,compteur.adresseClient
from client,compteur
where client.idClient = compteur.idClient;

create view factureClient as 
select client.* 
from client,facture
where client.idClient = facture.idClient;

create view facturePrelev as
select facture.idClient,facture.idFacture,detailFacture.idPrelevement,prelevement.mois,prelevement.annee,detailFacture.total
from prelevement,facture,detailFacture,client
where prelevement.idPrelevement = detailFacture.idPrelevement 
and facture.idClient = client.idClient
and facture.idFacture = detailFacture.idFacture;

select prelevement.consommation,prelevement.idPrelevement 
from prelevement
where prelevement.mois = '11' 
and prelevement.annee = '2018'
and prelevement.idCompteur = 'compteur1'
and prelevement.idPrelevement not in (select idPrelevement from prelevAnnuler);

create view facture2Avoir as 
select facture.idClient,facture.idFacture idFac,factureAvoir.*
from facture,factureAvoir
where facture.idFacture = factureAvoir.idFacture;

insert into client values('cl'||idClient.nextVal,'Andoharanofotsy','Fandresena');
insert into client values('cl'||idClient.nextVal,'Itaosy','Samyen');
insert into client values('cl'||idClient.nextVal,'Anosy','Yvan');
insert into client values('cl'||idClient.nextVal,'Tanjombato','Luc');

-- 1 elec 0 eau
insert into compteur values('compteur'||idCompteur.nextVal,'cl1',1,'Andoharanofotsy');
insert into compteur values('compteur'||idCompteur.nextVal,'cl1',1,'Andoharanofotsy');
insert into compteur values('compteur'||idCompteur.nextVal,'cl1',0,'Andoharanofotsy');
insert into compteur values('compteur'||idCompteur.nextVal,'cl2',1,'Itaosy');
insert into compteur values('compteur'||idCompteur.nextVal,'cl2',0,'Itaosy');
insert into compteur values('compteur'||idCompteur.nextVal,'cl3',1,'Anosy');
insert into compteur values('compteur'||idCompteur.nextVal,'cl4',0,'Tanjombato');

-- 1 elec 0 eau
insert into tarif values('t'||idTarif.nextVal,100,1000,1);
insert into tarif values('t'||idTarif.nextVal,150,1500,1);
insert into tarif values('t'||idTarif.nextVal,200,-1,1);
insert into tarif values('t'||idTarif.nextVal,100,1000,0);
insert into tarif values('t'||idTarif.nextVal,120,1500,0);
insert into tarif values('t'||idTarif.nextVal,200,-1,0);

insert into detailTarif values('dt'||idDetailT.nextVal,'cl1',3);
insert into detailTarif values('dt'||idDetailT.nextVal,'cl2',2);
insert into detailTarif values('dt'||idDetailT.nextVal,'cl3',3);
insert into detailTarif values('dt'||idDetailT.nextVal,'cl4',1);

-- etat 2 nonFacturer 1 facturer 0 payer 3 annuler
insert into prelevement values('p'||idPrelevement.nextVal,'compteur1',2000,timestamp'2018-10-02 00:00:00',10,2018,02);
insert into prelevement values('p'||idPrelevement.nextVal,'compteur2',5000,timestamp'2018-10-02 00:00:00',10,2018,02);
insert into prelevement values('p'||idPrelevement.nextVal,'compteur3',6000,timestamp'2018-10-02 00:00:00',10,2018,02);
insert into prelevement values('p'||idPrelevement.nextVal,'compteur4',1200,timestamp'2018-10-02 00:00:00',10,2018,02);
insert into prelevement values('p'||idPrelevement.nextVal,'compteur4',1200,timestamp'2018-10-02 00:00:00',10,2018,02);

-- 1 elec 0 eau
insert into offrePrepaye values('op'||idOffre.nextVal,15000,50000,120,30,1);
insert into offrePrepaye values('op'||idOffre.nextVal,30000,90000,130,30,1);
insert into offrePrepaye values('op'||idOffre.nextVal,15000,45000,120,30,0);
insert into offrePrepaye values('op'||idOffre.nextVal,30000,80000,120,30,0);

insert into abonnement values('abonnement'||idAbonnement.nextVal,'compteur1','op1',0.0,timestamp'2018-12-14 00:00:00',timestamp'2019-01-14 00:00:00');
insert into abonnement values('abonnement'||idAbonnement.nextVal,'compteur1','op2',3000,timestamp'2018-09-30 00:00:00',timestamp'2018-10-30 00:00:00');

insert into client values('cl'||idClient.nextVal,'Betongolo','Zah');
insert into compteur values('compteur'||idCompteur.nextVal,'cl5',1,'Tanjombato');
insert into detailTarif values('dt'||idDetailT.nextVal,'cl5',3);


delete from factureAvoir;
drop sequence idFactureAvoir;
create sequence idFactureAvoir;
delete from prelevAnnuler;
drop sequence idPrelevAnnuler;
create sequence idPrelevAnnuler;
delete from detailFacture;
drop sequence idDetailF;
create sequence idDetailF;
delete from facture;
drop sequence idFacture;
create sequence idFacture;
delete from prelevement;
drop sequence idPrelevement;
create sequence idPrelevement;
commit;
