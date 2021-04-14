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

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;


@Controller("/user")
public class UserController {

    @Inject
    protected UserService userService;


    @Get("/{Id}")
    public String show(String Id) {
        return userService.findById(Id);
    }

    @Post("/")
    public HttpResponse<User> save(@Body @Valid User user){
        userService.save(user);
        return HttpResponse
                .created(user)
                .headers(headers -> headers.location(toUri(user)));
    }

    @Delete("/{Id}")
    public String delete(String Id) {
        userService.deleteById(Id);
        return "Document Deleted";
    }

    private URI toUri(User user) {
        return URI.create("/employee/"+user.getId());
    }
}
