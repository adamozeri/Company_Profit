USE company;

CREATE TABLE DepartmentTable
(DName VARCHAR(20) NOT NULL PRIMARY KEY ,
NumOfEmployees int not null,
NumOfRoles int not null,
Changeable bool,
Syncable bool,
StartingHr int not null,
EndingHr int not null);

INSERT INTO DepartmentTable VALUES
('Programmer',1,2,true,false,0,0),('Marketing',1,2,false,true,15,0);

CREATE TABLE RoleTable
(RName VARCHAR(20) NOT NULL PRIMARY KEY,
NumOfEmployees int not null,
Changeable bool,
DName VARCHAR(20) NOT NULL,
FOREIGN KEY (DName) REFERENCES DepartmentTable(DName));

INSERT INTO RoleTable VALUES
('Developer',1,true,'Programmer'),('QA',0,true,'Programmer'),('Salesman',1,false,'Marketing'),('Advertiser',0,true,'Marketing');

create table EmployeeTable(EID VARCHAR(9) NOT NULL PRIMARY KEY
,First_Name VARCHAR(20) NOT NULL,
Last_Name VARCHAR(20) NOT NULL,
DName VARCHAR(20) NOT NULL,
RName VARCHAR(20) NOT NULL,
FOREIGN KEY (DName) REFERENCES DepartmentTable(DName),
FOREIGN KEY (RName) REFERENCES RoleTable(RName));

insert INTO EmployeeTable VALUES
('123456789','Nick','Mercs','Programmer','Developer'),
('123456785','Bob','Alice','Marketing','Salesman');

CREATE TABLE HourlyEmployeeTable 
(EID VARCHAR(9) NOT NULL PRIMARY KEY,
Hourly_Payment double NOT NULL,
Working_Hours INT NOT NULL,
Month_Salary double as (Hourly_Payment * Working_Hours),
FOREIGN KEY (EID) REFERENCES EmployeeTable(EID));


CREATE TABLE BasicEmployeeTable
(EID VARCHAR(9) NOT NULL PRIMARY KEY,
Month_Salary double NOT NULL,
FOREIGN KEY (EID) REFERENCES EmployeeTable(EID));

insert INTO BasicEmployeeTable VALUES
('123456789',2000);

CREATE TABLE SalesEmployeeTable
(EID VARCHAR(9) NOT NULL PRIMARY KEY,
Month_Salary double NOT NULL,
Sales double NOT NULL,
Salary double as (Month_Salary + (Sales / 100)),
FOREIGN KEY (EID) REFERENCES EmployeeTable(EID));

insert INTO SalesEmployeeTable(EID,Month_Salary,Sales)
Values ('123456785',1200,0);

CREATE TABLE PrefernceTypeTable
(PID int not null PRIMARY KEY,
type VARCHAR(10) NOT NULL);

insert INTO PrefernceTypeTable VALUES
(0,'Early'),(1,'Late'),(2,'Regular'),(3,'Home');

CREATE TABLE PreferenceTable
(EID VARCHAR(9) NOT NULL PRIMARY KEY,
StartingHr INT NOT NULL,
EndingHr INT NOT NULL,
PID INT NOT NULL,
FOREIGN KEY (PID) REFERENCES PrefernceTypeTable(PID),
FOREIGN KEY (EID) REFERENCES EmployeeTable(EID));

INSERT INTO PreferenceTable VALUES
('123456789',0,9,0),('123456785',22,7,1);

