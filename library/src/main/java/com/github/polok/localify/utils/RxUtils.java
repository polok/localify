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
package com.github.polok.localify.utils;

import java.util.concurrent.Executor;

import com.github.polok.localify.LocalifyCallback;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 *
 */
public final class RxUtils {

    /**
     *
     * @param executor
     * @param observable
     * @param callback
     * @param <R>
     * @return
     */
    public static <R> LocalifyCallback<R> subscribe(final Executor executor, Observable<R> observable, final LocalifyCallback<R> callback) {
        observable
                .subscribe(
                        new Action1<R>() {
                            @Override
                            public void call(final R r) {
                                executor.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.onSuccess(r);
                                    }
                                });
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(final Throwable throwable) {
                                executor.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.onError(throwable);
                                    }
                                });
                            }
                        });

        return callback;
    }

    /**
     *
     * @param <T>
     */
    public abstract static class DefFunc<T> implements Func0<Observable<T>> {
        @Override
        public final Observable<T> call() {
            return Observable.just(method());
        }

        /**
         *
         * @return
         */
        public abstract T method();
    }

    /**
     *
     * @param func
     * @param <R>
     * @return
     */
    public static <R> Observable<R> defer(DefFunc<R> func) {
        return Observable.defer(func).subscribeOn(Schedulers.io());
    }

}
