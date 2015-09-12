package org.littermappingtool.backend.utils.jsp;

import java.util.List;

import org.littermappingtool.backend.BinType;
import org.littermappingtool.backend.Brand;
import org.littermappingtool.backend.LitterType;
import org.littermappingtool.backend.dao.PreparedQueries;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Data loader for the manage.jsp page.
 */
public class DatastoreMngDataLoader {

	/** The bin types. */
	private List<BinType> binTypes;
	
	/** The brands. */
	private List<Brand> brands;
	
	/** The litter types. */
	private List<LitterType> litterTypes;
	
	/** The bin types json string. */
	private String binTypesJsonString;
	
	/** The brands json string. */
	private String brandsJsonString;
	
	/** The litter types json string. */
	private String litterTypesJsonString;
	
	/**
	 * Instantiates a new data loader.
	 */
	public DatastoreMngDataLoader() {
		process();
	}

	/**
	 * Gets the bin types.
	 *
	 * @return the bin types
	 */
	public List<BinType> getBinTypes() {
		return binTypes;
	}

	/**
	 * Sets the bin types.
	 *
	 * @param binTypes the new bin types
	 */
	public void setBinTypes(List<BinType> binTypes) {
		this.binTypes = binTypes;
	}
	
	/**
	 * Gets the brands.
	 *
	 * @return the brands
	 */
	public List<Brand> getBrands() {
		return brands;
	}

	/**
	 * Sets the brands.
	 *
	 * @param brands the new brands
	 */
	public void setBrands(List<Brand> brands) {
		this.brands = brands;
	}

	/**
	 * Gets the litter types.
	 *
	 * @return the litter types
	 */
	public List<LitterType> getLitterTypes() {
		return litterTypes;
	}

	/**
	 * Sets the litter types.
	 *
	 * @param litterTypes the new litter types
	 */
	public void setLitterTypes(List<LitterType> litterTypes) {
		this.litterTypes = litterTypes;
	}
	
	/**
	 * Gets the bin types json string.
	 *
	 * @return the bin types json string
	 */
	public String getBinTypesJsonString() {
		return binTypesJsonString;
	}

	/**
	 * Sets the bin types json string.
	 *
	 * @param binTypesJsonString the new bin types json string
	 */
	public void setBinTypesJsonString(String binTypesJsonString) {
		this.binTypesJsonString = binTypesJsonString;
	}

	/**
	 * Gets the brands json string.
	 *
	 * @return the brands json string
	 */
	public String getBrandsJsonString() {
		return brandsJsonString;
	}

	/**
	 * Sets the brands json string.
	 *
	 * @param brandsJsonString the new brands json string
	 */
	public void setBrandsJsonString(String brandsJsonString) {
		this.brandsJsonString = brandsJsonString;
	}

	/**
	 * Gets the litter types json string.
	 *
	 * @return the litter types json string
	 */
	public String getLitterTypesJsonString() {
		return litterTypesJsonString;
	}

	/**
	 * Sets the litter types json string.
	 *
	 * @param litterTypesJsonString the new litter types json string
	 */
	public void setLitterTypesJsonString(String litterTypesJsonString) {
		this.litterTypesJsonString = litterTypesJsonString;
	}

	/**
	 * Process.
	 */
	private void process() {
		
		// Query all data
		binTypes = PreparedQueries.getAllBinTypes();
		brands = PreparedQueries.getAllBrands();
		litterTypes = PreparedQueries.getAllLitterTypes();
		
		// Create JSON strings
		Gson gson = new GsonBuilder().create();
		binTypesJsonString = gson.toJson(binTypes);
		brandsJsonString = gson.toJson(brands);
		litterTypesJsonString = gson.toJson(litterTypes);
	}
}
