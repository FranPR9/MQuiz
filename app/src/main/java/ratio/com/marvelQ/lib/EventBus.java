package ratio.com.marvelQ.lib;

/**
 * Created by ykro.
 */
public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);
    boolean isRegister(Object event);

}
