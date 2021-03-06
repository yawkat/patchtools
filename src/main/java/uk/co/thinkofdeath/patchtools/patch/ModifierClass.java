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

package uk.co.thinkofdeath.patchtools.patch;

import org.jetbrains.annotations.NotNull;

public class ModifierClass {

    private final Ident ident;
    private final Mode mode;

    public ModifierClass(@NotNull Ident ident, @NotNull Mode mode) {
        this.ident = ident;
        this.mode = mode;
    }

    public Ident getIdent() {
        return ident;
    }

    public Mode getMode() {
        return mode;
    }
}
