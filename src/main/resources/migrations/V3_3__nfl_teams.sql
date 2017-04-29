CREATE TABLE nfl_team
(
  team text NOT NULL,
  url text,
  teampage text,
  city text,
  nickname text,
  conference text,
  division text,
  CONSTRAINT nfl_team_pk PRIMARY KEY (team)
)
WITH (
  OIDS=FALSE
);

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('BUF','http://www.buffalobills.com/','/teams/buffalobills/profile?team=BUF','Buffalo','Bills','AFC','East'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('MIA','http://www.miamidolphins.com/','/teams/miamidolphins/profile?team=MIA','Miami','Dolphins','AFC','East'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('NE','http://www.patriots.com/','/teams/newenglandpatriots/profile?team=NE','New England','Patriots','AFC','East'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('NYJ','http://www.newyorkjets.com/','/teams/newyorkjets/profile?team=NYJ','New York','Jets','AFC','East'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('BAL','http://www.baltimoreravens.com/','/teams/baltimoreravens/profile?team=BAL','Baltimore','Ravens','AFC','North'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('CIN','http://www.bengals.com/','/teams/cincinnatibengals/profile?team=CIN','Cincinnati','Bengals','AFC','North'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('CLE','http://www.clevelandbrowns.com/','/teams/clevelandbrowns/profile?team=CLE','Cleveland','Browns','AFC','North'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('PIT','http://www.steelers.com/','/teams/pittsburghsteelers/profile?team=PIT','Pittsburgh','Steelers','AFC','North'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('HOU','http://www.houstontexans.com/','/teams/houstontexans/profile?team=HOU','Houston','Texans','AFC','South'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('IND','http://www.colts.com/','/teams/indianapoliscolts/profile?team=IND','Indianapolis','Colts','AFC','South'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('JAX','http://www.jaguars.com/','/teams/jacksonvillejaguars/profile?team=JAC','Jacksonville','Jaguars','AFC','South'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('TEN','http://www.titansonline.com/','/teams/tennesseetitans/profile?team=TEN','Tennessee','Titans','AFC','South'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('DEN','http://www.denverbroncos.com/','/teams/denverbroncos/profile?team=DEN','Denver','Broncos','AFC','West'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('KC','http://www.kcchiefs.com/','/teams/kansascitychiefs/profile?team=KC','Kansas City','Chiefs','AFC','West'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('OAK','http://www.raiders.com/','/teams/oaklandraiders/profile?team=OAK','Oakland','Raiders','AFC','West'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('LAC','http://www.chargers.com/','/teams/sandiegochargers/profile?team=LAC','Los Angeles','Chargers','AFC','West'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('DAL','http://www.dallascowboys.com/','/teams/dallascowboys/profile?team=DAL','Dallas','Cowboys','NFC','East'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('NYG','http://www.giants.com/','/teams/newyorkgiants/profile?team=NYG','New York','Giants','NFL','East'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('PHI','http://www.philadelphiaeagles.com/','/teams/philadelphiaeagles/profile?team=PHI','Philadelphia','Eagles','NFC','East'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('WAS','http://www.redskins.com/','/teams/washingtonredskins/profile?team=WAS','Washington','Redskins','NFC','East'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('CHI','http://www.chicagobears.com/','/teams/chicagobears/profile?team=CHI','Chicago','Bears','NFC','North'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('DET','http://www.detroitlions.com/','/teams/detroitlions/profile?team=DET','Detroit','Lions','NFC','North'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('GB','http://www.packers.com/','/teams/greenbaypackers/profile?team=GB','Green Bay','Packers','NFC','North'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('MIN','http://www.vikings.com/','/teams/minnesotavikings/profile?team=MIN','Minnesota','Vikings','NFC','North'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('ATL','http://www.atlantafalcons.com/','/teams/atlantafalcons/profile?team=ATL','Atlanta','Falcons','NFC','South'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('CAR','http://www.panthers.com/','/teams/carolinapanthers/profile?team=CAR','Carolina','Panthers','NFC','South'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('NO','http://www.neworleanssaints.com/','/teams/neworleanssaints/profile?team=NO','New Orleans','Saints','NFC','South'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('TB','http://www.buccaneers.com/','/teams/tampabaybuccaneers/profile?team=TB','Tampa Bay','Buccaneers','NFC','South'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('ARI','http://www.azcardinals.com/','/teams/arizonacardinals/profile?team=ARI','Arizona','Cardinals','NFC','West'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('LA','http://www.therams.com/','/teams/losangelesrams/profile?team=LA','Los Angeles','Rams','NFC','West'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('SF','http://www.sf49ers.com/','/teams/sanfrancisco49ers/profile?team=SF','San Francisco','49ers','NFC','West'); 

insert into nfl_team (team, url, teampage, city, nickname, conference, division) values ('SEA','http://www.seahawks.com/','/teams/seattleseahawks/profile?team=SEA','Seattle','Seahawks','NFC','West'); 

