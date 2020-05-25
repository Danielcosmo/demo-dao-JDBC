package application;
import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("Find seller by id: ");
		Seller seller = sellerDao.findById(1);
		System.out.println(seller);
		
		System.out.println("\n\n\nFind seller by Department: ");
		Department dp = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(dp);
		
		for(Seller s : list) {
			System.out.println(s);
		}
		
		System.out.println("\n\n\nFind All: ");
		list = sellerDao.findAll();
		for(Seller s : list) {
			System.out.println(s);
		}
		
		System.out.println("Insert");
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 1000.0, dp);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! New id: "+ newSeller.getId());
	
		System.out.println("Update");
		seller = sellerDao.findById(1);
		seller.setName("Marta Waine");
		sellerDao.update(seller);
		System.out.println("Update completed");
		
		System.out.println("Delete por id");
		sellerDao.deleteById(5);
	}

}
