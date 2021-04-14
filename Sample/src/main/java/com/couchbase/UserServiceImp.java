/*
 * Copyright (c) 2021 Couchbase, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.couchbase;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.kv.MutationResult;
import io.micronaut.context.ApplicationContext;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

public class UserServiceImp implements UserService{

    @Inject
    ApplicationContext applicationContext = ApplicationContext.run();
    Cluster cluster = applicationContext.getBean(Cluster.class);
    Bucket bucket = cluster.bucket("default");
    Collection collection = bucket.defaultCollection();

    @Override
    public String findById(@NotNull String Id) {
        GetResult result  = collection.get(Id);
        return result.contentAsObject().toString();
    }


    @Override
    public User save(@NotNull User user) {
        MutationResult result = collection.upsert(user.getId(),user);
        return user;
    }

    @Override
    public void deleteById(String Id) {
        collection.remove(Id);
    }

}
