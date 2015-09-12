package org.littermappingtool.backend.servlets.dataimport;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

/**
 * Servlet to upload blob with CSV data.
 */
@SuppressWarnings("serial")
public class UploadBlobServlet extends HttpServlet {

	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		List<BlobKey> blobKeys = blobs.get("data");

		if (blobKeys == null || blobKeys.isEmpty()) {
			res.sendRedirect("/");
		} else {
			res.sendRedirect("/upload-success?blob-key=" + blobKeys.get(0).getKeyString());
		}
	}
}
