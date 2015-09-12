package org.littermappingtool.backend.utils.jsp;

import java.util.List;

import org.littermappingtool.backend.BinType;
import org.littermappingtool.backend.dao.PreparedQueries;

/**
 * Data loader for the upload-litter.jsp page.
 */
public class UploadBinDataLoader {
	

	/** The bin types. */
	private List<BinType> binTypes;

	/**
	 * Instantiates a new upload litter data loader.
	 */
	public UploadBinDataLoader() {
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
	 * Process.
	 */
	private void process() {
		
		binTypes = PreparedQueries.getAllBinTypes();
	}
}
