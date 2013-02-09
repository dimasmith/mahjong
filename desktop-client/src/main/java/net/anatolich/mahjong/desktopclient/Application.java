package net.anatolich.mahjong.desktopclient;

/**
 * Class that contains application context. Application context holds objects that should be accessible
 * throughout whole application. Context instance can be obtained by calling {@link #getContext() }.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class Application {

    private static Application context;

    public static synchronized Application getContext() {
        if ( context == null ) {
            context = new Application();
        }
        return context;
    }

    private boolean devMode = false;

    public boolean isDevMode() {
        return devMode;
    }

    void setDevMode( boolean devMode ) {
        this.devMode = devMode;
    }


}
