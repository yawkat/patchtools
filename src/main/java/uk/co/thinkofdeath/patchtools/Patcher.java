/*
 * Copyright 2014 Matthew Collins
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.thinkofdeath.patchtools;

import uk.co.thinkofdeath.patchtools.matching.MatchGenerator;
import uk.co.thinkofdeath.patchtools.patch.LineReader;
import uk.co.thinkofdeath.patchtools.patch.PatchClasses;
import uk.co.thinkofdeath.patchtools.wrappers.ClassSet;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Patcher {

    private final ClassSet classSet;

    public Patcher(ClassSet classSet) {
        this.classSet = classSet;
        classSet.simplify();
    }

    public PatchScope apply(InputStream inputStream) {
        return apply(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    public PatchScope apply(Reader reader) {
        return apply(new LineReader(reader));
    }

    public PatchScope apply(LineReader reader) {
        return apply(reader, new PatchScope());
    }

    public PatchScope apply(LineReader reader, PatchScope patchScope) {
        PatchClasses patchClasses;
        try (LineReader ignored = reader) {
            patchClasses = new PatchClasses(reader);
            return apply(patchClasses, patchScope);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public PatchScope apply(PatchClasses patchClasses, PatchScope patchScope) {
        MatchGenerator generator = new MatchGenerator(classSet, patchClasses, patchScope);
        PatchScope foundScope = generator.apply();
        if (foundScope == null) {
            return null;
        }
        patchClasses.getClasses().forEach(c -> c.apply(foundScope, classSet));
        return foundScope;
    }

    public ClassSet getClasses() {
        return classSet;
    }
}
