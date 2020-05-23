package model.dao;

import model.ado.impl.SellerDaoImplJDBC;

public class DaoFactory {
	
	public static SellerDao createSellerDao() {
		
		return new SellerDaoImplJDBC();
	}
}
