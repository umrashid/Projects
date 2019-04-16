DROP DATABASE IF EXISTS Contractors;
CREATE DATABASE Contractors;
use Contractors;

CREATE TABLE Contractor (
    contractorID INT(4) NOT NULL,
    fName VARCHAR(25) NOT NULL DEFAULT '',
    lName VARCHAR(25) NOT NULL DEFAULT '',
    hourlyWage Double NOT NULL,
    phoneNumber VARCHAR(25) NOT NULL DEFAULT '',
    email VARCHAR(50) NOT NULL DEFAULT '',
    PRIMARY KEY (contractorID)
);

CREATE TABLE Supervisor (
    supervisorID INT(4) NOT NULL,
    fName VARCHAR(25) NOT NULL DEFAULT '',
    lName VARCHAR(25) NOT NULL DEFAULT '',
    phoneNumber VARCHAR(25) NOT NULL DEFAULT '',
    email VARCHAR(50) NOT NULL DEFAULT '',
    PRIMARY KEY (supervisorID)
);

CREATE TABLE Clients (
    clientsID INT(4) NOT NULL,
    fName VARCHAR(25) NOT NULL DEFAULT '',
    lName VARCHAR(25) NOT NULL DEFAULT '',
    phoneNumber VARCHAR(25) NOT NULL DEFAULT '',
    email VARCHAR(50) NOT NULL DEFAULT '',
    PRIMARY KEY (clientsID)
);

CREATE TABLE Supplier(
    supplierID INT(4) NOT NULL,
    fName VARCHAR(25) NOT NULL DEFAULT '',
    lName VARCHAR(25) NOT NULL DEFAULT '',
    streetNumber VARCHAR(25) NOT NULL DEFAULT '',
    streetName VARCHAR(25) NOT NULL DEFAULT '',
    city VARCHAR(25) NOT NULL DEFAULT '',
    province VARCHAR(25) NOT NULL DEFAULT '',
    postCode CHAR(7) NOT NULL DEFAULT '',
    phoneNumber VARCHAR(25) NOT NULL DEFAULT '',
    email VARCHAR(50) NOT NULL DEFAULT '',
    PRIMARY KEY (supplierID)
);

CREATE TABLE Location(
    locationID INT(4) NOT NULL,
    streetNumber VARCHAR(25) NOT NULL DEFAULT '',
    streetName VARCHAR(25) NOT NULL DEFAULT '',
    city VARCHAR(25) NOT NULL DEFAULT '',
    province VARCHAR(25) NOT NULL DEFAULT '',
    postCode CHAR(7) NOT NULL DEFAULT '',
    clientsID INT(4) NOT NULL,
    PRIMARY KEY (locationID),
    FOREIGN KEY(clientsID)
        REFERENCES CLients (clientsID)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Material(
    materialID INT(4) NOT NULL,
    supplierID INT(4) NOT NULL ,
    description VARCHAR(25) NOT NULL DEFAULT '',
    costPerMaterial DOUBLE NOT NULL,
    PRIMARY KEY (materialID),
    FOREIGN KEY(supplierID)
        REFERENCES Supplier (supplierID)
        ON UPDATE CASCADE 
        ON DELETE CASCADE
);


CREATE TABLE Project(
    projectID INT(4) NOT NULL,
    clientsID INT(4) NOT NULL,
    supervisorID INT(4) NOT NULL,
    name VARCHAR(25) NOT NULL DEFAULT '',
    projectStart DATE NOT NULL,
    projectEnd DATE NOT NULL,
    progress VARCHAR(25) NOT NULL DEFAULT '',
    PRIMARY KEY(projectID),
    FOREIGN KEY (clientsID)
        REFERENCES Clients (clientsID)
        ON UPDATE CASCADE,
    FOREIGN KEY (supervisorID)
        REFERENCES Supervisor (supervisorID)
        ON UPDATE CASCADE
        
);

CREATE TABLE Contractor_Hours(
    contractorID INT(4) NOT NULL,
    projectID INT(4) NOT NULL,
    hoursWorked INT NOT NULL,
    PRIMARY KEY(contractorID, projectID),
    FOREIGN KEY (contractorID)
        REFERENCES Contractor (contractorID)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (projectID)
        REFERENCES Project (projectID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE Material_Quantity(
    materialID INT(4) NOT NULL,
    projectID INT(4) NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY(materialID, projectID),
    FOREIGN KEY (projectID)
        REFERENCES Project (projectID)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (materialID)
        REFERENCES Material (materialID)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);


/*---------------------------------------------------INSERT VALUES FOR CONTRACTOR-----------------------------------------------------------------*/
insert into Contractor (contractorID, fName, lName, hourlyWage, phoneNumber, email) values (1001, 'Gilbert', 'Croshaw', 57.08, '604-662-2419', 'gcroshaw0@adobe.com');
insert into Contractor (contractorID, fName, lName, hourlyWage, phoneNumber, email) values (1002, 'Ag', 'Riccard', 75.81, '604-259-7801', 'ariccard1@uol.com.br');
insert into Contractor (contractorID, fName, lName, hourlyWage, phoneNumber, email) values (1003, 'Linnea', 'Viles', 34.38, '604-238-6605', 'lviles2@comcast.net');
insert into Contractor (contractorID, fName, lName, hourlyWage, phoneNumber, email) values (1004, 'Codee', 'Duckering', 73.23, '604-164-9614', 'cduckering3@tmall.com');
insert into Contractor (contractorID, fName, lName, hourlyWage, phoneNumber, email) values (1005, 'Abel', 'Derr', 49.81, '604-152-7630', 'aderr4@xinhuanet.com');
insert into Contractor (contractorID, fName, lName, hourlyWage, phoneNumber, email) values (1006, 'Leonore', 'Cheasman', 72.61, '604-618-4006', 'lcheasman5@xinhuanet.com');
insert into Contractor (contractorID, fName, lName, hourlyWage, phoneNumber, email) values (1007, 'Anthe', 'Bermingham', 68.86, '604-714-3087', 'abermingham6@myspace.com');
insert into Contractor (contractorID, fName, lName, hourlyWage, phoneNumber, email) values (1008, 'Stormi', 'Butchart', 48.48, '604-313-0621', 'sbutchart7@ox.ac.uk');
insert into Contractor (contractorID, fName, lName, hourlyWage, phoneNumber, email) values (1009, 'Barris', 'Raulstone', 39.96, '604-886-2038', 'braulstone8@hao123.com');
insert into Contractor (contractorID, fName, lName, hourlyWage, phoneNumber, email) values (1010, 'Raoul', 'Colvie', 51.18, '604-153-2570', 'rcolvie9@addtoany.com');
/*---------------------------------------------------------------------------------------------------------------------------------------------------*/

/*---------------------------------------------------INSERT VALUES FOR SUPERVISOR--------------------------------------------------------------------*/
insert into Supervisor (supervisorID, fName, lName, phoneNumber, email) values (1001, 'Ban', 'Dunkerton', '604-511-4596', 'bdunkerton0@slate.com');
insert into Supervisor (supervisorID, fName, lName, phoneNumber, email) values (1002, 'Lilian', 'Forlonge', '604-691-0072', 'lforlonge1@moonfruit.com');
insert into Supervisor (supervisorID, fName, lName, phoneNumber, email) values (1003, 'Lizabeth', 'Zuanazzi', '604-091-1933', 'lzuanazzi2@squidoo.com');
insert into Supervisor (supervisorID, fName, lName, phoneNumber, email) values (1004, 'Karna', 'O''Bradain', '604-177-5548', 'kobradain3@miitbeian.gov.cn');
insert into Supervisor (supervisorID, fName, lName, phoneNumber, email) values (1005, 'Constantino', 'Kilrow', '604-135-7760', 'ckilrow4@geocities.com');
insert into Supervisor (supervisorID, fName, lName, phoneNumber, email) values (1006, 'Mari', 'Dagleas', '604-019-1604', 'mdagleas5@elpais.com');
insert into Supervisor (supervisorID, fName, lName, phoneNumber, email) values (1007, 'Stephi', 'Larwell', '604-116-5173', 'slarwell6@artisteer.com');
insert into Supervisor (supervisorID, fName, lName, phoneNumber, email) values (1008, 'Terri-jo', 'Wilkenson', '604-790-4168', 'twilkenson7@blogs.com');
insert into Supervisor (supervisorID, fName, lName, phoneNumber, email) values (1009, 'Elyse', 'Boutflour', '604-397-6788', 'eboutflour8@google.co.uk');
insert into Supervisor (supervisorID, fName, lName, phoneNumber, email) values (1010, 'Adara', 'Ipplett', '604-538-8508', 'aipplett9@slate.com');
/*---------------------------------------------------------------------------------------------------------------------------------------------------*/




/*---------------------------------------------------INSERT VALUES FOR CLIENTS----------------------------------------------------------------------*/
insert into Clients (clientsID, fName, lName, phoneNumber, email) values (1001, 'Dian', 'Bywaters', '604-113-9083', 'dbywaters0@sbwire.com');
insert into Clients (clientsID, fName, lName, phoneNumber, email) values (1002, 'Eleonora', 'Robertis', '604-443-3671', 'erobertis1@biblegateway.com');
insert into Clients (clientsID, fName, lName, phoneNumber, email) values (1003, 'Spence', 'Been', '604-574-2323', 'sbeen2@goodreads.com');
insert into Clients (clientsID, fName, lName, phoneNumber, email) values (1004, 'Nicolas', 'Howton', '604-229-5714', 'nhowton3@cnet.com');
insert into Clients (clientsID, fName, lName, phoneNumber, email) values (1005, 'Ulric', 'Spoole', '604-746-0284', 'uspoole4@skype.com');
insert into Clients (clientsID, fName, lName, phoneNumber, email) values (1006, 'Annissa', 'Severn', '604-198-9698', 'asevern5@weibo.com');
insert into Clients (clientsID, fName, lName, phoneNumber, email) values (1007, 'Alard', 'Duggon', '604-059-7892', 'aduggon6@fastcompany.com');
insert into Clients (clientsID, fName, lName, phoneNumber, email) values (1008, 'Van', 'Hobgen', '604-436-5241', 'vhobgen7@sohu.com');
insert into Clients (clientsID, fName, lName, phoneNumber, email) values (1009, 'Siegfried', 'Gelletly', '604-761-3689', 'sgelletly8@biglobe.ne.jp');
insert into Clients (clientsID, fName, lName, phoneNumber, email) values (1010, 'Krista', 'Jiroutka', '604-145-2040', 'kjiroutka9@chron.com');
/*---------------------------------------------------------------------------------------------------------------------------------------------------*/


/*---------------------------------------------------INSERT VALUES FOR SUPPLIER----------------------------------------------------------------------*/
insert into Supplier (supplierID, fName, lName, streetNumber, streetName, city, province, postCode, email, phoneNumber) values (1001, 'Ariel', 'Menichini', '5194', 'Sheridan', 'Surrey', 'BC', 'V8A 5Z0', 'amenichini0@dion.ne.jp', '604-584-7146');
insert into Supplier (supplierID, fName, lName, streetNumber, streetName, city, province, postCode, email, phoneNumber) values (1002, 'Dehlia', 'Firmin', '5738', 'Esch', 'Coquitlam', 'BC', 'V9V 8L8', 'dfirmin1@utexas.edu', '604-347-9196');
insert into Supplier (supplierID, fName, lName, streetNumber, streetName, city, province, postCode, email, phoneNumber) values (1003, 'Claire', 'Battisson', '457', 'Hovde', 'Burnaby', 'BC', 'V7A 5I5', 'cbattisson2@yellowpages.com', '604-748-8836');
insert into Supplier (supplierID, fName, lName, streetNumber, streetName, city, province, postCode, email, phoneNumber) values (1004, 'Lonee', 'Vautrey', '71307', 'Luster', 'New Westminister', 'BC', 'V4Y 9W0', 'lvautrey3@webmd.com', '604-559-4731');
insert into Supplier (supplierID, fName, lName, streetNumber, streetName, city, province, postCode, email, phoneNumber) values (1005, 'Faustine', 'Hollibone', '717', 'Kensington', 'Langley', 'BC', 'V3R 3E4', 'fhollibone4@webeden.co.uk', '604-218-3089');
insert into Supplier (supplierID, fName, lName, streetNumber, streetName, city, province, postCode, email, phoneNumber) values (1006, 'Truda', 'Romushkin', '2', 'Pankratz', 'Langley', 'BC', 'V4C 4F4', 'tromushkin5@liveinternet.ru', '604-240-9620');
insert into Supplier (supplierID, fName, lName, streetNumber, streetName, city, province, postCode, email, phoneNumber) values (1007, 'Udall', 'Pirdue', '03847', 'Continental', 'New Westminister', 'BC', 'V2Z 6O7', 'upirdue6@irs.gov', '604-150-1899');
insert into Supplier (supplierID, fName, lName, streetNumber, streetName, city, province, postCode, email, phoneNumber) values (1008, 'Deeann', 'Simmell', '5', 'Tennessee', 'Surrey', 'BC', 'V3Y 7V5', 'dsimmell7@t-online.de', '604-050-5466');
insert into Supplier (supplierID, fName, lName, streetNumber, streetName, city, province, postCode, email, phoneNumber) values (1009, 'Garek', 'Reagan', '6890', 'Sutteridge', 'Coquitlam', 'BC', 'V2Z 2R7', 'greagan8@yale.edu', '604-108-3932');
insert into Supplier (supplierID, fName, lName, streetNumber, streetName, city, province, postCode, email, phoneNumber) values (1010, 'Shepperd', 'Ganforth', '16418', 'Lyons', 'Richmond', 'BC', 'V7N 1J6', 'sganforth9@google.com', '604-493-1428');
/*---------------------------------------------------------------------------------------------------------------------------------------------------*/

/*---------------------------------------------------INSERT VALUES FOR LOCATION----------------------------------------------------------------------*/
insert into Location (locationID, streetNumber, streetName, city, province, postCode, clientsID) values (1001, '1706', 'Sundown', 'Langley', 'BC', 'V6A 6S8', 1010);
insert into Location (locationID, streetNumber, streetName, city, province, postCode, clientsID) values (1002, '85041', 'Dayton', 'Richmond', 'BC', 'V8D 3K3', 1009);
insert into Location (locationID, streetNumber, streetName, city, province, postCode, clientsID) values (1003, '40', 'Redwing', 'Burnaby', 'BC', 'V0R 4A5', 1006);
insert into Location (locationID, streetNumber, streetName, city, province, postCode, clientsID) values (1004, '1445', 'Annamark', 'New Westminister', 'BC', 'V5U 8C1', 1007);
insert into Location (locationID, streetNumber, streetName, city, province, postCode, clientsID) values (1005, '043', 'Northwestern', 'Vancouver', 'BC', 'V9H 1A0', 1002);
insert into Location (locationID, streetNumber, streetName, city, province, postCode, clientsID) values (1006, '932', 'Stuart', 'Vancouver', 'BC', 'V6Z 7R5', 1006);
insert into Location (locationID, streetNumber, streetName, city, province, postCode, clientsID) values (1007, '8292', 'Gateway', 'Coquitlam', 'BC', 'V8Z 5B4', 1006);
insert into Location (locationID, streetNumber, streetName, city, province, postCode, clientsID) values (1008, '8300', 'Huxley', 'Richmond', 'BC', 'V1A 8L2', 1006);
insert into Location (locationID, streetNumber, streetName, city, province, postCode, clientsID) values (1009, '9', 'Cambridge', 'Richmond', 'BC', 'V2E 4B6', 1003);
insert into Location (locationID, streetNumber, streetName, city, province, postCode, clientsID) values (1010, '6', 'Jackson', 'Burnaby', 'BC', 'V8O 4J4', 1003);
insert into Location (locationID, streetNumber, streetName, city, province, postCode, clientsID) values (1011, '3', 'Cascade', 'Richmond', 'BC', 'V1B 7C0', 1004);
insert into Location (locationID, streetNumber, streetName, city, province, postCode, clientsID) values (1012, '8', 'High Crossing', 'Langley', 'BC', 'V4T 5I2', 1003);
insert into Location (locationID, streetNumber, streetName, city, province, postCode, clientsID) values (1013, '3', 'Graedel', 'Coquitlam', 'BC', 'V6Z 9L0', 1005);
insert into Location (locationID, streetNumber, streetName, city, province, postCode, clientsID) values (1014, '5675', 'Dayton', 'Langley', 'BC', 'V7R 7A6', 1010);
insert into Location (locationID, streetNumber, streetName, city, province, postCode, clientsID) values (1015, '3628', 'Bayside', 'New Westminister', 'BC', 'V8W 2V2', 1010);
insert into Location (locationID, streetNumber, streetName, city, province, postCode, clientsID) values (1016, '54131', 'Redwing', 'Langley', 'BC', 'V1Y 5U9', 1007);
insert into Location (locationID, streetNumber, streetName, city, province, postCode, clientsID) values (1017, '215', 'Bay', 'Vancouver', 'BC', 'V6U 8K1', 1006);
insert into Location (locationID, streetNumber, streetName, city, province, postCode, clientsID) values (1018, '533', 'Moulton', 'Langley', 'BC', 'V0P 5D7', 1001);
insert into Location (locationID, streetNumber, streetName, city, province, postCode, clientsID) values (1019, '2', 'Kensington', 'Vancouver', 'BC', 'V4L 3X4', 1010);
insert into Location (locationID, streetNumber, streetName, city, province, postCode, clientsID) values (1020, '2392', 'Derek', 'Langley', 'BC', 'V1H 1Y4', 1001);

/*---------------------------------------------------------------------------------------------------------------------------------------------------*/

/*---------------------------------------------------INSERT VALUES FOR MATERIAL----------------------------------------------------------------------*/
insert into Material (materialID, supplierID, Description, CostPerMaterial) values (1001, 1001, 'Steel', 73.76);
insert into Material (materialID, supplierID, Description, CostPerMaterial) values (1002, 1002, 'Tiles', 192.18);
insert into Material (materialID, supplierID, Description, CostPerMaterial) values (1003, 1003, 'Building Insulation', 182.9);
insert into Material (materialID, supplierID, Description, CostPerMaterial) values (1004, 1004, 'Housewrap', 112.88);
insert into Material (materialID, supplierID, Description, CostPerMaterial) values (1005, 1005, 'Circuit Breakers', 110.39);
insert into Material (materialID, supplierID, Description, CostPerMaterial) values (1006, 1006, 'Electrical Wiring', 53.24);
insert into Material (materialID, supplierID, Description, CostPerMaterial) values (1007, 1007, 'Switches', 140.24);
insert into Material (materialID, supplierID, Description, CostPerMaterial) values (1008, 1008, 'Wood', 66.02);
insert into Material (materialID, supplierID, Description, CostPerMaterial) values (1009, 1009, 'Doors', 143.51);
insert into Material (materialID, supplierID, Description, CostPerMaterial) values (1010, 1010, 'Glass', 187.27);

/*---------------------------------------------------------------------------------------------------------------------------------------------------*/


/*---------------------------------------------------INSERT VALUES FOR PROJECT----------------------------------------------------------------------*/

insert into Project (projectID, clientsID, supervisorID, name, projectStart, projectEnd, progress) values (1001, 1010, 1001, 'Construction', '2018-03-15', '2018-12-02', 'IN PROGRESS');
insert into Project (projectID, clientsID, supervisorID, name, projectStart, projectEnd, progress) values (1002, 1008, 1002, 'Modelling', '2018-02-28', '2018-06-19', 'FINISHED');
insert into Project (projectID, clientsID, supervisorID, name, projectStart, projectEnd, progress) values (1003, 1001, 1003, 'Renovations', '2018-01-29', '2018-05-16', 'FINISHED');
insert into Project (projectID, clientsID, supervisorID, name, projectStart, projectEnd, progress) values (1004, 1007, 1004, 'Wiring', '2018-03-31', '2018-12-13', 'IN PROGRESS');
insert into Project (projectID, clientsID, supervisorID, name, projectStart, projectEnd, progress) values (1005, 1009, 1005, 'Flooring', '2018-04-24', '2018-05-23', 'IN PROGRESS');
insert into Project (projectID, clientsID, supervisorID, name, projectStart, projectEnd, progress) values (1006, 1006, 1006, 'Construction', '2018-01-31', '2018-12-10', 'IN PROGRESS');
insert into Project (projectID, clientsID, supervisorID, name, projectStart, projectEnd, progress) values (1007, 1003, 1007, 'Modelling', '2018-02-12', '2019-01-01', 'IN PROGRESS');
insert into Project (projectID, clientsID, supervisorID, name, projectStart, projectEnd, progress) values (1008, 1009, 1008, 'Renovations', '2018-03-25', '2018-06-03', 'FINISHED');
insert into Project (projectID, clientsID, supervisorID, name, projectStart, projectEnd, progress) values (1009, 1009, 1009, 'Wiring', '2018-02-01', '2018-12-24', 'IN PROGRESS');
insert into Project (projectID, clientsID, supervisorID, name, projectStart, projectEnd, progress) values (1010, 1009, 1010, 'Flooring', '2018-03-06', '2019-01-26', 'IN PROGRESS');
insert into Project (projectID, clientsID, supervisorID, name, projectStart, projectEnd, progress) values (1011, 1005, 1001, 'Construction', '2018-02-27', '2018-07-18', 'FINISHED');
insert into Project (projectID, clientsID, supervisorID, name, projectStart, projectEnd, progress) values (1012, 1005, 1002, 'Modelling', '2018-02-19', '2018-05-08', 'FINISHED');
insert into Project (projectID, clientsID, supervisorID, name, projectStart, projectEnd, progress) values (1013, 1006, 1003, 'Renovations', '2018-04-05', '2019-02-03', 'IN PROGRESS');
insert into Project (projectID, clientsID, supervisorID, name, projectStart, projectEnd, progress) values (1014, 1002, 1004, 'Wiring', '2018-04-07', '2018-08-08', 'FINISHED');
insert into Project (projectID, clientsID, supervisorID, name, projectStart, projectEnd, progress) values (1015, 1010, 1005, 'Flooring', '2018-04-05', '2018-12-14', 'IN PROGRESS');
insert into Project (projectID, clientsID, supervisorID, name, projectStart, projectEnd, progress) values (1016, 1001, 1006, 'Construction', '2018-03-07', '2018-06-22', 'FINISHED');
insert into Project (projectID, clientsID, supervisorID, name, projectStart, projectEnd, progress) values (1017, 1005, 1007, 'Modelling', '2018-03-06', '2018-12-22', 'IN PROGRESS');
insert into Project (projectID, clientsID, supervisorID, name, projectStart, projectEnd, progress) values (1018, 1006, 1008, 'Renovations', '2018-02-21', '2018-12-14', 'IN PROGRESS');
insert into Project (projectID, clientsID, supervisorID, name, projectStart, projectEnd, progress) values (1019, 1006, 1009, 'Wiring', '2018-04-08', '2018-12-21', 'IN PROGRESS');
insert into Project (projectID, clientsID, supervisorID, name, projectStart, projectEnd, progress) values (1020, 1003, 1010, 'Flooring', '2018-04-25', '2018-06-06', 'FINISHED');
insert into Project (projectID, clientsID, supervisorID, name, projectStart, projectEnd, progress) values (1021, 1008, 1001, 'Construction', '2018-03-21', '2018-05-06', 'FINISHED');
insert into Project (projectID, clientsID, supervisorID, name, projectStart, projectEnd, progress) values (1022, 1007, 1002, 'Modelling', '2018-02-11', '2018-08-08', 'FINISHED');
insert into Project (projectID, clientsID, supervisorID, name, projectStart, projectEnd, progress) values (1023, 1007, 1003, 'Renovations', '2018-02-06', '2018-05-23', 'FINISHED');
insert into Project (projectID, clientsID, supervisorID, name, projectStart, projectEnd, progress) values (1024, 1001, 1004, 'Wiring', '2018-04-15', '2018-08-21', 'FINISHED');
insert into Project (projectID, clientsID, supervisorID, name, projectStart, projectEnd, progress) values (1025, 1006, 1005, 'Flooring', '2018-02-09', '2019-02-12', 'IN PROGRESS');
/*---------------------------------------------------------------------------------------------------------------------------------------------------*/


/*---------------------------------------------------INSERT VALUES FOR CONTRACTOR_HOURS----------------------------------------------------------------------*/
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1006, 1001, 223);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1004, 1002, 359);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1001, 1003, 591);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1004, 1004, 292);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1001, 1005, 426);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1001, 1006, 392);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1010, 1007, 235);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1007, 1008, 175);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1007, 1009, 475);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1009, 1010, 306);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1007, 1011, 275);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1009, 1012, 460);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1009, 1013, 363);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1010, 1014, 474);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1006, 1015, 523);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1002, 1016, 265);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1004, 1017, 440);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1010, 1018, 158);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1002, 1019, 168);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1007, 1020, 490);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1002, 1021, 323);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1007, 1022, 170);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1008, 1023, 467);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1006, 1024, 239);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1005, 1025, 238);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1009, 1001, 227);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1005, 1002, 372);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1008, 1004, 472);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1006, 1005, 350);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1006, 1006, 337);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1009, 1007, 509);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1006, 1008, 162);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1003, 1009, 553);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1005, 1010, 487);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1008, 1011, 225);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1002, 1012, 574);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1001, 1013, 383);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1007, 1014, 424);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1005, 1015, 158);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1009, 1016, 434);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1007, 1017, 427);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1004, 1018, 331);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1006, 1019, 184);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1008, 1020, 403);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1006, 1021, 469);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1003, 1022, 492);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1004, 1023, 304);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1001, 1024, 151);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1001, 1025, 519);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1002, 1001, 413);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1005, 1003, 157);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1003, 1004, 452);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1002, 1005, 372);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1008, 1006, 285);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1002, 1007, 495);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1004, 1008, 507);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1001, 1010, 547);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1009, 1011, 203);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1007, 1013, 150);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1003, 1017, 546);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1008, 1018, 438);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1007, 1021, 216);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1008, 1022, 175);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1005, 1023, 379);
insert into Contractor_Hours (contractorID, projectID, hoursWorked) values (1005, 1024, 185);

/*------------------------------------------------------------------------------------------------------------------------------------------------------------*/

/*---------------------------------------------------INSERT VALUES FOR MATERIAL_QUANTITY----------------------------------------------------------------------*/
insert into Material_Quantity (materialID, projectID, quantity) values (1001, 1025, 86);
insert into Material_Quantity (materialID, projectID, quantity) values (1002, 1020, 183);
insert into Material_Quantity (materialID, projectID, quantity) values (1003, 1019, 367);
insert into Material_Quantity (materialID, projectID, quantity) values (1004, 1002, 197);
insert into Material_Quantity (materialID, projectID, quantity) values (1005, 1005, 320);
insert into Material_Quantity (materialID, projectID, quantity) values (1006, 1022, 385);
insert into Material_Quantity (materialID, projectID, quantity) values (1007, 1002, 45);
insert into Material_Quantity (materialID, projectID, quantity) values (1008, 1012, 250);
insert into Material_Quantity (materialID, projectID, quantity) values (1009, 1005, 153);
insert into Material_Quantity (materialID, projectID, quantity) values (1010, 1004, 363);
insert into Material_Quantity (materialID, projectID, quantity) values (1001, 1009, 75);
insert into Material_Quantity (materialID, projectID, quantity) values (1002, 1013, 256);
insert into Material_Quantity (materialID, projectID, quantity) values (1003, 1010, 62);
insert into Material_Quantity (materialID, projectID, quantity) values (1004, 1025, 303);
insert into Material_Quantity (materialID, projectID, quantity) values (1005, 1018, 175);
insert into Material_Quantity (materialID, projectID, quantity) values (1006, 1016, 371);
insert into Material_Quantity (materialID, projectID, quantity) values (1007, 1005, 26);
insert into Material_Quantity (materialID, projectID, quantity) values (1008, 1003, 37);
insert into Material_Quantity (materialID, projectID, quantity) values (1009, 1011, 134);
insert into Material_Quantity (materialID, projectID, quantity) values (1010, 1019, 315);
insert into Material_Quantity (materialID, projectID, quantity) values (1002, 1004, 163);
insert into Material_Quantity (materialID, projectID, quantity) values (1003, 1002, 60);
insert into Material_Quantity (materialID, projectID, quantity) values (1004, 1016, 163);
insert into Material_Quantity (materialID, projectID, quantity) values (1005, 1016, 313);
insert into Material_Quantity (materialID, projectID, quantity) values (1006, 1006, 19);
insert into Material_Quantity (materialID, projectID, quantity) values (1007, 1020, 171);
insert into Material_Quantity (materialID, projectID, quantity) values (1008, 1009, 260);
insert into Material_Quantity (materialID, projectID, quantity) values (1009, 1018, 42);
insert into Material_Quantity (materialID, projectID, quantity) values (1010, 1007, 149);
insert into Material_Quantity (materialID, projectID, quantity) values (1001, 1018, 38);
insert into Material_Quantity (materialID, projectID, quantity) values (1002, 1017, 39);
insert into Material_Quantity (materialID, projectID, quantity) values (1003, 1018, 154);
insert into Material_Quantity (materialID, projectID, quantity) values (1004, 1013, 379);
insert into Material_Quantity (materialID, projectID, quantity) values (1005, 1009, 107);
insert into Material_Quantity (materialID, projectID, quantity) values (1007, 1003, 141);
insert into Material_Quantity (materialID, projectID, quantity) values (1008, 1023, 62);
insert into Material_Quantity (materialID, projectID, quantity) values (1009, 1014, 265);
insert into Material_Quantity (materialID, projectID, quantity) values (1010, 1025, 129);
insert into Material_Quantity (materialID, projectID, quantity) values (1002, 1008, 82);
insert into Material_Quantity (materialID, projectID, quantity) values (1003, 1012, 38);
insert into Material_Quantity (materialID, projectID, quantity) values (1005, 1007, 18);
insert into Material_Quantity (materialID, projectID, quantity) values (1006, 1020, 333);
insert into Material_Quantity (materialID, projectID, quantity) values (1007, 1011, 203);
insert into Material_Quantity (materialID, projectID, quantity) values (1008, 1006, 240);
insert into Material_Quantity (materialID, projectID, quantity) values (1009, 1009, 59);
insert into Material_Quantity (materialID, projectID, quantity) values (1010, 1017, 10);
/*------------------------------------------------------------------------------------------------------------------------------------------------------------*/