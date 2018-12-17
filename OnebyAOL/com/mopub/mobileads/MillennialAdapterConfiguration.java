package com.mopub.mobileads;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.millennialmedia.MMSDK;
import com.millennialmedia.internal.ActivityListenerManager;
import com.mopub.common.BaseAdapterConfiguration;
import com.mopub.common.OnNetworkInitializationFinishedListener;
import com.mopub.common.Preconditions;
import com.mopub.common.logging.MoPubLog;

import java.util.Map;
import static com.mopub.common.logging.MoPubLog.AdapterLogEvent.CUSTOM_WITH_THROWABLE;

public class MillennialAdapterConfiguration extends BaseAdapterConfiguration {

    //Millennial's Keys
    private static final String DCN_KEY = "dcn";
    private static final String APID_KEY = "adUnitID";

    // Adapter's keys
    private static final String ADAPTER_NAME = MillennialAdapterConfiguration.class.getSimpleName();
    private static final String ADAPTER_VERSION = "6.8.2.0";
    private static final String MOPUB_NETWORK_NAME = "Millennial";

    @NonNull
    @Override
    public String getAdapterVersion() {
        return ADAPTER_VERSION;
    }

    @Nullable
    @Override
    public String getBiddingToken(@NonNull Context context) {
        return null;
    }

    @NonNull
    @Override
    public String getMoPubNetworkName() {
        return MOPUB_NETWORK_NAME;
    }

    @NonNull
    @Override
    public String getNetworkSdkVersion() {
        String adapterVersion = getAdapterVersion();
        return (!TextUtils.isEmpty(adapterVersion)) ?
                adapterVersion.substring(0, adapterVersion.lastIndexOf('.')) : "";
    }

    @Override
    public void initializeNetwork(@NonNull final Context context, @Nullable final Map<String, String> configuration, @NonNull final OnNetworkInitializationFinishedListener listener) {

        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(listener);

        boolean networkInitializationSucceeded = false;

        synchronized (MillennialAdapterConfiguration.class) {
            try {
                if (configuration != null) {
                    final String apid_key = configuration.get(APID_KEY);

                    MMSDK.initialize((Application) (context.getApplicationContext()));

                    networkInitializationSucceeded = true;
                }
            } catch (Exception e) {
                    MoPubLog.log(CUSTOM_WITH_THROWABLE, "Initializing AOL has encountered" +
                            "an exception.", e);
            }
        }
        if (networkInitializationSucceeded) {
            listener.onNetworkInitializationFinished(MillennialAdapterConfiguration.class,
                    MoPubErrorCode.ADAPTER_INITIALIZATION_SUCCESS);
        } else {
            listener.onNetworkInitializationFinished(MillennialAdapterConfiguration.class,
                    MoPubErrorCode.ADAPTER_CONFIGURATION_ERROR);
        }
    }

}