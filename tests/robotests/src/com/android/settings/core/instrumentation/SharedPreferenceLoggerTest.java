/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.settings.core.instrumentation;

import android.content.Context;
import android.content.SharedPreferences;
import com.android.settings.SettingsRobolectricTestRunner;
import com.android.settings.TestConfig;
import com.android.settings.overlay.FeatureFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SettingsRobolectricTestRunner.class)
@Config(manifest = TestConfig.MANIFEST_PATH, sdk = TestConfig.SDK_VERSION)
public class SharedPreferenceLoggerTest {

    private static final String TEST_TAG = "tag";
    private static final String TEST_KEY = "key";

    @Mock
    private LogWriter mLogWriter;

    private MetricsFeatureProvider mMetricsFeature;
    private ShadowApplication mApplication;
    private SharedPreferencesLogger mSharedPrefLogger;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mApplication = ShadowApplication.getInstance();
        Context context = mApplication.getApplicationContext();
        mMetricsFeature = FeatureFactory.getFactory(context).getMetricsFeatureProvider();
        ((MetricsFeatureProviderImpl) mMetricsFeature).addLogWriter(mLogWriter);

        mSharedPrefLogger = new SharedPreferencesLogger(
                mApplication.getApplicationContext(), TEST_TAG);
    }

    @Test
    public void putInt_shouldNotLogInitialPut() {
        final SharedPreferences.Editor editor = mSharedPrefLogger.edit();
        editor.putInt(TEST_KEY, 1);
        editor.putInt(TEST_KEY, 1);
        editor.putInt(TEST_KEY, 1);
        editor.putInt(TEST_KEY, 2);
        editor.putInt(TEST_KEY, 2);
        editor.putInt(TEST_KEY, 2);
        editor.putInt(TEST_KEY, 2);

        verify(mLogWriter, times(6)).count(any(Context.class), anyString(), anyInt());
    }

    @Test
    public void putBoolean_shouldNotLogInitialPut() {
        final SharedPreferences.Editor editor = mSharedPrefLogger.edit();
        editor.putBoolean(TEST_KEY, true);
        editor.putBoolean(TEST_KEY, true);
        editor.putBoolean(TEST_KEY, false);
        editor.putBoolean(TEST_KEY, false);
        editor.putBoolean(TEST_KEY, false);

        verify(mLogWriter, times(4)).count(any(Context.class), anyString(), anyInt());
    }

    @Test
    public void putLong_shouldNotLogInitialPut() {
        final SharedPreferences.Editor editor = mSharedPrefLogger.edit();
        editor.putLong(TEST_KEY, 1);
        editor.putLong(TEST_KEY, 1);
        editor.putLong(TEST_KEY, 1);
        editor.putLong(TEST_KEY, 1);
        editor.putLong(TEST_KEY, 2);

        verify(mLogWriter, times(4)).count(any(Context.class), anyString(), anyInt());
    }

    @Test
    public void putFloat_shouldNotLogInitialPut() {
        final SharedPreferences.Editor editor = mSharedPrefLogger.edit();
        editor.putFloat(TEST_KEY, 1);
        editor.putFloat(TEST_KEY, 1);
        editor.putFloat(TEST_KEY, 1);
        editor.putFloat(TEST_KEY, 1);
        editor.putFloat(TEST_KEY, 2);

        verify(mLogWriter, times(4)).count(any(Context.class), anyString(), anyInt());
    }

}
