/*
 * Copyright 2004-2005 the original author or authors.
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
package org.codehaus.groovy.grails.commons;

/**
 * @author Steven Devijver
 */
public class DefaultGrailsServiceClass extends AbstractInjectableGrailsClass implements GrailsServiceClass {

    public static final String SERVICE = "Service";
    private static final String TRANSACTIONAL = "transactional";

    private boolean transactional = true;
    private String dataSourceName;

    public DefaultGrailsServiceClass(Class<?> clazz) {
        super(clazz, SERVICE);

        Object tmpTransactional = getPropertyOrStaticPropertyOrFieldValue(TRANSACTIONAL, Boolean.class);
        transactional = tmpTransactional == null || tmpTransactional.equals(Boolean.TRUE);
    }

    public boolean isTransactional() {
        return transactional;
    }

    public String getDataSource() {
        if (dataSourceName == null) {
            if (isTransactional()) {
                CharSequence name = getStaticPropertyValue(DATA_SOURCE, CharSequence.class);
                dataSourceName = name == null ? null : name.toString();
                if (dataSourceName == null) {
                    dataSourceName = DEFAULT_DATA_SOURCE;
                }
            }
            else {
                dataSourceName = "";
            }
        }

        return dataSourceName;
    }

    public boolean usesDataSource(final String name) {
        return getDataSource().equals(name);
    }
}
