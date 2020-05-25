package application;
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
	}

}
