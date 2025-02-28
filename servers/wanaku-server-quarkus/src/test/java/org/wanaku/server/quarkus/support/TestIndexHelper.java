/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.wanaku.server.quarkus.support;

import java.io.File;
import java.io.IOException;

import ai.wanaku.core.util.IndexHelper;
import ai.wanaku.core.util.support.ResourcesHelper;
import ai.wanaku.core.util.support.ToolsHelper;
import ai.wanaku.server.quarkus.api.v1.resources.ResourcesResourceTest;
import ai.wanaku.server.quarkus.api.v1.tools.ToolsResourceTest;

public class TestIndexHelper {
    public static File createToolsIndex() throws IOException {
        File indexFile = new File(ToolsHelper.TOOLS_INDEX);
        if (!indexFile.getParentFile().exists()) {
            indexFile.getParentFile().mkdirs();
        }

        indexFile.deleteOnExit();

        if (indexFile.exists()) {
            return indexFile;
        }

        // Save the index to a file
        IndexHelper.saveToolsIndex(indexFile, ToolsResourceTest.TOOL_REFERENCES);
        return indexFile;
    }

    public static File createResourcesIndex() throws IOException {
        File indexFile = new File(ResourcesHelper.RESOURCES_INDEX);
        if (!indexFile.getParentFile().exists()) {
            indexFile.getParentFile().mkdirs();
        }

        indexFile.deleteOnExit();

        if (indexFile.exists()) {
            return indexFile;
        }

        // Save the index to a file
        IndexHelper.saveResourcesIndex(indexFile, ResourcesResourceTest.RESOURCE_REFERENCES);
        return indexFile;
    }
}
