/**
 * Copyright 2015 Marcin Polak
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.polok.localify.module;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.annotation.RawRes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.concurrent.Executor;

/**
 *
 */
public class LocalifyModule extends BaseModule {

    /**
     *
     */
    private AssetManager assetManager;

    /**
     *
     */
    private Resources resources;

    /**
     *
     * @param assetManager
     * @param resources
     * @param executor
     */
    public LocalifyModule(AssetManager assetManager, Resources resources, Executor executor) {
        super(executor);

        this.assetManager = assetManager;
        this.resources = resources;
    }

    @Override
    public ExtAsync async() {
        return extAsync;
    }

    @Override
    public ExtRxJava rx() {
        return extRxJava;
    }

    @Override
    public String loadAssetsFile(String fileName) {
        int size = 0;
        byte[] buffer = null;
        try {
            InputStream is = assetManager.open(fileName);
            size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();

            return new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public String loadRawFile(@RawRes int fileNameRawId) {
        InputStream is = resources.openRawResource(fileNameRawId);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return writer.toString();
    }
}
