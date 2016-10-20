package ratio.com.marvelQ.lib.di;

import android.app.Activity;

import com.bumptech.glide.request.RequestListener;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ratio.com.marvelQ.lib.EventBus;
import ratio.com.marvelQ.lib.GlideImageLoader;
import ratio.com.marvelQ.lib.GreenRobotEventBus;
import ratio.com.marvelQ.lib.ImageLoader;

/**
 * Created by ykro.
 */
@Module
public class LibsModule {
    Activity activity;
    RequestListener requestListener;

    public LibsModule() {
    }
    public LibsModule(Activity activity,RequestListener requestListener) {
        this.activity = activity;
        this.requestListener = requestListener;
    }

    @Provides
    @Singleton
    EventBus provideEventBus() {
        return new GreenRobotEventBus();
    }

    @Provides
    @Singleton
    ImageLoader provideImageLoader(Activity activity) {
        GlideImageLoader imageLoader = new GlideImageLoader();
        if (activity != null) {
            imageLoader.setLoaderContext(activity,requestListener);
        }
        return imageLoader;
    }


    @Provides
    @Singleton
    Activity provideActivity(){
        return this.activity;
    }

}
