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

package uk.co.thinkofdeath.patchtools.testcode;

public class BasicClass {

    public String str;

    public BasicClass() {

    }

    public BasicClass(String str) {
        this.str = str;
    }

    public String hello() {
        return "Hello bob";
    }

    public static BasicClass create() {
        return new BasicClass("Testing");
    }

    @Override
    public String toString() {
        return str;
    }
}
