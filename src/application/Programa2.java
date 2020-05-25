package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Programa2 {

	public static void main(String[] args) {
		
		DepartmentDao dpDao = DaoFactory.createDepartmentDao();
		
		System.out.println("Find department by Id: ");
		Department dp = dpDao.findById(1);
		System.out.println(dp);
		
		System.out.println("Find all");
		List<Department> list = dpDao.findAll();
		for(Department d : list) {
			System.out.println(d);
		}
	/*	System.out.println("Insert ");
		Department newDp = new Department(null, "Stephen King");
		dpDao.insert(newDp);
		System.out.println("Inserted! New Id: "+ newDp.getId());
	*/
		System.out.println("Update");
		dp = dpDao.findById(5);
		dp.setName("Edgard Allan Poe");
		dpDao.update(dp);
		System.out.println("Update completed");
		
	}

}
