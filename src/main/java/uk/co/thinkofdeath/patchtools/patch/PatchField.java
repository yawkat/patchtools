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

import com.google.common.base.Joiner;
import org.objectweb.asm.Type;
import uk.co.thinkofdeath.patchtools.instruction.instructions.Utils;

import java.io.IOException;

public class PatchField {

    private PatchClass owner;
    private Ident ident;
    private String desc;
    private Mode mode;
    private Object value;
    private boolean isStatic;
    private boolean isPrivate;

    public PatchField(PatchClass owner, Command mCommand) throws IOException {
        this.owner = owner;
        if (mCommand.args.length < 2) {
            throw new ValidateException("Incorrect number of arguments for field");
        }
        ident = new Ident(mCommand.args[0]);
        mode = mCommand.mode;
        desc = mCommand.args[1];
        Utils.validateType(desc);
        if (mCommand.args.length >= 3) {
            int i;
            accessModi:
            for (i = 2; i < mCommand.args.length; i++) {
                switch (mCommand.args[i]) {
                    case "static":
                        isStatic = true;
                        break;
                    case "private":
                        isPrivate = true;
                        break;
                    default:
                        break accessModi;
                }
            }
            String[] parts = new String[mCommand.args.length - i];
            if (parts.length != 0) {
                System.arraycopy(mCommand.args, i, parts, 0, parts.length);
                value = Utils.parseConstant(Joiner.on(' ').join(parts));
            }
        }
    }

    public Ident getIdent() {
        return ident;
    }

    public Type getDesc() {
        return Type.getMethodType(desc);
    }

    public String getDescRaw() {
        return desc;
    }

    public PatchClass getOwner() {
        return owner;
    }

    public Mode getMode() {
        return mode;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public Object getValue() {
        return value;
    }
}
