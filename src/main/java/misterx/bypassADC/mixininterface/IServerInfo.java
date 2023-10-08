package misterx.bypassADC.mixininterface;

public interface IServerInfo {
    void setLastPing(long timeMillis);

    long getLastPing();
}

