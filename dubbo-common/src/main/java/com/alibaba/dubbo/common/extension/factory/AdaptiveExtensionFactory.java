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
package com.alibaba.dubbo.common.extension.factory;

import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.ExtensionFactory;
import com.alibaba.dubbo.common.extension.ExtensionLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * AdaptiveExtensionFactory
 *这货就相当于一个代理入口，他会遍历当前系统中所有的ExtensionFactory实现来获取指定的扩展实现，
 * 取到扩展实现或遍历完所有的ExtensionFactory实现。这里调用了ExtensionLoader的getSupportedExtensions方法
 * 来获取ExtensionFactory的所有实现，
 * 又回到了ExtensionLoader类，下面我们就来分析ExtensionLoader的几个重要的实例方法
 * @author william.liangf
 */
@Adaptive
public class AdaptiveExtensionFactory implements ExtensionFactory {

    private final List<ExtensionFactory> factories;

    public AdaptiveExtensionFactory() {
        ExtensionLoader<ExtensionFactory> loader = ExtensionLoader.getExtensionLoader(ExtensionFactory.class);
        List<ExtensionFactory> list = new ArrayList<ExtensionFactory>();
        // 将所有ExtensionFactory实现保存起来
//        for (String name : loader.getSupportedExtensions()) {
//            list.add(loader.getExtension(name));
//        }
        Set<String> supportedExtensionSets=loader.getSupportedExtensions();
        for (String name : supportedExtensionSets) {
            ExtensionFactory extensionFactory=loader.getExtension(name);
            list.add(extensionFactory);
        }
        factories = Collections.unmodifiableList(list);
    }

    public <T> T getExtension(Class<T> type, String name) {
        // 依次遍历各个ExtensionFactory实现的getExtension方法，一旦获取到Extension即返回
        // 如果遍历完所有的ExtensionFactory实现均无法找到Extension,则返回null
        for (ExtensionFactory factory : factories) {
            T extension = factory.getExtension(type, name);
            if (extension != null) {
                return extension;
            }
        }
        return null;
    }

}
