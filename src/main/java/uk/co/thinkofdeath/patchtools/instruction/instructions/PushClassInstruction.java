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

package uk.co.thinkofdeath.patchtools.instruction.instructions;

import com.google.common.collect.ImmutableList;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;
import uk.co.thinkofdeath.patchtools.PatchScope;
import uk.co.thinkofdeath.patchtools.instruction.Instruction;
import uk.co.thinkofdeath.patchtools.instruction.InstructionHandler;
import uk.co.thinkofdeath.patchtools.matching.MatchClass;
import uk.co.thinkofdeath.patchtools.matching.MatchGenerator;
import uk.co.thinkofdeath.patchtools.patch.Ident;
import uk.co.thinkofdeath.patchtools.patch.PatchClass;
import uk.co.thinkofdeath.patchtools.patch.PatchInstruction;
import uk.co.thinkofdeath.patchtools.patch.ValidateException;
import uk.co.thinkofdeath.patchtools.wrappers.ClassSet;

import java.util.Arrays;
import java.util.List;

public class PushClassInstruction implements InstructionHandler {
    @Override
    public boolean check(ClassSet classSet, PatchScope scope, PatchInstruction patchInstruction, MethodNode method, AbstractInsnNode insn) {
        if (!(insn instanceof LdcInsnNode)) {
            return false;
        }
        LdcInsnNode ldcInsnNode = (LdcInsnNode) insn;
        String className = patchInstruction.params[0];

        if (ldcInsnNode.cst instanceof Type) {
            if (className.equals("*")) {
                return true;
            }

            Type type = (Type) ldcInsnNode.cst;

            return PatchClass.checkTypes(classSet, scope, Type.getObjectType(className), type);
        } else {
            return false;
        }
    }

    @Override
    public AbstractInsnNode create(ClassSet classSet, PatchScope scope, PatchInstruction instruction, MethodNode method) {
        StringBuilder nDesc = new StringBuilder();
        PatchClass.updatedTypeString(classSet, scope, nDesc, Type.getObjectType(instruction.params[0]));
        String desc = nDesc.toString();
        return new LdcInsnNode(desc);
    }

    @Override
    public boolean print(Instruction instruction, StringBuilder patch, MethodNode method, AbstractInsnNode insn) {
        if (!(insn instanceof LdcInsnNode) || !(((LdcInsnNode) insn).cst instanceof Type)) {
            return false;
        }
        patch.append("push-class ")
            .append(((Type) ((LdcInsnNode) insn).cst).getInternalName());
        return true;
    }

    @Override
    public void validate(PatchInstruction instruction) throws ValidateException {
        if (instruction.params.length != 1) {
            throw new ValidateException("Incorrect number of arguments for push-class");
        }

        Utils.validateObjectType(instruction.params[0]);
    }

    @Override
    public List<MatchClass> getReferencedClasses(PatchInstruction instruction) {
        String className = instruction.params[0];

        if (className.equals("*")) {
            return ImmutableList.of();
        }

        Type type = MatchGenerator.getRootType(Type.getType(className));
        if (type.getSort() != Type.OBJECT) {
            return ImmutableList.of();
        }
        return Arrays.asList(new MatchClass(new Ident(type.getInternalName()).getName()));
    }
}
