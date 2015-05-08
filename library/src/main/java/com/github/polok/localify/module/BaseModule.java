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

import android.support.annotation.RawRes;

import java.util.concurrent.Executor;

import com.github.polok.localify.LocalifyCallback;
import com.github.polok.localify.api.Localify;
import com.github.polok.localify.api.LocalifyAsync;
import com.github.polok.localify.api.LocalifyRx;
import com.github.polok.localify.utils.RxUtils;
import rx.Observable;

/**
 * BaseModule.
 */
abstract class BaseModule extends AbsModule<BaseModule.ExtAsync, BaseModule.ExtRxJava> implements Localify {

    /**
     *
     * @param executor
     */
    public BaseModule(Executor executor) {
        super(executor);
    }

    @Override
    ExtAsync createAsyncExtension() {
        return new ExtAsync();
    }

    @Override
    ExtRxJava createRxJavaExtension() {
        return new ExtRxJava();
    }

    /**
     *
     */
    public class ExtAsync extends AbsModule.Async implements LocalifyAsync<String> {
        @Override
        public void loadAssetsFile(String fileName, LocalifyCallback<String> callback) {
            RxUtils.subscribe(executor, rx().loadAssetsFile(fileName), callback);
        }

        @Override
        public void loadRawFile(@RawRes int fileNameRawId, LocalifyCallback<String> callback) {
            RxUtils.subscribe(executor, rx().loadRawFile(fileNameRawId), callback);
        }
    }

    /**
     *
     */
    public class ExtRxJava extends AbsModule.Rx implements LocalifyRx {
        @Override
        public Observable<String> loadAssetsFile(final String fileName) {
            return RxUtils.defer(new RxUtils.DefFunc<String>() {
                @Override
                public String method() {
                    return BaseModule.this.loadAssetsFile(fileName);
                }
            });
        }

        @Override
        public Observable<String> loadRawFile(@RawRes final int fileNameRawId) {
            return RxUtils.defer(new RxUtils.DefFunc<String>() {
                @Override
                public String method() {
                    return BaseModule.this.loadRawFile(fileNameRawId);
                }
            });
        }
    }

}