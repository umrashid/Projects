/*Show the cost of the Project from most to the least that are finished*/
select * from contractors.project,
(select contractorCost.projectID, Total_Contractors, IFNULL(Total_Materials, 0) as Total_Materials, (Total_Contractors + IFNULL(Total_Materials, 0)) as Total_Project_Cost from (
select projectID, sum(hourlyWage*hoursWorked)  Total_Contractors from contractors.contractor con, contractor_hours conhours
where con.contractorID = conhours.contractorID
GROUP BY projectID
ORDER BY projectID) as contractorCost
LEFT JOIN
(select projectID, sum(costPerMaterial*quantity) Total_Materials from contractors.material mat, material_quantity matq
where mat.materialID = matq.materialID
Group BY matq.projectID
Order By matq.projectID) as materialCost
 ON contractorCost.projectID = materialCost.projectID) as TotalCost
 where TotalCost.projectID = project.projectID
 and project.progress = 'FINISHED'
 order by Total_Project_Cost Desc
 
 
 
 /*Show the contractors who have charged more than $20000*/
 select con.contractorID, fName, lName, hourlyWage, phoneNumber, email, sum(hoursWorked) as "Total Hours Worked" ,hourlyWage*hoursWorked as "Total Earned"  
from contractors.contractor con, contractor_hours conhours
where con.contractorID = conhours.contractorID 
and hourlyWage*hoursWorked > 20000
group by con.contractorID, fName, lName, hourlyWage, phoneNumber, email
Order By hourlyWage*hoursWorked



/*Show the amount project each supervisor has been assigned in ascending order */
select Count(sup.supervisorID) as "Project Quantity", fName, lName, email  from contractors.supervisor sup, contractors.project pro
where sup.supervisorID = pro.supervisorID
GROUP BY fName, lName, email
order by Count(sup.supervisorID)


/*List the on going Projects with their respective locations*/
select   p.projectID, locationID, p.name, streetNumber, streetName, city, province, postCode  from project p, clients c, location l
where p.clientsID = c.clientsID
and l.clientsID = c.clientsID
and p.progress ="IN PROGRESS"
Order By projectID, locationID

/*Which project are finished and which are on-going?*/
select  progress as "Project Progress", count(progress) as "Quantity"  from project p
group by progress

/*Project using the more than 2 materials of different types that are on-going*/
select project.projectID, name, projectStart, projectEnd, progress, Different_Materials from project, (select  projectID, count(materialID) as Different_Materials from material_quantity
group by projectID
order by projectID) mat
where mat.projectID = project.projectID and
mat.Different_Materials > 2
and progress = "IN PROGRESS"
order by Different_Materials desc
