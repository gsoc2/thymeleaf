/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2014, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package org.thymeleaf.engine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.thymeleaf.inline.IInliner;
import org.thymeleaf.util.Validate;

/**
 *
 * @author Daniel Fern&aacute;ndez
 * @since 3.0.0
 * 
 */
final class DocumentStructureHandler implements IDocumentStructureHandler {


    boolean insertText;
    String insertTextValue;
    boolean insertTextProcessable;

    boolean insertMarkup;
    IMarkup insertMarkupValue;
    boolean insertMarkupProcessable;

    boolean setLocalVariable;
    Map<String,Object> addedLocalVariables;

    boolean removeLocalVariable;
    Set<String> removedLocalVariableNames;

    boolean setSelectionTarget;
    Object selectionTargetObject;

    boolean setInliner;
    IInliner setInlinerValue;




    DocumentStructureHandler() {
        super();
        reset();
    }



    public void insert(final String text, final boolean processable) {
        resetAllButLocalVariables();
        Validate.notNull(text, "Text cannot be null");
        this.insertText = true;
        this.insertTextValue = text;
        this.insertTextProcessable = processable;
    }


    public void insert(final IMarkup markup, final boolean processable) {
        resetAllButLocalVariables();
        Validate.notNull(markup, "Markup cannot be null");
        this.insertMarkup = true;
        this.insertMarkupValue = markup;
        this.insertMarkupProcessable = processable;
    }


    public void removeLocalVariable(final String name) {
        // Can be combined with others, no need to reset
        this.removeLocalVariable = true;
        if (this.removedLocalVariableNames == null) {
            this.removedLocalVariableNames = new HashSet<String>(3);
        }
        this.removedLocalVariableNames.add(name);
    }


    public void setLocalVariable(final String name, final Object value) {
        // Can be combined with others, no need to reset
        this.setLocalVariable = true;
        if (this.addedLocalVariables == null) {
            this.addedLocalVariables = new HashMap<String, Object>(3);
        }
        this.addedLocalVariables.put(name, value);
    }


    public void setSelectionTarget(final Object selectionTarget) {
        // Can be combined with others, no need to reset
        this.setSelectionTarget = true;
        this.selectionTargetObject = selectionTarget;
    }


    public void setInliner(final IInliner inliner) {
        this.setInliner = true;
        this.setInlinerValue = inliner;
    }




    public void reset() {

        resetAllButLocalVariables();

        this.setLocalVariable = false;
        if (this.addedLocalVariables != null) {
            this.addedLocalVariables.clear();
        }

        this.removeLocalVariable = false;
        if (this.removedLocalVariableNames != null) {
            this.removedLocalVariableNames.clear();
        }

        this.setSelectionTarget = false;
        this.selectionTargetObject = null;

        this.setInliner = false;
        this.setInlinerValue = null;

    }


    private void resetAllButLocalVariables() {

        this.insertText = false;
        this.insertTextValue = null;
        this.insertTextProcessable = false;

        this.insertMarkup = false;
        this.insertMarkupValue = null;
        this.insertMarkupProcessable = false;

    }


}