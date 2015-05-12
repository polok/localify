#Localify

###Version: 1.0.0

[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Localify-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1791)

###Description

Localify is a small library which allows to load a content from files which are stored under assets and/or raw directory. It can be useful when you have to load some init data or during tests to load mock data.

####Welcome to fork and pull request.

#####1.Integration

###### Using Gradle:

```groovy
dependencies {
    compile 'com.github.polok.localify:localify:1.0.0'
}
```

###### Using Maven:

```xml
<dependency>
    <groupId>com.github.polok.localify</groupId>
    <artifactId>localify</artifactId>
    <version>1.0.0</version>
</dependency>
```

#####2.Usage

######First you have to init a LocalifyClient (the basic  version) where you have to set the AssetManager and/or the Resources
```java
LocalifyClient localifyClient = new LocalifyClient.Builder()
                .withAssetManager(getAssets()) //If you want to read from raw directory
                .withResources(getResources()) //If you want to read from assets directory
                .build();
```

######Read a file from assets or raw directory. You can do this in three different ways:
######1)
```java
String loadAssetsFile(String fileName);
String loadRawFile(@RawRes int fileNameRawId);
```

```java
//Read from assets directory
String dataAssets = localifyClient.localify().loadAssetsFile("test.text");

//Read from raw directory
String dataRaw = localifyClient.localify().loadRawFile(R.raw.test);
```

######2)
```java
void loadAssetsFile(String fileName, LocalifyCallback<T> callback);
void loadRawFile(@RawRes int fileNameRawId, LocalifyCallback<T> callback);
```

```java
//Read from assets directory
localifyClient.localify()
            .async()
            .loadAssetsFile("test.txt", new LocalifyCallback<String>() {
                @Override
                public void onSuccess(String data) {
                }

                @Override
                public void onError(Throwable throwable) {
                }
            });

//Read from raw directory
localifyClient.localify()
            .async()
            .loadRawFile(R.raw.test, new LocalifyCallback<String>() {
                @Override
                public void onSuccess(String data) {
                }

                @Override
                public void onError(Throwable throwable) {
                }
            });
```

######3)
```java
Observable<String> loadAssetsFile(String fileName);
Observable<String> loadRawFile(@RawRes int fileNameRawId);
```

```java
localifyClient.localify()
            .rx()
            .loadAssetsFile("test.txt")
            .subscribeOn(Schedulers.io())
            .subscribe(new Subscriber<String>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onNext(String s) {
                }
            });

localifyClient.localify()
            .rx()
            .loadRawFile(R.raw.test)
            .subscribeOn(Schedulers.io())
            .subscribe(new Subscriber<String>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onNext(String s) {
                }
            });

```

####3. Additional settings
By default asynchronous callbacks run in Android main thread. If you want this can be changed by 'withExecutor(executor)' method invoked on LocalifyCalient's builder:

```java
LocalifyClient localifyClient = new LocalifyClient.Builder()
            .withExecutor(....)
            ...
            .build();

```

####4. More examples
######Load json file and map to object
```java
localifyClient.localify()
            .rx()
            .loadAssetsFile("test.json")
            .subscribeOn(Schedulers.io())
            .map(new Func1<String, User>() {
                @Override
                public User call(String data) {
                    Gson gson = new GsonBuilder().create();
                    return gson.fromJson(data, User.class);
                }
            }).subscribe(new Subscriber<User>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onNext(User user) {
                }
        });
```

####If you want to see more details, go ahead and check the demo!

License
--------

    Copyright 2015 Marcin Polak

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

