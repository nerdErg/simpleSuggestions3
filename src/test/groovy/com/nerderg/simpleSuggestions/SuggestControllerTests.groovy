package com.nerderg.simpleSuggestions

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification
import groovy.mock.interceptor.MockFor

class SuggestControllerTests extends Specification implements ControllerUnitTest<SuggestController> {

    void "test getting a suggestion"() {

        when: "We ask the controller for a suggestion"
        def mockControl = new MockFor(SuggestService)
        mockControl.demand.getSuggestions(1..1) { String subject, String term, Map params ->
            ['one', 'two']
        }
        controller.suggestService = mockControl.proxyDelegateInstance()
        controller.suggest('test', 'blah')

        then: "it returns simple text json response."

        '["one","two"]' == response.text
        "two" == response.json[1]
    }
}
