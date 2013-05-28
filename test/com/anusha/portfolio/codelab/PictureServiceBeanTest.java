package com.anusha.portfolio.codelab;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

/**
 * PictureServiceBean - Interface - DAO - (Awkward Collaborator) DataStoreService
 * @author rangar
 *
 */
public class PictureServiceBeanTest {
	
	/**
	 * Simple Stub - Fake Bean introduces Fake Method or Fake Input Data
	 */
	private class PictureServiceBeanStub extends BeanPictureService {
		public DAOPicture getPictureDAO() {
			DAOPicture pictureDAO = new PictureDataSourceDAOStub();
			return pictureDAO;
		}
	}
	
	/**
	 * Simple Stub - Fake PictureDataSourceDAO
	 * @author rangar
	 
	 * Since every method could internally call another method that
	 * contacts datasource, each method in the Bean needs to be faked. This
	 * bean cannot be used for all tests.
	 * 
	 * Since DTO can be generated locally, Fake Input Data is not required
	 *
	 */
	private class PictureDataSourceDAOStub implements DAOPicture {

		@Override
		public Iterable<Entity> getAllPictures() {
			return null;
		}

		@Override
		public Iterable<Entity> getSinglePicture(String pictureName) {
			return null;
		}

		@Override
		public Entity getSinglePictureEntity(String pictureKey) {
			for (DTOPicture pictureDTO : pictureList) {
				if (pictureDTO.getPictureName().equalsIgnoreCase(pictureKey)) {
					existInList = true;
				}
			}
			return null;
		}

		@Override
		public boolean createOrUpdatePicture(DTOPicture pictureDTO) {
			if (pictureDTO == null)
				return false;
			if(pictureDTO.getPictureName()!=null && 
					pictureDTO.getPictureName().length()>1) {
				getSinglePictureEntity(pictureDTO.getPictureName());
				if (!existInList)
					pictureList.add(pictureDTO);
				return true;
			} else {
				return false;
			}
		}

		@Override
		public void deletePictures(List<Key> keys) {
		}

		@Override
		public String deletePicture(String pictureKey) {
			for (DTOPicture pictureDTO : pictureList) {
				if (pictureDTO.getPictureName().equalsIgnoreCase(pictureKey)) {
					pictureList.remove(pictureDTO);
					return "Deletion Success";
				}
			}
			return "Deletion Failed";
		}

		@Override
		public Iterable<Entity> getPicturesForSubMenu(String topMenuKey) {
			return null;
		}
		
	}
	
	/**
	 * Test code
	 */
	private BeanPictureService bean;
	private DTOPicture pictureDTOWithRandomName1;
	private DTOPicture pictureDTOWithRandomName2;
	private DTOPicture pictureDTOWithNullName;
	final String pictureName = "Picture";
	final String pictureIncludeExclude = "include";
	final String pictureDescription = "description";
	private List<DTOPicture> pictureList = new ArrayList<DTOPicture>();
	boolean existInList;

	@Before
	public void setUp() {
		if (!pictureList.isEmpty()) {
			pictureList.clear();
		}
		bean = new PictureServiceBeanStub();
		pictureDTOWithRandomName1 = new DTOPicture(
				pictureName+"1", pictureIncludeExclude,
					pictureDescription, null);
		pictureDTOWithRandomName2 = new DTOPicture(
				pictureName+"2", pictureIncludeExclude,
					pictureDescription, null);
		pictureDTOWithNullName = new DTOPicture(null, null, null, null);
		existInList = false;
	}
	
	@Test
	public void getPictureInEmptyList() {
		try {
			bean.getSinglePictureEntity(pictureDTOWithRandomName1.getPictureName());
			assertFalse(existInList);
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void getPictureWithValidName() {
		try {
			bean.createOrUpdatePicture(pictureDTOWithRandomName1);
			bean.getSinglePictureEntity(pictureDTOWithRandomName1.getPictureName());
			assertTrue(existInList);
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void getPictureWithInValidName() {
		try {
			bean.createOrUpdatePicture(pictureDTOWithRandomName1);
			bean.getSinglePictureEntity(pictureDTOWithRandomName2.getPictureName());
			assertFalse(existInList);
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void getPictureWithNullName() {
		try {
			bean.createOrUpdatePicture(pictureDTOWithRandomName1);
			bean.getSinglePictureEntity(pictureDTOWithNullName.getPictureName());
			assertFalse(existInList);
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void getPictureWithNull() {
		try {
			bean.getSinglePictureEntity(null);
			assertFalse(existInList);
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void createPicture() {
		try {
			assertTrue(bean.createOrUpdatePicture(pictureDTOWithRandomName1));
			assertEquals(1, pictureList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void createPictureWithNullName() {
		try {
			assertFalse(bean.createOrUpdatePicture(pictureDTOWithNullName));
			assertEquals(0, pictureList.size());
		} catch (Exception e){
			fail();
		}
		
	}
	
	@Test
	public void createPictureWithNull() {
		try {
			assertFalse(bean.createOrUpdatePicture(null));
			assertEquals(0, pictureList.size());
		} catch (Exception e){
			fail();
		}
		
	}
	
	@Test
	public void createPictureWithInvalidName() {
		try {
			assertFalse(bean.createOrUpdatePicture(new DTOPicture()));
			assertEquals(0, pictureList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void createMultiplePictures() {
		try {
			assertTrue(bean.createOrUpdatePicture(pictureDTOWithRandomName1));
			assertTrue(bean.createOrUpdatePicture(pictureDTOWithRandomName2));
			assertEquals(2, pictureList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	// edit scenarios limited as editing of name is disallowed and uses the 
	// same as create API.
	@Test
	public void editPicture() {
		try {
			assertTrue(bean.createOrUpdatePicture(pictureDTOWithRandomName1));
			assertTrue(bean.createOrUpdatePicture(pictureDTOWithRandomName1));
			assertEquals(1, pictureList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void editMultiplePicture() {
		try {
			assertTrue(bean.createOrUpdatePicture(pictureDTOWithRandomName1));
			assertTrue(bean.createOrUpdatePicture(pictureDTOWithRandomName2));
			assertTrue(bean.createOrUpdatePicture(pictureDTOWithRandomName1));
			assertTrue(bean.createOrUpdatePicture(pictureDTOWithRandomName2));
			assertEquals(2, pictureList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void deletePicture() {
		try {
			bean.createOrUpdatePicture(pictureDTOWithRandomName1);
			assertEquals(1, pictureList.size());
			bean.deletePicture(pictureDTOWithRandomName1.getPictureName());
			assertEquals(0, pictureList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void deletePictureWithInvalidName() {
		try {
			bean.createOrUpdatePicture(pictureDTOWithRandomName1);
			bean.deletePicture(pictureDTOWithRandomName2.getPictureName());
			assertEquals(1, pictureList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void deletePictureWithEmptyName() {
		try {
			bean.createOrUpdatePicture(pictureDTOWithRandomName1);
			bean.deletePicture(new DTOPicture().getPictureName());
			assertEquals(1, pictureList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void deletePictureWithNullName() {
		try {
			bean.createOrUpdatePicture(pictureDTOWithRandomName1);
			bean.deletePicture(pictureDTOWithNullName.getPictureName());
			assertEquals(1, pictureList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void deletePictureWithNull() {
		try {
			bean.deletePicture(null);
			assertEquals(0, pictureList.size());
		} catch (Exception e){
			e.printStackTrace();
			fail();
		}
		
	}
	
}
