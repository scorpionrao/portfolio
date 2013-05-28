package com.anusha.portfolio.codelab;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

/**
 * SubMenuServiceBean - Interface - DAO - (Awkward Collaborator) DataStoreService
 * @author rangar
 *
 */
public class SubMenuServiceBeanTest {
	
	/**
	 * Simple Stub - Fake Bean introduces Fake Method or Fake Input Data
	 */
	private class SubMenuServiceBeanStub extends BeanSubMenuService {
		public DAOSubMenu getSubMenuDAO() {
			DAOSubMenu subMenuDAO = new SubMenuDataSourceDAOStub();
			return subMenuDAO;
		}
	}
	
	/**
	 * Simple Stub - Fake SubMenuDataSourceDAO
	 * @author rangar
	 
	 * Since every method could internally call another method that
	 * contacts datasource, each method in the Bean needs to be faked. This
	 * bean cannot be used for all tests.
	 * 
	 * Since DTO can be generated locally, Fake Input Data is not required
	 *
	 */
	private class SubMenuDataSourceDAOStub implements DAOSubMenu {

		@Override
		public Iterable<Entity> getAllSubMenus() {
			return null;
		}

		@Override
		public Iterable<Entity> getSingleSubMenu(String subMenuName) {
			return null;
		}

		@Override
		public Entity getSingleSubMenuEntity(String subMenuKey) {
			for (DTOSubMenu subMenuDTO : subMenuList) {
				if (subMenuDTO.getSubMenuName().equalsIgnoreCase(subMenuKey)) {
					existInList = true;
				}
			}
			return null;
		}

		@Override
		public boolean createOrUpdateSubMenu(DTOSubMenu subMenuDTO) {
			if (subMenuDTO == null)
				return false;
			if(subMenuDTO.getSubMenuName()!=null && 
					subMenuDTO.getSubMenuName().length()>1) {
				getSingleSubMenuEntity(subMenuDTO.getSubMenuName());
				if (!existInList)
					subMenuList.add(subMenuDTO);
				return true;
			} else {
				return false;
			}
		}

		@Override
		public void deleteSubMenus(List<Key> keys) {
		}

		@Override
		public String deleteSubMenu(String subMenuKey) {
			for (DTOSubMenu subMenuDTO : subMenuList) {
				if (subMenuDTO.getSubMenuName().equalsIgnoreCase(subMenuKey)) {
					subMenuList.remove(subMenuDTO);
					return "Deletion Success";
				}
			}
			return "Deletion Failed";
		}

		@Override
		public Iterable<Entity> getSubMenusForTopMenu(String topMenuKey) {
			return null;
		}
		
	}
	
	/**
	 * Test code
	 */
	private BeanSubMenuService bean;
	private DTOSubMenu subMenuDTOWithRandomName1;
	private DTOSubMenu subMenuDTOWithRandomName2;
	private DTOSubMenu subMenuDTOWithNullName;
	final String subMenuName = "SubMenu";
	final String subMenuIncludeExclude = "include";
	final String subMenuDescription = "description";
	private List<DTOSubMenu> subMenuList = new ArrayList<DTOSubMenu>();
	boolean existInList;

	@Before
	public void setUp() {
		if (!subMenuList.isEmpty()) {
			subMenuList.clear();
		}
		bean = new SubMenuServiceBeanStub();
		subMenuDTOWithRandomName1 = new DTOSubMenu(
				subMenuName+"1", subMenuIncludeExclude,
					subMenuDescription, null);
		subMenuDTOWithRandomName2 = new DTOSubMenu(
				subMenuName+"2", subMenuIncludeExclude,
					subMenuDescription, null);
		subMenuDTOWithNullName = new DTOSubMenu(null, null, null, null);
		existInList = false;
	}
	
	@Test
	public void getSubMenuInEmptyList() {
		try {
			bean.getSingleSubMenuEntity(subMenuDTOWithRandomName1.getSubMenuName());
			assertFalse(existInList);
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void getSubMenuWithValidName() {
		try {
			bean.createOrUpdateSubMenu(subMenuDTOWithRandomName1);
			bean.getSingleSubMenuEntity(subMenuDTOWithRandomName1.getSubMenuName());
			assertTrue(existInList);
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void getSubMenuWithInValidName() {
		try {
			bean.createOrUpdateSubMenu(subMenuDTOWithRandomName1);
			bean.getSingleSubMenuEntity(subMenuDTOWithRandomName2.getSubMenuName());
			assertFalse(existInList);
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void getSubMenuWithNullName() {
		try {
			bean.createOrUpdateSubMenu(subMenuDTOWithRandomName1);
			bean.getSingleSubMenuEntity(subMenuDTOWithNullName.getSubMenuName());
			assertFalse(existInList);
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void getSubMenuWithNull() {
		try {
			bean.getSingleSubMenuEntity(null);
			assertFalse(existInList);
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void createSubMenu() {
		try {
			assertTrue(bean.createOrUpdateSubMenu(subMenuDTOWithRandomName1));
			assertEquals(1, subMenuList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void createSubMenuWithNullName() {
		try {
			assertFalse(bean.createOrUpdateSubMenu(subMenuDTOWithNullName));
			assertEquals(0, subMenuList.size());
		} catch (Exception e){
			fail();
		}
		
	}
	
	@Test
	public void createSubMenuWithNull() {
		try {
			assertFalse(bean.createOrUpdateSubMenu(null));
			assertEquals(0, subMenuList.size());
		} catch (Exception e){
			fail();
		}
		
	}
	
	@Test
	public void createSubMenuWithInvalidName() {
		try {
			assertFalse(bean.createOrUpdateSubMenu(new DTOSubMenu()));
			assertEquals(0, subMenuList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void createMultipleSubMenu() {
		try {
			assertTrue(bean.createOrUpdateSubMenu(subMenuDTOWithRandomName1));
			assertTrue(bean.createOrUpdateSubMenu(subMenuDTOWithRandomName2));
			assertEquals(2, subMenuList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	// edit scenarios limited as editing of name is disallowed and uses the 
	// same as create API.
	@Test
	public void editSubMenu() {
		try {
			assertTrue(bean.createOrUpdateSubMenu(subMenuDTOWithRandomName1));
			assertTrue(bean.createOrUpdateSubMenu(subMenuDTOWithRandomName1));
			assertEquals(1, subMenuList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void editMultipleSubMenu() {
		try {
			assertTrue(bean.createOrUpdateSubMenu(subMenuDTOWithRandomName1));
			assertTrue(bean.createOrUpdateSubMenu(subMenuDTOWithRandomName2));
			assertTrue(bean.createOrUpdateSubMenu(subMenuDTOWithRandomName1));
			assertTrue(bean.createOrUpdateSubMenu(subMenuDTOWithRandomName2));
			assertEquals(2, subMenuList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void deleteSubMenu() {
		try {
			bean.createOrUpdateSubMenu(subMenuDTOWithRandomName1);
			assertEquals(1, subMenuList.size());
			bean.deleteSubMenu(subMenuDTOWithRandomName1.getSubMenuName());
			assertEquals(0, subMenuList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void deleteSubMenuWithInvalidName() {
		try {
			bean.createOrUpdateSubMenu(subMenuDTOWithRandomName1);
			bean.deleteSubMenu(subMenuDTOWithRandomName2.getSubMenuName());
			assertEquals(1, subMenuList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void deleteSubMenuWithEmptyName() {
		try {
			bean.createOrUpdateSubMenu(subMenuDTOWithRandomName1);
			bean.deleteSubMenu(new DTOSubMenu().getSubMenuName());
			assertEquals(1, subMenuList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void deleteSubMenuWithNullName() {
		try {
			bean.createOrUpdateSubMenu(subMenuDTOWithRandomName1);
			bean.deleteSubMenu(subMenuDTOWithNullName.getSubMenuName());
			assertEquals(1, subMenuList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void deleteSubMenuWithNull() {
		try {
			bean.deleteSubMenu(null);
			assertEquals(0, subMenuList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
}
