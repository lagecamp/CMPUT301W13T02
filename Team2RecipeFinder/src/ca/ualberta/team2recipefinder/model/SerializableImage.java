package ca.ualberta.team2recipefinder.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Adapted code that allow a bitmap to be serializable
 * taken from: http://stackoverflow.com/questions/9219023/best-way-to-serialize-deserialize-an-image-in-android
 * @author cmput-301 team 2
 *
 */
public class SerializableImage implements Serializable {

	private static final long serialVersionUID = 2L;

	private static final int NO_IMAGE = -1;

	private transient Bitmap image;

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		if (image != null) {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			image.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] imageByteArray = stream.toByteArray();
			Log.d("str_image", stream.size() + " ");
			stream.close();
			int length = imageByteArray.length;
			out.writeInt(length);
			Log.d("s_image", length + " ");
			out.write(imageByteArray);
		}
		else {
			out.writeInt(NO_IMAGE);
		}
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		int length = in.readInt();

		if (length != NO_IMAGE) {
			byte[] imageByteArray = new byte[length];
			in.read(imageByteArray);
			Log.d("s_image", length + " ");
			this.image = BitmapFactory.decodeByteArray(imageByteArray, 0, length);
		}
	}
}