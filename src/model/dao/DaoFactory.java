package model.dao;

import db.DB;
import model.ado.impl.DepartmentDaoImplJDBC;
import model.ado.impl.SellerDaoImplJDBC;

public class DaoFactory {
	
	public static SellerDao createSellerDao() {
		
		return new SellerDaoImplJDBC(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoImplJDBC(DB.getConnection());
	}
}
