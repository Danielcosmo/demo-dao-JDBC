package application;
import java.util.Date;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Department dp = new Department(1 , "Books");
		
		Seller s = new Seller(1, "João", "joao@gmail.com", new Date(), 3000.0, dp);
		
		//injeção de dependência
		SellerDao sd = DaoFactory.createSellerDao();
	}

}
