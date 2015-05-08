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
package com.github.polok.localify.api;

import android.support.annotation.RawRes;

import com.github.polok.localify.LocalifyCallback;

/**
 *
 * @param <T>
 */
public interface LocalifyAsync<T> {

    /**
     *
     * @param fileName
     * @param callback
     */
    void loadAssetsFile(String fileName, LocalifyCallback<T> callback);

    /**
     *
     * @param fileNameRawId
     * @param callback
     */
    void loadRawFile(@RawRes int fileNameRawId, LocalifyCallback<T> callback);

}
