/*
 * Copyright 1999-2012 Alibaba Group.
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
package com.alibaba.dubbo.config.validation;

import com.alibaba.dubbo.validation.MethodValidated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * ValidationService
 *
 * @author william.liangf
 */
public interface ValidationService { // 缺省可按服务接口区分验证场景，如：@NotNull(groups = ValidationService.class)

    /**
     *  没有加上“@MethodValidated(ValidationService.Save.class)”这句代码时，
     *  现在的检查逻辑不会去检验groups = ValidationService.Save.class这个分组
     *
     * @param parameter
     */
    @MethodValidated(Save.class)
    void save(ValidationParameter parameter);

    void update(ValidationParameter parameter);

    void delete(@Min(1) long id, @NotNull @Size(min = 2, max = 16) @Pattern(regexp = "^[a-zA-Z]+$") String operator);

    /**
     * 假设关联查询的时候需要同时传id和email的值。这时需要检查Sava分组和Update分组。
     * @param parameter
     */
    @MethodValidated({Save.class, Update.class})
    void relatedQuery(ValidationParameter parameter);

    @interface Save {
    } // 与方法同名接口，首字母大写，用于区分验证场景，如：@NotNull(groups = ValidationService.Save.class)，可选

    @interface Update {
    } // 与方法同名接口，首字母大写，用于区分验证场景，如：@NotNull(groups = ValidationService.Update.class)，可选


}
