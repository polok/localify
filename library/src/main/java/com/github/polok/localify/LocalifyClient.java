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
package com.github.polok.localify;

import android.content.res.AssetManager;
import android.content.res.Resources;

import java.util.concurrent.Executor;

import com.github.polok.localify.executor.AndroidMainThreadExecutor;
import com.github.polok.localify.module.LocalifyModule;

/**
 *
 */
public class LocalifyClient {

    /**
     *
     */
    private LocalifyModule localifyModule;

    /**
     *
     * @param builder
     */
    private LocalifyClient(Builder builder) {
        this.localifyModule = new LocalifyModule(builder.assetManager, builder.resources, builder.executor);
    }

    /**
     *
     * @return
     */
    public LocalifyModule localify() {
        return localifyModule;
    }

    /**
     *
     */
    public static class Builder {
        /**
         *
         */
        AssetManager assetManager;

        /**
         *
         */
        Resources resources;

        /**
         *
         */
        Executor executor;

        /**
         *
         */
        public Builder() {
            this.executor = new AndroidMainThreadExecutor();
        }

        /**
         *
         * @param executor
         * @return
         */
        public Builder withExecutor(Executor executor) {
            this.executor = executor;
            return this;
        }

        /**
         *
         * @param assetManager
         * @return
         */
        public Builder withAssetManager(AssetManager assetManager) {
            this.assetManager = assetManager;
            return this;
        }

        /**
         *
         * @param resources
         * @return
         */
        public Builder withResources(Resources resources) {
            this.resources = resources;
            return this;
        }

        /**
         *
         * @return
         */
        public LocalifyClient build() {
            return new LocalifyClient(this);
        }

    }
}
