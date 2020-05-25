package model.ado.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Statement;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoImplJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoImplJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller s) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("" + "INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) " + "value (?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, s.getName());
			st.setString(2, s.getEmail());
			st.setDate(3, new java.sql.Date(s.getBirthDate().getTime()));
			st.setDouble(4, s.getBaseSalary());
			st.setInt(5, s.getDepartment().getId());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();

				if (rs.next()) {
					int id = rs.getInt(1);
					s.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpeted erro! No rows Affected");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Seller s) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("" + "UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " + "WHERE id = ?");

			st.setString(1, s.getName());
			st.setString(2, s.getEmail());
			st.setDate(3, new java.sql.Date(s.getBirthDate().getTime()));
			st.setDouble(4, s.getBaseSalary());
			st.setInt(5, s.getDepartment().getId());
			st.setInt(6, s.getId());

			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());

		} finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*, department.Name as DepName FROM seller INNER JOIN department ON seller.DepartmentId = Department.Id WHERE seller.Id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {

				Department dp = instantiateDepartment(rs);

				Seller s = instantiateSeller(dp, rs);

				return s;
			}
			return null;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	@Override
	public List<Seller> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT seller.*, department.Name as DepName FROM seller "
					+ "INNER JOIN department ON seller.DepartmentId = department.Id " + "ORDER BY NAME");

			rs = st.executeQuery();

			List<Seller> list = new ArrayList<>();

			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {

				Department dp = map.get(rs.getInt("DepartmentId"));

				if (dp == null) {
					dp = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dp);
				}
				Seller s = instantiateSeller(dp, rs);

				list.add(s);

			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT seller.*, department.Name as DepName FROM seller "
					+ "INNER JOIN department ON seller.DepartmentId = department.Id WHERE "
					+ "DepartmentId = ? ORDER BY NAME");

			st.setInt(1, department.getId());
			rs = st.executeQuery();

			List<Seller> list = new ArrayList<>();

			/*
			 * aqui será implementada uma solução para que um mesmo departamento seja
			 * reutilizado A cada laço while haverá uma verificação se o objeto já existe
			 */

			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {

				Department dp = map.get(rs.getInt("DepartmentId"));

				if (dp == null) {
					dp = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dp);
				}

				Seller s = instantiateSeller(dp, rs);

				list.add(s);

			}
			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	// instanciar os vendedores
	private Seller instantiateSeller(Department dp, ResultSet rs) throws SQLException {
		Seller s = new Seller();
		s.setId(rs.getInt("Id"));
		s.setName(rs.getString("Name"));
		s.setEmail(rs.getString("Email"));
		s.setBaseSalary(rs.getDouble("BaseSalary"));
		s.setBirthDate(rs.getDate("BirthDate"));
		s.setDepartment(dp);
		return s;
	}

	// instanciar os departamentos
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dp = new Department();
		dp.setId(rs.getInt("DepartmentId"));
		dp.setName(rs.getString("DepName"));
		return dp;
	}

}
