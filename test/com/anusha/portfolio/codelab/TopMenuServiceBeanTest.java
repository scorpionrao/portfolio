package com.anusha.portfolio.codelab;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

/**
 * TopMenuServiceBean - Interface - DAO - (Awkward Collaborator) DataStoreService
 * @author rangar
 *
 */
public class TopMenuServiceBeanTest {
	
	/**
	 * Simple Stub - Fake Bean introduces Fake Method or Fake Input Data
	 */
	private class TopMenuServiceBeanStub extends BeanTopMenuService {
		public DAOTopMenu getTopMenuDAO() {
			DAOTopMenu topMenuDAO = new TopMenuDataSourceDAOStub();
			return topMenuDAO;
		}
	}
	
	/**
	 * Simple Stub - Fake TopMenuDataSourceDAO
	 * @author rangar
	 
	 * Since every method could internally call another method that
	 * contacts datasource, each method in the Bean needs to be faked. This
	 * bean cannot be used for all tests.
	 * 
	 * Since DTO can be generated locally, Fake Input Data is not required
	 *
	 */
	private class TopMenuDataSourceDAOStub implements DAOTopMenu {

		@Override
		public Iterable<Entity> getAllTopMenus() {
			return null;
		}

		@Override
		public Entity getTopMenu(String topMenuName) {
			for (DTOTopMenu topMenuDTO : topMenuList) {
				if (topMenuDTO.getTopMenuName().equalsIgnoreCase(topMenuName)) {
					existInList = true;
				}
			}
			return null;
		}

		@Override
		public boolean createOrUpdateTopMenu(DTOTopMenu topMenuDTO) {
			if (topMenuDTO == null)
				return false;
			if(topMenuDTO.getTopMenuName()!=null && 
					topMenuDTO.getTopMenuName().length()>1) {
				getTopMenu(topMenuDTO.getTopMenuName());
				if (!existInList)
					topMenuList.add(topMenuDTO);
				return true;
			} else {
				return false;
			}
		}

		@Override
		public void deleteTopMenus(List<Key> keys) {
		}

		@Override
		public String deleteTopMenu(String topMenuKey) {
			for (DTOTopMenu topMenuDTO : topMenuList) {
				if (topMenuDTO.getTopMenuName().equalsIgnoreCase(topMenuKey)) {
					topMenuList.remove(topMenuDTO);
					return "Deletion Success";
				}
			}
			return "Deletion Failed";
		}

		
	}
	
	/**
	 * Test code
	 */
	private BeanTopMenuService bean;
	private DTOTopMenu topMenuDTOWithRandomName1;
	private DTOTopMenu topMenuDTOWithRandomName2;
	private DTOTopMenu topMenuDTOWithNullName;
	final String topMenuName = "TopMenu";
	final String topMenuIncludeExclude = "include";
	final String topMenuDescription = "description";
	private List<DTOTopMenu> topMenuList = new ArrayList<DTOTopMenu>();
	boolean existInList;

	@Before
	public void setUp() {
		if (!topMenuList.isEmpty()) {
			topMenuList.clear();
		}
		bean = new TopMenuServiceBeanStub();
		topMenuDTOWithRandomName1 = new DTOTopMenu(
				topMenuName+"1", topMenuIncludeExclude,
					topMenuDescription);
		topMenuDTOWithRandomName2 = new DTOTopMenu(
				topMenuName+"2", topMenuIncludeExclude,
					topMenuDescription);
		topMenuDTOWithNullName = new DTOTopMenu(null, null, null);
		existInList = false;
	}
	
	@Test
	public void getTopMenuInEmptyList() {
		try {
			bean.getTopMenu(topMenuDTOWithRandomName1.getTopMenuName());
			assertFalse(existInList);
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void getTopMenuWithValidName() {
		try {
			bean.createOrUpdateTopMenu(topMenuDTOWithRandomName1);
			bean.getTopMenu(topMenuDTOWithRandomName1.getTopMenuName());
			assertTrue(existInList);
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void getTopMenuWithInValidName() {
		try {
			bean.createOrUpdateTopMenu(topMenuDTOWithRandomName1);
			bean.getTopMenu(topMenuDTOWithRandomName2.getTopMenuName());
			assertFalse(existInList);
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void getTopMenuWithNullName() {
		try {
			bean.createOrUpdateTopMenu(topMenuDTOWithRandomName1);
			bean.getTopMenu(topMenuDTOWithNullName.getTopMenuName());
			assertFalse(existInList);
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void getTopMenuWithNull() {
		try {
			bean.getTopMenu(null);
			assertFalse(existInList);
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void createTopMenu() {
		try {
			assertTrue(bean.createOrUpdateTopMenu(topMenuDTOWithRandomName1));
			assertEquals(1, topMenuList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void createTopMenuWithNullName() {
		try {
			assertFalse(bean.createOrUpdateTopMenu(topMenuDTOWithNullName));
			assertEquals(0, topMenuList.size());
		} catch (Exception e){
			fail();
		}
		
	}
	
	@Test
	public void createTopMenuWithNull() {
		try {
			assertFalse(bean.createOrUpdateTopMenu(null));
			assertEquals(0, topMenuList.size());
		} catch (Exception e){
			fail();
		}
		
	}
	
	@Test
	public void createTopMenuWithInvalidName() {
		try {
			assertFalse(bean.createOrUpdateTopMenu(new DTOTopMenu()));
			assertEquals(0, topMenuList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void createMultipleTopMenu() {
		try {
			assertTrue(bean.createOrUpdateTopMenu(topMenuDTOWithRandomName1));
			assertTrue(bean.createOrUpdateTopMenu(topMenuDTOWithRandomName2));
			assertEquals(2, topMenuList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	// edit scenarios limited as editing of name is disallowed and uses the 
	// same as create API.
	@Test
	public void editTopMenu() {
		try {
			assertTrue(bean.createOrUpdateTopMenu(topMenuDTOWithRandomName1));
			assertTrue(bean.createOrUpdateTopMenu(topMenuDTOWithRandomName1));
			assertEquals(1, topMenuList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void editMultipleTopMenu() {
		try {
			assertTrue(bean.createOrUpdateTopMenu(topMenuDTOWithRandomName1));
			assertTrue(bean.createOrUpdateTopMenu(topMenuDTOWithRandomName2));
			assertTrue(bean.createOrUpdateTopMenu(topMenuDTOWithRandomName1));
			assertTrue(bean.createOrUpdateTopMenu(topMenuDTOWithRandomName2));
			assertEquals(2, topMenuList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void deleteTopMenu() {
		try {
			bean.createOrUpdateTopMenu(topMenuDTOWithRandomName1);
			assertEquals(1, topMenuList.size());
			bean.deleteTopMenu(topMenuDTOWithRandomName1.getTopMenuName());
			assertEquals(0, topMenuList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void deleteTopMenuWithInvalidName() {
		try {
			bean.createOrUpdateTopMenu(topMenuDTOWithRandomName1);
			bean.deleteTopMenu(topMenuDTOWithRandomName2.getTopMenuName());
			assertEquals(1, topMenuList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void deleteTopMenuWithEmptyName() {
		try {
			bean.createOrUpdateTopMenu(topMenuDTOWithRandomName1);
			bean.deleteTopMenu(new DTOTopMenu().getTopMenuName());
			assertEquals(1, topMenuList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void deleteTopMenuWithNullName() {
		try {
			bean.createOrUpdateTopMenu(topMenuDTOWithRandomName1);
			bean.deleteTopMenu(topMenuDTOWithNullName.getTopMenuName());
			assertEquals(1, topMenuList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void deleteTopMenuWithNull() {
		try {
			bean.deleteTopMenu(null);
			assertEquals(0, topMenuList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
}
