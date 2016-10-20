package ratio.com.marvelQ.lib;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by ykro.
 */
public interface ImageLoader {
    void load(ImageView imageView, String URL);

    void setOnFinishedImageLoadingListener(Object object);

}
