package method2;

import java.util.List;

import android.hardware.Camera.Size;

public class UsefulImgSize {
	public static Size getOptimalSize(List<Size> sizes, int w, int h) {
		// final double ASPECT_TOLERANCE = 0.05;
		// double targetRatio = (double) w / h;
		if (sizes == null)
			return null;

		Size optimalSize = null;
		// double minDiff = Double.MAX_VALUE;

		// int targetHeight = h;

		// Try to find an size match aspect ratio and size
		// for (Size size : sizes) {
		// double ratio = (double) size.width / size.height;
		// Log.i("" + size.width, "" + size.height);
		// if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
		// continue;
		// if (Math.abs(size.height - targetHeight) < minDiff) {
		// optimalSize = size;
		// minDiff = Math.abs(size.height - targetHeight);
		// }
		// Log.i("wsy", "success" + ratio);
		// optimalSize = size;
		// }

		// Cannot find the one match the aspect ratio, ignore the requirement
		// if (optimalSize == null) {
		// minDiff = Double.MAX_VALUE;
		// for (Size size : sizes) {
		// if (Math.abs(size.height - targetHeight) < minDiff) {
		// optimalSize = size;
		// minDiff = Math.abs(size.height - targetHeight);
		// }
		// }
		// }

		// find the biggest size
		int maxSizeP = 0;
		int sizeP = 0;
		for (Size size : sizes) {
			// Log.i("" + (double) size.height / (double) size.width,
			// size.height
			// + " " + size.width);
			sizeP = size.height * size.width;
			if (sizeP > maxSizeP) {
				maxSizeP = sizeP;
				optimalSize = size;
			}
		}
		return optimalSize;
	}
}
