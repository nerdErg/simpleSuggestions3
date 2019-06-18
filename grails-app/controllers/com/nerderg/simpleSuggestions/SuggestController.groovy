/*
Copyright 2013 nerdErg Pty Ltd

This file is part of Simple Suggestions plugin.

Licensed under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License. You may obtain a copy
of the License at http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

 */

package com.nerderg.simpleSuggestions

/**
 * The suggestion controller provides a single end point at suggest/[subject]?term=blah, Where [subject] is the topic
 * that needs a suggestion and the term "blah" is what has been entered by the user.
 *
 * see <a href="http://nerderg.com/Simple+Suggestions+plugin">nerderg.com</a> for details.
 */

import grails.converters.JSON

class SuggestController {

    def suggestService

    /**
     * This is the /suggest action. It takes a subject and a term and gets a list of suggestions from the suggestion
     * service and return them as a JSON list.
     * @param subject
     * @param term
     * @return JSON list
     */
    def suggest(String subject, String term) {
        List suggestions = suggestService.getSuggestions(subject, term, params)
        render suggestions as JSON
    }

}
